package es.rfgr.cr4vegas.modelo.postgresql.dao.mantenimiento.instalacion;

import java.sql.Connection;

import es.rfgr.cr4vegas.modelo.postgresql.dto.mantenimiento.instalacion.Instalacion;
import es.rfgr.cr4vegas.modelo.postgresql.dto.mantenimiento.instalacion.RegistroInstalacion;
import es.rfgr.cr4vegas.modelo.postgresql.dto.mantenimiento.instalacion.RegistroInstalacionPK;
import es.rfgr.cr4vegas.modelo.postgresql.exceptions.DAOException;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;

public interface RegistroInstalacionDAO {

	void setConnection(Connection conn);

	ObservableList<RegistroInstalacion> getRegistroInstalacion();

	int getNumRegistroInstalacion();

	RegistroInstalacionPK insert(RegistroInstalacion dto) throws DAOException;

	ObservableList<RegistroInstalacionPK> insertAll(ObservableList<RegistroInstalacion> listToInsert)
			throws DAOException;

	void update(RegistroInstalacionPK pk, RegistroInstalacion dto) throws DAOException;

	void updateAll(ObservableMap<RegistroInstalacionPK, RegistroInstalacion> mapToUpdate) throws DAOException;

	void delete(RegistroInstalacionPK pk) throws DAOException;

	void deleteAll(ObservableList<RegistroInstalacion> listToDelete) throws DAOException;

	void findAll() throws DAOException;

	RegistroInstalacion findByPrimaryKey(RegistroInstalacionPK pk) throws DAOException;

	void setMaxRows(int maxRows);

	int getMaxRows();

	ObservableList<RegistroInstalacion> getInstalacionRegistros(Instalacion dto);

	void createDependencies(InstalacionDAO instalacionDAO);

}