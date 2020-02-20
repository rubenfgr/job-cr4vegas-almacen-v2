package es.rfgr.cr4vegas.modelo.postgresql.dao.evento.imp;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

import javax.management.OperationsException;

import es.rfgr.cr4vegas.modelo.postgresql.bd.evento.EventoBD;
import es.rfgr.cr4vegas.modelo.postgresql.dao.evento.EventoDAO;
import es.rfgr.cr4vegas.modelo.postgresql.dao.grupo.GrupoDAO;
import es.rfgr.cr4vegas.modelo.postgresql.dto.evento.Evento;
import es.rfgr.cr4vegas.modelo.postgresql.dto.evento.EventoPK;
import es.rfgr.cr4vegas.modelo.postgresql.dto.grupo.Grupo;
import es.rfgr.cr4vegas.modelo.postgresql.exceptions.DAOException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;

public class EventoDAOImp implements EventoDAO {

	private static final String ERROR = "EventoDAO: ";

	private ObservableList<Evento> listaEventos;
	
	private EventoBD eventoBD;

	public EventoDAOImp() {
		listaEventos = FXCollections.observableArrayList();
		eventoBD = new EventoBD();
	}

	public void setConnection(Connection conn) {
		eventoBD.setConnection(conn);
	}

	public ObservableList<Evento> getEventos() {
		return listaEventos;
	}

	public int getNumEventos() {
		return listaEventos.size();
	}

	public EventoPK insert(Evento dto) throws DAOException {
		EventoPK pk = dto.createPK();

		try {
			if (listaEventos.contains(dto)) {
				throw new OperationsException("El evento ya existe.");

			} else {
				pk = eventoBD.insert(dto);

				this.listaEventos.add(dto);
			}
		} catch (Exception e) {
			throwException(e);
		}
		return pk;
	}

	public ObservableList<EventoPK> insertAll(ObservableList<Evento> listToInsert) throws DAOException {
		ObservableList<EventoPK> listToReturn = FXCollections.observableArrayList();
		for (Evento dto : listToInsert) {
			EventoPK dtoPK = insert(dto);
			if (dtoPK != null) {
				listToReturn.add(dtoPK);
			}
		}
		return listToReturn;
	}
	
	public void update(EventoPK pk, Evento dto) throws DAOException {
		Evento oldEvento = getOldEvento(pk);

		try {
			if (dto == null) {
				throw new OperationsException("El evento a modificar no puede ser nulo.");
			}
			if (!listaEventos.contains(oldEvento)) {
				throw new OperationsException("El evento a modificar no existe.");

			} else {
				eventoBD.update(pk, dto);

				listaEventos.set(listaEventos.indexOf(oldEvento), dto);
			}
		} catch (Exception e) {
			throwException(e);
		}
	}
	
	public void updateAll(ObservableMap<EventoPK, Evento> mapToUpdate) throws DAOException {
		for (Map.Entry<EventoPK, Evento> entry : mapToUpdate.entrySet()) {
			update(entry.getKey(), entry.getValue());
		}
	}

	public void delete(EventoPK pk) throws DAOException {
		Evento oldEvento = getOldEvento(pk);

		try {
			if (pk == null) {
				throw new NullPointerException("No se puede eliminar un evento nulo.");
			}
			if (!listaEventos.contains(oldEvento)) {
				throw new OperationsException("El evento a eliminar no existe.");
				
			} else {
				eventoBD.delete(pk);

				listaEventos.remove(oldEvento);
			}
		} catch (Exception e) {
			throwException(e);
		}
	}
	
	public void deleteAll(ObservableList<Evento> listToDelete) throws DAOException {
		for (Evento dto : listToDelete) {
			delete(dto.createPK());
		}
	}

	public void findAll() throws DAOException {
		try {
			listaEventos.setAll(eventoBD.findAll());
		} catch (SQLException e) {
			throwException(e);
		}
	}

	public Evento findByPrimaryKey(EventoPK pk) throws DAOException {
		try {
			return eventoBD.findByPrimaryKey(pk);

		} catch (SQLException e) {
			throwException(e);
		}
		return null;
	}
	
	public void setMaxRows(int maxRows) {
		eventoBD.setMaxRows(maxRows);
	}

	public int getMaxRows() {
		return eventoBD.getMaxRows();
	}

	public void createDependencies(GrupoDAO grupoDAO) {
		for (Evento evento : listaEventos) {
			evento.setObjGrupo(dependencyEventoGrupo(evento, grupoDAO));
		}
	}

	private Grupo dependencyEventoGrupo(Evento dto, GrupoDAO grupoDAO) {
		for (Grupo grupo : grupoDAO.getGrupos()) {
			if (grupo.getNombre().equals(dto.getGrupo())) {
				return grupo;
			}
		}
		return null;
	}

	private void throwException(Exception e) throws DAOException {
		throw new DAOException(ERROR + e.getMessage(), e);
	}

	private Evento getOldEvento(EventoPK pk) {
		Evento oldEvento = new Evento();
		oldEvento.setCodEvento(pk.getCodEvento());
		oldEvento = listaEventos.get(listaEventos.indexOf(oldEvento));
		return oldEvento;
	}
}
