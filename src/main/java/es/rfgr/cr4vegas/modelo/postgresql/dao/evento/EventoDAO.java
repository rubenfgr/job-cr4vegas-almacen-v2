package es.rfgr.cr4vegas.modelo.postgresql.dao.evento;

import java.sql.Connection;

import es.rfgr.cr4vegas.modelo.postgresql.dao.grupo.GrupoDAO;
import es.rfgr.cr4vegas.modelo.postgresql.dto.evento.Evento;
import es.rfgr.cr4vegas.modelo.postgresql.dto.evento.EventoPK;
import es.rfgr.cr4vegas.modelo.postgresql.exceptions.DAOException;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;

public interface EventoDAO {

	void setConnection(Connection conn);

	ObservableList<Evento> getEventos();

	int getNumEventos();

	EventoPK insert(Evento dto) throws DAOException;

	ObservableList<EventoPK> insertAll(ObservableList<Evento> listToInsert) throws DAOException;

	void update(EventoPK pk, Evento dto) throws DAOException;

	void updateAll(ObservableMap<EventoPK, Evento> mapToUpdate) throws DAOException;

	void delete(EventoPK pk) throws DAOException;

	void deleteAll(ObservableList<Evento> listToDelete) throws DAOException;

	void findAll() throws DAOException;

	Evento findByPrimaryKey(EventoPK pk) throws DAOException;

	void setMaxRows(int maxRows);

	int getMaxRows();

	void createDependencies(GrupoDAO grupoDAO);

}