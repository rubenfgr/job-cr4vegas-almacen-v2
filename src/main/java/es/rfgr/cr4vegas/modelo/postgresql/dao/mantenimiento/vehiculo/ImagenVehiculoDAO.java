package es.rfgr.cr4vegas.modelo.postgresql.dao.mantenimiento.vehiculo;

import java.sql.Connection;

import es.rfgr.cr4vegas.modelo.postgresql.dto.mantenimiento.vehiculo.ImagenVehiculo;
import es.rfgr.cr4vegas.modelo.postgresql.dto.mantenimiento.vehiculo.ImagenVehiculoPK;
import es.rfgr.cr4vegas.modelo.postgresql.dto.mantenimiento.vehiculo.Vehiculo;
import es.rfgr.cr4vegas.modelo.postgresql.exceptions.DAOException;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;

public interface ImagenVehiculoDAO {

	void setConnection(Connection conn);

	ObservableList<ImagenVehiculo> getImagenesVehiculo();

	int getNumImagenesVehiculo();

	ImagenVehiculoPK insert(ImagenVehiculo dto) throws DAOException;

	ObservableList<ImagenVehiculoPK> insertAll(ObservableList<ImagenVehiculo> listToInsert) throws DAOException;

	void update(ImagenVehiculoPK pk, ImagenVehiculo dto) throws DAOException;

	void updateAll(ObservableMap<ImagenVehiculoPK, ImagenVehiculo> mapToUpdate) throws DAOException;

	void delete(ImagenVehiculoPK pk) throws DAOException;

	void deleteAll(ObservableList<ImagenVehiculo> listToDelete) throws DAOException;

	void findAll() throws DAOException;

	ImagenVehiculo findByPrimaryKey(ImagenVehiculoPK pk) throws DAOException;

	void setMaxRows(int maxRows);

	int getMaxRows();

	ObservableList<ImagenVehiculo> getVehiculoImagenes(Vehiculo dto);

	void createDependencies(VehiculoDAO vehiculoDAO);

}