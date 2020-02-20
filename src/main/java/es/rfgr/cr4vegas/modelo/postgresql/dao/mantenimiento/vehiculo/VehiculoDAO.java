package es.rfgr.cr4vegas.modelo.postgresql.dao.mantenimiento.vehiculo;

import java.sql.Connection;

import es.rfgr.cr4vegas.modelo.postgresql.dto.mantenimiento.vehiculo.Vehiculo;
import es.rfgr.cr4vegas.modelo.postgresql.dto.mantenimiento.vehiculo.VehiculoPK;
import es.rfgr.cr4vegas.modelo.postgresql.exceptions.DAOException;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;

public interface VehiculoDAO {

	void setConnection(Connection conn);

	ObservableList<Vehiculo> getVehiculos();

	int getNumVehiculos();

	VehiculoPK insert(Vehiculo dto) throws DAOException;

	ObservableList<VehiculoPK> insertAll(ObservableList<Vehiculo> listToInsert) throws DAOException;

	void update(VehiculoPK pk, Vehiculo dto) throws DAOException;

	void updateAll(ObservableMap<VehiculoPK, Vehiculo> mapToUpdate) throws DAOException;

	void delete(VehiculoPK pk) throws DAOException;

	void deleteAll(ObservableList<Vehiculo> listToDelete) throws DAOException;

	void findAll() throws DAOException;

	Vehiculo findByPrimaryKey(VehiculoPK pk) throws DAOException;

	void setMaxRows(int maxRows);

	int getMaxRows();

	void createDependencies(ImagenVehiculoDAO imagenVehiculoDAO);

}