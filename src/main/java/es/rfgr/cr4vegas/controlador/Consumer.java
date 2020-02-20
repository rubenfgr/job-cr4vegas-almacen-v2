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

public interface Consumer {

	void comenzar();

	void iniciarConexion(Connection conn) throws DAOException;

	/**
	 * --> Vista
	 */
	VistaModelo getVistaModelo();

	CompraDAO getCompraDAO();

	CompraMaterialDAO getCompraMaterialDAO();

	TiendaDAO getTiendaDAO();

	DirTiendaDAO getDirTiendaDAO();

	TelTiendaDAO getTelTiendaDAO();

	MaterialDAO getMaterialDAO();

	TipoMaterialDAO getTipoMaterialDAO();

	FamiliaDAO getFamiliaDAO();

	UbicacionDAO getUbicacionDAO();

	PresupuestoDAO getPresupuestoDAO();

	TienePresupuestoMaterialDAO getTienePresupuestoMaterialDAO();

	TaquillaDAO getTaquillaDAO();

	TipoTaquillaDAO getTipoTaquillaDAO();

	OperaTaquillaMaterialDAO getOperaTaquillaMaterialDAO();

	EventoDAO getEventoDAO();

	CreaEventoParteDAO getCreaEventoParteDAO();

	GrupoDAO getGrupoDAO();

	AlarmaDAO getAlarmaDAO();

	GeneraAlarmaEventoDAO getGeneraAlarmaEventoDAO();

	EstacionDAO getEstacionDAO();

	ImagenEstacionDAO getImagenEstacionDAO();

	RegistroEstacionDAO getRegistroEstacionDAO();

	RegistroImagenEstacionDAO getRegistroImagenEstacionDAO();

	InstalacionDAO getInstalacionDAO();

	TipoInstalacionDAO getTipoInstalacionDAO();

	ImagenInstalacionDAO getImagenInstalacionDAO();

	RegistroInstalacionDAO getRegistroInstalacionDAO();

	RegistroImagenInstalacionDAO getRegistroImagenInstalacionDAO();

	UnidadDeControlDAO getUnidadDeControlDAO();

	ImagenUnidadDeControlDAO getImagenUnidadDeControlDAO();

	RegistroUnidadDeControlDAO getRegistroUnidadDeControlDAO();

	RegistroImagenUnidadDeControlDAO getRegistroImagenUnidadDeControlDAO();

	GeneraUnidadDeControlEventoDAO getGeneraUnidadDeControlEventoDAO();

	MantenimientoUnidadDeControlDAO getMantenimientoUnidadDeControlDAO();

	VehiculoDAO getVehiculoDAO();

	ImagenVehiculoDAO getImagenVehiculoDAO();

	RegistroVehiculoDAO getRegistroVehiculoDAO();

	RegistroImagenVehiculoDAO getRegistroImagenVehiculoDAO();

	GeneraVehiculoEventoDAO getGeneraVehiculoEventoDAO();

	MantenimientoVehiculoDAO getMantenimientoVehiculoDAO();

	OperarioDAO getOperarioDAO();

	DirOperarioDAO getDirOperarioDAO();

	TelOperarioDAO getTelOperarioDAO();

	CreaOperarioParteDAO getCreaOperarioParteDAO();

	GeneraOperarioEventoDAO getGeneraOperarioEventoDAO();

	OperaOperarioMaterialDAO getOperaOperarioMaterialDAO();

	ParteDAO getParteDAO();

	ParteOficialDAO getParteOficialDAO();

	TipoParteDAO getTipoParteDAO();

	AsociaParteOperarioDAO getAsociaParteOperarioDAO();

	GeneraParteEventoDAO getGeneraParteEventoDAO();

	OperaParteMaterialDAO getOperaParteMaterialDAO();

	PrecioDAO getPrecioDAO();

	/**
	 * --> INICIAR
	 */
	void setAllConnections(Connection conn);

	/**
	 * --> End INICIAR
	 * 
	 * --> findAll BD
	 * 
	 * @throws DAOException
	 */
	void findAll() throws DAOException;

	/**
	 * --> End findAll DB
	 * 
	 * --> GENERAR DEPENDENCIAS
	 * 
	 * @throws DAOException
	 */
	void createAllDependencies();
}