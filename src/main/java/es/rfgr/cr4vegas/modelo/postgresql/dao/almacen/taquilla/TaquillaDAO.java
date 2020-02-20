package es.rfgr.cr4vegas.modelo.postgresql.dao.almacen.taquilla;

import java.sql.Connection;

import es.rfgr.cr4vegas.modelo.postgresql.dto.almacen.taquilla.Taquilla;
import es.rfgr.cr4vegas.modelo.postgresql.dto.almacen.taquilla.TaquillaPK;
import es.rfgr.cr4vegas.modelo.postgresql.exceptions.DAOException;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;

public interface TaquillaDAO {

	void setConnection(Connection conn);

	ObservableList<Taquilla> getTaquillas();

	int getNumTaquillas();

	TaquillaPK insert(Taquilla dto) throws DAOException;

	ObservableList<TaquillaPK> insertAll(ObservableList<Taquilla> listToInsert) throws DAOException;

	void update(TaquillaPK pk, Taquilla dto) throws DAOException;

	void updateAll(ObservableMap<TaquillaPK, Taquilla> mapToUpdate) throws DAOException;

	void delete(TaquillaPK pk) throws DAOException;

	void deleteAll(ObservableList<Taquilla> listToDelete) throws DAOException;

	void findAll() throws DAOException;

	Taquilla findByPrimaryKey(TaquillaPK pk) throws DAOException;

	void setMaxRows(int maxRows);

	int getMaxRows();

	void createDependencies(TipoTaquillaDAO tiposTaquilla);

}