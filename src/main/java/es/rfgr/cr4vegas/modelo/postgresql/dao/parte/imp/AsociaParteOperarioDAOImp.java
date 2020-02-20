package es.rfgr.cr4vegas.modelo.postgresql.dao.parte.imp;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

import javax.management.OperationsException;

import es.rfgr.cr4vegas.modelo.postgresql.bd.parte.AsociaParteOperarioBD;
import es.rfgr.cr4vegas.modelo.postgresql.dao.operario.OperarioDAO;
import es.rfgr.cr4vegas.modelo.postgresql.dao.parte.AsociaParteOperarioDAO;
import es.rfgr.cr4vegas.modelo.postgresql.dao.parte.ParteDAO;
import es.rfgr.cr4vegas.modelo.postgresql.dto.operario.Operario;
import es.rfgr.cr4vegas.modelo.postgresql.dto.parte.AsociaParteOperario;
import es.rfgr.cr4vegas.modelo.postgresql.dto.parte.AsociaParteOperarioPK;
import es.rfgr.cr4vegas.modelo.postgresql.dto.parte.Parte;
import es.rfgr.cr4vegas.modelo.postgresql.exceptions.DAOException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;

public class AsociaParteOperarioDAOImp implements AsociaParteOperarioDAO {

	private static final String ERROR = "AsociaParteOperarioDAO: ";

	private ObservableList<AsociaParteOperario> listaAsociaParteOperario;

	private AsociaParteOperarioBD asociaParteOperarioBD;

	public AsociaParteOperarioDAOImp() {
		listaAsociaParteOperario = FXCollections.observableArrayList();
		asociaParteOperarioBD = new AsociaParteOperarioBD();
	}

	public void setConnection(Connection conn) {
		asociaParteOperarioBD.setConnection(conn);
	}

	public ObservableList<AsociaParteOperario> getAsociaParteOperario() {
		return listaAsociaParteOperario;
	}

	public int getNumAsociaParteOperario() {
		return listaAsociaParteOperario.size();
	}

	public AsociaParteOperarioPK insert(AsociaParteOperario dto) throws DAOException {
		AsociaParteOperarioPK pk = dto.createPK();

		try {
			if (listaAsociaParteOperario.contains(dto)) {
				throw new OperationsException("El parte-operario ya existe.");
			} else {
				pk = asociaParteOperarioBD.insert(dto);

				this.listaAsociaParteOperario.add(dto);
			}
		} catch (Exception e) {
			throwException(e);
		}
		return pk;
	}
	
	public ObservableList<AsociaParteOperarioPK> insertAll(ObservableList<AsociaParteOperario> listToInsert) throws DAOException {
		ObservableList<AsociaParteOperarioPK> listToReturn = FXCollections.observableArrayList();
		for (AsociaParteOperario dto : listToInsert) {
			AsociaParteOperarioPK dtoPK = insert(dto);
			if (dtoPK != null) {
				listToReturn.add(dtoPK);
			}
		}
		return listToReturn;
	}

	public void update(AsociaParteOperarioPK pk, AsociaParteOperario dto) throws DAOException {
		AsociaParteOperario oldAsociaParteOperario = getOldAsociaParteOperario(pk);

		try {
			if (dto == null) {
				throw new OperationsException("El parte-operario a modificar no puede ser nulo.");
			}
			if (!listaAsociaParteOperario.contains(oldAsociaParteOperario)) {
				throw new OperationsException("El parte-operario a modificar no existe.");

			} else {
				asociaParteOperarioBD.update(pk, dto);

				listaAsociaParteOperario.set(listaAsociaParteOperario.indexOf(oldAsociaParteOperario), dto);
			}
		} catch (Exception e) {
			throwException(e);
		}
	}
	
	public void updateAll(ObservableMap<AsociaParteOperarioPK, AsociaParteOperario> mapToUpdate) throws DAOException {
		for (Map.Entry<AsociaParteOperarioPK, AsociaParteOperario> entry : mapToUpdate.entrySet()) {
			update(entry.getKey(), entry.getValue());
		}
	}

	public void delete(AsociaParteOperarioPK pk) throws DAOException {
		AsociaParteOperario oldAsociaParteOperario = getOldAsociaParteOperario(pk);

		try {
			if (pk == null) {
				throw new NullPointerException("No se puede eliminar un parte-operario nulo.");
			}
			if (!listaAsociaParteOperario.contains(oldAsociaParteOperario)) {
				throw new OperationsException("El parte-operario a eliminar no existe.");

			} else {
				asociaParteOperarioBD.delete(pk);

				listaAsociaParteOperario.remove(oldAsociaParteOperario);
			}
		} catch (Exception e) {
			throwException(e);
		}
	}

	public void deleteAll(ObservableList<AsociaParteOperario> listToDelete) throws DAOException {
		for (AsociaParteOperario dto : listToDelete) {
			delete(dto.createPK());
		}
	}

	public void findAll() throws DAOException {
		try {
			listaAsociaParteOperario.setAll(asociaParteOperarioBD.findAll());

		} catch (SQLException e) {
			throwException(e);
		}
	}

	public AsociaParteOperario findByPrimaryKey(AsociaParteOperarioPK pk) throws DAOException {
		try {
			return asociaParteOperarioBD.findByPrimaryKey(pk);

		} catch (SQLException e) {
			throwException(e);
		}
		return null;
	}
	
	public void setMaxRows(int maxRows) {
		asociaParteOperarioBD.setMaxRows(maxRows);
	}

	public int getMaxRows() {
		return asociaParteOperarioBD.getMaxRows();
	}
	
	public ObservableList<AsociaParteOperario> getParteOperarios(Parte dto) {
		ObservableList<AsociaParteOperario> listToReturn = FXCollections.observableArrayList();
		for(AsociaParteOperario dtoOnList : listaAsociaParteOperario) {
			if (dtoOnList.getCodParte() == dto.getCodParte()) {
				listToReturn.add(dtoOnList);
			}
		}
		return listToReturn;
	}

	public void createDependencies(ParteDAO parteDAO, OperarioDAO operarioDAO) {
		for (AsociaParteOperario apo : listaAsociaParteOperario) {
			apo.setObjOperario(dependencyAsociaParteOperarioOperario(apo, operarioDAO));
			apo.setObjParte(dependencyAsociaParteOperarioParte(apo, parteDAO));
		}
	}

	private Parte dependencyAsociaParteOperarioParte(AsociaParteOperario dto, ParteDAO parteDAO) {
		Parte parteSalida = null;
		for (Parte parte : parteDAO.getPartes()) {
			if (parte.getCodParte() == dto.getCodParte()) {
				parteSalida = parte;
			}
		}
		return parteSalida;
	}

	private Operario dependencyAsociaParteOperarioOperario(AsociaParteOperario dto, OperarioDAO operarioDAO) {
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

	private AsociaParteOperario getOldAsociaParteOperario(AsociaParteOperarioPK pk) {
		AsociaParteOperario oldAsociaParteOperario = new AsociaParteOperario();
		oldAsociaParteOperario.setCodParte(pk.getCodParte());
		oldAsociaParteOperario.setCodOp(pk.getCodOp());
		oldAsociaParteOperario = listaAsociaParteOperario.get(listaAsociaParteOperario.indexOf(oldAsociaParteOperario));
		return oldAsociaParteOperario;
	}
}
