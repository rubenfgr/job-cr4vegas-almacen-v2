package es.rfgr.cr4vegas.modelo.postgresql.dao.almacen.compra.imp;

import java.sql.Connection;
import java.util.Map;

import javax.management.OperationsException;

import es.rfgr.cr4vegas.modelo.postgresql.bd.almacen.compra.CompraMaterialBD;
import es.rfgr.cr4vegas.modelo.postgresql.dao.almacen.compra.CompraDAO;
import es.rfgr.cr4vegas.modelo.postgresql.dao.almacen.compra.CompraMaterialDAO;
import es.rfgr.cr4vegas.modelo.postgresql.dao.almacen.material.MaterialDAO;
import es.rfgr.cr4vegas.modelo.postgresql.dao.almacen.material.PrecioDAO;
import es.rfgr.cr4vegas.modelo.postgresql.dto.almacen.compra.Compra;
import es.rfgr.cr4vegas.modelo.postgresql.dto.almacen.compra.CompraMaterial;
import es.rfgr.cr4vegas.modelo.postgresql.dto.almacen.compra.CompraMaterialPK;
import es.rfgr.cr4vegas.modelo.postgresql.dto.almacen.material.Material;
import es.rfgr.cr4vegas.modelo.postgresql.dto.almacen.material.Precio;
import es.rfgr.cr4vegas.modelo.postgresql.exceptions.DAOException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;

public class CompraMaterialDAOImp implements CompraMaterialDAO {

	private static final String ERROR = "CompraMaterialDAO: ";

	private ObservableList<CompraMaterial> listaComprasMaterial;

	private CompraMaterialBD compraMaterialBD;

	public CompraMaterialDAOImp() {
		listaComprasMaterial = FXCollections.observableArrayList();
		compraMaterialBD = new CompraMaterialBD();
	}

	public void setConnection(Connection conn) {
		compraMaterialBD.setConnection(conn);
	}

	public ObservableList<CompraMaterial> getComprasMaterial() {
		return listaComprasMaterial;
	}

	public int getNumComprasMaterial() {
		return listaComprasMaterial.size();
	}

	public CompraMaterialPK insert(CompraMaterial dto) throws DAOException {
		CompraMaterialPK pk = null;
		try {
			if (listaComprasMaterial.contains(dto)) {
				throw new OperationsException("El material de la compra a insertar ya existe.");

			} else {
				pk = compraMaterialBD.insert(dto);

				listaComprasMaterial.add(dto);
			}
		} catch (Exception e) {
			throwException(e);
		}
		return pk;
	}

	public ObservableList<CompraMaterialPK> insertAll(ObservableList<CompraMaterial> listToInsert) throws DAOException {
		ObservableList<CompraMaterialPK> listToReturn = FXCollections.observableArrayList();
		for (CompraMaterial dto : listToInsert) {
			CompraMaterialPK dtoPK = insert(dto);
			if (dtoPK != null) {
				listToReturn.add(dtoPK);
			}
		}
		return listToReturn;
	}

	public void update(CompraMaterialPK pk, CompraMaterial dto) throws DAOException {
		CompraMaterial oldCompraMaterial = getOldCompraMaterial(pk);

		try {
			if (pk == null) {
				throw new NullPointerException("El material de la compra a modificar no puede ser nulo.");
			}
			if (!listaComprasMaterial.contains(oldCompraMaterial)) {
				throw new OperationsException("El material de la compra a modificar no existe.");

			} else {
				compraMaterialBD.update(dto.createPK(), dto);

				listaComprasMaterial.set(listaComprasMaterial.indexOf(oldCompraMaterial), dto);
			}
		} catch (Exception e) {
			throwException(e);
		}
	}

	public void updateAll(ObservableMap<CompraMaterialPK, CompraMaterial> mapToUpdate) throws DAOException {
		for (Map.Entry<CompraMaterialPK, CompraMaterial> entry : mapToUpdate.entrySet()) {
			update(entry.getKey(), entry.getValue());
		}
	}

