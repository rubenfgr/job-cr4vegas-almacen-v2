package es.rfgr.cr4vegas.modelo.postgresql;

import java.sql.Connection;
import java.sql.SQLException;

import es.rfgr.cr4vegas.modelo.ModeloCR4Vegas;
import es.rfgr.cr4vegas.modelo.postgresql.conexion.ConexionAlmacen;
import es.rfgr.cr4vegas.modelo.postgresql.dao.alarma.AlarmaDAO;
import es.rfgr.cr4vegas.modelo.postgresql.dao.alarma.GeneraAlarmaEventoDAO;
import es.rfgr.cr4vegas.modelo.postgresql.dao.alarma.imp.AlarmaDAOImp;
import es.rfgr.cr4vegas.modelo.postgresql.dao.alarma.imp.GeneraAlarmaEventoDAOImp;
import es.rfgr.cr4vegas.modelo.postgresql.dao.almacen.compra.CompraDAO;
import es.rfgr.cr4vegas.modelo.postgresql.dao.almacen.compra.CompraMaterialDAO;
import es.rfgr.cr4vegas.modelo.postgresql.dao.almacen.compra.imp.CompraDAOImp;
import es.rfgr.cr4vegas.modelo.postgresql.dao.almacen.compra.imp.CompraMaterialDAOImp;
import es.rfgr.cr4vegas.modelo.postgresql.dao.almacen.material.FamiliaDAO;
import es.rfgr.cr4vegas.modelo.postgresql.dao.almacen.material.MaterialDAO;
import es.rfgr.cr4vegas.modelo.postgresql.dao.almacen.material.PrecioDAO;
import es.rfgr.cr4vegas.modelo.postgresql.dao.almacen.material.TipoMaterialDAO;
import es.rfgr.cr4vegas.modelo.postgresql.dao.almacen.material.UbicacionDAO;
import es.rfgr.cr4vegas.modelo.postgresql.dao.almacen.material.imp.FamiliaDAOImp;
import es.rfgr.cr4vegas.modelo.postgresql.dao.almacen.material.imp.MaterialDAOImp;
import es.rfgr.cr4vegas.modelo.postgresql.dao.almacen.material.imp.PrecioDAOImp;
import es.rfgr.cr4vegas.modelo.postgresql.dao.almacen.material.imp.TipoMaterialDAOImp;
import es.rfgr.cr4vegas.modelo.postgresql.dao.almacen.material.imp.UbicacionDAOImp;
import es.rfgr.cr4vegas.modelo.postgresql.dao.almacen.presupuesto.PresupuestoDAO;
import es.rfgr.cr4vegas.modelo.postgresql.dao.almacen.presupuesto.TienePresupuestoMaterialDAO;
import es.rfgr.cr4vegas.modelo.postgresql.dao.almacen.presupuesto.imp.PresupuestoDAOImp;
import es.rfgr.cr4vegas.modelo.postgresql.dao.almacen.presupuesto.imp.TienePresupuestoMaterialDAOImp;
import es.rfgr.cr4vegas.modelo.postgresql.dao.almacen.taquilla.OperaTaquillaMaterialDAO;
import es.rfgr.cr4vegas.modelo.postgresql.dao.almacen.taquilla.TaquillaDAO;
import es.rfgr.cr4vegas.modelo.postgresql.dao.almacen.taquilla.TipoTaquillaDAO;
import es.rfgr.cr4vegas.modelo.postgresql.dao.almacen.taquilla.imp.OperaTaquillaMaterialDAOImp;
import es.rfgr.cr4vegas.modelo.postgresql.dao.almacen.taquilla.imp.TaquillaDAOImp;
import es.rfgr.cr4vegas.modelo.postgresql.dao.almacen.taquilla.imp.TipoTaquillaDAOImp;
import es.rfgr.cr4vegas.modelo.postgresql.dao.almacen.tienda.DirTiendaDAO;
import es.rfgr.cr4vegas.modelo.postgresql.dao.almacen.tienda.TelTiendaDAO;
import es.rfgr.cr4vegas.modelo.postgresql.dao.almacen.tienda.TiendaDAO;
import es.rfgr.cr4vegas.modelo.postgresql.dao.almacen.tienda.imp.DirTiendaDAOImp;
import es.rfgr.cr4vegas.modelo.postgresql.dao.almacen.tienda.imp.TelTiendaDAOImp;
import es.rfgr.cr4vegas.modelo.postgresql.dao.almacen.tienda.imp.TiendaDAOImp;
import es.rfgr.cr4vegas.modelo.postgresql.dao.evento.CreaEventoParteDAO;
import es.rfgr.cr4vegas.modelo.postgresql.dao.evento.EventoDAO;
import es.rfgr.cr4vegas.modelo.postgresql.dao.evento.imp.CreaEventoParteDAOImp;
import es.rfgr.cr4vegas.modelo.postgresql.dao.evento.imp.EventoDAOImp;
import es.rfgr.cr4vegas.modelo.postgresql.dao.grupo.GrupoDAO;
import es.rfgr.cr4vegas.modelo.postgresql.dao.grupo.imp.GrupoDAOImp;
import es.rfgr.cr4vegas.modelo.postgresql.dao.mantenimiento.estacion.EstacionDAO;
import es.rfgr.cr4vegas.modelo.postgresql.dao.mantenimiento.estacion.ImagenEstacionDAO;
import es.rfgr.cr4vegas.modelo.postgresql.dao.mantenimiento.estacion.RegistroEstacionDAO;
import es.rfgr.cr4vegas.modelo.postgresql.dao.mantenimiento.estacion.RegistroImagenEstacionDAO;
import es.rfgr.cr4vegas.modelo.postgresql.dao.mantenimiento.estacion.imp.EstacionDAOImp;
import es.rfgr.cr4vegas.modelo.postgresql.dao.mantenimiento.estacion.imp.ImagenEstacionDAOImp;
import es.rfgr.cr4vegas.modelo.postgresql.dao.mantenimiento.estacion.imp.RegistroEstacionDAOImp;
import es.rfgr.cr4vegas.modelo.postgresql.dao.mantenimiento.estacion.imp.RegistroImagenEstacionDAOImp;
import es.rfgr.cr4vegas.modelo.postgresql.dao.mantenimiento.instalacion.ImagenInstalacionDAO;
import es.rfgr.cr4vegas.modelo.postgresql.dao.mantenimiento.instalacion.InstalacionDAO;
import es.rfgr.cr4vegas.modelo.postgresql.dao.mantenimiento.instalacion.RegistroImagenInstalacionDAO;
import es.rfgr.cr4vegas.modelo.postgresql.dao.mantenimiento.instalacion.RegistroInstalacionDAO;
import es.rfgr.cr4vegas.modelo.postgresql.dao.mantenimiento.instalacion.TipoInstalacionDAO;
import es.rfgr.cr4vegas.modelo.postgresql.dao.mantenimiento.instalacion.imp.ImagenInstalacionDAOImp;
import es.rfgr.cr4vegas.modelo.postgresql.dao.mantenimiento.instalacion.imp.InstalacionDAOImp;
import es.rfgr.cr4vegas.modelo.postgresql.dao.mantenimiento.instalacion.imp.RegistroImagenInstalacionDAOImp;
import es.rfgr.cr4vegas.modelo.postgresql.dao.mantenimiento.instalacion.imp.RegistroInstalacionDAOImp;
import es.rfgr.cr4vegas.modelo.postgresql.dao.mantenimiento.instalacion.imp.TipoInstalacionDAOImp;
import es.rfgr.cr4vegas.modelo.postgresql.dao.mantenimiento.unidaddecontrol.GeneraUnidadDeControlEventoDAO;
import es.rfgr.cr4vegas.modelo.postgresql.dao.mantenimiento.unidaddecontrol.ImagenUnidadDeControlDAO;
import es.rfgr.cr4vegas.modelo.postgresql.dao.mantenimiento.unidaddecontrol.MantenimientoUnidadDeControlDAO;
import es.rfgr.cr4vegas.modelo.postgresql.dao.mantenimiento.unidaddecontrol.RegistroImagenUnidadDeControlDAO;
import es.rfgr.cr4vegas.modelo.postgresql.dao.mantenimiento.unidaddecontrol.RegistroUnidadDeControlDAO;
import es.rfgr.cr4vegas.modelo.postgresql.dao.mantenimiento.unidaddecontrol.UnidadDeControlDAO;
import es.rfgr.cr4vegas.modelo.postgresql.dao.mantenimiento.unidaddecontrol.imp.GeneraUnidadDeControlEventoDAOImp;
import es.rfgr.cr4vegas.modelo.postgresql.dao.mantenimiento.unidaddecontrol.imp.ImagenUnidadDeControlDAOImp;
import es.rfgr.cr4vegas.modelo.postgresql.dao.mantenimiento.unidaddecontrol.imp.MantenimientoUnidadDeControlDAOImp;
import es.rfgr.cr4vegas.modelo.postgresql.dao.mantenimiento.unidaddecontrol.imp.RegistroImagenUnidadDeControlDAOImp;
import es.rfgr.cr4vegas.modelo.postgresql.dao.mantenimiento.unidaddecontrol.imp.RegistroUnidadDeControlDAOImp;
import es.rfgr.cr4vegas.modelo.postgresql.dao.mantenimiento.unidaddecontrol.imp.UnidadDeControlDAOImp;
import es.rfgr.cr4vegas.modelo.postgresql.dao.mantenimiento.vehiculo.GeneraVehiculoEventoDAO;
import es.rfgr.cr4vegas.modelo.postgresql.dao.mantenimiento.vehiculo.ImagenVehiculoDAO;
import es.rfgr.cr4vegas.modelo.postgresql.dao.mantenimiento.vehiculo.MantenimientoVehiculoDAO;
import es.rfgr.cr4vegas.modelo.postgresql.dao.mantenimiento.vehiculo.RegistroImagenVehiculoDAO;
import es.rfgr.cr4vegas.modelo.postgresql.dao.mantenimiento.vehiculo.RegistroVehiculoDAO;
import es.rfgr.cr4vegas.modelo.postgresql.dao.mantenimiento.vehiculo.VehiculoDAO;
import es.rfgr.cr4vegas.modelo.postgresql.dao.mantenimiento.vehiculo.imp.GeneraVehiculoEventoDAOImp;
import es.rfgr.cr4vegas.modelo.postgresql.dao.mantenimiento.vehiculo.imp.ImagenVehiculoDAOImp;
import es.rfgr.cr4vegas.modelo.postgresql.dao.mantenimiento.vehiculo.imp.MantenimientoVehiculoDAOImp;
import es.rfgr.cr4vegas.modelo.postgresql.dao.mantenimiento.vehiculo.imp.RegistroImagenVehiculoDAOImp;
import es.rfgr.cr4vegas.modelo.postgresql.dao.mantenimiento.vehiculo.imp.RegistroVehiculoDAOImp;
import es.rfgr.cr4vegas.modelo.postgresql.dao.mantenimiento.vehiculo.imp.VehiculoDAOImp;
import es.rfgr.cr4vegas.modelo.postgresql.dao.operario.CreaOperarioParteDAO;
import es.rfgr.cr4vegas.modelo.postgresql.dao.operario.DirOperarioDAO;
import es.rfgr.cr4vegas.modelo.postgresql.dao.operario.GeneraOperarioEventoDAO;
import es.rfgr.cr4vegas.modelo.postgresql.dao.operario.OperaOperarioMaterialDAO;
import es.rfgr.cr4vegas.modelo.postgresql.dao.operario.OperarioDAO;
import es.rfgr.cr4vegas.modelo.postgresql.dao.operario.TelOperarioDAO;
import es.rfgr.cr4vegas.modelo.postgresql.dao.operario.imp.CreaOperarioParteDAOImp;
import es.rfgr.cr4vegas.modelo.postgresql.dao.operario.imp.DirOperarioDAOImp;
import es.rfgr.cr4vegas.modelo.postgresql.dao.operario.imp.GeneraOperarioEventoDAOImp;
import es.rfgr.cr4vegas.modelo.postgresql.dao.operario.imp.OperaOperarioMaterialDAOImp;
import es.rfgr.cr4vegas.modelo.postgresql.dao.operario.imp.OperarioDAOImp;
import es.rfgr.cr4vegas.modelo.postgresql.dao.operario.imp.TelOperarioDAOImp;
import es.rfgr.cr4vegas.modelo.postgresql.dao.parte.AsociaParteOperarioDAO;
import es.rfgr.cr4vegas.modelo.postgresql.dao.parte.GeneraParteEventoDAO;
import es.rfgr.cr4vegas.modelo.postgresql.dao.parte.OperaParteMaterialDAO;
import es.rfgr.cr4vegas.modelo.postgresql.dao.parte.ParteDAO;
import es.rfgr.cr4vegas.modelo.postgresql.dao.parte.TipoParteDAO;
import es.rfgr.cr4vegas.modelo.postgresql.dao.parte.imp.AsociaParteOperarioDAOImp;
import es.rfgr.cr4vegas.modelo.postgresql.dao.parte.imp.GeneraParteEventoDAOImp;
import es.rfgr.cr4vegas.modelo.postgresql.dao.parte.imp.OperaParteMaterialDAOImp;
import es.rfgr.cr4vegas.modelo.postgresql.dao.parte.imp.ParteDAOImp;
import es.rfgr.cr4vegas.modelo.postgresql.dao.parte.imp.TipoParteDAOImp;
import es.rfgr.cr4vegas.modelo.postgresql.dao.parteoficial.ParteOficialDAO;
import es.rfgr.cr4vegas.modelo.postgresql.dao.parteoficial.imp.ParteOficialDAOImp;
import es.rfgr.cr4vegas.modelo.postgresql.exceptions.DAOException;

