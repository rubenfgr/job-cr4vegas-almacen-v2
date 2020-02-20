package es.rfgr.cr4vegas.modelo.postgresql.dao.mantenimiento.unidaddecontrol;

import java.sql.Connection;

import es.rfgr.cr4vegas.modelo.postgresql.dao.mantenimiento.instalacion.InstalacionDAO;
import es.rfgr.cr4vegas.modelo.postgresql.dto.mantenimiento.unidaddecontrol.UnidadDeControl;
import es.rfgr.cr4vegas.modelo.postgresql.dto.mantenimiento.unidaddecontrol.UnidadDeControlPK;
import es.rfgr.cr4vegas.modelo.postgresql.exceptions.DAOException;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;

public interface UnidadDeControlDAO {

	void setConnection(Connection conn);

	ObservableList<UnidadDeControl> getUdsControl();

	int getNumUdsControl();

	UnidadDeControlPK insert(UnidadDeControl dto) throws DAOException;

	ObservableList<UnidadDeControlPK> insertAll(ObservableList<UnidadDeControl> listToInsert) throws DAOException;

	void update(UnidadDeControlPK pk, UnidadDeControl dto) throws DAOException;

	void updateAll(ObservableMap<UnidadDeControlPK, UnidadDeControl> mapToUpdate) throws DAOException;

	void delete(UnidadDeControlPK pk) throws DAOException;

	void deleteAll(ObservableList<UnidadDeControl> listToDelete) throws DAOException;

	void findAll() throws DAOException;

	UnidadDeControl findByPrimaryKey(UnidadDeControlPK pk) throws DAOException;

	void setMaxRows(int maxRows);

	int getMaxRows();

	void createDependencies(InstalacionDAO instalacionDAO, ImagenUnidadDeControlDAO imagenUDControlDAO);

}