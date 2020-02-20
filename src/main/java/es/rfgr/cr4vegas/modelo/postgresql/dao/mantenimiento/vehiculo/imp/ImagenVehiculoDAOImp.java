package es.rfgr.cr4vegas.modelo.postgresql.dao.mantenimiento.vehiculo.imp;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

import javax.management.OperationsException;

import es.rfgr.cr4vegas.modelo.postgresql.bd.mantenimiento.vehiculo.ImagenVehiculoBD;
import es.rfgr.cr4vegas.modelo.postgresql.dao.mantenimiento.vehiculo.ImagenVehiculoDAO;
import es.rfgr.cr4vegas.modelo.postgresql.dao.mantenimiento.vehiculo.VehiculoDAO;
import es.rfgr.cr4vegas.modelo.postgresql.dto.mantenimiento.vehiculo.ImagenVehiculo;
import es.rfgr.cr4vegas.modelo.postgresql.dto.mantenimiento.vehiculo.ImagenVehiculoPK;
import es.rfgr.cr4vegas.modelo.postgresql.dto.mantenimiento.vehiculo.Vehiculo;
import es.rfgr.cr4vegas.modelo.postgresql.exceptions.DAOException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;

public class ImagenVehiculoDAOImp implements ImagenVehiculoDAO {

	private static final String ERROR = "ImagenVehiculoDAO: ";

	private ObservableList<ImagenVehiculo> listaImagenesVehiculo;

	private ImagenVehiculoBD imagenVehiculoBD;

	public ImagenVehiculoDAOImp() {
		listaImagenesVehiculo = FXCollections.observableArrayList();
		imagenVehiculoBD = new ImagenVehiculoBD();
	}

	public void setConnection(Connection conn) {
		imagenVehiculoBD.setConnection(conn);
	}

	public ObservableList<ImagenVehiculo> getImagenesVehiculo() {
		return listaImagenesVehiculo;
	}

	public int getNumImagenesVehiculo() {
		return listaImagenesVehiculo.size();
	}

	public ImagenVehiculoPK insert(ImagenVehiculo dto) throws DAOException {
		ImagenVehiculoPK pk = (ImagenVehiculoPK) dto.createPK();

		try {
			if (listaImagenesVehiculo.contains(dto)) {
				throw new OperationsException("La imagen de vehículo ya existe.");

			} else {
				pk = imagenVehiculoBD.insert(dto);

				this.listaImagenesVehiculo.add(dto);
			}
		} catch (Exception e) {
			throwException(e);
		}
		return pk;
	}

	public ObservableList<ImagenVehiculoPK> insertAll(ObservableList<ImagenVehiculo> listToInsert) throws DAOException {
		ObservableList<ImagenVehiculoPK> listToReturn = FXCollections.observableArrayList();
		for (ImagenVehiculo dto : listToInsert) {
			ImagenVehiculoPK dtoPK = insert(dto);
			if (dtoPK != null) {
				listToReturn.add(dtoPK);
			}
		}
		return listToReturn;
	}

	public void update(ImagenVehiculoPK pk, ImagenVehiculo dto) throws DAOException {
		ImagenVehiculo oldImagenVehiculo = getOldImagenVehiculo(pk);

		try {
			if (dto == null) {
				throw new OperationsException("La imagen de vehículo a modificar no puede ser nula.");
			} else {
				if (!listaImagenesVehiculo.contains(oldImagenVehiculo)) {
					throw new OperationsException("La imagen de vehículo a modificar no existe.");

				} else {
					imagenVehiculoBD.update(pk, dto);

					listaImagenesVehiculo.set(listaImagenesVehiculo.indexOf(oldImagenVehiculo), dto);
				}
			}
		} catch (Exception e) {
			throwException(e);
		}
	}

	public void updateAll(ObservableMap<ImagenVehiculoPK, ImagenVehiculo> mapToUpdate) throws DAOException {
		for (Map.Entry<ImagenVehiculoPK, ImagenVehiculo> entry : mapToUpdate.entrySet()) {
			update(entry.getKey(), entry.getValue());
		}
	}

	public void delete(ImagenVehiculoPK pk) throws DAOException {
		ImagenVehiculo oldImagenVehiculo = getOldImagenVehiculo(pk);

		try {
			if (pk == null) {
				throw new NullPointerException("No se puede eliminar una imagen de vehículo nula.");
			}
			if (!listaImagenesVehiculo.contains(oldImagenVehiculo)) {
				throw new OperationsException("La imagen de vehículo a eliminar no existe.");

			} else {
				imagenVehiculoBD.delete(pk);

				listaImagenesVehiculo.remove(oldImagenVehiculo);
			}
		} catch (Exception e) {
			throwException(e);
		}
	}

	public void deleteAll(ObservableList<ImagenVehiculo> listToDelete) throws DAOException {
		for (ImagenVehiculo dto : listToDelete) {
			delete((ImagenVehiculoPK) dto.createPK());
		}
	}

	public void findAll() throws DAOException {
		try {
			listaImagenesVehiculo.setAll(imagenVehiculoBD.findAll());

		} catch (SQLException e) {
			throwException(e);
		}
	}

	public ImagenVehiculo findByPrimaryKey(ImagenVehiculoPK pk) throws DAOException {
		try {
			return imagenVehiculoBD.findByPrimaryKey(pk);

		} catch (SQLException e) {
			throwException(e);
		}
		return null;
	}

	public void setMaxRows(int maxRows) {
		imagenVehiculoBD.setMaxRows(maxRows);
	}

	public int getMaxRows() {
		return imagenVehiculoBD.getMaxRows();
	}

	public ObservableList<ImagenVehiculo> getVehiculoImagenes(Vehiculo dto) {
		ObservableList<ImagenVehiculo> listToReturn = FXCollections.observableArrayList();
		for (ImagenVehiculo dtoOnList : listaImagenesVehiculo) {
			if (dtoOnList.getMatricula() == dto.getMatricula()) {
				listToReturn.add(dtoOnList);
			}
		}
		return listToReturn;
	}

	public void createDependencies(VehiculoDAO vehiculoDAO) {
		for (ImagenVehiculo imagenVehiculo : listaImagenesVehiculo) {
			imagenVehiculo.setObjVehiculo(dependencyImagenVehiculoVehiculo(imagenVehiculo, vehiculoDAO));
		}
	}

	private Vehiculo dependencyImagenVehiculoVehiculo(ImagenVehiculo dto, VehiculoDAO vehiculoDAO) {
		for (Vehiculo vehiculo : vehiculoDAO.getVehiculos()) {
			if (vehiculo.getMatricula().equals(dto.getMatricula())) {
				return vehiculo;
			}
		}
		return null;
	}

	private void throwException(Exception e) throws DAOException {
		throw new DAOException(ERROR + e.getMessage(), e);
	}

	private ImagenVehiculo getOldImagenVehiculo(ImagenVehiculoPK pk) {
		ImagenVehiculo oldImagenVehiculo = new ImagenVehiculo();
		oldImagenVehiculo.setCodImagen(pk.getCodImagen());
		oldImagenVehiculo = listaImagenesVehiculo.get(listaImagenesVehiculo.indexOf(oldImagenVehiculo));
		return oldImagenVehiculo;
	}
}