public class ModeloCR4VegasImp implements ModeloCR4Vegas {

	private CompraDAO compraDAO;
	private CompraMaterialDAO compraMaterialDAO;
	private TiendaDAO tiendaDAO;
	private DirTiendaDAO dirTiendaDAO;
	private TelTiendaDAO telTiendaDAO;
	private MaterialDAO materialDAO;
	private TipoMaterialDAO tipoMaterialDAO;
	private FamiliaDAO familiaDAO;
	private UbicacionDAO ubicacionDAO;
	private PresupuestoDAO presupuestoDAO;
	private TienePresupuestoMaterialDAO tienePresupuestoMaterialDAO;
	private TaquillaDAO taquillaDAO;
	private TipoTaquillaDAO tipoTaquillaDAO;
	private OperaTaquillaMaterialDAO operaTaquillaMaterialDAO;
	private EventoDAO eventoDAO;
	private CreaEventoParteDAO creaEventoParteDAO;
	private GrupoDAO grupoDAO;
	private AlarmaDAO alarmaDAO;
	private GeneraAlarmaEventoDAO generaAlarmaEventoDAO;
	private EstacionDAO estacionDAO;
	private ImagenEstacionDAO imagenEstacionDAO;
	private RegistroEstacionDAO registroEstacionDAO;
	private RegistroImagenEstacionDAO registroImagenEstacionDAO;
	private InstalacionDAO instalacionDAO;
	private TipoInstalacionDAO tipoInstalacionDAO;
	private ImagenInstalacionDAO imagenInstalacionDAO;
	private RegistroInstalacionDAO registroInstalacionDAO;
	private RegistroImagenInstalacionDAO registroImagenInstalacionDAO;
	private UnidadDeControlDAO unidadDeControlDAO;
	private ImagenUnidadDeControlDAO imagenUnidadDeControlDAO;
	private RegistroUnidadDeControlDAO registroUnidadDeControlDAO;
	private RegistroImagenUnidadDeControlDAO registroImagenUnidadDeControlDAO;
	private GeneraUnidadDeControlEventoDAO generaUnidadDeControlEventoDAO;
	private MantenimientoUnidadDeControlDAO mantenimientoUnidadDeControlDAO;
	private VehiculoDAO vehiculoDAO;
	private ImagenVehiculoDAO imagenVehiculoDAO;
	private RegistroVehiculoDAO registroVehiculoDAO;
	private RegistroImagenVehiculoDAO registroImagenVehiculoDAO;
	private GeneraVehiculoEventoDAO generaVehiculoEventoDAO;
	private MantenimientoVehiculoDAO mantenimientoVehiculoDAO;
	private OperarioDAO operarioDAO;
	private DirOperarioDAO dirOperarioDAO;
	private TelOperarioDAO telOperarioDAO;
	private CreaOperarioParteDAO creaOperarioParteDAO;
	private GeneraOperarioEventoDAO generaOperarioEventoDAO;
	private OperaOperarioMaterialDAO operaOperarioMaterialDAO;
	private ParteDAO parteDAO;
	private ParteOficialDAO parteOficialDAO;
	private TipoParteDAO tipoParteDAO;
	private AsociaParteOperarioDAO asociaParteOperarioDAO;
	private GeneraParteEventoDAO generaParteEventoDAO;
	private OperaParteMaterialDAO operaParteMaterialDAO;
	private PrecioDAO precioDAO;

