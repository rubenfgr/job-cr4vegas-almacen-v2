package es.rfgr.cr4vegas.modelo.postgresql.dao.mantenimiento.unidaddecontrol.imp;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

import javax.management.OperationsException;

import es.rfgr.cr4vegas.modelo.postgresql.bd.mantenimiento.unidaddecontrol.MantenimientoUnidadDeControlBD;
import es.rfgr.cr4vegas.modelo.postgresql.dao.grupo.GrupoDAO;
import es.rfgr.cr4vegas.modelo.postgresql.dao.mantenimiento.unidaddecontrol.MantenimientoUnidadDeControlDAO;
import es.rfgr.cr4vegas.modelo.postgresql.dao.mantenimiento.unidaddecontrol.UnidadDeControlDAO;
import es.rfgr.cr4vegas.modelo.postgresql.dto.grupo.Grupo;
import es.rfgr.cr4vegas.modelo.postgresql.dto.mantenimiento.unidaddecontrol.MantenimientoUnidadDeControl;
import es.rfgr.cr4vegas.modelo.postgresql.dto.mantenimiento.unidaddecontrol.MantenimientoUnidadDeControlPK;
import es.rfgr.cr4vegas.modelo.postgresql.dto.mantenimiento.unidaddecontrol.UnidadDeControl;
import es.rfgr.cr4vegas.modelo.postgresql.exceptions.DAOException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;

public class MantenimientoUnidadDeControlDAOImp implements MantenimientoUnidadDeControlDAO {

	private static final String ERROR = "MantenimientoUdControlDAO: ";

	private ObservableList<MantenimientoUnidadDeControl> listaMantenimientosUdControl;

	private MantenimientoUnidadDeControlBD mantenimientoUdControlBD;

	public MantenimientoUnidadDeControlDAOImp() {
		listaMantenimientosUdControl = FXCollections.observableArrayList();
		mantenimientoUdControlBD = new MantenimientoUnidadDeControlBD();
	}

	public void setConnection(Connection conn) {
		mantenimientoUdControlBD.setConnection(conn);
	}

	public ObservableList<MantenimientoUnidadDeControl> getMantenimientosUdControl() {
		return listaMantenimientosUdControl;
	}

	public int getNumMantenimientosUdControl() {
		return listaMantenimientosUdControl.size();
	}

	public MantenimientoUnidadDeControlPK insert(MantenimientoUnidadDeControl dto) throws DAOException {
		MantenimientoUnidadDeControlPK pk = dto.createPK();

		try {
			if (listaMantenimientosUdControl.contains(dto)) {
				throw new OperationsException("El mantenimiento de Ud.Control ya existe.");

			} else {
				pk = mantenimientoUdControlBD.insert(dto);

				this.listaMantenimientosUdControl.add(dto);
			}
		} catch (Exception e) {
			throwException(e);
		}
		return pk;
	}

	public ObservableList<MantenimientoUnidadDeControlPK> insertAll(ObservableList<MantenimientoUnidadDeControl> listToInsert) throws DAOException {
		ObservableList<MantenimientoUnidadDeControlPK> listToReturn = FXCollections.observableArrayList();
		for (MantenimientoUnidadDeControl dto : listToInsert) {
			MantenimientoUnidadDeControlPK dtoPK = insert(dto);
			if (dtoPK != null) {
				listToReturn.add(dtoPK);
			}
		}
		return listToReturn;
	}

	public void update(MantenimientoUnidadDeControlPK pk, MantenimientoUnidadDeControl dto) throws DAOException {
		MantenimientoUnidadDeControl oldMantenimientoUDControl = getOldMantenimientoUDControl(pk);

		try {
			if (pk == null) {
				throw new OperationsException("El mantenimiento de Ud.Control a modificar no puede ser nulo.");
			}
			if (!listaMantenimientosUdControl.contains(oldMantenimientoUDControl)) {
				throw new OperationsException("El mantenimiento de Ud.Control a modificar no existe.");

			} else {
				mantenimientoUdControlBD.delete(pk);

				listaMantenimientosUdControl.set(listaMantenimientosUdControl.indexOf(oldMantenimientoUDControl), dto);
			}
		} catch (Exception e) {
			throwException(e);
		}
	}

