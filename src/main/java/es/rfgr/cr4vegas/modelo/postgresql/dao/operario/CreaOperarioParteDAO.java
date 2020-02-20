package es.rfgr.cr4vegas.modelo.postgresql.dao.operario;

import java.sql.Connection;

import es.rfgr.cr4vegas.modelo.postgresql.dao.parte.ParteDAO;
import es.rfgr.cr4vegas.modelo.postgresql.dto.operario.CreaOperarioParte;
import es.rfgr.cr4vegas.modelo.postgresql.dto.operario.CreaOperarioPartePK;
import es.rfgr.cr4vegas.modelo.postgresql.dto.operario.Operario;
import es.rfgr.cr4vegas.modelo.postgresql.exceptions.DAOException;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;

public interface CreaOperarioParteDAO {

	void setConnection(Connection conn);

	ObservableList<CreaOperarioParte> getCreaOperarioParte();

	int getNumCreaOperarioParte();

	CreaOperarioPartePK insert(CreaOperarioParte dto) throws DAOException;

	ObservableList<CreaOperarioPartePK> insertAll(ObservableList<CreaOperarioParte> listToInsert) throws DAOException;

	void update(CreaOperarioPartePK pk, CreaOperarioParte dto) throws DAOException;

	void updateAll(ObservableMap<CreaOperarioPartePK, CreaOperarioParte> mapToUpdate) throws DAOException;

	void delete(CreaOperarioPartePK pk) throws DAOException;

	void deleteAll(ObservableList<CreaOperarioParte> listToDelete) throws DAOException;

	void findAll() throws DAOException;

	CreaOperarioParte findByPrimaryKey(CreaOperarioPartePK pk) throws DAOException;

	void setMaxRows(int maxRows);

	int getMaxRows();

	ObservableList<CreaOperarioParte> getOperarioPartes(Operario dto);

	void createDependencies(OperarioDAO operarioDAO, ParteDAO parteDAO);

}