	public ModeloCR4VegasImp() throws DAOException {
		super();
		this.compraDAO = new CompraDAOImp();
		this.compraMaterialDAO = new CompraMaterialDAOImp();
		this.tiendaDAO = new TiendaDAOImp();
		this.dirTiendaDAO = new DirTiendaDAOImp();
		this.telTiendaDAO = new TelTiendaDAOImp();
		this.materialDAO = new MaterialDAOImp();
		this.tipoMaterialDAO = new TipoMaterialDAOImp();
		this.familiaDAO = new FamiliaDAOImp();
		this.ubicacionDAO = new UbicacionDAOImp();
		this.presupuestoDAO = new PresupuestoDAOImp();
		this.tienePresupuestoMaterialDAO = new TienePresupuestoMaterialDAOImp();
		this.taquillaDAO = new TaquillaDAOImp();
		this.tipoTaquillaDAO = new TipoTaquillaDAOImp();
		this.operaTaquillaMaterialDAO = new OperaTaquillaMaterialDAOImp();
		this.eventoDAO = new EventoDAOImp();
		this.creaEventoParteDAO = new CreaEventoParteDAOImp();
		this.grupoDAO = new GrupoDAOImp();
		this.alarmaDAO = new AlarmaDAOImp();
		this.generaAlarmaEventoDAO = new GeneraAlarmaEventoDAOImp();
		this.estacionDAO = new EstacionDAOImp();
		this.imagenEstacionDAO = new ImagenEstacionDAOImp();
		this.registroEstacionDAO = new RegistroEstacionDAOImp();
		this.registroImagenEstacionDAO = new RegistroImagenEstacionDAOImp();
		this.instalacionDAO = new InstalacionDAOImp();
		this.tipoInstalacionDAO = new TipoInstalacionDAOImp();
		this.imagenInstalacionDAO = new ImagenInstalacionDAOImp();
		this.registroInstalacionDAO = new RegistroInstalacionDAOImp();
		this.registroImagenInstalacionDAO = new RegistroImagenInstalacionDAOImp();
		this.unidadDeControlDAO = new UnidadDeControlDAOImp();
		this.imagenUnidadDeControlDAO = new ImagenUnidadDeControlDAOImp();
		this.registroUnidadDeControlDAO = new RegistroUnidadDeControlDAOImp();
		this.registroImagenUnidadDeControlDAO = new RegistroImagenUnidadDeControlDAOImp();
		this.generaUnidadDeControlEventoDAO = new GeneraUnidadDeControlEventoDAOImp();
		this.mantenimientoUnidadDeControlDAO = new MantenimientoUnidadDeControlDAOImp();
		this.vehiculoDAO = new VehiculoDAOImp();
		this.imagenVehiculoDAO = new ImagenVehiculoDAOImp();
		this.registroVehiculoDAO = new RegistroVehiculoDAOImp();
		this.registroImagenVehiculoDAO = new RegistroImagenVehiculoDAOImp();
		this.generaVehiculoEventoDAO = new GeneraVehiculoEventoDAOImp();
		this.mantenimientoVehiculoDAO = new MantenimientoVehiculoDAOImp();
		this.operarioDAO = new OperarioDAOImp();
		this.dirOperarioDAO = new DirOperarioDAOImp();
		this.telOperarioDAO = new TelOperarioDAOImp();
		this.creaOperarioParteDAO = new CreaOperarioParteDAOImp();
		this.generaOperarioEventoDAO = new GeneraOperarioEventoDAOImp();
		this.operaOperarioMaterialDAO = new OperaOperarioMaterialDAOImp();
		this.parteDAO = new ParteDAOImp();
		this.parteOficialDAO = new ParteOficialDAOImp(parteDAO);
		this.tipoParteDAO = new TipoParteDAOImp();
		this.asociaParteOperarioDAO = new AsociaParteOperarioDAOImp();
		this.generaParteEventoDAO = new GeneraParteEventoDAOImp();
		this.operaParteMaterialDAO = new OperaParteMaterialDAOImp();
		this.precioDAO = new PrecioDAOImp();
	}

