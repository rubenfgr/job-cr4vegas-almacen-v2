package es.rfgr.cr4vegas.modelo.postgresql.dao.mantenimiento.vehiculo.imp;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

import javax.management.OperationsException;

import es.rfgr.cr4vegas.modelo.postgresql.bd.mantenimiento.vehiculo.GeneraVehiculoEventoBD;
import es.rfgr.cr4vegas.modelo.postgresql.dao.evento.EventoDAO;
import es.rfgr.cr4vegas.modelo.postgresql.dao.mantenimiento.vehiculo.GeneraVehiculoEventoDAO;
import es.rfgr.cr4vegas.modelo.postgresql.dao.mantenimiento.vehiculo.MantenimientoVehiculoDAO;
import es.rfgr.cr4vegas.modelo.postgresql.dto.evento.Evento;
import es.rfgr.cr4vegas.modelo.postgresql.dto.mantenimiento.vehiculo.GeneraVehiculoEvento;
import es.rfgr.cr4vegas.modelo.postgresql.dto.mantenimiento.vehiculo.GeneraVehiculoEventoPK;
import es.rfgr.cr4vegas.modelo.postgresql.dto.mantenimiento.vehiculo.MantenimientoVehiculo;
import es.rfgr.cr4vegas.modelo.postgresql.exceptions.DAOException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;

public class GeneraVehiculoEventoDAOImp implements GeneraVehiculoEventoDAO {

	private static final String ERROR = "GeneraVehiculoEventoDAO: ";

	private ObservableList<GeneraVehiculoEvento> listaGeneraVehiculoEventos;

	private GeneraVehiculoEventoBD generaVehiculoEventoBD;

	public GeneraVehiculoEventoDAOImp() {
		listaGeneraVehiculoEventos = FXCollections.observableArrayList();
		generaVehiculoEventoBD = new GeneraVehiculoEventoBD();
	}

	public void setConnection(Connection conn) {
		generaVehiculoEventoBD.setConnection(conn);
	}

	public ObservableList<GeneraVehiculoEvento> getGeneraVehiculoEventos() {
		return listaGeneraVehiculoEventos;
	}

	public int getNumGeneraVehiculoEventos() {
		return listaGeneraVehiculoEventos.size();
	}

	public GeneraVehiculoEventoPK insert(GeneraVehiculoEvento dto) throws DAOException {
		GeneraVehiculoEventoPK pk = dto.createPK();

		try {
			if (listaGeneraVehiculoEventos.contains(dto)) {
				throw new OperationsException("El vehículo-evento ya existe.");

			} else {
				pk = generaVehiculoEventoBD.insert(dto);

				this.listaGeneraVehiculoEventos.add(dto);
			}
		} catch (Exception e) {
			throwException(e);
		}
		return pk;
	}

	public ObservableList<GeneraVehiculoEventoPK> insertAll(ObservableList<GeneraVehiculoEvento> listToInsert) throws DAOException {
		ObservableList<GeneraVehiculoEventoPK> listToReturn = FXCollections.observableArrayList();
		for (GeneraVehiculoEvento dto : listToInsert) {
			GeneraVehiculoEventoPK dtoPK = insert(dto);
			if (dtoPK != null) {
				listToReturn.add(dtoPK);
			}
		}
		return listToReturn;
	}

	public void update(GeneraVehiculoEventoPK pk, GeneraVehiculoEvento dto) throws DAOException {
		GeneraVehiculoEvento oldGeneraVehiculoEvento = getOldGeneraVehiculoEvento(pk);

		try {
			if (dto == null) {
				throw new OperationsException("El vehículo-evento a modificar no puede ser nulo.");
			} else {
				if (!listaGeneraVehiculoEventos.contains(oldGeneraVehiculoEvento)) {
					throw new OperationsException("El vehículo-evento a modificar no existe.");

				} else {
					generaVehiculoEventoBD.update(pk, dto);

					listaGeneraVehiculoEventos.set(listaGeneraVehiculoEventos.indexOf(oldGeneraVehiculoEvento), dto);
				}
			}
		} catch (Exception e) {
			throwException(e);
		}
	}

	public void updateAll(ObservableMap<GeneraVehiculoEventoPK, GeneraVehiculoEvento> mapToUpdate) throws DAOException {
		for (Map.Entry<GeneraVehiculoEventoPK, GeneraVehiculoEvento> entry : mapToUpdate.entrySet()) {
			update(entry.getKey(), entry.getValue());
		}
	}

