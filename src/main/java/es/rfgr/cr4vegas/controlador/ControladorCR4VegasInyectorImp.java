package es.rfgr.cr4vegas.controlador;

import es.rfgr.cr4vegas.modelo.ModeloCR4Vegas;
import es.rfgr.cr4vegas.vista.VistaCR4Vegas;
import es.rfgr.cr4vegas.vista.modelo.VistaModelo;

public class ControladorCR4VegasInyectorImp implements ControladorCR4VegasInyector {

	private Consumer app;

	public ControladorCR4VegasInyectorImp(ModeloCR4Vegas modelo, VistaCR4Vegas vista, VistaModelo vistaModelo) {
		app = new ControladorCR4VegasApp(new ControladorCR4VegasImp(modelo, vista, vistaModelo));
		vista.setConsumer(app);
		vistaModelo.setConsumer(app);
	}

	public Consumer getConsumer() {
		return app;
	}
}
