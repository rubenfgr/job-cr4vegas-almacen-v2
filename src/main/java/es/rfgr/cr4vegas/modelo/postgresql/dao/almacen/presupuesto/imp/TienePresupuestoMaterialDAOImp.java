package es.rfgr.cr4vegas.modelo.postgresql.dao.almacen.presupuesto.imp;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

import javax.management.OperationsException;

import es.rfgr.cr4vegas.modelo.postgresql.bd.almacen.presupuesto.TienePresupuestoMaterialBD;
import es.rfgr.cr4vegas.modelo.postgresql.dao.almacen.material.MaterialDAO;
import es.rfgr.cr4vegas.modelo.postgresql.dao.almacen.presupuesto.PresupuestoDAO;
import es.rfgr.cr4vegas.modelo.postgresql.dao.almacen.presupuesto.TienePresupuestoMaterialDAO;
import es.rfgr.cr4vegas.modelo.postgresql.dto.almacen.material.Material;
import es.rfgr.cr4vegas.modelo.postgresql.dto.almacen.presupuesto.Presupuesto;
import es.rfgr.cr4vegas.modelo.postgresql.dto.almacen.presupuesto.TienePresupuestoMaterial;
import es.rfgr.cr4vegas.modelo.postgresql.dto.almacen.presupuesto.TienePresupuestoMaterialPK;
import es.rfgr.cr4vegas.modelo.postgresql.exceptions.DAOException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;

public class TienePresupuestoMaterialDAOImp implements TienePresupuestoMaterialDAO {

	private static final String ERROR = "TienePresupuestoMaterialDAO: ";

	private ObservableList<TienePresupuestoMaterial> listaTienePresupuestoMaterial;

	private TienePresupuestoMaterialBD tienePresupuestoMaterialBD;

	public TienePresupuestoMaterialDAOImp() {
		listaTienePresupuestoMaterial = FXCollections.observableArrayList();
		tienePresupuestoMaterialBD = new TienePresupuestoMaterialBD();
	}

	public void setConnection(Connection conn) {
		tienePresupuestoMaterialBD.setConnection(conn);
	}

	public ObservableList<TienePresupuestoMaterial> getTienePresupuestoMaterial() {
		return listaTienePresupuestoMaterial;
	}

	public int getNumTienePresupuestoMaterial() {
		return listaTienePresupuestoMaterial.size();
	}

	public TienePresupuestoMaterialPK insert(TienePresupuestoMaterial dto) throws DAOException {
		TienePresupuestoMaterialPK pk = dto.createPK();

		try {
			if (listaTienePresupuestoMaterial.contains(dto)) {
				throw new OperationsException("El presupuesto-material ya existe.");

			} else {
				pk = tienePresupuestoMaterialBD.insert(dto);

				this.listaTienePresupuestoMaterial.add(dto);
			}
		} catch (Exception e) {
			throwException(e);
		}
		return pk;
	}

	public ObservableList<TienePresupuestoMaterialPK> insertAll(ObservableList<TienePresupuestoMaterial> listToInsert)
			throws DAOException {
		ObservableList<TienePresupuestoMaterialPK> listToReturn = FXCollections.observableArrayList();
		for (TienePresupuestoMaterial dto : listToInsert) {
			TienePresupuestoMaterialPK dtoPK = insert(dto);
			if (dtoPK != null) {
				listToReturn.add(dtoPK);
			}
		}
		return listToReturn;
	}

	public void update(TienePresupuestoMaterialPK pk, TienePresupuestoMaterial dto) throws DAOException {
		TienePresupuestoMaterial oldTienePresupuestoMaterial = getOldTienePresupuestoMaterial(pk);

		try {
			if (dto == null) {
				throw new OperationsException("El presupuesto-material a modificar no puede ser nulo.");
			}
			if (!listaTienePresupuestoMaterial.contains(oldTienePresupuestoMaterial)) {
				throw new OperationsException("El presupuesto-material a modificar no existe.");

			} else {
				tienePresupuestoMaterialBD.update(pk, dto);

				listaTienePresupuestoMaterial.set(listaTienePresupuestoMaterial.indexOf(oldTienePresupuestoMaterial),
						dto);
			}
		} catch (Exception e) {
			throwException(e);
		}
	}

