package es.rfgr.cr4vegas.modelo.postgresql.dao.parte;

import java.sql.Connection;

import es.rfgr.cr4vegas.modelo.postgresql.dto.parte.TipoParte;
import es.rfgr.cr4vegas.modelo.postgresql.dto.parte.TipoPartePK;
import es.rfgr.cr4vegas.modelo.postgresql.exceptions.DAOException;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;

public interface TipoParteDAO {

	void setConnection(Connection conn);

	ObservableList<TipoParte> getTiposParte();

	int getNumTiposParte();

	TipoPartePK insert(TipoParte dto) throws DAOException;

	ObservableList<TipoPartePK> insertAll(ObservableList<TipoParte> listToInsert) throws DAOException;

	void update(TipoPartePK pk, TipoParte dto) throws DAOException;

	void updateAll(ObservableMap<TipoPartePK, TipoParte> mapToUpdate) throws DAOException;

	void delete(TipoPartePK pk) throws DAOException;

	void deleteAll(ObservableList<TipoParte> listToDelete) throws DAOException;

	void findAll() throws DAOException;

	TipoParte findByPrimaryKey(TipoPartePK pk) throws DAOException;

	void setMaxRows(int maxRows);

	int getMaxRows();

}