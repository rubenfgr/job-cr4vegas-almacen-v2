package es.rfgr.cr4vegas.modelo.postgresql.bd.operario;

import java.io.ByteArrayInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import es.rfgr.cr4vegas.modelo.postgresql.conexion.ConexionAlmacen;
import es.rfgr.cr4vegas.modelo.postgresql.dto.operario.Operario;
import es.rfgr.cr4vegas.modelo.postgresql.dto.operario.OperarioPK;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class OperarioBD {

	private Connection userConn;

	private final String tableName = "public.operario";

	// SQL DML

	private final String SQL_SELECT = "SELECT * FROM " + getTableName();

	private final String SQL_INSERT = "INSERT INTO " + getTableName() + " VALUES (?, ?, ?, ?, ?, ?, ?, "
			+ "?, ?, ?, ?, ? ,? ,?)";

	private final String SQL_UPDATE = "UPDATE " + getTableName() + " SET codop=?, activo=?, nombre=?, apellido1=?, "
			+ "apellido2=?, grupo=?, foto=?, tcalzado=?, tpantlargo=?, tpantcorto=?, tchaqueta=?, "
			+ "tchaqueton=?, tforro=?, tpolo=? WHERE codop=?";

	private final String SQL_DELETE = "DELETE FROM " + getTableName() + " WHERE codop=?";

	// COLUMNS

	private static final int COLUMN_CODOP = 1;

	private static final int COLUMN_ACTIVO = 2;

	private static final int COLUMN_NOMBRE = 3;

	private static final int COLUMN_APELLIDO1 = 4;

	private static final int COLUMN_APELLIDO2 = 5;

	private static final int COLUMN_GRUPO = 6;

	private static final int COLUMN_IMAGEN = 7;

	private static final int COLUMN_TCALZADO = 8;

	private static final int COLUMN_TPANTLARGO = 9;

	private static final int COLUMN_TPANTCORTO = 10;

	private static final int COLUMN_TCHAQUETA = 11;

	private static final int COLUMN_TCHAQUETON = 12;

	private static final int COLUMN_TFORRO = 13;

	private static final int COLUMN_TPOLO = 14;

	@SuppressWarnings("unused")
	private static final int PK_COLUMN_CODOP = 1;

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

	public OperarioBD() {

	}

	public void setConnection(Connection conn) {
		this.userConn = conn;
	}

	// FUNCTIONS

	public OperarioPK insert(Operario dto) throws SQLException {
		try {
			initializeTimeConnSuppliedConn();

			ps = conn.prepareStatement(SQL_INSERT);

			index = 1;
			ps.setInt(index++, dto.getCodOp());
			ps.setBoolean(index++, dto.isActivo());
			ps.setString(index++, dto.getNombre());
			ps.setString(index++, dto.getApellido1());
			ps.setString(index++, dto.getApellido2());
			ps.setString(index++, dto.getGrupo());
			if (dto.getImagenBytes() != null) {
				ps.setBinaryStream(index++, new ByteArrayInputStream(dto.getImagenBytes()));
			} else {
				ps.setNull(index++, Types.NULL);
			}
			ps.setString(index++, dto.getTCalzado());
			ps.setString(index++, dto.getTPantLargo());
			ps.setString(index++, dto.getTPantCorto());
			ps.setString(index++, dto.getTChaqueta());
			ps.setString(index++, dto.getTChaqueton());
			ps.setString(index++, dto.getTForro());
			ps.setString(index, dto.getTPolo());
			System.out.println("Ejecutando " + SQL_INSERT + " con DTO: " + dto);

			rows = ps.executeUpdate();

			finalizeTimeConnSuppliedConn();

			return dto.createPK();

		} finally {
			finallyTryCatch();
		}
	}

	public void update(OperarioPK pk, Operario dto) throws SQLException {
		try {
			initializeTimeConnSuppliedConn();

			ps = conn.prepareStatement(SQL_UPDATE);

			index = 1;
			ps.setInt(index++, dto.getCodOp());
			ps.setBoolean(index++, dto.isActivo());
			ps.setString(index++, dto.getNombre());
			ps.setString(index++, dto.getApellido1());
			ps.setString(index++, dto.getApellido2());
			ps.setString(index++, dto.getGrupo());
			ps.setBinaryStream(index++, new ByteArrayInputStream(dto.getImagenBytes()), dto.getImagenBytes().length);
			ps.setString(index++, dto.getTCalzado());
			ps.setString(index++, dto.getTPantLargo());
			ps.setString(index++, dto.getTPantCorto());
			ps.setString(index++, dto.getTChaqueta());
			ps.setString(index++, dto.getTChaqueton());
			ps.setString(index++, dto.getTForro());
			ps.setString(index++, dto.getTPolo());
			ps.setInt(index, pk.getCodOp());
			System.out.println("Ejecutando " + SQL_UPDATE + " with DTO: " + dto);

			rows = ps.executeUpdate();

			finalizeTimeConnSuppliedConn();

		} finally {
			finallyTryCatch();
		}
	}

	public void delete(OperarioPK pk) throws SQLException {
		try {
			initializeTimeConnSuppliedConn();

			ps = conn.prepareStatement(SQL_DELETE);

			ps.setInt(1, pk.getCodOp());
			System.out.println("Ejecutando " + SQL_DELETE + " with DTO: " + pk);

			rows = ps.executeUpdate();

			finalizeTimeConnSuppliedConn();

		} finally {
			finallyTryCatch();
		}
	}

	public ObservableList<Operario> findAll() throws SQLException {
		return findByDynamicSelect(SQL_SELECT + " ORDER BY codop", null);
	}

	public Operario findByPrimaryKey(OperarioPK pk) throws SQLException {
		return findByPrimaryKey(pk.getCodOp());
	}

	public Operario findByPrimaryKey(int codOp) throws SQLException {
		ObservableList<Operario> operarios = findByDynamicSelect(SQL_SELECT + " WHERE codop = ?",
				new Object[] { Integer.valueOf(codOp) });
		return operarios.size() == 0 ? null : operarios.get(0);
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

	private void populateDto(Operario dto, ResultSet rs) throws SQLException {
		dto.setCodOp(rs.getInt(COLUMN_CODOP));
		dto.setActivo(rs.getBoolean(COLUMN_ACTIVO));
		dto.setNombre(rs.getString(COLUMN_NOMBRE));
		dto.setApellido1(rs.getString(COLUMN_APELLIDO1));
		dto.setApellido2(rs.getString(COLUMN_APELLIDO2));
		dto.setGrupo(rs.getString(COLUMN_GRUPO));
		dto.setImagenBytes(rs.getBytes(COLUMN_IMAGEN));
		dto.setTCalzado(rs.getString(COLUMN_TCALZADO));
		dto.setTPantLargo(rs.getString(COLUMN_TPANTLARGO));
		dto.setTPantCorto(rs.getString(COLUMN_TPANTCORTO));
		dto.setTChaqueta(rs.getString(COLUMN_TCHAQUETA));
		dto.setTChaqueton(rs.getString(COLUMN_TCHAQUETON));
		dto.setTForro(rs.getString(COLUMN_TFORRO));
		dto.setTPolo(rs.getString(COLUMN_TPOLO));
	}

	private ObservableList<Operario> fetchMultiResults(ResultSet rs) throws SQLException {
		ObservableList<Operario> resultList = FXCollections.observableArrayList();
		while (rs.next()) {
			Operario dto = new Operario();
			populateDto(dto, rs);
			resultList.add(dto);
		}
		return resultList;
	}

	private ObservableList<Operario> findByDynamicSelect(String sql, Object[] sqlParams) throws SQLException {
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
