package es.rfgr.cr4vegas.modelo.postgresql.dao.almacen.material;

import java.sql.Connection;

import es.rfgr.cr4vegas.modelo.postgresql.dto.almacen.material.TipoMaterial;
import es.rfgr.cr4vegas.modelo.postgresql.dto.almacen.material.TipoMaterialPK;
import es.rfgr.cr4vegas.modelo.postgresql.exceptions.DAOException;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;

public interface TipoMaterialDAO {

	void setConnection(Connection conn);

	ObservableList<TipoMaterial> getTiposMaterial();

	int getNumTiposMaterial();

	TipoMaterialPK insert(TipoMaterial dto) throws DAOException;

	ObservableList<TipoMaterialPK> insertAll(ObservableList<TipoMaterial> listToInsert) throws DAOException;

	void update(TipoMaterialPK pk, TipoMaterial dto) throws DAOException;

	void updateAll(ObservableMap<TipoMaterialPK, TipoMaterial> mapToUpdate) throws DAOException;

	void delete(TipoMaterialPK pk) throws DAOException;

	void deleteAll(ObservableList<TipoMaterial> listToDelete) throws DAOException;

	void findAll() throws DAOException;

	TipoMaterial findByPrimaryKey(TipoMaterialPK pk) throws DAOException;

	void setMaxRows(int maxRows);

	int getMaxRows();

}