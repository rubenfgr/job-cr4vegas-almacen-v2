package es.rfgr.cr4vegas.controlador;

import java.sql.Connection;

import es.rfgr.cr4vegas.modelo.ModeloCR4Vegas;
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
import es.rfgr.cr4vegas.vista.VistaCR4Vegas;
import es.rfgr.cr4vegas.vista.modelo.VistaModelo;

public class ControladorCR4VegasImp implements ControladorCR4Vegas {

	private ModeloCR4Vegas modelo;

	private VistaCR4Vegas vista;

	private VistaModelo vistaModelo;

	public ControladorCR4VegasImp(ModeloCR4Vegas modelo, VistaCR4Vegas vista, VistaModelo vistaModelo) {
		this.modelo = modelo;
		this.vista = vista;
		this.vistaModelo = vistaModelo;
	}

	public void comenzar() {
		vista.comenzar();
	}

	public void iniciarConexion(Connection conn) throws DAOException {
		modelo.iniciarConexion(conn);
	}

	/**
	 * --> Vista
	 */
	@Override
	public VistaModelo getVistaModelo() {
		return this.vistaModelo;
	}

	/**
	 * --> Modelo
	 */
	public CompraDAO getCompraDAO() {
		return modelo.getCompraDAO();
	}

	public CompraMaterialDAO getCompraMaterialDAO() {
		return modelo.getCompraMaterialDAO();
	}

	public TiendaDAO getTiendaDAO() {
		return modelo.getTiendaDAO();
	}

	public DirTiendaDAO getDirTiendaDAO() {
		return modelo.getDirTiendaDAO();
	}

	public TelTiendaDAO getTelTiendaDAO() {
		return modelo.getTelTiendaDAO();
	}

	public MaterialDAO getMaterialDAO() {
		return modelo.getMaterialDAO();
	}

	public TipoMaterialDAO getTipoMaterialDAO() {
		return modelo.getTipoMaterialDAO();
	}

	public FamiliaDAO getFamiliaDAO() {
		return modelo.getFamiliaDAO();
	}

	public UbicacionDAO getUbicacionDAO() {
		return modelo.getUbicacionDAO();
	}

	public PresupuestoDAO getPresupuestoDAO() {
		return modelo.getPresupuestoDAO();
	}

	public TienePresupuestoMaterialDAO getTienePresupuestoMaterialDAO() {
		return modelo.getTienePresupuestoMaterialDAO();
	}

	public TaquillaDAO getTaquillaDAO() {
		return modelo.getTaquillaDAO();
	}

	public TipoTaquillaDAO getTipoTaquillaDAO() {
		return modelo.getTipoTaquillaDAO();
	}

	public OperaTaquillaMaterialDAO getOperaTaquillaMaterialDAO() {
		return modelo.getOperaTaquillaMaterialDAO();
	}

	public EventoDAO getEventoDAO() {
		return modelo.getEventoDAO();
	}

	public CreaEventoParteDAO getCreaEventoParteDAO() {
		return modelo.getCreaEventoParteDAO();
	}

	public GrupoDAO getGrupoDAO() {
		return modelo.getGrupoDAO();
	}

	public AlarmaDAO getAlarmaDAO() {
		return modelo.getAlarmaDAO();
	}

	public GeneraAlarmaEventoDAO getGeneraAlarmaEventoDAO() {
		return modelo.getGeneraAlarmaEventoDAO();
	}

	public EstacionDAO getEstacionDAO() {
		return modelo.getEstacionDAO();
	}

	public ImagenEstacionDAO getImagenEstacionDAO() {
		return modelo.getImagenEstacionDAO();
	}

	public RegistroEstacionDAO getRegistroEstacionDAO() {
		return modelo.getRegistroEstacionDAO();
	}

	public RegistroImagenEstacionDAO getRegistroImagenEstacionDAO() {
		return modelo.getRegistroImagenEstacionDAO();
	}

	public InstalacionDAO getInstalacionDAO() {
		return modelo.getInstalacionDAO();
	}

	public TipoInstalacionDAO getTipoInstalacionDAO() {
		return modelo.getTipoInstalacionDAO();
	}

