package es.rfgr.cr4vegas.modelo.postgresql.dao.mantenimiento.instalacion;

import java.sql.Connection;

import es.rfgr.cr4vegas.modelo.postgresql.dto.mantenimiento.instalacion.ImagenInstalacion;
import es.rfgr.cr4vegas.modelo.postgresql.dto.mantenimiento.instalacion.ImagenInstalacionPK;
import es.rfgr.cr4vegas.modelo.postgresql.dto.mantenimiento.instalacion.Instalacion;
import es.rfgr.cr4vegas.modelo.postgresql.exceptions.DAOException;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;

public interface ImagenInstalacionDAO {

	void setConnection(Connection conn);

	ObservableList<ImagenInstalacion> getImagenInstalacion();

	int getNumImagenInstalacion();

	ImagenInstalacionPK insert(ImagenInstalacion dto) throws DAOException;

	ObservableList<ImagenInstalacionPK> insertAll(ObservableList<ImagenInstalacion> listToInsert) throws DAOException;

	void update(ImagenInstalacionPK pk, ImagenInstalacion dto) throws DAOException;

	void updateAll(ObservableMap<ImagenInstalacionPK, ImagenInstalacion> mapToUpdate) throws DAOException;

	void delete(ImagenInstalacionPK pk) throws DAOException;

	void deleteAll(ObservableList<ImagenInstalacion> listToDelete) throws DAOException;

	void findAll() throws DAOException;

	ImagenInstalacion findByPrimaryKey(ImagenInstalacionPK pk) throws DAOException;

	void setMaxRows(int maxRows);

	int getMaxRows();

	ObservableList<ImagenInstalacion> getInstalacionImagenes(Instalacion dto);

	void createDependencies(InstalacionDAO instalacionDAO);

}