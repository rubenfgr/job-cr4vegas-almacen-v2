package es.rfgr.cr4vegas.modelo.postgresql.dao.mantenimiento.unidaddecontrol.imp;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

import javax.management.OperationsException;

import es.rfgr.cr4vegas.modelo.postgresql.bd.mantenimiento.unidaddecontrol.GeneraUnidadDeControlEventoBD;
import es.rfgr.cr4vegas.modelo.postgresql.dao.evento.EventoDAO;
import es.rfgr.cr4vegas.modelo.postgresql.dao.mantenimiento.unidaddecontrol.GeneraUnidadDeControlEventoDAO;
import es.rfgr.cr4vegas.modelo.postgresql.dao.mantenimiento.unidaddecontrol.MantenimientoUnidadDeControlDAO;
import es.rfgr.cr4vegas.modelo.postgresql.dto.evento.Evento;
import es.rfgr.cr4vegas.modelo.postgresql.dto.mantenimiento.unidaddecontrol.GeneraUnidadDeControlEvento;
import es.rfgr.cr4vegas.modelo.postgresql.dto.mantenimiento.unidaddecontrol.GeneraUnidadDeControlEventoPK;
import es.rfgr.cr4vegas.modelo.postgresql.dto.mantenimiento.unidaddecontrol.MantenimientoUnidadDeControl;
import es.rfgr.cr4vegas.modelo.postgresql.exceptions.DAOException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;

public class GeneraUnidadDeControlEventoDAOImp implements GeneraUnidadDeControlEventoDAO {

	private static final String ERROR = "GeneraUdControlEventoDAO: ";

	private ObservableList<GeneraUnidadDeControlEvento> listaGeneraUdsControlEvento;

	private GeneraUnidadDeControlEventoBD generaUdControlEventoBD;

	public GeneraUnidadDeControlEventoDAOImp() {
		listaGeneraUdsControlEvento = FXCollections.observableArrayList();
		generaUdControlEventoBD = new GeneraUnidadDeControlEventoBD();
	}

	public void setConnection(Connection conn) {
		generaUdControlEventoBD.setConnection(conn);
	}

	public ObservableList<GeneraUnidadDeControlEvento> getGeneraUdsControlEvento() {
		return listaGeneraUdsControlEvento;
	}

	public int getNumGeneraUdsControlEvento() {
		return listaGeneraUdsControlEvento.size();
	}

	public GeneraUnidadDeControlEventoPK insert(GeneraUnidadDeControlEvento dto) throws DAOException {
		GeneraUnidadDeControlEventoPK pk = dto.createPK();

		try {
			if (listaGeneraUdsControlEvento.contains(dto)) {
				throw new OperationsException("El Ud.Control-evento ya existe.");

			} else {
				pk = generaUdControlEventoBD.insert(dto);

				this.listaGeneraUdsControlEvento.add(dto);
			}
		} catch (Exception e) {
			throwException(e);
		}
		return pk;
	}

	public ObservableList<GeneraUnidadDeControlEventoPK> insertAll(
			ObservableList<GeneraUnidadDeControlEvento> listToInsert) throws DAOException {
		ObservableList<GeneraUnidadDeControlEventoPK> listToReturn = FXCollections.observableArrayList();
		for (GeneraUnidadDeControlEvento dto : listToInsert) {
			GeneraUnidadDeControlEventoPK dtoPK = insert(dto);
			if (dtoPK != null) {
				listToReturn.add(dtoPK);
			}
		}
		return listToReturn;
	}

	public void update(GeneraUnidadDeControlEventoPK pk, GeneraUnidadDeControlEvento dto) throws DAOException {
		GeneraUnidadDeControlEvento oldGeneraUDControlEvento = getOldGeneraUDControlEvento(pk);

		try {
			if (dto == null) {
				throw new OperationsException("El Ud.Control-evento a modificar no puede ser nulo.");
			}
			if (!listaGeneraUdsControlEvento.contains(oldGeneraUDControlEvento)) {
				throw new OperationsException("El Ud.Control-evento a modificar no existe.");

			} else {
				generaUdControlEventoBD.update(pk, dto);

				listaGeneraUdsControlEvento.set(listaGeneraUdsControlEvento.indexOf(oldGeneraUDControlEvento), dto);
			}
		} catch (Exception e) {
			throwException(e);
		}
	}

