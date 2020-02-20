package es.rfgr.cr4vegas.modelo.postgresql.dao.operario.imp;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

import javax.management.OperationsException;

import es.rfgr.cr4vegas.modelo.postgresql.bd.operario.TelOperarioBD;
import es.rfgr.cr4vegas.modelo.postgresql.dao.operario.OperarioDAO;
import es.rfgr.cr4vegas.modelo.postgresql.dao.operario.TelOperarioDAO;
import es.rfgr.cr4vegas.modelo.postgresql.dto.operario.Operario;
import es.rfgr.cr4vegas.modelo.postgresql.dto.operario.TelOperario;
import es.rfgr.cr4vegas.modelo.postgresql.dto.operario.TelOperarioPK;
import es.rfgr.cr4vegas.modelo.postgresql.exceptions.DAOException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;

public class TelOperarioDAOImp implements TelOperarioDAO {

	private static final String ERROR = "TelOperarioDAO: ";

	private ObservableList<TelOperario> listaTelOperarios;

	private TelOperarioBD telOperarioBD;

	public TelOperarioDAOImp() {
		listaTelOperarios = FXCollections.observableArrayList();
		telOperarioBD = new TelOperarioBD();
	}

	public void setConnection(Connection conn) {
		telOperarioBD.setConnection(conn);
	}

	public ObservableList<TelOperario> getTelOperarios() {
		return listaTelOperarios;
	}

	public int getNumTelOperarios() {
		return listaTelOperarios.size();
	}

	public TelOperarioPK insert(TelOperario dto) throws DAOException {
		TelOperarioPK pk = dto.createPK();

		try {
			if (listaTelOperarios.contains(dto)) {
				throw new OperationsException("El teléfono de operario ya existe.");

			} else {
				pk = telOperarioBD.insert(dto);

				this.listaTelOperarios.add(dto);
			}
		} catch (Exception e) {
			throwException(e);
		}
		return pk;
	}

	public ObservableList<TelOperarioPK> insertAll(ObservableList<TelOperario> listToInsert) throws DAOException {
		ObservableList<TelOperarioPK> listToReturn = FXCollections.observableArrayList();
		for (TelOperario dto : listToInsert) {
			TelOperarioPK dtoPK = insert(dto);
			if (dtoPK != null) {
				listToReturn.add(dtoPK);
			}
		}
		return listToReturn;
	}

	public void update(TelOperarioPK pk, TelOperario dto) throws DAOException {
		TelOperario oldTelOperario = getOldTelOperario(pk);

		try {
			if (dto == null) {
				throw new OperationsException("El teléfono de operario a modificar no puede ser nulo.");
			}
			if (!listaTelOperarios.contains(oldTelOperario)) {
				throw new OperationsException("El teléfono de operario a modificar no existe.");

			} else {
				telOperarioBD.update(pk, dto);

				listaTelOperarios.set(listaTelOperarios.indexOf(oldTelOperario), dto);
			}
		} catch (Exception e) {
			throwException(e);
		}
	}

	public void updateAll(ObservableMap<TelOperarioPK, TelOperario> mapToUpdate) throws DAOException {
		for (Map.Entry<TelOperarioPK, TelOperario> entry : mapToUpdate.entrySet()) {
			update(entry.getKey(), entry.getValue());
		}
	}

	public void delete(TelOperarioPK pk) throws DAOException {
		TelOperario oldTelOperario = getOldTelOperario(pk);

		try {
			if (pk == null) {
				throw new NullPointerException("No se puede eliminar un teléfono de operario nulo.");
			}
			if (!listaTelOperarios.contains(oldTelOperario)) {
				throw new OperationsException("El teléfono de operario a eliminar no existe.");

			} else {
				telOperarioBD.delete(pk);

				listaTelOperarios.remove(oldTelOperario);
			}
		} catch (Exception e) {
			throwException(e);
		}
	}

	public void deleteAll(ObservableList<TelOperario> listToDelete) throws DAOException {
		for (TelOperario dto : listToDelete) {
			delete(dto.createPK());
		}
	}

	public void findAll() throws DAOException {
		try {
			listaTelOperarios.setAll(telOperarioBD.findAll());

		} catch (SQLException e) {
			throwException(e);
		}
	}

	public TelOperario findByPrimaryKey(TelOperarioPK pk) throws DAOException {
		try {
			return telOperarioBD.findByPrimaryKey(pk);

		} catch (SQLException e) {
			throwException(e);
		}
		return null;
	}
	
	public void setMaxRows(int maxRows) {
		telOperarioBD.setMaxRows(maxRows);
	}

	public int getMaxRows() {
		return telOperarioBD.getMaxRows();
	}
	
	public ObservableList<TelOperario> getOperarioTelefonos(Operario dto) {
		ObservableList<TelOperario> listToReturn = FXCollections.observableArrayList();
		for (TelOperario dtoOnList : listaTelOperarios) {
			if (dtoOnList.getCodOp() == dto.getCodOp()) {
				listToReturn.add(dtoOnList);
			}
		}
		return listToReturn;
	}

	public void createDependencies(OperarioDAO operarioDAO) {
		for (TelOperario telOperario : listaTelOperarios) {
			telOperario.setObjOperario(dependencyTelOperario(telOperario, operarioDAO));
		}
	}

	private Operario dependencyTelOperario(TelOperario dto, OperarioDAO operarioDAO) {
		Operario operarioSalida = null;
		for (Operario operario : operarioDAO.getOperarios()) {
			if (operario.getCodOp() == dto.getCodOp()) {
				operarioSalida = operario;
			}
		}
		return operarioSalida;
	}

	private void throwException(Exception e) throws DAOException {
		throw new DAOException(ERROR + e.getMessage(), e);
	}

	private TelOperario getOldTelOperario(TelOperarioPK pk) {
		TelOperario oldTelOperario = new TelOperario();
		oldTelOperario.setTelefono(pk.getTelefono());
		oldTelOperario = listaTelOperarios.get(listaTelOperarios.indexOf(oldTelOperario));
		return oldTelOperario;
	}
}
