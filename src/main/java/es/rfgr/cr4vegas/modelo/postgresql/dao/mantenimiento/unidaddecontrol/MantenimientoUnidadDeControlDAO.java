package es.rfgr.cr4vegas.modelo.postgresql.dao.mantenimiento.unidaddecontrol;

import java.sql.Connection;

import es.rfgr.cr4vegas.modelo.postgresql.dao.grupo.GrupoDAO;
import es.rfgr.cr4vegas.modelo.postgresql.dto.mantenimiento.unidaddecontrol.MantenimientoUnidadDeControl;
import es.rfgr.cr4vegas.modelo.postgresql.dto.mantenimiento.unidaddecontrol.MantenimientoUnidadDeControlPK;
import es.rfgr.cr4vegas.modelo.postgresql.dto.mantenimiento.unidaddecontrol.UnidadDeControl;
import es.rfgr.cr4vegas.modelo.postgresql.exceptions.DAOException;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;

public interface MantenimientoUnidadDeControlDAO {

	void setConnection(Connection conn);

	ObservableList<MantenimientoUnidadDeControl> getMantenimientosUdControl();

	int getNumMantenimientosUdControl();

	MantenimientoUnidadDeControlPK insert(MantenimientoUnidadDeControl dto) throws DAOException;

	ObservableList<MantenimientoUnidadDeControlPK> insertAll(ObservableList<MantenimientoUnidadDeControl> listToInsert)
			throws DAOException;

	void update(MantenimientoUnidadDeControlPK pk, MantenimientoUnidadDeControl dto) throws DAOException;

	void updateAll(ObservableMap<MantenimientoUnidadDeControlPK, MantenimientoUnidadDeControl> mapToUpdate)
			throws DAOException;

	void delete(MantenimientoUnidadDeControlPK pk) throws DAOException;

	void deleteAll(ObservableList<MantenimientoUnidadDeControl> listToDelete) throws DAOException;

	void findAll() throws DAOException;

	MantenimientoUnidadDeControl findByPrimaryKey(MantenimientoUnidadDeControlPK pk) throws DAOException;

	void setMaxRows(int maxRows);

	int getMaxRows();

	ObservableList<MantenimientoUnidadDeControl> getUdControlMantenimiento(UnidadDeControl dto);

	void createDependencies(GrupoDAO grupoDAO, UnidadDeControlDAO uDControlDAO);

}