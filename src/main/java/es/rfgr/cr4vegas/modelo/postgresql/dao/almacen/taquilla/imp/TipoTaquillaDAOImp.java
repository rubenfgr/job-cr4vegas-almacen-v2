package es.rfgr.cr4vegas.modelo.postgresql.dao.almacen.taquilla.imp;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

import javax.management.OperationsException;

import es.rfgr.cr4vegas.modelo.postgresql.bd.almacen.taquilla.TipoTaquillaBD;
import es.rfgr.cr4vegas.modelo.postgresql.dao.almacen.taquilla.TipoTaquillaDAO;
import es.rfgr.cr4vegas.modelo.postgresql.dto.almacen.taquilla.TipoTaquilla;
import es.rfgr.cr4vegas.modelo.postgresql.dto.almacen.taquilla.TipoTaquillaPK;
import es.rfgr.cr4vegas.modelo.postgresql.exceptions.DAOException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;

public class TipoTaquillaDAOImp implements TipoTaquillaDAO {

	private static final String ERROR = "TipoTaquillaDAO: ";

	private ObservableList<TipoTaquilla> listaTiposTaquilla;

	private TipoTaquillaBD tipoTaquillaBD;

	public TipoTaquillaDAOImp() {
		listaTiposTaquilla = FXCollections.observableArrayList();
		tipoTaquillaBD = new TipoTaquillaBD();
	}

	public void setConnection(Connection conn) {
		tipoTaquillaBD.setConnection(conn);
	}

	public ObservableList<TipoTaquilla> getTiposTaquilla() {
		return listaTiposTaquilla;
	}

	public int getNumTiposTaquilla() {
		return listaTiposTaquilla.size();
	}

	public TipoTaquillaPK insert(TipoTaquilla dto) throws DAOException {
		TipoTaquillaPK pk = dto.createPK();

		try {
			if (listaTiposTaquilla.contains(dto)) {
				throw new OperationsException("El tipo de taquilla ya existe.");
			} else {
				pk = tipoTaquillaBD.insert(dto);

				this.listaTiposTaquilla.add(dto);
			}
		} catch (Exception e) {
			throwException(e);
		}
		return pk;
	}
	
	public ObservableList<TipoTaquillaPK> insertAll(ObservableList<TipoTaquilla> listToInsert) throws DAOException {
		ObservableList<TipoTaquillaPK> listToReturn = FXCollections.observableArrayList();
		for (TipoTaquilla dto : listToInsert) {
			TipoTaquillaPK dtoPK = insert(dto);
			if (dtoPK != null) {
				listToReturn.add(dtoPK);
			}
		}
		return listToReturn;
	}

	public void update(TipoTaquillaPK pk, TipoTaquilla dto) throws DAOException {
		TipoTaquilla oldTipoTaquilla = getOldTipoTaquilla(pk);

		try {
			if (dto == null) {
				throw new OperationsException("El tipo de taquilla a modificar no puede ser nulo.");
			}
			if (!listaTiposTaquilla.contains(oldTipoTaquilla)) {
				throw new OperationsException("El tipo de taquilla a modificar no existe.");

			} else {
				tipoTaquillaBD.update(pk, dto);

				listaTiposTaquilla.set(listaTiposTaquilla.indexOf(oldTipoTaquilla), dto);
			}
		} catch (Exception e) {
			throwException(e);
		}
	}
	
	public void updateAll(ObservableMap<TipoTaquillaPK, TipoTaquilla> mapToUpdate) throws DAOException {
		for (Map.Entry<TipoTaquillaPK, TipoTaquilla> entry : mapToUpdate.entrySet()) {
			update(entry.getKey(), entry.getValue());
		}
	}

	public void delete(TipoTaquillaPK pk) throws DAOException {
		TipoTaquilla oldTipoTaquilla = getOldTipoTaquilla(pk);

		try {
			if (pk == null) {
				throw new NullPointerException("No se puede eliminar un tipo de taquilla nulo.");
			}
			if (!listaTiposTaquilla.contains(oldTipoTaquilla)) {
				throw new OperationsException("El tipo de taquilla a eliminar no existe.");

			} else {
				tipoTaquillaBD.delete(pk);

				listaTiposTaquilla.remove(oldTipoTaquilla);
			}
		} catch (Exception e) {
			throwException(e);
		}
	}
	
	public void deleteAll(ObservableList<TipoTaquilla> listToDelete) throws DAOException {
		for (TipoTaquilla dto : listToDelete) {
			delete(dto.createPK());
		}
	}

	public void findAll() throws DAOException {
		try {
			listaTiposTaquilla.setAll(tipoTaquillaBD.findAll());

		} catch (SQLException e) {
			throwException(e);
		}
	}
	
	public void setMaxRows(int maxRows) {
		tipoTaquillaBD.setMaxRows(maxRows);
	}

	public int getMaxRows() {
		return tipoTaquillaBD.getMaxRows();
	}

	public TipoTaquilla findByPrimaryKey(TipoTaquillaPK pk) throws DAOException {
		try {
			return tipoTaquillaBD.findByPrimaryKey(pk);

		} catch (SQLException e) {
			throwException(e);
		}
		return null;
	}

	private void throwException(Exception e) throws DAOException {
		throw new DAOException(ERROR + e.getMessage(), e);
	}

	private TipoTaquilla getOldTipoTaquilla(TipoTaquillaPK pk) {
		TipoTaquilla oldTipoTaquilla = new TipoTaquilla();
		oldTipoTaquilla.setNombre(pk.getNombre());
		oldTipoTaquilla = listaTiposTaquilla.get(listaTiposTaquilla.indexOf(oldTipoTaquilla));
		return oldTipoTaquilla;
	}
}
