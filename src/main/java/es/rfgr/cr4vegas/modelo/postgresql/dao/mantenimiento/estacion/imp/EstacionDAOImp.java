package es.rfgr.cr4vegas.modelo.postgresql.dao.mantenimiento.estacion.imp;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

import javax.management.OperationsException;

import es.rfgr.cr4vegas.modelo.postgresql.bd.mantenimiento.estacion.EstacionBD;
import es.rfgr.cr4vegas.modelo.postgresql.dao.mantenimiento.estacion.EstacionDAO;
import es.rfgr.cr4vegas.modelo.postgresql.dao.mantenimiento.estacion.ImagenEstacionDAO;
import es.rfgr.cr4vegas.modelo.postgresql.dto.mantenimiento.estacion.Estacion;
import es.rfgr.cr4vegas.modelo.postgresql.dto.mantenimiento.estacion.EstacionPK;
import es.rfgr.cr4vegas.modelo.postgresql.dto.mantenimiento.estacion.ImagenEstacion;
import es.rfgr.cr4vegas.modelo.postgresql.exceptions.DAOException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;

public class EstacionDAOImp implements EstacionDAO {

	private static final String ERROR = "EstacionDAO: ";

	private ObservableList<Estacion> listaEstaciones;

	private EstacionBD estacionBD;

	public EstacionDAOImp() {
		listaEstaciones = FXCollections.observableArrayList();
		estacionBD = new EstacionBD();
	}

	public void setConnection(Connection conn) {
		estacionBD.setConnection(conn);
	}

	public ObservableList<Estacion> getEstaciones() {
		return listaEstaciones;
	}

	public int getNumEstaciones() {
		return listaEstaciones.size();
	}

	public EstacionPK insert(Estacion dto) throws DAOException {
		EstacionPK pk = dto.createPK();

		try {
			if (listaEstaciones.contains(dto)) {
				throw new OperationsException("La estación ya existe.");

			} else {
				pk = estacionBD.insert(dto);

				this.listaEstaciones.add(dto);
			}
		} catch (Exception e) {
			throwException(e);
		}
		return pk;
	}

	public ObservableList<EstacionPK> insertAll(ObservableList<Estacion> listToInsert) throws DAOException {
		ObservableList<EstacionPK> listToReturn = FXCollections.observableArrayList();
		for (Estacion dto : listToInsert) {
			EstacionPK dtoPK = insert(dto);
			if (dtoPK != null) {
				listToReturn.add(dtoPK);
			}
		}
		return listToReturn;
	}

	public void update(EstacionPK pk, Estacion dto) throws DAOException {
		Estacion oldEstacion = getOldEstacion(pk);

		try {
			if (dto == null) {
				throw new OperationsException("La estación a modificar no puede ser nula.");
			}
			if (!listaEstaciones.contains(oldEstacion)) {
				throw new OperationsException("La estación a modificar no existe.");

			} else {
				estacionBD.update(pk, dto);

				listaEstaciones.set(listaEstaciones.indexOf(oldEstacion), dto);
			}
		} catch (Exception e) {
			throwException(e);
		}
	}

	public void updateAll(ObservableMap<EstacionPK, Estacion> mapToUpdate) throws DAOException {
		for (Map.Entry<EstacionPK, Estacion> entry : mapToUpdate.entrySet()) {
			update(entry.getKey(), entry.getValue());
		}
	}

	public void delete(EstacionPK pk) throws DAOException {
		Estacion oldEstacion = getOldEstacion(pk);

		try {
			if (pk == null) {
				throw new NullPointerException("No se puede eliminar una estación nula.");
			}
			if (!listaEstaciones.contains(oldEstacion)) {
				throw new OperationsException("La estación a eliminar no existe.");

			} else {
				estacionBD.delete(pk);

				listaEstaciones.remove(oldEstacion);
			}
		} catch (Exception e) {
			throwException(e);
		}
	}

	public void deleteAll(ObservableList<Estacion> listToDelete) throws DAOException {
		for (Estacion dto : listToDelete) {
			delete(dto.createPK());
		}
	}

	public void findAll() throws DAOException {
		try {
			listaEstaciones.setAll(estacionBD.findAll());

		} catch (SQLException e) {
			throwException(e);
		}
	}

	public Estacion findByPrimaryKey(EstacionPK pk) throws DAOException {
		try {
			return estacionBD.findByPrimaryKey(pk);

		} catch (SQLException e) {
			throwException(e);
		}
		return null;
	}
	
	public void setMaxRows(int maxRows) {
		estacionBD.setMaxRows(maxRows);
	}

	public int getMaxRows() {
		return estacionBD.getMaxRows();
	}

	public void createDependencies(ImagenEstacionDAO imagenEstacionDAO) {
		for (Estacion estacion : listaEstaciones) {
			estacion.setObjImagenEstacion(dependencyEstacionImagenEstacion(estacion, imagenEstacionDAO));
		}
	}

	private ImagenEstacion dependencyEstacionImagenEstacion(Estacion dto, ImagenEstacionDAO imagenEstacionDAO) {
		for (ImagenEstacion imagenEstacion : imagenEstacionDAO.getImagenEstacion()) {
			if (imagenEstacion.getCodImagen() == dto.getCodImagen()) {
				return imagenEstacion;
			}
		}
		return null;
	}

	private void throwException(Exception e) throws DAOException {
		throw new DAOException(ERROR + e.getMessage(), e);
	}

	private Estacion getOldEstacion(EstacionPK pk) {
		Estacion oldEstacion = new Estacion();
		oldEstacion.setCodEstacion(pk.getCodEstacion());
		oldEstacion = listaEstaciones.get(listaEstaciones.indexOf(oldEstacion));
		return oldEstacion;
	}
}
