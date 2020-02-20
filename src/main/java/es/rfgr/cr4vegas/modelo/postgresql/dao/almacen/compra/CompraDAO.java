package es.rfgr.cr4vegas.modelo.postgresql.dao.almacen.compra;

import java.sql.Connection;

import es.rfgr.cr4vegas.modelo.postgresql.dao.almacen.tienda.TiendaDAO;
import es.rfgr.cr4vegas.modelo.postgresql.dao.operario.OperarioDAO;
import es.rfgr.cr4vegas.modelo.postgresql.dto.almacen.compra.Compra;
import es.rfgr.cr4vegas.modelo.postgresql.dto.almacen.compra.CompraPK;
import es.rfgr.cr4vegas.modelo.postgresql.exceptions.DAOException;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;

public interface CompraDAO {

	void setConnection(Connection conn);

	ObservableList<Compra> getCompras();	

	int getNumCompras();

	CompraPK insert(Compra dto) throws DAOException;

	ObservableList<CompraPK> insertAll(ObservableList<Compra> listToInsert) throws DAOException;

	void update(CompraPK pk, Compra dto) throws DAOException;

	void updateAll(ObservableMap<CompraPK, Compra> mapToUpdate) throws DAOException;

	void delete(CompraPK pk) throws DAOException;

	void deleteAll(ObservableList<Compra> listToDelete) throws DAOException;

	void findAll() throws DAOException;

	void updateDTOCompra(Compra Compra) throws DAOException;
	
	Compra findByPrimaryKey(CompraPK pk) throws DAOException;

	void setMaxRows(int maxRows);

	int getMaxRows();

	void createDependencies(TiendaDAO tiendaDAO, OperarioDAO operarioDAO);

}