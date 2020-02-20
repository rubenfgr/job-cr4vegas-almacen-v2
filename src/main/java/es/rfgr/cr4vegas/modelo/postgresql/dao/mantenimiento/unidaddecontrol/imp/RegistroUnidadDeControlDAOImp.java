package es.rfgr.cr4vegas.modelo.postgresql.dao.mantenimiento.unidaddecontrol.imp;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

import javax.management.OperationsException;

import es.rfgr.cr4vegas.modelo.postgresql.bd.mantenimiento.unidaddecontrol.RegistroUnidadDeControlBD;
import es.rfgr.cr4vegas.modelo.postgresql.dao.mantenimiento.unidaddecontrol.RegistroUnidadDeControlDAO;
import es.rfgr.cr4vegas.modelo.postgresql.dao.mantenimiento.unidaddecontrol.UnidadDeControlDAO;
import es.rfgr.cr4vegas.modelo.postgresql.dto.mantenimiento.unidaddecontrol.RegistroUnidadDeControl;
import es.rfgr.cr4vegas.modelo.postgresql.dto.mantenimiento.unidaddecontrol.RegistroUnidadDeControlPK;
import es.rfgr.cr4vegas.modelo.postgresql.dto.mantenimiento.unidaddecontrol.UnidadDeControl;
import es.rfgr.cr4vegas.modelo.postgresql.exceptions.DAOException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;

public class RegistroUnidadDeControlDAOImp implements RegistroUnidadDeControlDAO {

	private static final String ERROR = "RegistroUdControlDAO: ";

	private ObservableList<RegistroUnidadDeControl> listaRegistrosUdControl;

	private RegistroUnidadDeControlBD registroUdControlBD;

	public RegistroUnidadDeControlDAOImp() {
		listaRegistrosUdControl = FXCollections.observableArrayList();
		registroUdControlBD = new RegistroUnidadDeControlBD();
	}

	public void setConnection(Connection conn) {
		registroUdControlBD.setConnection(conn);
	}

	public ObservableList<RegistroUnidadDeControl> getRegistrosUdControl() {
		return listaRegistrosUdControl;
	}

	public int getNumRegistrosUdControl() {
		return listaRegistrosUdControl.size();
	}

	public RegistroUnidadDeControlPK insert(RegistroUnidadDeControl dto) throws DAOException {
		RegistroUnidadDeControlPK pk = dto.createPK();

		try {
			if (listaRegistrosUdControl.contains(dto)) {
				throw new OperationsException("El registro de ud.control ya existe.");

			} else {
				pk = registroUdControlBD.insert(dto);

				this.listaRegistrosUdControl.add(dto);
			}
		} catch (Exception e) {
			throwException(e);
		}
		return pk;
	}
	
	public ObservableList<RegistroUnidadDeControlPK> insertAll(ObservableList<RegistroUnidadDeControl> listToInsert) throws DAOException {
		ObservableList<RegistroUnidadDeControlPK> listToReturn = FXCollections.observableArrayList();
		for (RegistroUnidadDeControl dto : listToInsert) {
			RegistroUnidadDeControlPK dtoPK = insert(dto);
			if (dtoPK != null) {
				listToReturn.add(dtoPK);
			}
		}
		return listToReturn;
	}

	public void update(RegistroUnidadDeControlPK pk, RegistroUnidadDeControl dto) throws DAOException {
		RegistroUnidadDeControl oldRegistroUDControl = getOldRegistroUDControl(pk);

		try {
			if (dto == null) {
				throw new OperationsException("El registro de ud.control a modificar no puede ser nula.");
			} else {
				if (!listaRegistrosUdControl.contains(oldRegistroUDControl)) {
					throw new OperationsException("El registro de ud.control a modificar no existe.");

				} else {
					registroUdControlBD.update(pk, dto);

					listaRegistrosUdControl.set(listaRegistrosUdControl.indexOf(oldRegistroUDControl), dto);
				}
			}
		} catch (Exception e) {
			throwException(e);
		}
	}
	
	public void updateAll(ObservableMap<RegistroUnidadDeControlPK, RegistroUnidadDeControl> mapToUpdate) throws DAOException {
		for (Map.Entry<RegistroUnidadDeControlPK, RegistroUnidadDeControl> entry : mapToUpdate.entrySet()) {
			update(entry.getKey(), entry.getValue());
		}
	}

	public void delete(RegistroUnidadDeControlPK pk) throws DAOException {
		RegistroUnidadDeControl oldRegistroUDControl = getOldRegistroUDControl(pk);

		try {
			if (pk == null) {
				throw new NullPointerException("No se puede eliminar un registro de ud.control nulo.");
			}
			if (!listaRegistrosUdControl.contains(oldRegistroUDControl)) {
				throw new OperationsException("El registro de ud.control a eliminar no existe.");

			} else {
				registroUdControlBD.delete(pk);

				listaRegistrosUdControl.remove(oldRegistroUDControl);
			}
		} catch (Exception e) {
			throwException(e);
		}
	}
	
	public void deleteAll(ObservableList<RegistroUnidadDeControl> listToDelete) throws DAOException {
		for (RegistroUnidadDeControl dto : listToDelete) {
			delete(dto.createPK());
		}
	}

	public void findAll() throws DAOException {
		try {
			listaRegistrosUdControl.setAll(registroUdControlBD.findAll());

		} catch (SQLException e) {
			throwException(e);
		}
	}

	public RegistroUnidadDeControl findByPrimaryKey(RegistroUnidadDeControlPK pk) throws DAOException {
		try {
			return registroUdControlBD.findByPrimaryKey(pk);

		} catch (SQLException e) {
			throwException(e);
		}
		return null;
	}
	
	public void setMaxRows(int maxRows) {
		registroUdControlBD.setMaxRows(maxRows);
	}

	public int getMaxRows() {
		return registroUdControlBD.getMaxRows();
	}
	
	public ObservableList<RegistroUnidadDeControl> getUdControlRegistros(UnidadDeControl dto) {
		ObservableList<RegistroUnidadDeControl> listToReturn = FXCollections.observableArrayList();
		for (RegistroUnidadDeControl ruc : listaRegistrosUdControl) {
			if (ruc.getCodUdControl() == dto.getCodUdControl()) {
				listToReturn.add(ruc);
			}
		}
		return listToReturn;
	}

	public void createDependencies(UnidadDeControlDAO uDControlDAO) {
		for (RegistroUnidadDeControl registroUdControl : listaRegistrosUdControl) {
			registroUdControl
					.setObjUdControl(dependencyRegistroudControlUdControl(registroUdControl, uDControlDAO));
		}
	}

	private UnidadDeControl dependencyRegistroudControlUdControl(RegistroUnidadDeControl dto, UnidadDeControlDAO uDControlDAO) {
		for (UnidadDeControl udControl : uDControlDAO.getUdsControl()) {
			if (udControl.getCodUdControl() == dto.getCodUdControl()) {
				return udControl;
			}
		}
		return null;
	}

	private void throwException(Exception e) throws DAOException {
		throw new DAOException(ERROR + e.getMessage(), e);
	}

	private RegistroUnidadDeControl getOldRegistroUDControl(RegistroUnidadDeControlPK pk) {
		RegistroUnidadDeControl oldRegistroUDControl = new RegistroUnidadDeControl();
		oldRegistroUDControl.setCodRegistro(pk.getCodRegistro());
		oldRegistroUDControl = listaRegistrosUdControl.get(listaRegistrosUdControl.indexOf(oldRegistroUDControl));
		return oldRegistroUDControl;
	}
}
