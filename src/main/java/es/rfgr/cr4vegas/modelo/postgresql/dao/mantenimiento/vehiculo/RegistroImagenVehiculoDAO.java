package es.rfgr.cr4vegas.modelo.postgresql.dao.mantenimiento.vehiculo;

import java.sql.Connection;

import es.rfgr.cr4vegas.modelo.postgresql.dto.mantenimiento.vehiculo.RegistroImagenVehiculo;
import es.rfgr.cr4vegas.modelo.postgresql.dto.mantenimiento.vehiculo.RegistroImagenVehiculoPK;
import es.rfgr.cr4vegas.modelo.postgresql.dto.mantenimiento.vehiculo.RegistroVehiculo;
import es.rfgr.cr4vegas.modelo.postgresql.exceptions.DAOException;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;

public interface RegistroImagenVehiculoDAO {

	void setConnection(Connection conn);

	ObservableList<RegistroImagenVehiculo> getRegistrosImagenesVehiculos();

	int getNumRegistrosImagenesVehiculos();

	RegistroImagenVehiculoPK insert(RegistroImagenVehiculo dto) throws DAOException;

	ObservableList<RegistroImagenVehiculoPK> insertAll(ObservableList<RegistroImagenVehiculo> listToInsert)
			throws DAOException;

	void update(RegistroImagenVehiculoPK pk, RegistroImagenVehiculo dto) throws DAOException;

	void updateAll(ObservableMap<RegistroImagenVehiculoPK, RegistroImagenVehiculo> mapToUpdate) throws DAOException;

	void delete(RegistroImagenVehiculoPK pk) throws DAOException;

	void deleteAll(ObservableList<RegistroImagenVehiculo> listToDelete) throws DAOException;

	void findAll() throws DAOException;

	RegistroImagenVehiculo findByPrimaryKey(RegistroImagenVehiculoPK pk) throws DAOException;

	void setMaxRows(int maxRows);

	int getMaxRows();

	ObservableList<RegistroImagenVehiculo> getRegistroVehiculoImagenes(RegistroVehiculo dto);

	void createDependencies(RegistroVehiculoDAO registroVehiculoDAO, ImagenVehiculoDAO imagenVehiculoDAO);

}