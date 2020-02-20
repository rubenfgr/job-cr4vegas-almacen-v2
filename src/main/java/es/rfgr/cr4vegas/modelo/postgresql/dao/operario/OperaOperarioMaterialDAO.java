package es.rfgr.cr4vegas.modelo.postgresql.dao.operario;

import java.sql.Connection;

import es.rfgr.cr4vegas.modelo.postgresql.dao.almacen.material.MaterialDAO;
import es.rfgr.cr4vegas.modelo.postgresql.dto.operario.OperaOperarioMaterial;
import es.rfgr.cr4vegas.modelo.postgresql.dto.operario.OperaOperarioMaterialPK;
import es.rfgr.cr4vegas.modelo.postgresql.dto.operario.Operario;
import es.rfgr.cr4vegas.modelo.postgresql.exceptions.DAOException;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;

public interface OperaOperarioMaterialDAO {

	void setConnection(Connection conn);

	ObservableList<OperaOperarioMaterial> getOperaOperarioMaterial();

	int getNumOperaOperarioMaterial();

	OperaOperarioMaterialPK insert(OperaOperarioMaterial dto) throws DAOException;

	ObservableList<OperaOperarioMaterialPK> insertAll(ObservableList<OperaOperarioMaterial> listToInsert)
			throws DAOException;

	void update(OperaOperarioMaterialPK pk, OperaOperarioMaterial dto) throws DAOException;

	void updateAll(ObservableMap<OperaOperarioMaterialPK, OperaOperarioMaterial> mapToUpdate) throws DAOException;

	void delete(OperaOperarioMaterialPK pk) throws DAOException;

	void deleteAll(ObservableList<OperaOperarioMaterial> listToDelete) throws DAOException;

	void findAll() throws DAOException;

	OperaOperarioMaterial findByPrimaryKey(OperaOperarioMaterialPK pk) throws DAOException;

	void setMaxRows(int maxRows);

	int getMaxRows();

	ObservableList<OperaOperarioMaterial> getOperarioMateriales(Operario dto);

	ObservableList<OperaOperarioMaterial> getOperarioOperaOperariosMateriales(Operario dto);

	void createDepedencies(OperarioDAO operarioDAO, MaterialDAO materialDAO);

}