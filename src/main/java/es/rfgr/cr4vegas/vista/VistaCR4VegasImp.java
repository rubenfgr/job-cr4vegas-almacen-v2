package es.rfgr.cr4vegas.vista;

import es.rfgr.cr4vegas.controlador.Consumer;
import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class VistaCR4VegasImp extends Application implements VistaCR4Vegas {

	
	
	private Consumer app;

	private static VistaCR4VegasImp instancia;

	public VistaCR4VegasImp() {
		if (instancia != null) {
			app = instancia.app;

		} else {
			instancia = this;
		}
	}

	public void comenzar() {
		launch(this.getClass());
	}

	public void setConsumer(Consumer app) {
		this.app = app;
	}

	public void start(Stage escenarioPrincipal) throws Exception {
		app.getVistaModelo().setEscenarioPrincipal(escenarioPrincipal);

		app.getVistaModelo().cargarFXMLyControladores();
		
		app.getVistaModelo().iniciarControladores();

		escenarioPrincipal.setTitle("CR4Vegas - Gestión de inventario y mantenimiento");
		escenarioPrincipal.setOnCloseRequest(e -> app.getVistaModelo().onCloseRequest(e));
		escenarioPrincipal.getIcons().add(new Image(getClass().getResourceAsStream("/es/rfgr/cr4vegas/vista/imagenes/icono.png"), 32, 32, true, true));
		escenarioPrincipal.setMaximized(true);

		app.getVistaModelo().verPanelPrincipal();

		escenarioPrincipal.show();
	}

}
