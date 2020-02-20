package es.rfgr.cr4vegas.modelo.postgresql.dao.parteoficial.imp;

import java.sql.SQLException;
import java.util.Map;

import javax.management.OperationsException;

import es.rfgr.cr4vegas.modelo.postgresql.bd.parteoficial.ParteOficialBD;
import es.rfgr.cr4vegas.modelo.postgresql.dao.parte.ParteDAO;
import es.rfgr.cr4vegas.modelo.postgresql.dao.parte.TipoParteDAO;
import es.rfgr.cr4vegas.modelo.postgresql.dao.parteoficial.ParteOficialDAO;
import es.rfgr.cr4vegas.modelo.postgresql.dto.parte.Parte;
import es.rfgr.cr4vegas.modelo.postgresql.dto.parte.TipoParte;
import es.rfgr.cr4vegas.modelo.postgresql.dto.parteoficial.ParteOficial;
import es.rfgr.cr4vegas.modelo.postgresql.dto.parteoficial.ParteOficialPK;
import es.rfgr.cr4vegas.modelo.postgresql.exceptions.DAOException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;

public class ParteOficialDAOImp implements ParteOficialDAO {

	private static final String ERROR = "ParteOficialDAO: ";

	private ObservableList<ParteOficial> listaPartesOficiales;

	private ParteOficialBD parteOficialBD;

	private ParteDAO parteDAO;

	public ParteOficialDAOImp(ParteDAO parteDAO) {
		listaPartesOficiales = FXCollections.observableArrayList();
		parteOficialBD = new ParteOficialBD();
		this.parteDAO = parteDAO;
	}

	public void comprobarNuevosPartesEInsertar() throws DAOException {
		try {
			ParteOficial nuevoParteOficial = parteOficialBD.findByPrimaryKey(parteOficialBD.getMaxCodParte() + 1);
			if (nuevoParteOficial != null) {
				Parte nuevoParte = new Parte();
				nuevoParte.copiarValoresDeParteOficial(nuevoParteOficial);
				parteDAO.insert(nuevoParte);
			}
		} catch (Exception e) {
			throwException(e);
		}
	}

	public ObservableList<ParteOficial> getPartesOficiales() {
		return listaPartesOficiales;
	}

	public int getNumPartesOficiales() {
		return listaPartesOficiales.size();
	}

	public ParteOficialPK insert(ParteOficial dto) throws DAOException {
		ParteOficialPK pk = dto.createPK();

		try {
			if (listaPartesOficiales.contains(dto)) {
				throw new OperationsException("El ParteOficial ya existe.");
			} else {
				pk = parteOficialBD.insert(dto);

				this.listaPartesOficiales.add(dto);
			}
		} catch (Exception e) {
			throwException(e);
		}
		return pk;
	}

	public ObservableList<ParteOficialPK> insertAll(ObservableList<ParteOficial> listToInsert) throws DAOException {
		ObservableList<ParteOficialPK> listToReturn = FXCollections.observableArrayList();
		for (ParteOficial dto : listToInsert) {
			ParteOficialPK dtoPK = insert(dto);
			if (dtoPK != null) {
				listToReturn.add(dtoPK);
			}
		}
		return listToReturn;
	}

	public void update(ParteOficialPK pk, ParteOficial dto) throws DAOException {
		ParteOficial oldParteOficial = getOldParteOficial(pk);

		try {
			if (dto == null) {
				throw new OperationsException("El ParteOficial a modificar no puede ser nulo.");
			}
			if (!listaPartesOficiales.contains(oldParteOficial)) {
				throw new OperationsException("El ParteOficial a modificar no existe.");

			} else {
				parteOficialBD.update(pk, dto);

				oldParteOficial.copiarValores(dto);
			}
		} catch (Exception e) {
			throwException(e);
		}
	}

