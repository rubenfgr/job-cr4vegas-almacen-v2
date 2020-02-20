package es.rfgr.cr4vegas.controlador;

import java.sql.Connection;

import es.rfgr.cr4vegas.modelo.postgresql.dao.alarma.AlarmaDAO;
import es.rfgr.cr4vegas.modelo.postgresql.dao.alarma.GeneraAlarmaEventoDAO;
import es.rfgr.cr4vegas.modelo.postgresql.dao.almacen.compra.CompraDAO;
import es.rfgr.cr4vegas.modelo.postgresql.dao.almacen.compra.CompraMaterialDAO;
import es.rfgr.cr4vegas.modelo.postgresql.dao.almacen.material.FamiliaDAO;
import es.rfgr.cr4vegas.modelo.postgresql.dao.almacen.material.MaterialDAO;
import es.rfgr.cr4vegas.modelo.postgresql.dao.almacen.material.PrecioDAO;
import es.rfgr.cr4vegas.modelo.postgresql.dao.almacen.material.TipoMaterialDAO;
import es.rfgr.cr4vegas.modelo.postgresql.dao.almacen.material.UbicacionDAO;
import es.rfgr.cr4vegas.modelo.postgresql.dao.almacen.presupuesto.PresupuestoDAO;
import es.rfgr.cr4vegas.modelo.postgresql.dao.almacen.presupuesto.TienePresupuestoMaterialDAO;
import es.rfgr.cr4vegas.modelo.postgresql.dao.almacen.taquilla.OperaTaquillaMaterialDAO;
import es.rfgr.cr4vegas.modelo.postgresql.dao.almacen.taquilla.TaquillaDAO;
import es.rfgr.cr4vegas.modelo.postgresql.dao.almacen.taquilla.TipoTaquillaDAO;
import es.rfgr.cr4vegas.modelo.postgresql.dao.almacen.tienda.DirTiendaDAO;
import es.rfgr.cr4vegas.modelo.postgresql.dao.almacen.tienda.TelTiendaDAO;
import es.rfgr.cr4vegas.modelo.postgresql.dao.almacen.tienda.TiendaDAO;
import es.rfgr.cr4vegas.modelo.postgresql.dao.evento.CreaEventoParteDAO;
import es.rfgr.cr4vegas.modelo.postgresql.dao.evento.EventoDAO;
import es.rfgr.cr4vegas.modelo.postgresql.dao.grupo.GrupoDAO;
import es.rfgr.cr4vegas.modelo.postgresql.dao.mantenimiento.estacion.EstacionDAO;
import es.rfgr.cr4vegas.modelo.postgresql.dao.mantenimiento.estacion.ImagenEstacionDAO;
import es.rfgr.cr4vegas.modelo.postgresql.dao.mantenimiento.estacion.RegistroEstacionDAO;
import es.rfgr.cr4vegas.modelo.postgresql.dao.mantenimiento.estacion.RegistroImagenEstacionDAO;
import es.rfgr.cr4vegas.modelo.postgresql.dao.mantenimiento.instalacion.ImagenInstalacionDAO;
import es.rfgr.cr4vegas.modelo.postgresql.dao.mantenimiento.instalacion.InstalacionDAO;
import es.rfgr.cr4vegas.modelo.postgresql.dao.mantenimiento.instalacion.RegistroImagenInstalacionDAO;
import es.rfgr.cr4vegas.modelo.postgresql.dao.mantenimiento.instalacion.RegistroInstalacionDAO;
import es.rfgr.cr4vegas.modelo.postgresql.dao.mantenimiento.instalacion.TipoInstalacionDAO;
import es.rfgr.cr4vegas.modelo.postgresql.dao.mantenimiento.unidaddecontrol.GeneraUnidadDeControlEventoDAO;
import es.rfgr.cr4vegas.modelo.postgresql.dao.mantenimiento.unidaddecontrol.ImagenUnidadDeControlDAO;
import es.rfgr.cr4vegas.modelo.postgresql.dao.mantenimiento.unidaddecontrol.MantenimientoUnidadDeControlDAO;
import es.rfgr.cr4vegas.modelo.postgresql.dao.mantenimiento.unidaddecontrol.RegistroImagenUnidadDeControlDAO;
import es.rfgr.cr4vegas.modelo.postgresql.dao.mantenimiento.unidaddecontrol.RegistroUnidadDeControlDAO;
import es.rfgr.cr4vegas.modelo.postgresql.dao.mantenimiento.unidaddecontrol.UnidadDeControlDAO;
import es.rfgr.cr4vegas.modelo.postgresql.dao.mantenimiento.vehiculo.GeneraVehiculoEventoDAO;
import es.rfgr.cr4vegas.modelo.postgresql.dao.mantenimiento.vehiculo.ImagenVehiculoDAO;
import es.rfgr.cr4vegas.modelo.postgresql.dao.mantenimiento.vehiculo.MantenimientoVehiculoDAO;
import es.rfgr.cr4vegas.modelo.postgresql.dao.mantenimiento.vehiculo.RegistroImagenVehiculoDAO;
import es.rfgr.cr4vegas.modelo.postgresql.dao.mantenimiento.vehiculo.RegistroVehiculoDAO;
import es.rfgr.cr4vegas.modelo.postgresql.dao.mantenimiento.vehiculo.VehiculoDAO;
import es.rfgr.cr4vegas.modelo.postgresql.dao.operario.CreaOperarioParteDAO;
import es.rfgr.cr4vegas.modelo.postgresql.dao.operario.DirOperarioDAO;
import es.rfgr.cr4vegas.modelo.postgresql.dao.operario.GeneraOperarioEventoDAO;
import es.rfgr.cr4vegas.modelo.postgresql.dao.operario.OperaOperarioMaterialDAO;
import es.rfgr.cr4vegas.modelo.postgresql.dao.operario.OperarioDAO;
import es.rfgr.cr4vegas.modelo.postgresql.dao.operario.TelOperarioDAO;
import es.rfgr.cr4vegas.modelo.postgresql.dao.parte.AsociaParteOperarioDAO;
import es.rfgr.cr4vegas.modelo.postgresql.dao.parte.GeneraParteEventoDAO;
import es.rfgr.cr4vegas.modelo.postgresql.dao.parte.OperaParteMaterialDAO;
import es.rfgr.cr4vegas.modelo.postgresql.dao.parte.ParteDAO;
import es.rfgr.cr4vegas.modelo.postgresql.dao.parte.TipoParteDAO;
import es.rfgr.cr4vegas.modelo.postgresql.dao.parteoficial.ParteOficialDAO;
import es.rfgr.cr4vegas.modelo.postgresql.exceptions.DAOException;
import es.rfgr.cr4vegas.vista.modelo.VistaModelo;

