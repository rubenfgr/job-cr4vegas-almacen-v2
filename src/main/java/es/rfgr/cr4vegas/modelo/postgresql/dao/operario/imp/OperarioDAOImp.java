package es.rfgr.cr4vegas.modelo.postgresql.dao.operario.imp;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

import javax.management.OperationsException;

import es.rfgr.cr4vegas.modelo.postgresql.bd.operario.OperarioBD;
import es.rfgr.cr4vegas.modelo.postgresql.dao.grupo.GrupoDAO;
import es.rfgr.cr4vegas.modelo.postgresql.dao.operario.OperarioDAO;
import es.rfgr.cr4vegas.modelo.postgresql.dto.grupo.Grupo;
import es.rfgr.cr4vegas.modelo.postgresql.dto.operario.Operario;
import es.rfgr.cr4vegas.modelo.postgresql.dto.operario.OperarioPK;
import es.rfgr.cr4vegas.modelo.postgresql.exceptions.DAOException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;

public class OperarioDAOImp implements OperarioDAO {

	private static final String ERROR = "OperarioDAO: ";

	private ObservableList<Operario> listaOperarios;

	private OperarioBD operarioBD;

	public OperarioDAOImp() {
		listaOperarios = FXCollections.observableArrayList();
		operarioBD = new OperarioBD();
	}

	public void setConnection(Connection conn) {
		operarioBD.setConnection(conn);
	}

	public ObservableList<Operario> getOperarios() {
		return listaOperarios;
	}

	public int getNumOperarios() {
		return listaOperarios.size();
	}

	public OperarioPK insert(Operario dto) throws DAOException {
		OperarioPK pk = dto.createPK();

		try {
			if (listaOperarios.contains(dto)) {
				throw new OperationsException("El operario ya existe.");
			} else {
				pk = operarioBD.insert(dto);

				listaOperarios.add(dto);
			}
		} catch (Exception e) {
			throwException(e);
		}
		return pk;
	}

	public ObservableList<OperarioPK> insertAll(ObservableList<Operario> listToInsert) throws DAOException {
		ObservableList<OperarioPK> listToReturn = FXCollections.observableArrayList();
		for (Operario dto : listToInsert) {
			OperarioPK dtoPK = insert(dto);
			if (dtoPK != null) {
				listToReturn.add(dtoPK);
			}
		}
		return listToReturn;
	}

	public void update(OperarioPK pk, Operario dto) throws DAOException {
		Operario oldOperario = getOldOperario(pk);

		try {
			if (dto == null) {
				throw new NullPointerException("No se puede modificar un operario nulo.");
			}
			if (!listaOperarios.contains(dto)) {
				throw new OperationsException("El operario a modificar NO existe.");

			} else {
				operarioBD.update(pk, dto);
				oldOperario.copiarValores(dto);
				System.out.println("método update: " + oldOperario);
			}
		} catch (Exception e) {
			throwException(e);
		}
	}

	public void updateAll(ObservableMap<OperarioPK, Operario> mapToUpdate) throws DAOException {
		for (Map.Entry<OperarioPK, Operario> entry : mapToUpdate.entrySet()) {
			update(entry.getKey(), entry.getValue());
		}
	}

	public void delete(OperarioPK pk) throws DAOException {
		Operario oldOperario = getOldOperario(pk);

		try {
			if (pk == null) {
				throw new NullPointerException("No se puede eliminar un operario nulo.");
			}
			if (!listaOperarios.contains(oldOperario)) {
				throw new OperationsException("El operario a eliminar no existe.");

			} else {
				operarioBD.delete(pk);

				listaOperarios.remove(oldOperario);
			}
		} catch (Exception e) {
			throwException(e);
		}
	}

	public void deleteAll(ObservableList<Operario> listToDelete) throws DAOException {
		for (Operario dto : listToDelete) {
			delete(dto.createPK());
		}
	}

	public void findAll() throws DAOException {
		try {
			listaOperarios.setAll(operarioBD.findAll());

		} catch (SQLException e) {
			throwException(e);
		}
	}

	public Operario findByPrimaryKey(OperarioPK pk) throws DAOException {
		try {
			return operarioBD.findByPrimaryKey(pk);

		} catch (SQLException e) {
			throwException(e);
		}
		return null;
	}

	public void setMaxRows(int maxRows) {
		operarioBD.setMaxRows(maxRows);
	}

	public int getMaxRows() {
		return operarioBD.getMaxRows();
	}

	public void createDependencies(GrupoDAO grupoDAO) {
		for (Operario operario : listaOperarios) {
			dependencyOperarioGrupo(operario, grupoDAO);
		}
	}

	public void dependencyOperarioGrupo(Operario dto, GrupoDAO grupoDAO) {
		for (Grupo grupo : grupoDAO.getGrupos()) {
			if (grupo.getNombre().equals(dto.getGrupo())) {
				dto.setObjGrupo(grupo);
			}
		}
	}

	private void throwException(Exception e) throws DAOException {
		throw new DAOException(ERROR + e.getMessage(), e);
	}

	private Operario getOldOperario(OperarioPK pk) {
		Operario oldOperario = new Operario();
		oldOperario.setCodOp(pk.getCodOp());
		oldOperario = listaOperarios.get(listaOperarios.indexOf(oldOperario));
		System.out.println("método getOldOperario: " + oldOperario);
		return oldOperario;
	}
}
