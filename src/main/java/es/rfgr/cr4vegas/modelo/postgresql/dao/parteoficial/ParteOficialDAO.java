package es.rfgr.cr4vegas.modelo.postgresql.dao.parteoficial;

import es.rfgr.cr4vegas.modelo.postgresql.dao.parte.TipoParteDAO;
import es.rfgr.cr4vegas.modelo.postgresql.dto.parteoficial.ParteOficial;
import es.rfgr.cr4vegas.modelo.postgresql.dto.parteoficial.ParteOficialPK;
import es.rfgr.cr4vegas.modelo.postgresql.exceptions.DAOException;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;

public interface ParteOficialDAO {

	ObservableList<ParteOficial> getPartesOficiales();

	int getNumPartesOficiales();

	ParteOficialPK insert(ParteOficial dto) throws DAOException;

	ObservableList<ParteOficialPK> insertAll(ObservableList<ParteOficial> listToInsert) throws DAOException;

	void update(ParteOficialPK pk, ParteOficial dto) throws DAOException;

	void updateAll(ObservableMap<ParteOficialPK, ParteOficial> mapToUpdate) throws DAOException;

	void delete(ParteOficialPK pk) throws DAOException;

	void deleteAll(ObservableList<ParteOficial> listToDelete) throws DAOException;

	void findAll() throws DAOException;

	ParteOficial findByPrimaryKey(ParteOficialPK pk) throws DAOException;

	void setMaxRows(int maxRows);

	int getMaxRows();

	void createDependencies(TipoParteDAO tipoParteDAO);

}