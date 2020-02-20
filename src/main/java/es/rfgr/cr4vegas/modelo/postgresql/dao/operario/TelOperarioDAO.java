package es.rfgr.cr4vegas.modelo.postgresql.dao.operario;

import java.sql.Connection;

import es.rfgr.cr4vegas.modelo.postgresql.dto.operario.Operario;
import es.rfgr.cr4vegas.modelo.postgresql.dto.operario.TelOperario;
import es.rfgr.cr4vegas.modelo.postgresql.dto.operario.TelOperarioPK;
import es.rfgr.cr4vegas.modelo.postgresql.exceptions.DAOException;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;

public interface TelOperarioDAO {

	void setConnection(Connection conn);

	ObservableList<TelOperario> getTelOperarios();

	int getNumTelOperarios();

	TelOperarioPK insert(TelOperario dto) throws DAOException;

	ObservableList<TelOperarioPK> insertAll(ObservableList<TelOperario> listToInsert) throws DAOException;

	void update(TelOperarioPK pk, TelOperario dto) throws DAOException;

	void updateAll(ObservableMap<TelOperarioPK, TelOperario> mapToUpdate) throws DAOException;

	void delete(TelOperarioPK pk) throws DAOException;

	void deleteAll(ObservableList<TelOperario> listToDelete) throws DAOException;

	void findAll() throws DAOException;

	TelOperario findByPrimaryKey(TelOperarioPK pk) throws DAOException;

	void setMaxRows(int maxRows);

	int getMaxRows();

	ObservableList<TelOperario> getOperarioTelefonos(Operario dto);

	void createDependencies(OperarioDAO operarioDAO);

}