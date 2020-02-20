package es.rfgr.cr4vegas.modelo.postgresql.bd.almacen.taquilla;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import es.rfgr.cr4vegas.modelo.postgresql.conexion.ConexionAlmacen;
import es.rfgr.cr4vegas.modelo.postgresql.dto.almacen.taquilla.TipoTaquilla;
import es.rfgr.cr4vegas.modelo.postgresql.dto.almacen.taquilla.TipoTaquillaPK;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class TipoTaquillaBD {

	private Connection userConn;

	private final String tableName = "public.tipotaquilla";

	// SQL DML

	private final String SQL_SELECT = "SELECT * FROM " + getTableName();

	private final String SQL_INSERT = "INSERT INTO " + getTableName() + " VALUES (?)";

	private final String SQL_UPDATE = "UPDATE " + getTableName() + " SET nombre=? WHERE nombre=?";

	private final String SQL_DELETE = "DELETE FROM " + getTableName() + " WHERE nombre=?";

	// COLUMNS

	private static final int COLUMN_NOMBRE = 1;

	@SuppressWarnings("unused")
	private static final int PK_COLUMN_NOMBRE = 1;

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

	public TipoTaquillaBD() {

	}

	public void setConnection(Connection conn) {
		this.userConn = conn;
	}

	// FUNCTIONS

	public TipoTaquillaPK insert(TipoTaquilla dto) throws SQLException {
		try {
			initializeTimeConnSuppliedConn();

			ps = conn.prepareStatement(SQL_INSERT);

			ps.setString(1, dto.getNombre());
			System.out.println("Ejecutando " + SQL_INSERT + " con DTO: " + dto);

			rows = ps.executeUpdate();

			finalizeTimeConnSuppliedConn();

			return dto.createPK();

		} finally {
			finallyTryCatch();
		}
	}

	public void update(TipoTaquillaPK pk, TipoTaquilla dto) throws SQLException {
		try {
			initializeTimeConnSuppliedConn();

			ps = conn.prepareStatement(SQL_UPDATE);

			index = 1;
			ps.setString(index++, dto.getNombre());
			ps.setString(index, pk.getNombre());
			System.out.println("Ejecutando " + SQL_UPDATE + " with DTO: " + dto);

			rows = ps.executeUpdate();

			finalizeTimeConnSuppliedConn();

		} finally {
			finallyTryCatch();
		}
	}

	public void delete(TipoTaquillaPK pk) throws SQLException {
		try {
			initializeTimeConnSuppliedConn();

			ps = conn.prepareStatement(SQL_DELETE);

			ps.setString(1, pk.getNombre());
			System.out.println("Ejecutando " + SQL_DELETE + " with DTO: " + pk);

			rows = ps.executeUpdate();

			finalizeTimeConnSuppliedConn();

		} finally {
			finallyTryCatch();
		}
	}

	public ObservableList<TipoTaquilla> findAll() throws SQLException {
		return findByDynamicSelect(SQL_SELECT + " ORDER BY nombre", null);
	}

	public TipoTaquilla findByPrimaryKey(TipoTaquillaPK pk) throws SQLException {
		return findByPrimaryKey(pk.getNombre());
	}

	public TipoTaquilla findByPrimaryKey(String nombre) throws SQLException {
		ObservableList<TipoTaquilla> tiposTaquilla = findByDynamicSelect(SQL_SELECT + " WHERE nombre = ?",
				new Object[] { nombre });
		return tiposTaquilla.size() == 0 ? null : tiposTaquilla.get(0);
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

	private void populateDto(TipoTaquilla dto, ResultSet rs) throws SQLException {
		dto.setNombre(rs.getString(COLUMN_NOMBRE));
	}

	private ObservableList<TipoTaquilla> fetchMultiResults(ResultSet rs) throws SQLException {
		ObservableList<TipoTaquilla> resultList = FXCollections.observableArrayList();
		while (rs.next()) {
			TipoTaquilla dto = new TipoTaquilla();
			populateDto(dto, rs);
			resultList.add(dto);
		}
		return resultList;
	}

	private ObservableList<TipoTaquilla> findByDynamicSelect(String sql, Object[] sqlParams) throws SQLException {
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
