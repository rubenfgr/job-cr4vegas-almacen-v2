package es.rfgr.cr4vegas.modelo.postgresql.dao.mantenimiento.unidaddecontrol.imp;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

import javax.management.OperationsException;

import es.rfgr.cr4vegas.modelo.postgresql.bd.mantenimiento.unidaddecontrol.RegistroImagenUnidadDeControlBD;
import es.rfgr.cr4vegas.modelo.postgresql.dao.mantenimiento.unidaddecontrol.ImagenUnidadDeControlDAO;
import es.rfgr.cr4vegas.modelo.postgresql.dao.mantenimiento.unidaddecontrol.RegistroImagenUnidadDeControlDAO;
import es.rfgr.cr4vegas.modelo.postgresql.dao.mantenimiento.unidaddecontrol.RegistroUnidadDeControlDAO;
import es.rfgr.cr4vegas.modelo.postgresql.dto.mantenimiento.unidaddecontrol.ImagenUnidadDeControl;
import es.rfgr.cr4vegas.modelo.postgresql.dto.mantenimiento.unidaddecontrol.RegistroImagenUnidadDeControl;
import es.rfgr.cr4vegas.modelo.postgresql.dto.mantenimiento.unidaddecontrol.RegistroImagenUnidadDeControlPK;
import es.rfgr.cr4vegas.modelo.postgresql.dto.mantenimiento.unidaddecontrol.RegistroUnidadDeControl;
import es.rfgr.cr4vegas.modelo.postgresql.exceptions.DAOException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;

public class RegistroImagenUnidadDeControlDAOImp implements RegistroImagenUnidadDeControlDAO {

	private static final String ERROR = "RegistroImagenUdControlDAO: ";

	private ObservableList<RegistroImagenUnidadDeControl> listaRegistroImagenUdControl;

	private RegistroImagenUnidadDeControlBD registroImagenUdControlBD;

	public RegistroImagenUnidadDeControlDAOImp() {
		listaRegistroImagenUdControl = FXCollections.observableArrayList();
		registroImagenUdControlBD = new RegistroImagenUnidadDeControlBD();
	}

	public void setConnection(Connection conn) {
		registroImagenUdControlBD.setConnection(conn);
	}

	public ObservableList<RegistroImagenUnidadDeControl> getRegistrosImagenesUdsControl() {
		return listaRegistroImagenUdControl;
	}

	public int getNumRegistrosImagenesUdsControl() {
		return listaRegistroImagenUdControl.size();
	}

	public RegistroImagenUnidadDeControlPK insert(RegistroImagenUnidadDeControl dto) throws DAOException {
		RegistroImagenUnidadDeControlPK pk = dto.createPK();

		try {
			if (listaRegistroImagenUdControl.contains(dto)) {
				throw new OperationsException("El registro-imagen-ud.control ya existe.");

			} else {
				pk = registroImagenUdControlBD.insert(dto);

				this.listaRegistroImagenUdControl.add(dto);
			}
		} catch (Exception e) {
			throwException(e);
		}
		return pk;
	}

	public ObservableList<RegistroImagenUnidadDeControlPK> insertAll(
			ObservableList<RegistroImagenUnidadDeControl> listToInsert) throws DAOException {
		ObservableList<RegistroImagenUnidadDeControlPK> listToReturn = FXCollections.observableArrayList();
		for (RegistroImagenUnidadDeControl dto : listToInsert) {
			RegistroImagenUnidadDeControlPK dtoPK = insert(dto);
			if (dtoPK != null) {
				listToReturn.add(dtoPK);
			}
		}
		return listToReturn;
	}

	public void update(RegistroImagenUnidadDeControlPK pk, RegistroImagenUnidadDeControl dto) throws DAOException {
		RegistroImagenUnidadDeControl oldRegistroImagenUDControl = getOldRegistroImagenUDControl(pk);

		try {
			if (dto == null) {
				throw new OperationsException("El registro-imagen-ud.control a modificar no puede ser nulo.");
			}
			if (!listaRegistroImagenUdControl.contains(oldRegistroImagenUDControl)) {
				throw new OperationsException("El registro-imagen-ud.control a modificar no existe.");

			} else {
				registroImagenUdControlBD.update(pk, dto);

				listaRegistroImagenUdControl.set(listaRegistroImagenUdControl.indexOf(oldRegistroImagenUDControl), dto);
			}

		} catch (Exception e) {
			throwException(e);
		}
	}

	public void updateAll(ObservableMap<RegistroImagenUnidadDeControlPK, RegistroImagenUnidadDeControl> mapToUpdate)
			throws DAOException {
		for (Map.Entry<RegistroImagenUnidadDeControlPK, RegistroImagenUnidadDeControl> entry : mapToUpdate.entrySet()) {
			update(entry.getKey(), entry.getValue());
		}
	}

