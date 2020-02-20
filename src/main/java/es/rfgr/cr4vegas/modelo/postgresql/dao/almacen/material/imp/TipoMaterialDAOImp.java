package es.rfgr.cr4vegas.modelo.postgresql.dao.almacen.material.imp;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

import javax.management.OperationsException;

import es.rfgr.cr4vegas.modelo.postgresql.bd.almacen.material.TipoMaterialBD;
import es.rfgr.cr4vegas.modelo.postgresql.dao.almacen.material.TipoMaterialDAO;
import es.rfgr.cr4vegas.modelo.postgresql.dto.almacen.material.TipoMaterial;
import es.rfgr.cr4vegas.modelo.postgresql.dto.almacen.material.TipoMaterialPK;
import es.rfgr.cr4vegas.modelo.postgresql.exceptions.DAOException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;

public class TipoMaterialDAOImp implements TipoMaterialDAO {

	private static final String ERROR = "TipoMaterialDAO: ";

	private ObservableList<TipoMaterial> listaTiposMaterial;

	private TipoMaterialBD tipoMaterialBD;

	public TipoMaterialDAOImp() {
		listaTiposMaterial = FXCollections.observableArrayList();
		tipoMaterialBD = new TipoMaterialBD();
	}

	public void setConnection(Connection conn) {
		tipoMaterialBD.setConnection(conn);
	}

	public ObservableList<TipoMaterial> getTiposMaterial() {
		return listaTiposMaterial;
	}

	public int getNumTiposMaterial() {
		return listaTiposMaterial.size();
	}

	public TipoMaterialPK insert(TipoMaterial dto) throws DAOException {
		TipoMaterialPK pk = dto.createPK();

		try {
			if (listaTiposMaterial.contains(dto)) {
				throw new OperationsException("El tipo de material ya existe.");

			} else {
				pk = tipoMaterialBD.insert(dto);

				this.listaTiposMaterial.add(dto);
			}
		} catch (Exception e) {
			throwException(e);
		}
		return pk;
	}

	public ObservableList<TipoMaterialPK> insertAll(ObservableList<TipoMaterial> listToInsert) throws DAOException {
		ObservableList<TipoMaterialPK> listToReturn = FXCollections.observableArrayList();
		for (TipoMaterial dto : listToInsert) {
			TipoMaterialPK dtoPK = insert(dto);
			if (dtoPK != null) {
				listToReturn.add(dtoPK);
			}
		}
		return listToReturn;
	}

	public void update(TipoMaterialPK pk, TipoMaterial dto) throws DAOException {
		TipoMaterial oldTipoMaterial = getOldTipoMaterial(pk);

		try {
			if (dto == null) {
				throw new OperationsException("El tipo de material a modificar no puede ser nulo.");
			}
			if (!listaTiposMaterial.contains(oldTipoMaterial)) {
				throw new OperationsException("El tipo de material a modificar no existe.");

			} else {
				tipoMaterialBD.update(pk, dto);

				oldTipoMaterial.copiarValores(dto);
			}
		} catch (Exception e) {
			throwException(e);
		}
	}

	public void updateAll(ObservableMap<TipoMaterialPK, TipoMaterial> mapToUpdate) throws DAOException {
		for (Map.Entry<TipoMaterialPK, TipoMaterial> entry : mapToUpdate.entrySet()) {
			update(entry.getKey(), entry.getValue());
		}
	}

	public void delete(TipoMaterialPK pk) throws DAOException {
		TipoMaterial oldTipoMaterial = getOldTipoMaterial(pk);

		try {
			if (pk == null) {
				throw new NullPointerException("No se puede eliminar un tipo de material nulo.");
			}
			if (!listaTiposMaterial.contains(oldTipoMaterial)) {
				throw new OperationsException("El tipo de material a eliminar no existe.");

			} else {
				tipoMaterialBD.delete(pk);

				listaTiposMaterial.remove(oldTipoMaterial);
			}
		} catch (Exception e) {
			throwException(e);
		}
	}

	public void deleteAll(ObservableList<TipoMaterial> listToDelete) throws DAOException {
		for (TipoMaterial dto : listToDelete) {
			delete(dto.createPK());
		}
	}

	public void findAll() throws DAOException {
		try {
			listaTiposMaterial.setAll(tipoMaterialBD.findAll());
		} catch (SQLException e) {
			throwException(e);
		}
	}
	
	public TipoMaterial findByPrimaryKey(TipoMaterialPK pk) throws DAOException {
		try {
			return tipoMaterialBD.findByPrimaryKey(pk);

		} catch (SQLException e) {
			throwException(e);
		}
		return null;
	}
	
	public void setMaxRows(int maxRows) {
		tipoMaterialBD.setMaxRows(maxRows);
	}

	public int getMaxRows() {
		return tipoMaterialBD.getMaxRows();
	}

	private void throwException(Exception e) throws DAOException {
		throw new DAOException(ERROR + e.getMessage(), e);
	}

	private TipoMaterial getOldTipoMaterial(TipoMaterialPK pk) {
		TipoMaterial oldTipoMaterial = new TipoMaterial();
		oldTipoMaterial.setNombre(pk.getNombre());
		oldTipoMaterial = listaTiposMaterial.get(listaTiposMaterial.indexOf(oldTipoMaterial));
		return oldTipoMaterial;
	}
}