	public void iniciarConexion(Connection conn) throws DAOException {
		setAllConnections(conn);
		findAll();
		try {
			ConexionAlmacen.close(conn);
		} catch (SQLException e) {
			throw new DAOException(e.getMessage(), e);
		}
		createAllDependencies();
	}

	public CompraDAO getCompraDAO() {
		return this.compraDAO;
	}

	public CompraMaterialDAO getCompraMaterialDAO() {
		return this.compraMaterialDAO;
	}

	public TiendaDAO getTiendaDAO() {
		return this.tiendaDAO;
	}

	public DirTiendaDAO getDirTiendaDAO() {
		return this.dirTiendaDAO;
	}

	public TelTiendaDAO getTelTiendaDAO() {
		return this.telTiendaDAO;
	}

	public MaterialDAO getMaterialDAO() {
		return this.materialDAO;
	}

	public TipoMaterialDAO getTipoMaterialDAO() {
		return this.tipoMaterialDAO;
	}

	public FamiliaDAO getFamiliaDAO() {
		return this.familiaDAO;
	}

	public UbicacionDAO getUbicacionDAO() {
		return this.ubicacionDAO;
	}

	public PresupuestoDAO getPresupuestoDAO() {
		return this.presupuestoDAO;
	}

	public TienePresupuestoMaterialDAO getTienePresupuestoMaterialDAO() {
		return this.tienePresupuestoMaterialDAO;
	}

