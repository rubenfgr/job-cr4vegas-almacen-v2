package es.rfgr.cr4vegas.modelo.postgresql.dao.parte;

import java.sql.Connection;

import es.rfgr.cr4vegas.modelo.postgresql.dao.evento.EventoDAO;
import es.rfgr.cr4vegas.modelo.postgresql.dto.parte.GeneraParteEvento;
import es.rfgr.cr4vegas.modelo.postgresql.dto.parte.GeneraParteEventoPK;
import es.rfgr.cr4vegas.modelo.postgresql.dto.parte.Parte;
import es.rfgr.cr4vegas.modelo.postgresql.exceptions.DAOException;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;

public interface GeneraParteEventoDAO {

	void setConnection(Connection conn);

	ObservableList<GeneraParteEvento> getGeneraParteEventos();

	int getNumGeneraParteEventos();

	GeneraParteEventoPK insert(GeneraParteEvento dto) throws DAOException;

	ObservableList<GeneraParteEventoPK> insertAll(ObservableList<GeneraParteEvento> listToInsert) throws DAOException;

	void update(GeneraParteEventoPK pk, GeneraParteEvento dto) throws DAOException;

	void updateAll(ObservableMap<GeneraParteEventoPK, GeneraParteEvento> mapToUpdate) throws DAOException;

	void delete(GeneraParteEventoPK pk) throws DAOException;

	void deleteAll(ObservableList<GeneraParteEvento> listToDelete) throws DAOException;

	void findAll() throws DAOException;

	GeneraParteEvento findByPrimaryKey(GeneraParteEventoPK pk) throws DAOException;

	void setMaxRows(int maxRows);

	int getMaxRows();

	ObservableList<GeneraParteEvento> getParteEventos(Parte dto);

	void createDependencies(ParteDAO parteDAO, EventoDAO eventoDAO);

}