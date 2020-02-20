package es.rfgr.cr4vegas.vista.modelo.imp;

import java.io.IOException;

import es.rfgr.cr4vegas.controlador.Consumer;
import es.rfgr.cr4vegas.vista.fxml.controlador.LoginControlador;
import es.rfgr.cr4vegas.vista.fxml.controlador.PrincipalControlador;
import es.rfgr.cr4vegas.vista.fxml.controlador.principal.ModuloControlador;
import es.rfgr.cr4vegas.vista.fxml.controlador.principal.modulo.ModuloEventoControlador;
import es.rfgr.cr4vegas.vista.fxml.controlador.principal.modulo.ModuloInventarioControlador;
import es.rfgr.cr4vegas.vista.fxml.controlador.principal.modulo.ModuloMantenimientoControlador;
import es.rfgr.cr4vegas.vista.fxml.controlador.principal.modulo.ModuloOperarioControlador;
import es.rfgr.cr4vegas.vista.fxml.controlador.principal.modulo.ModuloParteControlador;
import es.rfgr.cr4vegas.vista.fxml.controlador.principal.modulo.evento.EventoInformacionControlador;
import es.rfgr.cr4vegas.vista.fxml.controlador.principal.modulo.inventario.InventarioCompraControlador;
import es.rfgr.cr4vegas.vista.fxml.controlador.principal.modulo.inventario.InventarioInformacionControlador;
import es.rfgr.cr4vegas.vista.fxml.controlador.principal.modulo.inventario.InventarioMaterialControlador;
import es.rfgr.cr4vegas.vista.fxml.controlador.principal.modulo.inventario.InventarioPresupuestoControlador;
import es.rfgr.cr4vegas.vista.fxml.controlador.principal.modulo.inventario.InventarioTaquillaControlador;
import es.rfgr.cr4vegas.vista.fxml.controlador.principal.modulo.mantenimiento.MantenimientoInformacionControlador;
import es.rfgr.cr4vegas.vista.fxml.controlador.principal.modulo.operario.OperarioInformacionControlador;
import es.rfgr.cr4vegas.vista.fxml.controlador.principal.modulo.operario.OperarioPrincipalControlador;
import es.rfgr.cr4vegas.vista.fxml.controlador.principal.modulo.parte.ParteInformacionControlador;
import es.rfgr.cr4vegas.vista.fxml.controlador.principal.modulo.parte.PartePrincipalControlador;
import es.rfgr.cr4vegas.vista.modelo.ModeloPrincipal;
import javafx.fxml.FXMLLoader;

public class ModeloPrincipalImp implements ModeloPrincipal {

	private Consumer app;

	@Override
	public void setConsumer(Consumer app) {
		this.app = app;
	}
	
	// fxmlcontrolador
	private PrincipalControlador principalControlador;
	private LoginControlador loginControlador;

	// fxmlcontrolador.principal
	private ModuloControlador moduloControlador;

	// fxmlcontrolador.principal.modulo
	private ModuloInventarioControlador moduloInventarioControlador;
	private ModuloMantenimientoControlador moduloMantenimientoControlador;
	private ModuloOperarioControlador moduloOperarioControlador;
	private ModuloParteControlador moduloParteControlador;
	private ModuloEventoControlador moduloEventoControlador;

	// fxmlcontrolador.principal.modulo.evento
	private EventoInformacionControlador eventoInformacionControlador;

	// fxmlcontrolador.principal.modulo.inventario
	private InventarioInformacionControlador inventarioInformacionControlador;
	private InventarioCompraControlador inventarioCompraControlador;
	private InventarioMaterialControlador inventarioMaterialControlador;
	private InventarioTaquillaControlador inventarioTaquillaControlador;
	private InventarioPresupuestoControlador inventarioPresupuestoControlador;

	// fxmlcontrolador.principal.modulo.mantenimiento
	private MantenimientoInformacionControlador mantenimientoInformacionControlador;

	// fxmlcontrolador.principal.modulo.operario
	private OperarioInformacionControlador operarioInformacionControlador;
	private OperarioPrincipalControlador operarioPrincipalControlador;

	// fxmlcontrolador.principal.modulo.parte
	private ParteInformacionControlador parteInformacionControlador;
	private PartePrincipalControlador partePrincipalControlador;

