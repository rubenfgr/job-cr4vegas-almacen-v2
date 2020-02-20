package es.rfgr.cr4vegas.modelo.postgresql.dao.operario.imp;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

import javax.management.OperationsException;

import es.rfgr.cr4vegas.modelo.postgresql.bd.operario.GeneraOperarioEventoBD;
import es.rfgr.cr4vegas.modelo.postgresql.dao.evento.EventoDAO;
import es.rfgr.cr4vegas.modelo.postgresql.dao.operario.GeneraOperarioEventoDAO;
import es.rfgr.cr4vegas.modelo.postgresql.dao.operario.OperarioDAO;
import es.rfgr.cr4vegas.modelo.postgresql.dto.evento.Evento;
import es.rfgr.cr4vegas.modelo.postgresql.dto.operario.GeneraOperarioEvento;
import es.rfgr.cr4vegas.modelo.postgresql.dto.operario.GeneraOperarioEventoPK;
import es.rfgr.cr4vegas.modelo.postgresql.dto.operario.Operario;
import es.rfgr.cr4vegas.modelo.postgresql.exceptions.DAOException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;

public class GeneraOperarioEventoDAOImp implements GeneraOperarioEventoDAO {

	private static final String ERROR = "GeneraOperarioEventoDAO: ";

	private ObservableList<GeneraOperarioEvento> listaGeneraOperarioEvento;
	
	private GeneraOperarioEventoBD generaOperarioEventoBD;

	public GeneraOperarioEventoDAOImp() {
		listaGeneraOperarioEvento = FXCollections.observableArrayList();
		generaOperarioEventoBD = new GeneraOperarioEventoBD();
	}
	
	public void setConnection(Connection conn) {
		generaOperarioEventoBD.setConnection(conn);
	}

	public ObservableList<GeneraOperarioEvento> getGeneraOperarioEvento() {
		return listaGeneraOperarioEvento;
	}

	public int getNumGeneraOperarioEvento() {
		return listaGeneraOperarioEvento.size();
	}

	public GeneraOperarioEventoPK insert(GeneraOperarioEvento dto) throws DAOException {
		GeneraOperarioEventoPK pk = dto.createPK();
		
		try {
			if (listaGeneraOperarioEvento.contains(dto)) {
				throw new OperationsException("El operario-evento ya existe.");
			} else {
				pk = generaOperarioEventoBD.insert(dto);
				
				this.listaGeneraOperarioEvento.add(dto);
			}
		} catch (Exception e) {
			throwException(e);
		}
		return pk;
	}
	
	public ObservableList<GeneraOperarioEventoPK> insertAll(ObservableList<GeneraOperarioEvento> listToInsert) throws DAOException {
		ObservableList<GeneraOperarioEventoPK> listToReturn = FXCollections.observableArrayList();
		for (GeneraOperarioEvento dto : listToInsert) {
			GeneraOperarioEventoPK dtoPK = insert(dto);
			if (dtoPK != null) {
				listToReturn.add(dtoPK);
			}
		}
		return listToReturn;
	}

	public void update(GeneraOperarioEventoPK pk, GeneraOperarioEvento dto) throws DAOException {
		GeneraOperarioEvento oldGeneraOperarioEvento = getOldGeneraOperarioEvento(pk);
		
		try {
			if (dto == null) {
				throw new OperationsException("El operario-evento a modificar no puede ser nula.");
			} else {
				if (!listaGeneraOperarioEvento.contains(oldGeneraOperarioEvento)) {
					throw new OperationsException("El operario-evento a modificar no existe.");
					
				} else {
					generaOperarioEventoBD.update(pk, dto);
					
					listaGeneraOperarioEvento.set(listaGeneraOperarioEvento.indexOf(oldGeneraOperarioEvento), dto);
				}
			}
		} catch (Exception e) {
			throwException(e);
		}
	}
	
