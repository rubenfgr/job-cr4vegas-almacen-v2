package es.rfgr.cr4vegas.modelo.postgresql.dao.mantenimiento.instalacion;

import java.sql.Connection;

import es.rfgr.cr4vegas.modelo.postgresql.dto.mantenimiento.instalacion.TipoInstalacion;
import es.rfgr.cr4vegas.modelo.postgresql.dto.mantenimiento.instalacion.TipoInstalacionPK;
import es.rfgr.cr4vegas.modelo.postgresql.exceptions.DAOException;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;

public interface TipoInstalacionDAO {

	void setConnection(Connection conn);

	ObservableList<TipoInstalacion> getTiposInstalacion();

	int getNumTiposInstalacion();

	TipoInstalacionPK insert(TipoInstalacion dto) throws DAOException;

	ObservableList<TipoInstalacionPK> insertAll(ObservableList<TipoInstalacion> listToInsert) throws DAOException;

	void update(TipoInstalacionPK pk, TipoInstalacion dto) throws DAOException;

	void updateAll(ObservableMap<TipoInstalacionPK, TipoInstalacion> mapToUpdate) throws DAOException;

	void delete(TipoInstalacionPK pk) throws DAOException;

	void deleteAll(ObservableList<TipoInstalacion> listToDelete) throws DAOException;

	void findAll() throws DAOException;

	TipoInstalacion findByPrimaryKey(TipoInstalacionPK pk) throws DAOException;

	void setMaxRows(int maxRows);

	int getMaxRows();

}