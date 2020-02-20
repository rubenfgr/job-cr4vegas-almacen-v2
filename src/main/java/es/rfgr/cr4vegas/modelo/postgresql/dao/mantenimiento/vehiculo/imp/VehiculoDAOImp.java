package es.rfgr.cr4vegas.modelo.postgresql.dao.mantenimiento.vehiculo.imp;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

import javax.management.OperationsException;

import es.rfgr.cr4vegas.modelo.postgresql.bd.mantenimiento.vehiculo.VehiculoBD;
import es.rfgr.cr4vegas.modelo.postgresql.dao.mantenimiento.vehiculo.ImagenVehiculoDAO;
import es.rfgr.cr4vegas.modelo.postgresql.dao.mantenimiento.vehiculo.VehiculoDAO;
import es.rfgr.cr4vegas.modelo.postgresql.dto.mantenimiento.vehiculo.ImagenVehiculo;
import es.rfgr.cr4vegas.modelo.postgresql.dto.mantenimiento.vehiculo.Vehiculo;
import es.rfgr.cr4vegas.modelo.postgresql.dto.mantenimiento.vehiculo.VehiculoPK;
import es.rfgr.cr4vegas.modelo.postgresql.exceptions.DAOException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;

public class VehiculoDAOImp implements VehiculoDAO {

	private static final String ERROR = "VehiculoDAO: ";

	private ObservableList<Vehiculo> listaVehiculos;

	private VehiculoBD vehiculoBD;
	
	public VehiculoDAOImp() {
		listaVehiculos = FXCollections.observableArrayList();
		vehiculoBD = new VehiculoBD();
	}

	public void setConnection(Connection conn) {
		vehiculoBD.setConnection(conn);
	}

	public ObservableList<Vehiculo> getVehiculos() {
		return listaVehiculos;
	}

	public int getNumVehiculos() {
		return listaVehiculos.size();
	}

	public VehiculoPK insert(Vehiculo dto) throws DAOException {
		VehiculoPK pk = dto.createPK();
		
		try {
			if (listaVehiculos.contains(dto)) {
				throw new OperationsException("El vehículo ya existe.");
			} else {
				pk = vehiculoBD.insert(dto);
				
				this.listaVehiculos.add(dto);
			}
		} catch (Exception e) {
			throwException(e);
		}
		return pk;
	}
	
	public ObservableList<VehiculoPK> insertAll(ObservableList<Vehiculo> listToInsert) throws DAOException {
		ObservableList<VehiculoPK> listToReturn = FXCollections.observableArrayList();
		for (Vehiculo dto : listToInsert) {
			VehiculoPK dtoPK = insert(dto);
			if (dtoPK != null) {
				listToReturn.add(dtoPK);
			}
		}
		return listToReturn;
	}

	public void update(VehiculoPK pk, Vehiculo dto) throws DAOException {
		Vehiculo oldVehiculo = getOldVehiculo(pk);

		try {
			if (dto == null) {
				throw new OperationsException("La vehículo a modificar no puede ser nula.");
			} else {
				if (!listaVehiculos.contains(oldVehiculo)) {
					throw new OperationsException("El vehículo a modificar no existe.");
					
				} else {
					vehiculoBD.update(pk, dto);
					
					listaVehiculos.set(listaVehiculos.indexOf(oldVehiculo), dto);
				}
			}
		} catch (Exception e) {
			throwException(e);
		}
	}
	
	public void updateAll(ObservableMap<VehiculoPK, Vehiculo> mapToUpdate) throws DAOException {
		for (Map.Entry<VehiculoPK, Vehiculo> entry : mapToUpdate.entrySet()) {
			update(entry.getKey(), entry.getValue());
		}
	}

	public void delete(VehiculoPK pk) throws DAOException {
		Vehiculo oldVehiculo = getOldVehiculo(pk);

		try {
			if (pk == null) {
				throw new NullPointerException("No se puede eliminar un vehículo nulo.");
			}
			if (!listaVehiculos.contains(oldVehiculo)) {
				throw new OperationsException("El vehículo a eliminar no existe.");

			} else {
				vehiculoBD.delete(pk);

				listaVehiculos.remove(oldVehiculo);
			}
		} catch (Exception e) {
			throwException(e);
		}
	}
	
	public void deleteAll(ObservableList<Vehiculo> listToDelete) throws DAOException {
		for (Vehiculo dto : listToDelete) {
			delete(dto.createPK());
		}
	}

	public void findAll() throws DAOException {
		try {
			listaVehiculos.setAll(vehiculoBD.findAll());

		} catch (SQLException e) {
			throwException(e);
		}
	}

	public Vehiculo findByPrimaryKey(VehiculoPK pk) throws DAOException {
		try {
			return vehiculoBD.findByPrimaryKey(pk);

		} catch (SQLException e) {
			throwException(e);
		}
		return null;
	}
	
	public void setMaxRows(int maxRows) {
		vehiculoBD.setMaxRows(maxRows);
	}

	public int getMaxRows() {
		return vehiculoBD.getMaxRows();
	}

	public void createDependencies(ImagenVehiculoDAO imagenVehiculoDAO) {
		for (Vehiculo vehiculo : listaVehiculos) {
			vehiculo.setObjImagenVehiculo(dependencyVehiculoImagen(vehiculo, imagenVehiculoDAO));
		}
	}

	private ImagenVehiculo dependencyVehiculoImagen(Vehiculo vehiculo, ImagenVehiculoDAO imagenVehiculoDAO) {
		for (ImagenVehiculo imagenVehiculo : imagenVehiculoDAO.getImagenesVehiculo()) {
			if (imagenVehiculo.getCodImagen() == vehiculo.getCodImagen()) {
				return imagenVehiculo;
			}
		}
		return null;
	}

	private void throwException(Exception e) throws DAOException {
		throw new DAOException(ERROR + e.getMessage(), e);
	}

	private Vehiculo getOldVehiculo(VehiculoPK pk) {
		Vehiculo oldVehiculo = new Vehiculo();
		oldVehiculo.setMatricula(pk.getMatricula());
		oldVehiculo = listaVehiculos.get(listaVehiculos.indexOf(oldVehiculo));
		return oldVehiculo;
	}
	
}
