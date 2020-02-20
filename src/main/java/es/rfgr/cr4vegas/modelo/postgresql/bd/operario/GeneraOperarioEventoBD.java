package es.rfgr.cr4vegas.modelo.postgresql.bd.operario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import es.rfgr.cr4vegas.modelo.postgresql.conexion.ConexionAlmacen;
import es.rfgr.cr4vegas.modelo.postgresql.dto.operario.GeneraOperarioEvento;
import es.rfgr.cr4vegas.modelo.postgresql.dto.operario.GeneraOperarioEventoPK;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class GeneraOperarioEventoBD {

	private Connection userConn;

	private final String tableName = "public.generaoperarioevento";

	// SQL DML

	private final String SQL_SELECT = "SELECT * FROM " + getTableName();

	private final String SQL_INSERT = "INSERT INTO " + getTableName() + " VALUES (?, ?)";

	private final String SQL_UPDATE = "UPDATE " + getTableName() + " SET codOp=? WHERE codevento=?";

	private final String SQL_DELETE = "DELETE FROM " + getTableName() + " WHERE codevento=?";

	// COLUMNS

	private static final int COLUMN_CODEVENTO = 1;

	private static final int COLUMN_CODOP = 2;

	@SuppressWarnings("unused")
	private static final int PK_COLUMN_CODEVENTO = 1;

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

	public GeneraOperarioEventoBD() {

	}

	public void setConnection(Connection conn) {
		this.userConn = conn;
	}

	// FUNCTIONS

	public GeneraOperarioEventoPK insert(GeneraOperarioEvento dto) throws SQLException {
		try {
			initializeTimeConnSuppliedConn();

			ps = conn.prepareStatement(SQL_INSERT);

			index = 1;
			ps.setInt(index++, dto.getCodEvento());
			ps.setInt(index, dto.getCodOp());
			System.out.println("Ejecutando " + SQL_INSERT + " con DTO: " + dto);

			rows = ps.executeUpdate();

			finalizeTimeConnSuppliedConn();

			return dto.createPK();

		} finally {
			finallyTryCatch();
		}
	}

	public void update(GeneraOperarioEventoPK pk, GeneraOperarioEvento dto) throws SQLException {
		try {
			initializeTimeConnSuppliedConn();

			ps = conn.prepareStatement(SQL_UPDATE);

			index = 1;
			ps.setInt(index++, dto.getCodOp());
			ps.setInt(index, pk.getCodEvento());
			System.out.println("Ejecutando " + SQL_UPDATE + " with DTO: " + dto);

			rows = ps.executeUpdate();

			finalizeTimeConnSuppliedConn();

		} finally {
			finallyTryCatch();
		}
	}

	public void delete(GeneraOperarioEventoPK pk) throws SQLException {
		try {
			initializeTimeConnSuppliedConn();

			ps = conn.prepareStatement(SQL_DELETE);

			index = 1;
			ps.setInt(index, pk.getCodEvento());
			System.out.println("Ejecutando " + SQL_DELETE + " with DTO: " + pk);

			rows = ps.executeUpdate();

			finalizeTimeConnSuppliedConn();

		} finally {
			finallyTryCatch();
		}
	}

	public ObservableList<GeneraOperarioEvento> findAll() throws SQLException {
		return findByDynamicSelect(SQL_SELECT + " ORDER BY codevento", null);
	}
	
	public GeneraOperarioEvento findByPrimaryKey(GeneraOperarioEventoPK pk) throws SQLException {
		return findByPrimaryKey( pk.getCodEvento() );
	}

	public GeneraOperarioEvento findByPrimaryKey(int codEvento) throws SQLException {
		ObservableList<GeneraOperarioEvento> operariosEventos = findByDynamicSelect( SQL_SELECT + " WHERE codevento = ?", new Object[] {  Integer.valueOf(codEvento) } );
		return operariosEventos.size() == 0 ? null : operariosEventos.get(0);
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

	private void populateDto(GeneraOperarioEvento dto, ResultSet rs) throws SQLException {
		dto.setCodOp(rs.getInt(COLUMN_CODEVENTO));
		dto.setCodEvento(rs.getInt(COLUMN_CODOP));
	}

	private ObservableList<GeneraOperarioEvento> fetchMultiResults(ResultSet rs) throws SQLException {
		ObservableList<GeneraOperarioEvento> resultList = FXCollections.observableArrayList();
		while (rs.next()) {
			GeneraOperarioEvento dto = new GeneraOperarioEvento();
			populateDto(dto, rs);
			resultList.add(dto);
		}
		return resultList;
	}

	private ObservableList<GeneraOperarioEvento> findByDynamicSelect(String sql, Object[] sqlParams)
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
