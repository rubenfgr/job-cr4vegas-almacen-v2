package es.rfgr.cr4vegas.modelo.postgresql.bd.mantenimiento.vehiculo;

import java.io.ByteArrayInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import es.rfgr.cr4vegas.modelo.postgresql.conexion.ConexionAlmacen;
import es.rfgr.cr4vegas.modelo.postgresql.dto.mantenimiento.vehiculo.ImagenVehiculo;
import es.rfgr.cr4vegas.modelo.postgresql.dto.mantenimiento.vehiculo.ImagenVehiculoPK;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ImagenVehiculoBD {

	private Connection userConn;

	private final String tableName = "public.imagenvehiculo";

	// SQL DML

	private final String SQL_SELECT = "SELECT * FROM " + getTableName();

	private final String SQL_INSERT = "INSERT INTO " + getTableName() + " VALUES (0, ?, ?, ?)";

	private final String SQL_UPDATE = "UPDATE " + getTableName() + " SET nombre=?, matricula=?"
			+ ", imagen=? WHERE codimagen=?";

	private final String SQL_DELETE = "DELETE FROM " + getTableName() + " WHERE codimagen=?";

	// COLUMNS

	private static final int COLUMN_CODIMAGEN = 1;

	private static final int COLUMN_NOMBRE = 2;

	private static final int COLUMN_MATRICULA = 3;

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

	public ImagenVehiculoBD() {

	}

	public void setConnection(Connection conn) {
		this.userConn = conn;
	}

	public ImagenVehiculoPK insert(ImagenVehiculo dto) throws SQLException {
		try {
			initializeTimeConnSuppliedConn();

			ps = conn.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS);

			index = 1;
			ps.setString(index++, dto.getNombre());
			ps.setString(index++, dto.getMatricula());
			ps.setBinaryStream(index, new ByteArrayInputStream(dto.getImagen()), dto.getImagen().length);

			rows = ps.executeUpdate();

			finalizeTimeConnSuppliedConn();

			// Obtener valores de las columnas auto-incrementables
			rs = ps.getGeneratedKeys();
			if (rs != null && rs.next()) {
				dto.setCodImagen(rs.getInt(PK_COLUMN_CODIMAGEN));
			}

			return (ImagenVehiculoPK) dto.createPK();

		} finally {
			finallyTryCatch();
		}
	}

	public void update(ImagenVehiculoPK pk, ImagenVehiculo dto) throws SQLException {
		try {
			initializeTimeConnSuppliedConn();

			ps = conn.prepareStatement(SQL_UPDATE);

			index = 1;
			ps.setString(index++, dto.getNombre());
			ps.setString(index++, dto.getMatricula());
			ps.setBinaryStream(index++, new ByteArrayInputStream(dto.getImagen()), dto.getImagen().length);
			ps.setInt(index, pk.getCodImagen());
			System.out.println("Ejecutando " + SQL_UPDATE + " with DTO: " + dto);

			rows = ps.executeUpdate();

			finalizeTimeConnSuppliedConn();

		} finally {
			finallyTryCatch();
		}
	}

	public void delete(ImagenVehiculoPK pk) throws SQLException {
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

	public ObservableList<ImagenVehiculo> findAll() throws SQLException {
		return findByDynamicSelect(SQL_SELECT + " ORDER BY codimagen", null);
	}
	
	public ImagenVehiculo findByPrimaryKey(ImagenVehiculoPK pk) throws SQLException {
		return findByPrimaryKey( pk.getCodImagen() );
	}

	public ImagenVehiculo findByPrimaryKey(int codImagen) throws SQLException {
		ObservableList<ImagenVehiculo> imagensVehiculos = findByDynamicSelect( SQL_SELECT + " WHERE codimagen = ?", new Object[] {  Integer.valueOf(codImagen) } );
		return imagensVehiculos.size() == 0 ? null : imagensVehiculos.get(0);
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

	private void populateDto(ImagenVehiculo dto, ResultSet rs) throws SQLException {
		dto.setCodImagen(rs.getInt(COLUMN_CODIMAGEN));
		dto.setNombre(rs.getString(COLUMN_NOMBRE));
		dto.setMatricula(rs.getString(COLUMN_MATRICULA));
		dto.setImagen(rs.getBytes(COLUMN_IMAGEN));
	}

	private ObservableList<ImagenVehiculo> fetchMultiResults(ResultSet rs) throws SQLException {
		ObservableList<ImagenVehiculo> resultList = FXCollections.observableArrayList();
		while (rs.next()) {
			ImagenVehiculo dto = new ImagenVehiculo();
			populateDto(dto, rs);
			resultList.add(dto);
		}
		return resultList;
	}

	private ObservableList<ImagenVehiculo> findByDynamicSelect(String sql, Object[] sqlParams) throws SQLException {
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
