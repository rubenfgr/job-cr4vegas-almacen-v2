package es.rfgr.cr4vegas.modelo.postgresql.bd.alarma;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

import es.rfgr.cr4vegas.modelo.postgresql.conexion.ConexionAlmacen;
import es.rfgr.cr4vegas.modelo.postgresql.dto.alarma.Alarma;
import es.rfgr.cr4vegas.modelo.postgresql.dto.alarma.AlarmaPK;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class AlarmaBD {

	private Connection userConn;

	private final String tableName = "public.alarma";

	// SQL DML

	private final String SQL_SELECT = "SELECT * FROM " + getTableName();

	private final String SQL_INSERT = "INSERT INTO " + getTableName() + " VALUES (0, ?, ?, ?, ?, " + "?, ?, ?, ?)";

	private final String SQL_UPDATE = "UPDATE " + getTableName() + " SET codop=?, activo=?, grupo=?, "
			+ "orden=?, descripcion=?, fecha=?, repetir=?, frecuencia=? WHERE codalarma=?";

	private final String SQL_DELETE = "DELETE FROM " + getTableName() + " WHERE codalarma=?";

	// COLUMNS

	private static final int COLUMN_CODALARMA = 1;

	private static final int COLUMN_CODOP = 2;

	private static final int COLUMN_ACTIVO = 3;

	private static final int COLUMN_GRUPO = 4;

	private static final int COLUMN_ORDEN = 5;

	private static final int COLUMN_DESCRIPCION = 6;

	private static final int COLUMN_FECHA = 7;

	private static final int COLUMN_REPETIR = 8;

	private static final int COLUMN_FRECUENCIA = 9;

	private static final int PK_COLUMN_CODALARMA = 1;

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
	public AlarmaBD() {

	}

	public void setConnection(Connection conn) {
		this.userConn = conn;
	}

	// FUNCTIONS

	public AlarmaPK insert(Alarma dto) throws SQLException {
		try {
			initializeTimeConnSuppliedConn();

			ps = conn.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS);

			index = 1;
			ps.setInt(index++, dto.getCodOp());
			ps.setBoolean(index++, dto.isActivo());
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
				dto.setCodAlarma(rs.getInt(PK_COLUMN_CODALARMA));
			}

			return dto.createPK();

		} finally {
			finallyTryCatch();
		}
	}

	public void update(AlarmaPK pk, Alarma dto) throws SQLException {
		try {
			initializeTimeConnSuppliedConn();

			ps = conn.prepareStatement(SQL_UPDATE);

			index = 1;
			ps.setInt(index++, dto.getCodOp());
			ps.setBoolean(index++, dto.isActivo());
			ps.setString(index++, dto.getGrupo());
			ps.setString(index++, dto.getOrden());
			ps.setString(index++, dto.getDescripcion());
			ps.setTimestamp(index++, Timestamp.valueOf(dto.getFecha()));
			ps.setBoolean(index++, dto.isRepetir());
			ps.setInt(index++, dto.getFrecuencia());
			ps.setInt(index, pk.getCodAlarma());
			System.out.println("Ejecutando " + SQL_UPDATE + " with DTO: " + dto);

			rows = ps.executeUpdate();

			finalizeTimeConnSuppliedConn();

		} finally {
			finallyTryCatch();
		}
	}

	public void delete(AlarmaPK pk) throws SQLException {
		try {
			initializeTimeConnSuppliedConn();

			ps = conn.prepareStatement(SQL_DELETE);

			ps.setInt(1, pk.getCodAlarma());
			System.out.println("Ejecutando " + SQL_DELETE + " with DTO: " + pk);

			rows = ps.executeUpdate();

			finalizeTimeConnSuppliedConn();

		} finally {
			finallyTryCatch();
		}
	}

	public ObservableList<Alarma> findAll() throws SQLException {
		return findByDynamicSelect(SQL_SELECT + " ORDER BY codalarma", null);
	}

	public void updateDTOAlarma(Alarma alarma) throws SQLException {
		Alarma alarmaActualizada = findByPrimaryKey(alarma.createPK());
		if (alarmaActualizada != null) {
			alarma.copiarValores(alarmaActualizada);
		}
	}

	public Alarma findByPrimaryKey(AlarmaPK pk) throws SQLException {
		return findByPrimaryKey(pk.getCodAlarma());
	}

	public Alarma findByPrimaryKey(int codAlarma) throws SQLException {
		ObservableList<Alarma> alarmas = findByDynamicSelect(SQL_SELECT + " WHERE codalarma = ?",
				new Object[] { Integer.valueOf(codAlarma) });
		return alarmas.size() == 0 ? null : alarmas.get(0);
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

	private void populateDto(Alarma dto, ResultSet rs) throws SQLException {
		dto.setCodAlarma(rs.getInt(COLUMN_CODALARMA));
		dto.setCodOp(rs.getInt(COLUMN_CODOP));
		dto.setActivo(rs.getBoolean(COLUMN_ACTIVO));
		dto.setGrupo(rs.getString(COLUMN_GRUPO));
		dto.setOrden(rs.getString(COLUMN_ORDEN));
		dto.setDescripcion(rs.getString(COLUMN_DESCRIPCION));
		dto.setFecha(rs.getTimestamp(COLUMN_FECHA).toLocalDateTime());
		dto.setRepetir(rs.getBoolean(COLUMN_REPETIR));
		dto.setFrecuencia(rs.getInt(COLUMN_FRECUENCIA));
	}

	private ObservableList<Alarma> fetchMultiResults(ResultSet rs) throws SQLException {
		ObservableList<Alarma> resultList = FXCollections.observableArrayList();
		while (rs.next()) {
			Alarma dto = new Alarma();
			populateDto(dto, rs);
			resultList.add(dto);
		}
		return resultList;
	}

	private ObservableList<Alarma> findByDynamicSelect(String sql, Object[] sqlParams) throws SQLException {
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
