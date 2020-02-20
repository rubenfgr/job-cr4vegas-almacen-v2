package es.rfgr.cr4vegas.vista.modelo;

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

public interface ModeloPrincipal {

	void cargarFXMLyControladores();

	void iniciarControladores();
	
	PrincipalControlador getPrincipalControlador();

	LoginControlador getLoginControlador();

	ModuloControlador getModulosControlador();

	ModuloInventarioControlador getInventarioControlador();

	ModuloMantenimientoControlador getMantenimientoControlador();

	ModuloOperarioControlador getOperariosControlador();

	ModuloParteControlador getPartesControlador();

	ModuloEventoControlador getEventosControlador();

	EventoInformacionControlador getEventoInformacionControlador();

	InventarioInformacionControlador getInventarioInformacionControlador();

	InventarioMaterialControlador getInventarioMaterialControlador();

	InventarioCompraControlador getInventarioCompraControlador();

	InventarioTaquillaControlador getInventarioTaquillaControlador();
	
	InventarioPresupuestoControlador getInventarioPresupuestoControlador();

	MantenimientoInformacionControlador getMantenimientoInformacionControlador();

	OperarioInformacionControlador getOperarioInformacionControlador();

	OperarioPrincipalControlador getOperarioPrincipalControlador();

	ParteInformacionControlador getParteInformacionControlador();

	PartePrincipalControlador getPartePrincipalControlador();

	void setConsumer(Consumer app);

}