package es.rfgr.cr4vegas.modelo.postgresql.dao.parte;

import java.sql.Connection;

import es.rfgr.cr4vegas.modelo.postgresql.dao.grupo.GrupoDAO;
import es.rfgr.cr4vegas.modelo.postgresql.dao.parteoficial.ParteOficialDAO;
import es.rfgr.cr4vegas.modelo.postgresql.dto.parte.Parte;
import es.rfgr.cr4vegas.modelo.postgresql.dto.parte.PartePK;
import es.rfgr.cr4vegas.modelo.postgresql.exceptions.DAOException;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;

public interface ParteDAO {

	void setConnection(Connection conn);

	ObservableList<Parte> getPartes();

	int getNumPartes();

	PartePK insert(Parte dto) throws DAOException;

	ObservableList<PartePK> insertAll(ObservableList<Parte> listToInsert) throws DAOException;

	void update(PartePK pk, Parte dto) throws DAOException;

	void updateAll(ObservableMap<PartePK, Parte> mapToUpdate) throws DAOException;

	void delete(PartePK pk) throws DAOException;

	void deleteAll(ObservableList<Parte> listToDelete) throws DAOException;

	void findAll() throws DAOException;

	Parte findByPrimaryKey(PartePK pk) throws DAOException;

	void setMaxRows(int maxRows);

	int getMaxRows();

	void createDependencies(TipoParteDAO tipoParteDAO, GrupoDAO grupoDAO);

	void createDependencies(ParteOficialDAO parteOficialDAO);

}