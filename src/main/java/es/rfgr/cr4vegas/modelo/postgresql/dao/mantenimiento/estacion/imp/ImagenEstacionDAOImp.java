package es.rfgr.cr4vegas.modelo.postgresql.dao.mantenimiento.estacion.imp;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

import javax.management.OperationsException;

import es.rfgr.cr4vegas.modelo.postgresql.bd.mantenimiento.estacion.ImagenEstacionBD;
import es.rfgr.cr4vegas.modelo.postgresql.dao.mantenimiento.estacion.EstacionDAO;
import es.rfgr.cr4vegas.modelo.postgresql.dao.mantenimiento.estacion.ImagenEstacionDAO;
import es.rfgr.cr4vegas.modelo.postgresql.dto.mantenimiento.estacion.Estacion;
import es.rfgr.cr4vegas.modelo.postgresql.dto.mantenimiento.estacion.ImagenEstacion;
import es.rfgr.cr4vegas.modelo.postgresql.dto.mantenimiento.estacion.ImagenEstacionPK;
import es.rfgr.cr4vegas.modelo.postgresql.exceptions.DAOException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;

public class ImagenEstacionDAOImp implements ImagenEstacionDAO {

	private static final String ERROR = "ImagenEstacionDAO: ";

	private ObservableList<ImagenEstacion> listaImagenEstacion;

	private ImagenEstacionBD imagenEstacionBD;

	public ImagenEstacionDAOImp() {
		listaImagenEstacion = FXCollections.observableArrayList();
		imagenEstacionBD = new ImagenEstacionBD();
	}

	public void setConnection(Connection conn) {
		imagenEstacionBD.setConnection(conn);
	}

	public ObservableList<ImagenEstacion> getImagenEstacion() {
		return listaImagenEstacion;
	}

	public int getNumImagenEstacion() {
		return listaImagenEstacion.size();
	}

	public ImagenEstacionPK insert(ImagenEstacion dto) throws DAOException {
		ImagenEstacionPK pk = (ImagenEstacionPK) dto.createPK();

		try {
			if (listaImagenEstacion.contains(dto)) {
				throw new OperationsException("La imagen de estación ya existe.");

			} else {
				pk = imagenEstacionBD.insert(dto);

				this.listaImagenEstacion.add(dto);
			}
		} catch (Exception e) {
			throwException(e);
		}
		return pk;
	}

	public ObservableList<ImagenEstacionPK> insertAll(ObservableList<ImagenEstacion> listToInsert) throws DAOException {
		ObservableList<ImagenEstacionPK> listToReturn = FXCollections.observableArrayList();
		for (ImagenEstacion dto : listToInsert) {
			ImagenEstacionPK dtoPK = insert(dto);
			if (dtoPK != null) {
				listToReturn.add(dtoPK);
			}
		}
		return listToReturn;
	}

	public void update(ImagenEstacionPK pk, ImagenEstacion dto) throws DAOException {
		ImagenEstacion oldImagenEstacion = getOldImagenEstacion(pk);

		try {
			if (dto == null) {
				throw new OperationsException("La imagen de estación a modificar no puede ser nula.");
			}
			if (!listaImagenEstacion.contains(oldImagenEstacion)) {
				throw new OperationsException("La imagen de estación a modificar no existe.");

			} else {
				imagenEstacionBD.update(pk, dto);

				listaImagenEstacion.set(listaImagenEstacion.indexOf(oldImagenEstacion), dto);
			}
		} catch (Exception e) {
			throwException(e);
		}
	}

	public void updateAll(ObservableMap<ImagenEstacionPK, ImagenEstacion> mapToUpdate) throws DAOException {
		for (Map.Entry<ImagenEstacionPK, ImagenEstacion> entry : mapToUpdate.entrySet()) {
			update(entry.getKey(), entry.getValue());
		}
	}

	public void delete(ImagenEstacionPK pk) throws DAOException {
		ImagenEstacion oldImagenEstacion = getOldImagenEstacion(pk);

		try {
			if (pk == null) {
				throw new NullPointerException("No se puede eliminar una imagen de estación nula.");
			}
			if (!listaImagenEstacion.contains(oldImagenEstacion)) {
				throw new OperationsException("La imagen de estación a eliminar no existe.");

			} else {
				imagenEstacionBD.delete(pk);

				listaImagenEstacion.remove(oldImagenEstacion);
			}
		} catch (Exception e) {
			throwException(e);
		}
	}

	public void deleteAll(ObservableList<ImagenEstacion> listToDelete) throws DAOException {
		for (ImagenEstacion dto : listToDelete) {
			delete((ImagenEstacionPK) dto.createPK());
		}
	}

	public void findAll() throws DAOException {
		try {
			listaImagenEstacion.setAll(imagenEstacionBD.findAll());
		} catch (Exception e) {
			throwException(e);
		}
	}

	public ImagenEstacion findByPrimaryKey(ImagenEstacionPK pk) throws DAOException {
		try {
			return imagenEstacionBD.findByPrimaryKey(pk);

		} catch (SQLException e) {
			throwException(e);
		}
		return null;
	}

	public void setMaxRows(int maxRows) {
		imagenEstacionBD.setMaxRows(maxRows);
	}

	public int getMaxRows() {
		return imagenEstacionBD.getMaxRows();
	}

	public ObservableList<ImagenEstacion> getEstacionImagenes(Estacion dto) {
		ObservableList<ImagenEstacion> listaSalida = FXCollections.observableArrayList();
		for (ImagenEstacion ie : listaImagenEstacion) {
			if (ie.getCodEstacion() == dto.getCodEstacion()) {
				listaSalida.add(ie);
			}
		}
		return listaSalida;
	}

	public void createDependencies(EstacionDAO estacionDAO) {
		for (ImagenEstacion imagenEstacion : listaImagenEstacion) {
			imagenEstacion.setObjEstacion(dependencyImagenEstacionEstacion(imagenEstacion, estacionDAO));
		}
	}

	private Estacion dependencyImagenEstacionEstacion(ImagenEstacion dto, EstacionDAO estacionDAO) {
		for (Estacion estacion : estacionDAO.getEstaciones()) {
			if (estacion.getCodEstacion() == dto.getCodEstacion()) {
				return estacion;
			}
		}
		return null;
	}

	private void throwException(Exception e) throws DAOException {
		throw new DAOException(ERROR + e.getMessage(), e);
	}

	private ImagenEstacion getOldImagenEstacion(ImagenEstacionPK pk) {
		ImagenEstacion oldImagenEstacion = new ImagenEstacion();
		oldImagenEstacion.setCodImagen(pk.getCodImagen());
		oldImagenEstacion = listaImagenEstacion.get(listaImagenEstacion.indexOf(oldImagenEstacion));
		return oldImagenEstacion;
	}
}
