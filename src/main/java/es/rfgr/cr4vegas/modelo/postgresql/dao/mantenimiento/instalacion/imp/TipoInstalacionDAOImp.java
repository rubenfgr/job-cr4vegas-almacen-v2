package es.rfgr.cr4vegas.modelo.postgresql.dao.mantenimiento.instalacion.imp;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

import javax.management.OperationsException;

import es.rfgr.cr4vegas.modelo.postgresql.bd.mantenimiento.instalacion.TipoInstalacionBD;
import es.rfgr.cr4vegas.modelo.postgresql.dao.mantenimiento.instalacion.TipoInstalacionDAO;
import es.rfgr.cr4vegas.modelo.postgresql.dto.mantenimiento.instalacion.TipoInstalacion;
import es.rfgr.cr4vegas.modelo.postgresql.dto.mantenimiento.instalacion.TipoInstalacionPK;
import es.rfgr.cr4vegas.modelo.postgresql.exceptions.DAOException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;

public class TipoInstalacionDAOImp implements TipoInstalacionDAO {

	private static final String ERROR = "TipoInstalacionDAO: ";

	private ObservableList<TipoInstalacion> listaTiposInstalacion;

	private TipoInstalacionBD tipoInstalacionBD;

	public TipoInstalacionDAOImp() {
		listaTiposInstalacion = FXCollections.observableArrayList();
		tipoInstalacionBD = new TipoInstalacionBD();
	}

	public void setConnection(Connection conn) {
		tipoInstalacionBD.setConnection(conn);
	}

	public ObservableList<TipoInstalacion> getTiposInstalacion() {
		return listaTiposInstalacion;
	}

	public int getNumTiposInstalacion() {
		return listaTiposInstalacion.size();
	}

	public TipoInstalacionPK insert(TipoInstalacion dto) throws DAOException {
		TipoInstalacionPK pk = dto.createPK();

		try {
			if (listaTiposInstalacion.contains(dto)) {
				throw new OperationsException("El tipo de instalación ya existe.");
			} else {
				pk = tipoInstalacionBD.insert(dto);

				this.listaTiposInstalacion.add(dto);
			}
		} catch (Exception e) {
			throwException(e);
		}
		return pk;
	}
	
	public ObservableList<TipoInstalacionPK> insertAll(ObservableList<TipoInstalacion> listToInsert) throws DAOException {
		ObservableList<TipoInstalacionPK> listToReturn = FXCollections.observableArrayList();
		for (TipoInstalacion dto : listToInsert) {
			TipoInstalacionPK dtoPK = insert(dto);
			if (dtoPK != null) {
				listToReturn.add(dtoPK);
			}
		}
		return listToReturn;
	}

	public void update(TipoInstalacionPK pk, TipoInstalacion dto) throws DAOException {
		TipoInstalacion oldTipoInstalacion = getOldTipoInstalacion(pk);

		try {
			if (dto == null) {
				throw new OperationsException("El tipo de instalación a modificar no puede ser nulo.");
			} else {
				if (!listaTiposInstalacion.contains(oldTipoInstalacion)) {
					throw new OperationsException("El tipo de instalación a modificar no existe.");

				} else {
					tipoInstalacionBD.update(pk, dto);

					listaTiposInstalacion.set(listaTiposInstalacion.indexOf(oldTipoInstalacion), dto);
				}
			}
		} catch (Exception e) {
			throwException(e);
		}
	}
	
	public void updateAll(ObservableMap<TipoInstalacionPK, TipoInstalacion> mapToUpdate) throws DAOException {
		for (Map.Entry<TipoInstalacionPK, TipoInstalacion> entry : mapToUpdate.entrySet()) {
			update(entry.getKey(), entry.getValue());
		}
	}

	public void delete(TipoInstalacionPK pk) throws DAOException {
		TipoInstalacion oldTipoInstalacion = getOldTipoInstalacion(pk);

		try {
			if (pk == null) {
				throw new NullPointerException("No se puede eliminar un tipo de instalación nulo.");
			}
			if (!listaTiposInstalacion.contains(oldTipoInstalacion)) {
				throw new OperationsException("El tipo de instalación a eliminar no existe.");

			} else {
				tipoInstalacionBD.delete(pk);

				listaTiposInstalacion.remove(oldTipoInstalacion);
			}
		} catch (Exception e) {
			throwException(e);
		}
	}
	
	public void deleteAll(ObservableList<TipoInstalacion> listToDelete) throws DAOException {
		for (TipoInstalacion dto : listToDelete) {
			delete(dto.createPK());
		}
	}

	public void findAll() throws DAOException {
		try {
			listaTiposInstalacion.setAll(tipoInstalacionBD.findAll());

		} catch (SQLException e) {
			throwException(e);
		}
	}

	public TipoInstalacion findByPrimaryKey(TipoInstalacionPK pk) throws DAOException {
		try {
			return tipoInstalacionBD.findByPrimaryKey(pk);

		} catch (SQLException e) {
			throwException(e);
		}
		return null;
	}
	
	public void setMaxRows(int maxRows) {
		tipoInstalacionBD.setMaxRows(maxRows);
	}

	public int getMaxRows() {
		return tipoInstalacionBD.getMaxRows();
	}

	private void throwException(Exception e) throws DAOException {
		throw new DAOException(ERROR + e.getMessage(), e);
	}

	private TipoInstalacion getOldTipoInstalacion(TipoInstalacionPK pk) {
		TipoInstalacion oldTipoInstalacion = new TipoInstalacion();
		oldTipoInstalacion.setNombre(pk.getNombre());
		oldTipoInstalacion = listaTiposInstalacion.get(listaTiposInstalacion.indexOf(oldTipoInstalacion));
		return oldTipoInstalacion;
	}
}