	public TaquillaDAO getTaquillaDAO() {
		return this.taquillaDAO;
	}

	public TipoTaquillaDAO getTipoTaquillaDAO() {
		return this.tipoTaquillaDAO;
	}

	public OperaTaquillaMaterialDAO getOperaTaquillaMaterialDAO() {
		return this.operaTaquillaMaterialDAO;
	}

	public EventoDAO getEventoDAO() {
		return this.eventoDAO;
	}

	public CreaEventoParteDAO getCreaEventoParteDAO() {
		return this.creaEventoParteDAO;
	}

	public GrupoDAO getGrupoDAO() {
		return this.grupoDAO;
	}

	public AlarmaDAO getAlarmaDAO() {
		return this.alarmaDAO;
	}

	public GeneraAlarmaEventoDAO getGeneraAlarmaEventoDAO() {
		return this.generaAlarmaEventoDAO;
	}

	public EstacionDAO getEstacionDAO() {
		return this.estacionDAO;
	}

	public ImagenEstacionDAO getImagenEstacionDAO() {
		return this.imagenEstacionDAO;
	}

	public RegistroEstacionDAO getRegistroEstacionDAO() {
		return this.registroEstacionDAO;
	}

	public RegistroImagenEstacionDAO getRegistroImagenEstacionDAO() {
		return this.registroImagenEstacionDAO;
	}

