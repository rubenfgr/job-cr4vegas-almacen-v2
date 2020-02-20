package es.rfgr.cr4vegas.modelo.postgresql.bd.mantenimiento.unidaddecontrol;

import java.io.ByteArrayInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import es.rfgr.cr4vegas.modelo.postgresql.conexion.ConexionAlmacen;
import es.rfgr.cr4vegas.modelo.postgresql.dto.mantenimiento.unidaddecontrol.ImagenUnidadDeControl;
import es.rfgr.cr4vegas.modelo.postgresql.dto.mantenimiento.unidaddecontrol.ImagenUnidadDeControlPK;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ImagenUnidadDeControlBD {

	private Connection userConn;

	private final String tableName = "public.imagenudcontrol";

	// SQL DML

	private final String SQL_SELECT = "SELECT * FROM " + getTableName();

	private final String SQL_INSERT = "INSERT INTO " + getTableName() + " VALUES (0, ?, ?, ?)";

	private final String SQL_UPDATE = "UPDATE " + getTableName() + " SET nombre=?, codudcontrol=?"
			+ ", imagen=? WHERE codimagen=?";

	private final String SQL_DELETE = "DELETE FROM " + getTableName() + " WHERE codimagen=?";

	// COLUMNS

	private static final int COLUMN_CODIMAGEN = 1;

	private static final int COLUMN_NOMBRE = 2;

	private static final int COLUMN_CODUDCONTROL = 3;

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

	public ImagenUnidadDeControlBD() {

	}

	public void setConnection(Connection conn) {
		this.userConn = conn;
	}

	// FUNCTIONS

	public ImagenUnidadDeControlPK insert(ImagenUnidadDeControl dto) throws SQLException {
		try {
			initializeTimeConnSuppliedConn();

			ps = conn.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS);

			index = 1;
			ps.setString(index++, dto.getNombre());
			ps.setInt(index++, dto.getCodUdControl());
			ps.setBinaryStream(index, new ByteArrayInputStream(dto.getImagen()), dto.getImagen().length);
			System.out.println("Ejecutando " + SQL_INSERT + " con DTO: " + dto);

			rows = ps.executeUpdate();

			finalizeTimeConnSuppliedConn();

			// Obtener valores de las columnas auto-incrementables
			rs = ps.getGeneratedKeys();
			if (rs != null && rs.next()) {
				dto.setCodImagen(rs.getInt(PK_COLUMN_CODIMAGEN));
			}

			return (ImagenUnidadDeControlPK) dto.createPK();

		} finally {
			finallyTryCatch();
		}
	}

	public void update(ImagenUnidadDeControlPK pk, ImagenUnidadDeControl dto) throws SQLException {
		try {
			initializeTimeConnSuppliedConn();

			ps = conn.prepareStatement(SQL_UPDATE);

			index = 1;
			ps.setString(index++, dto.getNombre());
			ps.setInt(index++, dto.getCodUdControl());
			ps.setBinaryStream(index++, new ByteArrayInputStream(dto.getImagen()), dto.getImagen().length);
			ps.setInt(index, pk.getCodImagen());
			System.out.println("Ejecutando " + SQL_UPDATE + " with DTO: " + dto);

			rows = ps.executeUpdate();

			finalizeTimeConnSuppliedConn();

		} finally {
			finallyTryCatch();
		}
	}

	public void delete(ImagenUnidadDeControlPK pk) throws SQLException {
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

	public ObservableList<ImagenUnidadDeControl> findAll() throws SQLException {
		return findByDynamicSelect(SQL_SELECT + " ORDER BY codimagen", null);
	}

	public ImagenUnidadDeControl findByPrimaryKey(ImagenUnidadDeControlPK pk) throws SQLException {
		return findByPrimaryKey(pk.getCodImagen());
	}

	public ImagenUnidadDeControl findByPrimaryKey(int codimagen) throws SQLException {
		ObservableList<ImagenUnidadDeControl> imagenesUDControl = findByDynamicSelect(SQL_SELECT + " WHERE codimagen = ?",
				new Object[] { Integer.valueOf(codimagen) });
		return imagenesUDControl.size() == 0 ? null : imagenesUDControl.get(0);
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

	private void populateDto(ImagenUnidadDeControl dto, ResultSet rs) throws SQLException {
		dto.setCodImagen(rs.getInt(COLUMN_CODIMAGEN));
		dto.setNombre(rs.getString(COLUMN_NOMBRE));
		dto.setCodUdControl(rs.getInt(COLUMN_CODUDCONTROL));
		dto.setImagen(rs.getBytes(COLUMN_IMAGEN));
	}

	private ObservableList<ImagenUnidadDeControl> fetchMultiResults(ResultSet rs) throws SQLException {
		ObservableList<ImagenUnidadDeControl> resultList = FXCollections.observableArrayList();
		while (rs.next()) {
			ImagenUnidadDeControl dto = new ImagenUnidadDeControl();
			populateDto(dto, rs);
			resultList.add(dto);
		}
		return resultList;
	}

	private ObservableList<ImagenUnidadDeControl> findByDynamicSelect(String sql, Object[] sqlParams) throws SQLException {
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
