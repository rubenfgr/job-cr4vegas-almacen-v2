package es.rfgr.cr4vegas.modelo.postgresql.dao.alarma.imp;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

import javax.management.OperationsException;

import es.rfgr.cr4vegas.modelo.postgresql.bd.alarma.GeneraAlarmaEventoBD;
import es.rfgr.cr4vegas.modelo.postgresql.dao.alarma.AlarmaDAO;
import es.rfgr.cr4vegas.modelo.postgresql.dao.alarma.GeneraAlarmaEventoDAO;
import es.rfgr.cr4vegas.modelo.postgresql.dao.evento.EventoDAO;
import es.rfgr.cr4vegas.modelo.postgresql.dto.alarma.Alarma;
import es.rfgr.cr4vegas.modelo.postgresql.dto.alarma.GeneraAlarmaEvento;
import es.rfgr.cr4vegas.modelo.postgresql.dto.alarma.GeneraAlarmaEventoPK;
import es.rfgr.cr4vegas.modelo.postgresql.dto.evento.Evento;
import es.rfgr.cr4vegas.modelo.postgresql.exceptions.DAOException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;

public class GeneraAlarmaEventoDAOImp implements GeneraAlarmaEventoDAO {

	private static final String ERROR = "GeneraAlarmaEventoDAO: ";

	private ObservableList<GeneraAlarmaEvento> listaGeneraAlarmaEvento;

	private GeneraAlarmaEventoBD generaAlarmaEventoBD;

	public GeneraAlarmaEventoDAOImp() {
		listaGeneraAlarmaEvento = FXCollections.observableArrayList();
		generaAlarmaEventoBD = new GeneraAlarmaEventoBD();
	}

	public void setConnection(Connection conn) {
		generaAlarmaEventoBD.setConnection(conn);
	}

	public ObservableList<GeneraAlarmaEvento> getGeneraAlarmaEvento() {
		return listaGeneraAlarmaEvento;
	}

	public int getNumGeneraAlarmaEvento() {
		return listaGeneraAlarmaEvento.size();
	}

	public GeneraAlarmaEventoPK insert(GeneraAlarmaEvento dto) throws DAOException {
		GeneraAlarmaEventoPK pk = dto.createPK();

		try {
			if (listaGeneraAlarmaEvento.contains(dto)) {
				throw new OperationsException("La alarma-evento ya existe.");

			} else {
				pk = generaAlarmaEventoBD.insert(dto);

				this.listaGeneraAlarmaEvento.add(dto);
			}
		} catch (Exception e) {
			throwException(e);
		}
		return pk;
	}

	public ObservableList<GeneraAlarmaEventoPK> insertAll(ObservableList<GeneraAlarmaEvento> listToInsert) throws DAOException {
		ObservableList<GeneraAlarmaEventoPK> listToReturn = FXCollections.observableArrayList();
		for (GeneraAlarmaEvento dto : listToInsert) {
			GeneraAlarmaEventoPK dtoPK = insert(dto);
			if (dtoPK != null) {
				listToReturn.add(dtoPK);
			}
		}
		return listToReturn;
	}

	public void update(GeneraAlarmaEventoPK pk, GeneraAlarmaEvento dto) throws DAOException {
		GeneraAlarmaEvento oldGeneraAlarmaEvento = getOldGeneraAlarmaEvento(pk);

		try {
			if (dto == null) {
				throw new OperationsException("La alarma-evento a modificar no puede ser nula.");
			}
			if (!listaGeneraAlarmaEvento.contains(oldGeneraAlarmaEvento)) {
				throw new OperationsException("La alarma-evento a modificar no existe.");

			} else {
				generaAlarmaEventoBD.update(pk, dto);

				listaGeneraAlarmaEvento.set(listaGeneraAlarmaEvento.indexOf(oldGeneraAlarmaEvento), dto);
			}

		} catch (Exception e) {
			throwException(e);
		}
	}

