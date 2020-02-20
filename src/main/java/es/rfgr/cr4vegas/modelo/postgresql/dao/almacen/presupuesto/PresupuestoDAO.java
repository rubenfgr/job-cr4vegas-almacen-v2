package es.rfgr.cr4vegas.modelo.postgresql.dao.almacen.presupuesto;

import java.sql.Connection;

import es.rfgr.cr4vegas.modelo.postgresql.dto.almacen.presupuesto.Presupuesto;
import es.rfgr.cr4vegas.modelo.postgresql.dto.almacen.presupuesto.PresupuestoPK;
import es.rfgr.cr4vegas.modelo.postgresql.exceptions.DAOException;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;

public interface PresupuestoDAO {

	void setConnection(Connection conn);

	ObservableList<Presupuesto> getPresupuestos();

	int getNumPresupuestos();

	PresupuestoPK insert(Presupuesto dto) throws DAOException;

	ObservableList<PresupuestoPK> insertAll(ObservableList<Presupuesto> listToInsert) throws DAOException;

	void update(PresupuestoPK pk, Presupuesto dto) throws DAOException;

	void updateAll(ObservableMap<PresupuestoPK, Presupuesto> mapToUpdate) throws DAOException;

	void delete(PresupuestoPK pk) throws DAOException;

	void deleteAll(ObservableList<Presupuesto> listToDelete) throws DAOException;

	void findAll() throws DAOException;

	Presupuesto findByPrimaryKey(PresupuestoPK pk) throws DAOException;

	void setMaxRows(int maxRows);

	int getMaxRows();

}