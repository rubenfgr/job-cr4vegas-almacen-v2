package es.rfgr.cr4vegas.modelo.postgresql.dao.operario;

import java.sql.Connection;

import es.rfgr.cr4vegas.modelo.postgresql.dao.evento.EventoDAO;
import es.rfgr.cr4vegas.modelo.postgresql.dto.operario.GeneraOperarioEvento;
import es.rfgr.cr4vegas.modelo.postgresql.dto.operario.GeneraOperarioEventoPK;
import es.rfgr.cr4vegas.modelo.postgresql.dto.operario.Operario;
import es.rfgr.cr4vegas.modelo.postgresql.exceptions.DAOException;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;

public interface GeneraOperarioEventoDAO {

	void setConnection(Connection conn);

	ObservableList<GeneraOperarioEvento> getGeneraOperarioEvento();

	int getNumGeneraOperarioEvento();

	GeneraOperarioEventoPK insert(GeneraOperarioEvento dto) throws DAOException;

	ObservableList<GeneraOperarioEventoPK> insertAll(ObservableList<GeneraOperarioEvento> listToInsert)
			throws DAOException;

	void update(GeneraOperarioEventoPK pk, GeneraOperarioEvento dto) throws DAOException;

	void updateAll(ObservableMap<GeneraOperarioEventoPK, GeneraOperarioEvento> mapToUpdate) throws DAOException;

	void delete(GeneraOperarioEventoPK pk) throws DAOException;

	void deleteAll(ObservableList<GeneraOperarioEvento> listToDelete) throws DAOException;

	void findAll() throws DAOException;

	GeneraOperarioEvento findByPrimaryKey(GeneraOperarioEventoPK pk) throws DAOException;

	void setMaxRows(int maxRows);

	int getMaxRows();

	ObservableList<GeneraOperarioEvento> getOperarioEventos(Operario dto);

	void createDependencies(OperarioDAO operarioDAO, EventoDAO eventoDAO);

}