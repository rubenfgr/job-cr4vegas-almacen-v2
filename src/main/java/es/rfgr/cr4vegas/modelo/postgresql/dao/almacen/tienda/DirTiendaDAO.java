package es.rfgr.cr4vegas.modelo.postgresql.dao.almacen.tienda;

import java.sql.Connection;

import es.rfgr.cr4vegas.modelo.postgresql.dto.almacen.tienda.DirTienda;
import es.rfgr.cr4vegas.modelo.postgresql.dto.almacen.tienda.DirTiendaPK;
import es.rfgr.cr4vegas.modelo.postgresql.dto.almacen.tienda.Tienda;
import es.rfgr.cr4vegas.modelo.postgresql.exceptions.DAOException;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;

public interface DirTiendaDAO {

	void setConnection(Connection conn);

	ObservableList<DirTienda> getDirTiendas();

	int getNumDirTiendas();

	DirTiendaPK insert(DirTienda dto) throws DAOException;

	ObservableList<DirTiendaPK> insertAll(ObservableList<DirTienda> listToInsert) throws DAOException;

	void update(DirTiendaPK pk, DirTienda dto) throws DAOException;

	void updateAll(ObservableMap<DirTiendaPK, DirTienda> mapToUpdate) throws DAOException;

	void delete(DirTiendaPK pk) throws DAOException;

	void deleteAll(ObservableList<DirTienda> listToDelete) throws DAOException;

	void findAll() throws DAOException;

	DirTienda findByPrimaryKey(DirTiendaPK pk) throws DAOException;

	void setMaxRows(int maxRows);

	int getMaxRows();

	DirTienda getDirTienda(Tienda dto);

	void createDependencies(TiendaDAO tiendaDAO);

}