package es.rfgr.cr4vegas.modelo.postgresql.dao.parte.imp;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

import javax.management.OperationsException;

import es.rfgr.cr4vegas.modelo.postgresql.bd.parte.OperaParteMaterialBD;
import es.rfgr.cr4vegas.modelo.postgresql.dao.almacen.material.MaterialDAO;
import es.rfgr.cr4vegas.modelo.postgresql.dao.parte.OperaParteMaterialDAO;
import es.rfgr.cr4vegas.modelo.postgresql.dao.parte.ParteDAO;
import es.rfgr.cr4vegas.modelo.postgresql.dto.almacen.material.Material;
import es.rfgr.cr4vegas.modelo.postgresql.dto.parte.OperaParteMaterial;
import es.rfgr.cr4vegas.modelo.postgresql.dto.parte.OperaParteMaterialPK;
import es.rfgr.cr4vegas.modelo.postgresql.dto.parte.Parte;
import es.rfgr.cr4vegas.modelo.postgresql.exceptions.DAOException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;

public class OperaParteMaterialDAOImp implements OperaParteMaterialDAO {

	private static final String ERROR = "OperaParteMaterialDAO: ";

	private ObservableList<OperaParteMaterial> listaOperaParteMaterial;
	
	private OperaParteMaterialBD operaParteMaterialBD;

	public OperaParteMaterialDAOImp() {
		listaOperaParteMaterial = FXCollections.observableArrayList();
		operaParteMaterialBD = new OperaParteMaterialBD();
	}

	public void setConnection(Connection conn) {
		operaParteMaterialBD.setConnection(conn);
	}

	public ObservableList<OperaParteMaterial> getOperaParteMaterial() {
		return listaOperaParteMaterial;
	}

	public int getNumOperaParteMaterial() {
		return listaOperaParteMaterial.size();
	}

	public OperaParteMaterialPK insert(OperaParteMaterial dto) throws DAOException {
		OperaParteMaterialPK pk = dto.createPK();
		
		try {
			if (listaOperaParteMaterial.contains(dto)) {
				throw new OperationsException("El parte-material ya existe.");
			} else {
				pk = operaParteMaterialBD.insert(dto);
				
				this.listaOperaParteMaterial.add(dto);
			}
		} catch (Exception e) {
			throwException(e);
		}
		return pk;
	}

	public ObservableList<OperaParteMaterialPK> insertAll(ObservableList<OperaParteMaterial> listToInsert) throws DAOException {
		ObservableList<OperaParteMaterialPK> listToReturn = FXCollections.observableArrayList();
		for (OperaParteMaterial dto : listToInsert) {
			OperaParteMaterialPK dtoPK = insert(dto);
			if (dtoPK != null) {
				listToReturn.add(dtoPK);
			}
		}
		return listToReturn;
	}

	public void update(OperaParteMaterialPK pk, OperaParteMaterial dto) throws DAOException {
		OperaParteMaterial oldOperaParteMaterial = getOldOperaParteMaterial(pk);
		
		try {
			if (dto == null) {
				throw new OperationsException("El parte-material a modificar no puede ser nulo.");
			} else {
				if (!listaOperaParteMaterial.contains(oldOperaParteMaterial)) {
					throw new OperationsException("El parte-material a modificar no existe.");
					
				} else {
					operaParteMaterialBD.update(pk, dto);
					
					listaOperaParteMaterial.set(listaOperaParteMaterial.indexOf(oldOperaParteMaterial), dto);
				}
			}
		} catch (Exception e) {
			throwException(e);
		}
	}
	
	public void updateAll(ObservableMap<OperaParteMaterialPK, OperaParteMaterial> mapToUpdate) throws DAOException {
		for (Map.Entry<OperaParteMaterialPK, OperaParteMaterial> entry : mapToUpdate.entrySet()) {
			update(entry.getKey(), entry.getValue());
		}
	}


	public void delete(OperaParteMaterialPK pk) throws DAOException {
		System.out.println(pk);
		OperaParteMaterial oldOperaParteMaterial = getOldOperaParteMaterial(pk);
		
		try {
			if (pk == null) {
				throw new NullPointerException("No se puede eliminar un parte-material nulo.");
			}
			if (!listaOperaParteMaterial.contains(oldOperaParteMaterial)) {
				throw new OperationsException("El parte-material a eliminar no existe.");
				
			} else {
				operaParteMaterialBD.delete(pk);
				
				listaOperaParteMaterial.remove(oldOperaParteMaterial);
			}
		} catch (Exception e) {
			throwException(e);
		}
	}

	public void deleteAll(ObservableList<OperaParteMaterial> listToDelete) throws DAOException {
		for (OperaParteMaterial dto : listToDelete) {
			delete(dto.createPK());
		}
	}

	public void findAll() throws DAOException {
		try {
			listaOperaParteMaterial.setAll(operaParteMaterialBD.findAll());
			
		} catch (SQLException e) {
			throwException(e);
		}
	}
	
	public OperaParteMaterial findByPrimaryKey(OperaParteMaterialPK pk) throws DAOException {
		try {
			return operaParteMaterialBD.findByPrimaryKey(pk);
			
		} catch (SQLException e) {
			throwException(e);
		}
		return null;
	}
	
	public void setMaxRows(int maxRows) {
		operaParteMaterialBD.setMaxRows(maxRows);
	}

	public int getMaxRows() {
		return operaParteMaterialBD.getMaxRows();
	}
	
	public ObservableList<OperaParteMaterial> getParteMateriales(Parte dto) {
		ObservableList<OperaParteMaterial> listToReturn = FXCollections.observableArrayList();
		for (OperaParteMaterial dtoOnList : listaOperaParteMaterial) {
			if (dtoOnList.getCodParte() == dto.getCodParte()) {
				listToReturn.add(dtoOnList);
			}
		}
		return listToReturn;
	}

	public void createDependencies(ParteDAO parteDAO, MaterialDAO materialDAO) {
		for (OperaParteMaterial opm : listaOperaParteMaterial) {
			opm.setObjParte(dependencyOperaParteMaterialParte(opm, parteDAO));
			opm.setObjMaterial(dependencyOperaParteMaterialMaterial(opm, materialDAO));
		}
	}
	
	private Parte dependencyOperaParteMaterialParte(OperaParteMaterial dto, ParteDAO parteDAO) {
		Parte parteSalida = null;
		for (Parte parte : parteDAO.getPartes()) {
			if (parte.getCodParte() == dto.getCodParte()) {
				parteSalida = parte;
			}
		}
		return parteSalida;
	}

	private Material dependencyOperaParteMaterialMaterial(OperaParteMaterial dto, MaterialDAO materialDAO) {
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

	private OperaParteMaterial getOldOperaParteMaterial(OperaParteMaterialPK pk) {
		OperaParteMaterial oldOperaParteMaterial = new OperaParteMaterial();
		oldOperaParteMaterial.setCodOperacion(pk.getCodOperacion());
		oldOperaParteMaterial = listaOperaParteMaterial.get(listaOperaParteMaterial.indexOf(oldOperaParteMaterial));
		return oldOperaParteMaterial;
	}
}