	public ImagenInstalacionDAO getImagenInstalacionDAO() {
		return modelo.getImagenInstalacionDAO();
	}

	public RegistroInstalacionDAO getRegistroInstalacionDAO() {
		return modelo.getRegistroInstalacionDAO();
	}

	public RegistroImagenInstalacionDAO getRegistroImagenInstalacionDAO() {
		return modelo.getRegistroImagenInstalacionDAO();
	}

	public UnidadDeControlDAO getUnidadDeControlDAO() {
		return modelo.getUnidadDeControlDAO();
	}

	public ImagenUnidadDeControlDAO getImagenUnidadDeControlDAO() {
		return modelo.getImagenUnidadDeControlDAO();
	}

	public RegistroUnidadDeControlDAO getRegistroUnidadDeControlDAO() {
		return modelo.getRegistroUnidadDeControlDAO();
	}

	public RegistroImagenUnidadDeControlDAO getRegistroImagenUnidadDeControlDAO() {
		return modelo.getRegistroImagenUnidadDeControlDAO();
	}

	public GeneraUnidadDeControlEventoDAO getGeneraUnidadDeControlEventoDAO() {
		return modelo.getGeneraUnidadDeControlEventoDAO();
	}

	public MantenimientoUnidadDeControlDAO getMantenimientoUnidadDeControlDAO() {
		return modelo.getMantenimientoUnidadDeControlDAO();
	}

	public VehiculoDAO getVehiculoDAO() {
		return modelo.getVehiculoDAO();
	}

	public ImagenVehiculoDAO getImagenVehiculoDAO() {
		return modelo.getImagenVehiculoDAO();
	}

	public RegistroVehiculoDAO getRegistroVehiculoDAO() {
		return modelo.getRegistroVehiculoDAO();
	}

	public RegistroImagenVehiculoDAO getRegistroImagenVehiculoDAO() {
		return modelo.getRegistroImagenVehiculoDAO();
	}

	public GeneraVehiculoEventoDAO getGeneraVehiculoEventoDAO() {
		return modelo.getGeneraVehiculoEventoDAO();
	}

	public MantenimientoVehiculoDAO getMantenimientoVehiculoDAO() {
		return modelo.getMantenimientoVehiculoDAO();
	}

	public OperarioDAO getOperarioDAO() {
		return modelo.getOperarioDAO();
	}

	public DirOperarioDAO getDirOperarioDAO() {
		return modelo.getDirOperarioDAO();
	}

	public TelOperarioDAO getTelOperarioDAO() {
		return modelo.getTelOperarioDAO();
	}

	public CreaOperarioParteDAO getCreaOperarioParteDAO() {
		return modelo.getCreaOperarioParteDAO();
	}

	public GeneraOperarioEventoDAO getGeneraOperarioEventoDAO() {
		return modelo.getGeneraOperarioEventoDAO();
	}

	public OperaOperarioMaterialDAO getOperaOperarioMaterialDAO() {
		return modelo.getOperaOperarioMaterialDAO();
	}

	public ParteDAO getParteDAO() {
		return modelo.getParteDAO();
	}

	public ParteOficialDAO getParteOficialDAO() {
		return modelo.getParteOficialDAO();
	}

	public TipoParteDAO getTipoParteDAO() {
		return modelo.getTipoParteDAO();
	}

	public AsociaParteOperarioDAO getAsociaParteOperarioDAO() {
		return modelo.getAsociaParteOperarioDAO();
	}

	public GeneraParteEventoDAO getGeneraParteEventoDAO() {
		return modelo.getGeneraParteEventoDAO();
	}

	public OperaParteMaterialDAO getOperaParteMaterialDAO() {
		return modelo.getOperaParteMaterialDAO();
	}

	public PrecioDAO getPrecioDAO() {
		return modelo.getPrecioDAO();
	}

	/**
	 * --> INICIO
	 */
	@Override
	public void setAllConnections(Connection conn) {
		modelo.setAllConnections(conn);
	}

	@Override
	public void findAll() throws DAOException {
		modelo.findAll();
	}

	@Override
	public void createAllDependencies() {
		modelo.createAllDependencies();
	}

}
