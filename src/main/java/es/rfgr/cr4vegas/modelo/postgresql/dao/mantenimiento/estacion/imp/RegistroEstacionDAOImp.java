package es.rfgr.cr4vegas.modelo.postgresql.dao.mantenimiento.estacion.imp;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

import javax.management.OperationsException;

import es.rfgr.cr4vegas.modelo.postgresql.bd.mantenimiento.estacion.RegistroEstacionBD;
import es.rfgr.cr4vegas.modelo.postgresql.dao.mantenimiento.estacion.EstacionDAO;
import es.rfgr.cr4vegas.modelo.postgresql.dao.mantenimiento.estacion.RegistroEstacionDAO;
import es.rfgr.cr4vegas.modelo.postgresql.dto.mantenimiento.estacion.Estacion;
import es.rfgr.cr4vegas.modelo.postgresql.dto.mantenimiento.estacion.RegistroEstacion;
import es.rfgr.cr4vegas.modelo.postgresql.dto.mantenimiento.estacion.RegistroEstacionPK;
import es.rfgr.cr4vegas.modelo.postgresql.exceptions.DAOException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;

public class RegistroEstacionDAOImp implements RegistroEstacionDAO {

	private static final String ERROR = "RegistroEstacionDAO: ";

	private ObservableList<RegistroEstacion> listaRegistroEstacion;

	private RegistroEstacionBD registroEstacionBD;

	public RegistroEstacionDAOImp() {
		listaRegistroEstacion = FXCollections.observableArrayList();
		registroEstacionBD = new RegistroEstacionBD();
	}

	public void setConnection(Connection conn) {
		registroEstacionBD.setConnection(conn);
	}

	public ObservableList<RegistroEstacion> getRegistroEstacion() {
		return listaRegistroEstacion;
	}

	public int getNumRegistroEstacion() {
		return listaRegistroEstacion.size();
	}

	public RegistroEstacionPK insert(RegistroEstacion dto) throws DAOException {
		RegistroEstacionPK pk = dto.createPK();

		try {
			if (listaRegistroEstacion.contains(dto)) {
				throw new OperationsException("El registro de estación ya existe.");
			} else {
				pk = registroEstacionBD.insert(dto);

				this.listaRegistroEstacion.add(dto);
			}
		} catch (Exception e) {
			throwException(e);
		}
		return pk;
	}

	public ObservableList<RegistroEstacionPK> insertAll(ObservableList<RegistroEstacion> listToInsert) throws DAOException {
		ObservableList<RegistroEstacionPK> listToReturn = FXCollections.observableArrayList();
		for (RegistroEstacion dto : listToInsert) {
			RegistroEstacionPK dtoPK = insert(dto);
			if (dtoPK != null) {
				listToReturn.add(dtoPK);
			}
		}
		return listToReturn;
	}

	public void update(RegistroEstacionPK pk, RegistroEstacion dto) throws DAOException {
		RegistroEstacion oldRegistroEstacion = getOldRegistroEstacion(pk);

		try {
			if (dto == null) {
				throw new OperationsException("El registro de estación a modificar no puede ser nula.");
			}
			if (!listaRegistroEstacion.contains(oldRegistroEstacion)) {
				throw new OperationsException("El registro de estación a modificar no existe.");

			} else {
				registroEstacionBD.update(pk, dto);

				listaRegistroEstacion.set(listaRegistroEstacion.indexOf(oldRegistroEstacion), dto);
			}
		} catch (Exception e) {
			throwException(e);
		}
	}

	public void updateAll(ObservableMap<RegistroEstacionPK, RegistroEstacion> mapToUpdate) throws DAOException {
		for (Map.Entry<RegistroEstacionPK, RegistroEstacion> entry : mapToUpdate.entrySet()) {
			update(entry.getKey(), entry.getValue());
		}
	}

	public void delete(RegistroEstacionPK pk) throws DAOException {
		RegistroEstacion oldRegistroEstacion = getOldRegistroEstacion(pk);

		try {
			if (pk == null) {
				throw new NullPointerException("No se puede eliminar un registro de estación nulo.");
			}
			if (!listaRegistroEstacion.contains(oldRegistroEstacion)) {
				throw new OperationsException("El registro de estación a eliminar no existe.");

			} else {
				registroEstacionBD.delete(pk);

				listaRegistroEstacion.remove(oldRegistroEstacion);
			}
		} catch (Exception e) {
			throwException(e);
		}
	}

	public void deleteAll(ObservableList<RegistroEstacion> listToDelete) throws DAOException {
		for (RegistroEstacion dto : listToDelete) {
			delete(dto.createPK());
		}
	}

	public void findAll() throws DAOException {
		try {
			listaRegistroEstacion.setAll(registroEstacionBD.findAll());

		} catch (Exception e) {
			throwException(e);
		}
	}

	public RegistroEstacion findByPrimaryKey(RegistroEstacionPK pk) throws DAOException {
		try {
			return registroEstacionBD.findByPrimaryKey(pk);

		} catch (SQLException e) {
			throwException(e);
		}
		return null;
	}
	
	public void setMaxRows(int maxRows) {
		registroEstacionBD.setMaxRows(maxRows);
	}

	public int getMaxRows() {
		return registroEstacionBD.getMaxRows();
	}
	
	public ObservableList<RegistroEstacion> getEstacionRegistros(Estacion dto) {
		ObservableList<RegistroEstacion> listaSalida = FXCollections.observableArrayList();
		for (RegistroEstacion re : listaRegistroEstacion) {
			if (re.getCodEstacion() == dto.getCodEstacion()) {
				listaSalida.add(re);
			}
		}
		return listaSalida;
	}

	public void createDependencies(EstacionDAO estacionDAO) {
		for (RegistroEstacion registroEstacion : listaRegistroEstacion) {
			registroEstacion.setObjEstacion(dependencyRegistroEstacionEstacion(registroEstacion, estacionDAO));
		}
	}

	private Estacion dependencyRegistroEstacionEstacion(RegistroEstacion dto, EstacionDAO estacionDAO) {
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

	private RegistroEstacion getOldRegistroEstacion(RegistroEstacionPK pk) {
		RegistroEstacion oldRegistroEstacion = new RegistroEstacion();
		oldRegistroEstacion.setCodRegistro(pk.getCodRegistro());
		oldRegistroEstacion = listaRegistroEstacion.get(listaRegistroEstacion.indexOf(oldRegistroEstacion));
		return oldRegistroEstacion;
	}
}