	public void cargarFXMLyControladores() {
		try {
			cargarTodosFXML();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void cargarTodosFXML() throws IOException {
		// fxmlcontrolador
		iniciarPrincipal();
		iniciarLogin();

		// fxmlcontrolador.principal
		iniciarModulos();

		// fxmlcontrolador.principal.modulo
		iniciarModuloInventario();
		iniciarModuloMantenimiento();
		iniciarModuloOperario();
		iniciarModuloParte();
		iniciarModuloEvento();

		// fxmlcontrolador.modulo.evento
		iniciarEventoInformacion();

		// fxmlcontrolador.modulo.inventario
		iniciarInventarioInformacion();
		iniciarInventarioCompras();
		iniciarInventarioMaterial();
		iniciarInventarioTaquilla();
		iniciarInventarioPresupuesto();

		// fxmlcontrolador.principal.modulo.mantenimiento
		iniciarMantenimientoInformacion();

		// fxmlcontrolador.principal.modulo.operario
		iniciarOperarioInformacion();
		iniciarOperarioPrincipal();

		// fxmlcontrolador.principal.modulo.parte
		iniciarParteInformacion();
		iniciarPartePrincipal();
	}
	
	public void iniciarControladores() {
		// fxmlcontrolador
		principalControlador.iniciar();
		loginControlador.iniciar();

		// fxmlcontrolador.principal
		moduloControlador.iniciar();

		// fxmlcontrolador.principal.modulo
		moduloInventarioControlador.iniciar();
		moduloMantenimientoControlador.iniciar();
		moduloOperarioControlador.iniciar();
		moduloParteControlador.iniciar();
		moduloEventoControlador.iniciar();

		// fxmlcontrolador.principal.modulo.evento
		eventoInformacionControlador.iniciar();

		// fxmlcontrolador.principal.modulo.inventario
		inventarioInformacionControlador.iniciar();
		inventarioCompraControlador.iniciar();
		inventarioMaterialControlador.iniciar();
		inventarioTaquillaControlador.iniciar();
		inventarioPresupuestoControlador.iniciar();

		// fxmlcontrolador.principal.modulo.mantenimiento
		mantenimientoInformacionControlador.iniciar();

		// fxmlcontrolador.principal.modulo.operario
		operarioInformacionControlador.iniciar();
		operarioPrincipalControlador.iniciar();

		// fxmlcontrolador.principal.modulo.parte
		parteInformacionControlador.iniciar();
		partePrincipalControlador.iniciar();
	}

	/**
	 * --> fxmlcontrolador
	 * 
	 * @return PrincipalControlador
	 */
	public PrincipalControlador getPrincipalControlador() {
		return principalControlador;
	}

	private void iniciarPrincipal() throws IOException {
		FXMLLoader cargador = new FXMLLoader(getClass().getResource("/es/rfgr/cr4vegas/vista/fxml/principal.fxml"));
		cargador.load();

		principalControlador = cargador.getController();
		principalControlador.setConsumer(app);
	}

	/**
	 * @return LoginControlador
	 */
	public LoginControlador getLoginControlador() {
		return loginControlador;
	}

	private void iniciarLogin() throws IOException {
		FXMLLoader cargador = new FXMLLoader(getClass().getResource("/es/rfgr/cr4vegas/vista/fxml/login.fxml"));
		cargador.load();

		loginControlador = cargador.getController();
		loginControlador.setConsumer(app);
	}

	/**
	 * --> fxmlcontrolador.principal
	 * 
	 * @return ModuloControlador
	 */
	public ModuloControlador getModulosControlador() {
		return moduloControlador;
	}

	private void iniciarModulos() throws IOException {
		FXMLLoader cargador = new FXMLLoader(getClass().getResource("/es/rfgr/cr4vegas/vista/fxml/principal/modulo.fxml"));
		cargador.load();

		moduloControlador = cargador.getController();
		moduloControlador.setConsumer(app);
	}

	/**
	 * --> fxmlcontrolador.principal.modulo
	 * 
	 * @return ModuloInventarioControlador
	 */
	public ModuloInventarioControlador getInventarioControlador() {
		return moduloInventarioControlador;
	}

	private void iniciarModuloInventario() throws IOException {
		FXMLLoader cargador = new FXMLLoader(getClass().getResource("/es/rfgr/cr4vegas/vista/fxml/principal/modulo/moduloInventario.fxml"));
		cargador.load();

		moduloInventarioControlador = cargador.getController();
		moduloInventarioControlador.setConsumer(app);
	}

	/**
	 * @return ModuloMantenimientoControlador
	 */
	public ModuloMantenimientoControlador getMantenimientoControlador() {
		return moduloMantenimientoControlador;
	}

	private void iniciarModuloMantenimiento() throws IOException {
		FXMLLoader cargador = new FXMLLoader(getClass().getResource("/es/rfgr/cr4vegas/vista/fxml/principal/modulo/moduloMantenimiento.fxml"));
		cargador.load();

		moduloMantenimientoControlador = cargador.getController();
		moduloMantenimientoControlador.setConsumer(app);
	}

	/**
	 * @return ModuloOperarioControlador
	 */
	public ModuloOperarioControlador getOperariosControlador() {
		return moduloOperarioControlador;
	}

	private void iniciarModuloOperario() throws IOException {
		FXMLLoader cargador = new FXMLLoader(getClass().getResource("/es/rfgr/cr4vegas/vista/fxml/principal/modulo/moduloOperario.fxml"));
		cargador.load();

		moduloOperarioControlador = cargador.getController();
		moduloOperarioControlador.setConsumer(app);
	}

	/**
	 * @return ModuloParteControlador
	 */
	public ModuloParteControlador getPartesControlador() {
		return moduloParteControlador;
	}

	private void iniciarModuloParte() throws IOException {
		FXMLLoader cargador = new FXMLLoader(getClass().getResource("/es/rfgr/cr4vegas/vista/fxml/principal/modulo/moduloParte.fxml"));
		cargador.load();

		moduloParteControlador = cargador.getController();
		moduloParteControlador.setConsumer(app);
	}

	/**
	 * @return ModuloEventoControlador
	 */
	public ModuloEventoControlador getEventosControlador() {
		return moduloEventoControlador;
	}

	private void iniciarModuloEvento() throws IOException {
		FXMLLoader cargador = new FXMLLoader(getClass().getResource("/es/rfgr/cr4vegas/vista/fxml/principal/modulo/moduloEvento.fxml"));
		cargador.load();

		moduloEventoControlador = cargador.getController();
		moduloEventoControlador.setConsumer(app);
	}

	/**
	 * --> fxmlcontrolador.principal.modulo.evento
	 * 
	 * @return EventoInformacionControlador
	 */
	public EventoInformacionControlador getEventoInformacionControlador() {
		return eventoInformacionControlador;
	}

	private void iniciarEventoInformacion() throws IOException {
		FXMLLoader cargador = new FXMLLoader(
				getClass().getResource("/es/rfgr/cr4vegas/vista/fxml/principal/modulo/evento/eventoInformacion.fxml"));
		cargador.load();

		eventoInformacionControlador = cargador.getController();
		eventoInformacionControlador.setConsumer(app);
	}

	/**
	 * --> fxmlcontrolador.principal.modulo.inventario
	 * 
	 * @return InventarioInformacionControlador
	 */
	public InventarioInformacionControlador getInventarioInformacionControlador() {
		return inventarioInformacionControlador;
	}

	private void iniciarInventarioInformacion() throws IOException {
		FXMLLoader cargador = new FXMLLoader(
				getClass().getResource("/es/rfgr/cr4vegas/vista/fxml/principal/modulo/inventario/inventarioInformacion.fxml"));
		cargador.load();

		inventarioInformacionControlador = cargador.getController();
		inventarioInformacionControlador.setConsumer(app);
	}

	/**
	 * @return InventarioMaterialControlador
	 */
	public InventarioMaterialControlador getInventarioMaterialControlador() {
		return inventarioMaterialControlador;
	}

	private void iniciarInventarioMaterial() throws IOException {
		FXMLLoader cargador = new FXMLLoader(
				getClass().getResource("/es/rfgr/cr4vegas/vista/fxml/principal/modulo/inventario/inventarioMaterial.fxml"));
		cargador.load();

		inventarioMaterialControlador = cargador.getController();
		inventarioMaterialControlador.setConsumer(app);
	}

	/**
	 * @return InventarioCompraControlador
	 */
	public InventarioCompraControlador getInventarioCompraControlador() {
		return inventarioCompraControlador;
	}

	private void iniciarInventarioCompras() throws IOException {
		FXMLLoader cargador = new FXMLLoader(
				getClass().getResource("/es/rfgr/cr4vegas/vista/fxml/principal/modulo/inventario/inventarioCompra.fxml"));
		cargador.load();

		inventarioCompraControlador = cargador.getController();
		inventarioCompraControlador.setConsumer(app);
	}

	/**
	 * @return InventarioTaquillaControlador
	 */
	public InventarioTaquillaControlador getInventarioTaquillaControlador() {
		return inventarioTaquillaControlador;
	}

	private void iniciarInventarioTaquilla() throws IOException {
		FXMLLoader cargador = new FXMLLoader(
				getClass().getResource("/es/rfgr/cr4vegas/vista/fxml/principal/modulo/inventario/inventarioTaquilla.fxml"));
		cargador.load();

		inventarioTaquillaControlador = cargador.getController();
		inventarioTaquillaControlador.setConsumer(app);
	}
	
	/**
	 * @return InventarioPresupuestoControlador
	 */
	public InventarioPresupuestoControlador getInventarioPresupuestoControlador() {
		return inventarioPresupuestoControlador;
	}

	private void iniciarInventarioPresupuesto() throws IOException {
		FXMLLoader cargador = new FXMLLoader(
				getClass().getResource("/es/rfgr/cr4vegas/vista/fxml/principal/modulo/inventario/inventarioPresupuesto.fxml"));
		cargador.load();

		inventarioPresupuestoControlador = cargador.getController();
		inventarioPresupuestoControlador.setConsumer(app);
	}

	/**
	 * --> fxmlcontrolador.principal.modulo.mantenimiento
	 * 
	 * @return MantenimientoInformacionControlador
	 */
	public MantenimientoInformacionControlador getMantenimientoInformacionControlador() {
		return mantenimientoInformacionControlador;
	}

	private void iniciarMantenimientoInformacion() throws IOException {
		FXMLLoader cargador = new FXMLLoader(
				getClass().getResource("/es/rfgr/cr4vegas/vista/fxml/principal/modulo/mantenimiento/mantenimientoInformacion.fxml"));
		cargador.load();

		mantenimientoInformacionControlador = cargador.getController();
		mantenimientoInformacionControlador.setConsumer(app);
	}

	/**
	 * --> fxmlcontrolador.principal.modulo.operario
	 * 
	 * @return OperarioInformacionControlador
	 */
	public OperarioInformacionControlador getOperarioInformacionControlador() {
		return operarioInformacionControlador;
	}

	private void iniciarOperarioInformacion() throws IOException {

		FXMLLoader cargador = new FXMLLoader(
				getClass().getResource("/es/rfgr/cr4vegas/vista/fxml/principal/modulo/operario/operarioInformacion.fxml"));
		cargador.load();

		operarioInformacionControlador = cargador.getController();
		operarioInformacionControlador.setConsumer(app);
	}

	/**
	 * @return OperarioPrincipalControlador
	 */
	public OperarioPrincipalControlador getOperarioPrincipalControlador() {
		return operarioPrincipalControlador;
	}

	private void iniciarOperarioPrincipal() throws IOException {
		FXMLLoader cargador = new FXMLLoader(
				getClass().getResource("/es/rfgr/cr4vegas/vista/fxml/principal/modulo/operario/operarioPrincipal.fxml"));
		cargador.load();

		operarioPrincipalControlador = cargador.getController();
		operarioPrincipalControlador.setConsumer(app);
	}

	/**
	 * --> fxmlcontrolador.principal.modulo.parte
	 * 
	 * @return ParteInformacionControlador
	 */
	public ParteInformacionControlador getParteInformacionControlador() {
		return parteInformacionControlador;
	}

	private void iniciarParteInformacion() throws IOException {
		FXMLLoader cargador = new FXMLLoader(
				getClass().getResource("/es/rfgr/cr4vegas/vista/fxml/principal/modulo/parte/parteInformacion.fxml"));
		cargador.load();

		parteInformacionControlador = cargador.getController();
		parteInformacionControlador.setConsumer(app);
	}

	public PartePrincipalControlador getPartePrincipalControlador() {
		return partePrincipalControlador;
	}

	private void iniciarPartePrincipal() throws IOException {
		FXMLLoader cargador = new FXMLLoader(
				getClass().getResource("/es/rfgr/cr4vegas/vista/fxml/principal/modulo/parte/partePrincipal.fxml"));
		cargador.load();

		partePrincipalControlador = cargador.getController();
		partePrincipalControlador.setConsumer(app);
	}

//	panel.prefWidthProperty().bind(getInventarioControlador().getPanelRaiz().widthProperty());
//	panel.prefHeightProperty().bind(getInventarioControlador().getPanelRaiz().heightProperty());
}
