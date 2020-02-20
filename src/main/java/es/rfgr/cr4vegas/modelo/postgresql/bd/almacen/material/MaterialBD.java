package es.rfgr.cr4vegas.modelo.postgresql.bd.almacen.material;

import java.io.ByteArrayInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import es.rfgr.cr4vegas.modelo.postgresql.conexion.ConexionAlmacen;
import es.rfgr.cr4vegas.modelo.postgresql.dto.almacen.material.Material;
import es.rfgr.cr4vegas.modelo.postgresql.dto.almacen.material.MaterialPK;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class MaterialBD {

	private Connection userConn;

	private final String tableName = "public.material";

	// SQL DML

	private final String SQL_SELECT = "SELECT * FROM " + getTableName();

	private final String SQL_INSERT = "INSERT INTO " + getTableName()
			+ " (activo, nombre, nombretec, cantidad, cantidadmin,"
			+ "ubicacion, material, familia, diametro, pn, marca, imagen) VALUES (?, ?, ?, "
			+ "?, ?, ?, ?, ?, ?, ?, ?, ?)";

	private final String SQL_UPDATE = "UPDATE " + getTableName() + " SET activo=?, nombre=?, "
			+ "nombretec=?, cantidad=?, cantidadmin=?, ubicacion=?, material=?, familia=?, diametro=?,"
			+ "pn=?, marca=?, imagen=? WHERE codigo4v=?";

	private final String SQL_DELETE = "DELETE FROM " + getTableName() + " WHERE codigo4v=?";

	// COLUMNS

	private static final int COLUMN_CODIGO4V = 1;

	private static final int COLUMN_ACTIVO = 2;

	private static final int COLUMN_NOMBRE = 3;

	private static final int COLUMN_NOMBRETEC = 4;

	private static final int COLUMN_CANTIDAD = 5;

	private static final int COLUMN_CANTIDADMIN = 6;

	private static final int COLUMN_UBICACION = 7;

	private static final int COLUMN_MATERIAL = 8;

	private static final int COLUMN_FAMILIA = 9;

	private static final int COLUMN_DIAMETRO = 10;

	private static final int COLUMN_PN = 11;

	private static final int COLUMN_MARCA = 12;

	private static final int COLUMN_IMAGEN = 13;

	private static final int PK_COLUMN_CODIGO4V = 1;

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

	public MaterialBD() {

	}

	public void setConnection(Connection conn) {
		this.userConn = conn;
	}

	public MaterialPK insert(Material dto) throws SQLException {
		try {
			initializeTimeConnSuppliedConn();

			ps = conn.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS);

			index = 1;
			ps.setBoolean(index++, dto.isActivo());
			ps.setString(index++, dto.getNombre());
			ps.setString(index++, dto.getNombreTec());
			ps.setInt(index++, dto.getCantidad());
			ps.setInt(index++, dto.getCantidadMin());
			ps.setString(index++, dto.getUbicacion());
			ps.setString(index++, dto.getMaterial());
			ps.setString(index++, dto.getFamilia());
			ps.setString(index++, dto.getDiametro());
			ps.setString(index++, dto.getPn());
			ps.setString(index++, dto.getMarca());
			insertImage(dto, index);
			System.out.println("Ejecutando " + SQL_INSERT + " con DTO: " + dto);

			rows = ps.executeUpdate();

			finalizeTimeConnSuppliedConn();

			// Obtener valores de las columnas-incrementables
			rs = ps.getGeneratedKeys();
			if (rs != null && rs.next()) {
				dto.setCodigo4v(rs.getInt(PK_COLUMN_CODIGO4V));
			}

			return dto.createPK();

		} finally {
			finallyTryCatch();
		}
	}

	private void insertImage(Material dto, int index) throws SQLException {
		if (dto.getImagenBytes() != null) {
			ps.setBinaryStream(index, new ByteArrayInputStream(dto.getImagenBytes()), dto.getImagenBytes().length);
		} else {
			ps.setNull(index, java.sql.Types.NULL);
		}
	}

	public void update(MaterialPK pk, Material dto) throws SQLException {
		try {
			initializeTimeConnSuppliedConn();

			ps = conn.prepareStatement(SQL_UPDATE);

//			private final String SQL_UPDATE = "UPDATE " + getTableName() + " SET activo=?, nombre=?, "
//					+ "nombretec=?, cantidad=?, cantidadmin=?, ubicacion=?, material=?, familia=?, diametro=?,"
//					+ "pn=?, marca=?, imagen=? WHERE codigo4v=?";
			
			index = 1;
			ps.setBoolean(index++, dto.isActivo());
			ps.setString(index++, dto.getNombre());
			ps.setString(index++, dto.getNombreTec());
			ps.setInt(index++, dto.getCantidad());
			ps.setInt(index++, dto.getCantidadMin());
			ps.setString(index++, dto.getUbicacion());
			ps.setString(index++, dto.getMaterial());
			ps.setString(index++, dto.getFamilia());
			ps.setString(index++, dto.getDiametro());
			ps.setString(index++, dto.getPn());
			ps.setString(index++, dto.getMarca());
			insertImage(dto, index++);
			ps.setInt(index, pk.getCodigo4v());
			System.out.println("Ejecutando " + SQL_UPDATE + " with DTO: " + dto);

			rows = ps.executeUpdate();

			finalizeTimeConnSuppliedConn();

		} finally {
			finallyTryCatch();
		}
	}

	public void delete(MaterialPK dto) throws SQLException {
		try {
			initializeTimeConnSuppliedConn();

			ps = conn.prepareStatement(SQL_DELETE);

			ps.setInt(1, dto.getCodigo4v());
			System.out.println("Ejecutando " + SQL_DELETE + " with DTO: " + dto);

			rows = ps.executeUpdate();

			finalizeTimeConnSuppliedConn();

		} finally {
			finallyTryCatch();
		}
	}

	public ObservableList<Material> findAll() throws SQLException {
		return findByDynamicSelect(SQL_SELECT + " ORDER BY codigo4v", null);
	}

	public void updateDTOMaterial(Material material) throws SQLException {
		Material materialActualizado = findByPrimaryKey(material.createPK());
		if (materialActualizado != null) {
			material.setCantidad(materialActualizado.getCantidad());
		}
	}

	public Material findByPrimaryKey(MaterialPK pk) throws SQLException {
		return findByPrimaryKey(pk.getCodigo4v());
	}

	public Material findByPrimaryKey(int codigo4v) throws SQLException {
		ObservableList<Material> materiales = findByDynamicSelect(SQL_SELECT + " WHERE codigo4v = ?",
				new Object[] { Integer.valueOf(codigo4v) });
		return materiales.size() == 0 ? null : materiales.get(0);
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

	private void populateDto(Material dto, ResultSet rs) throws SQLException {
		dto.setCodigo4v(rs.getInt(COLUMN_CODIGO4V));
		dto.setActivo(rs.getBoolean(COLUMN_ACTIVO));
		dto.setNombre(rs.getString(COLUMN_NOMBRE));
		dto.setNombreTec(rs.getString(COLUMN_NOMBRETEC));
		dto.setCantidad(rs.getInt(COLUMN_CANTIDAD));
		dto.setCantidadMin(rs.getInt(COLUMN_CANTIDADMIN));
		dto.setUbicacion(rs.getString(COLUMN_UBICACION));
		dto.setMaterial(rs.getString(COLUMN_MATERIAL));
		dto.setFamilia(rs.getString(COLUMN_FAMILIA));
		dto.setDiametro(rs.getString(COLUMN_DIAMETRO));
		dto.setPn(rs.getString(COLUMN_PN));
		dto.setMarca(rs.getString(COLUMN_MARCA));
		dto.setImagenBytes(rs.getBytes(COLUMN_IMAGEN));
	}

	private ObservableList<Material> fetchMultiResults(ResultSet rs) throws SQLException {
		ObservableList<Material> resultList = FXCollections.observableArrayList();
		while (rs.next()) {
			Material dto = new Material();
			populateDto(dto, rs);
			resultList.add(dto);
		}
		return resultList;
	}

	private ObservableList<Material> findByDynamicSelect(String sql, Object[] sqlParams) throws SQLException {
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
