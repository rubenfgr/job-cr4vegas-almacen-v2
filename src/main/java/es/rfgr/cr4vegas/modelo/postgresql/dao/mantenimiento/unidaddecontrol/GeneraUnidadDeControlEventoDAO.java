package es.rfgr.cr4vegas.modelo.postgresql.dao.mantenimiento.unidaddecontrol;

import java.sql.Connection;

import es.rfgr.cr4vegas.modelo.postgresql.dao.evento.EventoDAO;
import es.rfgr.cr4vegas.modelo.postgresql.dto.mantenimiento.unidaddecontrol.GeneraUnidadDeControlEvento;
import es.rfgr.cr4vegas.modelo.postgresql.dto.mantenimiento.unidaddecontrol.GeneraUnidadDeControlEventoPK;
import es.rfgr.cr4vegas.modelo.postgresql.dto.mantenimiento.unidaddecontrol.MantenimientoUnidadDeControl;
import es.rfgr.cr4vegas.modelo.postgresql.exceptions.DAOException;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;

public interface GeneraUnidadDeControlEventoDAO {

	void setConnection(Connection conn);

	ObservableList<GeneraUnidadDeControlEvento> getGeneraUdsControlEvento();

	int getNumGeneraUdsControlEvento();

	GeneraUnidadDeControlEventoPK insert(GeneraUnidadDeControlEvento dto) throws DAOException;

	ObservableList<GeneraUnidadDeControlEventoPK> insertAll(ObservableList<GeneraUnidadDeControlEvento> listToInsert)
			throws DAOException;

	void update(GeneraUnidadDeControlEventoPK pk, GeneraUnidadDeControlEvento dto) throws DAOException;

	void updateAll(ObservableMap<GeneraUnidadDeControlEventoPK, GeneraUnidadDeControlEvento> mapToUpdate)
			throws DAOException;

	void delete(GeneraUnidadDeControlEventoPK pk) throws DAOException;

	void deleteAll(ObservableList<GeneraUnidadDeControlEvento> listToDelete) throws DAOException;

	void findAll() throws DAOException;

	GeneraUnidadDeControlEvento findByPrimaryKey(GeneraUnidadDeControlEventoPK pk) throws DAOException;

	void setMaxRows(int maxRows);

	int getMaxRows();

	ObservableList<GeneraUnidadDeControlEvento> getUDControlMantenimientoEventos(MantenimientoUnidadDeControl dto);

	void createDependencies(MantenimientoUnidadDeControlDAO mantenimientoUDControlDAO, EventoDAO eventoDAO);

}