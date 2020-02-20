package es.rfgr.cr4vegas.modelo.postgresql.dao.mantenimiento.vehiculo;

import java.sql.Connection;

import es.rfgr.cr4vegas.modelo.postgresql.dto.mantenimiento.vehiculo.RegistroVehiculo;
import es.rfgr.cr4vegas.modelo.postgresql.dto.mantenimiento.vehiculo.RegistroVehiculoPK;
import es.rfgr.cr4vegas.modelo.postgresql.dto.mantenimiento.vehiculo.Vehiculo;
import es.rfgr.cr4vegas.modelo.postgresql.exceptions.DAOException;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;

public interface RegistroVehiculoDAO {

	void setConnection(Connection conn);

	ObservableList<RegistroVehiculo> getRegistrosVehiculo();

	int getNumRegistrosVehiculo();

	RegistroVehiculoPK insert(RegistroVehiculo dto) throws DAOException;

	ObservableList<RegistroVehiculoPK> insertAll(ObservableList<RegistroVehiculo> listToInsert) throws DAOException;

	void update(RegistroVehiculoPK pk, RegistroVehiculo dto) throws DAOException;

	void updateAll(ObservableMap<RegistroVehiculoPK, RegistroVehiculo> mapToUpdate) throws DAOException;

	void delete(RegistroVehiculoPK pk) throws DAOException;

	void deleteAll(ObservableList<RegistroVehiculo> listToDelete) throws DAOException;

	void findAll() throws DAOException;

	RegistroVehiculo findByPrimaryKey(RegistroVehiculoPK pk) throws DAOException;

	void setMaxRows(int maxRows);

	int getMaxRows();

	ObservableList<RegistroVehiculo> getVehiculoRegistros(Vehiculo dto);

	void createDependencies(VehiculoDAO vehiculoDAO);

}