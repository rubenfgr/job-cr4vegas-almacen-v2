package es.rfgr.cr4vegas.modelo.postgresql.dao.mantenimiento.estacion;

import java.sql.Connection;

import es.rfgr.cr4vegas.modelo.postgresql.dto.mantenimiento.estacion.RegistroEstacion;
import es.rfgr.cr4vegas.modelo.postgresql.dto.mantenimiento.estacion.RegistroImagenEstacion;
import es.rfgr.cr4vegas.modelo.postgresql.dto.mantenimiento.estacion.RegistroImagenEstacionPK;
import es.rfgr.cr4vegas.modelo.postgresql.exceptions.DAOException;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;

public interface RegistroImagenEstacionDAO {

	void setConnection(Connection conn);

	ObservableList<RegistroImagenEstacion> getRegistrosImagenesEstaciones();

	int getNumRegistrosImagenesEstaciones();

	RegistroImagenEstacionPK insert(RegistroImagenEstacion dto) throws DAOException;

	ObservableList<RegistroImagenEstacionPK> insertAll(ObservableList<RegistroImagenEstacion> listToInsert)
			throws DAOException;

	void update(RegistroImagenEstacionPK pk, RegistroImagenEstacion dto) throws DAOException;

	void updateAll(ObservableMap<RegistroImagenEstacionPK, RegistroImagenEstacion> mapToUpdate) throws DAOException;

	void delete(RegistroImagenEstacionPK pk) throws DAOException;

	void deleteAll(ObservableList<RegistroImagenEstacion> listToDelete) throws DAOException;

	void findAll() throws DAOException;

	RegistroImagenEstacion findByPrimaryKey(RegistroImagenEstacionPK pk) throws DAOException;

	void setMaxRows(int maxRows);

	int getMaxRows();

	ObservableList<RegistroImagenEstacion> getRegistroEstacionImagenes(RegistroEstacion dto);

	void createDependencies(RegistroEstacionDAO registroEstacionDAO, ImagenEstacionDAO imagenEstacionDAO);

}