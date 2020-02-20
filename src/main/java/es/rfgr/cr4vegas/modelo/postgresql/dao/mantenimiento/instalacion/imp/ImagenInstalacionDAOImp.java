package es.rfgr.cr4vegas.modelo.postgresql.dao.mantenimiento.instalacion.imp;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

import javax.management.OperationsException;

import es.rfgr.cr4vegas.modelo.postgresql.bd.mantenimiento.instalacion.ImagenInstalacionBD;
import es.rfgr.cr4vegas.modelo.postgresql.dao.mantenimiento.instalacion.ImagenInstalacionDAO;
import es.rfgr.cr4vegas.modelo.postgresql.dao.mantenimiento.instalacion.InstalacionDAO;
import es.rfgr.cr4vegas.modelo.postgresql.dto.mantenimiento.instalacion.ImagenInstalacion;
import es.rfgr.cr4vegas.modelo.postgresql.dto.mantenimiento.instalacion.ImagenInstalacionPK;
import es.rfgr.cr4vegas.modelo.postgresql.dto.mantenimiento.instalacion.Instalacion;
import es.rfgr.cr4vegas.modelo.postgresql.exceptions.DAOException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;

public class ImagenInstalacionDAOImp implements ImagenInstalacionDAO {

	private static final String ERROR = "ImagenInstalacionDAO: ";

	private ObservableList<ImagenInstalacion> listaImagenInstalacion;

	private ImagenInstalacionBD imagenInstalacionBD;

	public ImagenInstalacionDAOImp() {
		listaImagenInstalacion = FXCollections.observableArrayList();
		imagenInstalacionBD = new ImagenInstalacionBD();
	}

	public void setConnection(Connection conn) {
		imagenInstalacionBD.setConnection(conn);
	}

	public ObservableList<ImagenInstalacion> getImagenInstalacion() {
		return listaImagenInstalacion;
	}

	public int getNumImagenInstalacion() {
		return listaImagenInstalacion.size();
	}

	public ImagenInstalacionPK insert(ImagenInstalacion dto) throws DAOException {
		ImagenInstalacionPK pk = (ImagenInstalacionPK) dto.createPK();

		try {
			if (listaImagenInstalacion.contains(dto)) {
				throw new OperationsException("La imagen de instalación ya existe.");
			} else {
				pk = imagenInstalacionBD.insert(dto);

				this.listaImagenInstalacion.add(dto);
			}
		} catch (Exception e) {
			throwException(e);
		}
		return pk;
	}

	public ObservableList<ImagenInstalacionPK> insertAll(ObservableList<ImagenInstalacion> listToInsert) throws DAOException {
		ObservableList<ImagenInstalacionPK> listToReturn = FXCollections.observableArrayList();
		for (ImagenInstalacion dto : listToInsert) {
			ImagenInstalacionPK dtoPK = insert(dto);
			if (dtoPK != null) {
				listToReturn.add(dtoPK);
			}
		}
		return listToReturn;
	}

	public void update(ImagenInstalacionPK pk, ImagenInstalacion dto) throws DAOException {
		ImagenInstalacion oldImagenInstalacion = getOldImagenInstalacion(pk);

		try {
			if (dto == null) {
				throw new OperationsException("La imagen de instalación a modificar no puede ser nula.");
			} else {
				if (!listaImagenInstalacion.contains(oldImagenInstalacion)) {
					throw new OperationsException("La imagen de instalación a modificar no existe.");

				} else {
					imagenInstalacionBD.update(pk, dto);

					listaImagenInstalacion.set(listaImagenInstalacion.indexOf(oldImagenInstalacion), dto);
				}
			}
		} catch (Exception e) {
			throwException(e);
		}
	}

	public void updateAll(ObservableMap<ImagenInstalacionPK, ImagenInstalacion> mapToUpdate) throws DAOException {
		for (Map.Entry<ImagenInstalacionPK, ImagenInstalacion> entry : mapToUpdate.entrySet()) {
			update(entry.getKey(), entry.getValue());
		}
	}

	public void delete(ImagenInstalacionPK pk) throws DAOException {
		ImagenInstalacion oldImagenInstalacion = getOldImagenInstalacion(pk);

		try {
			if (pk == null) {
				throw new NullPointerException("No se puede eliminar una imagen de instalación nula.");
			}
			if (!listaImagenInstalacion.contains(oldImagenInstalacion)) {
				throw new OperationsException("La imagen de instalación a eliminar no existe.");

			} else {
				imagenInstalacionBD.delete(pk);

				listaImagenInstalacion.remove(oldImagenInstalacion);
			}
		} catch (Exception e) {
			throwException(e);
		}
	}

	public void deleteAll(ObservableList<ImagenInstalacion> listToDelete) throws DAOException {
		for (ImagenInstalacion dto : listToDelete) {
			delete((ImagenInstalacionPK) dto.createPK());
		}
	}

	public void findAll() throws DAOException {
		try {
			listaImagenInstalacion.setAll(imagenInstalacionBD.findAll());

		} catch (SQLException e) {
			throwException(e);
		}
	}

	public ImagenInstalacion findByPrimaryKey(ImagenInstalacionPK pk) throws DAOException {
		try {
			return imagenInstalacionBD.findByPrimaryKey(pk);

		} catch (SQLException e) {
			throwException(e);
		}
		return null;
	}
	
	
	public void setMaxRows(int maxRows) {
		imagenInstalacionBD.setMaxRows(maxRows);
	}

	public int getMaxRows() {
		return imagenInstalacionBD.getMaxRows();
	}

	public ObservableList<ImagenInstalacion> getInstalacionImagenes(Instalacion dto) {
		ObservableList<ImagenInstalacion> listToReturn = FXCollections.observableArrayList();
		for (ImagenInstalacion ii : listaImagenInstalacion) {
			if (ii.getCodInstalacion() == dto.getCodInstalacion()) {
				listToReturn.add(ii);
			}
		}
		return listToReturn;
	}

	public void createDependencies(InstalacionDAO instalacionDAO) {
		for (ImagenInstalacion imagenInstalacion : listaImagenInstalacion) {
			imagenInstalacion
					.setObjInstalacion(dependencyImagenInstalacionInstalacion(imagenInstalacion, instalacionDAO));
		}
	}

	private Instalacion dependencyImagenInstalacionInstalacion(ImagenInstalacion dto, InstalacionDAO instalacionDAO) {
		for (Instalacion instalacion : instalacionDAO.getInstalaciones()) {
			if (instalacion.getCodInstalacion() == dto.getCodInstalacion()) {
				return instalacion;
			}
		}
		return null;
	}

	private void throwException(Exception e) throws DAOException {
		throw new DAOException(ERROR + e.getMessage(), e);
	}

	private ImagenInstalacion getOldImagenInstalacion(ImagenInstalacionPK pk) {
		ImagenInstalacion oldImagenInstalacion = new ImagenInstalacion();
		oldImagenInstalacion.setCodImagen(pk.getCodImagen());
		oldImagenInstalacion = listaImagenInstalacion.get(listaImagenInstalacion.indexOf(oldImagenInstalacion));
		return oldImagenInstalacion;
	}
}