	public void updateAll(ObservableMap<TienePresupuestoMaterialPK, TienePresupuestoMaterial> mapToUpdate)
			throws DAOException {
		for (Map.Entry<TienePresupuestoMaterialPK, TienePresupuestoMaterial> entry : mapToUpdate.entrySet()) {
			update(entry.getKey(), entry.getValue());
		}
	}

	public void delete(TienePresupuestoMaterialPK pk) throws DAOException {
		TienePresupuestoMaterial oldTienePresupuestoMaterial = getOldTienePresupuestoMaterial(pk);

		try {
			if (pk == null) {
				throw new NullPointerException("No se puede eliminar un presupuesto-material nulo.");
			}
			if (!listaTienePresupuestoMaterial.contains(oldTienePresupuestoMaterial)) {
				throw new OperationsException("El presupuesto-material a eliminar no existe.");

			} else {
				tienePresupuestoMaterialBD.delete(pk);

				listaTienePresupuestoMaterial.remove(oldTienePresupuestoMaterial);
			}
		} catch (Exception e) {
			throwException(e);
		}
	}

	public void deleteAll(ObservableList<TienePresupuestoMaterial> listToDelete) throws DAOException {
		for (TienePresupuestoMaterial dto : listToDelete) {
			delete(dto.createPK());
		}
	}

	public void findAll() throws DAOException {
		try {
			listaTienePresupuestoMaterial.setAll(tienePresupuestoMaterialBD.findAll());

		} catch (SQLException e) {
			throwException(e);
		}
	}

	public TienePresupuestoMaterial findByPrimaryKey(TienePresupuestoMaterialPK pk) throws DAOException {
		try {
			return tienePresupuestoMaterialBD.findByPrimaryKey(pk);

		} catch (SQLException e) {
			throwException(e);
		}
		return null;
	}

	public void setMaxRows(int maxRows) {
		tienePresupuestoMaterialBD.setMaxRows(maxRows);
	}

	public int getMaxRows() {
		return tienePresupuestoMaterialBD.getMaxRows();
	}

	public ObservableList<TienePresupuestoMaterial> getPresupuestoMateriales(Presupuesto dto) {
		ObservableList<TienePresupuestoMaterial> listToReturn = FXCollections.observableArrayList();
		for (TienePresupuestoMaterial dtoOnList : listaTienePresupuestoMaterial) {
			if (dtoOnList.getCodPresupuesto() == dto.getCodPresupuesto()) {
				listToReturn.add(dtoOnList);
			}
		}
		return listToReturn;
	}

	public void createDependencies(PresupuestoDAO presupuestoDAO, MaterialDAO materialDAO) {
		for (TienePresupuestoMaterial tpm : listaTienePresupuestoMaterial) {
			tpm.setObjMaterial(dependencyTienePresupuestoMaterialMaterial(tpm, materialDAO));
			tpm.setObjPresupuesto(dependencyTienePresupuestoMaterialPresupuesto(tpm, presupuestoDAO));
		}
	}

	private Presupuesto dependencyTienePresupuestoMaterialPresupuesto(TienePresupuestoMaterial dto,
			PresupuestoDAO presupuestoDAO) {
		for (Presupuesto presupuesto : presupuestoDAO.getPresupuestos()) {
			if (presupuesto.getCodPresupuesto() == dto.getCodPresupuesto()) {
				return presupuesto;
			}
		}
		return null;
	}

	private Material dependencyTienePresupuestoMaterialMaterial(TienePresupuestoMaterial dto, MaterialDAO materialDAO) {
		for (Material material : materialDAO.getMateriales()) {
			if (material.getCodigo4v() == dto.getCodigo4v()) {
				return material;
			}
		}
		return null;
	}

	private void throwException(Exception e) throws DAOException {
		throw new DAOException(ERROR + e.getMessage(), e);
	}

	private TienePresupuestoMaterial getOldTienePresupuestoMaterial(TienePresupuestoMaterialPK pk) {
		TienePresupuestoMaterial oldTienePresupuestoMaterial = new TienePresupuestoMaterial();
		oldTienePresupuestoMaterial.setCodPresupuesto(pk.getCodPresupuesto());
		oldTienePresupuestoMaterial.setCodigo4v(pk.getCodigo4v());
		oldTienePresupuestoMaterial = listaTienePresupuestoMaterial
				.get(listaTienePresupuestoMaterial.indexOf(oldTienePresupuestoMaterial));
		return oldTienePresupuestoMaterial;
	}
}
