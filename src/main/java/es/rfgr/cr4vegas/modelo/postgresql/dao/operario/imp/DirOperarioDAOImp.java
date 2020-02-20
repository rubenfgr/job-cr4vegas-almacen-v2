package es.rfgr.cr4vegas.modelo.postgresql.dao.operario.imp;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

import javax.management.OperationsException;

import es.rfgr.cr4vegas.modelo.postgresql.bd.operario.DirOperarioBD;
import es.rfgr.cr4vegas.modelo.postgresql.dao.operario.DirOperarioDAO;
import es.rfgr.cr4vegas.modelo.postgresql.dao.operario.OperarioDAO;
import es.rfgr.cr4vegas.modelo.postgresql.dto.operario.DirOperario;
import es.rfgr.cr4vegas.modelo.postgresql.dto.operario.DirOperarioPK;
import es.rfgr.cr4vegas.modelo.postgresql.dto.operario.Operario;
import es.rfgr.cr4vegas.modelo.postgresql.exceptions.DAOException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;

public class DirOperarioDAOImp implements DirOperarioDAO {

    private static final String ERROR = "DirOperarioDAO: ";

    private ObservableList<DirOperario> listaDirOperarios;

    private DirOperarioBD dirOperarioBD;

    public DirOperarioDAOImp() {
        listaDirOperarios = FXCollections.observableArrayList();
        dirOperarioBD = new DirOperarioBD();
    }

    public void setConnection(Connection conn) {
        dirOperarioBD.setConnection(conn);
    }

    public ObservableList<DirOperario> getDirOperarios() {
        return listaDirOperarios;
    }

    public int getNumDirOperarios() {
        return listaDirOperarios.size();
    }

    public DirOperarioPK insert(DirOperario dto) throws DAOException {
        DirOperarioPK pk = dto.createPK();

        try {
            if (listaDirOperarios.contains(dto)) {
                throw new OperationsException("La dirección del operario ya existe.");
            } else {
                pk = dirOperarioBD.insert(dto);

                this.listaDirOperarios.add(dto);
            }
        } catch (Exception e) {
            throwException(e);
        }
        return pk;
    }

    public ObservableList<DirOperarioPK> insertAll(ObservableList<DirOperario> listToInsert) throws DAOException {
        ObservableList<DirOperarioPK> listToReturn = FXCollections.observableArrayList();
        for (DirOperario dto : listToInsert) {
            DirOperarioPK dtoPK = insert(dto);
            if (dtoPK != null) {
                listToReturn.add(dtoPK);
            }
        }
        return listToReturn;
    }

    public void update(DirOperarioPK pk, DirOperario dto) throws DAOException {
        DirOperario oldDirOperario = getOldDirOperario(pk);

        try {
            if (dto == null) {
                throw new OperationsException("La dirección de operario a modificar no puede ser nula.");
            }
            if (!listaDirOperarios.contains(oldDirOperario)) {
                throw new OperationsException("La dirección de operario a modificar no existe.");

            } else {
                dirOperarioBD.update(pk, dto);

                listaDirOperarios.set(listaDirOperarios.indexOf(oldDirOperario), dto);
            }
        } catch (Exception e) {
            throwException(e);
        }
    }

    public void updateAll(ObservableMap<DirOperarioPK, DirOperario> mapToUpdate) throws DAOException {
        for (Map.Entry<DirOperarioPK, DirOperario> entry : mapToUpdate.entrySet()) {
            update(entry.getKey(), entry.getValue());
        }
    }

    public void delete(DirOperarioPK pk) throws DAOException {
        DirOperario oldDirOperario = getOldDirOperario(pk);

        try {
            if (pk == null) {
                throw new NullPointerException("No se puede eliminar una dirección de operario nula.");
            }
            if (!listaDirOperarios.contains(oldDirOperario)) {
                throw new OperationsException("La dirección de operario a eliminar no existe.");

            } else {
                dirOperarioBD.delete(pk);
                listaDirOperarios.remove(oldDirOperario);
            }
        } catch (Exception e) {
            throwException(e);
        }
    }

    public void deleteAll(ObservableList<DirOperario> listToDelete) throws DAOException {
        for (DirOperario dto : listToDelete) {
            delete(dto.createPK());
        }
    }

    public void findAll() throws DAOException {
        try {
            listaDirOperarios.setAll(dirOperarioBD.findAll());

        } catch (SQLException e) {
            throwException(e);
        }
    }

    public DirOperario findByPrimaryKey(DirOperarioPK pk) throws DAOException {
        try {
            return dirOperarioBD.findByPrimaryKey(pk);

        } catch (SQLException e) {
            throwException(e);
        }
        return null;
    }

    public void setMaxRows(int maxRows) {
        dirOperarioBD.setMaxRows(maxRows);
    }

    public int getMaxRows() {
        return dirOperarioBD.getMaxRows();
    }

    public DirOperario getDirOperario(Operario dto) {
        for (DirOperario dtoOnList : listaDirOperarios) {
            if (dtoOnList.getCodOp() == dto.getCodOp()) {
                return dtoOnList;
            }
        }
        return null;
    }

    public void createDependencies(OperarioDAO operarioDAO) {
        for (DirOperario dirOperario : listaDirOperarios) {
            dirOperario.setObjOperario(dependencyDirOperario(dirOperario, operarioDAO));
        }
    }

    private Operario dependencyDirOperario(DirOperario dto, OperarioDAO operarioDAO) {
        for (Operario operario : operarioDAO.getOperarios()) {
            if (operario.getCodOp() == dto.getCodOp()) {
                return operario;
            }
        }
        return null;
    }

    private void throwException(Exception e) throws DAOException {
        throw new DAOException(ERROR + e.getMessage(), e);
    }

    private DirOperario getOldDirOperario(DirOperarioPK pk) {
        DirOperario oldDirOperario = new DirOperario();
        oldDirOperario.setCodOp(pk.getCodOp());
        oldDirOperario = listaDirOperarios.get(listaDirOperarios.indexOf(oldDirOperario));
        return oldDirOperario;
    }
}
