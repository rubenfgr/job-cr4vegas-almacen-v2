package es.rfgr.cr4vegas.modelo.postgresql.dao.mantenimiento.instalacion.imp;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

import javax.management.OperationsException;

import es.rfgr.cr4vegas.modelo.postgresql.bd.mantenimiento.instalacion.RegistroImagenInstalacionBD;
import es.rfgr.cr4vegas.modelo.postgresql.dao.mantenimiento.instalacion.ImagenInstalacionDAO;
import es.rfgr.cr4vegas.modelo.postgresql.dao.mantenimiento.instalacion.RegistroImagenInstalacionDAO;
import es.rfgr.cr4vegas.modelo.postgresql.dao.mantenimiento.instalacion.RegistroInstalacionDAO;
import es.rfgr.cr4vegas.modelo.postgresql.dto.mantenimiento.instalacion.ImagenInstalacion;
import es.rfgr.cr4vegas.modelo.postgresql.dto.mantenimiento.instalacion.RegistroImagenInstalacion;
import es.rfgr.cr4vegas.modelo.postgresql.dto.mantenimiento.instalacion.RegistroImagenInstalacionPK;
import es.rfgr.cr4vegas.modelo.postgresql.dto.mantenimiento.instalacion.RegistroInstalacion;
import es.rfgr.cr4vegas.modelo.postgresql.exceptions.DAOException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;

public class RegistroImagenInstalacionDAOImp implements RegistroImagenInstalacionDAO {

	private static final String ERROR = "RegistroImagenInstalacionDAO: ";

	private ObservableList<RegistroImagenInstalacion> listaRegistroImagenInstalacion;

	private RegistroImagenInstalacionBD registroImagenInstalacionBD;

	public RegistroImagenInstalacionDAOImp() {
		listaRegistroImagenInstalacion = FXCollections.observableArrayList();
		registroImagenInstalacionBD = new RegistroImagenInstalacionBD();
	}

	public void setConnection(Connection conn) {
		registroImagenInstalacionBD.setConnection(conn);
	}

	public ObservableList<RegistroImagenInstalacion> getRegistrosImagenesInstalaciones() {
		return listaRegistroImagenInstalacion;
	}

	public int getNumRegistrosImagenesInstalaciones() {
		return listaRegistroImagenInstalacion.size();
	}

	public RegistroImagenInstalacionPK insert(RegistroImagenInstalacion dto) throws DAOException {
		RegistroImagenInstalacionPK pk = dto.createPK();

		try {
			if (listaRegistroImagenInstalacion.contains(dto)) {
				throw new OperationsException("El registro-imagen-instalación ya existe.");
			} else {
				pk = registroImagenInstalacionBD.insert(dto);

				this.listaRegistroImagenInstalacion.add(dto);
			}
		} catch (Exception e) {
			throwException(e);
		}
		return pk;
	}

	public ObservableList<RegistroImagenInstalacionPK> insertAll(ObservableList<RegistroImagenInstalacion> listToInsert)
			throws DAOException {
		ObservableList<RegistroImagenInstalacionPK> listToReturn = FXCollections.observableArrayList();
		for (RegistroImagenInstalacion dto : listToInsert) {
			RegistroImagenInstalacionPK dtoPK = insert(dto);
			if (dtoPK != null) {
				listToReturn.add(dtoPK);
			}
		}
		return listToReturn;
	}

	public void update(RegistroImagenInstalacionPK pk, RegistroImagenInstalacion dto) throws DAOException {
		RegistroImagenInstalacion oldRegistroImagenInstalacion = getOldRegistroImagenInstalacion(pk);

		try {
			if (dto == null) {
				throw new OperationsException("El registro-imagen-instalación a modificar no puede ser nulo.");
			}
			if (!listaRegistroImagenInstalacion.contains(oldRegistroImagenInstalacion)) {
				throw new OperationsException("El registro-imagen-instalación a modificar no existe.");

			} else {
				registroImagenInstalacionBD.update(pk, dto);

				listaRegistroImagenInstalacion.set(listaRegistroImagenInstalacion.indexOf(oldRegistroImagenInstalacion),
						dto);
			}

		} catch (Exception e) {
			throwException(e);
		}
	}

	public void updateAll(ObservableMap<RegistroImagenInstalacionPK, RegistroImagenInstalacion> mapToUpdate)
			throws DAOException {
		for (Map.Entry<RegistroImagenInstalacionPK, RegistroImagenInstalacion> entry : mapToUpdate.entrySet()) {
			update(entry.getKey(), entry.getValue());
		}
	}

