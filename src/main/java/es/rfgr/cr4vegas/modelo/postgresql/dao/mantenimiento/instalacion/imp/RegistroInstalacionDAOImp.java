package es.rfgr.cr4vegas.modelo.postgresql.dao.mantenimiento.instalacion.imp;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

import javax.management.OperationsException;

import es.rfgr.cr4vegas.modelo.postgresql.bd.mantenimiento.instalacion.RegistroInstalacionBD;
import es.rfgr.cr4vegas.modelo.postgresql.dao.mantenimiento.instalacion.InstalacionDAO;
import es.rfgr.cr4vegas.modelo.postgresql.dao.mantenimiento.instalacion.RegistroInstalacionDAO;
import es.rfgr.cr4vegas.modelo.postgresql.dto.mantenimiento.instalacion.Instalacion;
import es.rfgr.cr4vegas.modelo.postgresql.dto.mantenimiento.instalacion.RegistroInstalacion;
import es.rfgr.cr4vegas.modelo.postgresql.dto.mantenimiento.instalacion.RegistroInstalacionPK;
import es.rfgr.cr4vegas.modelo.postgresql.exceptions.DAOException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;

public class RegistroInstalacionDAOImp implements RegistroInstalacionDAO {

	private static final String ERROR = "RegistroInstalacionDAO: ";

	private ObservableList<RegistroInstalacion> listaRegistroEstacion;

	private RegistroInstalacionBD registroEstacionBD;

	public RegistroInstalacionDAOImp() {
		listaRegistroEstacion = FXCollections.observableArrayList();
		registroEstacionBD = new RegistroInstalacionBD();
	}

	public void setConnection(Connection conn) {
		registroEstacionBD.setConnection(conn);
	}

	public ObservableList<RegistroInstalacion> getRegistroInstalacion() {
		return listaRegistroEstacion;
	}

	public int getNumRegistroInstalacion() {
		return listaRegistroEstacion.size();
	}

	public RegistroInstalacionPK insert(RegistroInstalacion dto) throws DAOException {
		RegistroInstalacionPK pk = dto.createPK();

		try {
			if (listaRegistroEstacion.contains(dto)) {
				throw new OperationsException("El registro de instalación ya existe.");

			} else {
				pk = registroEstacionBD.insert(dto);

				this.listaRegistroEstacion.add(dto);
			}
		} catch (Exception e) {
			throwException(e);
		}
		return pk;
	}

	public ObservableList<RegistroInstalacionPK> insertAll(ObservableList<RegistroInstalacion> listToInsert) throws DAOException {
		ObservableList<RegistroInstalacionPK> listToReturn = FXCollections.observableArrayList();
		for (RegistroInstalacion dto : listToInsert) {
			RegistroInstalacionPK dtoPK = insert(dto);
			if (dtoPK != null) {
				listToReturn.add(dtoPK);
			}
		}
		return listToReturn;
	}

	public void update(RegistroInstalacionPK pk, RegistroInstalacion dto) throws DAOException {
		RegistroInstalacion oldRegistroInstalacion = getOldRegistroInstalacion(pk);

		try {
			if (dto == null) {
				throw new OperationsException("El registro de instalación a modificar no puede ser nula.");
			} else {
				if (!listaRegistroEstacion.contains(oldRegistroInstalacion)) {
					throw new OperationsException("El registro de instalación a modificar no existe.");

				} else {
					registroEstacionBD.update(pk, dto);

					listaRegistroEstacion.set(listaRegistroEstacion.indexOf(oldRegistroInstalacion), dto);
				}
			}
		} catch (Exception e) {
			throwException(e);
		}
	}

	public void updateAll(ObservableMap<RegistroInstalacionPK, RegistroInstalacion> mapToUpdate) throws DAOException {
		for (Map.Entry<RegistroInstalacionPK, RegistroInstalacion> entry : mapToUpdate.entrySet()) {
			update(entry.getKey(), entry.getValue());
		}
	}

	public void delete(RegistroInstalacionPK pk) throws DAOException {
		RegistroInstalacion oldRegistroInstalacion = getOldRegistroInstalacion(pk);

		try {
			if (pk == null) {
				throw new NullPointerException("No se puede eliminar un registro de instalación nulo.");
			}
			if (!listaRegistroEstacion.contains(oldRegistroInstalacion)) {
				registroEstacionBD.delete(pk);

				listaRegistroEstacion.remove(oldRegistroInstalacion);
			} else {
				throw new OperationsException("El registro de instalación a eliminar no existe.");
			}
		} catch (Exception e) {
			throwException(e);
		}
	}

	public void deleteAll(ObservableList<RegistroInstalacion> listToDelete) throws DAOException {
		for (RegistroInstalacion dto : listToDelete) {
			delete(dto.createPK());
		}
	}

	public void findAll() throws DAOException {
		try {
			listaRegistroEstacion.setAll(registroEstacionBD.findAll());

		} catch (SQLException e) {
			throwException(e);
		}
	}

	public RegistroInstalacion findByPrimaryKey(RegistroInstalacionPK pk) throws DAOException {
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

	public ObservableList<RegistroInstalacion> getInstalacionRegistros(Instalacion dto) {
		ObservableList<RegistroInstalacion> listaSalida = FXCollections.observableArrayList();
		for (RegistroInstalacion ri : listaRegistroEstacion) {
			if (ri.getCodInstalacion() == dto.getCodInstalacion()) {
				listaSalida.add(ri);
			}
		}
		return listaSalida;
	}

	public void createDependencies(InstalacionDAO instalacionDAO) {
		for (RegistroInstalacion dto : listaRegistroEstacion) {
			dto.setObjInstalacion(dependencyRegistroInstalacionInstalacion(dto, instalacionDAO));
		}
	}

	private Instalacion dependencyRegistroInstalacionInstalacion(RegistroInstalacion dto, InstalacionDAO instalacionDAO) {
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

	private RegistroInstalacion getOldRegistroInstalacion(RegistroInstalacionPK pk) {
		RegistroInstalacion oldRegistroInstalacion = new RegistroInstalacion();
		oldRegistroInstalacion.setCodRegistro(pk.getCodRegistro());
		oldRegistroInstalacion = listaRegistroEstacion.get(listaRegistroEstacion.indexOf(oldRegistroInstalacion));
		return oldRegistroInstalacion;
	}
}
