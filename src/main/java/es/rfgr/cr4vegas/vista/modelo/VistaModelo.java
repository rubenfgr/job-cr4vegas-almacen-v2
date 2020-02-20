package es.rfgr.cr4vegas.vista.modelo;

import es.rfgr.cr4vegas.controlador.Consumer;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public interface VistaModelo {

	void setConsumer(Consumer app);

	void cargarFXMLyControladores();

	void iniciarControladores();

	void verPanelPrincipal();

	void onCloseRequest(WindowEvent e);

	/**	
	 * ModeloPrincipal
	 */
	ModeloPrincipal getModeloPrincipal();
	
	/**
	 * ModeloVentana
	 */
	ModeloVentana getModeloVentana();
	
	/**
	 * --> escenarios
	 */
	void setEscenarioPrincipal(Stage escenarioPrincipal);

	Stage getEscenarioPrincipal();

	Stage getEscenario(AnchorPane ventana, String titulo);

}