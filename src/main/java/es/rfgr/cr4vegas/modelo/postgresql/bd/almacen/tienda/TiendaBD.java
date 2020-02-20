package es.rfgr.cr4vegas.modelo.postgresql.bd.almacen.tienda;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import es.rfgr.cr4vegas.modelo.postgresql.conexion.ConexionAlmacen;
import es.rfgr.cr4vegas.modelo.postgresql.dto.almacen.tienda.Tienda;
import es.rfgr.cr4vegas.modelo.postgresql.dto.almacen.tienda.TiendaPK;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class TiendaBD {

	private Connection userConn;

	private final String tableName = "public.tienda";

	// SQL DML

	private final String SQL_SELECT = "SELECT * FROM " + getTableName();

	private final String SQL_INSERT = "INSERT INTO " + getTableName() + " (activo, nombre, nif) VALUES (?, ?, ?)";

	private final String SQL_UPDATE = "UPDATE " + getTableName() + " SET activo=?, nombre=?, nif=? "
			+ "WHERE codtienda=?";

	private final String SQL_DELETE = "DELETE FROM " + getTableName() + " WHERE codtienda=?";

	// COLUMNS

	private static final int COLUMN_CODTIENDA = 1;

	private static final int COLUMN_ACTIVO = 2;

	private static final int COLUMN_NOMBRE = 3;

	private static final int COLUMN_NIF = 4;

	private static final int PK_COLUMN_CODTIENDA = 1;

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

	public TiendaBD() {

	}

	public void setConnection(Connection conn) {
		this.userConn = conn;
	}

	// FUNCTIONS

	public TiendaPK insert(Tienda dto) throws SQLException {
		try {
			initializeTimeConnSuppliedConn();

			ps = conn.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS);

			index = 1;
			ps.setBoolean(index++, dto.isActivo());
			ps.setString(index++, dto.getNombre());
			ps.setString(index, dto.getNif());
			System.out.println("Ejecutando " + SQL_INSERT + " con DTO: " + dto);

			rows = ps.executeUpdate();

			finalizeTimeConnSuppliedConn();

			// Obtener valores de las columnas-incrementables
			rs = ps.getGeneratedKeys();
			if (rs != null && rs.next()) {
				dto.setCodTienda(rs.getInt(PK_COLUMN_CODTIENDA));
			}

			return dto.createPK();

		} finally {
			finallyTryCatch();
		}
	}

	public void update(TiendaPK pk, Tienda dto) throws SQLException {
		try {
			initializeTimeConnSuppliedConn();

			ps = conn.prepareStatement(SQL_UPDATE);

			index = 1;
			ps.setBoolean(index++, dto.isActivo());
			ps.setString(index++, dto.getNombre());
			ps.setString(index++, dto.getNif());
			ps.setInt(index, pk.getCodTienda());
			System.out.println("Ejecutando " + SQL_UPDATE + " with DTO: " + dto);

			rows = ps.executeUpdate();

			finalizeTimeConnSuppliedConn();

		} finally {
			finallyTryCatch();
		}
	}

	public void delete(TiendaPK pk) throws SQLException {
		try {
			initializeTimeConnSuppliedConn();

			ps = conn.prepareStatement(SQL_DELETE);

			ps.setInt(1, pk.getCodTienda());
			System.out.println("Ejecutando " + SQL_DELETE + " with DTO: " + pk);

			rows = ps.executeUpdate();

			finalizeTimeConnSuppliedConn();

		} finally {
			finallyTryCatch();
		}
	}

	public ObservableList<Tienda> findAll() throws SQLException {
		return findByDynamicSelect(SQL_SELECT + " ORDER BY codtienda", null);
	}

	public Tienda findByPrimaryKey(TiendaPK pk) throws SQLException {
		return findByPrimaryKey(pk.getCodTienda());
	}

	public Tienda findByPrimaryKey(int codTienda) throws SQLException {
		ObservableList<Tienda> tiendas = findByDynamicSelect(SQL_SELECT + " WHERE codtienda = ?",
				new Object[] { Integer.valueOf(codTienda) });
		return tiendas.size() == 0 ? null : tiendas.get(0);
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

	private void populateDto(Tienda dto, ResultSet rs) throws SQLException {
		dto.setCodTienda(rs.getInt(COLUMN_CODTIENDA));
		dto.setActivo(rs.getBoolean(COLUMN_ACTIVO));
		dto.setNombre(rs.getString(COLUMN_NOMBRE));
		dto.setNif(rs.getString(COLUMN_NIF));
	}

	private ObservableList<Tienda> fetchMultiResults(ResultSet rs) throws SQLException {
		ObservableList<Tienda> resultList = FXCollections.observableArrayList();
		while (rs.next()) {
			Tienda dto = new Tienda();
			populateDto(dto, rs);
			resultList.add(dto);
		}
		return resultList;
	}

	private ObservableList<Tienda> findByDynamicSelect(String sql, Object[] sqlParams) throws SQLException {
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
