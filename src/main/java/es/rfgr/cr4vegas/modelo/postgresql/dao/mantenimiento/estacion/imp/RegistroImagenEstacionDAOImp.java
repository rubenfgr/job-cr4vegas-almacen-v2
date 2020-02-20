package es.rfgr.cr4vegas.modelo.postgresql.dao.mantenimiento.estacion.imp;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

import javax.management.OperationsException;

import es.rfgr.cr4vegas.modelo.postgresql.bd.mantenimiento.estacion.RegistroImagenEstacionBD;
import es.rfgr.cr4vegas.modelo.postgresql.dao.mantenimiento.estacion.ImagenEstacionDAO;
import es.rfgr.cr4vegas.modelo.postgresql.dao.mantenimiento.estacion.RegistroEstacionDAO;
import es.rfgr.cr4vegas.modelo.postgresql.dao.mantenimiento.estacion.RegistroImagenEstacionDAO;
import es.rfgr.cr4vegas.modelo.postgresql.dto.mantenimiento.estacion.ImagenEstacion;
import es.rfgr.cr4vegas.modelo.postgresql.dto.mantenimiento.estacion.RegistroEstacion;
import es.rfgr.cr4vegas.modelo.postgresql.dto.mantenimiento.estacion.RegistroImagenEstacion;
import es.rfgr.cr4vegas.modelo.postgresql.dto.mantenimiento.estacion.RegistroImagenEstacionPK;
import es.rfgr.cr4vegas.modelo.postgresql.exceptions.DAOException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;

public class RegistroImagenEstacionDAOImp implements RegistroImagenEstacionDAO {

	private static final String ERROR = "RegistroImagenEstacionDAO: ";

	private ObservableList<RegistroImagenEstacion> listaRegistroImagenEstacion;

	private RegistroImagenEstacionBD registroImagenEstacionBD;

	public RegistroImagenEstacionDAOImp() {
		listaRegistroImagenEstacion = FXCollections.observableArrayList();
		registroImagenEstacionBD = new RegistroImagenEstacionBD();
	}

	public void setConnection(Connection conn) {
		registroImagenEstacionBD.setConnection(conn);
	}

	public ObservableList<RegistroImagenEstacion> getRegistrosImagenesEstaciones() {
		return listaRegistroImagenEstacion;
	}

	public int getNumRegistrosImagenesEstaciones() {
		return listaRegistroImagenEstacion.size();
	}

	public RegistroImagenEstacionPK insert(RegistroImagenEstacion dto) throws DAOException {
		RegistroImagenEstacionPK pk = dto.createPK();

		try {
			if (listaRegistroImagenEstacion.contains(dto)) {
				throw new OperationsException("El registro-imagen-estación ya existe.");
			} else {
				pk = registroImagenEstacionBD.insert(dto);

				this.listaRegistroImagenEstacion.add(dto);
			}
		} catch (Exception e) {
			throwException(e);
		}
		return pk;
	}

	public ObservableList<RegistroImagenEstacionPK> insertAll(ObservableList<RegistroImagenEstacion> listToInsert)
			throws DAOException {
		ObservableList<RegistroImagenEstacionPK> listToReturn = FXCollections.observableArrayList();
		for (RegistroImagenEstacion dto : listToInsert) {
			RegistroImagenEstacionPK dtoPK = insert(dto);
			if (dtoPK != null) {
				listToReturn.add(dtoPK);
			}
		}
		return listToReturn;
	}

	public void update(RegistroImagenEstacionPK pk, RegistroImagenEstacion dto) throws DAOException {
		RegistroImagenEstacion oldRegistroImagenEstacion = getOldRegistroImagenEstacion(pk);

		try {
			if (dto == null) {
				throw new OperationsException("El registro-imagen-estación a modificar no puede ser nulo.");
			}
			if (!listaRegistroImagenEstacion.contains(oldRegistroImagenEstacion)) {
				throw new OperationsException("El registro-imagen-estación a modificar no existe.");
			} else {
				registroImagenEstacionBD.update(pk, dto);

				listaRegistroImagenEstacion.set(listaRegistroImagenEstacion.indexOf(oldRegistroImagenEstacion), dto);
			}
		} catch (Exception e) {
			throwException(e);
		}
	}

