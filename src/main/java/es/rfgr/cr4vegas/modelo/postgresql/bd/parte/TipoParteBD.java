package es.rfgr.cr4vegas.modelo.postgresql.bd.parte;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import es.rfgr.cr4vegas.modelo.postgresql.conexion.ConexionAlmacen;
import es.rfgr.cr4vegas.modelo.postgresql.dto.parte.TipoParte;
import es.rfgr.cr4vegas.modelo.postgresql.dto.parte.TipoPartePK;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class TipoParteBD {

	private Connection userConn;

	private final String tableName = "public.tipoparte";

	// SQL DML

	private final String SQL_SELECT = "SELECT * FROM " + getTableName();

	private final String SQL_INSERT = "INSERT INTO " + getTableName() + " VALUES (?, ?)";

	private final String SQL_UPDATE = "UPDATE " + getTableName() + " SET codigo=?, nombre=? WHERE codigo=?";

	private final String SQL_DELETE = "DELETE FROM " + getTableName() + " WHERE codigo=?";

	// COLUMNS

	private static final int COLUMN_CODIGO = 1;
	
	private static final int COLUMN_NOMBRE = 2;

	@SuppressWarnings("unused")
	private static final int PK_COLUMN_CODIGO = 1;

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

	public TipoParteBD() {

	}

	public void setConnection(Connection conn) {
		this.userConn = conn;
	}

	// FUNCTIONS

	public TipoPartePK insert(TipoParte dto) throws SQLException {
		try {
			initializeTimeConnSuppliedConn();

			ps = conn.prepareStatement(SQL_INSERT);

			int index = 1;
			ps.setInt(index++, dto.getCodigo());
			ps.setString(index, dto.getNombre());
			System.out.println("Ejecutando " + SQL_INSERT + " con DTO: " + dto);

			rows = ps.executeUpdate();

			finalizeTimeConnSuppliedConn();

			return dto.createPK();

		} finally {
			finallyTryCatch();
		}
	}

	public void update(TipoPartePK pk, TipoParte dto) throws SQLException {
		try {
			initializeTimeConnSuppliedConn();

			ps = conn.prepareStatement(SQL_UPDATE);

			index = 1;
			ps.setInt(index++, dto.getCodigo());
			ps.setString(index++, dto.getNombre());
			ps.setInt(index, pk.getCodigo());
			System.out.println("Ejecutando " + SQL_UPDATE + " with DTO: " + dto);

			rows = ps.executeUpdate();

			finalizeTimeConnSuppliedConn();

		} finally {
			finallyTryCatch();
		}
	}

	public void delete(TipoPartePK pk) throws SQLException {
		try {
			initializeTimeConnSuppliedConn();

			ps = conn.prepareStatement(SQL_DELETE);

			ps.setInt(1, pk.getCodigo());
			System.out.println("Ejecutando " + SQL_DELETE + " with DTO: " + pk);

			rows = ps.executeUpdate();

			finalizeTimeConnSuppliedConn();

		} finally {
			finallyTryCatch();
		}
	}

	public ObservableList<TipoParte> findAll() throws SQLException {
		return findByDynamicSelect(SQL_SELECT + " ORDER BY codigo", null);
	}

	public TipoParte findByPrimaryKey(TipoPartePK pk) throws SQLException {
		return findByPrimaryKey( pk.getCodigo() );
	}

	public TipoParte findByPrimaryKey(int codigo) throws SQLException {
		ObservableList<TipoParte> tiposPartes = findByDynamicSelect( SQL_SELECT + " WHERE codigo = ?", new Object[] {  codigo } );
		return tiposPartes.size() == 0 ? null : tiposPartes.get(0);
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

	private void populateDto(TipoParte dto, ResultSet rs) throws SQLException {
		dto.setCodigo(rs.getInt(COLUMN_CODIGO));
		dto.setNombre(rs.getString(COLUMN_NOMBRE));
	}

	private ObservableList<TipoParte> fetchMultiResults(ResultSet rs) throws SQLException {
		ObservableList<TipoParte> resultList = FXCollections.observableArrayList();
		while (rs.next()) {
			TipoParte dto = new TipoParte();
			populateDto(dto, rs);
			resultList.add(dto);
		}
		return resultList;
	}

	private ObservableList<TipoParte> findByDynamicSelect(String sql, Object[] sqlParams) throws SQLException {
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
