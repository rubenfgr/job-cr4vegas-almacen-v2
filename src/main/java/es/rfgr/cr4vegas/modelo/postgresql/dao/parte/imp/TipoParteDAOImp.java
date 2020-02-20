package es.rfgr.cr4vegas.modelo.postgresql.dao.parte.imp;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

import javax.management.OperationsException;

import es.rfgr.cr4vegas.modelo.postgresql.bd.parte.TipoParteBD;
import es.rfgr.cr4vegas.modelo.postgresql.dao.parte.TipoParteDAO;
import es.rfgr.cr4vegas.modelo.postgresql.dto.parte.TipoParte;
import es.rfgr.cr4vegas.modelo.postgresql.dto.parte.TipoPartePK;
import es.rfgr.cr4vegas.modelo.postgresql.exceptions.DAOException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;

public class TipoParteDAOImp implements TipoParteDAO {

	private static final String ERROR = "TipoParteDAO: ";

	private ObservableList<TipoParte> listaTiposParte;

	private TipoParteBD tipoParteBD;

	public TipoParteDAOImp() {
		listaTiposParte = FXCollections.observableArrayList();
		tipoParteBD = new TipoParteBD();
	}

	public void setConnection(Connection conn) {
		tipoParteBD.setConnection(conn);
	}

	public ObservableList<TipoParte> getTiposParte() {
		return listaTiposParte;
	}

	public int getNumTiposParte() {
		return listaTiposParte.size();
	}

	public TipoPartePK insert(TipoParte dto) throws DAOException {
		TipoPartePK pk = dto.createPK();

		try {
			if (listaTiposParte.contains(dto)) {
				throw new OperationsException("El tipo de parte ya existe.");
			} else {
				pk = tipoParteBD.insert(dto);

				this.listaTiposParte.add(dto);
			}
		} catch (Exception e) {
			throwException(e);
		}
		return pk;
	}
	
	public ObservableList<TipoPartePK> insertAll(ObservableList<TipoParte> listToInsert) throws DAOException {
		ObservableList<TipoPartePK> listToReturn = FXCollections.observableArrayList();
		for (TipoParte dto : listToInsert) {
			TipoPartePK dtoPK = insert(dto);
			if (dtoPK != null) {
				listToReturn.add(dtoPK);
			}
		}
		return listToReturn;
	}

	public void update(TipoPartePK pk, TipoParte dto) throws DAOException {
		TipoParte oldTipoParte = getOldTipoParte(pk);

		try {
			if (dto == null) {
				throw new OperationsException("El tipo de parte a modificar no puede ser nulo.");
			}
			if (!listaTiposParte.contains(dto)) {
				throw new OperationsException("El tipo de parte a modificar no existe.");

			} else {
				tipoParteBD.update(pk, dto);

				listaTiposParte.set(listaTiposParte.indexOf(oldTipoParte), dto);
			}
		} catch (Exception e) {
			throwException(e);
		}
	}
	
	public void updateAll(ObservableMap<TipoPartePK, TipoParte> mapToUpdate) throws DAOException {
		for (Map.Entry<TipoPartePK, TipoParte> entry : mapToUpdate.entrySet()) {
			update(entry.getKey(), entry.getValue());
		}
	}

	public void delete(TipoPartePK pk) throws DAOException {
		TipoParte oldTipoParte = getOldTipoParte(pk);

		try {
			if (pk == null) {
				throw new NullPointerException("No se puede eliminar un tipo de parte nulo.");
			}
			if (!listaTiposParte.contains(oldTipoParte)) {
				throw new OperationsException("El tipo de parte a eliminar no existe.");

			} else {
				tipoParteBD.delete(pk);

				listaTiposParte.remove(oldTipoParte);
			}
		} catch (Exception e) {
			throwException(e);
		}
	}
	
	public void deleteAll(ObservableList<TipoParte> listToDelete) throws DAOException {
		for (TipoParte dto : listToDelete) {
			delete(dto.createPK());
		}
	}

	public void findAll() throws DAOException {
		try {
			listaTiposParte.setAll(tipoParteBD.findAll());

		} catch (SQLException e) {
			throwException(e);
		}
	}

	public TipoParte findByPrimaryKey(TipoPartePK pk) throws DAOException {
		try {
			return tipoParteBD.findByPrimaryKey(pk);

		} catch (SQLException e) {
			throwException(e);
		}
		return null;
	}
	
	public void setMaxRows(int maxRows) {
		tipoParteBD.setMaxRows(maxRows);
	}

	public int getMaxRows() {
		return tipoParteBD.getMaxRows();
	}

	private void throwException(Exception e) throws DAOException {
		throw new DAOException(ERROR + e.getMessage(), e);
	}

	private TipoParte getOldTipoParte(TipoPartePK pk) {
		TipoParte oldTipoParte = new TipoParte();
		oldTipoParte.setCodigo(pk.getCodigo());
		oldTipoParte = listaTiposParte.get(listaTiposParte.indexOf(oldTipoParte));
		return oldTipoParte;
	}

}
