package es.rfgr.cr4vegas.modelo.postgresql.bd.mantenimiento.unidaddecontrol;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import es.rfgr.cr4vegas.modelo.postgresql.conexion.ConexionAlmacen;
import es.rfgr.cr4vegas.modelo.postgresql.dto.mantenimiento.unidaddecontrol.UnidadDeControl;
import es.rfgr.cr4vegas.modelo.postgresql.dto.mantenimiento.unidaddecontrol.UnidadDeControlPK;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class UnidadDeControlBD {

	private Connection userConn;

	private final String tableName = "public.udcontrol";

	// SQL DML

	private final String SQL_SELECT = "SELECT * FROM " + getTableName();

	private final String SQL_INSERT = "INSERT INTO " + getTableName() + " VALUES (0, ?, ?, ?, ?, ?, ?, ?, ?)";

	private final String SQL_UPDATE = "UPDATE " + getTableName() + " SET codinstalacion=?, activo=?, nombre=?, modelo=?"
			+ ", nota=?, normativa=?, mantenimiento=?, codimagen=? WHERE codudcontrol=?";

	private final String SQL_DELETE = "DELETE FROM " + getTableName() + " WHERE codudcontrol=?";

	// COLUMNS

	private static final int COLUMN_CODUDCONTROL = 1;

	private static final int COLUMN_CODINSTALACION = 2;

	private static final int COLUMN_ACTIVO = 3;

	private static final int COLUMN_NOMBRE = 4;

	private static final int COLUMN_MODELO = 5;

	private static final int COLUMN_NOTA = 6;

	private static final int COLUMN_NORMATIVA = 7;

	private static final int COLUMN_MANTENIMIENTO = 8;

	private static final int COLUMN_CODIMAGEN = 9;

	private static final int PK_COLUMN_CODUDCONTROL = 1;

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

	public UnidadDeControlBD() {

	}

	public void setConnection(Connection conn) {
		this.userConn = conn;
	}

	// FUNCTIONS

	public UnidadDeControlPK insert(UnidadDeControl dto) throws SQLException {
		try {
			initializeTimeConnSuppliedConn();

			ps = conn.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS);

			index = 1;
			ps.setInt(index++, dto.getCodInstalacion());
			ps.setBoolean(index++, dto.isActivo());
			ps.setString(index++, dto.getNombre());
			ps.setString(index++, dto.getModelo());
			ps.setString(index++, dto.getNota());
			ps.setString(index++, dto.getNormativa());
			ps.setString(index++, dto.getMantenimiento());
			ps.setInt(index, dto.getCodImagen());
			System.out.println("Ejecutando " + SQL_INSERT + " con DTO: " + dto);

			rows = ps.executeUpdate();

			finalizeTimeConnSuppliedConn();

			// Obtener valores de las columnas auto-incrementables
			rs = ps.getGeneratedKeys();
			if (rs != null && rs.next()) {
				dto.setCodUdControl(rs.getInt(PK_COLUMN_CODUDCONTROL));
			}

			return dto.createPK();

		} finally {
			finallyTryCatch();
		}
	}

	public void update(UnidadDeControlPK pk, UnidadDeControl dto) throws SQLException {
		try {
			initializeTimeConnSuppliedConn();

			ps = conn.prepareStatement(SQL_UPDATE);

			index = 1;
			ps.setInt(index++, dto.getCodInstalacion());
			ps.setBoolean(index++, dto.isActivo());
			ps.setString(index++, dto.getNombre());
			ps.setString(index++, dto.getModelo());
			ps.setString(index++, dto.getNota());
			ps.setString(index++, dto.getNormativa());
			ps.setString(index++, dto.getMantenimiento());
			ps.setInt(index++, dto.getCodImagen());
			ps.setInt(index, pk.getCodUDControl());
			System.out.println("Ejecutando " + SQL_UPDATE + " with DTO: " + dto);

			rows = ps.executeUpdate();

			finalizeTimeConnSuppliedConn();

		} finally {
			finallyTryCatch();
		}
	}

	public void delete(UnidadDeControlPK pk) throws SQLException {
		try {
			initializeTimeConnSuppliedConn();

			System.out.println("Ejecutando " + SQL_DELETE + " with DTO: " + pk);
			ps = conn.prepareStatement(SQL_DELETE);

			ps.setInt(1, pk.getCodUDControl());

			rows = ps.executeUpdate();

			finalizeTimeConnSuppliedConn();

		} finally {
			finallyTryCatch();
		}
	}

	public ObservableList<UnidadDeControl> findAll() throws SQLException {
		return findByDynamicSelect(SQL_SELECT + " ORDER BY codudcontrol", null);
	}
	
	public UnidadDeControl findByPrimaryKey(UnidadDeControlPK pk) throws SQLException {
		return findByPrimaryKey( pk.getCodUDControl() );
	}

	public UnidadDeControl findByPrimaryKey(int codUDControl) throws SQLException {
		ObservableList<UnidadDeControl> uDSControl = findByDynamicSelect( SQL_SELECT + " WHERE codudcontrol = ?", new Object[] {  Integer.valueOf(codUDControl) } );
		return uDSControl.size() == 0 ? null : uDSControl.get(0);
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

	private void populateDto(UnidadDeControl dto, ResultSet rs) throws SQLException {
		dto.setCodUdControl(rs.getInt(COLUMN_CODUDCONTROL));
		dto.setCodInstalacion(rs.getInt(COLUMN_CODINSTALACION));
		dto.setActivo(rs.getBoolean(COLUMN_ACTIVO));
		dto.setNombre(rs.getString(COLUMN_NOMBRE));
		dto.setModelo(rs.getString(COLUMN_MODELO));
		dto.setNota(rs.getString(COLUMN_NOTA));
		dto.setNormativa(rs.getString(COLUMN_NORMATIVA));
		dto.setMantenimiento(rs.getString(COLUMN_MANTENIMIENTO));
		dto.setCodImagen(rs.getInt(COLUMN_CODIMAGEN));
	}

	private ObservableList<UnidadDeControl> fetchMultiResults(ResultSet rs) throws SQLException {
		ObservableList<UnidadDeControl> resultList = FXCollections.observableArrayList();
		while (rs.next()) {
			UnidadDeControl dto = new UnidadDeControl();
			populateDto(dto, rs);
			resultList.add(dto);
		}
		return resultList;
	}

	private ObservableList<UnidadDeControl> findByDynamicSelect(String sql, Object[] sqlParams) throws SQLException {
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
