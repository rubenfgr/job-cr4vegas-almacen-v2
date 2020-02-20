package es.rfgr.cr4vegas.vista.fxml.controlador.principal.modulo.inventario;

import es.rfgr.cr4vegas.controlador.Consumer;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

public class InventarioInformacionControlador {

	@FXML
	private AnchorPane panelRaiz;

	@FXML
	private Label lbCompradoHoy;

	@FXML
	private Label lbCompradoAyer;

	@FXML
	private Label lbCompradoSemanaActual;

	@FXML
	private Label lbCompradoSemanaAnterior;

	@FXML
	private Label lbCompradoMesActual;

	@FXML
	private Label lbCompradoMesAnterior;

	@FXML
	private Label lbCompradoAnoActual;

	@FXML
	private Label lbCompradoAnoAnterior;

	@FXML
	private Label lbCompradoHoyTotal;

	@FXML
	private Label lbCompradoAyerTotal;

	@FXML
	private Label lbCompradoSemanaActualTotal;

	@FXML
	private Label lbCompradoSemanaAnteriorTotal;

	@FXML
	private Label lbCompradoMesActualTotal;

	@FXML
	private Label lbCompradoMesAnteriorTotal;

	@FXML
	private Label lbCompradoAnoActualTotal;

	@FXML
	private Label lbCompradoAnoAnteriorTotal;

	@FXML
	private Label lbGastadoHoy;

	@FXML
	private Label lbGastadoAyer;

	@FXML
	private Label lbGastadoSemanaActual;

	@FXML
	private Label lbGastadoSemanaAnterior;

	@FXML
	private Label lbGastadoMesActual;

	@FXML
	private Label lbGastadoMesAnterior;

	@FXML
	private Label lbGastadoAnoActual;

	@FXML
	private Label lbGastadoAnoAnterior;

	@FXML
	private Label lbGastadoHoyTotal;

	@FXML
	private Label lbGastadoAyerTotal;

	@FXML
	private Label lbGastadoSemanaActualTotal;

	@FXML
	private Label lbGastadoSemanaAnteriorTotal;

	@FXML
	private Label lbGastadoMesActualTotal;

	@FXML
	private Label lbGastadoMesAnteriorTotal;

	@FXML
	private Label lbGastadoAnoActualTotal;

	@FXML
	private Label lbGastadoAnoAnteriorTotal;

	@FXML
	private Label lbNumeroMateriales;

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
