package es.rfgr.cr4vegas.modelo.postgresql.bd.almacen.material;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import es.rfgr.cr4vegas.modelo.postgresql.conexion.ConexionAlmacen;
import es.rfgr.cr4vegas.modelo.postgresql.dto.almacen.material.Precio;
import es.rfgr.cr4vegas.modelo.postgresql.dto.almacen.material.PrecioPK;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class PrecioBD {

	private Connection userConn;

	private final String tableName = "public.precios";

	// SQL DML

	private final String SQL_SELECT = "SELECT * FROM " + getTableName();

	private final String SQL_INSERT = "INSERT INTO " + getTableName() + " VALUES (?, ?, ?, ?)";

	private final String SQL_UPDATE = "UPDATE " + getTableName()
			+ " SET precio = ?, preciopublico = ? WHERE codigo4v = ? AND " + "codtienda = ?";

	private final String SQL_DELETE = "DELETE FROM " + getTableName() + " WHERE codigo4v = ? AND codtienda = ?";

	// COLUMNS

	private static final int COLUMN_CODIGO4V = 1;

	private static final int COLUMN_CODTIENDA = 2;

	private static final int COLUMN_PRECIO = 3;

	private static final int COLUMN_PRECIO_PUBLICO = 4;

	@SuppressWarnings("unused")
	private static final int PK_COLUMN_CODIGO4V = 1;

	@SuppressWarnings("unused")
	private static final int PK_COLUMN_CODTIENDA = 2;

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

	public PrecioBD() {

	}

	public void setConnection(Connection conn) {
		this.userConn = conn;
	}

	// FUNCTIONS

	public PrecioPK insert(Precio dto) throws SQLException {
		try {
			initializeTimeConnSuppliedConn();

			ps = conn.prepareStatement(SQL_INSERT);

			int index = 1;
			ps.setInt(index++, dto.getCodigo4v());
			ps.setInt(index++, dto.getCodtienda());
			ps.setBigDecimal(index++, dto.getPrecio());
			ps.setBigDecimal(index, dto.getPrecioPublico());

			System.out.println("Ejecutando " + SQL_INSERT + " con DTO: " + dto);

			rows = ps.executeUpdate();

			finalizeTimeConnSuppliedConn();

			return dto.createPK();

		} finally {
			finallyTryCatch();
		}
	}

	public void update(PrecioPK pk, Precio dto) throws SQLException {
		try {
			initializeTimeConnSuppliedConn();

			ps = conn.prepareStatement(SQL_UPDATE);

			int index = 1;

			ps.setBigDecimal(index++, dto.getPrecio());
			ps.setBigDecimal(index++, dto.getPrecioPublico());
			ps.setInt(index++, dto.getCodigo4v());
			ps.setInt(index, dto.getCodtienda());

			System.out.println("Ejecutando " + SQL_UPDATE + " with DTO: " + pk);

			rows = ps.executeUpdate();

			finalizeTimeConnSuppliedConn();

		} finally {
			finallyTryCatch();
		}
	}

	public void delete(PrecioPK pk) throws SQLException {
		try {
			initializeTimeConnSuppliedConn();

			ps = conn.prepareStatement(SQL_DELETE);

			ps.setInt(index++, pk.getCodigo4v());
			ps.setInt(index, pk.getCodTienda());

			System.out.println("Ejecutando " + SQL_DELETE + " with DTO: " + pk);

			rows = ps.executeUpdate();

			finalizeTimeConnSuppliedConn();

		} finally {
			finallyTryCatch();
		}
	}

	public ObservableList<Precio> findAll() throws SQLException {
		return findByDynamicSelect(SQL_SELECT + " ORDER BY codigo4v, codtienda", null);
	}

	public Precio findByPrimaryKey(PrecioPK pk) throws SQLException {
		return findByPrimaryKey(pk.getCodigo4v(), pk.getCodTienda());
	}

	public Precio findByPrimaryKey(int codigo4v, int codtienda) throws SQLException {
		ObservableList<Precio> precios = findByDynamicSelect(SQL_SELECT + " WHERE nombre = ?",
				new Object[] { Integer.valueOf(codigo4v), Integer.valueOf(codtienda) });
		return precios.size() == 0 ? null : precios.get(0);
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

	private void populateDto(Precio dto, ResultSet rs) throws SQLException {
		dto.setCodigo4v(rs.getInt(COLUMN_CODIGO4V));
		dto.setCodtienda(rs.getInt(COLUMN_CODTIENDA));
		dto.setPrecio(rs.getBigDecimal(COLUMN_PRECIO));
		dto.setPrecioPublico(rs.getBigDecimal(COLUMN_PRECIO_PUBLICO));
	}

	private ObservableList<Precio> fetchMultiResults(ResultSet rs) throws SQLException {
		ObservableList<Precio> resultList = FXCollections.observableArrayList();
		while (rs.next()) {
			Precio dto = new Precio();
			populateDto(dto, rs);
			resultList.add(dto);
		}
		return resultList;
	}

	private ObservableList<Precio> findByDynamicSelect(String sql, Object[] sqlParams) throws SQLException {
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