	public void updateAll(ObservableMap<MantenimientoUnidadDeControlPK, MantenimientoUnidadDeControl> mapToUpdate)
			throws DAOException {
		for (Map.Entry<MantenimientoUnidadDeControlPK, MantenimientoUnidadDeControl> entry : mapToUpdate.entrySet()) {
			update(entry.getKey(), entry.getValue());
		}
	}

	public void delete(MantenimientoUnidadDeControlPK pk) throws DAOException {
		MantenimientoUnidadDeControl oldMantenimientoUDControl = getOldMantenimientoUDControl(pk);

		try {
			if (pk == null) {
				throw new NullPointerException("No se puede eliminar un mantenimiento de Ud.Control nulo.");
			}
			if (!listaMantenimientosUdControl.contains(oldMantenimientoUDControl)) {
				throw new OperationsException("El mantenimiento de Ud.Control a eliminar no existe.");

			} else {
				mantenimientoUdControlBD.delete(pk);

				listaMantenimientosUdControl.remove(oldMantenimientoUDControl);
			}
		} catch (Exception e) {
			throwException(e);
		}
	}

	public void deleteAll(ObservableList<MantenimientoUnidadDeControl> listToDelete) throws DAOException {
		for (MantenimientoUnidadDeControl dto : listToDelete) {
			delete(dto.createPK());
		}
	}

	public void findAll() throws DAOException {
		try {
			listaMantenimientosUdControl.setAll(mantenimientoUdControlBD.findAll());

		} catch (SQLException e) {
			throwException(e);
		}
	}

	public MantenimientoUnidadDeControl findByPrimaryKey(MantenimientoUnidadDeControlPK pk) throws DAOException {
		try {
			return mantenimientoUdControlBD.findByPrimaryKey(pk);

		} catch (SQLException e) {
			throwException(e);
		}
		return null;
	}
	
	public void setMaxRows(int maxRows) {
		mantenimientoUdControlBD.setMaxRows(maxRows);
	}

	public int getMaxRows() {
		return mantenimientoUdControlBD.getMaxRows();
	}

	public ObservableList<MantenimientoUnidadDeControl> getUdControlMantenimiento(UnidadDeControl dto) {
		ObservableList<MantenimientoUnidadDeControl> listaSalida = FXCollections.observableArrayList();
		for (MantenimientoUnidadDeControl muc : listaMantenimientosUdControl) {
			if (muc.getCodUdControl() == dto.getCodUdControl()) {
				listaSalida.add(muc);
			}
		}
		return listaSalida;
	}

	public void createDependencies(GrupoDAO grupoDAO, UnidadDeControlDAO uDControlDAO) {
		for (MantenimientoUnidadDeControl muc : listaMantenimientosUdControl) {
			muc.setObjGrupo(dependencyMantenimientoUdControlGrupo(muc, grupoDAO));
			muc.setObjUdControl(dependencyMantenimientoUdControlUdControl(muc, uDControlDAO));
		}
	}

	private Grupo dependencyMantenimientoUdControlGrupo(MantenimientoUnidadDeControl dto, GrupoDAO grupoDAO) {
		for (Grupo grupo : grupoDAO.getGrupos()) {
			if (grupo.getNombre().equals(dto.getGrupo())) {
				return grupo;
			}
		}
		return null;
	}

	private UnidadDeControl dependencyMantenimientoUdControlUdControl(MantenimientoUnidadDeControl dto, UnidadDeControlDAO uDControlDAO) {
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

	private MantenimientoUnidadDeControl getOldMantenimientoUDControl(MantenimientoUnidadDeControlPK pk) {
		MantenimientoUnidadDeControl oldMantenimientoUDControl = new MantenimientoUnidadDeControl();
		oldMantenimientoUDControl.setCodMan(pk.getCodMan());
		oldMantenimientoUDControl = listaMantenimientosUdControl
				.get(listaMantenimientosUdControl.indexOf(oldMantenimientoUDControl));
		return oldMantenimientoUDControl;
	}
}
