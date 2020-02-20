package es.rfgr.cr4vegas.vista.fxml.controlador.principal.modulo.parte;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

import es.rfgr.cr4vegas.controlador.Consumer;
import es.rfgr.cr4vegas.modelo.postgresql.dto.parte.AsociaParteOperario;
import es.rfgr.cr4vegas.modelo.postgresql.dto.parte.OperaParteMaterial;
import es.rfgr.cr4vegas.modelo.postgresql.dto.parte.Parte;
import es.rfgr.cr4vegas.vista.utileria.Filtrador;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.SortType;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class PartePrincipalControlador implements Initializable {

	@FXML
	private TableColumn<Parte, String> tcParteLlamo;

	@FXML
	private TableColumn<AsociaParteOperario, Integer> tcAsociaParteOperarioCodigo;

	@FXML
	private TableView<AsociaParteOperario> tvAsociaParteOperario;

	@FXML
	private TableColumn<Parte, Integer> tcParteCodigo;

	@FXML
	private TableColumn<AsociaParteOperario, String> tcApellidos;

	@FXML
	private Label lbTotalPartesOficiales;

	@FXML
	private TableColumn<Parte, Boolean> tcParteImpreso;

	@FXML
	private TableColumn<Parte, Boolean> tcParteEstado;

	@FXML
	private TableColumn<OperaParteMaterial, Integer> tcOperaParteMaterialCantidad;

	@FXML
	private TextField tfBuscarParte;

	@FXML
	private TableColumn<Parte, String> tcParteOrigen;

	@FXML
	private TableColumn<Parte, String> tcHidrante;

	@FXML
	private TableColumn<Parte, Integer> tcParteCodigoOficial;

	@FXML
	private TableColumn<Parte, LocalDateTime> tcParteFecha;

	@FXML
	private TextField tfBuscarAsociaParteOperario;

	@FXML
	private TableColumn<Parte, String> tcParteDescripcion;

	@FXML
	private Label lbTotalPartes;

	@FXML
	private TableColumn<AsociaParteOperario, String> tcAsociaParteOperarioNombre;

	@FXML
	private TableView<OperaParteMaterial> tvOperaParteMaterial;

	@FXML
	private AnchorPane panelRaiz;

	@FXML
	private TextField tfBuscarOperaParteMaterial;

	@FXML
	private TableColumn<OperaParteMaterial, String> tcOperaParteMaterialNombre;

	@FXML
	private TableColumn<Parte, String> tcPartePrioridad;

	@FXML
	private TableView<Parte> tvPartes;

	@FXML
	private TableColumn<Parte, String> tcParteOrden;

	@FXML
	private Label lbTotalPartesAlmacen;

	@FXML
	private TableColumn<OperaParteMaterial, LocalDateTime> tcOperaParteMaterialFecha;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		panelRaiz.setOnMouseClicked(e -> {
			tvAsociaParteOperario.getSelectionModel().clearSelection();
			tvOperaParteMaterial.getSelectionModel().clearSelection();
			tvPartes.getSelectionModel().clearSelection();
			actualizar();
		});

		listaAsociaParteOperario = FXCollections.observableArrayList();
		listaOperaParteMaterial = FXCollections.observableArrayList();

		tvPartes.setItems(listaPartes);
		tcParteCodigo.setCellValueFactory(parte -> parte.getValue().codParteProperty().asObject());
		tcParteCodigoOficial.setCellValueFactory(parte -> parte.getValue().parteOficialProperty().asObject());
		tcParteDescripcion.setCellValueFactory(parte -> parte.getValue().descripcionProperty());
		tcParteEstado.setCellValueFactory(parte -> parte.getValue().estadoProperty());
		tcParteFecha.setCellValueFactory(parte -> parte.getValue().fechaProperty());
		tcParteImpreso.setCellValueFactory(parte -> parte.getValue().impresoProperty());
		tcParteLlamo.setCellValueFactory(parte -> parte.getValue().llamoProperty());
		tcHidrante.setCellValueFactory(parte -> parte.getValue().hidranteProperty());
		tcParteOrden.setCellValueFactory(parte -> parte.getValue().ordenProperty());
		tcParteOrigen.setCellValueFactory(parte -> parte.getValue().origenProperty().asString());
		tcPartePrioridad.setCellValueFactory(parte -> parte.getValue().prioridadProperty().asString());
		tvPartes.getSelectionModel().selectedItemProperty().addListener(e -> actualizar());
		tvPartes.setOnMouseClicked(e -> seleccionarParteConDobleClic(e));

		tvAsociaParteOperario.setItems(listaAsociaParteOperario);
		tcAsociaParteOperarioCodigo.setCellValueFactory(
				asociaParteOperaro -> asociaParteOperaro.getValue().getObjOperario().codOpProperty().asObject());
		tcAsociaParteOperarioNombre.setCellValueFactory(
				asociaParteOperaro -> asociaParteOperaro.getValue().getObjOperario().nombreProperty());

		tvOperaParteMaterial.setItems(listaOperaParteMaterial);
		tcOperaParteMaterialNombre.setCellValueFactory(e -> {
			String salida = e.getValue().getObjMaterial().getNombreTec();
			salida = salida + " (" + e.getValue().getObjMaterial().getNombre() + ")";
			return new SimpleStringProperty(salida);
		});
		tcOperaParteMaterialCantidad
				.setCellValueFactory(operaParteMaterial -> operaParteMaterial.getValue().cantidadProperty().asObject());

		tcParteCodigo.setSortType(SortType.DESCENDING);
		tvPartes.getSortOrder().add(tcParteCodigo);
	}

	private Consumer app;
	private ObservableList<Parte> listaPartes;
	private ObservableList<AsociaParteOperario> listaAsociaParteOperario;
	private ObservableList<OperaParteMaterial> listaOperaParteMaterial;

	public void setConsumer(Consumer app) {
		this.app = app;
	}

	public AnchorPane getPanelRaiz() {
		return panelRaiz;
	}

	public void iniciar() {
		listaPartes = app.getParteDAO().getPartes();
		actualizar();
		Filtrador.filtrarTablasGenericas(listaPartes, tvPartes, tfBuscarParte);
		Filtrador.filtrarTablasGenericas(listaAsociaParteOperario, tvAsociaParteOperario, tfBuscarAsociaParteOperario);
		Filtrador.filtrarTablasGenericas(listaOperaParteMaterial, tvOperaParteMaterial, tfBuscarOperaParteMaterial);
	}

	public void actualizar() {
		Parte parte = tvPartes.getSelectionModel().getSelectedItem();
		if (parte == null) {
			listaAsociaParteOperario.setAll(app.getAsociaParteOperarioDAO().getAsociaParteOperario());
			listaOperaParteMaterial.setAll(app.getOperaParteMaterialDAO().getOperaParteMaterial());
		} else {
			listaAsociaParteOperario.setAll(app.getAsociaParteOperarioDAO().getParteOperarios(parte));
			listaOperaParteMaterial.setAll(app.getOperaParteMaterialDAO().getParteMateriales(parte));
		}
		refresh();
	}

	public Parte getParteSeleccionado() {
		return tvPartes.getSelectionModel().getSelectedItem();
	}

	private void seleccionarParteConDobleClic(MouseEvent e) {
		Parte parteSeleccionado = null;
		if (e.getClickCount() == 2) {
			parteSeleccionado = tvPartes.getSelectionModel().getSelectedItem();
			if (parteSeleccionado != null) {
				// asdf
			}
		}
	}

	private void refresh() {
		tvAsociaParteOperario.refresh();
		tvOperaParteMaterial.refresh();
		tvPartes.refresh();
	}
}