	public void updateAll(ObservableMap<RegistroImagenEstacionPK, RegistroImagenEstacion> mapToUpdate)
			throws DAOException {
		for (Map.Entry<RegistroImagenEstacionPK, RegistroImagenEstacion> entry : mapToUpdate.entrySet()) {
			update(entry.getKey(), entry.getValue());
		}
	}

	public void delete(RegistroImagenEstacionPK pk) throws DAOException {
		RegistroImagenEstacion oldRegistroImagenEstacion = getOldRegistroImagenEstacion(pk);

		try {
			if (pk == null) {
				throw new NullPointerException("No se puede eliminar un registro-imagen-estación nulo.");
			}
			if (!listaRegistroImagenEstacion.contains(oldRegistroImagenEstacion)) {
				throw new OperationsException("El registro-imagen-estación a eliminar no existe.");

			} else {
				registroImagenEstacionBD.delete(pk);

				listaRegistroImagenEstacion.remove(oldRegistroImagenEstacion);
			}
		} catch (Exception e) {
			throwException(e);
		}
	}

	public void deleteAll(ObservableList<RegistroImagenEstacion> listToDelete) throws DAOException {
		for (RegistroImagenEstacion dto : listToDelete) {
			delete(dto.createPK());
		}
	}

	public void findAll() throws DAOException {
		try {
			listaRegistroImagenEstacion.setAll(registroImagenEstacionBD.findAll());

		} catch (Exception e) {
			throwException(e);
		}
	}

	public RegistroImagenEstacion findByPrimaryKey(RegistroImagenEstacionPK pk) throws DAOException {
		try {
			return registroImagenEstacionBD.findByPrimaryKey(pk);

		} catch (SQLException e) {
			throwException(e);
		}
		return null;
	}

	public void setMaxRows(int maxRows) {
		registroImagenEstacionBD.setMaxRows(maxRows);
	}

	public int getMaxRows() {
		return registroImagenEstacionBD.getMaxRows();
	}

	public ObservableList<RegistroImagenEstacion> getRegistroEstacionImagenes(RegistroEstacion dto) {
		ObservableList<RegistroImagenEstacion> listToReturn = FXCollections.observableArrayList();
		for (RegistroImagenEstacion dtoOnList : listaRegistroImagenEstacion) {
			if (dtoOnList.getCodRegistroEstacion() == dto.getCodRegistro()) {
				listToReturn.add(dtoOnList);
			}
		}
		return listToReturn;
	}

	public void createDependencies(RegistroEstacionDAO registroEstacionDAO, ImagenEstacionDAO imagenEstacionDAO) {
		for (RegistroImagenEstacion dto : listaRegistroImagenEstacion) {
			dto.setObjRegistroEstacion(dependencyRegistroImagenEstacionEstacion(dto, registroEstacionDAO));
			dto.setObjImagenEstacion(dependencyRegistroImagenEstacionImagen(dto, imagenEstacionDAO));
		}
	}

	private RegistroEstacion dependencyRegistroImagenEstacionEstacion(RegistroImagenEstacion dto,
			RegistroEstacionDAO registroEstacionDAO) {
		for (RegistroEstacion registroEstacion : registroEstacionDAO.getRegistroEstacion()) {
			if (registroEstacion.getCodRegistro() == dto.getCodRegistroEstacion()) {
				return registroEstacion;
			}
		}
		return null;
	}

	private ImagenEstacion dependencyRegistroImagenEstacionImagen(RegistroImagenEstacion dto,
			ImagenEstacionDAO imagenEstacionDAO) {
		for (ImagenEstacion imagenEstacion : imagenEstacionDAO.getImagenEstacion()) {
			if (imagenEstacion.getCodImagen() == dto.getCodImagenEstacion()) {
				return imagenEstacion;
			}
		}
		return null;
	}

	private void throwException(Exception e) throws DAOException {
		throw new DAOException(ERROR + e.getMessage(), e);
	}

	private RegistroImagenEstacion getOldRegistroImagenEstacion(RegistroImagenEstacionPK pk) {
		RegistroImagenEstacion oldRegistroImagenEstacion = new RegistroImagenEstacion();
		oldRegistroImagenEstacion.setCodRegistroEstacion(pk.getCodRegistro());
		oldRegistroImagenEstacion.setCodImagenEstacion(pk.getCodImagen());
		oldRegistroImagenEstacion = listaRegistroImagenEstacion
				.get(listaRegistroImagenEstacion.indexOf(oldRegistroImagenEstacion));
		return oldRegistroImagenEstacion;
	}

}
