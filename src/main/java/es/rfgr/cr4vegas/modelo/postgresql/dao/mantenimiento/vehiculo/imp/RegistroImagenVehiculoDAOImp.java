package es.rfgr.cr4vegas.modelo.postgresql.dao.mantenimiento.vehiculo.imp;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

import javax.management.OperationsException;

import es.rfgr.cr4vegas.modelo.postgresql.bd.mantenimiento.vehiculo.RegistroImagenVehiculoBD;
import es.rfgr.cr4vegas.modelo.postgresql.dao.mantenimiento.vehiculo.ImagenVehiculoDAO;
import es.rfgr.cr4vegas.modelo.postgresql.dao.mantenimiento.vehiculo.RegistroImagenVehiculoDAO;
import es.rfgr.cr4vegas.modelo.postgresql.dao.mantenimiento.vehiculo.RegistroVehiculoDAO;
import es.rfgr.cr4vegas.modelo.postgresql.dto.mantenimiento.vehiculo.ImagenVehiculo;
import es.rfgr.cr4vegas.modelo.postgresql.dto.mantenimiento.vehiculo.RegistroImagenVehiculo;
import es.rfgr.cr4vegas.modelo.postgresql.dto.mantenimiento.vehiculo.RegistroImagenVehiculoPK;
import es.rfgr.cr4vegas.modelo.postgresql.dto.mantenimiento.vehiculo.RegistroVehiculo;
import es.rfgr.cr4vegas.modelo.postgresql.exceptions.DAOException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;

public class RegistroImagenVehiculoDAOImp implements RegistroImagenVehiculoDAO {

	private static final String ERROR = "RegistroImagenVehiculoDAO: ";

	private ObservableList<RegistroImagenVehiculo> listaRegistroImagenVehiculo;

	private RegistroImagenVehiculoBD registroImagenVehiculoBD;

	public RegistroImagenVehiculoDAOImp() {
		listaRegistroImagenVehiculo = FXCollections.observableArrayList();
		registroImagenVehiculoBD = new RegistroImagenVehiculoBD();
	}

	public void setConnection(Connection conn) {
		registroImagenVehiculoBD.setConnection(conn);
	}

	public ObservableList<RegistroImagenVehiculo> getRegistrosImagenesVehiculos() {
		return listaRegistroImagenVehiculo;
	}

	public int getNumRegistrosImagenesVehiculos() {
		return listaRegistroImagenVehiculo.size();
	}

	public RegistroImagenVehiculoPK insert(RegistroImagenVehiculo dto) throws DAOException {
		RegistroImagenVehiculoPK pk = dto.createPK();

		try {
			if (listaRegistroImagenVehiculo.contains(dto)) {
				throw new OperationsException("El registro-imagen-vehículo ya existe.");
			} else {
				pk = registroImagenVehiculoBD.insert(dto);

				this.listaRegistroImagenVehiculo.add(dto);
			}
		} catch (Exception e) {
			throwException(e);
		}
		return pk;
	}

	public ObservableList<RegistroImagenVehiculoPK> insertAll(ObservableList<RegistroImagenVehiculo> listToInsert) throws DAOException {
		ObservableList<RegistroImagenVehiculoPK> listToReturn = FXCollections.observableArrayList();
		for (RegistroImagenVehiculo dto : listToInsert) {
			RegistroImagenVehiculoPK dtoPK = insert(dto);
			if (dtoPK != null) {
				listToReturn.add(dtoPK);
			}
		}
		return listToReturn;
	}

	public void update(RegistroImagenVehiculoPK pk, RegistroImagenVehiculo dto) throws DAOException {
		RegistroImagenVehiculo oldRegistroImagenVehiculo = getOldRegistroImagenVehiculo(pk);

		try {
			if (dto == null) {
				throw new OperationsException("El registro-imagen-vehículo a modificar no puede ser nulo.");
			} else {
				if (!listaRegistroImagenVehiculo.contains(oldRegistroImagenVehiculo)) {
					throw new OperationsException("El registro-imagen-vehículo a modificar no existe.");

				} else {
					registroImagenVehiculoBD.update(pk, dto);

					listaRegistroImagenVehiculo.set(listaRegistroImagenVehiculo.indexOf(oldRegistroImagenVehiculo),
							dto);
				}
			}
		} catch (Exception e) {
			throwException(e);
		}
	}

	public void updateAll(ObservableMap<RegistroImagenVehiculoPK, RegistroImagenVehiculo> mapToUpdate)
			throws DAOException {
		for (Map.Entry<RegistroImagenVehiculoPK, RegistroImagenVehiculo> entry : mapToUpdate.entrySet()) {
			update(entry.getKey(), entry.getValue());
		}
	}

