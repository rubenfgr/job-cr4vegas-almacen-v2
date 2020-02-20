package es.rfgr.cr4vegas.vista.fxml.controlador.principal;

import es.rfgr.cr4vegas.controlador.Consumer;
import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.layout.AnchorPane;

public class ModuloControlador {

	@FXML
	private AnchorPane panelRaiz;

	@FXML
	private Tab tabPartes;

	@FXML
	private Tab tabOperarios;

	@FXML
	private Tab tabEventos;

	@FXML
	private Tab tabInventario;

	@FXML
	private Tab tabMantenimiento;

	private Consumer app;

	public void setConsumer(Consumer app) {
		this.app = app;
	}
	
	public AnchorPane getPanelRaiz() {
		return panelRaiz;
	}
	
	public void iniciar() {
		iniciarModulos();
	}

	public void iniciarModulos() {
		tabPartes.setContent(app.getVistaModelo().getModeloPrincipal().getPartesControlador().getPanelRaiz());
		tabOperarios.setContent(app.getVistaModelo().getModeloPrincipal().getOperariosControlador().getPanelRaiz());
		tabEventos.setContent(app.getVistaModelo().getModeloPrincipal().getEventosControlador().getPanelRaiz());
		tabInventario.setContent(app.getVistaModelo().getModeloPrincipal().getInventarioControlador().getPanelRaiz());
		tabMantenimiento.setContent(app.getVistaModelo().getModeloPrincipal().getMantenimientoControlador().getPanelRaiz());
	}

}
