package es.rfgr.cr4vegas.modelo.postgresql.bd.almacen.compra;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import es.rfgr.cr4vegas.modelo.postgresql.conexion.ConexionAlmacen;
import es.rfgr.cr4vegas.modelo.postgresql.dto.almacen.compra.Compra;
import es.rfgr.cr4vegas.modelo.postgresql.dto.almacen.compra.CompraPK;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class CompraBD {

	private Connection userConn;

	private final String tableName = "public.compra";

	// SQL DML

	private final String SQL_SELECT = "SELECT * FROM " + getTableName();

	private final String SQL_INSERT = "INSERT INTO " + getTableName() + "(codop, codtienda, factura, total, fecha)"
			+ " VALUES (?, ?, ?, ?, ?)";

	private final String SQL_UPDATE = "UPDATE " + getTableName() + " SET codop=?, codtienda=?, factura=?, "
			+ "total=? WHERE codcompra=?";

	private final String SQL_DELETE = "DELETE FROM " + getTableName() + " WHERE codcompra=?";

	// COLUMNS

	private static final int COLUMN_CODCOMPRA = 1;

	private static final int COLUMN_CODOP = 2;

	private static final int COLUMN_CODTIENDA = 3;

	private static final int COLUMN_FACTURA = 4;

	private static final int COLUMN_TOTAL = 5;

	private static final int COLUMN_FECHA = 6;

	private static final int PK_COLUMN_CODCOMPRA = 1;

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

	public CompraBD() {

	}

	public void setConnection(final Connection conn) {
		this.userConn = conn;
	}

	// FUNCTIONS

	public CompraPK insert(Compra dto) throws SQLException {
		try {
			initializeTimeConnSuppliedConn();

			ps = conn.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS);

			dto.setFecha(LocalDateTime.now());

			index = 1;
			ps.setInt(index++, dto.getCodOp());
			ps.setInt(index++, dto.getCodTienda());
			ps.setString(index++, dto.getFactura());
			ps.setBigDecimal(index++, dto.getTotal());
			ps.setTimestamp(index, Timestamp.valueOf(dto.getFecha()));
			System.out.println("Ejecutando " + SQL_INSERT + " con DTO: " + dto);

			rows = ps.executeUpdate();

			finalizeTimeConnSuppliedConn();

			// Obtener valores de las columnas auto-incrementables
			rs = ps.getGeneratedKeys();
			if (rs != null && rs.next()) {
				dto.setCodCompra(rs.getInt(PK_COLUMN_CODCOMPRA));
			}

			return dto.createPK();

		} finally {
			finallyTryCatch();
		}
	}

	public void update(CompraPK pk, Compra dto) throws SQLException {
		try {
			initializeTimeConnSuppliedConn();

			ps = conn.prepareStatement(SQL_UPDATE);

			index = 1;
			ps.setInt(index++, dto.getCodOp());
			ps.setInt(index++, dto.getCodTienda());
			ps.setString(index++, dto.getFactura());
			ps.setBigDecimal(index++, dto.getTotal());
			ps.setInt(index, pk.getCodCompra());
			System.out.println("Ejecutando " + SQL_UPDATE + " with DTO: " + dto);

			rows = ps.executeUpdate();

			finalizeTimeConnSuppliedConn();

		} finally {
			finallyTryCatch();
		}
	}

	public void delete(CompraPK pk) throws SQLException {
		try {
			initializeTimeConnSuppliedConn();

			ps = conn.prepareStatement(SQL_DELETE);

			ps.setInt(1, pk.getCodCompra());
			System.out.println("Ejecutando " + SQL_DELETE + " with DTO: " + pk);

			rows = ps.executeUpdate();

			finalizeTimeConnSuppliedConn();

		} finally {
			finallyTryCatch();
		}
	}

	public ObservableList<Compra> findAll() throws SQLException {
		return findByDynamicSelect(SQL_SELECT + " ORDER BY codcompra", null);
	}
	
	public void updateDTOCompra(Compra compra) throws SQLException {
		Compra compraActualizada = findByPrimaryKey(compra.createPK());
		if (compraActualizada != null) {
			compra.setTotal(compraActualizada.getTotal());
		}
	}
	
	public Compra findByPrimaryKey(CompraPK pk) throws SQLException {
		return findByPrimaryKey( pk.getCodCompra() );
	}

	public Compra findByPrimaryKey(int codCompra) throws SQLException {
		ObservableList<Compra> compras = findByDynamicSelect( SQL_SELECT + " WHERE codcompra = ?", new Object[] {  Integer.valueOf(codCompra) } );
		return compras.size() == 0 ? null : compras.get(0);
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

	private void populateDto(Compra dto, ResultSet rs) throws SQLException {
		dto.setCodCompra(rs.getInt(COLUMN_CODCOMPRA));
		dto.setCodOp(rs.getInt(COLUMN_CODOP));
		dto.setCodTienda(rs.getInt(COLUMN_CODTIENDA));
		dto.setFactura(rs.getString(COLUMN_FACTURA));
		dto.setTotal(rs.getBigDecimal(COLUMN_TOTAL));
		dto.setFecha(rs.getTimestamp(COLUMN_FECHA).toLocalDateTime());
	}

	private ObservableList<Compra> fetchMultiResults(ResultSet rs) throws SQLException {
		ObservableList<Compra> resultList = FXCollections.observableArrayList();
		while (rs.next()) {
			Compra dto = new Compra();
			populateDto(dto, rs);
			resultList.add(dto);
		}
		return resultList;
	}

	private ObservableList<Compra> findByDynamicSelect(String sql, Object[] sqlParams) throws SQLException {
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
