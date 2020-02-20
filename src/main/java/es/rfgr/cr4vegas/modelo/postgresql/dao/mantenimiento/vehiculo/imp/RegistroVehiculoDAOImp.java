package es.rfgr.cr4vegas.modelo.postgresql.dao.mantenimiento.vehiculo.imp;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

import javax.management.OperationsException;

import es.rfgr.cr4vegas.modelo.postgresql.bd.mantenimiento.vehiculo.RegistroVehiculoBD;
import es.rfgr.cr4vegas.modelo.postgresql.dao.mantenimiento.vehiculo.RegistroVehiculoDAO;
import es.rfgr.cr4vegas.modelo.postgresql.dao.mantenimiento.vehiculo.VehiculoDAO;
import es.rfgr.cr4vegas.modelo.postgresql.dto.mantenimiento.vehiculo.RegistroVehiculo;
import es.rfgr.cr4vegas.modelo.postgresql.dto.mantenimiento.vehiculo.RegistroVehiculoPK;
import es.rfgr.cr4vegas.modelo.postgresql.dto.mantenimiento.vehiculo.Vehiculo;
import es.rfgr.cr4vegas.modelo.postgresql.exceptions.DAOException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;

public class RegistroVehiculoDAOImp implements RegistroVehiculoDAO {

	private static final String ERROR = "RegistroVehiculoDAO: ";

	private ObservableList<RegistroVehiculo> listaRegistrosVehiculo;

	private RegistroVehiculoBD registroVehiculoBD;

	public RegistroVehiculoDAOImp() {
		listaRegistrosVehiculo = FXCollections.observableArrayList();
		registroVehiculoBD = new RegistroVehiculoBD();
	}

	public void setConnection(Connection conn) {
		registroVehiculoBD.setConnection(conn);
	}

	public ObservableList<RegistroVehiculo> getRegistrosVehiculo() {
		return listaRegistrosVehiculo;
	}

	public int getNumRegistrosVehiculo() {
		return listaRegistrosVehiculo.size();
	}

	public RegistroVehiculoPK insert(RegistroVehiculo dto) throws DAOException {
		RegistroVehiculoPK pk = dto.createPK();
		
		try {
			if (listaRegistrosVehiculo.contains(dto)) {
				throw new OperationsException("El registro de vehículo ya existe.");
			} else {
				pk = registroVehiculoBD.insert(dto);
				
				this.listaRegistrosVehiculo.add(dto);
			}
		} catch (Exception e) {
			throwException(e);
		}
		return pk;
	}
	
	public ObservableList<RegistroVehiculoPK> insertAll(ObservableList<RegistroVehiculo> listToInsert) throws DAOException {
		ObservableList<RegistroVehiculoPK> listToReturn = FXCollections.observableArrayList();
		for (RegistroVehiculo dto : listToInsert) {
			RegistroVehiculoPK dtoPK = insert(dto);
			if (dtoPK != null) {
				listToReturn.add(dtoPK);
			}
		}
		return listToReturn;
	}

	public void update(RegistroVehiculoPK pk, RegistroVehiculo dto) throws DAOException {
		RegistroVehiculo oldRegistroVehiculo = getOldRegistroVehiculo(pk);

		try {
			if (dto == null) {
				throw new OperationsException("El registro de vehículo a modificar no puede ser nula.");
			} else {
				if (!listaRegistrosVehiculo.contains(oldRegistroVehiculo)) {
					throw new OperationsException("El registro de vehículo a modificar no existe.");
					
				} else {
					registroVehiculoBD.update(pk, dto);
					
					listaRegistrosVehiculo.set(listaRegistrosVehiculo.indexOf(oldRegistroVehiculo), dto);
				}
			}
		} catch (Exception e) {
			throwException(e);
		}
	}
	
	public void updateAll(ObservableMap<RegistroVehiculoPK, RegistroVehiculo> mapToUpdate) throws DAOException {
		for (Map.Entry<RegistroVehiculoPK, RegistroVehiculo> entry : mapToUpdate.entrySet()) {
			update(entry.getKey(), entry.getValue());
		}
	}

	public void delete(RegistroVehiculoPK pk) throws DAOException {
		RegistroVehiculo oldRegistroVehiculo = getOldRegistroVehiculo(pk);

		try {
			if (pk == null) {
				throw new NullPointerException("No se puede eliminar un registro de vehículo nulo.");
			}
			if (!listaRegistrosVehiculo.contains(oldRegistroVehiculo)) {
				throw new OperationsException("El registro de vehículo a eliminar no existe.");

			} else {
				registroVehiculoBD.delete(pk);

				listaRegistrosVehiculo.remove(oldRegistroVehiculo);
			}
		} catch (Exception e) {
			throwException(e);
		}
	}
	
	public void deleteAll(ObservableList<RegistroVehiculo> listToDelete) throws DAOException {
		for (RegistroVehiculo dto : listToDelete) {
			delete(dto.createPK());
		}
	}

	public void findAll() throws DAOException {
		try {
			listaRegistrosVehiculo.setAll(registroVehiculoBD.findAll());

		} catch (SQLException e) {
			throwException(e);
		}
	}

	public RegistroVehiculo findByPrimaryKey(RegistroVehiculoPK pk) throws DAOException {
		try {
			return registroVehiculoBD.findByPrimaryKey(pk);

		} catch (SQLException e) {
			throwException(e);
		}
		return null;
	}
	
	public void setMaxRows(int maxRows) {
		registroVehiculoBD.setMaxRows(maxRows);
	}

	public int getMaxRows() {
		return registroVehiculoBD.getMaxRows();
	}
	
	public ObservableList<RegistroVehiculo> getVehiculoRegistros(Vehiculo dto) {
		ObservableList<RegistroVehiculo> listaSalida = FXCollections.observableArrayList();
		for (RegistroVehiculo rv : listaRegistrosVehiculo) {
			if (rv.getMatricula() == dto.getMatricula()) {
				listaSalida.add(rv);
			}
		}
		return listaSalida;
	}

	public void createDependencies(VehiculoDAO vehiculoDAO) {
		for (RegistroVehiculo rv : listaRegistrosVehiculo) {
			rv.setObjVehiculo(dependencyRegistroVehiculoVehiculo(rv, vehiculoDAO));
		}
	}

	private Vehiculo dependencyRegistroVehiculoVehiculo(RegistroVehiculo dto, VehiculoDAO vehiculoDAO) {
		for (Vehiculo vehiculo : vehiculoDAO.getVehiculos()) {
			if (vehiculo.getMatricula() == dto.getMatricula()) {
				return vehiculo;
			}
		}
		return null;
	}

	private void throwException(Exception e) throws DAOException {
		throw new DAOException(ERROR + e.getMessage(), e);
	}

	private RegistroVehiculo getOldRegistroVehiculo(RegistroVehiculoPK pk) {
		RegistroVehiculo oldRegistroVehiculo = new RegistroVehiculo();
		oldRegistroVehiculo.setCodRegistro(pk.getCodRegistro());
		oldRegistroVehiculo = listaRegistrosVehiculo.get(listaRegistrosVehiculo.indexOf(oldRegistroVehiculo));
		return oldRegistroVehiculo;
	}
}
