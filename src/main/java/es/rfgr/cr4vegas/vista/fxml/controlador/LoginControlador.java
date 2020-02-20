package es.rfgr.cr4vegas.vista.fxml.controlador;

import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;

import es.rfgr.cr4vegas.controlador.Consumer;
import es.rfgr.cr4vegas.modelo.postgresql.conexion.ConexionAlmacen;
import es.rfgr.cr4vegas.modelo.postgresql.conexion.ConexionOficina;
import es.rfgr.cr4vegas.modelo.postgresql.exceptions.DAOException;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class LoginControlador implements Initializable {

	private static final String TEXT_FILL_RED = "-fx-text-fill: red";
	private static final String TEXT_FILL_GREEN = "-fx-text-fill: green";

	@FXML
	private AnchorPane panelRaiz;

	@FXML
	private Button btEntrar;

	@FXML
	private PasswordField tfContrasena;

	@FXML
	private Label lbMensaje;

	@FXML
	private TextField tfUsuario;

	@FXML
	void handleEntrar() {
		try {
//			Conexion.setLogin(tfUsuario.getText(), tfContrasena.getText());
			
			Connection conn = ConexionAlmacen.getConexion();
			lbMensaje.setText("Datos correctos. Cargando...");
			msjGreen();
			app.iniciarConexion(conn);

			Platform.runLater(() -> {
				app.getVistaModelo().getModeloPrincipal().getPrincipalControlador().getPanelRaiz()
						.setCenter(app.getVistaModelo().getModeloPrincipal().getModulosControlador().getPanelRaiz());
				app.getVistaModelo().getModeloPrincipal().getPrincipalControlador().getPanelRaiz().setCenterShape(true);
				tfUsuario.setText("");
				tfContrasena.setText("");
				msjRed();
				lbMensaje.setText("---");
			});

		} catch (DAOException e) {
			msjRed();
			lbMensaje.setText(e.getMessage());
		} catch (SQLException e) {
			e.printStackTrace();
			msjRed();
			lbMensaje.setText("Datos incorrectos. Vuelva ha intentarlo");
		}
	}

	private Consumer app;

	public void setConsumer(Consumer app) {
		this.app = app;
	}

	public AnchorPane getPanelRaiz() {
		return panelRaiz;
	}

	public void iniciar() {
		// TODO Auto-generated method stub

	}

	private void msjGreen() {
		lbMensaje.setStyle(TEXT_FILL_GREEN);
	}

	private void msjRed() {
		lbMensaje.setStyle(TEXT_FILL_RED);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		ConexionAlmacen.iniciar();
		ConexionOficina.iniciar();
	}

}