	public void delete(CompraMaterialPK pk) throws DAOException {
		CompraMaterial oldCompraMaterial = getOldCompraMaterial(pk);

		try {
			if (pk == null) {
				throw new NullPointerException("El material de la compra a eliminar no puede ser nulo.");
			}
			if (!listaComprasMaterial.contains(oldCompraMaterial)) {
				throw new OperationsException("El material de la compra a eliminar no existe.");

			} else {
				compraMaterialBD.delete(pk);

				listaComprasMaterial.remove(oldCompraMaterial);
			}
		} catch (Exception e) {
			throwException(e);
		}
	}

	public void deleteAll(ObservableList<CompraMaterial> listToDelete) throws DAOException {
		for (CompraMaterial dtoOnList : listToDelete) {
			delete(dtoOnList.createPK());
		}
	}

	public void findAll() throws DAOException {
		try {
			listaComprasMaterial.setAll(compraMaterialBD.findAll());
		} catch (Exception e) {
			throwException(e);
		}
	}

	public CompraMaterial findByPrimaryKey(CompraMaterialPK pk) throws DAOException {
		try {
			return compraMaterialBD.findByPrimaryKey(pk);

		} catch (Exception e) {
			throwException(e);
		}
		return null;
	}

	public void setMaxRows(int maxRows) {
		compraMaterialBD.setMaxRows(maxRows);
	}

	public int getMaxRows() {
		return compraMaterialBD.getMaxRows();
	}

	public ObservableList<CompraMaterial> getCompraMateriales(Compra dto) {
		ObservableList<CompraMaterial> listToReturn = FXCollections.observableArrayList();
		for (CompraMaterial dtoOnList : listaComprasMaterial) {
			if (dtoOnList.getCodCompra() == dto.getCodCompra()) {
				listToReturn.add(dtoOnList);
			}
		}
		return listToReturn;
	}

	public void createDependencies(CompraDAO compraDAO, MaterialDAO materialDAO, PrecioDAO precioDAO) {
		for (CompraMaterial compraMaterial : listaComprasMaterial) {
			compraMaterial.setObjCompra(dependencyCompraMaterialCompra(compraMaterial, compraDAO));
			compraMaterial.setObjMaterial(dependencyCompraMaterialMaterial(compraMaterial, materialDAO));
			compraMaterial.setObjPrecio(dependencyPrecio(compraMaterial, precioDAO));
		}
	}

	private Compra dependencyCompraMaterialCompra(CompraMaterial dto, CompraDAO compraDAO) {
		for (Compra compra : compraDAO.getCompras()) {
			if (compra.getCodCompra() == dto.getCodCompra()) {
				return compra;
			}
		}
		return null;
	}

	private Material dependencyCompraMaterialMaterial(CompraMaterial dto, MaterialDAO materialDAO) {
		for (Material material : materialDAO.getMateriales()) {
			if (material.getCodigo4v() == dto.getCodigo4v()) {
				return material;
			}
		}
		return null;
	}

	private Precio dependencyPrecio(CompraMaterial dto, PrecioDAO precioDAO) {
		return precioDAO.getPrecioConMaterialYTienda(dto.getObjMaterial(), dto.getObjCompra().getObjTienda());
	}

	private void throwException(Exception e) throws DAOException {
		throw new DAOException(ERROR + e.getMessage(), e);
	}

	private CompraMaterial getOldCompraMaterial(CompraMaterialPK pk) {
		CompraMaterial oldCompraMaterial = new CompraMaterial();
		oldCompraMaterial.setCodCompra(pk.getCodCompra());
		oldCompraMaterial.setCodigo4v(pk.getCodigo4v());
		oldCompraMaterial = listaComprasMaterial.get(listaComprasMaterial.indexOf(oldCompraMaterial));
		return oldCompraMaterial;
	}

}
