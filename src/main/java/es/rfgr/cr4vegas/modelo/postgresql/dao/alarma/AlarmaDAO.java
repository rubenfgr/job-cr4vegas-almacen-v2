package es.rfgr.cr4vegas.modelo.postgresql.dao.alarma;

import java.sql.Connection;

import es.rfgr.cr4vegas.modelo.postgresql.dao.grupo.GrupoDAO;
import es.rfgr.cr4vegas.modelo.postgresql.dao.operario.OperarioDAO;
import es.rfgr.cr4vegas.modelo.postgresql.dto.alarma.Alarma;
import es.rfgr.cr4vegas.modelo.postgresql.dto.alarma.AlarmaPK;
import es.rfgr.cr4vegas.modelo.postgresql.exceptions.DAOException;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;

public interface AlarmaDAO {

	void setConnection(Connection conn);

	ObservableList<Alarma> getAlarmas();

	int getNumAlarmas();

	AlarmaPK insert(Alarma dto) throws DAOException;

	ObservableList<AlarmaPK> insertAll(ObservableList<Alarma> listToInsert) throws DAOException;

	void update(AlarmaPK pk, Alarma dto) throws DAOException;

	void updateAll(ObservableMap<AlarmaPK, Alarma> mapToUpdate) throws DAOException;

	void delete(AlarmaPK pk) throws DAOException;

	void deleteAll(ObservableList<Alarma> listToDelete) throws DAOException;

	void findAll() throws DAOException;

	Alarma findByPrimaryKey(AlarmaPK pk) throws DAOException;

	void setMaxRows(int maxRows);

	int getMaxRows();

	void createDependencies(GrupoDAO grupoDAO, OperarioDAO operarioDAO);

}