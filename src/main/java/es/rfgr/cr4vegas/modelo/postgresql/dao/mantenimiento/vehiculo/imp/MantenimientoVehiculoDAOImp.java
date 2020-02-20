package es.rfgr.cr4vegas.modelo.postgresql.dao.mantenimiento.vehiculo.imp;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

import javax.management.OperationsException;

import es.rfgr.cr4vegas.modelo.postgresql.bd.mantenimiento.vehiculo.MantenimientoVehiculoBD;
import es.rfgr.cr4vegas.modelo.postgresql.dao.grupo.GrupoDAO;
import es.rfgr.cr4vegas.modelo.postgresql.dao.mantenimiento.vehiculo.MantenimientoVehiculoDAO;
import es.rfgr.cr4vegas.modelo.postgresql.dao.mantenimiento.vehiculo.VehiculoDAO;
import es.rfgr.cr4vegas.modelo.postgresql.dto.grupo.Grupo;
import es.rfgr.cr4vegas.modelo.postgresql.dto.mantenimiento.vehiculo.MantenimientoVehiculo;
import es.rfgr.cr4vegas.modelo.postgresql.dto.mantenimiento.vehiculo.MantenimientoVehiculoPK;
import es.rfgr.cr4vegas.modelo.postgresql.dto.mantenimiento.vehiculo.Vehiculo;
import es.rfgr.cr4vegas.modelo.postgresql.exceptions.DAOException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;

public class MantenimientoVehiculoDAOImp implements MantenimientoVehiculoDAO {

	private static final String ERROR = "MantenimientoVehiculoDAO: ";

	private ObservableList<MantenimientoVehiculo> listaMantenimientosVehiculo;

	private MantenimientoVehiculoBD mantenimientoVehiculoBD;

	public MantenimientoVehiculoDAOImp() {
		listaMantenimientosVehiculo = FXCollections.observableArrayList();
		mantenimientoVehiculoBD = new MantenimientoVehiculoBD();
	}

	public void setConnection(Connection conn) {
		mantenimientoVehiculoBD.setConnection(conn);
	}

	public ObservableList<MantenimientoVehiculo> getMantenimientosVehiculo() {
		return listaMantenimientosVehiculo;
	}

	public int getNumMantenimientosVehiculo() {
		return listaMantenimientosVehiculo.size();
	}

	public MantenimientoVehiculoPK insert(MantenimientoVehiculo dto) throws DAOException {
		MantenimientoVehiculoPK pk = dto.createPK();

		try {
			if (listaMantenimientosVehiculo.contains(dto)) {
				throw new OperationsException("El mantenimiento de vehículo ya existe.");
			} else {
				pk = mantenimientoVehiculoBD.insert(dto);

				this.listaMantenimientosVehiculo.add(dto);
			}
		} catch (Exception e) {
			throwException(e);
		}
		return pk;
	}

	public ObservableList<MantenimientoVehiculoPK> insertAll(ObservableList<MantenimientoVehiculo> listToInsert)
			throws DAOException {
		ObservableList<MantenimientoVehiculoPK> listToReturn = FXCollections.observableArrayList();
		for (MantenimientoVehiculo dto : listToInsert) {
			MantenimientoVehiculoPK dtoPK = insert(dto);
			if (dtoPK != null) {
				listToReturn.add(dtoPK);
			}
		}
		return listToReturn;
	}

	public void update(MantenimientoVehiculoPK pk, MantenimientoVehiculo dto) throws DAOException {
		MantenimientoVehiculo oldMantenimientoVehiculo = getOldMantenimientoVehiculo(pk);

		try {
			if (dto == null) {
				throw new OperationsException("El mantenimiento de vehículo a modificar no puede ser nulo.");
			} else {
				if (!listaMantenimientosVehiculo.contains(oldMantenimientoVehiculo)) {
					throw new OperationsException("El mantenimiento de vehículo a modificar no existe.");

				} else {
					mantenimientoVehiculoBD.update(pk, dto);

					listaMantenimientosVehiculo.set(listaMantenimientosVehiculo.indexOf(oldMantenimientoVehiculo), dto);
				}
			}
		} catch (Exception e) {
			throwException(e);
		}
	}

