package es.rfgr.cr4vegas.modelo.postgresql.dao.almacen.presupuesto;

import java.sql.Connection;

import es.rfgr.cr4vegas.modelo.postgresql.dao.almacen.material.MaterialDAO;
import es.rfgr.cr4vegas.modelo.postgresql.dto.almacen.presupuesto.Presupuesto;
import es.rfgr.cr4vegas.modelo.postgresql.dto.almacen.presupuesto.TienePresupuestoMaterial;
import es.rfgr.cr4vegas.modelo.postgresql.dto.almacen.presupuesto.TienePresupuestoMaterialPK;
import es.rfgr.cr4vegas.modelo.postgresql.exceptions.DAOException;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;

public interface TienePresupuestoMaterialDAO {

	void setConnection(Connection conn);

	ObservableList<TienePresupuestoMaterial> getTienePresupuestoMaterial();

	int getNumTienePresupuestoMaterial();

	TienePresupuestoMaterialPK insert(TienePresupuestoMaterial dto) throws DAOException;

	ObservableList<TienePresupuestoMaterialPK> insertAll(ObservableList<TienePresupuestoMaterial> listToInsert)
			throws DAOException;

	void update(TienePresupuestoMaterialPK pk, TienePresupuestoMaterial dto) throws DAOException;

	void updateAll(ObservableMap<TienePresupuestoMaterialPK, TienePresupuestoMaterial> mapToUpdate) throws DAOException;

	void delete(TienePresupuestoMaterialPK pk) throws DAOException;

	void deleteAll(ObservableList<TienePresupuestoMaterial> listToDelete) throws DAOException;

	void findAll() throws DAOException;

	TienePresupuestoMaterial findByPrimaryKey(TienePresupuestoMaterialPK pk) throws DAOException;

	void setMaxRows(int maxRows);

	int getMaxRows();

	ObservableList<TienePresupuestoMaterial> getPresupuestoMateriales(Presupuesto dto);

	void createDependencies(PresupuestoDAO presupuestoDAO, MaterialDAO materialDAO);

}