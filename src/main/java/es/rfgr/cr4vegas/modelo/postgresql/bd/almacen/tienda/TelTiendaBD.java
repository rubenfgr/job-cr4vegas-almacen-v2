package es.rfgr.cr4vegas.modelo.postgresql.bd.almacen.tienda;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import es.rfgr.cr4vegas.modelo.postgresql.conexion.ConexionAlmacen;
import es.rfgr.cr4vegas.modelo.postgresql.dto.almacen.tienda.TelTienda;
import es.rfgr.cr4vegas.modelo.postgresql.dto.almacen.tienda.TelTiendaPK;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class TelTiendaBD {

	private Connection userConn;

	private final String tableName = "public.teltienda";

	// SQL DML

	private final String SQL_SELECT = "SELECT * FROM " + getTableName();

	private final String SQL_INSERT = "INSERT INTO " + getTableName() + " VALUES (?, ?)";

	private final String SQL_UPDATE = "UPDATE " + getTableName() + " SET telefono=? WHERE telefono=?";

	private final String SQL_DELETE_ONE = "DELETE FROM " + getTableName() + " WHERE telefono=?";

	// COLUMNS

	private static final int COLUMN_CODTIENDA = 2;

	private static final int COLUMN_TELEFONO = 1;

	@SuppressWarnings("unused")
	private static final int PK_COLUMN_CODTIENDA = 2;

	@SuppressWarnings("unused")
	private static final int PK_COLUMN_TELEFONO = 1;

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

	public TelTiendaBD() {

	}

	public void setConnection(Connection conn) {
		this.userConn = conn;
	}

	// FUNCTIONS

	public TelTiendaPK insert(TelTienda dto) throws SQLException {
		try {
			initializeTimeConnSuppliedConn();

			ps = conn.prepareStatement(SQL_INSERT);

			index = 1;
			ps.setInt(index++, dto.getCodTienda());
			ps.setString(index, dto.getTelefono());
			System.out.println("Ejecutando " + SQL_INSERT + " con DTO: " + dto);

			rows = ps.executeUpdate();

			finalizeTimeConnSuppliedConn();

			return dto.createPK();

		} finally {
			finallyTryCatch();
		}
	}

	public void update(TelTiendaPK pk, TelTienda dto) throws SQLException {
		try {
			initializeTimeConnSuppliedConn();

			ps = conn.prepareStatement(SQL_UPDATE);

			index = 1;
			ps.setString(index++, dto.getTelefono());
			ps.setString(index, pk.getTelefono());
			System.out.println("Ejecutando " + SQL_UPDATE + " with DTO: " + dto);

			rows = ps.executeUpdate();

			finalizeTimeConnSuppliedConn();

		} finally {
			finallyTryCatch();
		}
	}

	public void delete(TelTiendaPK pk) throws SQLException {
		try {
			initializeTimeConnSuppliedConn();

			ps = conn.prepareStatement(SQL_DELETE_ONE);

			index = 1;
			ps.setString(index, pk.getTelefono());
			System.out.println("Ejecutando " + SQL_DELETE_ONE + " with DTO: " + pk);

			rows = ps.executeUpdate();

			finalizeTimeConnSuppliedConn();

		} finally {
			finallyTryCatch();
		}
	}

	public ObservableList<TelTienda> findAll() throws SQLException {
		return findByDynamicSelect(SQL_SELECT + " ORDER BY codtienda", null);
	}

	public TelTienda findByPrimaryKey(TelTiendaPK pk) throws SQLException {
		return findByPrimaryKey(pk.getTelefono());
	}

	public TelTienda findByPrimaryKey(String telefono) throws SQLException {
		ObservableList<TelTienda> telsTienda = findByDynamicSelect(SQL_SELECT + " WHERE telefono = ?",
				new Object[] { telefono });
		return telsTienda.size() == 0 ? null : telsTienda.get(0);
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

	private void populateDto(TelTienda dto, ResultSet rs) throws SQLException {
		dto.setCodTienda(rs.getInt(COLUMN_CODTIENDA));
		dto.setTelefono(rs.getString(COLUMN_TELEFONO));
	}

	private ObservableList<TelTienda> fetchMultiResults(ResultSet rs) throws SQLException {
		ObservableList<TelTienda> resultList = FXCollections.observableArrayList();
		while (rs.next()) {
			TelTienda dto = new TelTienda();
			populateDto(dto, rs);
			resultList.add(dto);
		}
		return resultList;
	}

	private ObservableList<TelTienda> findByDynamicSelect(String sql, Object[] sqlParams) throws SQLException {
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
