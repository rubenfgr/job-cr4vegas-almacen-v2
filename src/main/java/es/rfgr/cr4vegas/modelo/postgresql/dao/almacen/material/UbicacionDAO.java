package es.rfgr.cr4vegas.modelo.postgresql.dao.almacen.material;

import java.sql.Connection;

import es.rfgr.cr4vegas.modelo.postgresql.dto.almacen.material.Ubicacion;
import es.rfgr.cr4vegas.modelo.postgresql.dto.almacen.material.UbicacionPK;
import es.rfgr.cr4vegas.modelo.postgresql.exceptions.DAOException;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;

public interface UbicacionDAO {

	void setConnection(Connection conn);

	ObservableList<Ubicacion> getUbicaciones();

	int getNumUbicaciones();

	UbicacionPK insert(Ubicacion dto) throws DAOException;

	ObservableList<UbicacionPK> insertAll(ObservableList<Ubicacion> listToInsert) throws DAOException;

	void update(UbicacionPK pk, Ubicacion dto) throws DAOException;

	void updateAll(ObservableMap<UbicacionPK, Ubicacion> mapToUpdate) throws DAOException;

	void delete(UbicacionPK pk) throws DAOException;

	void deleteAll(ObservableList<Ubicacion> listToDelete) throws DAOException;

	void findAll() throws DAOException;

	Ubicacion findByPrimaryKey(UbicacionPK pk) throws DAOException;

	void setMaxRows(int maxRows);

	int getMaxRows();

}