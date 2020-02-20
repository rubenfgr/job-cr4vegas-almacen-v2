package es.rfgr.cr4vegas.modelo.postgresql.bd.almacen.material;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import es.rfgr.cr4vegas.modelo.postgresql.conexion.ConexionAlmacen;
import es.rfgr.cr4vegas.modelo.postgresql.dto.almacen.material.TipoMaterial;
import es.rfgr.cr4vegas.modelo.postgresql.dto.almacen.material.TipoMaterialPK;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class TipoMaterialBD {

	private Connection userConn;

	private final String tableName = "public.tipomaterial";

	// SQL DML

	private final String SQL_SELECT = "SELECT * FROM " + getTableName();

	private final String SQL_INSERT = "INSERT INTO " + getTableName() + " VALUES (?)";

	private final String SQL_UPDATE = "UPDATE " + getTableName() + " SET nombre=? WHERE nombre=?";

	private final String SQL_DELETE = "DELETE FROM " + getTableName() + " WHERE nombre=?";

	// COLUMNS

	private static final int COLUMN_NOMBRE = 1;

	@SuppressWarnings("unused")
	private static final int PK_COLUMN_NOMBRE = 1;

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

	public TipoMaterialBD() {

	}

	public void setConnection(Connection conn) {
		this.userConn = conn;
	}

	// FUNCTIONS

	public TipoMaterialPK insert(TipoMaterial dto) throws SQLException {
		try {
			initializeTimeConnSuppliedConn();

			ps = conn.prepareStatement(SQL_INSERT);

			ps.setString(1, dto.getNombre());
			System.out.println("Ejecutando " + SQL_INSERT + " con DTO: " + dto);

			rows = ps.executeUpdate();

			finalizeTimeConnSuppliedConn();

			return dto.createPK();

		} finally {
			finallyTryCatch();
		}
	}

	public void update(TipoMaterialPK pk, TipoMaterial dto) throws SQLException {
		try {
			initializeTimeConnSuppliedConn();

			ps = conn.prepareStatement(SQL_UPDATE);

			index = 1;
			ps.setString(index++, dto.getNombre());
			ps.setString(index, pk.getNombre());
			System.out.println("Ejecutando " + SQL_UPDATE + " with DTO: " + dto);

			rows = ps.executeUpdate();

			finalizeTimeConnSuppliedConn();

		} finally {
			finallyTryCatch();
		}
	}

	public void delete(TipoMaterialPK pk) throws SQLException {
		try {
			initializeTimeConnSuppliedConn();

			ps = conn.prepareStatement(SQL_DELETE);

			ps.setString(1, pk.getNombre());
			System.out.println("Ejecutando " + SQL_DELETE + " with DTO: " + pk);

			rows = ps.executeUpdate();

			finalizeTimeConnSuppliedConn();

		} finally {
			finallyTryCatch();
		}
	}

	public ObservableList<TipoMaterial> findAll() throws SQLException {
		return findByDynamicSelect(SQL_SELECT + " ORDER BY nombre", null);
	}

	public TipoMaterial findByPrimaryKey(TipoMaterialPK pk) throws SQLException {
		return findByPrimaryKey(pk.getNombre());
	}

	public TipoMaterial findByPrimaryKey(String nombre) throws SQLException {
		ObservableList<TipoMaterial> tiposMaterial = findByDynamicSelect(SQL_SELECT + " WHERE nombre = ?",
				new Object[] { nombre });
		return tiposMaterial.size() == 0 ? null : tiposMaterial.get(0);
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

	private void populateDto(TipoMaterial dto, ResultSet rs) throws SQLException {
		dto.setNombre(rs.getString(COLUMN_NOMBRE));
	}

	private ObservableList<TipoMaterial> fetchMultiResults(ResultSet rs) throws SQLException {
		ObservableList<TipoMaterial> resultList = FXCollections.observableArrayList();
		while (rs.next()) {
			TipoMaterial dto = new TipoMaterial();
			populateDto(dto, rs);
			resultList.add(dto);
		}
		return resultList;
	}

	private ObservableList<TipoMaterial> findByDynamicSelect(String sql, Object[] sqlParams) throws SQLException {
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
