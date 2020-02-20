package es.rfgr.cr4vegas.modelo.postgresql.dao.almacen.tienda.imp;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

import javax.management.OperationsException;

import es.rfgr.cr4vegas.modelo.postgresql.bd.almacen.tienda.TiendaBD;
import es.rfgr.cr4vegas.modelo.postgresql.dao.almacen.tienda.TiendaDAO;
import es.rfgr.cr4vegas.modelo.postgresql.dto.almacen.tienda.Tienda;
import es.rfgr.cr4vegas.modelo.postgresql.dto.almacen.tienda.TiendaPK;
import es.rfgr.cr4vegas.modelo.postgresql.exceptions.DAOException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;

public class TiendaDAOImp implements TiendaDAO {

	private static final String ERROR = "TiendaDAO: ";

	private ObservableList<Tienda> listaTiendas;

	private TiendaBD tiendaBD;

	public TiendaDAOImp() {
		listaTiendas = FXCollections.observableArrayList();
		tiendaBD = new TiendaBD();
	}

	public void setConnection(Connection conn) {
		tiendaBD.setConnection(conn);
	}

	public ObservableList<Tienda> getTiendas() {
		return listaTiendas;
	}

	public int getNumTiendas() {
		return listaTiendas.size();
	}

	public TiendaPK insert(Tienda dto) throws DAOException {
		TiendaPK pk = dto.createPK();

		try {
			if (listaTiendas.contains(dto)) {
				throw new OperationsException("La tienda ya existe.");

			} else {
				pk = tiendaBD.insert(dto);

				listaTiendas.add(dto);
			}
		} catch (Exception e) {
			throwException(e);
		}
		return pk;
	}

	public ObservableList<TiendaPK> insertAll(ObservableList<Tienda> listToInsert) throws DAOException {
		ObservableList<TiendaPK> listToReturn = FXCollections.observableArrayList();
		for (Tienda dto : listToInsert) {
			TiendaPK dtoPK = insert(dto);
			if (dtoPK != null) {
				listToReturn.add(dtoPK);
			}
		}
		return listToReturn;
	}

	public void update(TiendaPK pk, Tienda dto) throws DAOException {
		Tienda oldTienda = getOldTienda(pk);

		try {
			if (dto == null) {
				throw new OperationsException("La tienda a modificar no puede ser nula.");
			}
			if (!listaTiendas.contains(oldTienda)) {
				throw new OperationsException("La tienda a modificar no existe.");

			} else {
				tiendaBD.update(pk, dto);

				oldTienda.copiarValores(dto);
			}
		} catch (Exception e) {
			throwException(e);
		}
	}

	public void updateAll(ObservableMap<TiendaPK, Tienda> mapToUpdate) throws DAOException {
		for (Map.Entry<TiendaPK, Tienda> entry : mapToUpdate.entrySet()) {
			update(entry.getKey(), entry.getValue());
		}
	}

	public void delete(TiendaPK pk) throws DAOException {
		Tienda oldTienda = getOldTienda(pk);

		try {
			if (pk == null) {
				throw new NullPointerException("No se puede eliminar una tienda nula.");
			}
			if (!listaTiendas.contains(oldTienda)) {
				throw new OperationsException("La tienda a eliminar no existe.");

			} else {
				tiendaBD.delete(pk);

				listaTiendas.remove(oldTienda);
			}
		} catch (Exception e) {
			throwException(e);
		}
	}

	public void deleteAll(ObservableList<Tienda> listToDelete) throws DAOException {
		for (Tienda dto : listToDelete) {
			delete(dto.createPK());
		}
	}

	public void findAll() throws DAOException {
		try {
			listaTiendas.setAll(tiendaBD.findAll());
		} catch (Exception e) {
			throwException(e);
		}
	}

	public Tienda findByPrimaryKey(TiendaPK pk) throws DAOException {
		try {
			return tiendaBD.findByPrimaryKey(pk);

		} catch (SQLException e) {
			throwException(e);
		}
		return null;
	}
	
	public void setMaxRows(int maxRows) {
		tiendaBD.setMaxRows(maxRows);
	}

	public int getMaxRows() {
		return tiendaBD.getMaxRows();
	}

	private void throwException(Exception e) throws DAOException {
		throw new DAOException(ERROR + e.getMessage(), e);
	}

	private Tienda getOldTienda(TiendaPK pk) {
		Tienda oldTienda = new Tienda();
		oldTienda.setCodTienda(pk.getCodTienda());
		oldTienda = listaTiendas.get(listaTiendas.indexOf(oldTienda));
		return oldTienda;
	}
}
