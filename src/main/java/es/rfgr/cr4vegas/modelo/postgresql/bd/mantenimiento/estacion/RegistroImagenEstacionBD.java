package es.rfgr.cr4vegas.modelo.postgresql.bd.mantenimiento.estacion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import es.rfgr.cr4vegas.modelo.postgresql.conexion.ConexionAlmacen;
import es.rfgr.cr4vegas.modelo.postgresql.dto.mantenimiento.estacion.RegistroImagenEstacion;
import es.rfgr.cr4vegas.modelo.postgresql.dto.mantenimiento.estacion.RegistroImagenEstacionPK;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class RegistroImagenEstacionBD {

	private Connection userConn;

	private final String tableName = "public.registroimagenestacion";

	// SQL DML

	private final String SQL_SELECT = "SELECT * FROM " + getTableName();

	private final String SQL_INSERT = "INSERT INTO " + getTableName() + " VALUES (?, ?)";

	private final String SQL_UPDATE = "UPDATE " + getTableName()
			+ " SET codimagen=? WHERE codregistro=? AND codimagen=?";

	private final String SQL_DELETE = "DELETE FROM " + getTableName() + " WHERE codregistro=? AND codimagen=?";

	// COLUMNS

	private static final int COLUMN_CODREGISTRO = 1;

	private static final int COLUMN_CODIMAGEN = 2;

	@SuppressWarnings("unused")
	private static final int PK_COLUMN_CODREGISTRO = 1;

	@SuppressWarnings("unused")
	private static final int PK_COLUMN_CODIMAGEN = 2;

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

	public RegistroImagenEstacionBD() {

	}

	public void setConnection(Connection conn) {
		this.userConn = conn;
	}

	public RegistroImagenEstacionPK insert(RegistroImagenEstacion dto) throws SQLException {
		try {
			initializeTimeConnSuppliedConn();

			ps = conn.prepareStatement(SQL_INSERT);

			index = 1;
			ps.setInt(index++, dto.getCodRegistroEstacion());
			ps.setInt(index, dto.getCodImagenEstacion());
			System.out.println("Ejecutando " + SQL_INSERT + " con DTO: " + dto);

			rows = ps.executeUpdate();

			finalizeTimeConnSuppliedConn();

			return dto.createPK();

		} finally {
			finallyTryCatch();
		}
	}

	public void update(RegistroImagenEstacionPK pk, RegistroImagenEstacion dto) throws SQLException {
		try {
			initializeTimeConnSuppliedConn();

			ps = conn.prepareStatement(SQL_UPDATE);

			index = 1;
			ps.setInt(index++, dto.getCodImagenEstacion());
			ps.setInt(index++, pk.getCodRegistro());
			ps.setInt(index, pk.getCodImagen());
			System.out.println("Ejecutando " + SQL_UPDATE + " with DTO: " + dto);

			rows = ps.executeUpdate();

			finalizeTimeConnSuppliedConn();

		} finally {
			finallyTryCatch();
		}
	}

	public void delete(RegistroImagenEstacionPK pk) throws SQLException {
		try {
			initializeTimeConnSuppliedConn();

			ps = conn.prepareStatement(SQL_DELETE);

			index = 1;
			ps.setInt(index++, pk.getCodRegistro());
			ps.setInt(index, pk.getCodImagen());
			System.out.println("Ejecutando " + SQL_DELETE + " with DTO: " + pk);

			rows = ps.executeUpdate();

			finalizeTimeConnSuppliedConn();

		} finally {
			finallyTryCatch();
		}
	}

	public ObservableList<RegistroImagenEstacion> findAll() throws SQLException {
		return findByDynamicSelect(SQL_SELECT + " ORDER BY codregistro, codimagen", null);
	}

	public RegistroImagenEstacion findByPrimaryKey(RegistroImagenEstacionPK pk) throws SQLException {
		return findByPrimaryKey(pk.getCodRegistro(), pk.getCodImagen());
	}

	public RegistroImagenEstacion findByPrimaryKey(int codRegistro, int codImagen) throws SQLException {
		ObservableList<RegistroImagenEstacion> registrosImagenesEstaciones = findByDynamicSelect(
				SQL_SELECT + " WHERE codregistro = ? AND codimagen = ?",
				new Object[] { Integer.valueOf(codRegistro), Integer.valueOf(codImagen) });
		return registrosImagenesEstaciones.size() == 0 ? null : registrosImagenesEstaciones.get(0);
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

	private void populateDto(RegistroImagenEstacion dto, ResultSet rs) throws SQLException {
		dto.setCodRegistroEstacion(rs.getInt(COLUMN_CODREGISTRO));
		dto.setCodImagenEstacion(rs.getInt(COLUMN_CODIMAGEN));
	}

	private ObservableList<RegistroImagenEstacion> fetchMultiResults(ResultSet rs) throws SQLException {
		ObservableList<RegistroImagenEstacion> resultList = FXCollections.observableArrayList();
		while (rs.next()) {
			RegistroImagenEstacion dto = new RegistroImagenEstacion();
			populateDto(dto, rs);
			resultList.add(dto);
		}
		return resultList;
	}

	private ObservableList<RegistroImagenEstacion> findByDynamicSelect(String sql, Object[] sqlParams)
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
