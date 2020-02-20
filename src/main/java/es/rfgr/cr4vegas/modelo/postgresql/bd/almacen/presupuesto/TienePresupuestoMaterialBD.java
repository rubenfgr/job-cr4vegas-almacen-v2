package es.rfgr.cr4vegas.modelo.postgresql.bd.almacen.presupuesto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import es.rfgr.cr4vegas.modelo.postgresql.conexion.ConexionAlmacen;
import es.rfgr.cr4vegas.modelo.postgresql.dto.almacen.presupuesto.TienePresupuestoMaterial;
import es.rfgr.cr4vegas.modelo.postgresql.dto.almacen.presupuesto.TienePresupuestoMaterialPK;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class TienePresupuestoMaterialBD {

	private Connection userConn;

	private final String tableName = "public.tienepresupuestomaterial";

	// SQL DML

	private final String SQL_SELECT = "SELECT * FROM " + getTableName();

	private final String SQL_INSERT = "INSERT INTO " + getTableName() + " VALUES (?, ?, ?, ?, ?)";

	private final String SQL_UPDATE = "UPDATE " + getTableName() + " SET cantidad=?"
			+ ", precio=?, total=? WHERE codpresupuesto=? AND codigo4v=?";

	private final String SQL_DELETE = "DELETE FROM " + getTableName() + " WHERE codpresupuesto=? AND " + "codigo4v=?";

	// COLUMNS

	private static final int COLUMN_CODPRESUPUESTO = 1;

	private static final int COLUMN_CODIGO4V = 2;

	private static final int COLUMN_CANTIDAD = 3;

	private static final int COLUMN_PRECIO = 4;
	
	private static final int COLUMN_TOTAL = 5;

	@SuppressWarnings("unused")
	private static final int PK_COLUMN_CODPRESUPUESTO = 1;

	@SuppressWarnings("unused")
	private static final int PK_COLUMN_CODIGO4V = 2;

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

	public TienePresupuestoMaterialBD() {

	}

	public void setConnection(Connection conn) {
		this.userConn = conn;
	}

	// FUNCTIONS

	public TienePresupuestoMaterialPK insert(TienePresupuestoMaterial dto) throws SQLException {
		try {
			initializeTimeConnSuppliedConn();

			ps = conn.prepareStatement(SQL_INSERT);

			index = 1;
			ps.setInt(index++, dto.getCodPresupuesto());
			ps.setInt(index++, dto.getCodigo4v());
			ps.setInt(index++, dto.getCantidad());
			ps.setBigDecimal(index++, dto.getPrecio());
			ps.setBigDecimal(index, dto.getTotal());
			System.out.println("Ejecutando " + SQL_INSERT + " con DTO: " + dto);

			rows = ps.executeUpdate();

			finalizeTimeConnSuppliedConn();

			return dto.createPK();

		} finally {
			finallyTryCatch();
		}
	}

	public void update(TienePresupuestoMaterialPK pk, TienePresupuestoMaterial dto) throws SQLException {
		try {
			initializeTimeConnSuppliedConn();

			ps = conn.prepareStatement(SQL_UPDATE);

			index = 1;
			ps.setInt(index++, dto.getCantidad());
			ps.setBigDecimal(index++, dto.getPrecio());
			ps.setBigDecimal(index++, dto.getTotal());
			ps.setInt(index++, dto.getCodPresupuesto());
			ps.setInt(index, pk.getCodigo4v());
			System.out.println("Ejecutando " + SQL_UPDATE + " with DTO: " + dto);

			rows = ps.executeUpdate();

			finalizeTimeConnSuppliedConn();

		} finally {
			finallyTryCatch();
		}
	}

	public void delete(TienePresupuestoMaterialPK pk) throws SQLException {
		try {
			initializeTimeConnSuppliedConn();

			ps = conn.prepareStatement(SQL_DELETE);

			index = 1;
			ps.setInt(index++, pk.getCodPresupuesto());
			ps.setInt(index, pk.getCodigo4v());
			System.out.println("Ejecutando " + SQL_DELETE + " with DTO: " + pk);

			rows = ps.executeUpdate();

			finalizeTimeConnSuppliedConn();

		} finally {
			finallyTryCatch();
		}
	}

	public ObservableList<TienePresupuestoMaterial> findAll() throws SQLException {
		return findByDynamicSelect(SQL_SELECT + " ORDER BY codpresupuesto, codigo4v", null);
	}

	public TienePresupuestoMaterial findByPrimaryKey(TienePresupuestoMaterialPK pk) throws SQLException {
		return findByPrimaryKey(pk.getCodPresupuesto(), pk.getCodigo4v());
	}

	public TienePresupuestoMaterial findByPrimaryKey(int codPresupuesto, int codigo4v) throws SQLException {
		ObservableList<TienePresupuestoMaterial> presupuestoMateriales = findByDynamicSelect(
				SQL_SELECT + " WHERE codpresupuesto = ? AND codigo4v = ?",
				new Object[] { Integer.valueOf(codPresupuesto), Integer.valueOf(codigo4v) });
		return presupuestoMateriales.size() == 0 ? null : presupuestoMateriales.get(0);
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

	private void populateDto(TienePresupuestoMaterial dto, ResultSet rs) throws SQLException {
		dto.setCodPresupuesto(rs.getInt(COLUMN_CODPRESUPUESTO));
		dto.setCodigo4v(rs.getInt(COLUMN_CODIGO4V));
		dto.setCantidad(rs.getInt(COLUMN_CANTIDAD));
		dto.setPrecio(rs.getBigDecimal(COLUMN_PRECIO));
		dto.setTotal(rs.getBigDecimal(COLUMN_TOTAL));
	}

	private ObservableList<TienePresupuestoMaterial> fetchMultiResults(ResultSet rs) throws SQLException {
		ObservableList<TienePresupuestoMaterial> resultList = FXCollections.observableArrayList();
		while (rs.next()) {
			TienePresupuestoMaterial dto = new TienePresupuestoMaterial();
			populateDto(dto, rs);
			resultList.add(dto);
		}
		return resultList;
	}

	private ObservableList<TienePresupuestoMaterial> findByDynamicSelect(String sql, Object[] sqlParams)
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
