package es.rfgr.cr4vegas.modelo.postgresql.dao.operario;

import java.sql.Connection;

import es.rfgr.cr4vegas.modelo.postgresql.dto.operario.DirOperario;
import es.rfgr.cr4vegas.modelo.postgresql.dto.operario.DirOperarioPK;
import es.rfgr.cr4vegas.modelo.postgresql.dto.operario.Operario;
import es.rfgr.cr4vegas.modelo.postgresql.exceptions.DAOException;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;

public interface DirOperarioDAO {

	void setConnection(Connection conn);

	ObservableList<DirOperario> getDirOperarios();

	int getNumDirOperarios();

	DirOperarioPK insert(DirOperario dto) throws DAOException;

	ObservableList<DirOperarioPK> insertAll(ObservableList<DirOperario> listToInsert) throws DAOException;

	void update(DirOperarioPK pk, DirOperario dto) throws DAOException;

	void updateAll(ObservableMap<DirOperarioPK, DirOperario> mapToUpdate) throws DAOException;

	void delete(DirOperarioPK pk) throws DAOException;

	void deleteAll(ObservableList<DirOperario> listToDelete) throws DAOException;

	void findAll() throws DAOException;

	DirOperario findByPrimaryKey(DirOperarioPK pk) throws DAOException;

	void setMaxRows(int maxRows);

	int getMaxRows();

	DirOperario getDirOperario(Operario dto);

	void createDependencies(OperarioDAO operarioDAO);

}