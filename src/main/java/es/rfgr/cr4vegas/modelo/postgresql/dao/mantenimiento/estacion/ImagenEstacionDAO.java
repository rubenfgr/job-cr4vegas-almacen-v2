package es.rfgr.cr4vegas.modelo.postgresql.dao.mantenimiento.estacion;

import java.sql.Connection;

import es.rfgr.cr4vegas.modelo.postgresql.dto.mantenimiento.estacion.Estacion;
import es.rfgr.cr4vegas.modelo.postgresql.dto.mantenimiento.estacion.ImagenEstacion;
import es.rfgr.cr4vegas.modelo.postgresql.dto.mantenimiento.estacion.ImagenEstacionPK;
import es.rfgr.cr4vegas.modelo.postgresql.exceptions.DAOException;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;

public interface ImagenEstacionDAO {

	void setConnection(Connection conn);

	ObservableList<ImagenEstacion> getImagenEstacion();

	int getNumImagenEstacion();

	ImagenEstacionPK insert(ImagenEstacion dto) throws DAOException;

	ObservableList<ImagenEstacionPK> insertAll(ObservableList<ImagenEstacion> listToInsert) throws DAOException;

	void update(ImagenEstacionPK pk, ImagenEstacion dto) throws DAOException;

	void updateAll(ObservableMap<ImagenEstacionPK, ImagenEstacion> mapToUpdate) throws DAOException;

	void delete(ImagenEstacionPK pk) throws DAOException;

	void deleteAll(ObservableList<ImagenEstacion> listToDelete) throws DAOException;

	void findAll() throws DAOException;

	ImagenEstacion findByPrimaryKey(ImagenEstacionPK pk) throws DAOException;

	void setMaxRows(int maxRows);

	int getMaxRows();

	ObservableList<ImagenEstacion> getEstacionImagenes(Estacion dto);

	void createDependencies(EstacionDAO estacionDAO);

}