package es.rfgr.cr4vegas.modelo.postgresql.dao.almacen.material.imp;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

import javax.management.OperationsException;

import es.rfgr.cr4vegas.modelo.postgresql.bd.almacen.material.UbicacionBD;
import es.rfgr.cr4vegas.modelo.postgresql.dao.almacen.material.UbicacionDAO;
import es.rfgr.cr4vegas.modelo.postgresql.dto.almacen.material.Ubicacion;
import es.rfgr.cr4vegas.modelo.postgresql.dto.almacen.material.UbicacionPK;
import es.rfgr.cr4vegas.modelo.postgresql.exceptions.DAOException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;

public class UbicacionDAOImp implements UbicacionDAO {

	private static final String ERROR = "UbicacionDAO: ";

	private ObservableList<Ubicacion> listaUbicaciones;

	private UbicacionBD ubicacionBD;

	public UbicacionDAOImp() {
		listaUbicaciones = FXCollections.observableArrayList();
		ubicacionBD = new UbicacionBD();
	}

	public void setConnection(Connection conn) {
		ubicacionBD.setConnection(conn);
	}

	public ObservableList<Ubicacion> getUbicaciones() {
		return listaUbicaciones;
	}

	public int getNumUbicaciones() {
		return listaUbicaciones.size();
	}

	public UbicacionPK insert(Ubicacion dto) throws DAOException {
		UbicacionPK pk = dto.createPK();

		try {
			if (listaUbicaciones.contains(dto)) {
				throw new OperationsException("La ubicación ya existe.");

			} else {
				pk = ubicacionBD.insert(dto);

				this.listaUbicaciones.add(dto);
			}
		} catch (Exception e) {
			throwException(e);
		}
		return pk;
	}
	
	public ObservableList<UbicacionPK> insertAll(ObservableList<Ubicacion> listToInsert) throws DAOException {
		ObservableList<UbicacionPK> listToReturn = FXCollections.observableArrayList();
		for (Ubicacion dto : listToInsert) {
			UbicacionPK dtoPK = insert(dto);
			if (dtoPK != null) {
				listToReturn.add(dtoPK);
			}
		}
		return listToReturn;
	}

	public void update(UbicacionPK pk, Ubicacion dto) throws DAOException {
		Ubicacion oldUbicacion = getOldUbicacion(pk);

		try {
			if (dto == null) {
				throw new OperationsException("La ubicación a modificar no puede ser nula.");
			} else {
				if (!listaUbicaciones.contains(oldUbicacion)) {
					throw new OperationsException("La ubicación a modificar no existe.");

				} else {
					ubicacionBD.update(pk, dto);

					oldUbicacion.copiarValores(dto);
				}
			}
		} catch (Exception e) {
			throwException(e);

		}
	}
	
	public void updateAll(ObservableMap<UbicacionPK, Ubicacion> mapToUpdate) throws DAOException {
		for (Map.Entry<UbicacionPK, Ubicacion> entry : mapToUpdate.entrySet()) {
			update(entry.getKey(), entry.getValue());
		}
	}

	public void delete(UbicacionPK pk) throws DAOException {
		Ubicacion oldUbicacion = getOldUbicacion(pk);

		try {
			if (pk == null) {
				throw new NullPointerException("No se puede eliminar una ubicación nula.");
			}
			if (!listaUbicaciones.contains(oldUbicacion)) {
				throw new OperationsException("La ubicación a eliminar no existe.");
			
			} else {
				ubicacionBD.delete(pk);

				listaUbicaciones.remove(oldUbicacion);
			}
		} catch (Exception e) {
			throwException(e);

		}
	}
	
	public void deleteAll(ObservableList<Ubicacion> listToDelete) throws DAOException {
		for (Ubicacion dto : listToDelete) {
			delete(dto.createPK());
		}
	}

	public void findAll() throws DAOException {
		try {
			listaUbicaciones.setAll(ubicacionBD.findAll());

		} catch (SQLException e) {
			throwException(e);
		}
	}

	public Ubicacion findByPrimaryKey(UbicacionPK pk) throws DAOException {
		try {
			return ubicacionBD.findByPrimaryKey(pk);

		} catch (SQLException e) {
			throwException(e);
		}
		return null;
	}

	public void setMaxRows(int maxRows) {
		ubicacionBD.setMaxRows(maxRows);
	}

	public int getMaxRows() {
		return ubicacionBD.getMaxRows();
	}

	private void throwException(Exception e) throws DAOException {
		throw new DAOException(ERROR + e.getMessage(), e);
	}

	private Ubicacion getOldUbicacion(UbicacionPK pk) {
		Ubicacion oldUbicacion = new Ubicacion();
		oldUbicacion.setNombre(pk.getNombre());
		oldUbicacion = listaUbicaciones.get(listaUbicaciones.indexOf(oldUbicacion));
		return oldUbicacion;
	}
}
