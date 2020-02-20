package es.rfgr.cr4vegas.vista.modelo.imp;

import java.io.IOException;

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
import es.rfgr.cr4vegas.vista.modelo.ModeloVentana;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ModeloVentanaImp implements ModeloVentana {

	private final static String CSS = "/es/rfgr/cr4vegas/vista/css/modena.css";

	private Consumer app;

	// ventana.inventario
	private VentanaManipulaCompraControlador ventanaManipulaCompraControlador;
	private VentanaManipulaMaterialControlador ventanaManipulaMaterialControlador;
	private VentanaOperaMaterialControlador ventanaOperaMaterialControlador;
	private VentanaManipulaTaquillaControlador ventanaManipulaTaquillaControlador;
	private VentanaManipulaPresupuestoControlador ventanaManipulaPresupuestoControlador;

	// ventana.operario
	private VentanaManipulaOperarioControlador ventanaManipulaOperarioControlador;

	// ventana.parte
	private VentanaAsociaParteOperarioControlador ventanaAsociaParteOperarioControlador;
	private VentanaManipulaParteControlador ventanaManipulaParteControlador;

	// ventana.principal
	private VentanaSimpleControlador<Object> ventanaSimpleControlador;
	private VentanaTiendaControlador ventanaTiendaControlador;
	private VentanaManipulaTiendaControlador ventanaManipulaTiendaControlador;
	private VentanaConexionControlador ventanaConexionControlador;

	// ventana.utileria
	private VentanaBuscarImagenControlador ventanaBuscarImagenControlador;
	private VentanaSeleccionarPrecioControlador ventanaSeleccionarPrecioControlador;

	@Override
	public void cargarFXMLyControladores() {
		try {
			cargarTodosFXML();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void cargarTodosFXML() throws IOException {
		// ventana.inventario
		iniciarVentanaManipulaCompra();
		iniciarVentanaManipulaMaterial();
		iniciarVentanaManipulaTaquilla();
		iniciarVentanaOperaMaterial();
		iniciarVentanaManipulaPresupuesto();

		iniciarVentanaTienda();

		// ventana.operario
		iniciarVentanaManipulaOperario();

		// ventana.parte
		iniciarVentanaAsociaParteOperario();
		iniciarVentanaManipulaParte();

		// ventana.principal
		iniciarVentanaSimple();
		iniciarVentanaTienda();
		iniciarVentanaManipulaTienda();
		iniciarVentanaConexion();

		// ventana.utileria
		iniciarVentanaBuscarImagen();
		iniciarVentanaSeleccionarPrecio();
	}

	@Override
	public void iniciarControladores() {
		// ventana.inventario
		ventanaManipulaCompraControlador.iniciar();
		ventanaManipulaMaterialControlador.iniciar();
		ventanaManipulaTaquillaControlador.iniciar();
		ventanaOperaMaterialControlador.iniciar();
		ventanaManipulaPresupuestoControlador.iniciar();

		// ventana.operario
		ventanaManipulaOperarioControlador.iniciar();

		// ventana.parte
		ventanaAsociaParteOperarioControlador.iniciar();
		ventanaManipulaParteControlador.iniciar();

		// ventana.principal
		ventanaSimpleControlador.iniciar();
		ventanaTiendaControlador.iniciar();
		ventanaManipulaTiendaControlador.iniciar();
		ventanaConexionControlador.iniciar();

		// ventana.utileria
		ventanaBuscarImagenControlador.iniciar();
		ventanaSeleccionarPrecioControlador.iniciar();
	}

	/**
	 * --> ventana.inventario
	 * 
	 * @return VentanaManipulaCompraControlador
	 */
	@Override
	public VentanaManipulaCompraControlador getVentanaManipulaCompraControlador() {
		return ventanaManipulaCompraControlador;
	}

	private void iniciarVentanaManipulaCompra() throws IOException {
		FXMLLoader cargador = new FXMLLoader(
				getClass().getResource("/es/rfgr/cr4vegas/vista/fxml/ventana/inventario/ventanaManipulaCompra.fxml"));
		cargador.load();

		ventanaManipulaCompraControlador = cargador.getController();
		ventanaManipulaCompraControlador.setConsumer(app);
	}

	/**
	 * @return VentanaManipulaMaterialControlador
	 */
	@Override
	public VentanaManipulaMaterialControlador getVentanaManipulaMaterialControlador() {
		return ventanaManipulaMaterialControlador;
	}

	private void iniciarVentanaManipulaMaterial() throws IOException {
		FXMLLoader cargador = new FXMLLoader(
				getClass().getResource("/es/rfgr/cr4vegas/vista/fxml/ventana/inventario/ventanaManipulaMaterial.fxml"));
		cargador.load();

		ventanaManipulaMaterialControlador = cargador.getController();
		ventanaManipulaMaterialControlador.setConsumer(app);
	}

	/**
	 * @return VentanaManipulaTaquillaControlador
	 */
	@Override
	public VentanaManipulaTaquillaControlador getVentanaManipulaTaquillaControlador() {
		return ventanaManipulaTaquillaControlador;
	}

	private void iniciarVentanaManipulaTaquilla() throws IOException {
		FXMLLoader cargador = new FXMLLoader(
				getClass().getResource("/es/rfgr/cr4vegas/vista/fxml/ventana/inventario/ventanaManipulaTaquilla.fxml"));
		cargador.load();

		ventanaManipulaTaquillaControlador = cargador.getController();
		ventanaManipulaTaquillaControlador.setConsumer(app);
	}

	/**
	 * @return VentanaOperaMaterialControlador
	 */
	@Override
	public VentanaOperaMaterialControlador getVentanaOperaMaterialControlador() {
		return ventanaOperaMaterialControlador;
	}

	private void iniciarVentanaOperaMaterial() throws IOException {
		FXMLLoader cargador = new FXMLLoader(
				getClass().getResource("/es/rfgr/cr4vegas/vista/fxml/ventana/inventario/ventanaOperaMaterial.fxml"));
		cargador.load();

		ventanaOperaMaterialControlador = cargador.getController();
		ventanaOperaMaterialControlador.setConsumer(app);
	}
	
	/**
	 * @return VentanaManipulaPresupuestoControlador
	 */
	@Override
	public VentanaManipulaPresupuestoControlador getVentanaManipulaPresupuestoControlador() {
		return ventanaManipulaPresupuestoControlador;
	}

	private void iniciarVentanaManipulaPresupuesto() throws IOException {
		FXMLLoader cargador = new FXMLLoader(
				getClass().getResource("/es/rfgr/cr4vegas/vista/fxml/ventana/inventario/ventanaManipulaPresupuesto.fxml"));
		cargador.load();

		ventanaManipulaPresupuestoControlador = cargador.getController();
		ventanaManipulaPresupuestoControlador.setConsumer(app);
	}

	/**
	 * --> ventana.operario
	 * 
	 * @return VentanaManipulaOperarioControlador
	 */
	@Override
	public VentanaManipulaOperarioControlador getVentanaManipulaOperarioControlador() {
		return ventanaManipulaOperarioControlador;
	}

	private void iniciarVentanaManipulaOperario() throws IOException {
		FXMLLoader cargador = new FXMLLoader(
				getClass().getResource("/es/rfgr/cr4vegas/vista/fxml/ventana/operario/ventanaManipulaOperario.fxml"));
		cargador.load();

		ventanaManipulaOperarioControlador = cargador.getController();
		ventanaManipulaOperarioControlador.setConsumer(app);
	}

	/**
	 * --> ventana.parte
	 * 
	 * @return VentanaAsociaParteOperarioControlador
	 */
	@Override
	public VentanaAsociaParteOperarioControlador getVentanaAsociaParteOperarioControlador() {
		return ventanaAsociaParteOperarioControlador;
	}

	private void iniciarVentanaAsociaParteOperario() throws IOException {
		FXMLLoader cargador = new FXMLLoader(
				getClass().getResource("/es/rfgr/cr4vegas/vista/fxml/ventana/parte/ventanaAsociaParteOperario.fxml"));
		cargador.load();

		ventanaAsociaParteOperarioControlador = cargador.getController();
		ventanaAsociaParteOperarioControlador.setConsumer(app);
	}

	/**
	 * @return VentanaManipulaParteControlador
	 */
	@Override
	public VentanaManipulaParteControlador getVentanaManipulaParteControlador() {
		return ventanaManipulaParteControlador;
	}

	private void iniciarVentanaManipulaParte() throws IOException {
		FXMLLoader cargador = new FXMLLoader(
				getClass().getResource("/es/rfgr/cr4vegas/vista/fxml/ventana/parte/ventanaManipulaParte.fxml"));
		cargador.load();

		ventanaManipulaParteControlador = cargador.getController();
		ventanaManipulaParteControlador.setConsumer(app);
	}

	/**
	 * --> ventana.principal
	 * 
	 * @return VentanaSimpleControlador
	 */
	@Override
	public VentanaSimpleControlador<Object> getVentanaSimpleControlador() {
		return ventanaSimpleControlador;
	}

	private void iniciarVentanaSimple() throws IOException {
		FXMLLoader cargador = new FXMLLoader(getClass().getResource("/es/rfgr/cr4vegas/vista/fxml/ventana/principal/ventanaSimple.fxml"));
		cargador.load();

		ventanaSimpleControlador = cargador.getController();
		ventanaSimpleControlador.setConsumer(app);
	}

	/**
	 * @return VentanaTiendaControlador
	 */
	@Override
	public VentanaTiendaControlador getVentanaTiendaControlador() {
		return ventanaTiendaControlador;
	}

	private void iniciarVentanaTienda() throws IOException {
		FXMLLoader cargador = new FXMLLoader(getClass().getResource("/es/rfgr/cr4vegas/vista/fxml/ventana/principal/ventanaTienda.fxml"));
		cargador.load();

		ventanaTiendaControlador = cargador.getController();
		ventanaTiendaControlador.setConsumer(app);
	}

	/**
	 * @return VentanaTiendaControlador
	 */
	@Override
	public VentanaManipulaTiendaControlador getVentanaManipulaTiendaControlador() {
		return ventanaManipulaTiendaControlador;
	}

	private void iniciarVentanaManipulaTienda() throws IOException {
		FXMLLoader cargador = new FXMLLoader(
				getClass().getResource("/es/rfgr/cr4vegas/vista/fxml/ventana/principal/ventanaManipulaTienda.fxml"));
		cargador.load();

		ventanaManipulaTiendaControlador = cargador.getController();
		ventanaManipulaTiendaControlador.setConsumer(app);
	}
	
	/**
	 * @return VentanaConexionControlador
	 */
	@Override
	public VentanaConexionControlador getVentanaConexionControlador() {
		return ventanaConexionControlador;
	}

	private void iniciarVentanaConexion() throws IOException {
		FXMLLoader cargador = new FXMLLoader(
				getClass().getResource("/es/rfgr/cr4vegas/vista/fxml/ventana/principal/ventanaConexion.fxml"));
		cargador.load();

		ventanaConexionControlador = cargador.getController();
		ventanaConexionControlador.setConsumer(app);
	}

	/**
	 * --> ventana.utileria
	 * 
	 * @return VentanaBuscarImagenControlador
	 */
	@Override
	public VentanaBuscarImagenControlador getVentanaBuscarImagenControlador() {
		return ventanaBuscarImagenControlador;
	}

	private void iniciarVentanaBuscarImagen() throws IOException {
		FXMLLoader cargador = new FXMLLoader(
				getClass().getResource("/es/rfgr/cr4vegas/vista/fxml/ventana/utileria/ventanaBuscarImagen.fxml"));
		cargador.load();

		ventanaBuscarImagenControlador = cargador.getController();
		ventanaBuscarImagenControlador.setConsumer(app);
	}
	
	/** 
	 * @return VentanaSeleccionarPrecioControlador
	 */
	@Override
	public VentanaSeleccionarPrecioControlador getVentanaSeleccionarPrecioControlador() {
		return ventanaSeleccionarPrecioControlador;
	}

	private void iniciarVentanaSeleccionarPrecio() throws IOException {
		FXMLLoader cargador = new FXMLLoader(
				getClass().getResource("/es/rfgr/cr4vegas/vista/fxml/ventana/utileria/ventanaSeleccionarPrecio.fxml"));
		cargador.load();

		ventanaSeleccionarPrecioControlador = cargador.getController();
		ventanaSeleccionarPrecioControlador.setConsumer(app);
	}

	/**
	 * Logica Ventanas
	 */
	@Override
	public Stage getEscenario(AnchorPane panelRaiz, String titulo) {
		Stage escenario = new Stage();
		AnchorPane panelVentana = new AnchorPane();
		panelVentana.getStylesheets().add(getClass().getResource(CSS).toExternalForm());
		panelVentana.getChildren().add(panelRaiz);
		panelVentana.resize(panelRaiz.getWidth(), panelRaiz.getHeight());
		Scene escena = new Scene(panelVentana);
		escenario.initOwner(app.getVistaModelo().getEscenarioPrincipal());
		escenario.getIcons().add(new Image(getClass().getResourceAsStream("/es/rfgr/cr4vegas/vista/imagenes/icono.png"), 32, 32, true, true));
		escenario.initModality(Modality.WINDOW_MODAL);
		escenario.setResizable(false);
		escenario.setScene(escena);
		escenario.setTitle(titulo);
		escenario.setOnCloseRequest(e -> escenario.close());
		return escenario;
	}

	@Override
	public void setConsumer(Consumer app) {
		this.app = app;
	}

}