	public InstalacionDAO getInstalacionDAO() {
		return this.instalacionDAO;
	}

	public TipoInstalacionDAO getTipoInstalacionDAO() {
		return this.tipoInstalacionDAO;
	}

	public ImagenInstalacionDAO getImagenInstalacionDAO() {
		return this.imagenInstalacionDAO;
	}

	public RegistroInstalacionDAO getRegistroInstalacionDAO() {
		return this.registroInstalacionDAO;
	}

	public RegistroImagenInstalacionDAO getRegistroImagenInstalacionDAO() {
		return this.registroImagenInstalacionDAO;
	}

	public UnidadDeControlDAO getUnidadDeControlDAO() {
		return this.unidadDeControlDAO;
	}

	public ImagenUnidadDeControlDAO getImagenUnidadDeControlDAO() {
		return this.imagenUnidadDeControlDAO;
	}

	public RegistroUnidadDeControlDAO getRegistroUnidadDeControlDAO() {
		return this.registroUnidadDeControlDAO;
	}

	public RegistroImagenUnidadDeControlDAO getRegistroImagenUnidadDeControlDAO() {
		return this.registroImagenUnidadDeControlDAO;
	}

	public GeneraUnidadDeControlEventoDAO getGeneraUnidadDeControlEventoDAO() {
		return this.generaUnidadDeControlEventoDAO;
	}

	public MantenimientoUnidadDeControlDAO getMantenimientoUnidadDeControlDAO() {
		return this.mantenimientoUnidadDeControlDAO;
	}

	public VehiculoDAO getVehiculoDAO() {
		return this.vehiculoDAO;
	}

	public ImagenVehiculoDAO getImagenVehiculoDAO() {
		return this.imagenVehiculoDAO;
	}

	public RegistroVehiculoDAO getRegistroVehiculoDAO() {
		return this.registroVehiculoDAO;
	}

	public RegistroImagenVehiculoDAO getRegistroImagenVehiculoDAO() {
		return this.registroImagenVehiculoDAO;
	}

	public GeneraVehiculoEventoDAO getGeneraVehiculoEventoDAO() {
		return this.generaVehiculoEventoDAO;
	}

	public MantenimientoVehiculoDAO getMantenimientoVehiculoDAO() {
		return this.mantenimientoVehiculoDAO;
	}

	public OperarioDAO getOperarioDAO() {
		return this.operarioDAO;
	}

	public DirOperarioDAO getDirOperarioDAO() {
		return this.dirOperarioDAO;
	}

	public TelOperarioDAO getTelOperarioDAO() {
		return this.telOperarioDAO;
	}

	public CreaOperarioParteDAO getCreaOperarioParteDAO() {
		return this.creaOperarioParteDAO;
	}

	public GeneraOperarioEventoDAO getGeneraOperarioEventoDAO() {
		return this.generaOperarioEventoDAO;
	}

	public OperaOperarioMaterialDAO getOperaOperarioMaterialDAO() {
		return this.operaOperarioMaterialDAO;
	}

	public ParteDAO getParteDAO() {
		return this.parteDAO;
	}

	public ParteOficialDAO getParteOficialDAO() {
		return this.parteOficialDAO;
	}

	public TipoParteDAO getTipoParteDAO() {
		return this.tipoParteDAO;
	}

	public AsociaParteOperarioDAO getAsociaParteOperarioDAO() {
		return this.asociaParteOperarioDAO;
	}

	public GeneraParteEventoDAO getGeneraParteEventoDAO() {
		return this.generaParteEventoDAO;
	}

	public OperaParteMaterialDAO getOperaParteMaterialDAO() {
		return this.operaParteMaterialDAO;
	}

	public PrecioDAO getPrecioDAO() {
		return this.precioDAO;
	}

