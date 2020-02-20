package es.rfgr.cr4vegas.modelo.postgresql.dao.mantenimiento.estacion;

import java.sql.Connection;

import es.rfgr.cr4vegas.modelo.postgresql.dto.mantenimiento.estacion.Estacion;
import es.rfgr.cr4vegas.modelo.postgresql.dto.mantenimiento.estacion.EstacionPK;
import es.rfgr.cr4vegas.modelo.postgresql.exceptions.DAOException;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;

public interface EstacionDAO {

	void setConnection(Connection conn);

	ObservableList<Estacion> getEstaciones();

	int getNumEstaciones();

	EstacionPK insert(Estacion dto) throws DAOException;

	ObservableList<EstacionPK> insertAll(ObservableList<Estacion> listToInsert) throws DAOException;

	void update(EstacionPK pk, Estacion dto) throws DAOException;

	void updateAll(ObservableMap<EstacionPK, Estacion> mapToUpdate) throws DAOException;

	void delete(EstacionPK pk) throws DAOException;

	void deleteAll(ObservableList<Estacion> listToDelete) throws DAOException;

	void findAll() throws DAOException;

	Estacion findByPrimaryKey(EstacionPK pk) throws DAOException;

	void setMaxRows(int maxRows);

	int getMaxRows();

	void createDependencies(ImagenEstacionDAO imagenEstacionDAO);

}