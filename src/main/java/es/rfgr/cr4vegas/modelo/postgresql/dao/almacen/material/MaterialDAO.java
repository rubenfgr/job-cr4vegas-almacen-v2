package es.rfgr.cr4vegas.modelo.postgresql.dao.almacen.material;

import java.sql.Connection;

import es.rfgr.cr4vegas.modelo.postgresql.dto.almacen.material.Material;
import es.rfgr.cr4vegas.modelo.postgresql.dto.almacen.material.MaterialPK;
import es.rfgr.cr4vegas.modelo.postgresql.exceptions.DAOException;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;

public interface MaterialDAO {

	void setConnection(Connection conn);

	ObservableList<Material> getMateriales();

	int getNumMateriales();

	MaterialPK insert(Material dto) throws DAOException;

	ObservableList<MaterialPK> insertAll(ObservableList<Material> listToInsert) throws DAOException;

	void update(MaterialPK pk, Material dto) throws DAOException;

	void updateAll(ObservableMap<MaterialPK, Material> mapToUpdate) throws DAOException;

	void delete(MaterialPK pk) throws DAOException;

	void deleteAll(ObservableList<Material> listToDelete) throws DAOException;

	void findAll() throws DAOException;

	void updateDTOMaterial(Material material) throws DAOException;

	Material findByPrimaryKey(MaterialPK pk) throws DAOException;

	void setMaxRows(int maxRows);

	int getMaxRows();

	void createDependencies(UbicacionDAO ubicacionDAO, TipoMaterialDAO tipoMaterialDAO, FamiliaDAO familiaDAO);

}