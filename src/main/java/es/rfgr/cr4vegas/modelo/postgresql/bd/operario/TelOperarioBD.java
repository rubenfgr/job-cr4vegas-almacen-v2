package es.rfgr.cr4vegas.modelo.postgresql.bd.operario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import es.rfgr.cr4vegas.modelo.postgresql.conexion.ConexionAlmacen;
import es.rfgr.cr4vegas.modelo.postgresql.dto.operario.TelOperario;
import es.rfgr.cr4vegas.modelo.postgresql.dto.operario.TelOperarioPK;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class TelOperarioBD {

	private Connection userConn;

	private final String tableName = "public.teloperario";

	// SQL DML

	private final String SQL_SELECT = "SELECT * FROM " + getTableName();

	private final String SQL_INSERT = "INSERT INTO " + getTableName() + " VALUES (?, ?)";

	private final String SQL_UPDATE = "UPDATE " + getTableName() + " SET telefono=? WHERE telefono=?";

	private final String SQL_DELETE_ONE = "DELETE FROM " + getTableName() + " WHERE telefono=?";

	// COLUMNS

	private static final int COLUMN_CODOP = 2;

	private static final int COLUMN_TELEFONO = 1;

	@SuppressWarnings("unused")
	private static final int PK_COLUMN_CODOP = 2;

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

	public TelOperarioBD() {

	}

	public void setConnection(Connection conn) {
		this.userConn = conn;
	}

	// FUNCTIONS

	public TelOperarioPK insert(TelOperario dto) throws SQLException {
		try {
			initializeTimeConnSuppliedConn();

			ps = conn.prepareStatement(SQL_INSERT);

			index = 1;
			ps.setString(index++, dto.getTelefono());
			ps.setInt(index, dto.getCodOp());
			
			System.out.println("Ejecutando " + SQL_INSERT + " con DTO: " + dto);

			rows = ps.executeUpdate();

			finalizeTimeConnSuppliedConn();

			return dto.createPK();

		} finally {
			finallyTryCatch();
		}
	}

	public void update(TelOperarioPK pk, TelOperario dto) throws SQLException {
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

	public void delete(TelOperarioPK pk) throws SQLException {
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

	public ObservableList<TelOperario> findAll() throws SQLException {
		return findByDynamicSelect(SQL_SELECT + " ORDER BY codop", null);
	}
	
	public TelOperario findByPrimaryKey(TelOperarioPK pk) throws SQLException {
		return findByPrimaryKey( pk.getTelefono() );
	}

	public TelOperario findByPrimaryKey(String telefono) throws SQLException {
		ObservableList<TelOperario> telsOperarios = findByDynamicSelect( SQL_SELECT + " WHERE telefono = ?", new Object[] {  telefono } );
		return telsOperarios.size() == 0 ? null : telsOperarios.get(0);
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

	private void populateDto(TelOperario dto, ResultSet rs) throws SQLException {
		dto.setCodOp(rs.getInt(COLUMN_CODOP));
		dto.setTelefono(rs.getString(COLUMN_TELEFONO));
	}

	private ObservableList<TelOperario> fetchMultiResults(ResultSet rs) throws SQLException {
		ObservableList<TelOperario> resultList = FXCollections.observableArrayList();
		while (rs.next()) {
			TelOperario dto = new TelOperario();
			populateDto(dto, rs);
			resultList.add(dto);
		}
		return resultList;
	}

	private ObservableList<TelOperario> findByDynamicSelect(String sql, Object[] sqlParams) throws SQLException {
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
