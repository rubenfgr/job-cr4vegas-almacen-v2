package es.rfgr.cr4vegas.modelo.postgresql.bd.parteoficial;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

import es.rfgr.cr4vegas.modelo.postgresql.conexion.ConexionAlmacen;
import es.rfgr.cr4vegas.modelo.postgresql.conexion.ConexionOficina;
import es.rfgr.cr4vegas.modelo.postgresql.dto.parteoficial.ParteOficial;
import es.rfgr.cr4vegas.modelo.postgresql.dto.parteoficial.ParteOficialPK;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ParteOficialBD {

	private final String tableName = "public.ordendetrabajo";

	// SQL DML

	private final String SQL_SELECT = "SELECT * FROM " + getTableName();

	private final String SQL_INSERT = "INSERT INTO " + getTableName() + "(idordendetrabajo, idhidrante, fecha, "
			+ "tipo, descripcion, orden, estado, telefono, quien, contador, impresa)  VALUES (?, ?, ?, ?, ?, ?, ?"
			+ ", ?, ?, ?, ?)";

	private final String SQL_UPDATE = "UPDATE " + getTableName() + " SET idhidrante=?, tipo=?, descripcion=?, "
			+ "orden=?, estado=?, telefono=?, quien=?, contador=?, impresa=? WHERE idordendetrabajo=?";

	private final String SQL_DELETE = "DELETE FROM " + getTableName() + " WHERE idordendetrabajo=?";

	// COLUMNS

	private static final int COLUMN_IDORDENDETRABAJO = 1;

	private static final int COLUMN_IDHIDRANTE = 2;

	private static final int COLUMN_FECHA = 3;

	private static final int COLUMN_TIPO = 5;

	private static final int COLUMN_DESCRIPCION = 6;

	private static final int COLUMN_ORDEN = 7;

	private static final int COLUMN_ESTADO = 8;

	private static final int COLUMN_TELEFONO = 10;

	private static final int COLUMN_QUIEN = 11;

	private static final int COLUMN_CONTADOR = 12;

	private static final int COLUMN_IMPRESA = 13;

	private static final int PK_COLUMN_IDORDENDETRABAJO = 1;

	// CLASS VARS

	private int maxRows;

	private PreparedStatement ps;

	private Connection conn;

	private ResultSet rs;

	private long t1, t2;

	private int rows;

	private boolean isConnSupplied;

	private int index;

	private int maxCodParte;

	// BUILDERS

	public ParteOficialBD() {

	}

	// FUNCTIONS

	public ParteOficialPK insert(ParteOficial dto) throws SQLException {
		try {
			initializeTimeConnSuppliedConn();

			PreparedStatement psMaxCodParte = conn.prepareStatement("SELECT MAX(idordendetrabajo) FROM ordendetrabajo");
			ResultSet rsMaxCodParte = psMaxCodParte.executeQuery();

			if (rsMaxCodParte.next()) {
				int codParte = rsMaxCodParte.getInt(1) + 1;

				ps = conn.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS);
//				idhidrante, fecha, "tipo, descripcion, orden, estado, telefono, quien, contador, impresa
				index = 1;
				ps.setInt(index++, codParte);
				ps.setString(index++, dto.getIdHidrante());
				ps.setTimestamp(index++, Timestamp.valueOf(dto.getFecha()));
				ps.setInt(index++, dto.getObjTipoParte().getCodigo());
				ps.setString(index++, dto.getDescripcion());
				ps.setString(index++, dto.getOrden());
				ps.setInt(index++, dto.isEstado() ? 1 : 0);
				ps.setString(index++, dto.getTelefono());
				ps.setString(index++, dto.getLlamo());
				ps.setString(index++, dto.getContador());
				ps.setBoolean(index, dto.isImpreso());

				System.out.println("Ejecutando " + SQL_INSERT + " con DTO: " + dto);

				rows = ps.executeUpdate();

				// Obtener valores de las columnas auto-incrementables
				rs = ps.getGeneratedKeys();
				if (rs != null && rs.next()) {
					dto.setCodParte(rs.getInt(PK_COLUMN_IDORDENDETRABAJO));
				}

				finalizeTimeConnSuppliedConn();

				return dto.createPK();
			} else {
				throw new SQLException(
						"No se ha podido obtener el último ID desde la tabla de partes de la oficina, vuelva "
								+ "a intentarlo en otro momento, o contacte con el administrador");
			}
		} finally {
			finallyTryCatch();
		}
	}

	public void update(ParteOficialPK pk, ParteOficial dto) throws SQLException {
		try {
			initializeTimeConnSuppliedConn();

			ps = conn.prepareStatement(SQL_UPDATE);

			index = 1;
			ps.setString(index++, dto.getIdHidrante());
			ps.setInt(index++, dto.getObjTipoParte().getCodigo());
			ps.setString(index++, dto.getDescripcion());
			ps.setString(index++, dto.getOrden());
			ps.setInt(index++, dto.isEstado() ? 1 : 0);
			ps.setString(index++, dto.getTelefono());
			ps.setString(index++, dto.getLlamo());
			ps.setString(index++, dto.getContador());
			ps.setBoolean(index++, dto.isImpreso());
			ps.setInt(index, dto.getCodParte());
			System.out.println("Ejecutando " + SQL_UPDATE + " with DTO: " + dto);

			rows = ps.executeUpdate();

			finalizeTimeConnSuppliedConn();

		} finally {
			finallyTryCatch();
		}
	}

	public void delete(ParteOficialPK pk) throws SQLException {
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

	public ObservableList<ParteOficial> findAll() throws SQLException {
		return findByDynamicSelect(SQL_SELECT + " ORDER BY idordendetrabajo", null);
	}

	public ParteOficial findByPrimaryKey(ParteOficialPK pk) throws SQLException {
		return findByPrimaryKey(pk.getCodParte());
	}

	public ParteOficial findByPrimaryKey(int codParte) throws SQLException {
		ObservableList<ParteOficial> partes = findByDynamicSelect(SQL_SELECT + " WHERE idordendetrabajo = ?",
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

	public int getMaxCodParte() {
		return maxCodParte;
	}

	private void populateDto(ParteOficial dto, ResultSet rs) throws SQLException {
		dto.setCodParte(rs.getInt(COLUMN_IDORDENDETRABAJO));
		dto.setIdHidrante(rs.getString(COLUMN_IDHIDRANTE));
		dto.setFecha(rs.getTimestamp(COLUMN_FECHA).toLocalDateTime());
		dto.setTipo(rs.getInt(COLUMN_TIPO));
		dto.setDescripcion(rs.getString(COLUMN_DESCRIPCION));
		dto.setOrden(rs.getString(COLUMN_ORDEN));
		dto.setEstado(rs.getBoolean(COLUMN_ESTADO));
		dto.setTelefono(rs.getString(COLUMN_TELEFONO));
		dto.setLlamo(rs.getString(COLUMN_QUIEN));
		dto.setContador(rs.getString(COLUMN_CONTADOR));
		dto.setImpreso(rs.getBoolean(COLUMN_IMPRESA));
	}

	private ObservableList<ParteOficial> fetchMultiResults(ResultSet rs) throws SQLException {
		ObservableList<ParteOficial> resultList = FXCollections.observableArrayList();
		while (rs.next()) {
			ParteOficial dto = new ParteOficial();
			populateDto(dto, rs);
			if (dto.getCodParte() > this.maxCodParte) {
				this.maxCodParte = dto.getCodParte();
			}
			resultList.add(dto);
		}
		return resultList;
	}

	private ObservableList<ParteOficial> findByDynamicSelect(String sql, Object[] sqlParams) throws SQLException {
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

		return fetchMultiResults(rs);
	}

	private void finallyTryCatch() throws SQLException {
		if (ps != null) {
			ConexionAlmacen.close(ps);
		}
		if (rs != null) {
			ConexionAlmacen.close(rs);
		}
		if (conn != null) {
			ConexionAlmacen.close(conn);
		}
	}

	private void initializeTimeConnSuppliedConn() throws SQLException {
		t1 = System.currentTimeMillis();

		isConnSupplied = conn != null && !conn.isClosed();

		conn = isConnSupplied ? conn : ConexionOficina.getConexion();
	}

	private void finalizeTimeConnSuppliedConn() {
		t2 = System.currentTimeMillis();

		System.out.println(rows + " filas afectadas (" + (t2 - t1) + " ms)");
	}
}
