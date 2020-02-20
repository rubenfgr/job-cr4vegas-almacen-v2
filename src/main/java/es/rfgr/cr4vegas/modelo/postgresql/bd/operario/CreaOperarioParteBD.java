package es.rfgr.cr4vegas.modelo.postgresql.bd.operario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import es.rfgr.cr4vegas.modelo.postgresql.conexion.ConexionAlmacen;
import es.rfgr.cr4vegas.modelo.postgresql.dto.operario.CreaOperarioParte;
import es.rfgr.cr4vegas.modelo.postgresql.dto.operario.CreaOperarioPartePK;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class CreaOperarioParteBD {

	private Connection userConn;

	private final String tableName = "public.creaoperarioparte";

	// SQL DML

	private final String SQL_SELECT = "SELECT * FROM " + getTableName();

	private final String SQL_INSERT = "INSERT INTO " + getTableName() + " VALUES (?, ?)";

	private final String SQL_UPDATE = "UPDATE " + getTableName() + " SET codparte=? WHERE codop=? AND codparte=?";

	private final String SQL_DELETE = "DELETE FROM " + getTableName() + " WHERE codop=? AND codparte=?";

	// COLUMNS

	private static final int COLUMN_CODOP = 1;

	private static final int COLUMN_CODPARTE = 2;

	@SuppressWarnings("unused")
	private static final int PK_COLUMN_CODOP = 1;

	@SuppressWarnings("unused")
	private static final int PK_COLUMN_CODPARTE = 2;

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
	public CreaOperarioParteBD() {

	}

	public void setConnection(Connection conn) {
		this.userConn = conn;
	}

	// FUNCTIONS

	public CreaOperarioPartePK insert(CreaOperarioParte dto) throws SQLException {
		try {
			initializeTimeConnSuppliedConn();

			ps = conn.prepareStatement(SQL_INSERT);

			index = 1;
			ps.setInt(index++, dto.getCodOp());
			ps.setInt(index, dto.getCodParte());
			System.out.println("Ejecutando " + SQL_INSERT + " con DTO: " + dto);

			rows = ps.executeUpdate();

			finalizeTimeConnSuppliedConn();

			return dto.createPK();

		} finally {
			finallyTryCatch();
		}
	}

	public void update(CreaOperarioPartePK pk, CreaOperarioParte dto) throws SQLException {
		try {
			initializeTimeConnSuppliedConn();

			ps = conn.prepareStatement(SQL_UPDATE);

			index = 1;
			ps.setInt(index++, dto.getCodParte());
			ps.setInt(index++, pk.getCodOp());
			ps.setInt(index, pk.getCodParte());
			System.out.println("Ejecutando " + SQL_UPDATE + " with DTO: " + dto);

			rows = ps.executeUpdate();

			finalizeTimeConnSuppliedConn();

		} finally {
			finallyTryCatch();
		}
	}

	public void delete(CreaOperarioPartePK pk) throws SQLException {
		try {
			initializeTimeConnSuppliedConn();

			ps = conn.prepareStatement(SQL_DELETE);

			index = 1;
			ps.setInt(index++, pk.getCodOp());
			ps.setInt(index, pk.getCodParte());
			System.out.println("Ejecutando " + SQL_DELETE + " with DTO: " + pk);

			rows = ps.executeUpdate();

			finalizeTimeConnSuppliedConn();

		} finally {
			finallyTryCatch();
		}
	}

	public ObservableList<CreaOperarioParte> findAll() throws SQLException {
		return findByDynamicSelect(SQL_SELECT + " ORDER BY codop", null);
	}
	
	public CreaOperarioParte findByPrimaryKey(CreaOperarioPartePK pk) throws SQLException {
		return findByPrimaryKey( pk.getCodOp(), pk.getCodParte() );
	}

	public CreaOperarioParte findByPrimaryKey(int codOp, int codParte) throws SQLException {
		ObservableList<CreaOperarioParte> operariosPartes = findByDynamicSelect( SQL_SELECT + " WHERE codop = ? AND codparte = ?", new Object[] {  Integer.valueOf(codOp), Integer.valueOf(codParte) } );
		return operariosPartes.size() == 0 ? null : operariosPartes.get(0);
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

	private void populateDto(CreaOperarioParte dto, ResultSet rs) throws SQLException {
		dto.setCodOp(rs.getInt(COLUMN_CODOP));
		dto.setCodParte(rs.getInt(COLUMN_CODPARTE));
	}

	private ObservableList<CreaOperarioParte> fetchMultiResults(ResultSet rs) throws SQLException {
		ObservableList<CreaOperarioParte> resultList = FXCollections.observableArrayList();
		while (rs.next()) {
			CreaOperarioParte dto = new CreaOperarioParte();
			populateDto(dto, rs);
			resultList.add(dto);
		}
		return resultList;
	}

	private ObservableList<CreaOperarioParte> findByDynamicSelect(String sql, Object[] sqlParams) throws SQLException {
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
