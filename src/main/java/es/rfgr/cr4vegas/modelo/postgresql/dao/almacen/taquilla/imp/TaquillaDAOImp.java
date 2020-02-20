package es.rfgr.cr4vegas.modelo.postgresql.dao.almacen.taquilla.imp;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

import javax.management.OperationsException;

import es.rfgr.cr4vegas.modelo.postgresql.bd.almacen.taquilla.TaquillaBD;
import es.rfgr.cr4vegas.modelo.postgresql.dao.almacen.taquilla.TaquillaDAO;
import es.rfgr.cr4vegas.modelo.postgresql.dao.almacen.taquilla.TipoTaquillaDAO;
import es.rfgr.cr4vegas.modelo.postgresql.dto.almacen.taquilla.Taquilla;
import es.rfgr.cr4vegas.modelo.postgresql.dto.almacen.taquilla.TaquillaPK;
import es.rfgr.cr4vegas.modelo.postgresql.dto.almacen.taquilla.TipoTaquilla;
import es.rfgr.cr4vegas.modelo.postgresql.exceptions.DAOException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;

public class TaquillaDAOImp implements TaquillaDAO {

	private static final String ERROR = "TaquillaDAO: ";

	private ObservableList<Taquilla> listaTaquillas;

	private TaquillaBD taquillaBD;

	public TaquillaDAOImp() {
		listaTaquillas = FXCollections.observableArrayList();
		taquillaBD = new TaquillaBD();
	}

	public void setConnection(Connection conn) {
		taquillaBD.setConnection(conn);
	}

	public ObservableList<Taquilla> getTaquillas() {
		return listaTaquillas;
	}

	public int getNumTaquillas() {
		return listaTaquillas.size();
	}

	public TaquillaPK insert(Taquilla dto) throws DAOException {
		boolean existe = false;
		TaquillaPK pk = null;

		try {
			for (Taquilla taquilla : listaTaquillas) {
				if (dto.getLugar().equals(taquilla.getLugar())) {
					if (dto.getObjTipoTaquilla().equals(taquilla.getObjTipoTaquilla())) {
						existe = true;
					}
				}
			}
			if (existe) {
				throw new OperationsException("La taquilla ya existe.");

			} else {
				pk = taquillaBD.insert(dto);
				
				dto.setCodTaquilla(pk.getCodTaquilla());

				this.listaTaquillas.add(dto);
			}
		} catch (Exception e) {
			throwException(e);
		}
		return pk;
	}
	
	public ObservableList<TaquillaPK> insertAll(ObservableList<Taquilla> listToInsert) throws DAOException {
		ObservableList<TaquillaPK> listToReturn = FXCollections.observableArrayList();
		for (Taquilla dto : listToInsert) {
			TaquillaPK dtoPK = insert(dto);
			if (dtoPK != null) {
				listToReturn.add(dtoPK);
			}
		}
		return listToReturn;
	}

	public void update(TaquillaPK pk, Taquilla dto) throws DAOException {
		Taquilla oldTaquilla = getOldTaquilla(pk);

		try {
			if (dto == null) {
				throw new OperationsException("La taquilla a modificar no puede ser nula.");
			}
			if (!listaTaquillas.contains(oldTaquilla)) {
				throw new OperationsException("La taquilla a modificar no existe.");
				
			} else {
				taquillaBD.update(pk, dto);

				listaTaquillas.set(listaTaquillas.indexOf(oldTaquilla), dto);
			}
		} catch (Exception e) {
			throwException(e);
		}
	}
	
	public void updateAll(ObservableMap<TaquillaPK, Taquilla> mapToUpdate) throws DAOException {
		for (Map.Entry<TaquillaPK, Taquilla> entry : mapToUpdate.entrySet()) {
			update(entry.getKey(), entry.getValue());
		}
	}

	public void delete(TaquillaPK pk) throws DAOException {
		Taquilla oldTaquilla = getOldTaquilla(pk);

		try {
			if (pk == null) {
				throw new NullPointerException("No se puede eliminar una taquilla nula.");
			}
			if (!listaTaquillas.contains(oldTaquilla)) {
				throw new OperationsException("La taquilla a eliminar no existe.");
				
			} else {
				taquillaBD.delete(pk);

				listaTaquillas.remove(oldTaquilla);
			}
		} catch (Exception e) {
			throwException(e);	
		}
	}
	
	public void deleteAll(ObservableList<Taquilla> listToDelete) throws DAOException {
		for (Taquilla dto : listToDelete) {
			delete(dto.createPK());
		}
	}

	public void findAll() throws DAOException {
		try {
			listaTaquillas.setAll(taquillaBD.findAll());

		} catch (SQLException e) {
			throwException(e);
		}
	}
	
	public Taquilla findByPrimaryKey(TaquillaPK pk) throws DAOException {
		try {
			return taquillaBD.findByPrimaryKey(pk);
			
		} catch (SQLException e) {
			throwException(e);
		}
		return null;
	}
	
	public void setMaxRows(int maxRows) {
		taquillaBD.setMaxRows(maxRows);
	}

	public int getMaxRows() {
		return taquillaBD.getMaxRows();
	}

	public void createDependencies(TipoTaquillaDAO tiposTaquilla) {
		for (Taquilla taquilla : listaTaquillas) {
			taquilla.setObjTipoTaquilla(dependencyTaquillaTipo(taquilla, tiposTaquilla));
		}
	}

	private TipoTaquilla dependencyTaquillaTipo(Taquilla dto, TipoTaquillaDAO dtoDAO) {
		for (TipoTaquilla tipoTaquilla : dtoDAO.getTiposTaquilla()) {
			if (tipoTaquilla.getNombre().equals(dto.getTipoTaquilla())) {
				return tipoTaquilla;
			}
		}
		return null;
	}

	private void throwException(Exception e) throws DAOException {
		throw new DAOException(ERROR + e.getMessage(), e);
	}

	private Taquilla getOldTaquilla(TaquillaPK pk) {
		Taquilla oldTaquilla = new Taquilla();
		oldTaquilla.setCodTaquilla(pk.getCodTaquilla());
		oldTaquilla = listaTaquillas.get(listaTaquillas.indexOf(oldTaquilla));
		return oldTaquilla;
	}
}
