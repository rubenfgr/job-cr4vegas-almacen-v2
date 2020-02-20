package es.rfgr.cr4vegas.modelo.postgresql.bd.operario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import es.rfgr.cr4vegas.modelo.postgresql.conexion.ConexionAlmacen;
import es.rfgr.cr4vegas.modelo.postgresql.dto.operario.DirOperario;
import es.rfgr.cr4vegas.modelo.postgresql.dto.operario.DirOperarioPK;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DirOperarioBD {

	private Connection userConn;

	private final String tableName = "public.diroperario";

	// SQL DML

	private final String SQL_SELECT = "SELECT * FROM " + getTableName();

	private final String SQL_INSERT = "INSERT INTO " + getTableName() + " VALUES (?, ?, ?, ?, ?, ?, ?)";

	private final String SQL_UPDATE = "UPDATE " + getTableName() + " SET localidad=?, cp=?, calle=?, "
			+ "numero=?, planta=?, numplanta=? WHERE codop=? ";

	private final String SQL_DELETE = "DELETE FROM " + getTableName() + " WHERE codop=?";

	// COLUMNS

	private static final int COLUMN_CODOP = 1;

	private static final int COLUMN_LOCALIDAD = 2;

	private static final int COLUMN_CP = 3;

	private static final int COLUMN_CALLE = 4;

	private static final int COLUMN_NUMERO = 5;

	private static final int COLUMN_PLANTA = 6;

	private static final int COLUMN_NUMPLANTA = 7;

	@SuppressWarnings("unused")
	private static final int PK_COLUMN_CODOP = 1;

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

	public DirOperarioBD() {

	}

	public void setConnection(Connection conn) {
		this.userConn = conn;
	}

	// FUNCTIONS

	public DirOperarioPK insert(DirOperario dto) throws SQLException {
		try {
			initializeTimeConnSuppliedConn();

			ps = conn.prepareStatement(SQL_INSERT);

			index = 1;
			ps.setInt(index++, dto.getCodOp());
			ps.setString(index++, dto.getLocalidad());
			ps.setString(index++, dto.getCp());
			ps.setString(index++, dto.getCalle());
			ps.setInt(index++, dto.getNumero());
			ps.setInt(index++, dto.getPlanta());
			ps.setString(index, dto.getNumPlanta());
			System.out.println("Ejecutando " + SQL_INSERT + " con DTO: " + dto);

			rows = ps.executeUpdate();

			finalizeTimeConnSuppliedConn();

			return dto.createPK();

		} finally {
			finallyTryCatch();
		}
	}

	public void update(DirOperarioPK pk, DirOperario dto) throws SQLException {
		try {
			initializeTimeConnSuppliedConn();

			ps = conn.prepareStatement(SQL_UPDATE);

			index = 1;
			ps.setString(index++, dto.getLocalidad());
			ps.setString(index++, dto.getCp());
			ps.setString(index++, dto.getCalle());
			ps.setInt(index++, dto.getNumero());
			ps.setInt(index++, dto.getPlanta());
			ps.setString(index++, dto.getNumPlanta());
			ps.setInt(index, pk.getCodOp());
			System.out.println("Ejecutando " + SQL_UPDATE + " with DTO: " + dto);

			rows = ps.executeUpdate();

			finalizeTimeConnSuppliedConn();

		} finally {
			finallyTryCatch();
		}
	}

	public void delete(DirOperarioPK pk) throws SQLException {
		try {
			initializeTimeConnSuppliedConn();

			ps = conn.prepareStatement(SQL_DELETE);

			ps.setInt(1, pk.getCodOp());
			System.out.println("Ejecutando " + SQL_DELETE + " with DTO: " + pk);

			rows = ps.executeUpdate();

			finalizeTimeConnSuppliedConn();

		} finally {
			finallyTryCatch();
		}
	}

	public ObservableList<DirOperario> findAll() throws SQLException {
		return findByDynamicSelect(SQL_SELECT + " ORDER BY codop", null);
	}
	
	public DirOperario findByPrimaryKey(DirOperarioPK pk) throws SQLException {
		return findByPrimaryKey( pk.getCodOp() );
	}

	public DirOperario findByPrimaryKey(int codop) throws SQLException {
		ObservableList<DirOperario> dirsOperarios = findByDynamicSelect( SQL_SELECT + " WHERE codop = ?", new Object[] {  Integer.valueOf(codop) } );
		return dirsOperarios.size() == 0 ? null : dirsOperarios.get(0);
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

	private void populateDto(DirOperario dto, ResultSet rs) throws SQLException {
		dto.setCodOp(rs.getInt(COLUMN_CODOP));
		dto.setLocalidad(rs.getString(COLUMN_LOCALIDAD));
		dto.setCp(rs.getString(COLUMN_CP));
		dto.setCalle(rs.getString(COLUMN_CALLE));
		dto.setNumero(rs.getInt(COLUMN_NUMERO));
		dto.setPlanta(rs.getInt(COLUMN_PLANTA));
		dto.setNumPlanta(rs.getString(COLUMN_NUMPLANTA));
	}

	private ObservableList<DirOperario> fetchMultiResults(ResultSet rs) throws SQLException {
		ObservableList<DirOperario> resultList = FXCollections.observableArrayList();
		while (rs.next()) {
			DirOperario dto = new DirOperario();
			populateDto(dto, rs);
			resultList.add(dto);
		}
		return resultList;
	}

	private ObservableList<DirOperario> findByDynamicSelect(String sql, Object[] sqlParams) throws SQLException {
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
