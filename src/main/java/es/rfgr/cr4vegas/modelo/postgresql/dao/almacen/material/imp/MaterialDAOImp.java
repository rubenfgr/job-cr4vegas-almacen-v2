package es.rfgr.cr4vegas.modelo.postgresql.dao.almacen.material.imp;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

import javax.management.OperationsException;

import es.rfgr.cr4vegas.modelo.postgresql.bd.almacen.material.MaterialBD;
import es.rfgr.cr4vegas.modelo.postgresql.dao.almacen.material.FamiliaDAO;
import es.rfgr.cr4vegas.modelo.postgresql.dao.almacen.material.MaterialDAO;
import es.rfgr.cr4vegas.modelo.postgresql.dao.almacen.material.TipoMaterialDAO;
import es.rfgr.cr4vegas.modelo.postgresql.dao.almacen.material.UbicacionDAO;
import es.rfgr.cr4vegas.modelo.postgresql.dto.almacen.material.Familia;
import es.rfgr.cr4vegas.modelo.postgresql.dto.almacen.material.Material;
import es.rfgr.cr4vegas.modelo.postgresql.dto.almacen.material.MaterialPK;
import es.rfgr.cr4vegas.modelo.postgresql.dto.almacen.material.TipoMaterial;
import es.rfgr.cr4vegas.modelo.postgresql.dto.almacen.material.Ubicacion;
import es.rfgr.cr4vegas.modelo.postgresql.exceptions.DAOException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;

public class MaterialDAOImp implements MaterialDAO {

	private static final String ERROR = "MaterialDAO: ";

	private ObservableList<Material> listaMateriales;

	private MaterialBD materialBD;

	public MaterialDAOImp() {
		listaMateriales = FXCollections.observableArrayList();
		materialBD = new MaterialBD();
	}

	public void setConnection(Connection conn) {
		materialBD.setConnection(conn);
	}

	public ObservableList<Material> getMateriales() {
		return listaMateriales;
	}

	public int getNumMateriales() {
		return listaMateriales.size();
	}

	public MaterialPK insert(Material dto) throws DAOException {
		MaterialPK pk = dto.createPK();

		try {
			if (listaMateriales.contains(dto)) {
				throw new OperationsException("El material ya existe.");
				
			} else {
				pk = materialBD.insert(dto);

				listaMateriales.add(dto);
			}
		} catch (Exception e) {
			throwException(e);
		}
		return pk;
	}

	public ObservableList<MaterialPK> insertAll(ObservableList<Material> listToInsert) throws DAOException {
		ObservableList<MaterialPK> listToReturn = FXCollections.observableArrayList();
		for (Material dto : listToInsert) {
			MaterialPK dtoPK = insert(dto);
			if (dtoPK != null) {
				listToReturn.add(dtoPK);
			}
		}
		return listToReturn;
	}

	public void update(MaterialPK pk, Material dto) throws DAOException {
		Material oldMaterial = getOldMaterial(pk);

		try {
			if (dto == null) {
				throw new OperationsException("El material a modificar no puede ser nulo.");
			}
			if (!listaMateriales.contains(oldMaterial)) {
				throw new OperationsException("El material a modificar no existe.");

			} else {
				materialBD.update(pk, dto);

				listaMateriales.set(listaMateriales.indexOf(oldMaterial), dto);
			}
		} catch (Exception e) {
			throwException(e);
		}
	}

	public void updateAll(ObservableMap<MaterialPK, Material> mapToUpdate) throws DAOException {
		for (Map.Entry<MaterialPK, Material> entry : mapToUpdate.entrySet()) {
			update(entry.getKey(), entry.getValue());
		}
	}

	public void delete(MaterialPK pk) throws DAOException {
		Material oldMaterial = getOldMaterial(pk);

		try {
			if (pk == null) {
				throw new NullPointerException("No se puede eliminar un material nulo.");
			}
			if (!listaMateriales.contains(oldMaterial)) {
				throw new OperationsException("El material a eliminar no existe.");

			} else {
				materialBD.delete(pk);

				listaMateriales.remove(oldMaterial);
			}
		} catch (Exception e) {
			throwException(e);
		}
	}

	public void deleteAll(ObservableList<Material> listToDelete) throws DAOException {
		for (Material dto : listToDelete) {
			delete(dto.createPK());
		}
	}

	public void findAll() throws DAOException {
		try {
			listaMateriales.setAll(materialBD.findAll());

		} catch (SQLException e) {
			throwException(e);
		}
	}

	public void updateDTOMaterial(Material material) throws DAOException {
		try {
			materialBD.updateDTOMaterial(material);
		} catch (SQLException e) {
			throwException(e);
		}
	}

	public Material findByPrimaryKey(MaterialPK pk) throws DAOException {
		try {
			return materialBD.findByPrimaryKey(pk);

		} catch (SQLException e) {
			throwException(e);
		}
		return null;
	}

	public void setMaxRows(int maxRows) {
		materialBD.setMaxRows(maxRows);
	}

	public int getMaxRows() {
		return materialBD.getMaxRows();
	}

	public void createDependencies(UbicacionDAO ubicacionDAO, TipoMaterialDAO tipoMaterialDAO, FamiliaDAO familiaDAO) {
		for (Material material : listaMateriales) {
			material.setObjFamilia(dependencyMaterialFamilia(material, familiaDAO));
			material.setObjTipoMaterial(dependencyMaterialTipo(material, tipoMaterialDAO));
			material.setObjUbicacion(dependencyMaterialUbicacion(material, ubicacionDAO));
		}
	}

	private Ubicacion dependencyMaterialUbicacion(Material dto, UbicacionDAO ubicacionDAO) {
		for (Ubicacion ubicacion : ubicacionDAO.getUbicaciones()) {
			if (ubicacion.getNombre().equals(dto.getUbicacion())) {
				return ubicacion;
			}
		}
		return null;
	}

	private TipoMaterial dependencyMaterialTipo(Material dto, TipoMaterialDAO tipoMaterialDAO) {
		for (TipoMaterial tipoMaterial : tipoMaterialDAO.getTiposMaterial()) {
			if (tipoMaterial.getNombre().equals(dto.getMaterial())) {
				return tipoMaterial;
			}
		}
		return null;
	}

	private Familia dependencyMaterialFamilia(Material dto, FamiliaDAO familiaDAO) {
		for (Familia familia : familiaDAO.getFamilias()) {
			if (familia.getNombre().equals(dto.getFamilia())) {
				return familia;
			}
		}
		return null;
	}

	private void throwException(Exception e) throws DAOException {
		throw new DAOException(ERROR + e.getMessage(), e);
	}

	private Material getOldMaterial(MaterialPK pk) {
		Material oldMaterial = new Material();
		oldMaterial.setCodigo4v(pk.getCodigo4v());
		oldMaterial = listaMateriales.get(listaMateriales.indexOf(oldMaterial));
		return oldMaterial;
	}
}
