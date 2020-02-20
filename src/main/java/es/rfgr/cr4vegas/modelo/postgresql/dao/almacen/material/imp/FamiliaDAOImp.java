package es.rfgr.cr4vegas.modelo.postgresql.dao.almacen.material.imp;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

import javax.management.OperationsException;

import es.rfgr.cr4vegas.modelo.postgresql.bd.almacen.material.FamiliaBD;
import es.rfgr.cr4vegas.modelo.postgresql.dao.almacen.material.FamiliaDAO;
import es.rfgr.cr4vegas.modelo.postgresql.dto.almacen.material.Familia;
import es.rfgr.cr4vegas.modelo.postgresql.dto.almacen.material.FamiliaPK;
import es.rfgr.cr4vegas.modelo.postgresql.exceptions.DAOException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;

public class FamiliaDAOImp implements FamiliaDAO {

	private static final String ERROR = "FamiliaDAO: ";

	private ObservableList<Familia> listaFamilias;

	private FamiliaBD familiaBD;

	public FamiliaDAOImp() {
		listaFamilias = FXCollections.observableArrayList();
		familiaBD = new FamiliaBD();
	}

	public void setConnection(Connection conn) {
		familiaBD.setConnection(conn);
	}

	public ObservableList<Familia> getFamilias() {
		return listaFamilias;
	}

	public int getNumFamilias() {
		return listaFamilias.size();
	}

	public FamiliaPK insert(Familia dto) throws DAOException {
		FamiliaPK pk = dto.createPK();

		try {
			if (listaFamilias.contains(dto)) {
				throw new OperationsException("La familia ya existe.");
			} else {
				pk = familiaBD.insert(dto);

				this.listaFamilias.add(dto);
			}
		} catch (Exception e) {
			throwException(e);
		}
		return pk;
	}
	
	public ObservableList<FamiliaPK> insertAll(ObservableList<Familia> listToInsert) throws DAOException {
		ObservableList<FamiliaPK> listToReturn = FXCollections.observableArrayList();
		for (Familia dto : listToInsert) {
			FamiliaPK dtoPK = insert(dto);
			if (dtoPK != null) {
				listToReturn.add(dtoPK);
			}
		}
		return listToReturn;
	}

	public void update(FamiliaPK pk, Familia dto) throws DAOException {
		Familia oldFamilia = getOldFamilia(pk);

		try {
			if (dto == null) {
				throw new OperationsException("La familia a modificar no puede ser nula.");
			}
			if (!listaFamilias.contains(oldFamilia)) {
				throw new OperationsException("La familia a modificar no existe.");

			} else {
				familiaBD.update(pk, dto);

				oldFamilia.copiarValores(dto);
			}
		} catch (Exception e) {
			throwException(e);
		}
	}
	
	public void updateAll(ObservableMap<FamiliaPK, Familia> mapToUpdate) throws DAOException {
		for (Map.Entry<FamiliaPK, Familia> entry : mapToUpdate.entrySet()) {
			update(entry.getKey(), entry.getValue());
		}
	}

	public void delete(FamiliaPK pk) throws DAOException {
		Familia oldFamilia = getOldFamilia(pk);

		try {
			if (pk == null) {
				throw new NullPointerException("No se puede eliminar una familia nula.");
			}
			if (!listaFamilias.contains(oldFamilia)) {
				throw new OperationsException("La familia a eliminar no existe.");

			} else {
				familiaBD.delete(pk);

				listaFamilias.remove(oldFamilia);
			}
		} catch (Exception e) {
			throwException(e);
		}
	}
	
	public void deleteAll(ObservableList<Familia> listToDelete) throws DAOException {
		for (Familia dto : listToDelete) {
			delete(dto.createPK());
		}
	}

	public void findAll() throws DAOException {
		try {
			listaFamilias.setAll(familiaBD.findAll());

		} catch (SQLException e) {
			throwException(e);
		}
	}

	public Familia findByPrimaryKey(FamiliaPK pk) throws DAOException {
		try {
			return familiaBD.findByPrimaryKey(pk);

		} catch (SQLException e) {
			throwException(e);
		}
		return null;
	}
	
	public void setMaxRows(int maxRows) {
		familiaBD.setMaxRows(maxRows);
	}

	public int getMaxRows() {
		return familiaBD.getMaxRows();
	}

	private void throwException(Exception e) throws DAOException {
		throw new DAOException(ERROR + e.getMessage(), e);
	}

	private Familia getOldFamilia(FamiliaPK pk) {
		Familia oldFamilia = new Familia();
		oldFamilia.setNombre(pk.getNombre());
		oldFamilia = listaFamilias.get(listaFamilias.indexOf(oldFamilia));
		return oldFamilia;
	}
}
