package es.rfgr.cr4vegas.modelo.postgresql.bd.almacen.taquilla;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import es.rfgr.cr4vegas.modelo.postgresql.conexion.ConexionAlmacen;
import es.rfgr.cr4vegas.modelo.postgresql.dto.almacen.taquilla.Taquilla;
import es.rfgr.cr4vegas.modelo.postgresql.dto.almacen.taquilla.TaquillaPK;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class TaquillaBD {

	private Connection userConn;

	private final String tableName = "public.taquilla";

	private final String SQL_SELECT = "SELECT * FROM " + getTableName();

	private final String SQL_INSERT = "INSERT INTO " + getTableName() + "(activo, tipo, lugar) VALUES (?, ?, ?)";

	private final String SQL_UPDATE = "UPDATE " + getTableName() + " SET activo=?, tipo=?, lugar=? "
			+ "WHERE codtaquilla=?";

	private final String SQL_DELETE = "DELETE FROM " + getTableName() + " WHERE codtaquilla=?";

	// COLUMNS

	private static final int COLUMN_CODTAQUILLA = 1;

	private static final int COLUMN_ACTIVO = 2;

	private static final int COLUMN_TIPO = 3;

	private static final int COLUMN_LUGAR = 4;

	private static final int PK_COLUMN_CODTAQUILLA = 1;

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
	public TaquillaBD() {

	}

	public void setConnection(Connection conn) {
		this.userConn = conn;
	}

	// FUNCTIONS

	public TaquillaPK insert(Taquilla dto) throws SQLException {
		try {
			initializeTimeConnSuppliedConn();

			ps = conn.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS);

			index = 1;
			ps.setBoolean(index++, dto.isActivo());
			ps.setString(index++, dto.getTipoTaquilla());
			ps.setString(index, dto.getLugar());
			System.out.println("Ejecutando " + SQL_INSERT + " con DTO: " + dto);

			rows = ps.executeUpdate();

			finalizeTimeConnSuppliedConn();

			// Obtener valores de las columnas auto-incrementables
			rs = ps.getGeneratedKeys();
			if (rs != null && rs.next()) {
				dto.setCodTaquilla(rs.getInt(PK_COLUMN_CODTAQUILLA));
			}

			return dto.createPK();

		} finally {
			finallyTryCatch();
		}
	}

	public void update(TaquillaPK pk, Taquilla dto) throws SQLException {
		try {
			initializeTimeConnSuppliedConn();

			ps = conn.prepareStatement(SQL_UPDATE);

			index = 1;
			ps.setBoolean(index++, dto.isActivo());
			ps.setString(index++, dto.getTipoTaquilla());
			ps.setString(index++, dto.getLugar());
			ps.setInt(index, pk.getCodTaquilla());
			System.out.println("Ejecutando " + SQL_UPDATE + " with DTO: " + dto);

			rows = ps.executeUpdate();

			finalizeTimeConnSuppliedConn();

		} finally {
			finallyTryCatch();
		}
	}

	public void delete(TaquillaPK pk) throws SQLException {
		try {
			initializeTimeConnSuppliedConn();

			ps = conn.prepareStatement(SQL_DELETE);

			ps.setInt(1, pk.getCodTaquilla());
			System.out.println("Ejecutando " + SQL_DELETE + " with DTO: " + pk);

			rows = ps.executeUpdate();

			finalizeTimeConnSuppliedConn();

		} finally {
			finallyTryCatch();
		}
	}

	public ObservableList<Taquilla> findAll() throws SQLException {
		return findByDynamicSelect(SQL_SELECT + " ORDER BY codtaquilla", null);
	}
	
	public Taquilla findByPrimaryKey(TaquillaPK pk) throws SQLException {
		return findByPrimaryKey( pk.getCodTaquilla() );
	}

	public Taquilla findByPrimaryKey(int codTaquilla) throws SQLException {
		ObservableList<Taquilla> taquillas = findByDynamicSelect( SQL_SELECT + " WHERE codtaquilla = ?", new Object[] {  Integer.valueOf(codTaquilla) } );
		return taquillas.size() == 0 ? null : taquillas.get(0);
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

	private void populateDto(Taquilla dto, ResultSet rs) throws SQLException {
		dto.setCodTaquilla(rs.getInt(COLUMN_CODTAQUILLA));
		dto.setActivo(rs.getBoolean(COLUMN_ACTIVO));
		dto.setTipoTaquilla(rs.getString(COLUMN_TIPO));
		dto.setLugar(rs.getString(COLUMN_LUGAR));
	}

	private ObservableList<Taquilla> fetchMultiResults(ResultSet rs) throws SQLException {
		ObservableList<Taquilla> resultList = FXCollections.observableArrayList();
		while (rs.next()) {
			Taquilla dto = new Taquilla();
			populateDto(dto, rs);
			resultList.add(dto);
		}
		return resultList;
	}

	private ObservableList<Taquilla> findByDynamicSelect(String sql, Object[] sqlParams) throws SQLException {
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