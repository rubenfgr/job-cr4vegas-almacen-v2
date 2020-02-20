package es.rfgr.cr4vegas.modelo.postgresql.dao.alarma.imp;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

import javax.management.OperationsException;

import es.rfgr.cr4vegas.modelo.postgresql.bd.alarma.AlarmaBD;
import es.rfgr.cr4vegas.modelo.postgresql.dao.alarma.AlarmaDAO;
import es.rfgr.cr4vegas.modelo.postgresql.dao.grupo.GrupoDAO;
import es.rfgr.cr4vegas.modelo.postgresql.dao.operario.OperarioDAO;
import es.rfgr.cr4vegas.modelo.postgresql.dto.alarma.Alarma;
import es.rfgr.cr4vegas.modelo.postgresql.dto.alarma.AlarmaPK;
import es.rfgr.cr4vegas.modelo.postgresql.dto.grupo.Grupo;
import es.rfgr.cr4vegas.modelo.postgresql.dto.operario.Operario;
import es.rfgr.cr4vegas.modelo.postgresql.exceptions.DAOException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;

public class AlarmaDAOImp implements AlarmaDAO {

	private static final String ERROR = "AlarmaDAO: ";

	private ObservableList<Alarma> listaAlarmas;

	private AlarmaBD alarmaBD;

	public AlarmaDAOImp() {
		listaAlarmas = FXCollections.observableArrayList();
		alarmaBD = new AlarmaBD();
	}

	public void setConnection(Connection conn) {
		alarmaBD.setConnection(conn);
	}

	public ObservableList<Alarma> getAlarmas() {
		return listaAlarmas;
	}

	public int getNumAlarmas() {
		return listaAlarmas.size();
	}

	public AlarmaPK insert(Alarma dto) throws DAOException {
		AlarmaPK pk = dto.createPK();

		try {
			if (listaAlarmas.contains(dto)) {
				throw new OperationsException("La alarma ya existe.");

			} else {
				pk = alarmaBD.insert(dto);

				this.listaAlarmas.add(dto);
			}
		} catch (Exception e) {
			throwException(e);
		}
		return pk;
	}

	public ObservableList<AlarmaPK> insertAll(ObservableList<Alarma> listToInsert) throws DAOException {
		ObservableList<AlarmaPK> listToReturn = FXCollections.observableArrayList();
		for (Alarma dto : listToInsert) {
			AlarmaPK dtoPK = insert(dto);
			if (dtoPK != null) {
				listToReturn.add(dtoPK);
			}
		}
		return listToReturn;
	}
	
	public void update(AlarmaPK pk, Alarma dto) throws DAOException {
		Alarma oldAlarma = getOldAlarma(pk);

		try {
			if (dto == null) {
				throw new OperationsException("La alarma a modificar no puede ser nula.");
			}
			if (!listaAlarmas.contains(oldAlarma)) {
				throw new OperationsException("La alarma a modificar no existe.");

			} else {
				alarmaBD.update(pk, dto);

				listaAlarmas.set(listaAlarmas.indexOf(oldAlarma), dto);
			}

		} catch (Exception e) {
			throwException(e);
		}
	}
	
	public void updateAll(ObservableMap<AlarmaPK, Alarma> mapToUpdate) throws DAOException {
		for (Map.Entry<AlarmaPK, Alarma> entry : mapToUpdate.entrySet()) {
			update(entry.getKey(), entry.getValue());
		}
	}

	public void delete(AlarmaPK pk) throws DAOException {
		Alarma oldAlarma = getOldAlarma(pk);

		try {
			if (pk == null) {
				throw new NullPointerException("No se puede eliminar una alarma nula.");
			}
			if (!listaAlarmas.contains(oldAlarma)) {
				throw new OperationsException("La alarma a eliminar no existe.");

			} else {
				alarmaBD.delete(pk);

				listaAlarmas.remove(oldAlarma);
			}
		} catch (Exception e) {
			throwException(e);
		}
	}
	
	public void deleteAll(ObservableList<Alarma> listToDelete) throws DAOException {
		for (Alarma dto : listToDelete) {
			delete(dto.createPK());
		}
	}

	public void findAll() throws DAOException {
		try {
			listaAlarmas.setAll(alarmaBD.findAll());
		} catch (SQLException e) {
			throwException(e);
		}
	}

	public Alarma findByPrimaryKey(AlarmaPK pk) throws DAOException {
		try {
			return alarmaBD.findByPrimaryKey(pk);

		} catch (SQLException e) {
			throwException(e);
		}
		return null;
	}
	
	public void setMaxRows(int maxRows) {
		alarmaBD.setMaxRows(maxRows);
	}

	public int getMaxRows() {
		return alarmaBD.getMaxRows();
	}

	public void createDependencies(GrupoDAO grupoDAO, OperarioDAO operarioDAO) {
		for (Alarma alarma : listaAlarmas) {
			alarma.setObjGrupo(dependencyAlarmaGrupo(alarma, grupoDAO));
			alarma.setObjOperario(dependencyAlarmaOperario(alarma, operarioDAO));
		}
	}

	private Grupo dependencyAlarmaGrupo(Alarma dto, GrupoDAO grupoDAO) {
		for (Grupo grupo : grupoDAO.getGrupos()) {
			if (grupo.getNombre().equals(dto.getGrupo())) {
				return grupo;
			}
		}
		return null;
	}

	private Operario dependencyAlarmaOperario(Alarma dto, OperarioDAO operarioDAO) {
		for (Operario operario : operarioDAO.getOperarios()) {
			if (operario.getCodOp() == dto.getCodOp()) {
				return operario;
			}
		}
		return null;
	}

	private void throwException(Exception e) throws DAOException {
		throw new DAOException(ERROR + e.getMessage(), e);
	}

	private Alarma getOldAlarma(AlarmaPK pk) {
		Alarma oldAlarma = new Alarma();
		oldAlarma.setCodAlarma(pk.getCodAlarma());
		oldAlarma = listaAlarmas.get(listaAlarmas.indexOf(oldAlarma));
		return oldAlarma;
	}
}
