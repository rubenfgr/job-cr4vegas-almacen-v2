package es.rfgr.cr4vegas.vista.modelo.imp;

import es.rfgr.cr4vegas.controlador.Consumer;
import es.rfgr.cr4vegas.vista.modelo.ModeloPrincipal;
import es.rfgr.cr4vegas.vista.modelo.ModeloVentana;
import es.rfgr.cr4vegas.vista.modelo.VistaModelo;
import es.rfgr.cr4vegas.vista.utileria.Dialogos;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class VistaModeloImp implements VistaModelo {

	private ModeloPrincipal modeloPrincipal;

	private ModeloVentana modeloVentana;

	private Stage escenarioPrincipal;

	public VistaModeloImp(ModeloPrincipal modeloPrincipal, ModeloVentana modeloVentana) {
		this.modeloPrincipal = modeloPrincipal;
		this.modeloVentana = modeloVentana;
	}

	@Override
	public void setConsumer(Consumer app) {
		modeloPrincipal.setConsumer(app);
		modeloVentana.setConsumer(app);
	}

	@Override
	public void cargarFXMLyControladores() {
		modeloPrincipal.cargarFXMLyControladores();
		modeloVentana.cargarFXMLyControladores();
	}

	@Override
	public void iniciarControladores() {
		modeloPrincipal.iniciarControladores();
		modeloVentana.iniciarControladores();
	}

	@Override
	public void verPanelPrincipal() {
		BorderPane panelPrincipal = modeloPrincipal.getPrincipalControlador().getPanelRaiz();

		AnchorPane panelLogin = modeloPrincipal.getLoginControlador().getPanelRaiz();

		panelPrincipal.setCenter(panelLogin);

		escenarioPrincipal.setScene(new Scene(panelPrincipal));
	}

	@Override
	public void onCloseRequest(WindowEvent e) {
		if (Dialogos.mostrarDialogoConfirmacion("¿Desea salir de la aplicación?", escenarioPrincipal)) {
			escenarioPrincipal.close();

		} else
			e.consume();
	}

	/**
	 * ModeloPrincipal
	 */
	@Override
	public ModeloPrincipal getModeloPrincipal() {
		return modeloPrincipal;
	}

	/**
	 * ModeloVentana
	 */
	@Override
	public ModeloVentana getModeloVentana() {
		return modeloVentana;
	}

	/**
	 * --> escenarios
	 */
	@Override
	public void setEscenarioPrincipal(Stage escenarioPrincipal) {
		this.escenarioPrincipal = escenarioPrincipal;
	}

	@Override
	public Stage getEscenarioPrincipal() {
		return escenarioPrincipal;
	}

	@Override
	public Stage getEscenario(AnchorPane ventana, String titulo) {
		return modeloVentana.getEscenario(ventana, titulo);
	}

}
