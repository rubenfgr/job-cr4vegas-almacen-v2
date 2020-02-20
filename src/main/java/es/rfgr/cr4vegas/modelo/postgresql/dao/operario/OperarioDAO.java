package es.rfgr.cr4vegas.modelo.postgresql.dao.operario;

import java.sql.Connection;

import es.rfgr.cr4vegas.modelo.postgresql.dao.grupo.GrupoDAO;
import es.rfgr.cr4vegas.modelo.postgresql.dto.operario.Operario;
import es.rfgr.cr4vegas.modelo.postgresql.dto.operario.OperarioPK;
import es.rfgr.cr4vegas.modelo.postgresql.exceptions.DAOException;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;

public interface OperarioDAO {

	void setConnection(Connection conn);

	ObservableList<Operario> getOperarios();

	int getNumOperarios();

	OperarioPK insert(Operario dto) throws DAOException;

	ObservableList<OperarioPK> insertAll(ObservableList<Operario> listToInsert) throws DAOException;

	void update(OperarioPK pk, Operario dto) throws DAOException;

	void updateAll(ObservableMap<OperarioPK, Operario> mapToUpdate) throws DAOException;

	void delete(OperarioPK pk) throws DAOException;

	void deleteAll(ObservableList<Operario> listToDelete) throws DAOException;

	void findAll() throws DAOException;

	Operario findByPrimaryKey(OperarioPK pk) throws DAOException;

	void setMaxRows(int maxRows);

	int getMaxRows();

	void createDependencies(GrupoDAO grupoDAO);

	void dependencyOperarioGrupo(Operario dto, GrupoDAO grupoDAO);

}