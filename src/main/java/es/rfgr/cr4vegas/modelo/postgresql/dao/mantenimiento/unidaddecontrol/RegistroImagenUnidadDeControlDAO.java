package es.rfgr.cr4vegas.modelo.postgresql.dao.mantenimiento.unidaddecontrol;

import java.sql.Connection;

import es.rfgr.cr4vegas.modelo.postgresql.dto.mantenimiento.unidaddecontrol.RegistroImagenUnidadDeControl;
import es.rfgr.cr4vegas.modelo.postgresql.dto.mantenimiento.unidaddecontrol.RegistroImagenUnidadDeControlPK;
import es.rfgr.cr4vegas.modelo.postgresql.dto.mantenimiento.unidaddecontrol.RegistroUnidadDeControl;
import es.rfgr.cr4vegas.modelo.postgresql.exceptions.DAOException;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;

public interface RegistroImagenUnidadDeControlDAO {

	void setConnection(Connection conn);

	ObservableList<RegistroImagenUnidadDeControl> getRegistrosImagenesUdsControl();

	int getNumRegistrosImagenesUdsControl();

	RegistroImagenUnidadDeControlPK insert(RegistroImagenUnidadDeControl dto) throws DAOException;

	ObservableList<RegistroImagenUnidadDeControlPK> insertAll(
			ObservableList<RegistroImagenUnidadDeControl> listToInsert) throws DAOException;

	void update(RegistroImagenUnidadDeControlPK pk, RegistroImagenUnidadDeControl dto) throws DAOException;

	void updateAll(ObservableMap<RegistroImagenUnidadDeControlPK, RegistroImagenUnidadDeControl> mapToUpdate)
			throws DAOException;

	void delete(RegistroImagenUnidadDeControlPK pk) throws DAOException;

	void deleteAll(ObservableList<RegistroImagenUnidadDeControl> listToDelete) throws DAOException;

	void findAll() throws DAOException;

	RegistroImagenUnidadDeControl findByPrimaryKey(RegistroImagenUnidadDeControlPK pk) throws DAOException;

	void setMaxRows(int maxRows);

	int getMaxRows();

	ObservableList<RegistroImagenUnidadDeControl> getRegistroUDControlImagenes(RegistroUnidadDeControl dto);

	void createDependencies(RegistroUnidadDeControlDAO registroUDControlDAO,
			ImagenUnidadDeControlDAO imagenUDControlDAO);

}