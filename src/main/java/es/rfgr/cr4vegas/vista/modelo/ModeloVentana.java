package es.rfgr.cr4vegas.vista.modelo;

import es.rfgr.cr4vegas.controlador.Consumer;
import es.rfgr.cr4vegas.vista.fxml.controlador.ventana.inventario.VentanaManipulaCompraControlador;
import es.rfgr.cr4vegas.vista.fxml.controlador.ventana.inventario.VentanaManipulaMaterialControlador;
import es.rfgr.cr4vegas.vista.fxml.controlador.ventana.inventario.VentanaManipulaPresupuestoControlador;
import es.rfgr.cr4vegas.vista.fxml.controlador.ventana.inventario.VentanaManipulaTaquillaControlador;
import es.rfgr.cr4vegas.vista.fxml.controlador.ventana.inventario.VentanaOperaMaterialControlador;
import es.rfgr.cr4vegas.vista.fxml.controlador.ventana.operario.VentanaManipulaOperarioControlador;
import es.rfgr.cr4vegas.vista.fxml.controlador.ventana.parte.VentanaAsociaParteOperarioControlador;
import es.rfgr.cr4vegas.vista.fxml.controlador.ventana.parte.VentanaManipulaParteControlador;
import es.rfgr.cr4vegas.vista.fxml.controlador.ventana.principal.VentanaConexionControlador;
import es.rfgr.cr4vegas.vista.fxml.controlador.ventana.principal.VentanaManipulaTiendaControlador;
import es.rfgr.cr4vegas.vista.fxml.controlador.ventana.principal.VentanaSimpleControlador;
import es.rfgr.cr4vegas.vista.fxml.controlador.ventana.principal.VentanaTiendaControlador;
import es.rfgr.cr4vegas.vista.fxml.controlador.ventana.utileria.VentanaBuscarImagenControlador;
import es.rfgr.cr4vegas.vista.fxml.controlador.ventana.utileria.VentanaSeleccionarPrecioControlador;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public interface ModeloVentana {

	void cargarFXMLyControladores();

	void iniciarControladores();

	/**
	 * --> ventana.inventario
	 * 
	 * @return VentanaManipulaCompraControlador
	 */
	VentanaManipulaCompraControlador getVentanaManipulaCompraControlador();

	/**
	 * @return VentanaManipulaMaterialControlador
	 */
	VentanaManipulaMaterialControlador getVentanaManipulaMaterialControlador();

	/**
	 * @return VentanaOperaMaterialControlador
	 */
	VentanaOperaMaterialControlador getVentanaOperaMaterialControlador();

	/**
	 * @return VentanaManipulaTaquillaControlador
	 */
	VentanaManipulaTaquillaControlador getVentanaManipulaTaquillaControlador();

	/**
	 * @return VentanaManipulaPresupuestoControlador
	 */
	VentanaManipulaPresupuestoControlador getVentanaManipulaPresupuestoControlador();

	/**
	 * --> ventana.operario
	 * 
	 * @return VentanaManipulaOperarioControlador
	 */
	VentanaManipulaOperarioControlador getVentanaManipulaOperarioControlador();

	/**
	 * --> ventana.parte
	 * 
	 * @return VentanaAsociaParteOperarioControlador
	 */
	VentanaAsociaParteOperarioControlador getVentanaAsociaParteOperarioControlador();

	/**
	 * @return VentanaManipulaParteControlador
	 */
	VentanaManipulaParteControlador getVentanaManipulaParteControlador();

	/**
	 * --> ventana.principal
	 * 
	 * @return VentanaSimpleControlador
	 */
	VentanaSimpleControlador<Object> getVentanaSimpleControlador();

	/**
	 * @return VentanaTiendaControlador
	 */
	VentanaTiendaControlador getVentanaTiendaControlador();

	/**
	 * @return VentanaTiendaControlador
	 */
	VentanaManipulaTiendaControlador getVentanaManipulaTiendaControlador();

	/**
	 * @return VentanaConexionControlador
	 */
	VentanaConexionControlador getVentanaConexionControlador();

	/**
	 * --> ventana.utileria
	 * 
	 * @return VentanaBuscarImagenControlador
	 */
	VentanaBuscarImagenControlador getVentanaBuscarImagenControlador();

	/**
	 * @return VentanaSeleccionarPrecioControlador
	 */
	VentanaSeleccionarPrecioControlador getVentanaSeleccionarPrecioControlador();

	/**
	 * Logica Ventanas
	 */
	Stage getEscenario(AnchorPane panelRaiz, String titulo);

	void setConsumer(Consumer app);

}