	public void updateAll(ObservableMap<MantenimientoVehiculoPK, MantenimientoVehiculo> mapToUpdate)
			throws DAOException {
		for (Map.Entry<MantenimientoVehiculoPK, MantenimientoVehiculo> entry : mapToUpdate.entrySet()) {
			update(entry.getKey(), entry.getValue());
		}
	}

	public void delete(MantenimientoVehiculoPK pk) throws DAOException {
		MantenimientoVehiculo oldMantenimientoVehiculo = getOldMantenimientoVehiculo(pk);

		try {
			if (pk == null) {
				throw new NullPointerException("No se puede eliminar un mantenimiento de vehículo nulo.");
			}
			if (!listaMantenimientosVehiculo.contains(oldMantenimientoVehiculo)) {
				throw new OperationsException("El mantenimiento de vehículo a eliminar no existe.");

			} else {
				mantenimientoVehiculoBD.delete(pk);

				listaMantenimientosVehiculo.remove(oldMantenimientoVehiculo);
			}
		} catch (Exception e) {
			throwException(e);
		}
	}

	public void deleteAll(ObservableList<MantenimientoVehiculo> listToDelete) throws DAOException {
		for (MantenimientoVehiculo dto : listToDelete) {
			delete(dto.createPK());
		}
	}

	public void findAll() throws DAOException {
		try {
			listaMantenimientosVehiculo.setAll(mantenimientoVehiculoBD.findAll());

		} catch (SQLException e) {
			throwException(e);
		}
	}

	public MantenimientoVehiculo findByPrimaryKey(MantenimientoVehiculoPK pk) throws DAOException {
		try {
			return mantenimientoVehiculoBD.findByPrimaryKey(pk);

		} catch (SQLException e) {
			throwException(e);
		}
		return null;
	}

	public void setMaxRows(int maxRows) {
		mantenimientoVehiculoBD.setMaxRows(maxRows);
	}

	public int getMaxRows() {
		return mantenimientoVehiculoBD.getMaxRows();
	}

	public ObservableList<MantenimientoVehiculo> getVehiculoMantenimientos(Vehiculo dto) {
		ObservableList<MantenimientoVehiculo> listaSalida = FXCollections.observableArrayList();
		for (MantenimientoVehiculo mv : listaMantenimientosVehiculo) {
			if (mv.getMatricula() == dto.getMatricula()) {
				listaSalida.add(mv);
			}
		}
		return listaSalida;
	}

	public void createDependencies(GrupoDAO grupoDAO, VehiculoDAO vehiculoDAO) {
		for (MantenimientoVehiculo mv : listaMantenimientosVehiculo) {
			mv.setObjGrupo(dependencyMantenimientoVehiculoGrupo(mv, grupoDAO));
			mv.setObjVehiculo(dependencyMantenimientoVehiculoVehiculo(mv, vehiculoDAO));
		}
	}

	private Grupo dependencyMantenimientoVehiculoGrupo(MantenimientoVehiculo dto, GrupoDAO grupoDAO) {
		for (Grupo grupo : grupoDAO.getGrupos()) {
			if (grupo.getNombre().equals(dto.getGrupo())) {
				return grupo;
			}
		}
		return null;
	}

	private Vehiculo dependencyMantenimientoVehiculoVehiculo(MantenimientoVehiculo dto, VehiculoDAO vehiculoDAO) {
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

	private MantenimientoVehiculo getOldMantenimientoVehiculo(MantenimientoVehiculoPK pk) {
		MantenimientoVehiculo oldMantenimientoVehiculo = new MantenimientoVehiculo();
		oldMantenimientoVehiculo.setCodMan(pk.getCodMan());
		oldMantenimientoVehiculo = listaMantenimientosVehiculo
				.get(listaMantenimientosVehiculo.indexOf(oldMantenimientoVehiculo));
		return oldMantenimientoVehiculo;
	}
}
