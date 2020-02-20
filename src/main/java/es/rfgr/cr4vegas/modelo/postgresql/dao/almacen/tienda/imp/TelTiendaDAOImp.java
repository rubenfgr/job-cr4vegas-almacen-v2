package es.rfgr.cr4vegas.modelo.postgresql.dao.almacen.tienda.imp;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

import javax.management.OperationsException;

import es.rfgr.cr4vegas.modelo.postgresql.bd.almacen.tienda.TelTiendaBD;
import es.rfgr.cr4vegas.modelo.postgresql.dao.almacen.tienda.TelTiendaDAO;
import es.rfgr.cr4vegas.modelo.postgresql.dao.almacen.tienda.TiendaDAO;
import es.rfgr.cr4vegas.modelo.postgresql.dto.almacen.tienda.TelTienda;
import es.rfgr.cr4vegas.modelo.postgresql.dto.almacen.tienda.TelTiendaPK;
import es.rfgr.cr4vegas.modelo.postgresql.dto.almacen.tienda.Tienda;
import es.rfgr.cr4vegas.modelo.postgresql.exceptions.DAOException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;

public class TelTiendaDAOImp implements TelTiendaDAO {

	private static final String ERROR = "TelTiendaDAO: ";

	private ObservableList<TelTienda> listaTelTiendas;

	private TelTiendaBD telTiendaBD;

	public TelTiendaDAOImp() {
		listaTelTiendas = FXCollections.observableArrayList();
		telTiendaBD = new TelTiendaBD();
	}

	public void setConnection(Connection conn) {
		telTiendaBD.setConnection(conn);
	}

	public ObservableList<TelTienda> getTelTiendas() {
		return listaTelTiendas;
	}

	public int getNumTelTiendas() {
		return listaTelTiendas.size();
	}

	public TelTiendaPK insert(TelTienda dto) throws DAOException {
		TelTiendaPK pk = dto.createPK();
		try {
			if (listaTelTiendas.contains(dto)) {
				throw new OperationsException("El teléfono de tienda ya existe.");

			} else {
				pk = telTiendaBD.insert(dto);

				this.listaTelTiendas.add(dto);
			}
		} catch (Exception e) {
			throwException(e);
		}

		return pk;
	}

	public ObservableList<TelTiendaPK> insertAll(ObservableList<TelTienda> listToInsert) throws DAOException {
		ObservableList<TelTiendaPK> listToReturn = FXCollections.observableArrayList();
		for (TelTienda dto : listToInsert) {
			TelTiendaPK dtoPK = insert(dto);
			if (dtoPK != null) {
				listToReturn.add(dtoPK);
			}
		}
		return listToReturn;
	}

	public void update(TelTiendaPK pk, TelTienda dto) throws DAOException {
		try {
			TelTienda oldTelTienda = getOldTelTienda(pk);

			if (dto == null) {
				throw new OperationsException("El teléfono de tienda a modificar no puede ser nulo.");
			}
			if (!listaTelTiendas.contains(oldTelTienda)) {
				throw new OperationsException("El teléfono de tienda a modificar no existe.");

			} else {
				telTiendaBD.update(pk, dto);

				listaTelTiendas.set(listaTelTiendas.indexOf(oldTelTienda), dto);
			}
		} catch (Exception e) {
			throwException(e);
		}
	}

	public void updateAll(ObservableMap<TelTiendaPK, TelTienda> mapToUpdate) throws DAOException {
		for (Map.Entry<TelTiendaPK, TelTienda> entry : mapToUpdate.entrySet()) {
			update(entry.getKey(), entry.getValue());
		}
	}

	public void delete(TelTiendaPK pk) throws DAOException {
		try {
			TelTienda oldTelTienda = getOldTelTienda(pk);
			
			if (listaTelTiendas.contains(oldTelTienda)) {
				telTiendaBD.delete(pk);

				listaTelTiendas.remove(oldTelTienda);
			}
		} catch (Exception e) {
			throwException(e);
		}
	}

	public void deleteAll(ObservableList<TelTienda> listToDelete) throws DAOException {
		for (TelTienda dto : listToDelete) {
			delete(dto.createPK());
		}
	}

	public void findAll() throws DAOException {
		try {
			listaTelTiendas.setAll(telTiendaBD.findAll());

		} catch (Exception e) {
			throwException(e);
		}
	}

	public TelTienda findByPrimaryKey(TelTiendaPK pk) throws DAOException {
		try {
			return telTiendaBD.findByPrimaryKey(pk);

		} catch (SQLException e) {
			throwException(e);
		}
		return null;
	}

	public void setMaxRows(int maxRows) {
		telTiendaBD.setMaxRows(maxRows);
	}

	public int getMaxRows() {
		return telTiendaBD.getMaxRows();
	}

	public ObservableList<TelTienda> getTelTienda(Tienda dto) {
		ObservableList<TelTienda> listToReturn = FXCollections.observableArrayList();
		for (TelTienda telTienda : listaTelTiendas) {
			if (telTienda.getCodTienda() == dto.getCodTienda()) {
				listToReturn.add(telTienda);
			}
		}
		return listToReturn;
	}

	public void createDependencies(TiendaDAO tiendaDAO) {
		for (TelTienda telTienda : listaTelTiendas) {
			telTienda.setObjTienda(dependencyTelTiendaTienda(telTienda, tiendaDAO));
		}
	}

	private Tienda dependencyTelTiendaTienda(TelTienda dto, TiendaDAO tiendaDAO) {
		for (Tienda tienda : tiendaDAO.getTiendas()) {
			if (tienda.getCodTienda() == dto.getCodTienda()) {
				return tienda;
			}
		}
		return null;
	}

	private void throwException(Exception e) throws DAOException {
		throw new DAOException(ERROR + e.getMessage(), e);
	}

	private TelTienda getOldTelTienda(TelTiendaPK pk) {
		TelTienda oldTelTienda = new TelTienda();
		oldTelTienda.setTelefono(pk.getTelefono());
		
		if (listaTelTiendas.contains(oldTelTienda)) {
			return listaTelTiendas.get(listaTelTiendas.indexOf(oldTelTienda));
			
		} else {
			return null;
		}
	}

}
