package es.rfgr.cr4vegas.modelo.postgresql.dao.parte.imp;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

import javax.management.OperationsException;

import es.rfgr.cr4vegas.modelo.postgresql.bd.parte.ParteBD;
import es.rfgr.cr4vegas.modelo.postgresql.dao.grupo.GrupoDAO;
import es.rfgr.cr4vegas.modelo.postgresql.dao.parte.ParteDAO;
import es.rfgr.cr4vegas.modelo.postgresql.dao.parte.TipoParteDAO;
import es.rfgr.cr4vegas.modelo.postgresql.dao.parteoficial.ParteOficialDAO;
import es.rfgr.cr4vegas.modelo.postgresql.dto.grupo.Grupo;
import es.rfgr.cr4vegas.modelo.postgresql.dto.parte.Parte;
import es.rfgr.cr4vegas.modelo.postgresql.dto.parte.PartePK;
import es.rfgr.cr4vegas.modelo.postgresql.dto.parte.TipoParte;
import es.rfgr.cr4vegas.modelo.postgresql.dto.parteoficial.ParteOficial;
import es.rfgr.cr4vegas.modelo.postgresql.exceptions.DAOException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;

public class ParteDAOImp implements ParteDAO {

	private static final String ERROR = "ParteDAO: ";

	private ObservableList<Parte> listaPartes;

	private ParteBD parteBD;

	public ParteDAOImp() {
		listaPartes = FXCollections.observableArrayList();
		parteBD = new ParteBD();
	}

	public void setConnection(Connection conn) {
		parteBD.setConnection(conn);
	}

	public ObservableList<Parte> getPartes() {
		return listaPartes;
	}

	public int getNumPartes() {
		return listaPartes.size();
	}

	public PartePK insert(Parte dto) throws DAOException {
		PartePK pk = null;
		
		try {
			if (listaPartes.contains(dto)) {
				throw new OperationsException("El parte ya existe.");
			} else {
				pk = parteBD.insert(dto);
				
				this.listaPartes.add(dto);

			}
		} catch (Exception e) {
			throwException(e);
		}
		return pk;
	}

	public ObservableList<PartePK> insertAll(ObservableList<Parte> listToInsert) throws DAOException {
		ObservableList<PartePK> listToReturn = FXCollections.observableArrayList();
		for (Parte dto : listToInsert) {
			PartePK dtoPK = insert(dto);
			if (dtoPK != null) {
				listToReturn.add(dtoPK);
			}
		}
		return listToReturn;
	}

	public void update(PartePK pk, Parte dto) throws DAOException {
		Parte oldParte = getOldParte(pk);

		try {
			if (dto == null) {
				throw new OperationsException("El parte a modificar no puede ser nulo.");
			}
			if (!listaPartes.contains(oldParte)) {
				throw new OperationsException("El parte a modificar no existe.");

			} else {
				parteBD.update(pk, dto);

				listaPartes.set(listaPartes.indexOf(oldParte), dto);
			}
		} catch (Exception e) {
			throwException(e);
		}
	}

	public void updateAll(ObservableMap<PartePK, Parte> mapToUpdate) throws DAOException {
		for (Map.Entry<PartePK, Parte> entry : mapToUpdate.entrySet()) {
			update(entry.getKey(), entry.getValue());
		}
	}

	public void delete(PartePK pk) throws DAOException {
		Parte oldParte = getOldParte(pk);

		try {
			if (pk == null) {
				throw new NullPointerException("No se puede eliminar un parte nulo.");
			}
			if (!listaPartes.contains(oldParte)) {
				throw new OperationsException("El parte a eliminar no existe.");

			} else {
				parteBD.delete(pk);

				listaPartes.remove(oldParte);
			}
		} catch (Exception e) {
			throwException(e);
		}
	}

	public void deleteAll(ObservableList<Parte> listToDelete) throws DAOException {
		for (Parte dto : listToDelete) {
			delete(dto.createPK());
		}
	}

	public void findAll() throws DAOException {
		try {
			listaPartes.setAll(parteBD.findAll());

		} catch (SQLException e) {
			throwException(e);
		}
	}

	public Parte findByPrimaryKey(PartePK pk) throws DAOException {
		try {
			return parteBD.findByPrimaryKey(pk);

		} catch (SQLException e) {
			throwException(e);
		}
		return null;
	}

	public void setMaxRows(int maxRows) {
		parteBD.setMaxRows(maxRows);
	}

	public int getMaxRows() {
		return parteBD.getMaxRows();
	}

	public void createDependencies(TipoParteDAO tipoParteDAO, GrupoDAO grupoDAO) {
		for (Parte parte : listaPartes) {
			parte.setObjTipoParte(depParteTipo(parte, tipoParteDAO));
			parte.setObjGrupo(depParteGrupo(parte, grupoDAO));
		}
	}

	public void createDependencies(ParteOficialDAO parteOficialDAO) {
		for (Parte parte : listaPartes) {
			for (ParteOficial parteOficial : parteOficialDAO.getPartesOficiales()) {
				if (parte.getParteOficial() == parteOficial.getCodParte()) {
					parte.setObjParteOficial(parteOficial);
				}
			}
		}
	}

	private TipoParte depParteTipo(Parte dto, TipoParteDAO tipoParteDAO) {
		TipoParte tipoParteSalida = null;
		for (TipoParte tipoParte : tipoParteDAO.getTiposParte()) {
			if (tipoParte.getCodigo() == dto.getTipo()) {
				tipoParteSalida = tipoParte;
			}
		}
		return tipoParteSalida;
	}

	private Grupo depParteGrupo(Parte dto, GrupoDAO grupoDAO) {
		Grupo grupoSalida = null;
		for (Grupo grupo : grupoDAO.getGrupos()) {
			if (grupo.getNombre().equals(dto.getGrupo())) {
				grupoSalida = grupo;
			}
		}
		return grupoSalida;
	}

	private void throwException(Exception e) throws DAOException {
		throw new DAOException(ERROR + e.getMessage(), e);
	}

	private Parte getOldParte(PartePK pk) {
		Parte oldParte = new Parte();
		oldParte.setCodParte(pk.getCodParte());
		oldParte = listaPartes.get(listaPartes.indexOf(oldParte));
		return oldParte;
	}
}
