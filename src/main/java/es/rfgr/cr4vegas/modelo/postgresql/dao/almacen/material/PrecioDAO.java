package es.rfgr.cr4vegas.modelo.postgresql.dao.almacen.material;

import java.sql.Connection;

import es.rfgr.cr4vegas.modelo.postgresql.dao.almacen.tienda.TiendaDAO;
import es.rfgr.cr4vegas.modelo.postgresql.dto.almacen.material.Material;
import es.rfgr.cr4vegas.modelo.postgresql.dto.almacen.material.Precio;
import es.rfgr.cr4vegas.modelo.postgresql.dto.almacen.material.PrecioPK;
import es.rfgr.cr4vegas.modelo.postgresql.dto.almacen.tienda.Tienda;
import es.rfgr.cr4vegas.modelo.postgresql.exceptions.DAOException;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;

public interface PrecioDAO {

	void setConnection(Connection conn);

	ObservableList<Precio> getPrecios();

	int getNumPrecios();

	PrecioPK insert(Precio dto) throws DAOException;

	ObservableList<PrecioPK> insertAll(ObservableList<Precio> listToInsert) throws DAOException;

	void update(PrecioPK pk, Precio dto) throws DAOException;

	void updateAll(ObservableMap<PrecioPK, Precio> mapToUpdate) throws DAOException;

	void delete(PrecioPK pk) throws DAOException;

	void deleteAll(ObservableList<Precio> listToDelete) throws DAOException;

	void findAll() throws DAOException;

	Precio findByPrimaryKey(PrecioPK pk) throws DAOException;

	void setMaxRows(int maxRows);

	int getMaxRows();
	
	void createDependencies(MaterialDAO materialDAO, TiendaDAO tiendaDAO);
	
	Precio getPrecioConMaterialYTienda(Material material, Tienda tienda);

	ObservableList<Precio> getPreciosMaterial(Material material);

}