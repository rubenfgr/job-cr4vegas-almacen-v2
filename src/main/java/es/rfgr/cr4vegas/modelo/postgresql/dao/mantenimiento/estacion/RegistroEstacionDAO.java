package es.rfgr.cr4vegas.modelo.postgresql.dao.mantenimiento.estacion;

import java.sql.Connection;

import es.rfgr.cr4vegas.modelo.postgresql.dto.mantenimiento.estacion.Estacion;
import es.rfgr.cr4vegas.modelo.postgresql.dto.mantenimiento.estacion.RegistroEstacion;
import es.rfgr.cr4vegas.modelo.postgresql.dto.mantenimiento.estacion.RegistroEstacionPK;
import es.rfgr.cr4vegas.modelo.postgresql.exceptions.DAOException;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;

public interface RegistroEstacionDAO {

	void setConnection(Connection conn);

	ObservableList<RegistroEstacion> getRegistroEstacion();

	int getNumRegistroEstacion();

	RegistroEstacionPK insert(RegistroEstacion dto) throws DAOException;

	ObservableList<RegistroEstacionPK> insertAll(ObservableList<RegistroEstacion> listToInsert) throws DAOException;

	void update(RegistroEstacionPK pk, RegistroEstacion dto) throws DAOException;

	void updateAll(ObservableMap<RegistroEstacionPK, RegistroEstacion> mapToUpdate) throws DAOException;

	void delete(RegistroEstacionPK pk) throws DAOException;

	void deleteAll(ObservableList<RegistroEstacion> listToDelete) throws DAOException;

	void findAll() throws DAOException;

	RegistroEstacion findByPrimaryKey(RegistroEstacionPK pk) throws DAOException;

	void setMaxRows(int maxRows);

	int getMaxRows();

	ObservableList<RegistroEstacion> getEstacionRegistros(Estacion dto);

	void createDependencies(EstacionDAO estacionDAO);

}