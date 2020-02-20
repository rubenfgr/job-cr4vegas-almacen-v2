package es.rfgr.cr4vegas.modelo.postgresql.dao.operario.imp;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

import javax.management.OperationsException;

import es.rfgr.cr4vegas.modelo.postgresql.bd.operario.CreaOperarioParteBD;
import es.rfgr.cr4vegas.modelo.postgresql.dao.operario.CreaOperarioParteDAO;
import es.rfgr.cr4vegas.modelo.postgresql.dao.operario.OperarioDAO;
import es.rfgr.cr4vegas.modelo.postgresql.dao.parte.ParteDAO;
import es.rfgr.cr4vegas.modelo.postgresql.dto.operario.CreaOperarioParte;
import es.rfgr.cr4vegas.modelo.postgresql.dto.operario.CreaOperarioPartePK;
import es.rfgr.cr4vegas.modelo.postgresql.dto.operario.Operario;
import es.rfgr.cr4vegas.modelo.postgresql.dto.parte.Parte;
import es.rfgr.cr4vegas.modelo.postgresql.exceptions.DAOException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;

public class CreaOperarioParteDAOImp implements CreaOperarioParteDAO {

	private static final String ERROR = "CreaOperarioParteDAO: ";

	private ObservableList<CreaOperarioParte> listaCreaOperarioParte;

	private CreaOperarioParteBD creaOperarioParteBD;

	public CreaOperarioParteDAOImp() {
		listaCreaOperarioParte = FXCollections.observableArrayList();
		creaOperarioParteBD = new CreaOperarioParteBD();
	}

	public void setConnection(Connection conn) {
		creaOperarioParteBD.setConnection(conn);
	}

	public ObservableList<CreaOperarioParte> getCreaOperarioParte() {
		return listaCreaOperarioParte;
	}

	public int getNumCreaOperarioParte() {
		return listaCreaOperarioParte.size();
	}

	public CreaOperarioPartePK insert(CreaOperarioParte dto) throws DAOException {
		CreaOperarioPartePK pk = dto.createPK();

		try {
			if (listaCreaOperarioParte.contains(dto)) {
				throw new OperationsException("El operario-parte ya existe.");

			} else {
				pk = creaOperarioParteBD.insert(dto);

				this.listaCreaOperarioParte.add(dto);
			}
		} catch (Exception e) {
			throwException(e);
		}
		return pk;
	}

	public ObservableList<CreaOperarioPartePK> insertAll(ObservableList<CreaOperarioParte> listToInsert) throws DAOException {
		ObservableList<CreaOperarioPartePK> listToReturn = FXCollections.observableArrayList();
		for (CreaOperarioParte dto : listToInsert) {
			CreaOperarioPartePK dtoPK = insert(dto);
			if (dtoPK != null) {
				listToReturn.add(dtoPK);
			}
		}
		return listToReturn;
	}

	public void update(CreaOperarioPartePK pk, CreaOperarioParte dto) throws DAOException {
		CreaOperarioParte oldCreaOperarioParte = getOldCreaOperarioParte(pk);

		try {
			if (dto == null) {
				throw new OperationsException("El operario-parte a modificar no puede ser nula.");
			} else {
				if (!listaCreaOperarioParte.contains(oldCreaOperarioParte)) {
					throw new OperationsException("El operario-parte a modificar no existe.");

				} else {
					creaOperarioParteBD.update(pk, dto);

					listaCreaOperarioParte.set(listaCreaOperarioParte.indexOf(oldCreaOperarioParte), dto);
				}
			}
		} catch (Exception e) {
			throwException(e);
		}
	}

	public void updateAll(ObservableMap<CreaOperarioPartePK, CreaOperarioParte> mapToUpdate) throws DAOException {
		for (Map.Entry<CreaOperarioPartePK, CreaOperarioParte> entry : mapToUpdate.entrySet()) {
			update(entry.getKey(), entry.getValue());
		}
	}

	public void delete(CreaOperarioPartePK pk) throws DAOException {
		CreaOperarioParte oldCreaOperarioParte = getOldCreaOperarioParte(pk);

		try {
			if (pk == null) {
				throw new NullPointerException("No se puede eliminar un operario-parte nulo.");
			}
			if (!listaCreaOperarioParte.contains(oldCreaOperarioParte)) {
				throw new OperationsException("El operario-parte a eliminar no existe.");

			} else {
				creaOperarioParteBD.delete(pk);

				listaCreaOperarioParte.remove(oldCreaOperarioParte);
			}
		} catch (Exception e) {
			throwException(e);
		}
	}

	public void deleteAll(ObservableList<CreaOperarioParte> listToDelete) throws DAOException {
		for (CreaOperarioParte dto : listToDelete) {
			delete(dto.createPK());
		}
	}

	public void findAll() throws DAOException {
		try {
			listaCreaOperarioParte.setAll(creaOperarioParteBD.findAll());

		} catch (SQLException e) {
			throwException(e);
		}
	}

	public CreaOperarioParte findByPrimaryKey(CreaOperarioPartePK pk) throws DAOException {
		try {
			return creaOperarioParteBD.findByPrimaryKey(pk);

		} catch (SQLException e) {
			throwException(e);
		}
		return null;
	}
	
	public void setMaxRows(int maxRows) {
		creaOperarioParteBD.setMaxRows(maxRows);
	}

	public int getMaxRows() {
		return creaOperarioParteBD.getMaxRows();
	}
	
	public ObservableList<CreaOperarioParte> getOperarioPartes(Operario dto) {
		ObservableList<CreaOperarioParte> listToReturn = FXCollections.observableArrayList();
		for (CreaOperarioParte dtoOnList : listaCreaOperarioParte) {
			if (dtoOnList.getCodOp() == dto.getCodOp()) {
				listToReturn.add(dtoOnList);
			}
		}
		return listToReturn;
	}

	public void createDependencies(OperarioDAO operarioDAO, ParteDAO parteDAO) {
		for (CreaOperarioParte dtoOnList : listaCreaOperarioParte) {
			dtoOnList.setObjOperario(dependencyCreaOperarioParteOperario(dtoOnList, operarioDAO));
			dtoOnList.setObjParte(dependencyCreaOperarioParteParte(dtoOnList, parteDAO));
		}
	}

	private Operario dependencyCreaOperarioParteOperario(CreaOperarioParte dto, OperarioDAO operarioDAO) {
		for (Operario operario : operarioDAO.getOperarios()) {
			if (operario.getCodOp() == dto.getCodOp()) {
				return operario;
			}
		}
		return null;
	}

	private Parte dependencyCreaOperarioParteParte(CreaOperarioParte dto, ParteDAO parteDAO) {
		for (Parte parte : parteDAO.getPartes()) {
			if (parte.getCodParte() == dto.getCodParte()) {
				return parte;
			}
		}
		return null;
	}

	private void throwException(Exception e) throws DAOException {
		throw new DAOException(ERROR + e.getMessage(), e);
	}

	private CreaOperarioParte getOldCreaOperarioParte(CreaOperarioPartePK pk) {
		CreaOperarioParte oldCreaOperarioParte = new CreaOperarioParte();
		oldCreaOperarioParte.setCodOp(pk.getCodOp());
		oldCreaOperarioParte.setCodParte(pk.getCodParte());
		oldCreaOperarioParte = listaCreaOperarioParte.get(listaCreaOperarioParte.indexOf(oldCreaOperarioParte));
		return oldCreaOperarioParte;
	}

}