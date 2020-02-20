package es.rfgr.cr4vegas.modelo.postgresql.bd.mantenimiento.estacion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import es.rfgr.cr4vegas.modelo.postgresql.conexion.ConexionAlmacen;
import es.rfgr.cr4vegas.modelo.postgresql.dto.mantenimiento.estacion.Estacion;
import es.rfgr.cr4vegas.modelo.postgresql.dto.mantenimiento.estacion.EstacionPK;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class EstacionBD {

	private Connection userConn;

	private final String tableName = "public.estacion";

	// SQL DML

	private final String SQL_SELECT = "SELECT * FROM " + getTableName();

	private final String SQL_INSERT = "INSERT INTO " + getTableName() + " VALUES (0, ?, ?, ?)";

	private final String SQL_UPDATE = "UPDATE " + getTableName()
			+ " SET nombre=?, nota=?, codimagen=? WHERE codestacion=?";

	private final String SQL_DELETE = "DELETE FROM " + getTableName() + " WHERE codestacion=?";

	// COLUMNS

	private static final int COLUMN_CODESTACION = 1;

	private static final int COLUMN_NOMBRE = 2;

	private static final int COLUMN_NOTA = 3;

	private static final int COLUMN_CODIMAGEN = 4;

	private static final int PK_COLUMN_CODESTACION = 1;

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

	public EstacionBD() {

	}

	public void setConnection(Connection conn) {
		this.userConn = conn;
	}

	// FUNCTIONS

	public EstacionPK insert(Estacion dto) throws SQLException {
		try {
			initializeTimeConnSuppliedConn();

			ps = conn.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS);

			index = 1;
			ps.setString(index++, dto.getNombre());
			ps.setString(index++, dto.getNota());
			ps.setInt(index, dto.getCodImagen());
			System.out.println("Ejecutando " + SQL_INSERT + " con DTO: " + dto);

			rows = ps.executeUpdate();

			finalizeTimeConnSuppliedConn();

			// Obtener valores de las columnas auto-incrementables
			rs = ps.getGeneratedKeys();
			if (rs != null && rs.next()) {
				dto.setCodEstacion(rs.getInt(PK_COLUMN_CODESTACION));
			}

			return dto.createPK();

		} finally {
			finallyTryCatch();
		}
	}

	public void update(EstacionPK pk, Estacion dto) throws SQLException {
		try {
			initializeTimeConnSuppliedConn();

			ps = conn.prepareStatement(SQL_UPDATE);

			index = 1;
			ps.setString(index++, dto.getNombre());
			ps.setString(index++, dto.getNota());
			ps.setInt(index++, dto.getCodImagen());
			ps.setInt(index, pk.getCodEstacion());
			System.out.println("Ejecutando " + SQL_UPDATE + " with DTO: " + dto);

			rows = ps.executeUpdate();

			finalizeTimeConnSuppliedConn();

		} finally {
			finallyTryCatch();
		}
	}

	public void delete(EstacionPK pk) throws SQLException {
		try {
			initializeTimeConnSuppliedConn();

			ps = conn.prepareStatement(SQL_DELETE);

			ps.setInt(1, pk.getCodEstacion());
			System.out.println("Ejecutando " + SQL_DELETE + " with DTO: " + pk);

			rows = ps.executeUpdate();

			finalizeTimeConnSuppliedConn();

		} finally {
			finallyTryCatch();
		}
	}

	public ObservableList<Estacion> findAll() throws SQLException {
		return findByDynamicSelect(SQL_SELECT + " ORDER BY codestacion", null);
	}
	
	public Estacion findByPrimaryKey(EstacionPK pk) throws SQLException {
		return findByPrimaryKey( pk.getCodEstacion() );
	}

	public Estacion findByPrimaryKey(int codEstacion) throws SQLException {
		ObservableList<Estacion> estaciones = findByDynamicSelect( SQL_SELECT + " WHERE codestacion = ?", new Object[] {  Integer.valueOf(codEstacion) } );
		return estaciones.size() == 0 ? null : estaciones.get(0);
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

	private void populateDto(Estacion dto, ResultSet rs) throws SQLException {
		dto.setCodEstacion(rs.getInt(COLUMN_CODESTACION));
		dto.setNombre(rs.getString(COLUMN_NOMBRE));
		dto.setNota(rs.getString(COLUMN_NOTA));
		dto.setCodImagen(rs.getInt(COLUMN_CODIMAGEN));
	}

	private ObservableList<Estacion> fetchMultiResults(ResultSet rs) throws SQLException {
		ObservableList<Estacion> resultList = FXCollections.observableArrayList();
		while (rs.next()) {
			Estacion dto = new Estacion();
			populateDto(dto, rs);
			resultList.add(dto);
		}
		return resultList;
	}

	private ObservableList<Estacion> findByDynamicSelect(String sql, Object[] sqlParams) throws SQLException {
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
