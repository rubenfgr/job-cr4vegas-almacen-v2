package es.rfgr.cr4vegas.modelo.postgresql.dao.mantenimiento.instalacion.imp;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

import javax.management.OperationsException;

import es.rfgr.cr4vegas.modelo.postgresql.bd.mantenimiento.instalacion.InstalacionBD;
import es.rfgr.cr4vegas.modelo.postgresql.dao.mantenimiento.estacion.EstacionDAO;
import es.rfgr.cr4vegas.modelo.postgresql.dao.mantenimiento.instalacion.ImagenInstalacionDAO;
import es.rfgr.cr4vegas.modelo.postgresql.dao.mantenimiento.instalacion.InstalacionDAO;
import es.rfgr.cr4vegas.modelo.postgresql.dao.mantenimiento.instalacion.TipoInstalacionDAO;
import es.rfgr.cr4vegas.modelo.postgresql.dto.mantenimiento.estacion.Estacion;
import es.rfgr.cr4vegas.modelo.postgresql.dto.mantenimiento.instalacion.ImagenInstalacion;
import es.rfgr.cr4vegas.modelo.postgresql.dto.mantenimiento.instalacion.Instalacion;
import es.rfgr.cr4vegas.modelo.postgresql.dto.mantenimiento.instalacion.InstalacionPK;
import es.rfgr.cr4vegas.modelo.postgresql.dto.mantenimiento.instalacion.TipoInstalacion;
import es.rfgr.cr4vegas.modelo.postgresql.exceptions.DAOException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;

public class InstalacionDAOImp implements InstalacionDAO {

	private static final String ERROR = "InstalacionDAO: ";

	private ObservableList<Instalacion> listaInstalaciones;

	private InstalacionBD instalacionBD;

	public InstalacionDAOImp() {
		listaInstalaciones = FXCollections.observableArrayList();
		instalacionBD = new InstalacionBD();
	}

	public void setConnection(Connection conn) {
		instalacionBD.setConnection(conn);
	}

	public ObservableList<Instalacion> getInstalaciones() {
		return listaInstalaciones;
	}

	public int getNumInstalaciones() {
		return listaInstalaciones.size();
	}

	public InstalacionPK insert(Instalacion dto) throws DAOException {
		InstalacionPK pk = null;

		try {
			if (listaInstalaciones.contains(dto)) {
				throw new OperationsException("La instalación ya existe.");
			} else {
				pk = instalacionBD.insert(dto);

				this.listaInstalaciones.add(dto);
			}
		} catch (Exception e) {
			throwException(e);
		}
		return pk;
	}

	public ObservableList<InstalacionPK> insertAll(ObservableList<Instalacion> listToInsert) throws DAOException {
		ObservableList<InstalacionPK> listToReturn = FXCollections.observableArrayList();
		for (Instalacion dto : listToInsert) {
			InstalacionPK dtoPK = insert(dto);
			if (dtoPK != null) {
				listToReturn.add(dtoPK);
			}
		}
		return listToReturn;
	}

	public void update(InstalacionPK pk, Instalacion dto) throws DAOException {
		Instalacion oldInstalacion = getOldInstalacion(pk);

		try {
			if (dto == null) {
				throw new OperationsException("La instalación a modificar no puede ser nula.");
			}
			if (!listaInstalaciones.contains(oldInstalacion)) {
				throw new OperationsException("La instalación a modificar no existe.");

			} else {
				instalacionBD.update(pk, dto);

				listaInstalaciones.set(listaInstalaciones.indexOf(oldInstalacion), dto);
			}

		} catch (Exception e) {
			throwException(e);
		}
	}

	public void updateAll(ObservableMap<InstalacionPK, Instalacion> mapToUpdate) throws DAOException {
		for (Map.Entry<InstalacionPK, Instalacion> entry : mapToUpdate.entrySet()) {
			update(entry.getKey(), entry.getValue());
		}
	}

	public void delete(InstalacionPK pk) throws DAOException {
		Instalacion oldInstalacion = getOldInstalacion(pk);

		try {
			if (pk == null) {
				throw new NullPointerException("No se puede eliminar una instalación nula.");
			}
			if (!listaInstalaciones.contains(oldInstalacion)) {
				throw new OperationsException("La instalación a eliminar no existe.");

			} else {
				instalacionBD.delete(pk);

				listaInstalaciones.remove(oldInstalacion);
			}
		} catch (Exception e) {
			throwException(e);
		}
	}

	public void deleteAll(ObservableList<Instalacion> listToDelete) throws DAOException {
		for (Instalacion dto : listToDelete) {
			delete(dto.createPK());
		}
	}

	public void findAll() throws DAOException {
		try {
			listaInstalaciones.setAll(instalacionBD.findAll());

		} catch (SQLException e) {
			throwException(e);
		}
	}

	public Instalacion findByPrimaryKey(InstalacionPK pk) throws DAOException {
		try {
			return instalacionBD.findByPrimaryKey(pk);

		} catch (SQLException e) {
			throwException(e);
		}
		return null;
	}
	
	public void setMaxRows(int maxRows) {
		instalacionBD.setMaxRows(maxRows);
	}

	public int getMaxRows() {
		return instalacionBD.getMaxRows();
	}

	public void createDependencies(EstacionDAO estacionDAO, TipoInstalacionDAO tipoInstalacionDAO,
			ImagenInstalacionDAO imagenInstalacionDAO) {
		for (Instalacion instalacion : listaInstalaciones) {
			instalacion.setObjEstacion(dependencyInstalacionEstacion(instalacion, estacionDAO));
			instalacion.setObjTipoInstalacion(dependencyInstalacionTipo(instalacion, tipoInstalacionDAO));
			instalacion.setObjImagenInstalacion(dependencyInstalacionImagen(instalacion, imagenInstalacionDAO));
		}
	}

	private Estacion dependencyInstalacionEstacion(Instalacion dto, EstacionDAO estacionDAO) {
		for (Estacion estacion : estacionDAO.getEstaciones()) {
			if (estacion.getCodEstacion() == dto.getCodEstacion()) {
				return estacion;
			}
		}
		return null;
	}

	private TipoInstalacion dependencyInstalacionTipo(Instalacion dto, TipoInstalacionDAO tipoInstalacionDAO) {
		for (TipoInstalacion tipoInstalacion : tipoInstalacionDAO.getTiposInstalacion()) {
			if (tipoInstalacion.getNombre().equals(dto.getTipo())) {
				return tipoInstalacion;
			}
		}
		return null;
	}

	private ImagenInstalacion dependencyInstalacionImagen(Instalacion dto, ImagenInstalacionDAO imagenInstalacionDAO) {
		for (ImagenInstalacion imagenInstalacion : imagenInstalacionDAO.getImagenInstalacion()) {
			if (imagenInstalacion.getCodImagen() == dto.getCodImagen()) {
				return imagenInstalacion;
			}
		}
		return null;
	}

	private void throwException(Exception e) throws DAOException {
		throw new DAOException(ERROR + e.getMessage(), e);
	}

	private Instalacion getOldInstalacion(InstalacionPK pk) {
		Instalacion oldInstalacion = new Instalacion();
		oldInstalacion.setCodInstalacion(pk.getCodInstalacion());
		oldInstalacion = listaInstalaciones.get(listaInstalaciones.indexOf(oldInstalacion));
		return oldInstalacion;
	}
	
}
