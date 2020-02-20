package es.rfgr.cr4vegas.modelo.postgresql.dao.almacen.material.imp;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

import javax.management.OperationsException;

import es.rfgr.cr4vegas.modelo.postgresql.bd.almacen.material.PrecioBD;
import es.rfgr.cr4vegas.modelo.postgresql.dao.almacen.material.MaterialDAO;
import es.rfgr.cr4vegas.modelo.postgresql.dao.almacen.material.PrecioDAO;
import es.rfgr.cr4vegas.modelo.postgresql.dao.almacen.tienda.TiendaDAO;
import es.rfgr.cr4vegas.modelo.postgresql.dto.almacen.material.Material;
import es.rfgr.cr4vegas.modelo.postgresql.dto.almacen.material.Precio;
import es.rfgr.cr4vegas.modelo.postgresql.dto.almacen.material.PrecioPK;
import es.rfgr.cr4vegas.modelo.postgresql.dto.almacen.tienda.Tienda;
import es.rfgr.cr4vegas.modelo.postgresql.exceptions.DAOException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;

public class PrecioDAOImp implements PrecioDAO {

	private static final String ERROR = "PrecioDAO: ";

	private ObservableList<Precio> listaPrecios;

	private PrecioBD precioBD;

	public PrecioDAOImp() {
		listaPrecios = FXCollections.observableArrayList();
		precioBD = new PrecioBD();
	}

	public void setConnection(Connection conn) {
		precioBD.setConnection(conn);
	}

	public ObservableList<Precio> getPrecios() {
		return listaPrecios;
	}

	public int getNumPrecios() {
		return listaPrecios.size();
	}

	public PrecioPK insert(Precio dto) throws DAOException {
		PrecioPK pk = dto.createPK();

		try {
			if (listaPrecios.contains(dto)) {
				throw new OperationsException("El precio ya existe.");
			} else {
				pk = precioBD.insert(dto);

				this.listaPrecios.add(dto);
			}
		} catch (Exception e) {
			throwException(e);
		}
		return pk;
	}

	public ObservableList<PrecioPK> insertAll(ObservableList<Precio> listToInsert) throws DAOException {
		ObservableList<PrecioPK> listToReturn = FXCollections.observableArrayList();
		for (Precio dto : listToInsert) {
			PrecioPK dtoPK = insert(dto);
			if (dtoPK != null) {
				listToReturn.add(dtoPK);
			}
		}
		return listToReturn;
	}

	public void update(PrecioPK pk, Precio dto) throws DAOException {
		Precio oldPrecio = getOldPrecio(pk);

		try {
			if (dto == null) {
				throw new OperationsException("El precio a modificar no puede ser nulo.");
			}
			if (!listaPrecios.contains(oldPrecio)) {
				throw new OperationsException("El precio a modificar no existe.");

			} else {
				precioBD.update(pk, dto);

				oldPrecio.copiarValores(dto);
			}
		} catch (Exception e) {
			throwException(e);
		}
	}

	public void updateAll(ObservableMap<PrecioPK, Precio> mapToUpdate) throws DAOException {
		for (Map.Entry<PrecioPK, Precio> entry : mapToUpdate.entrySet()) {
			update(entry.getKey(), entry.getValue());
		}
	}

	public void delete(PrecioPK pk) throws DAOException {
		Precio oldPrecio = getOldPrecio(pk);

		try {
			if (pk == null) {
				throw new NullPointerException("No se puede eliminar un precio nulo.");
			}
			if (!listaPrecios.contains(oldPrecio)) {
				throw new OperationsException("El precio a eliminar no existe.");

			} else {
				precioBD.delete(pk);

				listaPrecios.remove(oldPrecio);
			}
		} catch (Exception e) {
			throwException(e);
		}
	}

	public void deleteAll(ObservableList<Precio> listToDelete) throws DAOException {
		for (Precio dto : listToDelete) {
			delete(dto.createPK());
		}
	}

	public void findAll() throws DAOException {
		try {
			listaPrecios.setAll(precioBD.findAll());

		} catch (SQLException e) {
			throwException(e);
		}
	}

	public Precio findByPrimaryKey(PrecioPK pk) throws DAOException {
		try {
			return precioBD.findByPrimaryKey(pk);

		} catch (SQLException e) {
			throwException(e);
		}
		return null;
	}

	public void setMaxRows(int maxRows) {
		precioBD.setMaxRows(maxRows);
	}

	public int getMaxRows() {
		return precioBD.getMaxRows();
	}

	private void throwException(Exception e) throws DAOException {
		throw new DAOException(ERROR + e.getMessage(), e);
	}

	private Precio getOldPrecio(PrecioPK pk) {
		Precio oldPrecio = new Precio();
		oldPrecio.setCodigo4v(pk.getCodigo4v());
		oldPrecio.setCodtienda(pk.getCodTienda());
		System.out.println(oldPrecio);
		oldPrecio = listaPrecios.get(listaPrecios.indexOf(oldPrecio));
		return oldPrecio;
	}

	public Precio getPrecioConMaterialYTienda(Material material, Tienda tienda) {
		if (material != null && tienda != null) {
			for (Precio p : listaPrecios) {
				if (p.getCodigo4v() == material.getCodigo4v() && p.getCodtienda() == tienda.getCodTienda()) {
					return p;
				}
			}
		}
		return null;
	}
	
	public ObservableList<Precio> getPreciosMaterial(Material material) {
		ObservableList<Precio> listaSalida = FXCollections.observableArrayList();
		for (Precio precio : listaPrecios) {
			if (precio.getObjMaterial().equals(material)) {
				listaSalida.add(precio);
			}
		}
		return listaSalida;
	}

	@Override
	public void createDependencies(MaterialDAO materialDAO, TiendaDAO tiendaDAO) {
		for (Precio precio : listaPrecios) {
			crearDependenciaMaterialDAO(precio, materialDAO);
			crearDependenciaTiendaDAO(precio, tiendaDAO);
		}
	}

	private void crearDependenciaMaterialDAO(Precio precio, MaterialDAO materialDAO) {
		for (Material material : materialDAO.getMateriales()) {
			if (precio.getCodigo4v() == material.getCodigo4v()) {
				precio.setObjMaterial(material);
				break;
			}
		}
	}

	private void crearDependenciaTiendaDAO(Precio precio, TiendaDAO tiendaDAO) {
		for (Tienda tienda : tiendaDAO.getTiendas()) {
			if (precio.getCodtienda() == tienda.getCodTienda()) {
				precio.setObjTienda(tienda);
				break;
			}
		}
	}
}
