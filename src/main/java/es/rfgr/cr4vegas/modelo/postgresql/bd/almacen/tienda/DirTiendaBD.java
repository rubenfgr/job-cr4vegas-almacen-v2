package es.rfgr.cr4vegas.modelo.postgresql.bd.almacen.tienda;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import es.rfgr.cr4vegas.modelo.postgresql.conexion.ConexionAlmacen;
import es.rfgr.cr4vegas.modelo.postgresql.dto.almacen.tienda.DirTienda;
import es.rfgr.cr4vegas.modelo.postgresql.dto.almacen.tienda.DirTiendaPK;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DirTiendaBD {

	private Connection userConn;

	private final String tableName = "public.dirtienda";

	private int maxRows;

	// SQL DML

	private final String SQL_SELECT = "SELECT * FROM " + getTableName();

	private final String SQL_INSERT = "INSERT INTO " + getTableName() + " VALUES (?, ?, ?, ?, ?)";

	private final String SQL_UPDATE = "UPDATE " + getTableName() + " SET localidad=?, cp=?, calle=?, "
			+ "numero=? WHERE codtienda=? ";

	private final String SQL_DELETE = "DELETE FROM " + getTableName() + " WHERE codtienda=?";

	// COLUMNS

	private static final int COLUMN_CODTIENDA = 1;

	private static final int COLUMN_LOCALIDAD = 2;

	private static final int COLUMN_CP = 3;

	private static final int COLUMN_CALLE = 4;

	private static final int COLUMN_NUMERO = 5;

	private static final int PK_COLUMN_CODTIENDA = 1;

	private PreparedStatement ps;

	private Connection conn;

	private ResultSet rs;

	private long t1, t2;

	private int rows;

	private boolean isConnSupplied;

	private int index;

	// BUILDERS

	public DirTiendaBD() {

	}

	public void setConnection(Connection conn) {
		this.userConn = conn;
	}

	// FUNCTIONS

	public DirTiendaPK insert(DirTienda dto) throws SQLException {
		try {
			initializeTimeConnSuppliedConn();

			ps = conn.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS);

			index = 1;
			ps.setInt(index++, dto.getCodTienda());
			ps.setString(index++, dto.getLocalidad());
			ps.setString(index++, dto.getCp());
			ps.setString(index++, dto.getCalle());
			ps.setInt(index, dto.getNumero());
			System.out.println("Ejecutando " + SQL_INSERT + " con DTO: " + dto);

			rows = ps.executeUpdate();

			finalizeTimeConnSuppliedConn();

			// Obtener valores de las columnas auto-incrementables
			rs = ps.getGeneratedKeys();
			if (rs != null && rs.next()) {
				dto.setCodTienda(rs.getInt(PK_COLUMN_CODTIENDA));
			}

			return dto.createPK();

		} finally {
			finallyTryCatch();
		}
	}

	public void update(DirTiendaPK pk, DirTienda dto) throws SQLException {
		try {
			initializeTimeConnSuppliedConn();

			ps = conn.prepareStatement(SQL_UPDATE);

			index = 1;
			ps.setString(index++, dto.getLocalidad());
			ps.setString(index++, dto.getCp());
			ps.setString(index++, dto.getCalle());
			ps.setInt(index++, dto.getNumero());
			ps.setInt(index, pk.getCodTienda());
			System.out.println("Ejecutando " + SQL_UPDATE + " with DTO: " + dto);

			rows = ps.executeUpdate();

			finalizeTimeConnSuppliedConn();

		} finally {
			finallyTryCatch();
		}
	}

	public void delete(DirTiendaPK pk) throws SQLException {
		try {
			t1 = System.currentTimeMillis();

			isConnSupplied = (userConn != null && !userConn.isClosed());

			conn = isConnSupplied ? userConn : ConexionAlmacen.getConexion();

			ps = conn.prepareStatement(SQL_DELETE);

			ps.setInt(1, pk.getCodTienda());
			System.out.println("Ejecutando " + SQL_DELETE + " with DTO: " + pk);

			rows = ps.executeUpdate();
			t2 = System.currentTimeMillis();
			System.out.println(rows + " filas afectadas (" + (t2 - t1) + " ms)");

		} finally {
			finallyTryCatch();
		}
	}

	public ObservableList<DirTienda> findAll() throws SQLException {
		return findByDynamicSelect(SQL_SELECT + " ORDER BY codtienda", null);
	}

	public DirTienda findByPrimaryKey(DirTiendaPK pk) throws SQLException {
		return findByPrimaryKey(pk.getCodTienda());
	}

	public DirTienda findByPrimaryKey(int codTienda) throws SQLException {
		ObservableList<DirTienda> dirTienda = findByDynamicSelect(SQL_SELECT + " WHERE codtienda = ?",
				new Object[] { Integer.valueOf(codTienda) });
		return dirTienda.size() == 0 ? null : dirTienda.get(0);
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

	private void populateDto(DirTienda dto, ResultSet rs) throws SQLException {
		dto.setCodTienda(rs.getInt(COLUMN_CODTIENDA));
		dto.setLocalidad(rs.getString(COLUMN_LOCALIDAD));
		dto.setCp(rs.getString(COLUMN_CP));
		dto.setCalle(rs.getString(COLUMN_CALLE));
		dto.setNumero(rs.getInt(COLUMN_NUMERO));
	}

	private ObservableList<DirTienda> fetchMultiResults(ResultSet rs) throws SQLException {
		ObservableList<DirTienda> resultList = FXCollections.observableArrayList();
		while (rs.next()) {
			DirTienda dto = new DirTienda();
			populateDto(dto, rs);
			resultList.add(dto);
		}
		return resultList;
	}

	private ObservableList<DirTienda> findByDynamicSelect(String sql, Object[] sqlParams) throws SQLException {
		try {
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

		} finally {
			finallyTryCatch();
		}
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