	/**
	 * --> INICIAR
	 */
	public void setAllConnections(Connection conn) {
		compraDAO.setConnection(conn);
		compraMaterialDAO.setConnection(conn);
		tiendaDAO.setConnection(conn);
		dirTiendaDAO.setConnection(conn);
		telTiendaDAO.setConnection(conn);
		materialDAO.setConnection(conn);
		tipoMaterialDAO.setConnection(conn);
		familiaDAO.setConnection(conn);
		ubicacionDAO.setConnection(conn);
		presupuestoDAO.setConnection(conn);
		tienePresupuestoMaterialDAO.setConnection(conn);
		taquillaDAO.setConnection(conn);
		tipoTaquillaDAO.setConnection(conn);
		operaTaquillaMaterialDAO.setConnection(conn);
		eventoDAO.setConnection(conn);
		creaEventoParteDAO.setConnection(conn);
		grupoDAO.setConnection(conn);
		alarmaDAO.setConnection(conn);
		generaAlarmaEventoDAO.setConnection(conn);
		estacionDAO.setConnection(conn);
		imagenEstacionDAO.setConnection(conn);
		registroEstacionDAO.setConnection(conn);
		registroImagenEstacionDAO.setConnection(conn);
		instalacionDAO.setConnection(conn);
		tipoInstalacionDAO.setConnection(conn);
		imagenInstalacionDAO.setConnection(conn);
		registroInstalacionDAO.setConnection(conn);
		registroInstalacionDAO.setConnection(conn);
		unidadDeControlDAO.setConnection(conn);
		imagenUnidadDeControlDAO.setConnection(conn);
		registroUnidadDeControlDAO.setConnection(conn);
		registroImagenUnidadDeControlDAO.setConnection(conn);
		generaUnidadDeControlEventoDAO.setConnection(conn);
		mantenimientoUnidadDeControlDAO.setConnection(conn);
		vehiculoDAO.setConnection(conn);
		imagenVehiculoDAO.setConnection(conn);
		registroVehiculoDAO.setConnection(conn);
		registroImagenVehiculoDAO.setConnection(conn);
		generaVehiculoEventoDAO.setConnection(conn);
		mantenimientoVehiculoDAO.setConnection(conn);
		operarioDAO.setConnection(conn);
		dirOperarioDAO.setConnection(conn);
		telOperarioDAO.setConnection(conn);
		creaOperarioParteDAO.setConnection(conn);
		generaOperarioEventoDAO.setConnection(conn);
		operaOperarioMaterialDAO.setConnection(conn);
		parteDAO.setConnection(conn);
		tipoParteDAO.setConnection(conn);
		asociaParteOperarioDAO.setConnection(conn);
		generaParteEventoDAO.setConnection(conn);
		operaParteMaterialDAO.setConnection(conn);
		precioDAO.setConnection(conn);
	}

	/**
	 * --> End INICIAR
	 * 
	 * --> findAll BD
	 * 
	 * @throws DAOException
	 */
	public void findAll() throws DAOException {

		this.compraDAO.findAll();
		this.compraMaterialDAO.findAll();
		this.tiendaDAO.findAll();
		this.dirTiendaDAO.findAll();
		this.telTiendaDAO.findAll();
		this.materialDAO.findAll();
		this.tipoMaterialDAO.findAll();
		this.familiaDAO.findAll();
		this.ubicacionDAO.findAll();
		this.presupuestoDAO.findAll();
		this.tienePresupuestoMaterialDAO.findAll();
		this.taquillaDAO.findAll();
		this.tipoTaquillaDAO.findAll();
		this.operaTaquillaMaterialDAO.findAll();
		this.eventoDAO.findAll();
		this.creaEventoParteDAO.findAll();
		this.grupoDAO.findAll();
		this.alarmaDAO.findAll();
		this.generaAlarmaEventoDAO.findAll();
		this.estacionDAO.findAll();
		this.imagenEstacionDAO.findAll();
		this.registroEstacionDAO.findAll();
		this.registroImagenEstacionDAO.findAll();
		this.instalacionDAO.findAll();
		this.tipoInstalacionDAO.findAll();
		this.imagenInstalacionDAO.findAll();
		this.registroInstalacionDAO.findAll();
		this.registroImagenInstalacionDAO.findAll();
		this.unidadDeControlDAO.findAll();
		this.imagenUnidadDeControlDAO.findAll();
		this.registroUnidadDeControlDAO.findAll();
		this.registroImagenUnidadDeControlDAO.findAll();
		this.generaUnidadDeControlEventoDAO.findAll();
		this.mantenimientoUnidadDeControlDAO.findAll();
		this.vehiculoDAO.findAll();
		this.imagenVehiculoDAO.findAll();
		this.registroVehiculoDAO.findAll();
		this.registroImagenVehiculoDAO.findAll();
		this.generaVehiculoEventoDAO.findAll();
		this.mantenimientoVehiculoDAO.findAll();
		this.operarioDAO.findAll();
		this.dirOperarioDAO.findAll();
		this.telOperarioDAO.findAll();
		this.creaOperarioParteDAO.findAll();
		this.generaOperarioEventoDAO.findAll();
		this.operaOperarioMaterialDAO.findAll();
		this.tipoParteDAO.findAll();
		this.parteDAO.findAll();
		this.parteOficialDAO.findAll();
		this.asociaParteOperarioDAO.findAll();
		this.generaParteEventoDAO.findAll();
		this.operaParteMaterialDAO.findAll();
		this.precioDAO.findAll();
	}

