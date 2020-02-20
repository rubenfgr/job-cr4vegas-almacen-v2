package es.rfgr.cr4vegas.modelo.postgresql.dao.almacen.tienda.imp;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

import javax.management.OperationsException;

import es.rfgr.cr4vegas.modelo.postgresql.bd.almacen.tienda.DirTiendaBD;
import es.rfgr.cr4vegas.modelo.postgresql.dao.almacen.tienda.DirTiendaDAO;
import es.rfgr.cr4vegas.modelo.postgresql.dao.almacen.tienda.TiendaDAO;
import es.rfgr.cr4vegas.modelo.postgresql.dto.almacen.tienda.DirTienda;
import es.rfgr.cr4vegas.modelo.postgresql.dto.almacen.tienda.DirTiendaPK;
import es.rfgr.cr4vegas.modelo.postgresql.dto.almacen.tienda.Tienda;
import es.rfgr.cr4vegas.modelo.postgresql.exceptions.DAOException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;

public class DirTiendaDAOImp implements DirTiendaDAO {

    private static final String ERROR = "DirTiendaDAO: ";

    private ObservableList<DirTienda> listaDirTiendas;

    private DirTiendaBD dirTiendaBD;

    public DirTiendaDAOImp() {
        listaDirTiendas = FXCollections.observableArrayList();
        dirTiendaBD = new DirTiendaBD();
    }

    public void setConnection(Connection conn) {
        dirTiendaBD.setConnection(conn);
    }

    public ObservableList<DirTienda> getDirTiendas() {
        return listaDirTiendas;
    }

    public int getNumDirTiendas() {
        return listaDirTiendas.size();
    }

    public DirTiendaPK insert(DirTienda dto) throws DAOException {
        DirTiendaPK pk = dto.createPK();

        try {
            if (listaDirTiendas.contains(dto)) {
                throw new OperationsException("La dirección de tienda ya existe.");

            } else {
                pk = dirTiendaBD.insert(dto);

                listaDirTiendas.add(dto);
            }
        } catch (Exception e) {
            throwException(e);
        }
        return pk;
    }

    public ObservableList<DirTiendaPK> insertAll(ObservableList<DirTienda> listToInsert) throws DAOException {
        ObservableList<DirTiendaPK> listToReturn = FXCollections.observableArrayList();
        for (DirTienda dto : listToInsert) {
            DirTiendaPK dtoPK = insert(dto);
            if (dtoPK != null) {
                listToReturn.add(dtoPK);
            }
        }
        return listToReturn;
    }

    public void update(DirTiendaPK pk, DirTienda dto) throws DAOException {
        DirTienda oldDirTienda = getOldDirTienda(pk);

        try {
            if (dto == null) {
                throw new OperationsException("La dirección de tienda a modificar no puede ser nula.");
            }
            if (!listaDirTiendas.contains(dto)) {
                throw new OperationsException("La dirección de tienda a modificar no existe.");

            } else {
                dirTiendaBD.update(pk, dto);

                listaDirTiendas.set(listaDirTiendas.indexOf(oldDirTienda), dto);
            }
        } catch (Exception e) {
            throwException(e);
        }
    }

    public void updateAll(ObservableMap<DirTiendaPK, DirTienda> mapToUpdate) throws DAOException {
        for (Map.Entry<DirTiendaPK, DirTienda> entry : mapToUpdate.entrySet()) {
            update(entry.getKey(), entry.getValue());
        }
    }

    public void delete(DirTiendaPK pk) throws DAOException {
        DirTienda oldDirTienda = getOldDirTienda(pk);

        try {
            if (pk == null) {
                throw new NullPointerException("No se puede eliminar una dirección de tienda nula.");
            }
            if (!listaDirTiendas.contains(oldDirTienda)) {
                throw new OperationsException("La dirección de tienda a eliminar no existe.");

            } else {
                dirTiendaBD.delete(pk);

                listaDirTiendas.remove(oldDirTienda);
            }
        } catch (Exception e) {
            throwException(e);
        }
    }

    public void deleteAll(ObservableList<DirTienda> listToDelete) throws DAOException {
        for (DirTienda dto : listToDelete) {
            delete(dto.createPK());
        }
    }

    public void findAll() throws DAOException {
        try {
            listaDirTiendas.setAll(dirTiendaBD.findAll());

        } catch (Exception e) {
            throwException(e);
        }
    }

    public DirTienda findByPrimaryKey(DirTiendaPK pk) throws DAOException {
        try {
            return dirTiendaBD.findByPrimaryKey(pk);

        } catch (SQLException e) {
            throwException(e);
        }
        return null;
    }

    public void setMaxRows(int maxRows) {
        dirTiendaBD.setMaxRows(maxRows);
    }

    public int getMaxRows() {
        return dirTiendaBD.getMaxRows();
    }

    public DirTienda getDirTienda(Tienda dto) {
        DirTienda dirTiendaToReturn = null;
        for (DirTienda dirTienda : listaDirTiendas) {
            if (dirTienda.getCodTienda() == dto.getCodTienda()) {
                dirTiendaToReturn = dirTienda;
            }
        }
        return dirTiendaToReturn;
    }

    public void createDependencies(TiendaDAO tiendaDAO) {
        for (DirTienda dirTienda : listaDirTiendas) {
            dirTienda.setObjTienda(dependencyDirTiendaTienda(dirTienda, tiendaDAO));
        }
    }

    private Tienda dependencyDirTiendaTienda(DirTienda dto, TiendaDAO tiendaDAO) {
        for (Tienda tienda : tiendaDAO.getTiendas()) {
            if (tienda.getCodTienda() == dto.getCodTienda()) {
                return tienda;
            }
        }
        return null;
    }

    private void throwException(Exception e) throws DAOException {
        throw new DAOException(ERROR + e.getMessage(), e);
    }

    private DirTienda getOldDirTienda(DirTiendaPK pk) {
        DirTienda oldDirTienda = new DirTienda();
        oldDirTienda.setCodTienda(pk.getCodTienda());
        for (DirTienda dirTienda : listaDirTiendas) {
            System.out.println(dirTienda);
        }
        
        System.out.println("Tienda buscada: " + oldDirTienda);
        oldDirTienda = listaDirTiendas.get(listaDirTiendas.indexOf(oldDirTienda));
        return oldDirTienda;
    }
}
