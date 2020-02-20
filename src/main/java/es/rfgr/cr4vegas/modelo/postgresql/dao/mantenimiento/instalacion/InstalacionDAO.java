package es.rfgr.cr4vegas.modelo.postgresql.dao.mantenimiento.instalacion;

import java.sql.Connection;

import es.rfgr.cr4vegas.modelo.postgresql.dao.mantenimiento.estacion.EstacionDAO;
import es.rfgr.cr4vegas.modelo.postgresql.dto.mantenimiento.instalacion.Instalacion;
import es.rfgr.cr4vegas.modelo.postgresql.dto.mantenimiento.instalacion.InstalacionPK;
import es.rfgr.cr4vegas.modelo.postgresql.exceptions.DAOException;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;

public interface InstalacionDAO {

	void setConnection(Connection conn);

	ObservableList<Instalacion> getInstalaciones();

	int getNumInstalaciones();

	InstalacionPK insert(Instalacion dto) throws DAOException;

	ObservableList<InstalacionPK> insertAll(ObservableList<Instalacion> listToInsert) throws DAOException;

	void update(InstalacionPK pk, Instalacion dto) throws DAOException;

	void updateAll(ObservableMap<InstalacionPK, Instalacion> mapToUpdate) throws DAOException;

	void delete(InstalacionPK pk) throws DAOException;

	void deleteAll(ObservableList<Instalacion> listToDelete) throws DAOException;

	void findAll() throws DAOException;

	Instalacion findByPrimaryKey(InstalacionPK pk) throws DAOException;

	void setMaxRows(int maxRows);

	int getMaxRows();

	void createDependencies(EstacionDAO estacionDAO, TipoInstalacionDAO tipoInstalacionDAO,
			ImagenInstalacionDAO imagenInstalacionDAO);

}