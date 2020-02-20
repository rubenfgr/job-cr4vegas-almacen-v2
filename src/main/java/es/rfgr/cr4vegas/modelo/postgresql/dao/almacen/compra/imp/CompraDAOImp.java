package es.rfgr.cr4vegas.modelo.postgresql.dao.almacen.compra.imp;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

import javax.management.OperationsException;

import es.rfgr.cr4vegas.modelo.postgresql.bd.almacen.compra.CompraBD;
import es.rfgr.cr4vegas.modelo.postgresql.dao.almacen.compra.CompraDAO;
import es.rfgr.cr4vegas.modelo.postgresql.dao.almacen.tienda.TiendaDAO;
import es.rfgr.cr4vegas.modelo.postgresql.dao.operario.OperarioDAO;
import es.rfgr.cr4vegas.modelo.postgresql.dto.almacen.compra.Compra;
import es.rfgr.cr4vegas.modelo.postgresql.dto.almacen.compra.CompraPK;
import es.rfgr.cr4vegas.modelo.postgresql.dto.almacen.tienda.Tienda;
import es.rfgr.cr4vegas.modelo.postgresql.dto.operario.Operario;
import es.rfgr.cr4vegas.modelo.postgresql.exceptions.DAOException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;

public class CompraDAOImp implements CompraDAO {

    private static final String ERROR = "CompraDAO: ";

    private ObservableList<Compra> listaCompras;

    private CompraBD compraBD;

    public CompraDAOImp() {
        listaCompras = FXCollections.observableArrayList();
        compraBD = new CompraBD();
    }

    public void setConnection(Connection conn) {
        compraBD.setConnection(conn);
    }

    public ObservableList<Compra> getCompras() {
        return listaCompras;
    }

    public int getNumCompras() {
        return listaCompras.size();
    }

    public CompraPK insert(Compra dto) throws DAOException {
        CompraPK pk = null;

        try {
            if (dto == null) {
                throw new NullPointerException("La compra a insertar no puede ser nula.");
            }
            if (listaCompras.contains(dto)) {
                throw new OperationsException("La compra a insertar ya existe.");

            } else {

                pk = compraBD.insert(dto);

                this.listaCompras.add(dto);
            }
        } catch (Exception e) {
            throwException(e);
        }
        return pk;
    }

    public ObservableList<CompraPK> insertAll(ObservableList<Compra> listToInsert) throws DAOException {
        ObservableList<CompraPK> listToReturn = FXCollections.observableArrayList();
        for (Compra dto : listToInsert) {
            CompraPK dtoPK = insert(dto);
            if (dtoPK != null) {
                listToReturn.add(dtoPK);
            }
        }
        return listToReturn;
    }

    public void update(CompraPK pk, Compra dto) throws DAOException {
        Compra oldCompra = getOldCompra(pk);

        try {
            if (dto == null) {
                throw new NullPointerException("La compra a modificar no puede ser nula.");
            }

            if (!listaCompras.contains(oldCompra)) {
                throw new OperationsException("La compra a modificar no existe.");

            } else {
                compraBD.update(pk, dto);

                listaCompras.set(listaCompras.indexOf(oldCompra), dto);
            }
        } catch (Exception e) {
            throwException(e);
        }
    }

    public void updateAll(ObservableMap<CompraPK, Compra> mapToUpdate) throws DAOException {
        for (Map.Entry<CompraPK, Compra> entry : mapToUpdate.entrySet()) {
            update(entry.getKey(), entry.getValue());
        }
    }

    public void delete(CompraPK pk) throws DAOException {
        Compra oldCompra = getOldCompra(pk);

        try {
            if (pk == null) {
                throw new NullPointerException("La compra a eliminar no puede ser nula.");
            }
            if (!listaCompras.contains(oldCompra)) {
                throw new OperationsException("La compra a eliminar no existe.");

            } else {
                compraBD.delete(pk);

                listaCompras.remove(oldCompra);
            }
        } catch (Exception e) {
            throwException(e);
        }
    }

    public void deleteAll(ObservableList<Compra> listToDelete) throws DAOException {
        for (Compra dto : listToDelete) {
            delete(dto.createPK());
        }
    }

    public void findAll() throws DAOException {
        try {
            listaCompras.setAll(compraBD.findAll());

        } catch (SQLException e) {
            throwException(e);
        }
    }

	public void updateDTOCompra(Compra compra) throws DAOException {
		try {
			compraBD.updateDTOCompra(compra);
		} catch (SQLException e) {
			throwException(e);
		}
	}
	
    public Compra findByPrimaryKey(CompraPK pk) throws DAOException {
        try {
            return compraBD.findByPrimaryKey(pk);

        } catch (SQLException e) {
            throwException(e);
        }
        return null;
    }

    public void setMaxRows(int maxRows) {
        compraBD.setMaxRows(maxRows);
    }

    public int getMaxRows() {
        return compraBD.getMaxRows();
    }

    public void createDependencies(TiendaDAO tiendaDAO, OperarioDAO operarioDAO) {
        for (Compra compra : listaCompras) {
            compra.setObjOperario(dependencyCompraOperario(compra, operarioDAO));
            compra.setObjTienda(dependencyCompraTienda(compra, tiendaDAO));
        }
    }

    private Tienda dependencyCompraTienda(Compra dto, TiendaDAO tiendaDAO) {
        Tienda tiendaSalida = null;
        for (Tienda tienda : tiendaDAO.getTiendas()) {
            if (tienda.getCodTienda() == dto.getCodTienda()) {
                tiendaSalida = tienda;
            }
        }
        return tiendaSalida;
    }

    private Operario dependencyCompraOperario(Compra dto, OperarioDAO operarioDAO) {
        Operario operarioSalida = null;
        for (Operario operario : operarioDAO.getOperarios()) {
            if (operario.getCodOp() == dto.getCodOp()) {
                operarioSalida = operario;
            }
        }
        return operarioSalida;
    }

    private void throwException(Exception e) throws DAOException {
        throw new DAOException(ERROR + e.getMessage(), e);
    }

    private Compra getOldCompra(CompraPK pk) {
        Compra oldCompra = new Compra();
        oldCompra.setCodCompra(pk.getCodCompra());
        oldCompra = listaCompras.get(listaCompras.indexOf(oldCompra));
        return oldCompra;
    }
}
