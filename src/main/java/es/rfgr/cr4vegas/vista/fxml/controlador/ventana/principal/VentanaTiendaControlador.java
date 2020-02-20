package es.rfgr.cr4vegas.vista.fxml.controlador.ventana.principal;

import es.rfgr.cr4vegas.controlador.Consumer;
import es.rfgr.cr4vegas.modelo.postgresql.dto.almacen.tienda.DirTienda;
import es.rfgr.cr4vegas.modelo.postgresql.dto.almacen.tienda.TelTienda;
import es.rfgr.cr4vegas.modelo.postgresql.dto.almacen.tienda.Tienda;
import es.rfgr.cr4vegas.vista.utileria.Dialogos;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class VentanaTiendaControlador {

	/**
	 * FXML
	 */
	@FXML
	private AnchorPane panelRaiz;

	@FXML
	private TableView<Tienda> tvTiendas;

	@FXML
	private TableColumn<Tienda, Integer> tcCodTienda;

	@FXML
	private TableColumn<Tienda, Boolean> tcActivo;

	@FXML
	private TableColumn<Tienda, String> tcNombre;

	@FXML
	private TableColumn<Tienda, String> tcNif;

	@FXML
	private Button btAgregarTienda;

	@FXML
	private Button btModificarTienda;

	@FXML
	private Button btEliminarTienda;

	@FXML
	private VBox panelActivar;

	@FXML
	private Label lbID;

	@FXML
	private Label lbActivo;

	@FXML
	private Label lbNombre;

	@FXML
	private Label lbNIF;

	@FXML
	private Label lbLocalidad;

	@FXML
	private Label lbCP;

	@FXML
	private Label lbCalle;

	@FXML
	private Label lbNumero;

	@FXML
	private ListView<TelTienda> lvTelefonos;

	@FXML
	private Button btSalir;

	/**
	 * VARIABLES
	 */
	private Consumer app;

	private Stage escenarioVentana;

	private DirTienda dirTienda;

	private ObservableList<TelTienda> listaTelTienda;

	/*
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

		panelRaiz.setOnKeyPressed(e -> escape(e));

		tvTiendas.getSelectionModel().selectedItemProperty()
				.addListener((ov, oldValue, newValue) -> actualizarCampos(newValue));

		tcCodTienda.setCellValueFactory(tienda -> tienda.getValue().codTiendaProperty().asObject());
		tcActivo.setCellValueFactory(tienda -> tienda.getValue().activoProperty());
		tcNombre.setCellValueFactory(tienda -> tienda.getValue().nombreProperty());
		tcNif.setCellValueFactory(tienda -> tienda.getValue().nifProperty());

		btAgregarTienda.setOnAction(e -> agregarTienda());
		btModificarTienda.setOnAction(e -> modificarTienda(getTiendaSeleccionada()));
		btEliminarTienda.setOnAction(e -> eliminarTienda(getTiendaSeleccionada()));

		btSalir.setOnAction(e -> escenarioVentana.close());

		tvTiendas.setItems(app.getTiendaDAO().getTiendas());

		lvTelefonos.setItems(listaTelTienda);
	}

	public void iniciarCon() {
		vaciarCampos();
		escenarioVentana = app.getVistaModelo().getEscenario(panelRaiz, "TIENDAS");
		escenarioVentana.showAndWait();
	}

	private Tienda getTiendaSeleccionada() {
		return tvTiendas.getSelectionModel().getSelectedItem();
	}

	public void actualizarCampos(Tienda tienda) {
		if (tienda == null) {
			vaciarCampos();
		} else {
			llenarCampos(tienda);
		}
	}

	private void vaciarCampos() {
		lbActivo.setText("");
		lbCalle.setText("");
		lbCP.setText("");
		lbID.setText("");
		lbLocalidad.setText("");
		lbNIF.setText("");
		lbNombre.setText("");
		lbNumero.setText("");

		dirTienda = null;

		listaTelTienda.clear();
	}

	private void llenarCampos(Tienda tienda) {
		lbID.setText(String.valueOf(tienda.getCodTienda()));
		lbActivo.setText(tienda.isActivo() ? "Si" : "No");
		lbNIF.setText(tienda.getNif());
		lbNombre.setText(tienda.getNombre());

		dirTienda = app.getDirTiendaDAO().getDirTienda(tienda);
		if (dirTienda != null) {
			lbCalle.setText(dirTienda.getCalle());
			lbCP.setText(dirTienda.getCp());
			lbLocalidad.setText(dirTienda.getLocalidad());
			lbNumero.setText(String.valueOf(dirTienda.getNumero()));
		}

		listaTelTienda.setAll(app.getTelTiendaDAO().getTelTienda(tienda));
	}

	private void agregarTienda() {
		app.getVistaModelo().getModeloVentana().getVentanaManipulaTiendaControlador().iniciarCon(null, null, null);
	}

	private void modificarTienda(Tienda tienda) {
		if (tienda == null) {
			Dialogos.mostrarDialogoAdvertencia("Debe seleccionar la tienda ha modificar", escenarioVentana);
		} else {
			app.getVistaModelo().getModeloVentana().getVentanaManipulaTiendaControlador().iniciarCon(tienda, dirTienda, listaTelTienda);
		}
	}

	private void eliminarTienda(Tienda tienda) {
		try {
			if (tienda == null) {
				Dialogos.mostrarDialogoAdvertencia("Debe seleccionar la tienda ha eliminar", escenarioVentana);
			} else {
				app.getTiendaDAO().delete(tienda.createPK());
			}
		} catch (Exception e) {
			mostrarDialogoExcepcion(e);
		}
	}

	private void escape(KeyEvent e) {
		if (e.getCode() == KeyCode.ESCAPE) {
			tvTiendas.getSelectionModel().clearSelection();
		}
	}

	private void mostrarDialogoExcepcion(Exception e) {
		Dialogos.mostrarDialogoError(e.getMessage(), escenarioVentana);
		e.printStackTrace();
	}
}
