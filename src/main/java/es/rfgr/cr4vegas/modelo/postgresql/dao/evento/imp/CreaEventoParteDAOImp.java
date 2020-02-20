package es.rfgr.cr4vegas.modelo.postgresql.dao.evento.imp;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

import javax.management.OperationsException;

import es.rfgr.cr4vegas.modelo.postgresql.bd.evento.CreaEventoParteBD;
import es.rfgr.cr4vegas.modelo.postgresql.dao.evento.CreaEventoParteDAO;
import es.rfgr.cr4vegas.modelo.postgresql.dao.evento.EventoDAO;
import es.rfgr.cr4vegas.modelo.postgresql.dao.parte.ParteDAO;
import es.rfgr.cr4vegas.modelo.postgresql.dto.evento.CreaEventoParte;
import es.rfgr.cr4vegas.modelo.postgresql.dto.evento.CreaEventoPartePK;
import es.rfgr.cr4vegas.modelo.postgresql.dto.evento.Evento;
import es.rfgr.cr4vegas.modelo.postgresql.dto.parte.Parte;
import es.rfgr.cr4vegas.modelo.postgresql.exceptions.DAOException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;

public class CreaEventoParteDAOImp implements CreaEventoParteDAO {

	private static final String ERROR = "CreaEventoParteDAO: ";

	private ObservableList<CreaEventoParte> listaCreaEventoParte;

	private CreaEventoParteBD creaEventoParteBD;

	public CreaEventoParteDAOImp() {
		listaCreaEventoParte = FXCollections.observableArrayList();
		creaEventoParteBD = new CreaEventoParteBD();
	}

	public void setConnection(Connection conn) {
		creaEventoParteBD.setConnection(conn);
	}

	public ObservableList<CreaEventoParte> getCreaEventoParte() {
		return listaCreaEventoParte;
	}

	public int getNumCreaEventoParte() {
		return listaCreaEventoParte.size();
	}

	public CreaEventoPartePK insert(CreaEventoParte dto) throws DAOException {
		CreaEventoPartePK pk = dto.createPK();

		try {
			if (listaCreaEventoParte.contains(dto)) {
				throw new OperationsException("El evento-parte ya existe.");

			} else {
				pk = creaEventoParteBD.insert(dto);

				this.listaCreaEventoParte.add(dto);
			}
		} catch (Exception e) {
			throwException(e);
		}
		return pk;
	}

	public ObservableList<CreaEventoPartePK> insertAll(ObservableList<CreaEventoParte> listToInsert) throws DAOException {
		ObservableList<CreaEventoPartePK> listToReturn = FXCollections.observableArrayList();
		for (CreaEventoParte dto : listToInsert) {
			CreaEventoPartePK dtoPK = insert(dto);
			if (dtoPK != null) {
				listToReturn.add(dtoPK);
			}
		}
		return listToReturn;
	}

	public void update(CreaEventoPartePK pk, CreaEventoParte dto) throws DAOException {
		CreaEventoParte oldCreaEventoParte = getOldCreaEventoParte(pk);

		try {
			if (dto == null) {
				throw new OperationsException("El evento-parte a modificar no puede ser nulo.");
			}
			if (!listaCreaEventoParte.contains(oldCreaEventoParte)) {
				throw new OperationsException("El evento-parte a modificar no existe.");

			} else {
				creaEventoParteBD.update(pk, dto);

				listaCreaEventoParte.set(listaCreaEventoParte.indexOf(oldCreaEventoParte), dto);
			}
		} catch (Exception e) {
			throwException(e);
		}
	}

	public void updateAll(ObservableMap<CreaEventoPartePK, CreaEventoParte> mapToUpdate) throws DAOException {
		for (Map.Entry<CreaEventoPartePK, CreaEventoParte> entry : mapToUpdate.entrySet()) {
			update(entry.getKey(), entry.getValue());
		}
	}

	public void delete(CreaEventoPartePK pk) throws DAOException {
		CreaEventoParte oldCreaEventoParte = getOldCreaEventoParte(pk);

		try {
			if (pk == null) {
				throw new NullPointerException("No se puede eliminar un evento-parte nulo.");
			}
			if (!listaCreaEventoParte.contains(oldCreaEventoParte)) {
				throw new OperationsException("El evento-parte a eliminar no existe.");

			} else {
				creaEventoParteBD.delete(pk);

				listaCreaEventoParte.remove(oldCreaEventoParte);
			}
		} catch (Exception e) {
			throwException(e);
		}
	}

	public void deleteAll(ObservableList<CreaEventoParte> listToDelete) throws DAOException {
		for (CreaEventoParte dto : listToDelete) {
			delete(dto.createPK());
		}
	}

	public void findAll() throws DAOException {
		try {
			listaCreaEventoParte.setAll(creaEventoParteBD.findAll());

		} catch (SQLException e) {
			throwException(e);
		}
	}

	public CreaEventoParte findByPrimaryKey(CreaEventoPartePK pk) throws DAOException {
		try {
			return creaEventoParteBD.findByPrimaryKey(pk);

		} catch (SQLException e) {
			throwException(e);
		}
		return null;
	}

	public void setMaxRows(int maxRows) {
		creaEventoParteBD.setMaxRows(maxRows);
	}

	public int getMaxRows() {
		return creaEventoParteBD.getMaxRows();
	}
	
	public CreaEventoParte getEventoCreaEventoParte(Evento dto) {
		for (CreaEventoParte dtoOnList : listaCreaEventoParte) {
			if (dtoOnList.getCodEvento() == dto.getCodEvento()) {
				return dtoOnList;
			}
		}
		return null;
	}

	public void createDependencies(EventoDAO eventoDAO, ParteDAO parteDAO) {
		for (CreaEventoParte cep : listaCreaEventoParte) {
			cep.setObjEvento(dependencyCreaEventoParteEvento(cep, eventoDAO));
			cep.setObjParte(dependencyCreaEventoParteParte(cep, parteDAO));
		}
	}

	private Evento dependencyCreaEventoParteEvento(CreaEventoParte dto, EventoDAO eventoDAO) {
		for (Evento evento : eventoDAO.getEventos()) {
			if (evento.getCodEvento() == dto.getCodEvento()) {
				return evento;
			}
		}
		return null;
	}

	private Parte dependencyCreaEventoParteParte(CreaEventoParte dto, ParteDAO parteDAO) {
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

	private CreaEventoParte getOldCreaEventoParte(CreaEventoPartePK pk) {
		CreaEventoParte oldCreaEventoParte = new CreaEventoParte();
		oldCreaEventoParte.setCodEvento(pk.getCodEvento());
		oldCreaEventoParte = listaCreaEventoParte.get(listaCreaEventoParte.indexOf(oldCreaEventoParte));
		return oldCreaEventoParte;
	}
}