	public void updateAll(ObservableMap<GeneraAlarmaEventoPK, GeneraAlarmaEvento> mapToUpdate) throws DAOException {
		for (Map.Entry<GeneraAlarmaEventoPK, GeneraAlarmaEvento> entry : mapToUpdate.entrySet()) {
			update(entry.getKey(), entry.getValue());
		}
	}

	public void delete(GeneraAlarmaEventoPK pk) throws DAOException {
		GeneraAlarmaEvento oldGeneraAlarmaEvento = getOldGeneraAlarmaEvento(pk);

		try {
			if (pk == null) {
				throw new NullPointerException("No se puede eliminar una alarma-evento nula.");
			}
			if (!listaGeneraAlarmaEvento.contains(oldGeneraAlarmaEvento)) {
				throw new OperationsException("La alarma-evento a eliminar no existe.");

			} else {
				generaAlarmaEventoBD.delete(pk);

				listaGeneraAlarmaEvento.remove(oldGeneraAlarmaEvento);
			}
		} catch (Exception e) {
			throwException(e);
		}
	}

	public void deleteAll(ObservableList<GeneraAlarmaEvento> listToDelete) throws DAOException {
		for (GeneraAlarmaEvento dto : listToDelete) {
			delete(dto.createPK());
		}
	}

	public void findAll() throws DAOException {
		try {
			listaGeneraAlarmaEvento.setAll(generaAlarmaEventoBD.findAll());

		} catch (SQLException e) {
			throwException(e);
		}
	}

	public GeneraAlarmaEvento findByPrimaryKey(GeneraAlarmaEventoPK pk) throws DAOException {
		try {
			return generaAlarmaEventoBD.findByPrimaryKey(pk);

		} catch (SQLException e) {
			throwException(e);
		}
		return null;
	}
	
	public void setMaxRows(int maxRows) {
		generaAlarmaEventoBD.setMaxRows(maxRows);
	}

	public int getMaxRows() {
		return generaAlarmaEventoBD.getMaxRows();
	}
	
	public ObservableList<GeneraAlarmaEvento> getAlarmaEventos(Alarma dto) {
		ObservableList<GeneraAlarmaEvento> mapaSalida = FXCollections.observableArrayList();
		for (GeneraAlarmaEvento dtoOnList : listaGeneraAlarmaEvento) {
			if (dtoOnList.getCodAlarma() == dto.getCodAlarma()) {
				mapaSalida.add(dtoOnList);
			}
		}
		return mapaSalida;
	}

	public void createDependencies(AlarmaDAO alarmaDAO, EventoDAO eventoDAO) {
		for (GeneraAlarmaEvento gae : listaGeneraAlarmaEvento) {
			gae.setObjEvento(dependencyGeneraAlarmaEventoEvento(gae, eventoDAO));
			gae.setObjAlarma(dependencyGeneraAlarmaEventoAlarma(gae, alarmaDAO));
		}
	}

	private Alarma dependencyGeneraAlarmaEventoAlarma(GeneraAlarmaEvento dto, AlarmaDAO alarmaDAO) {
		for (Alarma alarma : alarmaDAO.getAlarmas()) {
			if (alarma.getCodAlarma() == dto.getCodAlarma()) {
				return alarma;
			}
		}
		return null;
	}

	private Evento dependencyGeneraAlarmaEventoEvento(GeneraAlarmaEvento dto, EventoDAO eventoDAO) {
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

	private GeneraAlarmaEvento getOldGeneraAlarmaEvento(GeneraAlarmaEventoPK pk) {
		GeneraAlarmaEvento oldGeneraAlarmaEvento = new GeneraAlarmaEvento();
		oldGeneraAlarmaEvento.setCodEvento(pk.getCodEvento());
		oldGeneraAlarmaEvento = listaGeneraAlarmaEvento.get(listaGeneraAlarmaEvento.indexOf(oldGeneraAlarmaEvento));
		return oldGeneraAlarmaEvento;
	}
}
