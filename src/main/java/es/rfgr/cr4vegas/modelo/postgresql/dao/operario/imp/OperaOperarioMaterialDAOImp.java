package es.rfgr.cr4vegas.modelo.postgresql.dao.operario.imp;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

import javax.management.OperationsException;

import es.rfgr.cr4vegas.modelo.postgresql.bd.operario.OperaOperarioMaterialBD;
import es.rfgr.cr4vegas.modelo.postgresql.dao.almacen.material.MaterialDAO;
import es.rfgr.cr4vegas.modelo.postgresql.dao.operario.OperaOperarioMaterialDAO;
import es.rfgr.cr4vegas.modelo.postgresql.dao.operario.OperarioDAO;
import es.rfgr.cr4vegas.modelo.postgresql.dto.almacen.material.Material;
import es.rfgr.cr4vegas.modelo.postgresql.dto.operario.OperaOperarioMaterial;
import es.rfgr.cr4vegas.modelo.postgresql.dto.operario.OperaOperarioMaterialPK;
import es.rfgr.cr4vegas.modelo.postgresql.dto.operario.Operario;
import es.rfgr.cr4vegas.modelo.postgresql.exceptions.DAOException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;

public class OperaOperarioMaterialDAOImp implements OperaOperarioMaterialDAO {

	private static final String ERROR = "OperaOperarioMaterialDAO: ";

	private ObservableList<OperaOperarioMaterial> listaOperaOperarioMaterial;

	private OperaOperarioMaterialBD operaOperarioMaterialBD;

	public OperaOperarioMaterialDAOImp() {
		listaOperaOperarioMaterial = FXCollections.observableArrayList();
		operaOperarioMaterialBD = new OperaOperarioMaterialBD();
	}

	public void setConnection(Connection conn) {
		operaOperarioMaterialBD.setConnection(conn);
	}

	public ObservableList<OperaOperarioMaterial> getOperaOperarioMaterial() {
		return listaOperaOperarioMaterial;
	}

	public int getNumOperaOperarioMaterial() {
		return listaOperaOperarioMaterial.size();
	}

	public OperaOperarioMaterialPK insert(OperaOperarioMaterial dto) throws DAOException {
		OperaOperarioMaterialPK pk = dto.createPK();

		try {
			if (listaOperaOperarioMaterial.contains(dto)) {
				throw new OperationsException("El operario-material ya existe.");
			} else {
				pk = operaOperarioMaterialBD.insert(dto);

				this.listaOperaOperarioMaterial.add(dto);
			}
		} catch (Exception e) {
			throwException(e);
		}
		return pk;
	}

	public ObservableList<OperaOperarioMaterialPK> insertAll(ObservableList<OperaOperarioMaterial> listToInsert)
			throws DAOException {
		ObservableList<OperaOperarioMaterialPK> listToReturn = FXCollections.observableArrayList();
		for (OperaOperarioMaterial dto : listToInsert) {
			OperaOperarioMaterialPK dtoPK = insert(dto);
			if (dtoPK != null) {
				listToReturn.add(dtoPK);
			}
		}
		return listToReturn;
	}

	public void update(OperaOperarioMaterialPK pk, OperaOperarioMaterial dto) throws DAOException {
		OperaOperarioMaterial oldOperaOperarioMaterial = getOldOperaOperarioMaterial(pk);

		try {
			if (dto == null) {
				throw new OperationsException("El operario-material a modificar no puede ser nula.");
			}
			if (!listaOperaOperarioMaterial.contains(oldOperaOperarioMaterial)) {
				throw new OperationsException("El operario-material a modificar no existe.");

			} else {
				operaOperarioMaterialBD.update(pk, dto);

				listaOperaOperarioMaterial.set(listaOperaOperarioMaterial.indexOf(oldOperaOperarioMaterial), dto);
			}
		} catch (Exception e) {
			throwException(e);
		}
	}

	public void updateAll(ObservableMap<OperaOperarioMaterialPK, OperaOperarioMaterial> mapToUpdate)
			throws DAOException {
		for (Map.Entry<OperaOperarioMaterialPK, OperaOperarioMaterial> entry : mapToUpdate.entrySet()) {
			update(entry.getKey(), entry.getValue());
		}
	}

