package es.rfgr.cr4vegas.modelo.postgresql.conexion;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import java.util.ResourceBundle;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;

import javafx.fxml.Initializable;

public class ConexionOficina implements Initializable {

	private static final String ERROR = "ConexionOficina: ";

	private static String JDBC_DRIVER;
	private static String JDBC_URL;
	private static String JDBC_IP;
	private static String JDBC_PUERTO;
	private static String JDBC_NOMBRE_BD;
	private static String JDBC_USER;
	private static String JDBC_PASSWORD;
	private static String JDBC_FILE_NAME = "es/rfgr/cr4vegas/modelo/postgresql/conexion/jdbcOficina.properties";

	private static Properties prop = new Properties();

	private static BasicDataSource dataSource;
	private static BasicDataSource dataSourceComprobaciones;

	public static void iniciar() {
		loadProperties();
	}

	private static void loadProperties() {
		try {
			prop.load(ConexionOficina.class.getClassLoader().getResourceAsStream(JDBC_FILE_NAME));
		} catch (IOException e) {
			e.printStackTrace();
		}
		JDBC_DRIVER = prop.getProperty("driver");
		JDBC_IP = prop.getProperty("ip");
		JDBC_PUERTO = prop.getProperty("port");
		JDBC_NOMBRE_BD = prop.getProperty("db");
		JDBC_USER = prop.getProperty("user");
		JDBC_PASSWORD = prop.getProperty("pass");
	}

	public static synchronized Connection getConexion() throws SQLException {
		try {
			return getDataSource().getConnection();

		} catch (SQLException e) {
			throw new SQLException(ERROR + e.getMessage(), e);
		}
	}

	private static DataSource getDataSource() {
		if (dataSource == null) {
			dataSource = new BasicDataSource();

			// Conexiones iniciales
			dataSource.setInitialSize(5);
		}
		dataSource.setDriverClassName(JDBC_DRIVER);

		JDBC_URL = "jdbc:postgresql://" + JDBC_IP + ":" + JDBC_PUERTO + "/" + JDBC_NOMBRE_BD;

		dataSource.setUrl(JDBC_URL);

		dataSource.setUsername(JDBC_USER);
		dataSource.setPassword(JDBC_PASSWORD);
		return dataSource;
	}

	public static void reiniciarLogin() {
		prop.setProperty("user", "");
		JDBC_USER = "";
		prop.setProperty("pass", "");
		JDBC_PASSWORD = "";
	}

	public static String getIP() {
		return JDBC_IP;
	}

	public static String getPuerto() {
		return JDBC_PUERTO;
	}

	public static String getNombreBD() {
		return JDBC_NOMBRE_BD;
	}

	public static void setConexion(String ip, String puerto, String nombreBD) throws SQLException {
		setIP(ip);
		setPuerto(puerto);
		setNombreBD(nombreBD);
	}

	public static void setConexion(String usuario, String pass) throws SQLException {
		setUsuario(usuario);
		setPass(pass);
	}

	public static boolean comprobarConexion(String ip, String puerto, String nombreBD, String usuario, String pass)
			throws SQLException {
		if (comprobarIP(ip) && comprobarPuerto(puerto) && comprobarNombreBD(nombreBD) && comprobarUsuario(usuario)
				&& comprobarPass(pass)) {
			if (dataSourceComprobaciones == null) {
				dataSourceComprobaciones = new BasicDataSource();
				dataSourceComprobaciones.setInitialSize(1);
			}
			dataSourceComprobaciones.setDriverClassName(JDBC_DRIVER);

			String url = "jdbc:postgresql://" + ip + ":" + puerto + "/" + nombreBD;

			dataSourceComprobaciones.setUrl(url);

			dataSourceComprobaciones.setUsername(usuario);
			dataSourceComprobaciones.setPassword(pass);
			Connection conn = dataSourceComprobaciones.getConnection();
			
			if (conn != null) {
				return true;
			}
		}
		return false;
	}

	private static void setIP(String ip) throws SQLException {
		if (comprobarIP(ip)) {
			prop.setProperty("ip", ip);
			JDBC_IP = ip;
		}
	}

	private static void setPuerto(String puerto) throws SQLException {
		if (comprobarPuerto(puerto)) {
			prop.setProperty("port", puerto);
			JDBC_PUERTO = puerto;
		}
	}

	private static void setNombreBD(String nombreBD) throws SQLException {
		if (comprobarNombreBD(nombreBD)) {
			prop.setProperty("db", nombreBD);
			JDBC_NOMBRE_BD = nombreBD;
		}
	}

	private static void setUsuario(String usuario) throws SQLException {
		if (comprobarNombreBD(usuario)) {
			prop.setProperty("user", usuario);
			JDBC_USER = usuario;
		}
	}

	private static void setPass(String pass) throws SQLException {
		if (comprobarNombreBD(pass)) {
			prop.setProperty("pass", pass);
			JDBC_PASSWORD = pass;
		}
	}

	private static boolean comprobarIP(String ip) throws SQLException {
		if (ip == null || ip.equals("")) {
			throw new SQLException("La ip no puede ser nula ni estar vacía.");
		}
		if (!ip.matches("[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}")) {
			throw new SQLException("La ip no es correcta (Ej: 192.168.X.X");
		}
		return true;
	}

	private static boolean comprobarPuerto(String puerto) throws SQLException {
		if (puerto == null || puerto.equals("")) {
			throw new SQLException("El puerto no puede ser nulo ni estar vacío.");
		}
		if (!puerto.matches("[0-9]{4}")) {
			throw new SQLException("El puerto no es correcto (Ej: 4242)");
		}
		return true;
	}

	private static boolean comprobarNombreBD(String nombreBD) throws SQLException {
		if (nombreBD == null || nombreBD.equals("")) {
			throw new SQLException("El nombre de la base de datos no puede ser nulo ni estar vacío");
		}
		return true;
	}

	private static boolean comprobarUsuario(String usuario) throws SQLException {
		if (usuario == null || usuario.equals("")) {
			throw new SQLException("El usuario de la base de datos no puede ser nulo ni estar vacío");
		}
		return true;
	}

	private static boolean comprobarPass(String pass) throws SQLException {
		if (pass == null || pass.equals("")) {
			throw new SQLException("La contraseña de la base de datos no puede ser nula ni estar vacía");
		}
		return true;
	}

	/**
	 * Métodos para cerrar los recursos utilizados en la conexión
	 * 
	 */
	public static void close(Connection conn) throws SQLException {
		try {
			if (conn != null) {
				conn.close();
			}
		} catch (SQLException e) {
			throw new SQLException(ERROR + "Ocurrio un error al intentar cerrar un Connection. " + e.getMessage(), e);
		}
	}

	public static void close(PreparedStatement stmt) throws SQLException {
		try {
			if (stmt != null)
				stmt.close();
		} catch (SQLException e) {
			throw new SQLException(
					ERROR + "Ocurrio un error al intentar cerrar un PreparedStatement. " + e.getMessage(), e);
		}
	}

	public static void close(ResultSet rs) throws SQLException {
		try {
			if (rs != null)
				rs.close();
		} catch (SQLException e) {
			throw new SQLException(ERROR + "Ocurrio un error al intentar cerrar un ResultSet. " + e.getMessage(), e);
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub

	}
}
