package es.rfgr.cr4vegas.modelo.postgresql.dao.almacen.taquilla;

import java.sql.Connection;

import es.rfgr.cr4vegas.modelo.postgresql.dao.almacen.material.MaterialDAO;
import es.rfgr.cr4vegas.modelo.postgresql.dto.almacen.taquilla.OperaTaquillaMaterial;
import es.rfgr.cr4vegas.modelo.postgresql.dto.almacen.taquilla.OperaTaquillaMaterialPK;
import es.rfgr.cr4vegas.modelo.postgresql.dto.almacen.taquilla.Taquilla;
import es.rfgr.cr4vegas.modelo.postgresql.exceptions.DAOException;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;

public interface OperaTaquillaMaterialDAO {

	void setConnection(Connection conn);

	ObservableList<OperaTaquillaMaterial> getOperaTaquillaMateriales();

	int getNumOperaTaquillaMateriales();

	OperaTaquillaMaterialPK insert(OperaTaquillaMaterial dto) throws DAOException;

	ObservableList<OperaTaquillaMaterialPK> insertAll(ObservableList<OperaTaquillaMaterial> listToInsert)
			throws DAOException;

	void update(OperaTaquillaMaterialPK pk, OperaTaquillaMaterial dto) throws DAOException;

	void updateAll(ObservableMap<OperaTaquillaMaterialPK, OperaTaquillaMaterial> mapToUpdate) throws DAOException;

	void delete(OperaTaquillaMaterialPK pk) throws DAOException;

	void deleteAll(ObservableList<OperaTaquillaMaterial> listToDelete) throws DAOException;

	void findAll() throws DAOException;

	OperaTaquillaMaterial findByPrimaryKey(OperaTaquillaMaterialPK pk) throws DAOException;

	void setMaxRows(int maxRows);

	int getMaxRows();

	ObservableList<OperaTaquillaMaterial> getTaquillaMateriales(Taquilla dto);

	void createDependencies(TaquillaDAO taquillaDAO, MaterialDAO materialDAO);

}