package es.rfgr.cr4vegas.modelo.postgresql.bd.mantenimiento.instalacion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import es.rfgr.cr4vegas.modelo.postgresql.conexion.ConexionAlmacen;
import es.rfgr.cr4vegas.modelo.postgresql.dto.mantenimiento.instalacion.Instalacion;
import es.rfgr.cr4vegas.modelo.postgresql.dto.mantenimiento.instalacion.InstalacionPK;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class InstalacionBD {

	private Connection userConn;

	private final String tableName = "public.instalacion";

	// SQL DML

	private final String SQL_SELECT = "SELECT * FROM " + getTableName();

	private final String SQL_INSERT = "INSERT INTO " + getTableName() + " VALUES (0, ?, ?, ?, ?, ?)";

	private final String SQL_UPDATE = "UPDATE " + getTableName() + " SET codestacion=?, tipo=?, nombre=? "
			+ ", nota=?, codimagen=? WHERE codinstalacion=?";

	private final String SQL_DELETE = "DELETE FROM " + getTableName() + " WHERE codinstalacion=?";

	// COLUMNS

	private static final int COLUMN_CODINSTALACION = 1;

	private static final int COLUMN_CODESTACION = 2;

	private static final int COLUMN_TIPO = 3;

	private static final int COLUMN_NOMBRE = 4;

	private static final int COLUMN_NOTA = 5;

	private static final int COLUMN_CODIMAGEN = 6;

	private static final int PK_COLUMN_CODINSTALACION = 1;

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

	public InstalacionBD() {

	}

	public void setConnection(Connection conn) {
		this.userConn = conn;
	}

	// FUNCTIONS

	public InstalacionPK insert(Instalacion dto) throws SQLException {
		try {
			initializeTimeConnSuppliedConn();

			ps = conn.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS);

			index = 1;
			ps.setInt(index++, dto.getCodEstacion());
			ps.setString(index++, dto.getTipo());
			ps.setString(index++, dto.getNombre());
			ps.setString(index++, dto.getNota());
			ps.setInt(index, dto.getCodImagen());
			System.out.println("Ejecutando " + SQL_INSERT + " con DTO: " + dto);

			rows = ps.executeUpdate();

			finalizeTimeConnSuppliedConn();

			// Obtener valores de las columnas auto-incrementables
			rs = ps.getGeneratedKeys();
			if (rs != null && rs.next()) {
				dto.setCodInstalacion(rs.getInt(PK_COLUMN_CODINSTALACION));
			}

			return dto.createPK();

		} finally {
			finallyTryCatch();
		}
	}

	public void update(InstalacionPK pk, Instalacion dto) throws SQLException {
		try {
			initializeTimeConnSuppliedConn();

			ps = conn.prepareStatement(SQL_UPDATE);

			index = 1;
			ps.setInt(index++, dto.getCodEstacion());
			ps.setString(index++, dto.getTipo());
			ps.setString(index++, dto.getNombre());
			ps.setString(index++, dto.getNota());
			ps.setInt(index++, dto.getCodImagen());
			ps.setInt(index, pk.getCodInstalacion());
			System.out.println("Ejecutando " + SQL_UPDATE + " with DTO: " + dto);

			rows = ps.executeUpdate();

			finalizeTimeConnSuppliedConn();

		} finally {
			finallyTryCatch();
		}
	}

	public void delete(InstalacionPK pk) throws SQLException {
		try {
			initializeTimeConnSuppliedConn();

			System.out.println("Ejecutando " + SQL_DELETE + " with DTO: " + pk);
			ps = conn.prepareStatement(SQL_DELETE);

			ps.setInt(1, pk.getCodInstalacion());

			rows = ps.executeUpdate();

			finalizeTimeConnSuppliedConn();

		} finally {
			finallyTryCatch();
		}
	}

	public ObservableList<Instalacion> findAll() throws SQLException {
		return findByDynamicSelect(SQL_SELECT + " ORDER BY codinstalacion", null);
	}
	
	public Instalacion findByPrimaryKey(InstalacionPK pk) throws SQLException {
		return findByPrimaryKey( pk.getCodInstalacion() );
	}

	public Instalacion findByPrimaryKey(int codinstalacion) throws SQLException {
		ObservableList<Instalacion> instalaciones = findByDynamicSelect( SQL_SELECT + " WHERE codinstalacion = ?", new Object[] {  Integer.valueOf(codinstalacion) } );
		return instalaciones.size() == 0 ? null : instalaciones.get(0);
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

	private void populateDto(Instalacion dto, ResultSet rs) throws SQLException {
		dto.setCodInstalacion(rs.getInt(COLUMN_CODINSTALACION));
		dto.setCodEstacion(rs.getInt(COLUMN_CODESTACION));
		dto.setTipo(rs.getString(COLUMN_TIPO));
		dto.setNombre(rs.getString(COLUMN_NOMBRE));
		dto.setNota(rs.getString(COLUMN_NOTA));
		dto.setCodImagen(rs.getInt(COLUMN_CODIMAGEN));
	}

	private ObservableList<Instalacion> fetchMultiResults(ResultSet rs) throws SQLException {
		ObservableList<Instalacion> resultList = FXCollections.observableArrayList();
		while (rs.next()) {
			Instalacion dto = new Instalacion();
			populateDto(dto, rs);
			resultList.add(dto);
		}
		return resultList;
	}

	private ObservableList<Instalacion> findByDynamicSelect(String sql, Object[] sqlParams) throws SQLException {
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
