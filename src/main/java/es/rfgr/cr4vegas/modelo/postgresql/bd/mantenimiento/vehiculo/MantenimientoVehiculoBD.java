package es.rfgr.cr4vegas.modelo.postgresql.bd.mantenimiento.vehiculo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

import es.rfgr.cr4vegas.modelo.postgresql.conexion.ConexionAlmacen;
import es.rfgr.cr4vegas.modelo.postgresql.dto.mantenimiento.vehiculo.MantenimientoVehiculo;
import es.rfgr.cr4vegas.modelo.postgresql.dto.mantenimiento.vehiculo.MantenimientoVehiculoPK;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class MantenimientoVehiculoBD {

	private Connection userConn;

	private final String tableName = "public.mantenimientovehiculo";

	// SQL DML

	private final String SQL_SELECT = "SELECT * FROM " + getTableName();

	private final String SQL_INSERT = "INSERT INTO " + getTableName() + " VALUES (0, ?, ?, ?, ?, ?, ?, ?)";

	private final String SQL_UPDATE = "UPDATE " + getTableName() + " SET matricula=?, grupo=?, orden=?, descripcion=?"
			+ ", fecha=?, repetir=?, frecuencia=? WHERE codman=?";

	private final String SQL_DELETE = "DELETE FROM " + getTableName() + " WHERE codman=?";

	// COLUMNS

	private static final int COLUMN_CODMAN = 1;

	private static final int COLUMN_MATRICULA = 2;

	private static final int COLUMN_GRUPO = 3;

	private static final int COLUMN_ORDEN = 4;

	private static final int COLUMN_DESCRIPCION = 5;

	private static final int COLUMN_FECHA = 6;

	private static final int COLUMN_REPETIR = 7;

	private static final int COLUMN_FRECUENCIA = 8;

	private static final int PK_COLUMN_CODMAN = 1;

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

	public MantenimientoVehiculoBD() {

	}

	public void setConnection(Connection conn) {
		this.userConn = conn;
	}

	// FUNCTIONS

	public MantenimientoVehiculoPK insert(MantenimientoVehiculo dto) throws SQLException {
		try {
			initializeTimeConnSuppliedConn();

			ps = conn.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS);

			index = 1;
			ps.setString(index++, dto.getMatricula());
			ps.setString(index++, dto.getGrupo());
			ps.setString(index++, dto.getOrden());
			ps.setString(index++, dto.getDescripcion());
			ps.setTimestamp(index++, Timestamp.valueOf(dto.getFecha()));
			ps.setBoolean(index++, dto.isRepetir());
			ps.setInt(index, dto.getFrecuencia());
			System.out.println("Ejecutando " + SQL_INSERT + " con DTO: " + dto);

			rows = ps.executeUpdate();

			finalizeTimeConnSuppliedConn();

			// Obtener valores de las columnas auto-incrementables
			rs = ps.getGeneratedKeys();
			if (rs != null && rs.next()) {
				dto.setCodMan(rs.getInt(PK_COLUMN_CODMAN));
			}

			return dto.createPK();

		} finally {
			finallyTryCatch();
		}
	}

	public void update(MantenimientoVehiculoPK pk, MantenimientoVehiculo dto) throws SQLException {
		try {
			initializeTimeConnSuppliedConn();

			ps = conn.prepareStatement(SQL_UPDATE);

			index = 1;
			ps.setString(index++, dto.getMatricula());
			ps.setString(index++, dto.getGrupo());
			ps.setString(index++, dto.getOrden());
			ps.setString(index++, dto.getDescripcion());
			ps.setTimestamp(index++, Timestamp.valueOf(dto.getFecha()));
			ps.setBoolean(index++, dto.isRepetir());
			ps.setInt(index++, dto.getFrecuencia());
			ps.setInt(index, pk.getCodMan());
			System.out.println("Ejecutando " + SQL_UPDATE + " with DTO: " + dto);

			rows = ps.executeUpdate();

			finalizeTimeConnSuppliedConn();

		} finally {
			finallyTryCatch();
		}
	}

	public void delete(MantenimientoVehiculoPK pk) throws SQLException {
		try {
			initializeTimeConnSuppliedConn();

			ps = conn.prepareStatement(SQL_DELETE);

			ps.setInt(1, pk.getCodMan());
			System.out.println("Ejecutando " + SQL_DELETE + " with DTO: " + pk);

			rows = ps.executeUpdate();

			finalizeTimeConnSuppliedConn();

		} finally {
			finallyTryCatch();
		}
	}

	public ObservableList<MantenimientoVehiculo> findAll() throws SQLException {
		return findByDynamicSelect(SQL_SELECT + " ORDER BY codman", null);
	}

	public MantenimientoVehiculo findByPrimaryKey(MantenimientoVehiculoPK pk) throws SQLException {
		return findByPrimaryKey( pk.getCodMan() );
	}

	public MantenimientoVehiculo findByPrimaryKey(int codMan) throws SQLException {
		ObservableList<MantenimientoVehiculo> mantenimientosVehiculos = findByDynamicSelect( SQL_SELECT + " WHERE codman = ?", new Object[] {  Integer.valueOf(codMan) } );
		return mantenimientosVehiculos.size() == 0 ? null : mantenimientosVehiculos.get(0);
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

	private void populateDto(MantenimientoVehiculo dto, ResultSet rs) throws SQLException {
		dto.setCodMan(rs.getInt(COLUMN_CODMAN));
		dto.setMatricula(rs.getString(COLUMN_MATRICULA));
		dto.setGrupo(rs.getString(COLUMN_GRUPO));
		dto.setOrden(rs.getString(COLUMN_ORDEN));
		dto.setDescripcion(rs.getString(COLUMN_DESCRIPCION));
		dto.setFecha(rs.getTimestamp(COLUMN_FECHA).toLocalDateTime());
		dto.setRepetir(rs.getBoolean(COLUMN_REPETIR));
		dto.setFrecuencia(rs.getInt(COLUMN_FRECUENCIA));
	}

	private ObservableList<MantenimientoVehiculo> fetchMultiResults(ResultSet rs) throws SQLException {
		ObservableList<MantenimientoVehiculo> resultList = FXCollections.observableArrayList();
		while (rs.next()) {
			MantenimientoVehiculo dto = new MantenimientoVehiculo();
			populateDto(dto, rs);
			resultList.add(dto);
		}
		return resultList;
	}

	private ObservableList<MantenimientoVehiculo> findByDynamicSelect(String sql, Object[] sqlParams)
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