	public void delete(RegistroImagenUnidadDeControlPK pk) throws DAOException {
		RegistroImagenUnidadDeControl oldRegistroImagenUDControl = getOldRegistroImagenUDControl(pk);

		try {
			if (pk == null) {
				throw new NullPointerException("No se puede eliminar un registro-imagen-ud.control nulo.");
			}
			if (!listaRegistroImagenUdControl.contains(oldRegistroImagenUDControl)) {
				throw new OperationsException("El registro-imagen-ud.control a eliminar no existe.");

			} else {
				registroImagenUdControlBD.delete(pk);

				listaRegistroImagenUdControl.remove(oldRegistroImagenUDControl);
			}
		} catch (Exception e) {
			throwException(e);
		}
	}

	public void deleteAll(ObservableList<RegistroImagenUnidadDeControl> listToDelete) throws DAOException {
		for (RegistroImagenUnidadDeControl dto : listToDelete) {
			delete(dto.createPK());
		}
	}

	public void findAll() throws DAOException {
		try {
			listaRegistroImagenUdControl.setAll(registroImagenUdControlBD.findAll());

		} catch (SQLException e) {
			throwException(e);
		}
	}

	public RegistroImagenUnidadDeControl findByPrimaryKey(RegistroImagenUnidadDeControlPK pk) throws DAOException {
		try {
			return registroImagenUdControlBD.findByPrimaryKey(pk);

		} catch (SQLException e) {
			throwException(e);
		}
		return null;
	}

	public void setMaxRows(int maxRows) {
		registroImagenUdControlBD.setMaxRows(maxRows);
	}

	public int getMaxRows() {
		return registroImagenUdControlBD.getMaxRows();
	}

	public ObservableList<RegistroImagenUnidadDeControl> getRegistroUDControlImagenes(RegistroUnidadDeControl dto) {
		ObservableList<RegistroImagenUnidadDeControl> listToReturn = FXCollections.observableArrayList();
		for (RegistroImagenUnidadDeControl dtoOnList : listaRegistroImagenUdControl) {
			listToReturn.add(dtoOnList);
		}
		return listToReturn;
	}

	public void createDependencies(RegistroUnidadDeControlDAO registroUDControlDAO,
			ImagenUnidadDeControlDAO imagenUDControlDAO) {
		for (RegistroImagenUnidadDeControl dtoOnList : listaRegistroImagenUdControl) {
			dtoOnList.setObjRegistroUnidadDeControl(dependencyRegistroImagenUdControlUdControl(dtoOnList, registroUDControlDAO));
			dtoOnList.setObjImagenUdControl(dependencyRegistroImagenUdControlImagen(dtoOnList, imagenUDControlDAO));
		}
	}

	private RegistroUnidadDeControl dependencyRegistroImagenUdControlUdControl(RegistroImagenUnidadDeControl dto,
			RegistroUnidadDeControlDAO registroUDControlDAO) {
		for (RegistroUnidadDeControl registroUdControl : registroUDControlDAO.getRegistrosUdControl()) {
			if (registroUdControl.getCodRegistro() == dto.getCodRegistroUnidadDeControl()) {
				return registroUdControl;
			}
		}
		return null;
	}

	private ImagenUnidadDeControl dependencyRegistroImagenUdControlImagen(RegistroImagenUnidadDeControl dto,
			ImagenUnidadDeControlDAO imagenUDControlDAO) {
		for (ImagenUnidadDeControl imagenUdControl : imagenUDControlDAO.getImagenesUdControl()) {
			if (imagenUdControl.getCodImagen() == dto.getCodImagenUdControl()) {
				return imagenUdControl;
			}
		}
		return null;
	}

	private void throwException(Exception e) throws DAOException {
		throw new DAOException(ERROR + e.getMessage(), e);
	}

	private RegistroImagenUnidadDeControl getOldRegistroImagenUDControl(RegistroImagenUnidadDeControlPK pk) {
		RegistroImagenUnidadDeControl oldRegistroImagenUDControl = new RegistroImagenUnidadDeControl();
		oldRegistroImagenUDControl.setCodRegistroUnidadDeControl(pk.getCodRegistroUnidadDeControl());
		oldRegistroImagenUDControl.setCodImagenUdControl(pk.getCodImagenUdControl());
		oldRegistroImagenUDControl = listaRegistroImagenUdControl
				.get(listaRegistroImagenUdControl.indexOf(oldRegistroImagenUDControl));
		return oldRegistroImagenUDControl;
	}
}
