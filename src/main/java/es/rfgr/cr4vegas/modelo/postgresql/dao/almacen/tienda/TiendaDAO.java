package es.rfgr.cr4vegas.modelo.postgresql.dao.almacen.tienda;

import java.sql.Connection;

import es.rfgr.cr4vegas.modelo.postgresql.dto.almacen.tienda.Tienda;
import es.rfgr.cr4vegas.modelo.postgresql.dto.almacen.tienda.TiendaPK;
import es.rfgr.cr4vegas.modelo.postgresql.exceptions.DAOException;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;

public interface TiendaDAO {

	void setConnection(Connection conn);

	ObservableList<Tienda> getTiendas();

	int getNumTiendas();

	TiendaPK insert(Tienda dto) throws DAOException;

	ObservableList<TiendaPK> insertAll(ObservableList<Tienda> listToInsert) throws DAOException;

	void update(TiendaPK pk, Tienda dto) throws DAOException;

	void updateAll(ObservableMap<TiendaPK, Tienda> mapToUpdate) throws DAOException;

	void delete(TiendaPK pk) throws DAOException;

	void deleteAll(ObservableList<Tienda> listToDelete) throws DAOException;

	void findAll() throws DAOException;

	Tienda findByPrimaryKey(TiendaPK pk) throws DAOException;

	void setMaxRows(int maxRows);

	int getMaxRows();

}