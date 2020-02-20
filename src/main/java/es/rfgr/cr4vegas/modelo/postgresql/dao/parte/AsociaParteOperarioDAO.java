package es.rfgr.cr4vegas.modelo.postgresql.dao.parte;

import java.sql.Connection;

import es.rfgr.cr4vegas.modelo.postgresql.dao.operario.OperarioDAO;
import es.rfgr.cr4vegas.modelo.postgresql.dto.parte.AsociaParteOperario;
import es.rfgr.cr4vegas.modelo.postgresql.dto.parte.AsociaParteOperarioPK;
import es.rfgr.cr4vegas.modelo.postgresql.dto.parte.Parte;
import es.rfgr.cr4vegas.modelo.postgresql.exceptions.DAOException;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;

public interface AsociaParteOperarioDAO {

	void setConnection(Connection conn);

	ObservableList<AsociaParteOperario> getAsociaParteOperario();

	int getNumAsociaParteOperario();

	AsociaParteOperarioPK insert(AsociaParteOperario dto) throws DAOException;

	ObservableList<AsociaParteOperarioPK> insertAll(ObservableList<AsociaParteOperario> listToInsert)
			throws DAOException;

	void update(AsociaParteOperarioPK pk, AsociaParteOperario dto) throws DAOException;

	void updateAll(ObservableMap<AsociaParteOperarioPK, AsociaParteOperario> mapToUpdate) throws DAOException;

	void delete(AsociaParteOperarioPK pk) throws DAOException;

	void deleteAll(ObservableList<AsociaParteOperario> listToDelete) throws DAOException;

	void findAll() throws DAOException;

	AsociaParteOperario findByPrimaryKey(AsociaParteOperarioPK pk) throws DAOException;

	void setMaxRows(int maxRows);

	int getMaxRows();

	ObservableList<AsociaParteOperario> getParteOperarios(Parte dto);

	void createDependencies(ParteDAO parteDAO, OperarioDAO operarioDAO);

}