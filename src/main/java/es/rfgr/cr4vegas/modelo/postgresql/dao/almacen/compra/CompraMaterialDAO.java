package es.rfgr.cr4vegas.modelo.postgresql.dao.almacen.compra;

import java.sql.Connection;

import es.rfgr.cr4vegas.modelo.postgresql.dao.almacen.material.MaterialDAO;
import es.rfgr.cr4vegas.modelo.postgresql.dao.almacen.material.PrecioDAO;
import es.rfgr.cr4vegas.modelo.postgresql.dto.almacen.compra.Compra;
import es.rfgr.cr4vegas.modelo.postgresql.dto.almacen.compra.CompraMaterial;
import es.rfgr.cr4vegas.modelo.postgresql.dto.almacen.compra.CompraMaterialPK;
import es.rfgr.cr4vegas.modelo.postgresql.exceptions.DAOException;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;

public interface CompraMaterialDAO {

	void setConnection(Connection conn);

	ObservableList<CompraMaterial> getComprasMaterial();

	int getNumComprasMaterial();

	CompraMaterialPK insert(CompraMaterial dto) throws DAOException;

	ObservableList<CompraMaterialPK> insertAll(ObservableList<CompraMaterial> listToInsert) throws DAOException;

	void update(CompraMaterialPK pk, CompraMaterial dto) throws DAOException;

	void updateAll(ObservableMap<CompraMaterialPK, CompraMaterial> mapToUpdate) throws DAOException;

	void delete(CompraMaterialPK pk) throws DAOException;

	void deleteAll(ObservableList<CompraMaterial> listToDelete) throws DAOException;

	void findAll() throws DAOException;

	CompraMaterial findByPrimaryKey(CompraMaterialPK pk) throws DAOException;

	void setMaxRows(int maxRows);

	int getMaxRows();

	ObservableList<CompraMaterial> getCompraMateriales(Compra dto);

	void createDependencies(CompraDAO compraDAO, MaterialDAO materialDAO, PrecioDAO precioDAO);

}