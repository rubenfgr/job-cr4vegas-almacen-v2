package es.rfgr.cr4vegas.modelo.postgresql.dao.mantenimiento.instalacion;

import java.sql.Connection;

import es.rfgr.cr4vegas.modelo.postgresql.dto.mantenimiento.instalacion.RegistroImagenInstalacion;
import es.rfgr.cr4vegas.modelo.postgresql.dto.mantenimiento.instalacion.RegistroImagenInstalacionPK;
import es.rfgr.cr4vegas.modelo.postgresql.dto.mantenimiento.instalacion.RegistroInstalacion;
import es.rfgr.cr4vegas.modelo.postgresql.exceptions.DAOException;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;

public interface RegistroImagenInstalacionDAO {

	void setConnection(Connection conn);

	ObservableList<RegistroImagenInstalacion> getRegistrosImagenesInstalaciones();

	int getNumRegistrosImagenesInstalaciones();

	RegistroImagenInstalacionPK insert(RegistroImagenInstalacion dto) throws DAOException;

	ObservableList<RegistroImagenInstalacionPK> insertAll(ObservableList<RegistroImagenInstalacion> listToInsert)
			throws DAOException;

	void update(RegistroImagenInstalacionPK pk, RegistroImagenInstalacion dto) throws DAOException;

	void updateAll(ObservableMap<RegistroImagenInstalacionPK, RegistroImagenInstalacion> mapToUpdate)
			throws DAOException;

	void delete(RegistroImagenInstalacionPK pk) throws DAOException;

	void deleteAll(ObservableList<RegistroImagenInstalacion> listToDelete) throws DAOException;

	void findAll() throws DAOException;

	RegistroImagenInstalacion findByPrimaryKey(RegistroImagenInstalacionPK pk) throws DAOException;

	void setMaxRows(int maxRows);

	int getMaxRows();

	ObservableList<RegistroImagenInstalacion> getRegistroInstalacionImagenes(RegistroInstalacion dto);

	void createDependencies(RegistroInstalacionDAO registroInstalacionDAO,
			ImagenInstalacionDAO imagenInstalacionDAO);

}