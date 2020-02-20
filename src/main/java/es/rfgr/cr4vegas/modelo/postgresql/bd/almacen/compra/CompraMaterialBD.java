package es.rfgr.cr4vegas.modelo.postgresql.bd.almacen.compra;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import es.rfgr.cr4vegas.modelo.postgresql.conexion.ConexionAlmacen;
import es.rfgr.cr4vegas.modelo.postgresql.dto.almacen.compra.CompraMaterial;
import es.rfgr.cr4vegas.modelo.postgresql.dto.almacen.compra.CompraMaterialPK;
import es.rfgr.cr4vegas.modelo.postgresql.exceptions.DAOException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class CompraMaterialBD {

	private Connection userConn;

	private final String tableName = "public.compramaterial";

	private int maxRows;

	// SQL DML

	private final String SQL_SELECT = "SELECT * FROM " + getTableName();

	private final String SQL_INSERT = "INSERT INTO " + getTableName()
			+ "(codcompra, codigo4v, cantidad, precio) VALUES (?, ?, ?, ?)";

	private final String SQL_UPDATE = "UPDATE " + getTableName() + " SET cantidad=?, "
			+ "precio=? WHERE codcompra=? AND codigo4v=?";

	private final String SQL_DELETE = "DELETE FROM " + getTableName() + " WHERE codcompra=? AND codigo4v=?";

	// COLUMNS

	private static final int COLUMN_CODCOMPRA = 1;

	private static final int COLUMN_CODIGO4V = 2;

	private static final int COLUMN_CANTIDAD = 3;

	private static final int COLUMN_PRECIO = 4;

	private static final int PK_COLUMN_CODCOMPRA = 1;

	private static final int PK_COLUMN_CODIGO4V = 2;

	private PreparedStatement ps;

	private Connection conn;

	private ResultSet rs;

	private long t1, t2;

	private int rows;

	private boolean isConnSupplied;

	private int index;

	// BUILDERS

	public CompraMaterialBD() {

	}

	public void setConnection(Connection conn) {
		this.userConn = conn;
	}

	// FUNCTIONS

	public CompraMaterialPK insert(CompraMaterial dto) throws SQLException {
		try {
			initializeTimeConnSuppliedConn();

			ps = conn.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS);

			index = 1;
			ps.setInt(index++, dto.getCodCompra());
			ps.setInt(index++, dto.getCodigo4v());
			ps.setInt(index++, dto.getCantidad());
			ps.setBigDecimal(index, dto.getPrecio());
			System.out.println("Ejecutando " + SQL_INSERT + " con DTO: " + dto);

			rows = ps.executeUpdate();

			finalizeTimeConnSuppliedConn();

			// Obtener valores de las columnas auto-incrementables
			rs = ps.getGeneratedKeys();
			if (rs != null && rs.next()) {
				dto.setCodCompra(rs.getInt(PK_COLUMN_CODCOMPRA));
				dto.setCodigo4v(rs.getInt(PK_COLUMN_CODIGO4V));
			}

			return dto.createPK();

		} finally {
			finallyTryCatch();
		}
	}

	public void update(CompraMaterialPK pk, CompraMaterial dto) throws SQLException {
		try {
			initializeTimeConnSuppliedConn();

			ps = conn.prepareStatement(SQL_UPDATE);

			index = 1;
			ps.setInt(index++, dto.getCantidad());
			ps.setBigDecimal(index++, dto.getPrecio());
			ps.setInt(index++, pk.getCodCompra());
			ps.setInt(index, pk.getCodigo4v());
			System.out.println("Ejecutando " + SQL_UPDATE + " with DTO: " + dto);

			rows = ps.executeUpdate();

			finalizeTimeConnSuppliedConn();

		} finally {
			finallyTryCatch();
		}
	}

	public void delete(CompraMaterialPK pk) throws SQLException {
		try {
			initializeTimeConnSuppliedConn();

			ps = conn.prepareStatement(SQL_DELETE);

			index = 1;
			ps.setInt(index++, pk.getCodCompra());
			ps.setInt(index, pk.getCodigo4v());
			System.out.println("Ejecutando " + SQL_DELETE + " with DTO: " + pk);

			rows = ps.executeUpdate();

			finalizeTimeConnSuppliedConn();

		} finally {
			finallyTryCatch();
		}
	}

	public ObservableList<CompraMaterial> findAll() throws SQLException, DAOException {
		return findByDynamicSelect(SQL_SELECT + " ORDER BY codcompra", null);
	}

	public void updateDTOCompraMaterial(CompraMaterial compraMaterial) throws SQLException {
		CompraMaterial compraActualizada = findByPrimaryKey(compraMaterial.createPK());
		if (compraActualizada != null) {
			compraMaterial.copiarValores(compraActualizada);
		}
	}

	public CompraMaterial findByPrimaryKey(CompraMaterialPK pk) throws SQLException {
		return findByPrimaryKey(pk.getCodCompra(), pk.getCodigo4v());
	}

	public CompraMaterial findByPrimaryKey(int codCompra, int codigo4v) throws SQLException {
		ObservableList<CompraMaterial> compraMateriales = findByDynamicSelect(
				SQL_SELECT + " WHERE codcompra = ? AND codigo4v = ?",
				new Object[] { Integer.valueOf(codCompra), Integer.valueOf(codigo4v) });
		return compraMateriales.size() == 0 ? null : compraMateriales.get(0);
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

	private void populateDto(CompraMaterial dto, ResultSet rs) throws SQLException {
		dto.setCodCompra(rs.getInt(COLUMN_CODCOMPRA));
		dto.setCodigo4v(rs.getInt(COLUMN_CODIGO4V));
		dto.setCantidadInicial(rs.getInt(COLUMN_CANTIDAD));
		dto.setPrecio(rs.getBigDecimal(COLUMN_PRECIO));
	}

	private ObservableList<CompraMaterial> fetchMultiResults(ResultSet rs) throws SQLException {
		ObservableList<CompraMaterial> resultList = FXCollections.observableArrayList();
		while (rs.next()) {
			CompraMaterial dto = new CompraMaterial();
			populateDto(dto, rs);
			resultList.add(dto);
		}
		return resultList;
	}

	private ObservableList<CompraMaterial> findByDynamicSelect(String sql, Object[] sqlParams) throws SQLException {
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
