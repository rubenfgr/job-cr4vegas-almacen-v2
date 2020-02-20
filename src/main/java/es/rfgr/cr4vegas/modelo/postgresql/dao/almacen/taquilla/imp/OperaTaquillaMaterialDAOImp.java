package es.rfgr.cr4vegas.modelo.postgresql.dao.almacen.taquilla.imp;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

import javax.management.OperationsException;

import es.rfgr.cr4vegas.modelo.postgresql.bd.almacen.taquilla.OperaTaquillaMaterialBD;
import es.rfgr.cr4vegas.modelo.postgresql.dao.almacen.material.MaterialDAO;
import es.rfgr.cr4vegas.modelo.postgresql.dao.almacen.taquilla.OperaTaquillaMaterialDAO;
import es.rfgr.cr4vegas.modelo.postgresql.dao.almacen.taquilla.TaquillaDAO;
import es.rfgr.cr4vegas.modelo.postgresql.dto.almacen.material.Material;
import es.rfgr.cr4vegas.modelo.postgresql.dto.almacen.taquilla.OperaTaquillaMaterial;
import es.rfgr.cr4vegas.modelo.postgresql.dto.almacen.taquilla.OperaTaquillaMaterialPK;
import es.rfgr.cr4vegas.modelo.postgresql.dto.almacen.taquilla.Taquilla;
import es.rfgr.cr4vegas.modelo.postgresql.exceptions.DAOException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;

public class OperaTaquillaMaterialDAOImp implements OperaTaquillaMaterialDAO {

	private static final String ERROR = "OperaTaquillaMaterialDAO: ";

	private ObservableList<OperaTaquillaMaterial> listaOperaTaquillaMaterial;

	private OperaTaquillaMaterialBD operaTaquillaMaterialBD;

	public OperaTaquillaMaterialDAOImp() {
		listaOperaTaquillaMaterial = FXCollections.observableArrayList();
		operaTaquillaMaterialBD = new OperaTaquillaMaterialBD();
	}

	public void setConnection(Connection conn) {
		operaTaquillaMaterialBD.setConnection(conn);
	}

	public ObservableList<OperaTaquillaMaterial> getOperaTaquillaMateriales() {
		return listaOperaTaquillaMaterial;
	}

	public int getNumOperaTaquillaMateriales() {
		return listaOperaTaquillaMaterial.size();
	}

	public OperaTaquillaMaterialPK insert(OperaTaquillaMaterial dto) throws DAOException {
		OperaTaquillaMaterialPK pk = dto.createPK();

		try {
			if (listaOperaTaquillaMaterial.contains(dto)) {
				throw new OperationsException("La taquilla-material ya existe.");
			} else {
				pk = operaTaquillaMaterialBD.insert(dto);

				this.listaOperaTaquillaMaterial.add(dto);
			}
		} catch (Exception e) {
			throwException(e);
		}
		return pk;
	}

	public ObservableList<OperaTaquillaMaterialPK> insertAll(ObservableList<OperaTaquillaMaterial> listToInsert)
			throws DAOException {
		ObservableList<OperaTaquillaMaterialPK> listToReturn = FXCollections.observableArrayList();
		for (OperaTaquillaMaterial dto : listToInsert) {
			OperaTaquillaMaterialPK dtoPK = insert(dto);
			if (dtoPK != null) {
				listToReturn.add(dtoPK);
			}
		}
		return listToReturn;
	}

	public void update(OperaTaquillaMaterialPK pk, OperaTaquillaMaterial dto) throws DAOException {
		OperaTaquillaMaterial oldOperaTaquillaMaterial = getOldOperaTaquillaMaterial(pk);

		try {
			if (dto == null) {
				throw new OperationsException("La taquilla-material a modificar no puede ser nula.");
			}
			if (!listaOperaTaquillaMaterial.contains(oldOperaTaquillaMaterial)) {
				throw new OperationsException("La taquilla-material a modificar no existe.");

			} else {
				operaTaquillaMaterialBD.update(pk, dto);

				listaOperaTaquillaMaterial.set(listaOperaTaquillaMaterial.indexOf(oldOperaTaquillaMaterial), dto);
			}
		} catch (Exception e) {
			throwException(e);
		}
	}

