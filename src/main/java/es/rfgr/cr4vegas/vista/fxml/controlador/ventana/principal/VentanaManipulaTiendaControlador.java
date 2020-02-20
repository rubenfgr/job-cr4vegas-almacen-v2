package es.rfgr.cr4vegas.vista.fxml.controlador.ventana.principal;

import java.sql.Connection;

import es.rfgr.cr4vegas.controlador.Consumer;
import es.rfgr.cr4vegas.modelo.postgresql.conexion.ConexionAlmacen;
import es.rfgr.cr4vegas.modelo.postgresql.dto.almacen.tienda.DirTienda;
import es.rfgr.cr4vegas.modelo.postgresql.dto.almacen.tienda.TelTienda;
import es.rfgr.cr4vegas.modelo.postgresql.dto.almacen.tienda.Tienda;
import es.rfgr.cr4vegas.modelo.postgresql.dto.almacen.tienda.TiendaPK;
import es.rfgr.cr4vegas.modelo.postgresql.exceptions.DAOException;
import es.rfgr.cr4vegas.vista.utileria.Dialogos;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class VentanaManipulaTiendaControlador {

	@FXML
	private AnchorPane panelRaiz;

	@FXML
	private VBox panelActivar;

	@FXML
	private CheckBox cbActivo;

	@FXML
	private TextField tfNombre;

	@FXML
	private TextField tfNIF;

	@FXML
	private TextField tfLocalidad;

	@FXML
	private TextField tfCP;

	@FXML
	private TextField tfCalle;

	@FXML
	private TextField tfNumero;

	@FXML
	private ListView<TelTienda> lvTelefonos;

	@FXML
	private TextField tfTelefono;

	@FXML
	private Button btAgregarTelefono;

	@FXML
	private Button btBorrarTelefono;

	@FXML
	private Button btAceptar;

	@FXML
	private Button btCancelar;

	/**
	 * VARIABLES
	 */
	private Consumer app;

	private Tienda tienda;
	private DirTienda dirTienda;
	private ObservableList<TelTienda> listaTelTienda;
	private ObservableList<TelTienda> listaTelTiendaCopia;

	private boolean agregar;

	private Stage escenarioVentana;

	/**
	 * METODOS
	 */
	public void setConsumer(Consumer app) {
		this.app = app;
	}

	public AnchorPane getPanelRaiz() {
		return panelRaiz;
	}

	public void iniciar() {
		listaTelTienda = FXCollections.observableArrayList();
		listaTelTiendaCopia = FXCollections.observableArrayList();

		btAceptar.setOnAction(e -> aceptar());
		btCancelar.setOnAction(e -> escenarioVentana.close());

		btAgregarTelefono.setOnAction(e -> agregarTelefono());
		btBorrarTelefono.setOnAction(e -> borrarTelefono());
	}

	public void iniciarCon(Tienda tienda, DirTienda dirTienda, ObservableList<TelTienda> listaTelTienda) {
		if (tienda == null) {
			iniciarAgregarTienda();

		} else {
			iniciarModificarTienda(tienda, dirTienda, listaTelTienda);
		}
		escenarioVentana = app.getVistaModelo().getEscenario(panelRaiz, "TIENDAS");
		escenarioVentana.showAndWait();
	}

	private void iniciarAgregarTienda() {
		agregar = true;

		tienda = new Tienda();
		dirTienda = new DirTienda();
		dirTienda.setObjTienda(tienda);

		listaTelTienda.clear();

		llenarCampos();
	}

	private void iniciarModificarTienda(Tienda tienda, DirTienda dirTienda, ObservableList<TelTienda> listaTelTienda) {
		agregar = false;

		this.tienda = tienda;
		if (dirTienda != null) {
			this.dirTienda = dirTienda;
		} else {
			this.dirTienda = new DirTienda();
		}

		for (TelTienda telTienda : app.getTelTiendaDAO().getTelTienda(tienda)) {
			this.listaTelTienda.add(telTienda);
			this.listaTelTiendaCopia.add(new TelTienda(telTienda));
		}

		llenarCampos();
	}

	private void llenarCampos() {
		cbActivo.setSelected(tienda.isActivo());
		tfNombre.setText(tienda.getNombre());
		tfNIF.setText(tienda.getNif());

		if (dirTienda != null) {
			tfCalle.setText(dirTienda.getCalle());
			tfCP.setText(dirTienda.getCp());
			tfLocalidad.setText(dirTienda.getLocalidad());
			tfNumero.setText(String.valueOf(dirTienda.getNumero()));
			tfTelefono.setText("");
		}

		lvTelefonos.setItems(listaTelTienda);
	}

	private void aceptar() {
		Connection userConn = null;
		try {
			modificarTiendaYDirTiendaConNuevosValores();
			userConn = ConexionAlmacen.getConexion();
			app.getTiendaDAO().setConnection(userConn);
			app.getDirTiendaDAO().setConnection(userConn);
			app.getTelTiendaDAO().setConnection(userConn);
			userConn.setAutoCommit(false);

			if (agregar) {
				TiendaPK tiendaPK = app.getTiendaDAO().insert(tienda);
				tienda.setCodTienda(tiendaPK.getCodTienda());

				dirTienda.setObjTienda(tienda);
				app.getDirTiendaDAO().insert(dirTienda);

				modificarObjTiendaEnTelefonos();
				app.getTelTiendaDAO().insertAll(listaTelTienda);

			} else {
				app.getTiendaDAO().update(tienda.createPK(), tienda);

				if (dirTienda.getObjTienda() == null) {
					dirTienda.setObjTienda(tienda);
					app.getDirTiendaDAO().insert(dirTienda);
				} else {
					app.getDirTiendaDAO().update(dirTienda.createPK(), dirTienda);
				}

				modificarObjTiendaEnTelefonos();
				for (TelTienda telTienda : listaTelTiendaCopia) {
					if (!listaTelTienda.contains(telTienda)) {
						app.getTelTiendaDAO().delete(telTienda.createPK());
					}
				}
				for (TelTienda telTienda : listaTelTienda) {
					if (!listaTelTiendaCopia.contains(telTienda)) {
						app.getTelTiendaDAO().insert(telTienda);
					} else {
						app.getTelTiendaDAO().update(telTienda.createPK(), telTienda);
					}
				}
			}
			userConn.commit();
			userConn.close();

			app.getVistaModelo().getModeloVentana().getVentanaTiendaControlador().actualizarCampos(tienda);
			escenarioVentana.close();

		} catch (Exception e) {
			try {
				e.printStackTrace();
				userConn.rollback();
				userConn.close();
				app.findAll();
				app.createAllDependencies();
				Dialogos.mostrarDialogoExcepcion(e, escenarioVentana);

			} catch (Exception e1) {
				e1.printStackTrace();
				Dialogos.mostrarDialogoExcepcion(e1, escenarioVentana);
			}

		}
	}

	private void modificarTiendaYDirTiendaConNuevosValores() throws Exception {
		tienda.setActivo(cbActivo.isSelected());
		tienda.setNif(tfNIF.getText());
		tienda.setNombre(tfNombre.getText());

		dirTienda.setCalle(tfCalle.getText());
		dirTienda.setCp(tfCP.getText());
		dirTienda.setLocalidad(tfLocalidad.getText());
		dirTienda.setNumero(tfNumero.getText());

	}

	private void modificarObjTiendaEnTelefonos() {
		listaTelTienda.forEach((telTienda) -> {
			telTienda.setObjTienda(tienda);
		});
	}

	private void borrarTelefono() {
		try {
			TelTienda telTienda = lvTelefonos.getSelectionModel().getSelectedItem();
			if (telTienda == null) {
				Dialogos.mostrarDialogoAdvertencia("Debe seleccionar el teléfono ha eliminar", escenarioVentana);
			} else {
				app.getTelTiendaDAO().delete(telTienda.createPK());
				listaTelTienda.remove(telTienda);
			}
		} catch (DAOException e) {
			Dialogos.mostrarDialogoExcepcion(e, escenarioVentana);
		}
	}

	private void agregarTelefono() {
		try {
			TelTienda telTienda = new TelTienda();
			telTienda.setTelefono(tfTelefono.getText());
			listaTelTienda.add(telTienda);
			tfTelefono.setText("");

		} catch (Exception e) {
			Dialogos.mostrarDialogoExcepcion(e, escenarioVentana);
		}
	}

}
