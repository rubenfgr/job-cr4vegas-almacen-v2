package es.rfgr.cr4vegas.modelo.postgresql.bd.evento;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import es.rfgr.cr4vegas.modelo.postgresql.conexion.ConexionAlmacen;
import es.rfgr.cr4vegas.modelo.postgresql.dto.evento.Evento;
import es.rfgr.cr4vegas.modelo.postgresql.dto.evento.EventoPK;
import es.rfgr.cr4vegas.utileria.Origen;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class EventoBD {

	private Connection userConn;

	private final String tableName = "public.evento";

	// SQL DML

	private final String SQL_SELECT = "SELECT * FROM " + getTableName();

	private final String SQL_INSERT = "INSERT INTO " + getTableName() + " VALUES (0, ?, ?, ?, ?, " + "?, ?, ?, ?, ?)";

	private final String SQL_UPDATE = "UPDATE " + getTableName() + " SET activo=?, origen=?, grupo=?, "
			+ "orden=?, descripcion=?, prioridad=?, estado=?, parte=? WHERE codevento=?";

	private final String SQL_DELETE = "DELETE FROM " + getTableName() + " WHERE codevento=?";

	// COLUMNS

	private static final int COLUMN_CODEVENTO = 1;

	private static final int COLUMN_ACTIVO = 2;

	private static final int COLUMN_ORIGEN = 3;

	private static final int COLUMN_GRUPO = 4;

	private static final int COLUMN_ORDEN = 5;

	private static final int COLUMN_DESCRIPCION = 6;

	private static final int COLUMN_PRIORIDAD = 7;

	private static final int COLUMN_ESTADO = 8;

	private static final int COLUMN_FECHA = 9;

	private static final int COLUMN_PARTE = 10;

	private static final int PK_COLUMN_CODEVENTO = 1;

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

	public EventoBD() {

	}

	public void setConnection(Connection conn) {
		this.userConn = conn;
	}

	// FUNCTIONS

	public EventoPK insert(Evento dto) throws SQLException {
		try {
			initializeTimeConnSuppliedConn();

			ps = conn.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS);

			dto.setFecha(LocalDateTime.now());

			index = 1;
			ps.setBoolean(index++, dto.isActivo());
			ps.setString(index++, dto.getOrigen().toString());
			ps.setString(index++, dto.getGrupo());
			ps.setString(index++, dto.getOrden());
			ps.setString(index++, dto.getDescripcion());
			ps.setString(index++, dto.getPrioridad());
			ps.setBoolean(index++, dto.isEstado());
			ps.setTimestamp(index++, Timestamp.valueOf(dto.getFecha()));
			ps.setBoolean(index, dto.isParte());
			System.out.println("Ejecutando " + SQL_INSERT + " con DTO: " + dto);

			rows = ps.executeUpdate();

			finalizeTimeConnSuppliedConn();

			// Obtener valores de las columnas auto-incrementables
			rs = ps.getGeneratedKeys();
			if (rs != null && rs.next()) {
				dto.setCodEvento(rs.getInt(PK_COLUMN_CODEVENTO));
			}

			return dto.createPK();

		} finally {
			finallyTryCatch();
		}
	}

	public void update(EventoPK pk, Evento dto) throws SQLException {
		try {
			initializeTimeConnSuppliedConn();

			ps = conn.prepareStatement(SQL_UPDATE);

			index = 1;
			ps.setBoolean(index++, dto.isActivo());
			ps.setString(index++, dto.getOrigen().toString());
			ps.setString(index++, dto.getGrupo());
			ps.setString(index++, dto.getOrden());
			ps.setString(index++, dto.getDescripcion());
			ps.setString(index++, dto.getPrioridad());
			ps.setBoolean(index++, dto.isEstado());
			ps.setBoolean(index++, dto.isParte());
			ps.setInt(index, pk.getCodEvento());
			System.out.println("Ejecutando " + SQL_UPDATE + " with DTO: " + dto);

			rows = ps.executeUpdate();

			finalizeTimeConnSuppliedConn();

		} finally {
			finallyTryCatch();
		}
	}

	public void delete(EventoPK pk) throws SQLException {
		try {
			initializeTimeConnSuppliedConn();

			ps = conn.prepareStatement(SQL_DELETE);

			ps.setInt(1, pk.getCodEvento());
			System.out.println("Ejecutando " + SQL_DELETE + " with DTO: " + pk);

			rows = ps.executeUpdate();

			finalizeTimeConnSuppliedConn();

		} finally {
			finallyTryCatch();
		}
	}

	public ObservableList<Evento> findAll() throws SQLException {
		return findByDynamicSelect(SQL_SELECT + " ORDER BY codevento", null);
	}
	
	public Evento findByPrimaryKey(EventoPK pk) throws SQLException {
		return findByPrimaryKey( pk.getCodEvento() );
	}

	public Evento findByPrimaryKey(int codEvento) throws SQLException {
		ObservableList<Evento> eventos = findByDynamicSelect( SQL_SELECT + " WHERE codevento = ?", new Object[] {  Integer.valueOf(codEvento) } );
		return eventos.size() == 0 ? null : eventos.get(0);
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

	private void populateDto(Evento dto, ResultSet rs) throws SQLException {
		dto.setCodEvento(rs.getInt(COLUMN_CODEVENTO));
		dto.setActivo(rs.getBoolean(COLUMN_ACTIVO));
		dto.setOrigen(Origen.valueOf(rs.getString(COLUMN_ORIGEN)));
		dto.setGrupo(rs.getString(COLUMN_GRUPO));
		dto.setOrden(rs.getString(COLUMN_ORDEN));
		dto.setDescripcion(rs.getString(COLUMN_DESCRIPCION));
		dto.setPrioridad(rs.getString(COLUMN_PRIORIDAD));
		dto.setEstado(rs.getBoolean(COLUMN_ESTADO));
		dto.setFecha(rs.getTimestamp(COLUMN_FECHA).toLocalDateTime());
		dto.setParte(rs.getBoolean(COLUMN_PARTE));
	}

	private ObservableList<Evento> fetchMultiResults(ResultSet rs) throws SQLException {
		ObservableList<Evento> resultList = FXCollections.observableArrayList();
		while (rs.next()) {
			Evento dto = new Evento();
			populateDto(dto, rs);
			resultList.add(dto);
		}
		return resultList;
	}

	private ObservableList<Evento> findByDynamicSelect(String sql, Object[] sqlParams) throws SQLException {
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