	public void updateAll(ObservableMap<GeneraUnidadDeControlEventoPK, GeneraUnidadDeControlEvento> mapToUpdate)
			throws DAOException {
		for (Map.Entry<GeneraUnidadDeControlEventoPK, GeneraUnidadDeControlEvento> entry : mapToUpdate.entrySet()) {
			update(entry.getKey(), entry.getValue());
		}
	}

	public void delete(GeneraUnidadDeControlEventoPK pk) throws DAOException {
		GeneraUnidadDeControlEvento oldGeneraUDControlEvento = getOldGeneraUDControlEvento(pk);

		try {
			if (pk == null) {
				throw new NullPointerException("No se puede eliminar un Ud.Control-evento nulo.");
			}
			if (!listaGeneraUdsControlEvento.contains(oldGeneraUDControlEvento)) {
				throw new OperationsException("El Ud.Control-evento a eliminar no existe.");

			} else {
				generaUdControlEventoBD.delete(pk);

				listaGeneraUdsControlEvento.remove(oldGeneraUDControlEvento);
			}
		} catch (Exception e) {
			throwException(e);
		}
	}

	public void deleteAll(ObservableList<GeneraUnidadDeControlEvento> listToDelete) throws DAOException {
		for (GeneraUnidadDeControlEvento dto : listToDelete) {
			delete(dto.createPK());
		}
	}

	public void findAll() throws DAOException {
		try {
			listaGeneraUdsControlEvento.setAll(generaUdControlEventoBD.findAll());

		} catch (SQLException e) {
			throwException(e);
		}
	}

	public GeneraUnidadDeControlEvento findByPrimaryKey(GeneraUnidadDeControlEventoPK pk) throws DAOException {
		try {
			return generaUdControlEventoBD.findByPrimaryKey(pk);

		} catch (SQLException e) {
			throwException(e);
		}
		return null;
	}

	public void setMaxRows(int maxRows) {
		generaUdControlEventoBD.setMaxRows(maxRows);
	}

	public int getMaxRows() {
		return generaUdControlEventoBD.getMaxRows();
	}

	public ObservableList<GeneraUnidadDeControlEvento> getUDControlMantenimientoEventos(
			MantenimientoUnidadDeControl dto) {
		ObservableList<GeneraUnidadDeControlEvento> listToReturn = FXCollections.observableArrayList();
		for (GeneraUnidadDeControlEvento dtoOnList : listaGeneraUdsControlEvento) {
			if (dtoOnList.getCodMan() == dto.getCodMan()) {
				listToReturn.add(dtoOnList);
			}
		}
		return listToReturn;
	}

	public void createDependencies(MantenimientoUnidadDeControlDAO mantenimientoUDControlDAO, EventoDAO eventoDAO) {
		for (GeneraUnidadDeControlEvento guce : listaGeneraUdsControlEvento) {
			guce.setObjEvento(dependencyGeneraUdControlEventoEvento(guce, eventoDAO));
			guce.setObjMantenimientoUdControl(
					dependencyGeneraUdControlEventoMantenimiento(guce, mantenimientoUDControlDAO));
		}
	}

	private MantenimientoUnidadDeControl dependencyGeneraUdControlEventoMantenimiento(GeneraUnidadDeControlEvento dto,
			MantenimientoUnidadDeControlDAO mantenimientoUDControlDAO) {
		for (MantenimientoUnidadDeControl muc : mantenimientoUDControlDAO.getMantenimientosUdControl()) {
			if (muc.getCodMan() == dto.getCodMan()) {
				return muc;
			}
		}
		return null;
	}

	private Evento dependencyGeneraUdControlEventoEvento(GeneraUnidadDeControlEvento dto, EventoDAO eventoDAO) {
		for (Evento evento : eventoDAO.getEventos()) {
			if (evento.getCodEvento() == dto.getCodEvento()) {
				return evento;
			}
		}
		return null;
	}

	private void throwException(Exception e) throws DAOException {
		throw new DAOException(ERROR + e.getMessage(), e);
	}

	private GeneraUnidadDeControlEvento getOldGeneraUDControlEvento(GeneraUnidadDeControlEventoPK pk) {
		GeneraUnidadDeControlEvento oldGeneraUDControlEvento = new GeneraUnidadDeControlEvento();
		oldGeneraUDControlEvento.setCodEvento(pk.getCodEvento());
		oldGeneraUDControlEvento = listaGeneraUdsControlEvento
				.get(listaGeneraUdsControlEvento.indexOf(oldGeneraUDControlEvento));
		return oldGeneraUDControlEvento;
	}
}
