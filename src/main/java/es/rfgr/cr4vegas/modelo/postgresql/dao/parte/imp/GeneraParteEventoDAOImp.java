package es.rfgr.cr4vegas.modelo.postgresql.dao.parte.imp;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

import javax.management.OperationsException;

import es.rfgr.cr4vegas.modelo.postgresql.bd.parte.GeneraParteEventoBD;
import es.rfgr.cr4vegas.modelo.postgresql.dao.evento.EventoDAO;
import es.rfgr.cr4vegas.modelo.postgresql.dao.parte.GeneraParteEventoDAO;
import es.rfgr.cr4vegas.modelo.postgresql.dao.parte.ParteDAO;
import es.rfgr.cr4vegas.modelo.postgresql.dto.evento.Evento;
import es.rfgr.cr4vegas.modelo.postgresql.dto.parte.GeneraParteEvento;
import es.rfgr.cr4vegas.modelo.postgresql.dto.parte.GeneraParteEventoPK;
import es.rfgr.cr4vegas.modelo.postgresql.dto.parte.Parte;
import es.rfgr.cr4vegas.modelo.postgresql.exceptions.DAOException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;

public class GeneraParteEventoDAOImp implements GeneraParteEventoDAO {

	private static final String ERROR = "GeneraParteEventoDAO: ";

	private ObservableList<GeneraParteEvento> listaGeneraParteEvento;

	private GeneraParteEventoBD generaParteEventoBD;

	public GeneraParteEventoDAOImp() {
		listaGeneraParteEvento = FXCollections.observableArrayList();
		generaParteEventoBD = new GeneraParteEventoBD();
	}

	public void setConnection(Connection conn) {
		generaParteEventoBD.setConnection(conn);
	}

	public ObservableList<GeneraParteEvento> getGeneraParteEventos() {
		return listaGeneraParteEvento;
	}

	public int getNumGeneraParteEventos() {
		return listaGeneraParteEvento.size();
	}

	public GeneraParteEventoPK insert(GeneraParteEvento dto) throws DAOException {
		GeneraParteEventoPK pk = dto.createPK();

		try {
			if (listaGeneraParteEvento.contains(dto)) {
				throw new OperationsException("El parte-evento ya existe.");
			} else {
				pk = generaParteEventoBD.insert(dto);

				this.listaGeneraParteEvento.add(dto);
			}
		} catch (Exception e) {
			throwException(e);
		}
		return pk;
	}
	
	public ObservableList<GeneraParteEventoPK> insertAll(ObservableList<GeneraParteEvento> listToInsert) throws DAOException {
		ObservableList<GeneraParteEventoPK> listToReturn = FXCollections.observableArrayList();
		for (GeneraParteEvento dto : listToInsert) {
			GeneraParteEventoPK dtoPK = insert(dto);
			if (dtoPK != null) {
				listToReturn.add(dtoPK);
			}
		}
		return listToReturn;
	}

	public void update(GeneraParteEventoPK pk, GeneraParteEvento dto) throws DAOException {
		GeneraParteEvento oldGeneraParteEvento = getOldGeneraParteEvento(pk);

		try {
			if (dto == null) {
				throw new OperationsException("El parte-evento a modificar no puede ser nulo.");
			}
			if (!listaGeneraParteEvento.contains(oldGeneraParteEvento)) {
				throw new OperationsException("El parte-evento a modificar no existe.");

			} else {
				generaParteEventoBD.update(pk, dto);

				listaGeneraParteEvento.set(listaGeneraParteEvento.indexOf(oldGeneraParteEvento), dto);
			}
		} catch (Exception e) {
			throwException(e);
		}
	}
	
	public void updateAll(ObservableMap<GeneraParteEventoPK, GeneraParteEvento> mapToUpdate) throws DAOException {
		for (Map.Entry<GeneraParteEventoPK, GeneraParteEvento> entry : mapToUpdate.entrySet()) {
			update(entry.getKey(), entry.getValue());
		}
	}

	public void delete(GeneraParteEventoPK pk) throws DAOException {
		GeneraParteEvento oldGeneraParteEvento = getOldGeneraParteEvento(pk);

		try {
			if (pk == null) {
				throw new NullPointerException("No se puede eliminar un parte-evento nulo.");
			}
			if (!listaGeneraParteEvento.contains(oldGeneraParteEvento)) {
				throw new OperationsException("El parte-evento a eliminar no existe.");

			} else {
				generaParteEventoBD.delete(pk);

				listaGeneraParteEvento.remove(oldGeneraParteEvento);
			}
		} catch (Exception e) {
			throwException(e);
		}
	}
	
	public void deleteAll(ObservableList<GeneraParteEvento> listToDelete) throws DAOException {
		for (GeneraParteEvento dto : listToDelete) {
			delete(dto.createPK());
		}
	}

	public void findAll() throws DAOException {
		try {
			listaGeneraParteEvento.setAll(generaParteEventoBD.findAll());

		} catch (SQLException e) {
			throwException(e);
		}
	}

	public GeneraParteEvento findByPrimaryKey(GeneraParteEventoPK pk) throws DAOException {
		try {
			return generaParteEventoBD.findByPrimaryKey(pk);

		} catch (SQLException e) {
			throwException(e);
		}
		return null;
	}
	
	public void setMaxRows(int maxRows) {
		generaParteEventoBD.setMaxRows(maxRows);
	}

	public int getMaxRows() {
		return generaParteEventoBD.getMaxRows();
	}
	
	public ObservableList<GeneraParteEvento> getParteEventos(Parte dto) {
		ObservableList<GeneraParteEvento> listToReturn = FXCollections.observableArrayList();
		for (GeneraParteEvento dtoOnList : listaGeneraParteEvento) {
			if (dtoOnList.getCodParte() == dto.getCodParte()) {
				listToReturn.add(dtoOnList);
			}
		}
		return listToReturn;
	}

	public void createDependencies(ParteDAO parteDAO, EventoDAO eventoDAO) {
		for (GeneraParteEvento gpe : listaGeneraParteEvento) {
			gpe.setObjEvento(dependencyGeneraParteEventoEvento(gpe, eventoDAO));
			gpe.setObjParte(dependencyGeneraParteEventoParte(gpe, parteDAO));
		}
	}

	private Parte dependencyGeneraParteEventoParte(GeneraParteEvento dto, ParteDAO parteDAO) {
		Parte parteSalida = null;
		for (Parte parte : parteDAO.getPartes()) {
			if (parte.getCodParte() == dto.getCodParte()) {
				parteSalida = parte;
			}
		}
		return parteSalida;
	}

	private Evento dependencyGeneraParteEventoEvento(GeneraParteEvento dto, EventoDAO eventoDAO) {
		Evento eventoSalida = null;
		for (Evento evento : eventoDAO.getEventos()) {
			if (evento.getCodEvento() == dto.getCodEvento()) {
				eventoSalida = evento;
			}
		}
		return eventoSalida;
	}

	private void throwException(Exception e) throws DAOException {
		throw new DAOException(ERROR + e.getMessage(), e);
	}

	private GeneraParteEvento getOldGeneraParteEvento(GeneraParteEventoPK pk) {
		GeneraParteEvento oldGeneraParteEvento = new GeneraParteEvento();
		oldGeneraParteEvento.setCodEvento(pk.getCodEvento());
		oldGeneraParteEvento = listaGeneraParteEvento.get(listaGeneraParteEvento.indexOf(oldGeneraParteEvento));
		return oldGeneraParteEvento;
	}
}
