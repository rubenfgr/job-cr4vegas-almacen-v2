package es.rfgr.cr4vegas.vista.fxml.controlador.ventana.principal;

import java.sql.SQLException;

import es.rfgr.cr4vegas.controlador.Consumer;
import es.rfgr.cr4vegas.modelo.postgresql.conexion.ConexionAlmacen;
import es.rfgr.cr4vegas.modelo.postgresql.conexion.ConexionOficina;
import es.rfgr.cr4vegas.vista.utileria.Dialogos;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class VentanaConexionControlador {

	@FXML
	private Button btAceptar;

	@FXML
	private AnchorPane panelRaiz;

	@FXML
	private PasswordField pfPass;

	@FXML
	private TextField tfPuerto;

	@FXML
	private TextField tfNombreBD;

	@FXML
	private Button btComprobar;

	@FXML
	private TextField tfUsuario;

	@FXML
	private TextField tfIP;

	@FXML
	private Button btCancelar;

	public void iniciar() {
		btComprobar.setOnAction(e -> comprobarConexion());
		btAceptar.setOnAction(e -> aceptar());
		btCancelar.setOnAction(e -> salir());
	}

	private Consumer app;
	private String tipoConexion;
	private Stage escenarioVentana;
	private String ip;
	private String puerto;
	private String nombreBD;
	private String usuario;
	private String pass;

	public void setConsumer(Consumer app) {
		this.app = app;
	}

	public AnchorPane getPanelRaiz() {
		return panelRaiz;
	}

	public void iniciarCon(String tipoConexion) {
		this.tipoConexion = tipoConexion;

		if (tipoConexion.equals("almacen")) {
			tfIP.setText(ConexionAlmacen.getIP() == null ? "" : ConexionAlmacen.getIP());
			tfPuerto.setText(ConexionAlmacen.getPuerto() == null ? "" : ConexionAlmacen.getPuerto());
			tfNombreBD.setText(ConexionAlmacen.getNombreBD() == null ? "" : ConexionAlmacen.getNombreBD());
		}
		if (tipoConexion.equals("oficina")) {
			tfIP.setText(ConexionOficina.getIP() == null ? "" : ConexionAlmacen.getIP());
			tfPuerto.setText(ConexionOficina.getPuerto() == null ? "" : ConexionAlmacen.getPuerto());
			tfNombreBD.setText(ConexionOficina.getNombreBD() == null ? "" : ConexionAlmacen.getNombreBD());
		}

		escenarioVentana = app.getVistaModelo().getEscenario(panelRaiz, "Configuración conexión " + tipoConexion);
		escenarioVentana.showAndWait();
	}

	private void comprobarConexion() {
		cargarEstado();
		if (tipoConexion.equals("almacen")) {
			comprobarConexionAlmacen();
		}
		if (tipoConexion.equals("oficina")) {
			comprobarConexionOficina();
		}
	}

	private void comprobarConexionAlmacen() {
		try {
			if (ConexionAlmacen.comprobarConexion(ip, puerto, nombreBD, usuario, pass)) {
				Dialogos.mostrarDialogoInformacion(
						"Los datos son correctos. Se realizo la conexión con la BD del Almacen.", escenarioVentana);
			}
		} catch (SQLException e) {
			Dialogos.mostrarDialogoExcepcion(e, escenarioVentana);
		}
	}

	private void comprobarConexionOficina() {
		try {
			if (ConexionOficina.comprobarConexion(ip, puerto, nombreBD, usuario, pass)) {
				Dialogos.mostrarDialogoInformacion(
						"Los datos son correctos. Se realizo la conexión con la BD de la Oficina.", escenarioVentana);
			}
		} catch (SQLException e) {
			Dialogos.mostrarDialogoExcepcion(e, escenarioVentana);
		}
	}

	private void aceptar() {
		cargarEstado();
		if (tipoConexion.equals("almacen")) {
			aceptarConexionAlmacen();
		}
		if (tipoConexion.equals("oficina")) {
			aceptarConexionOficina();
		}
	}

	private void aceptarConexionAlmacen() {
		try {
			if (ConexionAlmacen.comprobarConexion(ip, puerto, nombreBD, usuario, pass)) {
				Dialogos.mostrarDialogoInformacion("Los datos de la conexión se guardarón con éxito.",
						escenarioVentana);
				ConexionAlmacen.setConexion(ip, puerto, nombreBD);
				
				salir();
			}
		} catch (SQLException e) {
			Dialogos.mostrarDialogoInformacion("Datos incorrectos, la conexión no se realizó.",
					escenarioVentana);
		}
	}

	private void aceptarConexionOficina() {
		try {
			if (ConexionOficina.comprobarConexion(ip, puerto, nombreBD, usuario, pass)) {
				Dialogos.mostrarDialogoInformacion("Los datos de la conexión se guardarón con éxito.",
						escenarioVentana);
				ConexionOficina.setConexion(ip, puerto, nombreBD);
				
				salir();
			}
		} catch (SQLException e) {
			Dialogos.mostrarDialogoInformacion("Datos incorrectos, la conexión no se realizó.",
					escenarioVentana);
		}
	}

	private void salir() {
		reiniciar();
		escenarioVentana.close();
	}

	private void cargarEstado() {
		ip = tfIP.getText();
		puerto = tfPuerto.getText();
		nombreBD = tfNombreBD.getText();
		usuario = tfUsuario.getText();
		pass = pfPass.getText();
	}

	private void reiniciar() {
		tfIP.setText("");
		tfPuerto.setText("");
		tfNombreBD.setText("");
		tfUsuario.setText("");
		pfPass.setText("");
	}
}
