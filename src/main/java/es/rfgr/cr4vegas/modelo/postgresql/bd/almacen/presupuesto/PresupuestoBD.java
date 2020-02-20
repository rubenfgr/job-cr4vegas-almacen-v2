package es.rfgr.cr4vegas.modelo.postgresql.bd.almacen.presupuesto;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import es.rfgr.cr4vegas.modelo.postgresql.conexion.ConexionAlmacen;
import es.rfgr.cr4vegas.modelo.postgresql.dto.almacen.presupuesto.Presupuesto;
import es.rfgr.cr4vegas.modelo.postgresql.dto.almacen.presupuesto.PresupuestoPK;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class PresupuestoBD {

	private Connection userConn;

	private final String tableName = "public.presupuesto";

	// SQL DML

	private final String SQL_SELECT = "SELECT * FROM " + getTableName();

	private final String SQL_INSERT = "INSERT INTO " + getTableName() + "(cliente, telefono, total, "
			+ "validez, fecha) VALUES (?, ?, ?, ?, ?)";

	private final String SQL_UPDATE = "UPDATE " + getTableName() + " SET cliente=?, telefono=?, total=?, "
			+ "validez=? WHERE codpresupuesto=?";

	private final String SQL_DELETE = "DELETE FROM " + getTableName() + " WHERE codpresupuesto=?";

	// COLUMNS

	private static final int COLUMN_CODPRESUPUESTO = 1;

	private static final int COLUMN_CLIENTE = 2;

	private static final int COLUMN_TELEFONO = 3;

	private static final int COLUMN_TOTAL = 4;

	private static final int COLUMN_VALIDEZ = 5;

	private static final int COLUMN_FECHA = 6;

	private static final int PK_COLUMN_CODPRESUPUESTO = 1;

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

	public PresupuestoBD() {

	}

	public void setConnection(Connection conn) {
		this.userConn = conn;
	}

	// FUNCTIONS

	public PresupuestoPK insert(Presupuesto dto) throws SQLException {
		try {
			initializeTimeConnSuppliedConn();

			ps = conn.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS);

			index = 1;
			ps.setString(index++, dto.getCliente());
			ps.setString(index++, dto.getTelefono());
			ps.setBigDecimal(index++, dto.getTotal());
			ps.setDate(index++, Date.valueOf(dto.getFecha()));
			ps.setDate(index, Date.valueOf(dto.getValidez()));
			System.out.println("Ejecutando " + SQL_INSERT + " con DTO: " + dto);

			rows = ps.executeUpdate();

			finalizeTimeConnSuppliedConn();

			// Obtener valores de las columnas auto-incrementables
			rs = ps.getGeneratedKeys();
			if (rs != null && rs.next()) {
				dto.setCodPresupuesto(rs.getInt(PK_COLUMN_CODPRESUPUESTO));
			}

			return dto.createPK();

		} finally {
			finallyTryCatch();
		}
	}

	public void update(PresupuestoPK pk, Presupuesto dto) throws SQLException {
		try {
			initializeTimeConnSuppliedConn();

			ps = conn.prepareStatement(SQL_UPDATE);

			index = 1;
			ps.setString(index++, dto.getCliente());
			ps.setString(index++, dto.getTelefono());
			ps.setBigDecimal(index++, dto.getTotal());
			ps.setDate(index++, Date.valueOf(dto.getValidez()));
			ps.setInt(index, pk.getCodPresupuesto());
			System.out.println("Ejecutando " + SQL_UPDATE + " with DTO: " + dto);

			rows = ps.executeUpdate();

			finalizeTimeConnSuppliedConn();

		} finally {
			finallyTryCatch();
		}
	}

	public void delete(PresupuestoPK pk) throws SQLException {
		try {
			initializeTimeConnSuppliedConn();

			ps = conn.prepareStatement(SQL_DELETE);

			ps.setInt(1, pk.getCodPresupuesto());
			System.out.println("Ejecutando " + SQL_DELETE + " with DTO: " + pk);

			rows = ps.executeUpdate();

			finalizeTimeConnSuppliedConn();

		} finally {
			finallyTryCatch();
		}
	}

	public ObservableList<Presupuesto> findAll() throws SQLException {
		return findByDynamicSelect(SQL_SELECT + " ORDER BY codpresupuesto", null);
	}

	public Presupuesto findByPrimaryKey(PresupuestoPK pk) throws SQLException {
		return findByPrimaryKey(pk.getCodPresupuesto());
	}

	public Presupuesto findByPrimaryKey(int codPresupuesto) throws SQLException {
		ObservableList<Presupuesto> presupuestos = findByDynamicSelect(SQL_SELECT + " WHERE codpresupuesto = ?",
				new Object[] { Integer.valueOf(codPresupuesto) });
		return presupuestos.size() == 0 ? null : presupuestos.get(0);
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

	private void populateDto(Presupuesto dto, ResultSet rs) throws SQLException {
		dto.setCodPresupuesto(rs.getInt(COLUMN_CODPRESUPUESTO));
		dto.setCliente(rs.getString(COLUMN_CLIENTE));
		dto.setTelefono(rs.getString(COLUMN_TELEFONO));
		dto.setTotal(rs.getBigDecimal(COLUMN_TOTAL));
		dto.setValidez(rs.getDate(COLUMN_VALIDEZ).toLocalDate());
		dto.setFecha(rs.getDate(COLUMN_FECHA).toLocalDate());
	}

	private ObservableList<Presupuesto> fetchMultiResults(ResultSet rs) throws SQLException {
		ObservableList<Presupuesto> resultList = FXCollections.observableArrayList();
		while (rs.next()) {
			Presupuesto dto = new Presupuesto();
			populateDto(dto, rs);
			resultList.add(dto);
		}
		return resultList;
	}

	private ObservableList<Presupuesto> findByDynamicSelect(String sql, Object[] sqlParams) throws SQLException {
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
