package es.rfgr.cr4vegas.modelo.postgresql.dao.mantenimiento.unidaddecontrol.imp;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

import javax.management.OperationsException;

import es.rfgr.cr4vegas.modelo.postgresql.bd.mantenimiento.unidaddecontrol.UnidadDeControlBD;
import es.rfgr.cr4vegas.modelo.postgresql.dao.mantenimiento.instalacion.InstalacionDAO;
import es.rfgr.cr4vegas.modelo.postgresql.dao.mantenimiento.unidaddecontrol.ImagenUnidadDeControlDAO;
import es.rfgr.cr4vegas.modelo.postgresql.dao.mantenimiento.unidaddecontrol.UnidadDeControlDAO;
import es.rfgr.cr4vegas.modelo.postgresql.dto.mantenimiento.instalacion.Instalacion;
import es.rfgr.cr4vegas.modelo.postgresql.dto.mantenimiento.unidaddecontrol.ImagenUnidadDeControl;
import es.rfgr.cr4vegas.modelo.postgresql.dto.mantenimiento.unidaddecontrol.UnidadDeControl;
import es.rfgr.cr4vegas.modelo.postgresql.dto.mantenimiento.unidaddecontrol.UnidadDeControlPK;
import es.rfgr.cr4vegas.modelo.postgresql.exceptions.DAOException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;

public class UnidadDeControlDAOImp implements UnidadDeControlDAO {

	private static final String ERROR = "UdControlDAO: ";

	private ObservableList<UnidadDeControl> listaUdsControl;

	private UnidadDeControlBD udControlBD;

	public UnidadDeControlDAOImp() {
		listaUdsControl = FXCollections.observableArrayList();
		udControlBD = new UnidadDeControlBD();
	}

	public void setConnection(Connection conn) {
		udControlBD.setConnection(conn);
	}

	public ObservableList<UnidadDeControl> getUdsControl() {
		return listaUdsControl;
	}

	public int getNumUdsControl() {
		return listaUdsControl.size();
	}

	public UnidadDeControlPK insert(UnidadDeControl dto) throws DAOException {
		UnidadDeControlPK pk = dto.createPK();

		try {
			if (listaUdsControl.contains(dto)) {
				throw new OperationsException("La Ud.Control ya existe.");

			} else {
				pk = udControlBD.insert(dto);

				this.listaUdsControl.add(dto);
			}
		} catch (Exception e) {
			throwException(e);
		}
		return pk;
	}

	public ObservableList<UnidadDeControlPK> insertAll(ObservableList<UnidadDeControl> listToInsert) throws DAOException {
		ObservableList<UnidadDeControlPK> listToReturn = FXCollections.observableArrayList();
		for (UnidadDeControl dto : listToInsert) {
			UnidadDeControlPK dtoPK = insert(dto);
			if (dtoPK != null) {
				listToReturn.add(dtoPK);
			}
		}
		return listToReturn;
	}

	public void update(UnidadDeControlPK pk, UnidadDeControl dto) throws DAOException {
		UnidadDeControl oldUDControl = getOldUDControl(pk);

		try {
			if (dto == null) {
				throw new OperationsException("La Ud.Control a modificar no puede ser nula.");
			}
			if (!listaUdsControl.contains(oldUDControl)) {
				throw new OperationsException("La Ud.Control a modificar no existe.");

			} else {
				udControlBD.update(pk, dto);

				listaUdsControl.set(listaUdsControl.indexOf(oldUDControl), dto);
			}
		} catch (Exception e) {
			throwException(e);
		}
	}

	public void updateAll(ObservableMap<UnidadDeControlPK, UnidadDeControl> mapToUpdate) throws DAOException {
		for (Map.Entry<UnidadDeControlPK, UnidadDeControl> entry : mapToUpdate.entrySet()) {
			update(entry.getKey(), entry.getValue());
		}
	}

	public void delete(UnidadDeControlPK pk) throws DAOException {
		UnidadDeControl oldUDControl = getOldUDControl(pk);

		try {
			if (pk == null) {
				throw new NullPointerException("No se puede eliminar una Ud.Control nula.");
			}
			if (!listaUdsControl.contains(oldUDControl)) {
				throw new OperationsException("La Ud.Control a eliminar no existe.");

			} else {
				udControlBD.delete(pk);

				listaUdsControl.remove(oldUDControl);
			}
		} catch (Exception e) {
			throwException(e);
		}
	}

	public void deleteAll(ObservableList<UnidadDeControl> listToDelete) throws DAOException {
		for (UnidadDeControl dto : listToDelete) {
			delete(dto.createPK());
		}
	}

	public void findAll() throws DAOException {
		try {
			listaUdsControl.setAll(udControlBD.findAll());

		} catch (SQLException e) {
			throwException(e);
		}
	}

	public UnidadDeControl findByPrimaryKey(UnidadDeControlPK pk) throws DAOException {
		try {
			return udControlBD.findByPrimaryKey(pk);

		} catch (SQLException e) {
			throwException(e);
		}
		return null;
	}
	
	public void setMaxRows(int maxRows) {
		udControlBD.setMaxRows(maxRows);
	}

	public int getMaxRows() {
		return udControlBD.getMaxRows();
	}

	public void createDependencies(InstalacionDAO instalacionDAO, ImagenUnidadDeControlDAO imagenUDControlDAO) {
		for (UnidadDeControl udControl : listaUdsControl) {
			udControl.setObjImagenUdControl(dependencyUdControlImagenUdControl(udControl, imagenUDControlDAO));
			udControl.setObjInstalacion(dependencyUdControlInstalacion(udControl, instalacionDAO));
		}
	}

	private Instalacion dependencyUdControlInstalacion(UnidadDeControl dto, InstalacionDAO instalacionDAO) {
		for (Instalacion instalacion : instalacionDAO.getInstalaciones()) {
			if (instalacion.getCodInstalacion() == dto.getCodInstalacion()) {
				return instalacion;
			}
		}
		return null;
	}

	private ImagenUnidadDeControl dependencyUdControlImagenUdControl(UnidadDeControl dto, ImagenUnidadDeControlDAO imagenUDControlDAO) {
		for (ImagenUnidadDeControl imagenUdControl : imagenUDControlDAO.getImagenesUdControl()) {
			if (imagenUdControl.getCodImagen() == dto.getCodImagen()) {
				return imagenUdControl;
			}
		}
		return null;
	}

	private void throwException(Exception e) throws DAOException {
		throw new DAOException(ERROR + e.getMessage(), e);
	}

	private UnidadDeControl getOldUDControl(UnidadDeControlPK pk) {
		UnidadDeControl oldUdControl = new UnidadDeControl();
		oldUdControl.setCodUdControl(pk.getCodUDControl());
		oldUdControl = listaUdsControl.get(listaUdsControl.indexOf(oldUdControl));
		return oldUdControl;
	}

}
