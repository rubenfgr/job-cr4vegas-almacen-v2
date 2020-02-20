package es.rfgr.cr4vegas.modelo.postgresql.bd.mantenimiento.unidaddecontrol;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import es.rfgr.cr4vegas.modelo.postgresql.conexion.ConexionAlmacen;
import es.rfgr.cr4vegas.modelo.postgresql.dto.mantenimiento.unidaddecontrol.RegistroImagenUnidadDeControl;
import es.rfgr.cr4vegas.modelo.postgresql.dto.mantenimiento.unidaddecontrol.RegistroImagenUnidadDeControlPK;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class RegistroImagenUnidadDeControlBD {

	private Connection userConn;

	private final String tableName = "public.registroimagenudcontrol";

	// SQL DML

	private final String SQL_SELECT = "SELECT * FROM " + getTableName();

	private final String SQL_INSERT = "INSERT INTO " + getTableName() + " VALUES (?, ?)";

	private final String SQL_UPDATE = "UPDATE " + getTableName()
			+ " SET codimagen=? WHERE codregistro=? AND codimagen=?";

	private final String SQL_DELETE = "DELETE FROM " + getTableName() + " WHERE codregistro=? AND codimagen=?";

	// COLUMNS

	private static final int COLUMN_CODREGISTRO = 1;

	private static final int COLUMN_CODIMAGEN = 2;

	@SuppressWarnings("unused")
	private static final int PK_COLUMN_CODREGISTRO = 1;

	@SuppressWarnings("unused")
	private static final int PK_COLUMN_CODIMAGEN = 2;

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

	public RegistroImagenUnidadDeControlBD() {

	}

	public void setConnection(Connection conn) {
		this.userConn = conn;
	}

	public RegistroImagenUnidadDeControlPK insert(RegistroImagenUnidadDeControl dto) throws SQLException {
		try {
			initializeTimeConnSuppliedConn();

			ps = conn.prepareStatement(SQL_INSERT);

			index = 1;
			ps.setInt(index++, dto.getCodRegistroUnidadDeControl());
			ps.setInt(index, dto.getCodImagenUdControl());
			System.out.println("Ejecutando " + SQL_INSERT + " con DTO: " + dto);

			rows = ps.executeUpdate();

			finalizeTimeConnSuppliedConn();

			return dto.createPK();

		} finally {
			finallyTryCatch();
		}
	}

	public void update(RegistroImagenUnidadDeControlPK pk, RegistroImagenUnidadDeControl dto) throws SQLException {
		try {
			initializeTimeConnSuppliedConn();

			ps = conn.prepareStatement(SQL_UPDATE);

			index = 1;
			ps.setInt(index++, dto.getCodImagenUdControl());
			ps.setInt(index++, pk.getCodRegistroUnidadDeControl());
			ps.setInt(index, pk.getCodImagenUdControl());
			System.out.println("Ejecutando " + SQL_UPDATE + " with DTO: " + dto);

			rows = ps.executeUpdate();

			finalizeTimeConnSuppliedConn();

		} finally {
			finallyTryCatch();
		}
	}

	public void delete(RegistroImagenUnidadDeControlPK pk) throws SQLException {
		try {
			initializeTimeConnSuppliedConn();

			ps = conn.prepareStatement(SQL_DELETE);

			index = 1;
			ps.setInt(index++, pk.getCodRegistroUnidadDeControl());
			ps.setInt(index, pk.getCodImagenUdControl());
			System.out.println("Ejecutando " + SQL_DELETE + " with DTO: " + pk);

			rows = ps.executeUpdate();

			finalizeTimeConnSuppliedConn();

		} finally {
			finallyTryCatch();
		}
	}

	public ObservableList<RegistroImagenUnidadDeControl> findAll() throws SQLException {
		return findByDynamicSelect(SQL_SELECT + " ORDER BY codregistro, codimagen", null);
	}

	public RegistroImagenUnidadDeControl findByPrimaryKey(RegistroImagenUnidadDeControlPK pk) throws SQLException {
		return findByPrimaryKey(pk.getCodRegistroUnidadDeControl(), pk.getCodImagenUdControl());
	}

	public RegistroImagenUnidadDeControl findByPrimaryKey(int codRegistro, int codImagen) throws SQLException {
		ObservableList<RegistroImagenUnidadDeControl> registroImagenUDControl = findByDynamicSelect(
				SQL_SELECT + " WHERE codregistro = ? AND codimagen = ?",
				new Object[] { Integer.valueOf(codRegistro), Integer.valueOf(codImagen) });
		return registroImagenUDControl.size() == 0 ? null : registroImagenUDControl.get(0);
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

	private void populateDto(RegistroImagenUnidadDeControl dto, ResultSet rs) throws SQLException {
		dto.setCodRegistroUnidadDeControl(rs.getInt(COLUMN_CODREGISTRO));
		dto.setCodImagenUdControl(rs.getInt(COLUMN_CODIMAGEN));
	}

	private ObservableList<RegistroImagenUnidadDeControl> fetchMultiResults(ResultSet rs) throws SQLException {
		ObservableList<RegistroImagenUnidadDeControl> resultList = FXCollections.observableArrayList();
		while (rs.next()) {
			RegistroImagenUnidadDeControl dto = new RegistroImagenUnidadDeControl();
			populateDto(dto, rs);
			resultList.add(dto);
		}
		return resultList;
	}

	private ObservableList<RegistroImagenUnidadDeControl> findByDynamicSelect(String sql, Object[] sqlParams)
			throws SQLException {
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
