package es.rfgr.cr4vegas.modelo.postgresql.bd.mantenimiento.estacion;

import java.io.ByteArrayInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import es.rfgr.cr4vegas.modelo.postgresql.conexion.ConexionAlmacen;
import es.rfgr.cr4vegas.modelo.postgresql.dto.mantenimiento.estacion.ImagenEstacion;
import es.rfgr.cr4vegas.modelo.postgresql.dto.mantenimiento.estacion.ImagenEstacionPK;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ImagenEstacionBD {

	private Connection userConn;

	private final String tableName = "public.imagenestacion";

	// SQL DML

	private final String SQL_SELECT = "SELECT * FROM " + getTableName();

	private final String SQL_INSERT = "INSERT INTO " + getTableName() + " VALUES (0, ?, ?, ?)";

	private final String SQL_UPDATE = "UPDATE " + getTableName() + " SET nombre=?, codestacion=?"
			+ ", imagen=? WHERE codimagen=?";

	private final String SQL_DELETE = "DELETE FROM " + getTableName() + " WHERE codimagen=?";

	// COLUMNS

	private static final int COLUMN_CODIMAGEN = 1;

	private static final int COLUMN_NOMBRE = 2;

	private static final int COLUMN_CODESTACION = 3;

	private static final int COLUMN_IMAGEN = 4;

	private static final int PK_COLUMN_CODIMAGEN = 1;

	// CLASS VARS

	private int maxRows;

	private PreparedStatement ps;

	private Connection conn;

	private ResultSet rs;

	private long t1, t2;

	private int rows;

	private boolean isConnSupplied;

	private int index;

	// BUILDERS
	public ImagenEstacionBD() {

	}

	public void setConnection(Connection conn) {
		this.userConn = conn;
	}

	public ImagenEstacionPK insert(ImagenEstacion dto) throws SQLException {
		try {
			initializeTimeConnSuppliedConn();

			ps = conn.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS);

			index = 1;
			ps.setString(index++, dto.getNombre());
			ps.setInt(index++, dto.getCodEstacion());
			ps.setBinaryStream(index, new ByteArrayInputStream(dto.getImagen()), dto.getImagen().length);
			System.out.println("Ejecutando " + SQL_INSERT + " con DTO: " + dto);

			rows = ps.executeUpdate();

			finalizeTimeConnSuppliedConn();

			// Obtener valores de las columnas auto-incrementables
			rs = ps.getGeneratedKeys();
			if (rs != null && rs.next()) {
				dto.setCodImagen(rs.getInt(PK_COLUMN_CODIMAGEN));
			}

			return (ImagenEstacionPK) dto.createPK();

		} finally {
			finallyTryCatch();
		}
	}

	public void update(ImagenEstacionPK pk, ImagenEstacion dto) throws SQLException {
		try {
			initializeTimeConnSuppliedConn();

			ps = conn.prepareStatement(SQL_UPDATE);

			index = 1;
			ps.setString(index++, dto.getNombre());
			ps.setInt(index++, dto.getCodEstacion());
			ps.setBinaryStream(index++, new ByteArrayInputStream(dto.getImagen()), dto.getImagen().length);
			ps.setInt(index, pk.getCodImagen());
			System.out.println("Ejecutando " + SQL_UPDATE + " with DTO: " + dto);

			rows = ps.executeUpdate();

			finalizeTimeConnSuppliedConn();

		} finally {
			finallyTryCatch();
		}
	}

	public void delete(ImagenEstacionPK pk) throws SQLException {
		try {
			initializeTimeConnSuppliedConn();

			ps = conn.prepareStatement(SQL_DELETE);

			ps.setInt(1, pk.getCodImagen());
			System.out.println("Ejecutando " + SQL_DELETE + " with DTO: " + pk);

			rows = ps.executeUpdate();

			finalizeTimeConnSuppliedConn();

		} finally {
			finallyTryCatch();
		}
	}

	public ObservableList<ImagenEstacion> findAll() throws SQLException {
		return findByDynamicSelect(SQL_SELECT + " ORDER BY codimagen", null);
	}
	
	public ImagenEstacion findByPrimaryKey(ImagenEstacionPK pk) throws SQLException {
		return findByPrimaryKey( pk.getCodImagen() );
	}

	public ImagenEstacion findByPrimaryKey(int codImagenEstacion) throws SQLException {
		ObservableList<ImagenEstacion> imagenesEstaciones = findByDynamicSelect( SQL_SELECT + " WHERE codimagen = ?", new Object[] {  Integer.valueOf(codImagenEstacion) } );
		return imagenesEstaciones.size() == 0 ? null : imagenesEstaciones.get(0);
	}

	public void setMaxRows(int maxRows) {
		this.maxRows = maxRows;
	}

	public int getMaxRows() {
		return maxRows;
	}

	public String getTableName() {
		return tableName;
	}

	private void populateDto(ImagenEstacion dto, ResultSet rs) throws SQLException {
		dto.setCodImagen(rs.getInt(COLUMN_CODIMAGEN));
		dto.setNombre(rs.getString(COLUMN_NOMBRE));
		dto.setCodEstacion(rs.getInt(COLUMN_CODESTACION));
		dto.setImagen(rs.getBytes(COLUMN_IMAGEN));
	}

	private ObservableList<ImagenEstacion> fetchMultiResults(ResultSet rs) throws SQLException {
		ObservableList<ImagenEstacion> resultList = FXCollections.observableArrayList();
		while (rs.next()) {
			ImagenEstacion dto = new ImagenEstacion();
			populateDto(dto, rs);
			resultList.add(dto);
		}
		return resultList;
	}

	private ObservableList<ImagenEstacion> findByDynamicSelect(String sql, Object[] sqlParams) throws SQLException {
		initializeTimeConnSuppliedConn();

		final String SQL = sql;

		ps = conn.prepareStatement(SQL);
		ps.setMaxRows(maxRows);

		// bind parameters
		for (int i = 0; sqlParams != null && i < sqlParams.length; i++) {
			ps.setObject(i + 1, sqlParams[i]);
		}

		System.out.println("Ejecutando " + SQL);

		rs = ps.executeQuery();
		
		finalizeTimeConnSuppliedConn();

		return fetchMultiResults(rs);
	}

	private void finallyTryCatch() throws SQLException {
		if (ps != null) {
			ConexionAlmacen.close(ps);
		}
		if (rs != null) {
			ConexionAlmacen.close(rs);
		}
		if ((userConn == null || userConn.isClosed()) && conn != null) {
			ConexionAlmacen.close(conn);
		}
	}

	private void initializeTimeConnSuppliedConn() throws SQLException {
		t1 = System.currentTimeMillis();

		isConnSupplied = userConn != null && !userConn.isClosed();

		conn = isConnSupplied ? userConn : ConexionAlmacen.getConexion();
	}

	private void finalizeTimeConnSuppliedConn() {
		t2 = System.currentTimeMillis();

		System.out.println(rows + " filas afectadas (" + (t2 - t1) + " ms)");
	}
}
