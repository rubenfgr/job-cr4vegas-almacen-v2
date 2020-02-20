package es.rfgr.cr4vegas.modelo.postgresql.dao.herencia.imp;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

import javax.management.OperationsException;

import es.rfgr.cr4vegas.modelo.postgresql.bd.herencia.ImagenBD;
import es.rfgr.cr4vegas.modelo.postgresql.dao.herencia.ImagenDAO;
import es.rfgr.cr4vegas.modelo.postgresql.dto.herencia.imagen.Imagen;
import es.rfgr.cr4vegas.modelo.postgresql.dto.herencia.imagen.ImagenPK;
import es.rfgr.cr4vegas.modelo.postgresql.exceptions.DAOException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;

public class ImagenDAOImp implements ImagenDAO {

	private static final String ERROR = "ImagenDAO: ";

	private ObservableList<Imagen> listaImagen;

	private ImagenBD ImagenBD;

	public ImagenDAOImp() {
		listaImagen = FXCollections.observableArrayList();
		ImagenBD = new ImagenBD();
	}

	public void setConnection(Connection conn) {
		ImagenBD.setConnection(conn);
	}

	public ObservableList<Imagen> getImagen() {
		return listaImagen;
	}

	public int getNumImagen() {
		return listaImagen.size();
	}

	public ImagenPK insert(Imagen dto) throws DAOException {
		ImagenPK pk = (ImagenPK) dto.createPK();

		try {
			if (listaImagen.contains(dto)) {
				throw new OperationsException("La imagen de estación ya existe.");

			} else {
				pk = ImagenBD.insert(dto);

				this.listaImagen.add(dto);
			}
		} catch (Exception e) {
			throwException(e);
		}
		return pk;
	}

	public ObservableList<ImagenPK> insertAll(ObservableList<Imagen> listToInsert) throws DAOException {
		ObservableList<ImagenPK> listToReturn = FXCollections.observableArrayList();
		for (Imagen dto : listToInsert) {
			ImagenPK dtoPK = insert(dto);
			if (dtoPK != null) {
				listToReturn.add(dtoPK);
			}
		}
		return listToReturn;
	}

	public void update(ImagenPK pk, Imagen dto) throws DAOException {
		Imagen oldImagen = getOldImagen(pk);

		try {
			if (dto == null) {
				throw new OperationsException("La imagen de estación a modificar no puede ser nula.");
			}
			if (!listaImagen.contains(oldImagen)) {
				throw new OperationsException("La imagen de estación a modificar no existe.");

			} else {
				ImagenBD.update(pk, dto);

				listaImagen.set(listaImagen.indexOf(oldImagen), dto);
			}
		} catch (Exception e) {
			throwException(e);
		}
	}

	public void updateAll(ObservableMap<ImagenPK, Imagen> mapToUpdate) throws DAOException {
		for (Map.Entry<ImagenPK, Imagen> entry : mapToUpdate.entrySet()) {
			update(entry.getKey(), entry.getValue());
		}
	}

	public void delete(ImagenPK pk) throws DAOException {
		Imagen oldImagen = getOldImagen(pk);

		try {
			if (pk == null) {
				throw new NullPointerException("No se puede eliminar una imagen de estación nula.");
			}
			if (!listaImagen.contains(oldImagen)) {
				throw new OperationsException("La imagen de estación a eliminar no existe.");

			} else {
				ImagenBD.delete(pk);

				listaImagen.remove(oldImagen);
			}
		} catch (Exception e) {
			throwException(e);
		}
	}

	public void deleteAll(ObservableList<Imagen> listToDelete) throws DAOException {
		for (Imagen dto : listToDelete) {
			delete((ImagenPK) dto.createPK());
		}
	}

	public void findAll() throws DAOException {
		try {
			listaImagen.setAll(ImagenBD.findAll());
		} catch (Exception e) {
			throwException(e);
		}
	}

	public Imagen findByPrimaryKey(ImagenPK pk) throws DAOException {
		try {
			return ImagenBD.findByPrimaryKey(pk);

		} catch (SQLException e) {
			throwException(e);
		}
		return null;
	}

	public void setMaxRows(int maxRows) {
		ImagenBD.setMaxRows(maxRows);
	}

	public int getMaxRows() {
		return ImagenBD.getMaxRows();
	}

	private void throwException(Exception e) throws DAOException {
		throw new DAOException(ERROR + e.getMessage(), e);
	}

	private Imagen getOldImagen(ImagenPK pk) {
		Imagen oldImagen = new Imagen();
		oldImagen.setCodImagen(pk.getCodImagen());
		oldImagen = listaImagen.get(listaImagen.indexOf(oldImagen));
		return oldImagen;
	}
}
