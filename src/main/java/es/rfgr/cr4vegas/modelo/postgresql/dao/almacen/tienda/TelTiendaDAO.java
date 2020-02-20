package es.rfgr.cr4vegas.modelo.postgresql.dao.almacen.tienda;

import java.sql.Connection;

import es.rfgr.cr4vegas.modelo.postgresql.dto.almacen.tienda.TelTienda;
import es.rfgr.cr4vegas.modelo.postgresql.dto.almacen.tienda.TelTiendaPK;
import es.rfgr.cr4vegas.modelo.postgresql.dto.almacen.tienda.Tienda;
import es.rfgr.cr4vegas.modelo.postgresql.exceptions.DAOException;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;

public interface TelTiendaDAO {

	void setConnection(Connection conn);

	ObservableList<TelTienda> getTelTiendas();

	int getNumTelTiendas();

	TelTiendaPK insert(TelTienda dto) throws DAOException;

	ObservableList<TelTiendaPK> insertAll(ObservableList<TelTienda> listToInsert) throws DAOException;

	void update(TelTiendaPK pk, TelTienda dto) throws DAOException;

	void updateAll(ObservableMap<TelTiendaPK, TelTienda> mapToUpdate) throws DAOException;

	void delete(TelTiendaPK pk) throws DAOException;

	void deleteAll(ObservableList<TelTienda> listToDelete) throws DAOException;

	void findAll() throws DAOException;

	TelTienda findByPrimaryKey(TelTiendaPK pk) throws DAOException;

	void setMaxRows(int maxRows);

	int getMaxRows();

	ObservableList<TelTienda> getTelTienda(Tienda dto);

	void createDependencies(TiendaDAO tiendaDAO);

}