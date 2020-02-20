package es.rfgr.cr4vegas.modelo.postgresql.bd.parte;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

import es.rfgr.cr4vegas.modelo.postgresql.conexion.ConexionAlmacen;
import es.rfgr.cr4vegas.modelo.postgresql.dto.parte.Parte;
import es.rfgr.cr4vegas.modelo.postgresql.dto.parte.PartePK;
import es.rfgr.cr4vegas.utileria.Origen;
import es.rfgr.cr4vegas.utileria.Prioridad;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ParteBD {

	private Connection userConn;

	private final String tableName = "public.parte";

	// SQL DML

	private final String SQL_SELECT = "SELECT * FROM " + getTableName();

	private final String SQL_INSERT = "INSERT INTO " + getTableName()
			+ " (parteoficial, tipo, grupo, origen, orden, descripcion,"
			+ " prioridad, fecha, estimado, llamo, contador, telefono, estado, impreso, hidrante) VALUES (?, ?, ?, ?, ?, ?"
			+ ", ?, ?, ?, ?, ?, ?, ?, ?, ?)";

	private final String SQL_UPDATE = "UPDATE " + getTableName() + " SET parteoficial=?, tipo=?, grupo=?"
			+ ", origen=?, orden=?, descripcion=?, prioridad=?, fecha=?, estimado=?, llamo=?, contador=?"
			+ ", telefono=?, estado=?, impreso=?, hidrante=? WHERE codparte=?";

	private final String SQL_DELETE = "DELETE FROM " + getTableName() + " WHERE codparte=?";

	// COLUMNS

	private static final int COLUMN_CODPARTE = 1;

	private static final int COLUMN_PARTEOFICIAL = 2;

	private static final int COLUMN_TIPO = 3;

	private static final int COLUMN_GRUPO = 4;

	private static final int COLUMN_ORIGEN = 5;

	private static final int COLUMN_ORDEN = 6;

	private static final int COLUMN_DESCRIPCION = 7;

	private static final int COLUMN_PRIORIDAD = 8;

	private static final int COLUMN_FECHA = 9;

	private static final int COLUMN_ESTIMADO = 10;

	private static final int COLUMN_LLAMO = 11;

	private static final int COLUMN_CONTADOR = 12;

	private static final int COLUMN_TELEFONO = 13;

	private static final int COLUMN_ESTADO = 14;

	private static final int COLUMN_IMPRESO = 15;
	
	private static final int COLUMN_HIDRANTE = 16;

	private static final int PK_COLUMN_CODPARTE = 1;

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

	public ParteBD() {

	}

	public void setConnection(Connection conn) {
		this.userConn = conn;
	}

	// FUNCTIONS

	public PartePK insert(Parte dto) throws SQLException {
		try {
			initializeTimeConnSuppliedConn();

			ps = conn.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS);

			index = 1;
			ps.setInt(index++, dto.getParteOficial());
			ps.setInt(index++, dto.getTipo());
			ps.setString(index++, dto.getGrupo());
			ps.setString(index++, dto.getOrigen().name());
			ps.setString(index++, dto.getOrden());
			ps.setString(index++, dto.getDescripcion());
			ps.setString(index++, dto.getPrioridad().name());
			ps.setTimestamp(index++, Timestamp.valueOf(dto.getFecha()));
			ps.setString(index++, dto.getEstimado());
			ps.setString(index++, dto.getLlamo());
			ps.setString(index++, dto.getContador());
			ps.setString(index++, dto.getTelefono());
			ps.setBoolean(index++, dto.isEstado());
			ps.setBoolean(index++, dto.isImpreso());
			ps.setString(index, dto.getHidrante());
			System.out.println("Ejecutando " + SQL_INSERT + " con DTO: " + dto);

			rows = ps.executeUpdate();

			// Obtener valores de las columnas auto-incrementables
			rs = ps.getGeneratedKeys();

			if (rs != null && rs.next()) {
				dto.setCodParte(rs.getInt(PK_COLUMN_CODPARTE));
			}
			
			finalizeTimeConnSuppliedConn();

			return dto.createPK();

		} finally {
			finallyTryCatch();
		}
	}

	public void update(PartePK pk, Parte dto) throws SQLException {
		try {
			initializeTimeConnSuppliedConn();

			ps = conn.prepareStatement(SQL_UPDATE);

			index = 1;
			ps.setInt(index++, dto.getParteOficial());
			ps.setInt(index++, dto.getTipo());
			ps.setString(index++, dto.getGrupo());
			ps.setString(index++, dto.getOrigen().name());
			ps.setString(index++, dto.getOrden());
			ps.setString(index++, dto.getDescripcion());
			ps.setString(index++, dto.getPrioridad().name());
			ps.setTimestamp(index++, Timestamp.valueOf(dto.getFecha()));
			ps.setString(index++, dto.getEstimado());
			ps.setString(index++, dto.getLlamo());
			ps.setString(index++, dto.getContador());
			ps.setString(index++, dto.getTelefono());
			ps.setBoolean(index++, dto.isEstado());
			ps.setBoolean(index++, dto.isImpreso());
			ps.setString(index++, dto.getHidrante());
			ps.setInt(index, pk.getCodParte());
			System.out.println("Ejecutando " + SQL_UPDATE + " with DTO: " + dto);

			rows = ps.executeUpdate();

			finalizeTimeConnSuppliedConn();

		} finally {
			finallyTryCatch();
		}
	}

	public void delete(PartePK pk) throws SQLException {
		try {
			initializeTimeConnSuppliedConn();

			ps = conn.prepareStatement(SQL_DELETE);

			ps.setInt(1, pk.getCodParte());
			System.out.println("Ejecutando " + SQL_DELETE + " with DTO: " + pk);

			rows = ps.executeUpdate();

			finalizeTimeConnSuppliedConn();

		} finally {
			finallyTryCatch();
		}
	}

	public ObservableList<Parte> findAll() throws SQLException {
		return findByDynamicSelect(SQL_SELECT + " ORDER BY codparte DESC", null);
	}

	public Parte findByPrimaryKey(PartePK pk) throws SQLException {
		return findByPrimaryKey(pk.getCodParte());
	}

	public Parte findByPrimaryKey(int codParte) throws SQLException {
		ObservableList<Parte> partes = findByDynamicSelect(SQL_SELECT + " WHERE codparte = ?",
				new Object[] { Integer.valueOf(codParte) });
		return partes.size() == 0 ? null : partes.get(0);
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

	private void populateDto(Parte dto, ResultSet rs) throws SQLException {
		dto.setCodParte(rs.getInt(COLUMN_CODPARTE));
		dto.setParteOficial(rs.getInt(COLUMN_PARTEOFICIAL));
		dto.setTipo(rs.getInt(COLUMN_TIPO));
		dto.setGrupo(rs.getString(COLUMN_GRUPO));
		dto.setOrigen(Origen.valueOf(rs.getString(COLUMN_ORIGEN) == null? "NINGUNO" : rs.getString(COLUMN_ORIGEN)));
		dto.setOrden(rs.getString(COLUMN_ORDEN));
		dto.setDescripcion(rs.getString(COLUMN_DESCRIPCION));
		dto.setPrioridad(Prioridad.valueOf(rs.getString(COLUMN_PRIORIDAD) == null? "NINGUNA" : rs.getString(COLUMN_PRIORIDAD)));
		dto.setFecha(rs.getTimestamp(COLUMN_FECHA).toLocalDateTime());
		dto.setEstimado(rs.getString(COLUMN_ESTIMADO));
		dto.setLlamo(rs.getString(COLUMN_LLAMO));
		dto.setContador(rs.getString(COLUMN_CONTADOR));
		dto.setTelefono(rs.getString(COLUMN_TELEFONO));
		dto.setEstado(rs.getBoolean(COLUMN_ESTADO));
		dto.setImpreso(rs.getBoolean(COLUMN_IMPRESO));
		dto.setHidrante(rs.getString(COLUMN_HIDRANTE));
	}

	private ObservableList<Parte> fetchMultiResults(ResultSet rs) throws SQLException {
		ObservableList<Parte> resultList = FXCollections.observableArrayList();
		while (rs.next()) {
			Parte dto = new Parte();
			populateDto(dto, rs);
			resultList.add(dto);
		}
		return resultList;
	}

	private ObservableList<Parte> findByDynamicSelect(String sql, Object[] sqlParams) throws SQLException {
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
