package es.rfgr.cr4vegas.modelo.postgresql.dao.mantenimiento.unidaddecontrol;

import java.sql.Connection;

import es.rfgr.cr4vegas.modelo.postgresql.dto.mantenimiento.unidaddecontrol.ImagenUnidadDeControl;
import es.rfgr.cr4vegas.modelo.postgresql.dto.mantenimiento.unidaddecontrol.ImagenUnidadDeControlPK;
import es.rfgr.cr4vegas.modelo.postgresql.dto.mantenimiento.unidaddecontrol.UnidadDeControl;
import es.rfgr.cr4vegas.modelo.postgresql.exceptions.DAOException;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;

public interface ImagenUnidadDeControlDAO {

	void setConnection(Connection conn);

	ObservableList<ImagenUnidadDeControl> getImagenesUdControl();

	int getNumImagenesUdControl();

	ImagenUnidadDeControlPK insert(ImagenUnidadDeControl dto) throws DAOException;

	ObservableList<ImagenUnidadDeControlPK> insertAll(ObservableList<ImagenUnidadDeControl> listToInsert)
			throws DAOException;

	void update(ImagenUnidadDeControlPK pk, ImagenUnidadDeControl dto) throws DAOException;

	void updateAll(ObservableMap<ImagenUnidadDeControlPK, ImagenUnidadDeControl> mapToUpdate) throws DAOException;

	void delete(ImagenUnidadDeControlPK pk) throws DAOException;

	void deleteAll(ObservableList<ImagenUnidadDeControl> listToDelete) throws DAOException;

	void findAll() throws DAOException;

	ImagenUnidadDeControl findByPrimaryKey(ImagenUnidadDeControlPK pk) throws DAOException;

	void setMaxRows(int maxRows);

	int getMaxRows();

	ObservableList<ImagenUnidadDeControl> getUdControlImagenes(UnidadDeControl dto);

	void createDependencies(UnidadDeControlDAO uDControlDAO);

}