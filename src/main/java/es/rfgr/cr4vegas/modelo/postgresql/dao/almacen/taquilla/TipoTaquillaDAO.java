package es.rfgr.cr4vegas.modelo.postgresql.dao.almacen.taquilla;

import java.sql.Connection;

import es.rfgr.cr4vegas.modelo.postgresql.dto.almacen.taquilla.TipoTaquilla;
import es.rfgr.cr4vegas.modelo.postgresql.dto.almacen.taquilla.TipoTaquillaPK;
import es.rfgr.cr4vegas.modelo.postgresql.exceptions.DAOException;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;

public interface TipoTaquillaDAO {

	void setConnection(Connection conn);

	ObservableList<TipoTaquilla> getTiposTaquilla();

	int getNumTiposTaquilla();

	TipoTaquillaPK insert(TipoTaquilla dto) throws DAOException;

	ObservableList<TipoTaquillaPK> insertAll(ObservableList<TipoTaquilla> listToInsert) throws DAOException;

	void update(TipoTaquillaPK pk, TipoTaquilla dto) throws DAOException;

	void updateAll(ObservableMap<TipoTaquillaPK, TipoTaquilla> mapToUpdate) throws DAOException;

	void delete(TipoTaquillaPK pk) throws DAOException;

	void deleteAll(ObservableList<TipoTaquilla> listToDelete) throws DAOException;

	void findAll() throws DAOException;

	void setMaxRows(int maxRows);

	int getMaxRows();

	TipoTaquilla findByPrimaryKey(TipoTaquillaPK pk) throws DAOException;

}