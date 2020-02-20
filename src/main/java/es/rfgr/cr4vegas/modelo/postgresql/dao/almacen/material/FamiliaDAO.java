package es.rfgr.cr4vegas.modelo.postgresql.dao.almacen.material;

import java.sql.Connection;

import es.rfgr.cr4vegas.modelo.postgresql.dto.almacen.material.Familia;
import es.rfgr.cr4vegas.modelo.postgresql.dto.almacen.material.FamiliaPK;
import es.rfgr.cr4vegas.modelo.postgresql.exceptions.DAOException;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;

public interface FamiliaDAO {

	void setConnection(Connection conn);

	ObservableList<Familia> getFamilias();

	int getNumFamilias();

	FamiliaPK insert(Familia dto) throws DAOException;

	ObservableList<FamiliaPK> insertAll(ObservableList<Familia> listToInsert) throws DAOException;

	void update(FamiliaPK pk, Familia dto) throws DAOException;

	void updateAll(ObservableMap<FamiliaPK, Familia> mapToUpdate) throws DAOException;

	void delete(FamiliaPK pk) throws DAOException;

	void deleteAll(ObservableList<Familia> listToDelete) throws DAOException;

	void findAll() throws DAOException;

	Familia findByPrimaryKey(FamiliaPK pk) throws DAOException;

	void setMaxRows(int maxRows);

	int getMaxRows();

}