	public void delete(RegistroImagenInstalacionPK pk) throws DAOException {
		RegistroImagenInstalacion oldRegistroImagenInstalacion = getOldRegistroImagenInstalacion(pk);

		try {
			if (pk == null) {
				throw new NullPointerException("No se puede eliminar un registro-imagen-instalación nulo.");
			}
			if (!listaRegistroImagenInstalacion.contains(oldRegistroImagenInstalacion)) {
				throw new OperationsException("El registro-imagen-instalación a eliminar no existe.");

			} else {
				registroImagenInstalacionBD.delete(pk);

				listaRegistroImagenInstalacion.remove(oldRegistroImagenInstalacion);
			}
		} catch (Exception e) {
			throwException(e);
		}
	}

	public void deleteAll(ObservableList<RegistroImagenInstalacion> listToDelete) throws DAOException {
		for (RegistroImagenInstalacion dto : listToDelete) {
			delete(dto.createPK());
		}
	}

	public void findAll() throws DAOException {
		try {
			listaRegistroImagenInstalacion.setAll(registroImagenInstalacionBD.findAll());

		} catch (SQLException e) {
			throwException(e);
		}
	}

	public RegistroImagenInstalacion findByPrimaryKey(RegistroImagenInstalacionPK pk) throws DAOException {
		try {
			return registroImagenInstalacionBD.findByPrimaryKey(pk);

		} catch (SQLException e) {
			throwException(e);
		}
		return null;
	}

	public void setMaxRows(int maxRows) {
		registroImagenInstalacionBD.setMaxRows(maxRows);
	}

	public int getMaxRows() {
		return registroImagenInstalacionBD.getMaxRows();
	}

	public ObservableList<RegistroImagenInstalacion> getRegistroInstalacionImagenes(RegistroInstalacion dto) {
		ObservableList<RegistroImagenInstalacion> listToReturn = FXCollections.observableArrayList();
		for (RegistroImagenInstalacion dtoOnList : listaRegistroImagenInstalacion) {
			if (dtoOnList.getCodRegistroInstalacion() == dto.getCodRegistro()) {
				listToReturn.add(dtoOnList);
			}
		}
		return listToReturn;
	}

	public void createDependencies(RegistroInstalacionDAO registroInstalacionDAO,
			ImagenInstalacionDAO imagenInstalacionDAO) {
		for (RegistroImagenInstalacion rii : listaRegistroImagenInstalacion) {
			rii.setObjRegistroInstalacion(dependencyRegistroImagenInstalacionInstalacion(rii, registroInstalacionDAO));
			rii.setObjImagenInstalacion(dependencyRegistroImagenInstalacionImagen(rii, imagenInstalacionDAO));
		}
	}

	private RegistroInstalacion dependencyRegistroImagenInstalacionInstalacion(RegistroImagenInstalacion dto,
			RegistroInstalacionDAO registroInstalacionDAO) {
		for (RegistroInstalacion registroInstalacion : registroInstalacionDAO.getRegistroInstalacion()) {
			if (registroInstalacion.getCodRegistro() == dto.getCodRegistroInstalacion()) {
				return registroInstalacion;
			}
		}
		return null;
	}

	private ImagenInstalacion dependencyRegistroImagenInstalacionImagen(RegistroImagenInstalacion dto,
			ImagenInstalacionDAO imagenInstalacionDAO) {
		for (ImagenInstalacion imagenInstalacion : imagenInstalacionDAO.getImagenInstalacion()) {
			if (imagenInstalacion.getCodImagen() == dto.getCodImagenInstalacion()) {
				return imagenInstalacion;
			}
		}
		return null;
	}

	private void throwException(Exception e) throws DAOException {
		throw new DAOException(ERROR + e.getMessage(), e);
	}

	private RegistroImagenInstalacion getOldRegistroImagenInstalacion(RegistroImagenInstalacionPK pk) {
		RegistroImagenInstalacion oldRegistroImagenInstalacion = new RegistroImagenInstalacion();
		oldRegistroImagenInstalacion.setCodRegistroInstalacion(pk.getCodRegistro());
		oldRegistroImagenInstalacion.setCodImagenInstalacion(pk.getCodImagen());
		oldRegistroImagenInstalacion = listaRegistroImagenInstalacion
				.get(listaRegistroImagenInstalacion.indexOf(oldRegistroImagenInstalacion));
		return oldRegistroImagenInstalacion;
	}
}
