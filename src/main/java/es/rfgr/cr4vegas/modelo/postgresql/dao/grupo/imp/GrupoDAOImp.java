package es.rfgr.cr4vegas.modelo.postgresql.dao.grupo.imp;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

import javax.management.OperationsException;

import es.rfgr.cr4vegas.modelo.postgresql.bd.grupo.GrupoBD;
import es.rfgr.cr4vegas.modelo.postgresql.dao.grupo.GrupoDAO;
import es.rfgr.cr4vegas.modelo.postgresql.dto.grupo.Grupo;
import es.rfgr.cr4vegas.modelo.postgresql.dto.grupo.GrupoPK;
import es.rfgr.cr4vegas.modelo.postgresql.exceptions.DAOException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;

public class GrupoDAOImp implements GrupoDAO {

	private static final String ERROR = "GrupoDAO: ";

	private ObservableList<Grupo> listaGrupos;

	private GrupoBD grupoBD;

	public GrupoDAOImp() {
		listaGrupos = FXCollections.observableArrayList();
		grupoBD = new GrupoBD();
	}

	public void setConnection(Connection conn) {
		grupoBD.setConnection(conn);
	}

	public ObservableList<Grupo> getGrupos() {
		return listaGrupos;
	}

	public int getNumGrupos() {
		return listaGrupos.size();
	}

	public GrupoPK insert(Grupo dto) throws DAOException {
		GrupoPK pk = dto.createPK();

		try {
			if (listaGrupos.contains(dto)) {
				throw new OperationsException("El grupo ya existe.");
			} else {
				pk = grupoBD.insert(dto);

				this.listaGrupos.add(dto);
			}
		} catch (Exception e) {
			throwException(e);
		}
		return pk;
	}

	public ObservableList<GrupoPK> insertAll(ObservableList<Grupo> listToInsert) throws DAOException {
		ObservableList<GrupoPK> listToReturn = FXCollections.observableArrayList();
		for (Grupo dto : listToInsert) {
			GrupoPK dtoPK = insert(dto);
			if (dtoPK != null) {
				listToReturn.add(dtoPK);
			}
		}
		return listToReturn;
	}

	public void update(GrupoPK pk, Grupo dto) throws DAOException {
		Grupo oldGrupo = getOldGrupo(pk);

		try {
			if (dto == null) {
				throw new OperationsException("El grupo a modificar no puede ser nulo.");
			}
			if (!listaGrupos.contains(oldGrupo)) {
				throw new OperationsException("El grupo a modificar no existe.");

			} else {
				grupoBD.update(pk, dto);

				listaGrupos.set(listaGrupos.indexOf(oldGrupo), dto);
			}
		} catch (Exception e) {
			throwException(e);
		}
	}

	public void updateAll(ObservableMap<GrupoPK, Grupo> mapToUpdate) throws DAOException {
		for (Map.Entry<GrupoPK, Grupo> entry : mapToUpdate.entrySet()) {
			update(entry.getKey(), entry.getValue());
		}
	}

	public void delete(GrupoPK pk) throws DAOException {
		Grupo oldGrupo = getOldGrupo(pk);

		try {
			if (pk == null) {
				throw new NullPointerException("No se puede eliminar un grupo nulo.");
			}
			if (!listaGrupos.contains(oldGrupo)) {
				throw new OperationsException("El grupo a eliminar no existe.");

			} else {
				grupoBD.delete(pk);

				listaGrupos.remove(oldGrupo);
			}
		} catch (Exception e) {
			throwException(e);
		}
	}

	public void deleteAll(ObservableList<Grupo> listToDelete) throws DAOException {
		for (Grupo dto : listToDelete) {
			delete(dto.createPK());
		}
	}

	public void findAll() throws DAOException {
		try {
			listaGrupos.setAll(grupoBD.findAll());
		} catch (SQLException e) {
			throwException(e);
		}
	}

	public Grupo findByPrimaryKey(GrupoPK pk) throws DAOException {
		try {
			return grupoBD.findByPrimaryKey(pk);

		} catch (SQLException e) {
			throwException(e);
		}
		return null;
	}

	public void setMaxRows(int maxRows) {
		grupoBD.setMaxRows(maxRows);
	}

	public int getMaxRows() {
		return grupoBD.getMaxRows();
	}

	private void throwException(Exception e) throws DAOException {
		throw new DAOException(ERROR + e.getMessage(), e);
	}
	
	private Grupo getOldGrupo(GrupoPK pk) {
		Grupo oldGrupo = new Grupo();
		oldGrupo.setNombre(pk.getNombre());
		oldGrupo = listaGrupos.get(listaGrupos.indexOf(oldGrupo));
		return oldGrupo;
	}
}
