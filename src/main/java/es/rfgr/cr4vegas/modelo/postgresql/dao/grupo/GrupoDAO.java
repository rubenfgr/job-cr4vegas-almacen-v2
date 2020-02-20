package es.rfgr.cr4vegas.modelo.postgresql.dao.grupo;

import java.sql.Connection;

import es.rfgr.cr4vegas.modelo.postgresql.dto.grupo.Grupo;
import es.rfgr.cr4vegas.modelo.postgresql.dto.grupo.GrupoPK;
import es.rfgr.cr4vegas.modelo.postgresql.exceptions.DAOException;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;

public interface GrupoDAO {

	void setConnection(Connection conn);

	ObservableList<Grupo> getGrupos();

	int getNumGrupos();

	GrupoPK insert(Grupo dto) throws DAOException;

	ObservableList<GrupoPK> insertAll(ObservableList<Grupo> listToInsert) throws DAOException;

	void update(GrupoPK pk, Grupo dto) throws DAOException;

	void updateAll(ObservableMap<GrupoPK, Grupo> mapToUpdate) throws DAOException;

	void delete(GrupoPK pk) throws DAOException;

	void deleteAll(ObservableList<Grupo> listToDelete) throws DAOException;

	void findAll() throws DAOException;

	Grupo findByPrimaryKey(GrupoPK pk) throws DAOException;

	void setMaxRows(int maxRows);

	int getMaxRows();

}