public class ControladorCR4VegasApp implements Consumer {

	private ControladorCR4Vegas service;

	public ControladorCR4VegasApp(ControladorCR4Vegas controladorMVC) {
		service = controladorMVC;
	}

	public void iniciarConexion(Connection conn) throws DAOException {
		service.iniciarConexion(conn);
	}

	public void comenzar() {
		service.comenzar();
	}

	/**
	 * --> Vista
	 */
	@Override
	public VistaModelo getVistaModelo() {
		return service.getVistaModelo();
	}

	/**
	 * --> Modelo
	 */
	public CompraDAO getCompraDAO() {
		return service.getCompraDAO();
	}

	public CompraMaterialDAO getCompraMaterialDAO() {
		return service.getCompraMaterialDAO();
	}

	public TiendaDAO getTiendaDAO() {
		return service.getTiendaDAO();
	}

	public DirTiendaDAO getDirTiendaDAO() {
		return service.getDirTiendaDAO();
	}

	public TelTiendaDAO getTelTiendaDAO() {
		return service.getTelTiendaDAO();
	}

	public MaterialDAO getMaterialDAO() {
		return service.getMaterialDAO();
	}

	public TipoMaterialDAO getTipoMaterialDAO() {
		return service.getTipoMaterialDAO();
	}

	public FamiliaDAO getFamiliaDAO() {
		return service.getFamiliaDAO();
	}

	public UbicacionDAO getUbicacionDAO() {
		return service.getUbicacionDAO();
	}

	public PresupuestoDAO getPresupuestoDAO() {
		return service.getPresupuestoDAO();
	}

	public TienePresupuestoMaterialDAO getTienePresupuestoMaterialDAO() {
		return service.getTienePresupuestoMaterialDAO();
	}

	public TaquillaDAO getTaquillaDAO() {
		return service.getTaquillaDAO();
	}

	public TipoTaquillaDAO getTipoTaquillaDAO() {
		return service.getTipoTaquillaDAO();
	}

	public OperaTaquillaMaterialDAO getOperaTaquillaMaterialDAO() {
		return service.getOperaTaquillaMaterialDAO();
	}

	public EventoDAO getEventoDAO() {
		return service.getEventoDAO();
	}

	public CreaEventoParteDAO getCreaEventoParteDAO() {
		return service.getCreaEventoParteDAO();
	}

	public GrupoDAO getGrupoDAO() {
		return service.getGrupoDAO();
	}

	public AlarmaDAO getAlarmaDAO() {
		return service.getAlarmaDAO();
	}

	public GeneraAlarmaEventoDAO getGeneraAlarmaEventoDAO() {
		return service.getGeneraAlarmaEventoDAO();
	}