	public void delete(GeneraVehiculoEventoPK pk) throws DAOException {
		GeneraVehiculoEvento oldGeneraVehiculoEvento = getOldGeneraVehiculoEvento(pk);

		try {
			if (pk == null) {
				throw new NullPointerException("No se puede eliminar un vehículo-evento nulo.");
			}
			if (!listaGeneraVehiculoEventos.contains(oldGeneraVehiculoEvento)) {
				throw new OperationsException("El vehículo-evento a eliminar no existe.");

			} else {
				generaVehiculoEventoBD.delete(pk);

				listaGeneraVehiculoEventos.remove(oldGeneraVehiculoEvento);
			}
		} catch (Exception e) {
			throwException(e);
		}
	}

	public void deleteAll(ObservableList<GeneraVehiculoEvento> listToDelete) throws DAOException {
		for (GeneraVehiculoEvento dto : listToDelete) {
			delete(dto.createPK());
		}
	}

	public void findAll() throws DAOException {
		try {
			listaGeneraVehiculoEventos.setAll(generaVehiculoEventoBD.findAll());

		} catch (SQLException e) {
			throwException(e);
		}
	}

	public GeneraVehiculoEvento findByPrimaryKey(GeneraVehiculoEventoPK pk) throws DAOException {
		try {
			return generaVehiculoEventoBD.findByPrimaryKey(pk);

		} catch (SQLException e) {
			throwException(e);
		}
		return null;
	}
	
	public void setMaxRows(int maxRows) {
		generaVehiculoEventoBD.setMaxRows(maxRows);
	}

	public int getMaxRows() {
		return generaVehiculoEventoBD.getMaxRows();
	}
	
	public ObservableList<Evento> getMantenimientoVehiculoEventos(MantenimientoVehiculo dto) {
		ObservableList<Evento> listToReturn = FXCollections.observableArrayList();
		for (GeneraVehiculoEvento dtoOnList : listaGeneraVehiculoEventos) {
			if (dtoOnList.getCodMan() == dto.getCodMan()) {
				listToReturn.add(dtoOnList.getObjEvento());
			}
		}
		return listToReturn;
	}

	public void createDependencies(MantenimientoVehiculoDAO mantenimientoVehiculoDAO, EventoDAO eventoDAO) {
		for (GeneraVehiculoEvento gve : listaGeneraVehiculoEventos) {
			gve.setObjEvento(dependencyGeneraVehiculoEventoEvento(gve, eventoDAO));
			gve.setObjMantenimientoVehiculo(
					dependencyGeneraVehiculoEventoMantenimientoVehiculo(gve, mantenimientoVehiculoDAO));
		}
	}

	private MantenimientoVehiculo dependencyGeneraVehiculoEventoMantenimientoVehiculo(GeneraVehiculoEvento dto,
			MantenimientoVehiculoDAO mantenimientoVehiculoDAO) {
		for (MantenimientoVehiculo mantenimientoVehiculo : mantenimientoVehiculoDAO.getMantenimientosVehiculo()) {
			if (mantenimientoVehiculo.getCodMan() == dto.getCodMan()) {
				return mantenimientoVehiculo;
			}
		}
		return null;
	}

	private Evento dependencyGeneraVehiculoEventoEvento(GeneraVehiculoEvento dto, EventoDAO eventoDAO) {
		for (Evento evento : eventoDAO.getEventos()) {
			if (evento.getCodEvento() == dto.getCodEvento()) {
				return evento;
			}
		}
		return null;
	}

	private void throwException(Exception e) throws DAOException {
		throw new DAOException(ERROR + e.getMessage(), e);
	}

	private GeneraVehiculoEvento getOldGeneraVehiculoEvento(GeneraVehiculoEventoPK pk) {
		GeneraVehiculoEvento oldGeneraVehiculoEvento = new GeneraVehiculoEvento();
		oldGeneraVehiculoEvento.setCodEvento(pk.getCodEvento());
		oldGeneraVehiculoEvento = listaGeneraVehiculoEventos
				.get(listaGeneraVehiculoEventos.indexOf(oldGeneraVehiculoEvento));
		return oldGeneraVehiculoEvento;
	}
}
