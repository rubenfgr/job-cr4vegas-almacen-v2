package es.rfgr.cr4vegas.modelo.postgresql.bd.mantenimiento.vehiculo;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import es.rfgr.cr4vegas.modelo.postgresql.conexion.ConexionAlmacen;
import es.rfgr.cr4vegas.modelo.postgresql.dto.mantenimiento.vehiculo.Vehiculo;
import es.rfgr.cr4vegas.modelo.postgresql.dto.mantenimiento.vehiculo.VehiculoPK;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class VehiculoBD {

	private Connection userConn;

	private final String tableName = "public.vehiculo";

	// SQL DML

	private final String SQL_SELECT = "SELECT * FROM " + getTableName();

	private final String SQL_INSERT = "INSERT INTO " + getTableName() + " VALUES (0, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

	private final String SQL_UPDATE = "UPDATE " + getTableName() + " SET activo=?, modelo=?, marca=?, numbastidor=?"
			+ ", fechamatriculacion=?, km=?, fechaitv=?, itv=?, nota=?, codimagen=? WHERE matricula=?";

	private final String SQL_DELETE = "DELETE FROM " + getTableName() + " WHERE matricula=?";

	// COLUMNS

	private static final int COLUMN_MATRICULA = 1;

	private static final int COLUMN_ACTIVO = 2;

	private static final int COLUMN_MODELO = 3;

	private static final int COLUMN_MARCA = 4;

	private static final int COLUMN_NUMBASTIDOR = 5;

	private static final int COLUMN_FECHAMATRICULACION = 6;

	private static final int COLUMN_KM = 7;

	private static final int COLUMN_FECHAITV = 8;

	private static final int COLUMN_ITV = 9;

	private static final int COLUMN_NOTA = 10;

	private static final int COLUMN_CODIMAGEN = 11;

	@SuppressWarnings("unused")
	private static final int PK_COLUMN_MATRICULA = 1;

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

	public VehiculoBD() {

	}

	public void setConnection(Connection conn) {
		this.userConn = conn;
	}

	// FUNCTIONS

	public VehiculoPK insert(Vehiculo dto) throws SQLException {
		try {
			initializeTimeConnSuppliedConn();

			ps = conn.prepareStatement(SQL_INSERT);

			index = 1;
			ps.setBoolean(index++, dto.isActivo());
			ps.setString(index++, dto.getModelo());
			ps.setString(index++, dto.getMarca());
			ps.setString(index++, dto.getNumBastidor());
			ps.setDate(index++, Date.valueOf(dto.getFechaMatriculacion()));
			ps.setInt(index++, dto.getKm());
			ps.setDate(index++, Date.valueOf(dto.getFechaItv()));
			ps.setBoolean(index++, dto.isItv());
			ps.setString(index++, dto.getNota());
			ps.setInt(index, dto.getCodImagen());
			System.out.println("Ejecutando " + SQL_INSERT + " con DTO: " + dto);

			rows = ps.executeUpdate();

			finalizeTimeConnSuppliedConn();

			return dto.createPK();

		} finally {
			finallyTryCatch();
		}
	}

	public void update(VehiculoPK pk, Vehiculo dto) throws SQLException {
		try {
			initializeTimeConnSuppliedConn();

			ps = conn.prepareStatement(SQL_UPDATE);

			index = 1;
			ps.setBoolean(index++, dto.isActivo());
			ps.setString(index++, dto.getModelo());
			ps.setString(index++, dto.getMarca());
			ps.setString(index++, dto.getNumBastidor());
			ps.setDate(index++, Date.valueOf(dto.getFechaMatriculacion()));
			ps.setInt(index++, dto.getKm());
			ps.setDate(index++, Date.valueOf(dto.getFechaItv()));
			ps.setBoolean(index++, dto.isItv());
			ps.setString(index++, dto.getNota());
			ps.setInt(index++, dto.getCodImagen());
			ps.setString(index, pk.getMatricula());
			System.out.println("Ejecutando " + SQL_UPDATE + " with DTO: " + dto);

			rows = ps.executeUpdate();

			finalizeTimeConnSuppliedConn();

		} finally {
			finallyTryCatch();
		}
	}

	public void delete(VehiculoPK pk) throws SQLException {
		try {
			initializeTimeConnSuppliedConn();

			ps = conn.prepareStatement(SQL_DELETE);

			ps.setString(1, pk.getMatricula());
			System.out.println("Ejecutando " + SQL_DELETE + " with DTO: " + pk);

			rows = ps.executeUpdate();

			finalizeTimeConnSuppliedConn();

		} finally {
			finallyTryCatch();
		}
	}

	public ObservableList<Vehiculo> findAll() throws SQLException {
		return findByDynamicSelect(SQL_SELECT + " ORDER BY matricula", null);
	}
	
	public Vehiculo findByPrimaryKey(VehiculoPK pk) throws SQLException {
		return findByPrimaryKey( pk.getMatricula() );
	}

	public Vehiculo findByPrimaryKey(String matricula) throws SQLException {
		ObservableList<Vehiculo> vehiculos = findByDynamicSelect( SQL_SELECT + " WHERE matricula = ?", new Object[] {  matricula } );
		return vehiculos.size() == 0 ? null : vehiculos.get(0);
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

	private void populateDto(Vehiculo dto, ResultSet rs) throws SQLException {
		dto.setMatricula(rs.getString(COLUMN_MATRICULA));
		dto.setActivo(rs.getBoolean(COLUMN_ACTIVO));
		dto.setModelo(rs.getString(COLUMN_MODELO));
		dto.setMarca(rs.getString(COLUMN_MARCA));
		dto.setNumBastidor(rs.getString(COLUMN_NUMBASTIDOR));
		dto.setFechaMatriculacion(rs.getDate(COLUMN_FECHAMATRICULACION).toLocalDate());
		dto.setKm(rs.getInt(COLUMN_KM));
		dto.setFechaItv(rs.getDate(COLUMN_FECHAITV).toLocalDate());
		dto.setItv(rs.getBoolean(COLUMN_ITV));
		dto.setNota(rs.getString(COLUMN_NOTA));
		dto.setCodImagen(rs.getInt(COLUMN_CODIMAGEN));
	}

	private ObservableList<Vehiculo> fetchMultiResults(ResultSet rs) throws SQLException {
		ObservableList<Vehiculo> resultList = FXCollections.observableArrayList();
		while (rs.next()) {
			Vehiculo dto = new Vehiculo();
			populateDto(dto, rs);
			resultList.add(dto);
		}
		return resultList;
	}

	private ObservableList<Vehiculo> findByDynamicSelect(String sql, Object[] sqlParams) throws SQLException {
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
