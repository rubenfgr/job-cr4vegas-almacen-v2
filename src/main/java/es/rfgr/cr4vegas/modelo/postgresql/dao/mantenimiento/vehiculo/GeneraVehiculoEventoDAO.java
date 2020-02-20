package es.rfgr.cr4vegas.modelo.postgresql.dao.mantenimiento.vehiculo;

import java.sql.Connection;

import es.rfgr.cr4vegas.modelo.postgresql.dao.evento.EventoDAO;
import es.rfgr.cr4vegas.modelo.postgresql.dto.evento.Evento;
import es.rfgr.cr4vegas.modelo.postgresql.dto.mantenimiento.vehiculo.GeneraVehiculoEvento;
import es.rfgr.cr4vegas.modelo.postgresql.dto.mantenimiento.vehiculo.GeneraVehiculoEventoPK;
import es.rfgr.cr4vegas.modelo.postgresql.dto.mantenimiento.vehiculo.MantenimientoVehiculo;
import es.rfgr.cr4vegas.modelo.postgresql.exceptions.DAOException;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;

public interface GeneraVehiculoEventoDAO {

	void setConnection(Connection conn);

	ObservableList<GeneraVehiculoEvento> getGeneraVehiculoEventos();

	int getNumGeneraVehiculoEventos();

	GeneraVehiculoEventoPK insert(GeneraVehiculoEvento dto) throws DAOException;

	ObservableList<GeneraVehiculoEventoPK> insertAll(ObservableList<GeneraVehiculoEvento> listToInsert)
			throws DAOException;

	void update(GeneraVehiculoEventoPK pk, GeneraVehiculoEvento dto) throws DAOException;

	void updateAll(ObservableMap<GeneraVehiculoEventoPK, GeneraVehiculoEvento> mapToUpdate) throws DAOException;

	void delete(GeneraVehiculoEventoPK pk) throws DAOException;

	void deleteAll(ObservableList<GeneraVehiculoEvento> listToDelete) throws DAOException;

	void findAll() throws DAOException;

	GeneraVehiculoEvento findByPrimaryKey(GeneraVehiculoEventoPK pk) throws DAOException;

	void setMaxRows(int maxRows);

	int getMaxRows();

	ObservableList<Evento> getMantenimientoVehiculoEventos(MantenimientoVehiculo dto);

	void createDependencies(MantenimientoVehiculoDAO mantenimientoVehiculoDAO, EventoDAO eventoDAO);

}