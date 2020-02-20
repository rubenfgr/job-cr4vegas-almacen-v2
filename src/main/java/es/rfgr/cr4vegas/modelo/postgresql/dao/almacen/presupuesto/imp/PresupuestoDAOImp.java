package es.rfgr.cr4vegas.modelo.postgresql.dao.almacen.presupuesto.imp;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

import javax.management.OperationsException;

import es.rfgr.cr4vegas.modelo.postgresql.bd.almacen.presupuesto.PresupuestoBD;
import es.rfgr.cr4vegas.modelo.postgresql.dao.almacen.presupuesto.PresupuestoDAO;
import es.rfgr.cr4vegas.modelo.postgresql.dto.almacen.presupuesto.Presupuesto;
import es.rfgr.cr4vegas.modelo.postgresql.dto.almacen.presupuesto.PresupuestoPK;
import es.rfgr.cr4vegas.modelo.postgresql.exceptions.DAOException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;

public class PresupuestoDAOImp implements PresupuestoDAO {

	private static final String ERROR = "PresupuestoDAO: ";

	private ObservableList<Presupuesto> listaPresupuestos;

	private PresupuestoBD presupuestoBD;

	public PresupuestoDAOImp() {
		listaPresupuestos = FXCollections.observableArrayList();
		presupuestoBD = new PresupuestoBD();
	}

	public void setConnection(Connection conn) {
		presupuestoBD.setConnection(conn);
	}

	public ObservableList<Presupuesto> getPresupuestos() {
		return listaPresupuestos;
	}

	public int getNumPresupuestos() {
		return listaPresupuestos.size();
	}

	public PresupuestoPK insert(Presupuesto dto) throws DAOException {
		PresupuestoPK pk = dto.createPK();

		try {
			if (listaPresupuestos.contains(dto)) {
				throw new OperationsException("El presupuesto ya existe.");
			} else {
				pk = presupuestoBD.insert(dto);

				this.listaPresupuestos.add(dto);
			}
		} catch (Exception e) {
			throwException(e);
		}
		return pk;
	}

	public ObservableList<PresupuestoPK> insertAll(ObservableList<Presupuesto> listToInsert) throws DAOException {
		ObservableList<PresupuestoPK> listToReturn = FXCollections.observableArrayList();
		for (Presupuesto dto : listToInsert) {
			PresupuestoPK dtoPK = insert(dto);
			if (dtoPK != null) {
				listToReturn.add(dtoPK);
			}
		}
		return listToReturn;
	}

	public void update(PresupuestoPK pk, Presupuesto dto) throws DAOException {
		Presupuesto oldPresupuesto = getOldPresupuesto(pk);

		try {
			if (dto == null) {
				throw new OperationsException("El presupuesto a modificar no puede ser nulo.");
			}
			if (!listaPresupuestos.contains(oldPresupuesto)) {
				throw new OperationsException("El presupuesto a modificar no existe.");

			} else {
				presupuestoBD.update(pk, dto);

				listaPresupuestos.set(listaPresupuestos.indexOf(oldPresupuesto), dto);
			}
		} catch (Exception e) {
			throwException(e);
		}
	}

	public void updateAll(ObservableMap<PresupuestoPK, Presupuesto> mapToUpdate) throws DAOException {
		for (Map.Entry<PresupuestoPK, Presupuesto> entry : mapToUpdate.entrySet()) {
			update(entry.getKey(), entry.getValue());
		}
	}

	public void delete(PresupuestoPK pk) throws DAOException {
		Presupuesto oldPresupuesto = getOldPresupuesto(pk);

		try {
			if (pk == null) {
				throw new NullPointerException("No se puede eliminar un presupuesto nulo.");
			}
			if (!listaPresupuestos.contains(oldPresupuesto)) {
				throw new OperationsException("El presupuesto a eliminar no existe.");

			} else {
				presupuestoBD.delete(pk);

				listaPresupuestos.remove(oldPresupuesto);
			}
		} catch (Exception e) {
			throwException(e);
		}
	}

	public void deleteAll(ObservableList<Presupuesto> listToDelete) throws DAOException {
		for (Presupuesto dto : listToDelete) {
			delete(dto.createPK());
		}
	}

	public void findAll() throws DAOException {
		try {
			listaPresupuestos.setAll(presupuestoBD.findAll());

		} catch (SQLException e) {
			throwException(e);
		}
	}

	public Presupuesto findByPrimaryKey(PresupuestoPK pk) throws DAOException {
		try {
			return presupuestoBD.findByPrimaryKey(pk);

		} catch (SQLException e) {
			throwException(e);
		}
		return null;
	}

	public void setMaxRows(int maxRows) {
		presupuestoBD.setMaxRows(maxRows);
	}

	public int getMaxRows() {
		return presupuestoBD.getMaxRows();
	}

	private void throwException(Exception e) throws DAOException {
		throw new DAOException(ERROR + e.getMessage(), e);
	}

	private Presupuesto getOldPresupuesto(PresupuestoPK pk) {
		Presupuesto oldPresupuesto = new Presupuesto();
		oldPresupuesto.setCodPresupuesto(pk.getCodPresupuesto());
		oldPresupuesto = listaPresupuestos.get(listaPresupuestos.indexOf(oldPresupuesto));
		return oldPresupuesto;
	}
}
