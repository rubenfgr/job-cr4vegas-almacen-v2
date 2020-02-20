package es.rfgr.cr4vegas.modelo.postgresql.bd.parte;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import es.rfgr.cr4vegas.modelo.postgresql.conexion.ConexionAlmacen;
import es.rfgr.cr4vegas.modelo.postgresql.dto.parte.AsociaParteOperario;
import es.rfgr.cr4vegas.modelo.postgresql.dto.parte.AsociaParteOperarioPK;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class AsociaParteOperarioBD {

	private Connection userConn;

	private final String tableName = "public.asociaparteoperario";

	// SQL DML
	
	private final String SQL_SELECT = "SELECT * FROM " + getTableName();

	private final String SQL_INSERT = "INSERT INTO " + getTableName() + " VALUES (?, ?, ?)";

	private final String SQL_UPDATE = "UPDATE " + getTableName() + " SET codop=? WHERE codparte=? AND codop=?";

	private final String SQL_DELETE = "DELETE FROM " + getTableName() + " WHERE codparte=? AND codop=?";

	// COLUMNS

	private static final int COLUMN_CODPARTE = 1;

	private static final int COLUMN_CODOP = 2;

	private static final int COLUMN_FECHA = 3;

	@SuppressWarnings("unused")
	private static final int PK_COLUMN_CODPARTE = 1;

	@SuppressWarnings("unused")
	private static final int PK_COLUMN_CODOP = 2;

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
	
	public AsociaParteOperarioBD() {
		
	}
	
	public void setConnection(Connection conn) {
		this.userConn = conn;
	}
	
	// FUNCTIONS
	
	public AsociaParteOperarioPK insert(AsociaParteOperario dto) throws SQLException {
		try {
			initializeTimeConnSuppliedConn();
			
			ps = conn.prepareStatement(SQL_INSERT);
			
			dto.setFecha(LocalDateTime.now());
			
			index = 1;
			ps.setInt(index++, dto.getCodParte());
			ps.setInt(index++, dto.getCodOp());
			ps.setTimestamp(index, Timestamp.valueOf(dto.getFecha()));
			System.out.println("Ejecutando " + SQL_INSERT + " con DTO: " + dto);
			
			rows = ps.executeUpdate();

			finalizeTimeConnSuppliedConn();

			return dto.createPK();

		} finally {
			finallyTryCatch();
		}
	}

	public void update(AsociaParteOperarioPK pk, AsociaParteOperario dto) throws SQLException {
		try {
			initializeTimeConnSuppliedConn();

			ps = conn.prepareStatement(SQL_UPDATE);
			
			index = 1;
			ps.setInt(index++, dto.getCodOp());
			ps.setInt(index++, pk.getCodParte());
			ps.setInt(index, pk.getCodOp());
			System.out.println("Ejecutando " + SQL_UPDATE + " with DTO: " + dto);

			rows = ps.executeUpdate();

			finalizeTimeConnSuppliedConn();

		} finally {
			finallyTryCatch();
		}
	}

	public void delete(AsociaParteOperarioPK pk) throws SQLException {
		try {
			initializeTimeConnSuppliedConn();

			ps = conn.prepareStatement(SQL_DELETE);
			
			index = 1;
			ps.setInt(index++, pk.getCodParte());
			ps.setInt(index, pk.getCodOp());
			System.out.println("Ejecutando " + SQL_DELETE + " with DTO: " + pk);

			rows = ps.executeUpdate();

			finalizeTimeConnSuppliedConn();

		} finally {
			finallyTryCatch();
		}
	}

	public ObservableList<AsociaParteOperario> findAll() throws SQLException {
		return findByDynamicSelect(SQL_SELECT + " ORDER BY codparte, codop", null);
	}
	
	public AsociaParteOperario findByPrimaryKey(AsociaParteOperarioPK pk) throws SQLException {
		return findByPrimaryKey( pk.getCodParte(), pk.getCodOp() );
	}

	public AsociaParteOperario findByPrimaryKey(int codParte, int codOp) throws SQLException {
		ObservableList<AsociaParteOperario> partesOperarios = findByDynamicSelect( SQL_SELECT + " WHERE codparte = ? AND codop = ?", new Object[] {  Integer.valueOf(codParte), Integer.valueOf(codOp) } );
		return partesOperarios.size() == 0 ? null : partesOperarios.get(0);
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

	private void populateDto(AsociaParteOperario dto, ResultSet rs) throws SQLException {
		dto.setCodParte(rs.getInt(COLUMN_CODPARTE));
		dto.setCodOp(rs.getInt(COLUMN_CODOP));
		dto.setFecha(rs.getTimestamp(COLUMN_FECHA).toLocalDateTime());
	}
	
	private ObservableList<AsociaParteOperario> fetchMultiResults(ResultSet rs) throws SQLException {
		ObservableList<AsociaParteOperario> resultList = FXCollections.observableArrayList();
		while (rs.next()) {
			AsociaParteOperario dto = new AsociaParteOperario();
			populateDto(dto, rs);
			resultList.add(dto);
		}
		return resultList;
	}

	private ObservableList<AsociaParteOperario> findByDynamicSelect(String sql, Object[] sqlParams) throws SQLException {
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
