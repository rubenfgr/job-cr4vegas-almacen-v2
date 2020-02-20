package es.rfgr.cr4vegas.modelo.postgresql.dao.parte;

import java.sql.Connection;

import es.rfgr.cr4vegas.modelo.postgresql.dao.almacen.material.MaterialDAO;
import es.rfgr.cr4vegas.modelo.postgresql.dto.parte.OperaParteMaterial;
import es.rfgr.cr4vegas.modelo.postgresql.dto.parte.OperaParteMaterialPK;
import es.rfgr.cr4vegas.modelo.postgresql.dto.parte.Parte;
import es.rfgr.cr4vegas.modelo.postgresql.exceptions.DAOException;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;

public interface OperaParteMaterialDAO {

	void setConnection(Connection conn);

	ObservableList<OperaParteMaterial> getOperaParteMaterial();

	int getNumOperaParteMaterial();

	OperaParteMaterialPK insert(OperaParteMaterial dto) throws DAOException;

	ObservableList<OperaParteMaterialPK> insertAll(ObservableList<OperaParteMaterial> listToInsert) throws DAOException;

	void update(OperaParteMaterialPK pk, OperaParteMaterial dto) throws DAOException;

	void updateAll(ObservableMap<OperaParteMaterialPK, OperaParteMaterial> mapToUpdate) throws DAOException;

	void delete(OperaParteMaterialPK pk) throws DAOException;

	void deleteAll(ObservableList<OperaParteMaterial> listToDelete) throws DAOException;

	void findAll() throws DAOException;

	OperaParteMaterial findByPrimaryKey(OperaParteMaterialPK pk) throws DAOException;

	void setMaxRows(int maxRows);

	int getMaxRows();

	ObservableList<OperaParteMaterial> getParteMateriales(Parte dto);

	void createDependencies(ParteDAO parteDAO, MaterialDAO materialDAO);

}