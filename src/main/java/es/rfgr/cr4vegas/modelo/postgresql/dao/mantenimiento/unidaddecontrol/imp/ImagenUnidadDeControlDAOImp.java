package es.rfgr.cr4vegas.modelo.postgresql.dao.mantenimiento.unidaddecontrol.imp;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

import javax.management.OperationsException;

import es.rfgr.cr4vegas.modelo.postgresql.bd.mantenimiento.unidaddecontrol.ImagenUnidadDeControlBD;
import es.rfgr.cr4vegas.modelo.postgresql.dao.mantenimiento.unidaddecontrol.ImagenUnidadDeControlDAO;
import es.rfgr.cr4vegas.modelo.postgresql.dao.mantenimiento.unidaddecontrol.UnidadDeControlDAO;
import es.rfgr.cr4vegas.modelo.postgresql.dto.mantenimiento.unidaddecontrol.ImagenUnidadDeControl;
import es.rfgr.cr4vegas.modelo.postgresql.dto.mantenimiento.unidaddecontrol.ImagenUnidadDeControlPK;
import es.rfgr.cr4vegas.modelo.postgresql.dto.mantenimiento.unidaddecontrol.UnidadDeControl;
import es.rfgr.cr4vegas.modelo.postgresql.exceptions.DAOException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;

public class ImagenUnidadDeControlDAOImp implements ImagenUnidadDeControlDAO {

	private static final String ERROR = "ImagenUdControlDAO: ";

	private ObservableList<ImagenUnidadDeControl> listaImagenesUdControl;

	private ImagenUnidadDeControlBD imagenUdControlBD;

	public ImagenUnidadDeControlDAOImp() {
		listaImagenesUdControl = FXCollections.observableArrayList();
		imagenUdControlBD = new ImagenUnidadDeControlBD();
	}

	public void setConnection(Connection conn) {
		imagenUdControlBD.setConnection(conn);
	}

	public ObservableList<ImagenUnidadDeControl> getImagenesUdControl() {
		return listaImagenesUdControl;
	}

	public int getNumImagenesUdControl() {
		return listaImagenesUdControl.size();
	}

	public ImagenUnidadDeControlPK insert(ImagenUnidadDeControl dto) throws DAOException {
		ImagenUnidadDeControlPK pk = (ImagenUnidadDeControlPK) dto.createPK();

		try {
			if (listaImagenesUdControl.contains(dto)) {
				throw new OperationsException("La imagen de ud.control ya existe.");

			} else {
				pk = imagenUdControlBD.insert(dto);

				this.listaImagenesUdControl.add(dto);
			}
		} catch (Exception e) {
			throwException(e);
		}
		return pk;
	}

	public ObservableList<ImagenUnidadDeControlPK> insertAll(ObservableList<ImagenUnidadDeControl> listToInsert)
			throws DAOException {
		ObservableList<ImagenUnidadDeControlPK> listToReturn = FXCollections.observableArrayList();
		for (ImagenUnidadDeControl dto : listToInsert) {
			ImagenUnidadDeControlPK dtoPK = insert(dto);
			if (dtoPK != null) {
				listToReturn.add(dtoPK);
			}
		}
		return listToReturn;
	}

	public void update(ImagenUnidadDeControlPK pk, ImagenUnidadDeControl dto) throws DAOException {
		ImagenUnidadDeControl oldImagenUDControl = getOldImagenUDControl(pk);

		try {
			if (dto == null) {
				throw new OperationsException("La imagen de ud.control a modificar no puede ser nula.");
			}
			if (!listaImagenesUdControl.contains(oldImagenUDControl)) {
				throw new OperationsException("La imagen de ud.control a modificar no existe.");

			} else {
				imagenUdControlBD.update(pk, dto);

				listaImagenesUdControl.set(listaImagenesUdControl.indexOf(oldImagenUDControl), dto);
			}
		} catch (Exception e) {
			throwException(e);
		}
	}

	public void updateAll(ObservableMap<ImagenUnidadDeControlPK, ImagenUnidadDeControl> mapToUpdate)
			throws DAOException {
		for (Map.Entry<ImagenUnidadDeControlPK, ImagenUnidadDeControl> entry : mapToUpdate.entrySet()) {
			update(entry.getKey(), entry.getValue());
		}
	}

	public void delete(ImagenUnidadDeControlPK pk) throws DAOException {
		ImagenUnidadDeControl oldImagenUDControl = getOldImagenUDControl(pk);

		try {
			if (pk == null) {
				throw new NullPointerException("No se puede eliminar una imagen de ud.control nula.");
			}
			if (!listaImagenesUdControl.contains(oldImagenUDControl)) {
				throw new OperationsException("La imagen de ud.control a eloiminar no existe.");

			} else {
				imagenUdControlBD.delete(pk);

				listaImagenesUdControl.remove(oldImagenUDControl);
			}
		} catch (Exception e) {
			throwException(e);
		}
	}

	public void deleteAll(ObservableList<ImagenUnidadDeControl> listToDelete) throws DAOException {
		for (ImagenUnidadDeControl dto : listToDelete) {
			delete((ImagenUnidadDeControlPK) dto.createPK());
		}
	}

	public void findAll() throws DAOException {
		try {
			listaImagenesUdControl.setAll(imagenUdControlBD.findAll());

		} catch (SQLException e) {
			throwException(e);
		}
	}

	public ImagenUnidadDeControl findByPrimaryKey(ImagenUnidadDeControlPK pk) throws DAOException {
		try {
			return imagenUdControlBD.findByPrimaryKey(pk);

		} catch (SQLException e) {
			throwException(e);
		}
		return null;
	}

	public void setMaxRows(int maxRows) {
		imagenUdControlBD.setMaxRows(maxRows);
	}

	public int getMaxRows() {
		return imagenUdControlBD.getMaxRows();
	}

	public ObservableList<ImagenUnidadDeControl> getUdControlImagenes(UnidadDeControl dto) {
		ObservableList<ImagenUnidadDeControl> listaSalida = FXCollections.observableArrayList();
		for (ImagenUnidadDeControl iuc : listaImagenesUdControl) {
			if (iuc.getCodUdControl() == dto.getCodUdControl()) {
				listaSalida.add(iuc);
			}
		}
		return listaSalida;
	}

	public void createDependencies(UnidadDeControlDAO uDControlDAO) {
		for (ImagenUnidadDeControl imagenUdControl : listaImagenesUdControl) {
			imagenUdControl.setObjUdControl(dependencyImagenUdControlUdControl(imagenUdControl, uDControlDAO));
		}
	}

	private UnidadDeControl dependencyImagenUdControlUdControl(ImagenUnidadDeControl dto,
			UnidadDeControlDAO uDControlDAO) {
		for (UnidadDeControl udControl : uDControlDAO.getUdsControl()) {
			if (udControl.getCodUdControl() == dto.getCodUdControl()) {
				return udControl;
			}
		}
		return null;
	}

	private void throwException(Exception e) throws DAOException {
		throw new DAOException(ERROR + e.getMessage(), e);
	}

	private ImagenUnidadDeControl getOldImagenUDControl(ImagenUnidadDeControlPK pk) {
		ImagenUnidadDeControl oldImagenUDControl = new ImagenUnidadDeControl();
		oldImagenUDControl.setCodImagen(pk.getCodImagen());
		oldImagenUDControl = listaImagenesUdControl.get(listaImagenesUdControl.indexOf(oldImagenUDControl));
		return oldImagenUDControl;
	}
}