	public void updateAll(ObservableMap<ParteOficialPK, ParteOficial> mapToUpdate) throws DAOException {
		for (Map.Entry<ParteOficialPK, ParteOficial> entry : mapToUpdate.entrySet()) {
			update(entry.getKey(), entry.getValue());
		}
	}

	public void delete(ParteOficialPK pk) throws DAOException {
		ParteOficial oldParteOficial = getOldParteOficial(pk);

		try {
			if (pk == null) {
				throw new NullPointerException("No se puede eliminar un ParteOficial nulo.");
			}
			if (!listaPartesOficiales.contains(oldParteOficial)) {
				throw new OperationsException("El ParteOficial a eliminar no existe.");

			} else {
				parteOficialBD.delete(pk);

				listaPartesOficiales.remove(oldParteOficial);
			}
		} catch (Exception e) {
			throwException(e);
		}
	}

	public void deleteAll(ObservableList<ParteOficial> listToDelete) throws DAOException {
		for (ParteOficial dto : listToDelete) {
			delete(dto.createPK());
		}
	}

	public void findAll() throws DAOException {
		try {
			listaPartesOficiales.setAll(parteOficialBD.findAll());

		} catch (SQLException e) {
			throwException(e);
		}
	}

	private void comprobarExisteYActuar() throws DAOException {
		boolean existe = false;

		try {
			for (ParteOficial parteOficial : listaPartesOficiales) {
				existe = false;
				for (Parte parte : parteDAO.getPartes()) {
					if (parteOficial.getCodParte() == parte.getParteOficial()) {
						existe = true;
						if (!parte.esIgualA(parteOficial)) {
							parte.copiarValoresDeParteOficial(parteOficial);
							parteDAO.update(parte.createPK(), parte);
						}
						break;
					}
				}
				if (!existe) {
					Parte nuevoParte = new Parte();
					nuevoParte.copiarValoresDeParteOficial(parteOficial);
					parteDAO.insert(nuevoParte);
				}
			}
		} catch (Exception e) {
			throwException(e);
		}
	}

	public ParteOficial findByPrimaryKey(ParteOficialPK pk) throws DAOException {
		try {
			return parteOficialBD.findByPrimaryKey(pk);

		} catch (SQLException e) {
			throwException(e);
		}
		return null;
	}

	public void setMaxRows(int maxRows) {
		parteOficialBD.setMaxRows(maxRows);
	}

	public int getMaxRows() {
		return parteOficialBD.getMaxRows();
	}

	public void createDependencies(TipoParteDAO tipoParteDAO) {
		for (ParteOficial ParteOficial : listaPartesOficiales) {
			ParteOficial.setObjTipoParte(depParteOficialTipo(ParteOficial, tipoParteDAO));
		}
		
		try {
			comprobarExisteYActuar();
		} catch (DAOException e) {
			e.printStackTrace();
		}
	}

	private TipoParte depParteOficialTipo(ParteOficial dto, TipoParteDAO tipoParteOficialDAO) {
		TipoParte tipoParteOficialSalida = tipoParteOficialDAO.getTiposParte().get(0);
		for (TipoParte tipoParteOficial : tipoParteOficialDAO.getTiposParte()) {
			if (tipoParteOficial.getNombre().equals(dto.getTipo())) {
				tipoParteOficialSalida = tipoParteOficial;
			}
		}
		return tipoParteOficialSalida;
	}

	private void throwException(Exception e) throws DAOException {
		e.printStackTrace();
		throw new DAOException(ERROR + e.getMessage(), e);
	}

	private ParteOficial getOldParteOficial(ParteOficialPK pk) {
		ParteOficial oldParteOficial = new ParteOficial();
		oldParteOficial.setCodParte(pk.getCodParte());
		for (ParteOficial po : listaPartesOficiales) {
			System.out.println(po.getCodParte());
		}
		oldParteOficial = listaPartesOficiales.get(listaPartesOficiales.indexOf(oldParteOficial));
		return oldParteOficial;
	}

}