	public void delete(RegistroImagenVehiculoPK pk) throws DAOException {
		RegistroImagenVehiculo oldRegistroImagenVehiculo = getOldRegistroImagenVehiculo(pk);

		try {
			if (pk == null) {
				throw new NullPointerException("No se puede eliminar un registro-imagen-vehículo nulo.");
			}
			if (!listaRegistroImagenVehiculo.contains(oldRegistroImagenVehiculo)) {
				throw new OperationsException("El registro-imagen-vehículo a eliminar no existe.");

			} else {
				registroImagenVehiculoBD.delete(pk);

				listaRegistroImagenVehiculo.remove(oldRegistroImagenVehiculo);
			}
		} catch (Exception e) {
			throwException(e);
		}
	}

	public void deleteAll(ObservableList<RegistroImagenVehiculo> listToDelete) throws DAOException {
		for (RegistroImagenVehiculo dto : listToDelete) {
			delete(dto.createPK());
		}
	}

	public void findAll() throws DAOException {
		try {
			listaRegistroImagenVehiculo.setAll(registroImagenVehiculoBD.findAll());

		} catch (SQLException e) {
			throwException(e);
		}
	}

	public RegistroImagenVehiculo findByPrimaryKey(RegistroImagenVehiculoPK pk) throws DAOException {
		try {
			return registroImagenVehiculoBD.findByPrimaryKey(pk);

		} catch (SQLException e) {
			throwException(e);
		}
		return null;
	}
	
	public void setMaxRows(int maxRows) {
		registroImagenVehiculoBD.setMaxRows(maxRows);
	}

	public int getMaxRows() {
		return registroImagenVehiculoBD.getMaxRows();
	}

	public ObservableList<RegistroImagenVehiculo> getRegistroVehiculoImagenes(RegistroVehiculo dto) {
		ObservableList<RegistroImagenVehiculo> listToReturn = FXCollections.observableArrayList();
		for (RegistroImagenVehiculo dtoOnList : listaRegistroImagenVehiculo) {
			if (dtoOnList.getCodRegistroVehiculo() == dto.getCodRegistro()) {
				listToReturn.add(dtoOnList);
			}
		}
		return listToReturn;
	}

	public void createDependencies(RegistroVehiculoDAO registroVehiculoDAO, ImagenVehiculoDAO imagenVehiculoDAO) {
		for (RegistroImagenVehiculo riv : listaRegistroImagenVehiculo) {
			riv.setObjRegistroVehiculo(dependencyRegistroImagenVehiculoRegistroVehiculo(riv, registroVehiculoDAO));
			riv.setObjImagenVehiculo(dependencyRegistroImagenVehiculoImagen(riv, imagenVehiculoDAO));
		}
	}

	private RegistroVehiculo dependencyRegistroImagenVehiculoRegistroVehiculo(RegistroImagenVehiculo dto,
			RegistroVehiculoDAO registroVehiculoDAO) {
		for (RegistroVehiculo registroVehiculo : registroVehiculoDAO.getRegistrosVehiculo()) {
			if (registroVehiculo.getCodRegistro() == dto.getCodRegistroVehiculo()) {
				return registroVehiculo;
			}
		}
		return null;
	}

	private ImagenVehiculo dependencyRegistroImagenVehiculoImagen(RegistroImagenVehiculo dto,
			ImagenVehiculoDAO imagenVehiculoDAO) {
		for (ImagenVehiculo imagenVehiculo : imagenVehiculoDAO.getImagenesVehiculo()) {
			if (imagenVehiculo.getCodImagen() == dto.getCodImagenVehiculo()) {
				return imagenVehiculo;
			}
		}
		return null;
	}

	private void throwException(Exception e) throws DAOException {
		throw new DAOException(ERROR + e.getMessage(), e);
	}

	private RegistroImagenVehiculo getOldRegistroImagenVehiculo(RegistroImagenVehiculoPK pk) {
		RegistroImagenVehiculo oldRegistroImagenVehiculo = new RegistroImagenVehiculo();
		oldRegistroImagenVehiculo.setCodRegistroVehiculo(pk.getCodRegistro());
		oldRegistroImagenVehiculo.setCodImagenVehiculo(pk.getCodImagen());
		oldRegistroImagenVehiculo = listaRegistroImagenVehiculo
				.get(listaRegistroImagenVehiculo.indexOf(oldRegistroImagenVehiculo));
		return oldRegistroImagenVehiculo;
	}
}