	/**
	 * --> End findAll DB
	 * 
	 * --> GENERAR DEPENDENCIAS
	 * 
	 * @throws DAOException
	 */
	public void createAllDependencies() {
		this.compraDAO.createDependencies(tiendaDAO, operarioDAO);
		this.dirTiendaDAO.createDependencies(tiendaDAO);
		this.telTiendaDAO.createDependencies(tiendaDAO);
		this.materialDAO.createDependencies(ubicacionDAO, tipoMaterialDAO, familiaDAO);
		this.precioDAO.createDependencies(materialDAO, tiendaDAO);
		this.compraMaterialDAO.createDependencies(compraDAO, materialDAO, precioDAO);
		this.tienePresupuestoMaterialDAO.createDependencies(presupuestoDAO, materialDAO);
		this.taquillaDAO.createDependencies(tipoTaquillaDAO);
		this.operaTaquillaMaterialDAO.createDependencies(taquillaDAO, materialDAO);
		this.eventoDAO.createDependencies(grupoDAO);
		this.creaEventoParteDAO.createDependencies(eventoDAO, parteDAO);
		this.alarmaDAO.createDependencies(grupoDAO, operarioDAO);
		this.generaAlarmaEventoDAO.createDependencies(alarmaDAO, eventoDAO);
		this.estacionDAO.createDependencies(imagenEstacionDAO);
		this.imagenEstacionDAO.createDependencies(estacionDAO);
		this.registroEstacionDAO.createDependencies(estacionDAO);
		this.registroImagenEstacionDAO.createDependencies(registroEstacionDAO, imagenEstacionDAO);
		this.instalacionDAO.createDependencies(estacionDAO, tipoInstalacionDAO, imagenInstalacionDAO);
		this.imagenInstalacionDAO.createDependencies(instalacionDAO);
		this.registroInstalacionDAO.createDependencies(instalacionDAO);
		this.registroImagenInstalacionDAO.createDependencies(registroInstalacionDAO, imagenInstalacionDAO);
		this.unidadDeControlDAO.createDependencies(instalacionDAO, imagenUnidadDeControlDAO);
		this.imagenUnidadDeControlDAO.createDependencies(unidadDeControlDAO);
		this.registroUnidadDeControlDAO.createDependencies(unidadDeControlDAO);
		this.registroImagenUnidadDeControlDAO.createDependencies(registroUnidadDeControlDAO, imagenUnidadDeControlDAO);
		this.generaUnidadDeControlEventoDAO.createDependencies(mantenimientoUnidadDeControlDAO, eventoDAO);
		this.mantenimientoUnidadDeControlDAO.createDependencies(grupoDAO, unidadDeControlDAO);
		this.vehiculoDAO.createDependencies(imagenVehiculoDAO);
		this.imagenVehiculoDAO.createDependencies(vehiculoDAO);
		this.registroVehiculoDAO.createDependencies(vehiculoDAO);
		this.registroImagenVehiculoDAO.createDependencies(registroVehiculoDAO, imagenVehiculoDAO);
		this.generaVehiculoEventoDAO.createDependencies(mantenimientoVehiculoDAO, eventoDAO);
		this.mantenimientoVehiculoDAO.createDependencies(grupoDAO, vehiculoDAO);
		this.operarioDAO.createDependencies(grupoDAO);
		this.dirOperarioDAO.createDependencies(operarioDAO);
		this.telOperarioDAO.createDependencies(operarioDAO);
		this.creaOperarioParteDAO.createDependencies(operarioDAO, parteDAO);
		this.generaOperarioEventoDAO.createDependencies(operarioDAO, eventoDAO);
		this.operaOperarioMaterialDAO.createDepedencies(operarioDAO, materialDAO);
		this.parteDAO.createDependencies(tipoParteDAO, grupoDAO);
		this.parteOficialDAO.createDependencies(tipoParteDAO);
		this.parteDAO.createDependencies(parteOficialDAO);
		this.asociaParteOperarioDAO.createDependencies(parteDAO, operarioDAO);
		this.generaParteEventoDAO.createDependencies(parteDAO, eventoDAO);
		this.operaParteMaterialDAO.createDependencies(parteDAO, materialDAO);
	}

}