	public EstacionDAO getEstacionDAO() {
		return service.getEstacionDAO();
	}

	public ImagenEstacionDAO getImagenEstacionDAO() {
		return service.getImagenEstacionDAO();
	}

	public RegistroEstacionDAO getRegistroEstacionDAO() {
		return service.getRegistroEstacionDAO();
	}

	public RegistroImagenEstacionDAO getRegistroImagenEstacionDAO() {
		return service.getRegistroImagenEstacionDAO();
	}

	public InstalacionDAO getInstalacionDAO() {
		return service.getInstalacionDAO();
	}

	public TipoInstalacionDAO getTipoInstalacionDAO() {
		return service.getTipoInstalacionDAO();
	}

	public ImagenInstalacionDAO getImagenInstalacionDAO() {
		return service.getImagenInstalacionDAO();
	}

	public RegistroInstalacionDAO getRegistroInstalacionDAO() {
		return service.getRegistroInstalacionDAO();
	}

	public RegistroImagenInstalacionDAO getRegistroImagenInstalacionDAO() {
		return service.getRegistroImagenInstalacionDAO();
	}

	public UnidadDeControlDAO getUnidadDeControlDAO() {
		return service.getUnidadDeControlDAO();
	}

	public ImagenUnidadDeControlDAO getImagenUnidadDeControlDAO() {
		return service.getImagenUnidadDeControlDAO();
	}

	public RegistroUnidadDeControlDAO getRegistroUnidadDeControlDAO() {
		return service.getRegistroUnidadDeControlDAO();
	}

	public RegistroImagenUnidadDeControlDAO getRegistroImagenUnidadDeControlDAO() {
		return service.getRegistroImagenUnidadDeControlDAO();
	}

	public GeneraUnidadDeControlEventoDAO getGeneraUnidadDeControlEventoDAO() {
		return service.getGeneraUnidadDeControlEventoDAO();
	}

	public MantenimientoUnidadDeControlDAO getMantenimientoUnidadDeControlDAO() {
		return service.getMantenimientoUnidadDeControlDAO();
	}

	public VehiculoDAO getVehiculoDAO() {
		return service.getVehiculoDAO();
	}

	public ImagenVehiculoDAO getImagenVehiculoDAO() {
		return service.getImagenVehiculoDAO();
	}

	public RegistroVehiculoDAO getRegistroVehiculoDAO() {
		return service.getRegistroVehiculoDAO();
	}

	public RegistroImagenVehiculoDAO getRegistroImagenVehiculoDAO() {
		return service.getRegistroImagenVehiculoDAO();
	}

	public GeneraVehiculoEventoDAO getGeneraVehiculoEventoDAO() {
		return service.getGeneraVehiculoEventoDAO();
	}

	public MantenimientoVehiculoDAO getMantenimientoVehiculoDAO() {
		return service.getMantenimientoVehiculoDAO();
	}

	public OperarioDAO getOperarioDAO() {
		return service.getOperarioDAO();
	}

	public DirOperarioDAO getDirOperarioDAO() {
		return service.getDirOperarioDAO();
	}

	public TelOperarioDAO getTelOperarioDAO() {
		return service.getTelOperarioDAO();
	}

	public CreaOperarioParteDAO getCreaOperarioParteDAO() {
		return service.getCreaOperarioParteDAO();
	}

	public GeneraOperarioEventoDAO getGeneraOperarioEventoDAO() {
		return service.getGeneraOperarioEventoDAO();
	}

	public OperaOperarioMaterialDAO getOperaOperarioMaterialDAO() {
		return service.getOperaOperarioMaterialDAO();
	}

	public ParteDAO getParteDAO() {
		return service.getParteDAO();
	}

	public ParteOficialDAO getParteOficialDAO() {
		return service.getParteOficialDAO();
	}

	public TipoParteDAO getTipoParteDAO() {
		return service.getTipoParteDAO();
	}

	public AsociaParteOperarioDAO getAsociaParteOperarioDAO() {
		return service.getAsociaParteOperarioDAO();
	}

	public GeneraParteEventoDAO getGeneraParteEventoDAO() {
		return service.getGeneraParteEventoDAO();
	}

	public OperaParteMaterialDAO getOperaParteMaterialDAO() {
		return service.getOperaParteMaterialDAO();
	}

	public PrecioDAO getPrecioDAO() {
		return service.getPrecioDAO();
	}

	/**
	 * --> INICIO
	 */
	@Override
	public void setAllConnections(Connection conn) {
		service.setAllConnections(conn);
	}

	@Override
	public void findAll() throws DAOException {
		service.findAll();
	}

	@Override
	public void createAllDependencies() {
		service.createAllDependencies();
	}

}