	public void delete(OperaOperarioMaterialPK pk) throws DAOException {
		OperaOperarioMaterial oldOperaOperarioMaterial = getOldOperaOperarioMaterial(pk);

		try {
			if (pk == null) {
				throw new NullPointerException("No se puede eliminar un operario-material nulo.");
			}
			if (!listaOperaOperarioMaterial.contains(oldOperaOperarioMaterial)) {
				throw new OperationsException("El operario-material a eliminar no existe.");

			} else {
				operaOperarioMaterialBD.delete(pk);

				listaOperaOperarioMaterial.remove(oldOperaOperarioMaterial);
			}
		} catch (Exception e) {
			throwException(e);
		}
	}

	public void deleteAll(ObservableList<OperaOperarioMaterial> listToDelete) throws DAOException {
		for (OperaOperarioMaterial dto : listToDelete) {
			delete(dto.createPK());
		}
	}

	public void findAll() throws DAOException {
		try {
			listaOperaOperarioMaterial.setAll(operaOperarioMaterialBD.findAll());

		} catch (SQLException e) {
			throwException(e);
		}
	}

	public OperaOperarioMaterial findByPrimaryKey(OperaOperarioMaterialPK pk) throws DAOException {
		try {
			return operaOperarioMaterialBD.findByPrimaryKey(pk);

		} catch (SQLException e) {
			throwException(e);
		}
		return null;
	}

	public void setMaxRows(int maxRows) {
		operaOperarioMaterialBD.setMaxRows(maxRows);
	}

	public int getMaxRows() {
		return operaOperarioMaterialBD.getMaxRows();
	}

	public ObservableList<OperaOperarioMaterial> getOperarioMateriales(Operario dto) {
		ObservableList<OperaOperarioMaterial> listToReturn = FXCollections.observableArrayList();
		for (OperaOperarioMaterial dtoOnList : listaOperaOperarioMaterial) {
			if (dtoOnList.getCodOp() == dto.getCodOp()) {
				listToReturn.add(dtoOnList);
			}
		}
		return listToReturn;
	}

	public ObservableList<OperaOperarioMaterial> getOperarioOperaOperariosMateriales(Operario dto) {
		ObservableList<OperaOperarioMaterial> listToReturn = FXCollections.observableArrayList();
		for (OperaOperarioMaterial dtoOnList : listaOperaOperarioMaterial) {
			if (dtoOnList.getCodOp() == dto.getCodOp()) {
				listToReturn.add(dtoOnList);
			}
		}
		return listToReturn;
	}

	public void createDepedencies(OperarioDAO operarioDAO, MaterialDAO materialDAO) {
		for (OperaOperarioMaterial oom : listaOperaOperarioMaterial) {
			oom.setObjOperario(dependencyOperaOperarioMaterialOperario(oom, operarioDAO));
			oom.setObjMaterial(dependencyOperaOperarioMaterialMaterial(oom, materialDAO));
		}
	}

	private Operario dependencyOperaOperarioMaterialOperario(OperaOperarioMaterial dto, OperarioDAO operarioDAO) {
		Operario operarioSalida = null;
		for (Operario operario : operarioDAO.getOperarios()) {
			if (operario.getCodOp() == dto.getCodOp()) {
				operarioSalida = operario;
			}
		}
		return operarioSalida;
	}

	private Material dependencyOperaOperarioMaterialMaterial(OperaOperarioMaterial dto, MaterialDAO materialDAO) {
		Material materialSalida = null;
		for (Material material : materialDAO.getMateriales()) {
			if (material.getCodigo4v() == dto.getCodigo4v()) {
				materialSalida = material;
			}
		}
		return materialSalida;
	}

	private void throwException(Exception e) throws DAOException {
		throw new DAOException(ERROR + e.getMessage(), e);
	}

	private OperaOperarioMaterial getOldOperaOperarioMaterial(OperaOperarioMaterialPK pk) {
		OperaOperarioMaterial oldOperaOperarioMaterial = new OperaOperarioMaterial();
		oldOperaOperarioMaterial.setCodOperacion(pk.getCodOperacion());
		oldOperaOperarioMaterial = listaOperaOperarioMaterial
				.get(listaOperaOperarioMaterial.indexOf(oldOperaOperarioMaterial));
		return oldOperaOperarioMaterial;
	}

}