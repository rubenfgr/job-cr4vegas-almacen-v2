package es.rfgr.cr4vegas.vista.fxml.controlador.principal.modulo;

import es.rfgr.cr4vegas.controlador.Consumer;
import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;

public class ModuloMantenimientoControlador {

	@FXML
	private AnchorPane panelRaiz;

	@FXML
	private RadioButton rbInformacion;

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
		grupoRB.selectedToggleProperty().addListener(e -> obtenerRadioBotonSeleccionado());
		rbInformacion.setSelected(true);

		obtenerRadioBotonSeleccionado();
	}

	public void obtenerRadioBotonSeleccionado() {
		RadioButton rbSeleccionado = (RadioButton) grupoRB.getSelectedToggle();
		panelRaiz.getChildren().clear();
		if (rbSeleccionado.equals(rbInformacion)) {
			panelRaiz.getChildren().add(
					app.getVistaModelo().getModeloPrincipal().getMantenimientoInformacionControlador().getPanelRaiz());
		}
	}
}
