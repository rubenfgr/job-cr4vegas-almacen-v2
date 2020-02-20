package es.rfgr.cr4vegas.modelo.postgresql.dao.alarma;

import java.sql.Connection;

import es.rfgr.cr4vegas.modelo.postgresql.dao.evento.EventoDAO;
import es.rfgr.cr4vegas.modelo.postgresql.dto.alarma.Alarma;
import es.rfgr.cr4vegas.modelo.postgresql.dto.alarma.GeneraAlarmaEvento;
import es.rfgr.cr4vegas.modelo.postgresql.dto.alarma.GeneraAlarmaEventoPK;
import es.rfgr.cr4vegas.modelo.postgresql.exceptions.DAOException;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;

public interface GeneraAlarmaEventoDAO {

	void setConnection(Connection conn);

	ObservableList<GeneraAlarmaEvento> getGeneraAlarmaEvento();

	int getNumGeneraAlarmaEvento();

	GeneraAlarmaEventoPK insert(GeneraAlarmaEvento dto) throws DAOException;

	ObservableList<GeneraAlarmaEventoPK> insertAll(ObservableList<GeneraAlarmaEvento> listToInsert) throws DAOException;

	void update(GeneraAlarmaEventoPK pk, GeneraAlarmaEvento dto) throws DAOException;

	void updateAll(ObservableMap<GeneraAlarmaEventoPK, GeneraAlarmaEvento> mapToUpdate) throws DAOException;

	void delete(GeneraAlarmaEventoPK pk) throws DAOException;

	void deleteAll(ObservableList<GeneraAlarmaEvento> listToDelete) throws DAOException;

	void findAll() throws DAOException;

	GeneraAlarmaEvento findByPrimaryKey(GeneraAlarmaEventoPK pk) throws DAOException;

	void setMaxRows(int maxRows);

	int getMaxRows();

	ObservableList<GeneraAlarmaEvento> getAlarmaEventos(Alarma dto);

	void createDependencies(AlarmaDAO alarmaDAO, EventoDAO eventoDAO);

}