	public void updateAll(ObservableMap<OperaTaquillaMaterialPK, OperaTaquillaMaterial> mapToUpdate)
			throws DAOException {
		for (Map.Entry<OperaTaquillaMaterialPK, OperaTaquillaMaterial> entry : mapToUpdate.entrySet()) {
			update(entry.getKey(), entry.getValue());
		}
	}

	public void delete(OperaTaquillaMaterialPK pk) throws DAOException {
		OperaTaquillaMaterial oldOperaTaquillaMaterial = getOldOperaTaquillaMaterial(pk);

		try {
			if (pk == null) {
				throw new NullPointerException("No se puede eliminar una taquilla-material nula.");
			}
			if (!listaOperaTaquillaMaterial.contains(oldOperaTaquillaMaterial)) {
				throw new OperationsException("La taquilla-material a eliminar no existe.");

			} else {
				operaTaquillaMaterialBD.delete(pk);

				listaOperaTaquillaMaterial.remove(oldOperaTaquillaMaterial);
			}
		} catch (Exception e) {
			throwException(e);
		}
	}

	public void deleteAll(ObservableList<OperaTaquillaMaterial> listToDelete) throws DAOException {
		for (OperaTaquillaMaterial dto : listToDelete) {
			delete(dto.createPK());
		}
	}

	public void findAll() throws DAOException {
		try {
			listaOperaTaquillaMaterial.setAll(operaTaquillaMaterialBD.findAll());
		} catch (SQLException e) {
			throwException(e);
		}
	}

	public OperaTaquillaMaterial findByPrimaryKey(OperaTaquillaMaterialPK pk) throws DAOException {
		try {
			return operaTaquillaMaterialBD.findByPrimaryKey(pk);

		} catch (SQLException e) {
			throwException(e);
		}
		return null;
	}

	public void setMaxRows(int maxRows) {
		operaTaquillaMaterialBD.setMaxRows(maxRows);
	}

	public int getMaxRows() {
		return operaTaquillaMaterialBD.getMaxRows();
	}

	public ObservableList<OperaTaquillaMaterial> getTaquillaMateriales(Taquilla dto) {
		ObservableList<OperaTaquillaMaterial> listToReturn = FXCollections.observableArrayList();
		for (OperaTaquillaMaterial dtoOnList : listaOperaTaquillaMaterial) {
			if (dtoOnList.getCodTaquilla() == dto.getCodTaquilla()) {
				listToReturn.add(dtoOnList);
			}
		}
		return listToReturn;
	}

	public void createDependencies(TaquillaDAO taquillaDAO, MaterialDAO materialDAO) {
		for (OperaTaquillaMaterial dto : listaOperaTaquillaMaterial) {
			dto.setObjMaterial(dependencyOperaTaquillaMaterialMaterial(dto, materialDAO));
			dto.setObjTaquilla(dependencyOperaTaquillaMaterialTaquilla(dto, taquillaDAO));
		}
	}

	private Taquilla dependencyOperaTaquillaMaterialTaquilla(OperaTaquillaMaterial dto, TaquillaDAO taquillaDAO) {
		for (Taquilla taquilla : taquillaDAO.getTaquillas()) {
			if (taquilla.getCodTaquilla() == dto.getCodTaquilla()) {
				return taquilla;
			}
		}
		return null;
	}

	private Material dependencyOperaTaquillaMaterialMaterial(OperaTaquillaMaterial dto, MaterialDAO materialDAO) {
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

	private OperaTaquillaMaterial getOldOperaTaquillaMaterial(OperaTaquillaMaterialPK pk) {
		OperaTaquillaMaterial oldOperaTaquillaMaterial = new OperaTaquillaMaterial();
		oldOperaTaquillaMaterial.setCodOperacion(pk.getCodOperacion());
		oldOperaTaquillaMaterial = listaOperaTaquillaMaterial
				.get(listaOperaTaquillaMaterial.indexOf(oldOperaTaquillaMaterial));
		return oldOperaTaquillaMaterial;
	}
}
