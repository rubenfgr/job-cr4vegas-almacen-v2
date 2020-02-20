package es.rfgr.cr4vegas.modelo.postgresql.bd.almacen.taquilla;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import es.rfgr.cr4vegas.modelo.postgresql.conexion.ConexionAlmacen;
import es.rfgr.cr4vegas.modelo.postgresql.dto.almacen.taquilla.OperaTaquillaMaterial;
import es.rfgr.cr4vegas.modelo.postgresql.dto.almacen.taquilla.OperaTaquillaMaterialPK;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class OperaTaquillaMaterialBD {

	private Connection userConn;

	private final String tableName = "public.operataquillamaterial";

	// SQL DML

	private final String SQL_SELECT = "SELECT * FROM " + getTableName();

	private final String SQL_INSERT = "INSERT INTO " + getTableName()
			+ " (codtaquilla, codigo4v, cantidad, fecha) VALUES (?, ?, ?, ?)";

	private final String SQL_UPDATE = "UPDATE " + getTableName() + " SET codtaquilla=?, cantidad=? "
			+ "WHERE codoperacion=?";

	private final String SQL_DELETE = "DELETE FROM " + getTableName() + " WHERE codoperacion=?";

	// COLUMNS

	private static final int COLUMN_CODOPERACION = 1;

	private static final int COLUMN_CODTAQUILLA = 2;

	private static final int COLUMN_CODIGO4V = 3;

	private static final int COLUMN_CANTIDAD = 4;

	private static final int COLUMN_FECHA = 5;

	private static final int PK_COLUMN_CODOPERACION = 1;

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

	public OperaTaquillaMaterialBD() {

	}

	public void setConnection(Connection conn) {
		this.userConn = conn;
	}

	// FUNCTIONS

	public OperaTaquillaMaterialPK insert(OperaTaquillaMaterial dto) throws SQLException {
		try {
			initializeTimeConnSuppliedConn();

			ps = conn.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS);

			dto.setFecha(LocalDateTime.now());

			index = 1;
			ps.setInt(index++, dto.getCodTaquilla());
			ps.setInt(index++, dto.getCodigo4v());
			ps.setInt(index++, dto.getCantidad());
			ps.setTimestamp(index, Timestamp.valueOf(dto.getFecha()));
			System.out.println("Ejecutando " + SQL_INSERT + " con DTO: " + dto);

			rows = ps.executeUpdate();

			finalizeTimeConnSuppliedConn();

			// Obtener valores de las columnas auto-incrementables
			rs = ps.getGeneratedKeys();
			if (rs != null && rs.next()) {
				dto.setCodOperacion(rs.getInt(PK_COLUMN_CODOPERACION));
			}

			return dto.createPK();

		} finally {
			finallyTryCatch();
		}
	}

	public void update(OperaTaquillaMaterialPK pk, OperaTaquillaMaterial dto) throws SQLException {
		try {
			initializeTimeConnSuppliedConn();

			ps = conn.prepareStatement(SQL_UPDATE);

			index = 1;
			ps.setInt(index++, dto.getCodTaquilla());
			ps.setInt(index++, dto.getCantidad());
			ps.setInt(index, pk.getCodOperacion());
			System.out.println("Ejecutando " + SQL_UPDATE + " with DTO: " + dto);

			rows = ps.executeUpdate();

			finalizeTimeConnSuppliedConn();

		} finally {
			finallyTryCatch();
		}
	}

	public void delete(OperaTaquillaMaterialPK pk) throws SQLException {
		try {
			initializeTimeConnSuppliedConn();

			ps = conn.prepareStatement(SQL_DELETE);

			ps.setInt(1, pk.getCodOperacion());
			System.out.println("Ejecutando " + SQL_DELETE + " with DTO: " + pk);

			rows = ps.executeUpdate();

			finalizeTimeConnSuppliedConn();

		} finally {
			finallyTryCatch();
		}
	}

	public ObservableList<OperaTaquillaMaterial> findAll() throws SQLException {
		return findByDynamicSelect(SQL_SELECT + " ORDER BY codoperacion", null);
	}
	
	public OperaTaquillaMaterial findByPrimaryKey(OperaTaquillaMaterialPK pk) throws SQLException {
		return findByPrimaryKey( pk.getCodOperacion() );
	}

	public OperaTaquillaMaterial findByPrimaryKey(int codOperacion) throws SQLException {
		ObservableList<OperaTaquillaMaterial> taquillaMateriales = findByDynamicSelect( SQL_SELECT + " WHERE codoperacion = ?", new Object[] {  Integer.valueOf(codOperacion) } );
		return taquillaMateriales.size() == 0 ? null : taquillaMateriales.get(0);
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

	private void populateDto(OperaTaquillaMaterial dto, ResultSet rs) throws SQLException {
		dto.setCodOperacion(COLUMN_CODOPERACION);
		dto.setCodTaquilla(rs.getInt(COLUMN_CODTAQUILLA));
		dto.setCodigo4v(rs.getInt(COLUMN_CODIGO4V));
		dto.setCantidad(rs.getInt(COLUMN_CANTIDAD));
		dto.setFecha(rs.getTimestamp(COLUMN_FECHA).toLocalDateTime());
	}

	private ObservableList<OperaTaquillaMaterial> fetchMultiResults(ResultSet rs) throws SQLException {
		ObservableList<OperaTaquillaMaterial> resultList = FXCollections.observableArrayList();
		while (rs.next()) {
			OperaTaquillaMaterial dto = new OperaTaquillaMaterial();
			populateDto(dto, rs);
			resultList.add(dto);
		}
		return resultList;
	}

	private ObservableList<OperaTaquillaMaterial> findByDynamicSelect(String sql, Object[] sqlParams)
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