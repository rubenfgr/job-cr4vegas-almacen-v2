package es.rfgr.cr4vegas.modelo.postgresql.dao.mantenimiento.vehiculo;

import java.sql.Connection;

import es.rfgr.cr4vegas.modelo.postgresql.dao.grupo.GrupoDAO;
import es.rfgr.cr4vegas.modelo.postgresql.dto.mantenimiento.vehiculo.MantenimientoVehiculo;
import es.rfgr.cr4vegas.modelo.postgresql.dto.mantenimiento.vehiculo.MantenimientoVehiculoPK;
import es.rfgr.cr4vegas.modelo.postgresql.dto.mantenimiento.vehiculo.Vehiculo;
import es.rfgr.cr4vegas.modelo.postgresql.exceptions.DAOException;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;

public interface MantenimientoVehiculoDAO {

	void setConnection(Connection conn);

	ObservableList<MantenimientoVehiculo> getMantenimientosVehiculo();

	int getNumMantenimientosVehiculo();

	MantenimientoVehiculoPK insert(MantenimientoVehiculo dto) throws DAOException;

	ObservableList<MantenimientoVehiculoPK> insertAll(ObservableList<MantenimientoVehiculo> listToInsert)
			throws DAOException;

	void update(MantenimientoVehiculoPK pk, MantenimientoVehiculo dto) throws DAOException;

	void updateAll(ObservableMap<MantenimientoVehiculoPK, MantenimientoVehiculo> mapToUpdate) throws DAOException;

	void delete(MantenimientoVehiculoPK pk) throws DAOException;

	void deleteAll(ObservableList<MantenimientoVehiculo> listToDelete) throws DAOException;

	void findAll() throws DAOException;

	MantenimientoVehiculo findByPrimaryKey(MantenimientoVehiculoPK pk) throws DAOException;

	void setMaxRows(int maxRows);

	int getMaxRows();

	ObservableList<MantenimientoVehiculo> getVehiculoMantenimientos(Vehiculo dto);

	void createDependencies(GrupoDAO grupoDAO, VehiculoDAO vehiculoDAO);

}