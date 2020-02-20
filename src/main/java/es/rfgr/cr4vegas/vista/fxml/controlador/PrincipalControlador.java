package es.rfgr.cr4vegas.vista.fxml.controlador;

import es.rfgr.cr4vegas.controlador.Consumer;
import es.rfgr.cr4vegas.modelo.postgresql.conexion.ConexionAlmacen;
import es.rfgr.cr4vegas.vista.utileria.Dialogos;
import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;

public class PrincipalControlador {

	@FXML
	private BorderPane panelRaiz;

	@FXML
	private MenuItem miSalir;

	@FXML
	private MenuItem miDesconectar;

	@FXML
	private MenuItem miAcercaDe;

	@FXML
	private MenuItem miGrupos;

	@FXML
	private MenuItem miTiendas;

	@FXML
	private MenuItem miUbicacion;

	@FXML
	private MenuItem miTipoMaterial;

	@FXML
	private MenuItem miFamilia;

	@FXML
	private MenuItem miTiposAlmacen;

	@FXML
	private MenuItem miBDOficina;

	@FXML
	private MenuItem miBDAlmacen;

	private Consumer app;

	public void setConsumer(Consumer app) {
		this.app = app;
	}

	public BorderPane getPanelRaiz() {
		return panelRaiz;
	}

	public void iniciar() {
		miSalir.setOnAction(e -> salir());
		miDesconectar.setOnAction(e -> desconectar());
		miFamilia.setOnAction(
				e -> app.getVistaModelo().getModeloVentana().getVentanaSimpleControlador().iniciarCon("familia"));
		miTipoMaterial.setOnAction(
				e -> app.getVistaModelo().getModeloVentana().getVentanaSimpleControlador().iniciarCon("tipoMaterial"));
		miUbicacion.setOnAction(
				e -> app.getVistaModelo().getModeloVentana().getVentanaSimpleControlador().iniciarCon("ubicacion"));
		miTiendas.setOnAction(e -> app.getVistaModelo().getModeloVentana().getVentanaTiendaControlador().iniciarCon());
		miGrupos.setOnAction(
				e -> app.getVistaModelo().getModeloVentana().getVentanaSimpleControlador().iniciarCon("grupo"));
		miTiposAlmacen.setOnAction(
				e -> app.getVistaModelo().getModeloVentana().getVentanaSimpleControlador().iniciarCon("tipoTaquilla"));
		miAcercaDe.setOnAction(e -> acercaDe());
		miBDAlmacen.setOnAction(e -> configurarBDAlmacen());
		miBDOficina.setOnAction(e -> configurarBDOficina());
	}

	void salir() {
		if (Dialogos.mostrarDialogoConfirmacion("¿Desea salir de la aplicación?",
				app.getVistaModelo().getEscenarioPrincipal())) {
			app.getVistaModelo().getEscenarioPrincipal().close();
		}
	}

	void desconectar() {
		ConexionAlmacen.reiniciarLogin();
		app.getVistaModelo().getModeloPrincipal().getPrincipalControlador().getPanelRaiz()
				.setCenter(app.getVistaModelo().getModeloPrincipal().getLoginControlador().getPanelRaiz());
	}

	void acercaDe() {

	}

	private void configurarBDOficina() {
		app.getVistaModelo().getModeloVentana().getVentanaConexionControlador().iniciarCon("oficina");
	}

	private void configurarBDAlmacen() {
		app.getVistaModelo().getModeloVentana().getVentanaConexionControlador().iniciarCon("almacen");
	}
}
