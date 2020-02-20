package es.rfgr.cr4vegas.modelo.postgresql.dao.herencia;

import java.sql.Connection;

import es.rfgr.cr4vegas.modelo.postgresql.dto.herencia.imagen.Imagen;
import es.rfgr.cr4vegas.modelo.postgresql.dto.herencia.imagen.ImagenPK;
import es.rfgr.cr4vegas.modelo.postgresql.exceptions.DAOException;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;

public interface ImagenDAO {

	void setConnection(Connection conn);

	ObservableList<Imagen> getImagen();

	int getNumImagen();

	ImagenPK insert(Imagen dto) throws DAOException;

	ObservableList<ImagenPK> insertAll(ObservableList<Imagen> listToInsert) throws DAOException;

	void update(ImagenPK pk, Imagen dto) throws DAOException;

	void updateAll(ObservableMap<ImagenPK, Imagen> mapToUpdate) throws DAOException;

	void delete(ImagenPK pk) throws DAOException;

	void deleteAll(ObservableList<Imagen> listToDelete) throws DAOException;

	void findAll() throws DAOException;

	Imagen findByPrimaryKey(ImagenPK pk) throws DAOException;

	void setMaxRows(int maxRows);

	int getMaxRows();

}