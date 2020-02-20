package es.rfgr.cr4vegas.vista.fxml.controlador.principal.modulo.parte;

import es.rfgr.cr4vegas.controlador.Consumer;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;

public class ParteInformacionControlador {

	@FXML
	private AnchorPane panelRaiz;

	private Consumer app;

	public void setConsumer(Consumer app) {
		this.app = app;
	}

	public AnchorPane getPanelRaiz() {
		return panelRaiz;
	}

	public void iniciar() {
		
		// ELIMINAR...
		app.getVistaModelo();

	}
}
