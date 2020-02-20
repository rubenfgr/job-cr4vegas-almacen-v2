package es.rfgr.cr4vegas.modelo.postgresql.dao.mantenimiento.unidaddecontrol;

import java.sql.Connection;

import es.rfgr.cr4vegas.modelo.postgresql.dto.mantenimiento.unidaddecontrol.RegistroUnidadDeControl;
import es.rfgr.cr4vegas.modelo.postgresql.dto.mantenimiento.unidaddecontrol.RegistroUnidadDeControlPK;
import es.rfgr.cr4vegas.modelo.postgresql.dto.mantenimiento.unidaddecontrol.UnidadDeControl;
import es.rfgr.cr4vegas.modelo.postgresql.exceptions.DAOException;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;

public interface RegistroUnidadDeControlDAO {

	void setConnection(Connection conn);

	ObservableList<RegistroUnidadDeControl> getRegistrosUdControl();

	int getNumRegistrosUdControl();

	RegistroUnidadDeControlPK insert(RegistroUnidadDeControl dto) throws DAOException;

	ObservableList<RegistroUnidadDeControlPK> insertAll(ObservableList<RegistroUnidadDeControl> listToInsert)
			throws DAOException;

	void update(RegistroUnidadDeControlPK pk, RegistroUnidadDeControl dto) throws DAOException;

	void updateAll(ObservableMap<RegistroUnidadDeControlPK, RegistroUnidadDeControl> mapToUpdate) throws DAOException;

	void delete(RegistroUnidadDeControlPK pk) throws DAOException;

	void deleteAll(ObservableList<RegistroUnidadDeControl> listToDelete) throws DAOException;

	void findAll() throws DAOException;

	RegistroUnidadDeControl findByPrimaryKey(RegistroUnidadDeControlPK pk) throws DAOException;

	void setMaxRows(int maxRows);

	int getMaxRows();

	ObservableList<RegistroUnidadDeControl> getUdControlRegistros(UnidadDeControl dto);

	void createDependencies(UnidadDeControlDAO uDControlDAO);

}