package es.rfgr.cr4vegas.vista.fxml.controlador.principal.modulo;

import es.rfgr.cr4vegas.controlador.Consumer;
import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

public class ModuloEventoControlador {

	@FXML
	private AnchorPane panelRaiz;
	
	@FXML
	private RadioButton rbInformacion;

	@FXML
	private StackPane paneInventarioPrincipal;

	private Consumer app;
	private ToggleGroup grupoRB;

	public void setConsumer(Consumer app) {
		this.app = app;
	}
	
	public AnchorPane getPanelRaiz() {
		return panelRaiz;
	}
	
	public void iniciar() {
		grupoRB = new ToggleGroup();
		rbInformacion.setToggleGroup(grupoRB);
		rbInformacion.setSelected(true);
		grupoRB.selectedToggleProperty().addListener(e -> obtenerRadioBotonSeleccionado());
		
		obtenerRadioBotonSeleccionado();
	}

	public void obtenerRadioBotonSeleccionado() {
		RadioButton rbSeleccionado = (RadioButton) grupoRB.getSelectedToggle();
		paneInventarioPrincipal.getChildren().clear();
		if (rbSeleccionado.equals(rbInformacion)) {
			paneInventarioPrincipal.getChildren().add(app.getVistaModelo().getModeloPrincipal().getEventoInformacionControlador().getPanelRaiz());
		}
	}
}