	public void updateAll(ObservableMap<GeneraOperarioEventoPK, GeneraOperarioEvento> mapToUpdate) throws DAOException {
		for (Map.Entry<GeneraOperarioEventoPK, GeneraOperarioEvento> entry : mapToUpdate.entrySet()) {
			update(entry.getKey(), entry.getValue());
		}
	}

	public void delete(GeneraOperarioEventoPK pk) throws DAOException {
		GeneraOperarioEvento oldGeneraOperarioEvento = getOldGeneraOperarioEvento(pk);
		
		try {
			if (pk == null) {
				throw new NullPointerException("No se puede eliminar un operario-evento nulo.");
			}
			if (!listaGeneraOperarioEvento.contains(oldGeneraOperarioEvento)) {
				throw new OperationsException("El operario-evento a eliminar no existe.");
				
			} else {
				generaOperarioEventoBD.delete(pk);
				
				listaGeneraOperarioEvento.remove(oldGeneraOperarioEvento);
			}
		} catch (Exception e) {
			throwException(e);
		}
	}
	
	public void deleteAll(ObservableList<GeneraOperarioEvento> listToDelete) throws DAOException {
		for (GeneraOperarioEvento dto : listToDelete) {
			delete(dto.createPK());
		}
	}

	public void findAll() throws DAOException {
		try {
			listaGeneraOperarioEvento.setAll(generaOperarioEventoBD.findAll());
			
		} catch (SQLException e) {
			throwException(e);
		}
	}
	
	public GeneraOperarioEvento findByPrimaryKey(GeneraOperarioEventoPK pk) throws DAOException {
		try {
			return generaOperarioEventoBD.findByPrimaryKey(pk);
			
		} catch (SQLException e) {
			throwException(e);
		}
		return null;
	}
	
	public void setMaxRows(int maxRows) {
		generaOperarioEventoBD.setMaxRows(maxRows);
	}

	public int getMaxRows() {
		return generaOperarioEventoBD.getMaxRows();
	}
	
	public ObservableList<GeneraOperarioEvento> getOperarioEventos(Operario dto) {
		ObservableList<GeneraOperarioEvento> listToReturn = FXCollections.observableArrayList();
		for (GeneraOperarioEvento dtoOnList : listaGeneraOperarioEvento) {
			if (dtoOnList.getCodOp() == dto.getCodOp()) {
				listToReturn.add(dtoOnList);
			}
		}
		return listToReturn;
	}
	
	public void createDependencies(OperarioDAO operarioDAO, EventoDAO eventoDAO) {
		for (GeneraOperarioEvento goe : listaGeneraOperarioEvento) {
			goe.setObjEvento(dependencyGeneraOperarioEventoEvento(goe, eventoDAO));
			goe.setObjOperario(dependencyGeneraOperarioEventoOperario(goe, operarioDAO));
		}
	}
	
	private Operario dependencyGeneraOperarioEventoOperario(GeneraOperarioEvento dto, OperarioDAO operarioDAO) {
		Operario operarioSalida = null;
		for (Operario operario : operarioDAO.getOperarios()) {
			if (operario.getCodOp() == dto.getCodOp()) {
				operarioSalida = operario;
			}
		}
		return operarioSalida;
	}
	
	private Evento dependencyGeneraOperarioEventoEvento(GeneraOperarioEvento dto, EventoDAO eventoDAO) {
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
	
	private GeneraOperarioEvento getOldGeneraOperarioEvento(GeneraOperarioEventoPK pk) {
		GeneraOperarioEvento oldGeneraOperarioEvento = new GeneraOperarioEvento();
		oldGeneraOperarioEvento.setCodEvento(pk.getCodEvento());
		oldGeneraOperarioEvento = listaGeneraOperarioEvento.get(listaGeneraOperarioEvento.indexOf(oldGeneraOperarioEvento));
		return oldGeneraOperarioEvento;
	}
}