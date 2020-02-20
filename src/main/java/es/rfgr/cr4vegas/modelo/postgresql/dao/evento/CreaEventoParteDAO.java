package es.rfgr.cr4vegas.modelo.postgresql.dao.evento;

import java.sql.Connection;

import es.rfgr.cr4vegas.modelo.postgresql.dao.parte.ParteDAO;
import es.rfgr.cr4vegas.modelo.postgresql.dto.evento.CreaEventoParte;
import es.rfgr.cr4vegas.modelo.postgresql.dto.evento.CreaEventoPartePK;
import es.rfgr.cr4vegas.modelo.postgresql.dto.evento.Evento;
import es.rfgr.cr4vegas.modelo.postgresql.exceptions.DAOException;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;

public interface CreaEventoParteDAO {

	void setConnection(Connection conn);

	ObservableList<CreaEventoParte> getCreaEventoParte();

	int getNumCreaEventoParte();

	CreaEventoPartePK insert(CreaEventoParte dto) throws DAOException;

	ObservableList<CreaEventoPartePK> insertAll(ObservableList<CreaEventoParte> listToInsert) throws DAOException;

	void update(CreaEventoPartePK pk, CreaEventoParte dto) throws DAOException;

	void updateAll(ObservableMap<CreaEventoPartePK, CreaEventoParte> mapToUpdate) throws DAOException;

	void delete(CreaEventoPartePK pk) throws DAOException;

	void deleteAll(ObservableList<CreaEventoParte> listToDelete) throws DAOException;

	void findAll() throws DAOException;

	CreaEventoParte findByPrimaryKey(CreaEventoPartePK pk) throws DAOException;

	void setMaxRows(int maxRows);

	int getMaxRows();

	CreaEventoParte getEventoCreaEventoParte(Evento dto);

	void createDependencies(EventoDAO eventoDAO, ParteDAO parteDAO);

}