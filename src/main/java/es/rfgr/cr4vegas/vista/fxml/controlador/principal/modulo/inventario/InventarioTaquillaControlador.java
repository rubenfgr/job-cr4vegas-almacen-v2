package es.rfgr.cr4vegas.vista.fxml.controlador.principal.modulo.inventario;

import java.time.LocalDateTime;

import es.rfgr.cr4vegas.controlador.Consumer;
import es.rfgr.cr4vegas.modelo.postgresql.dto.almacen.taquilla.OperaTaquillaMaterial;
import es.rfgr.cr4vegas.modelo.postgresql.dto.almacen.taquilla.Taquilla;
import es.rfgr.cr4vegas.vista.utileria.Filtrador;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class InventarioTaquillaControlador {

	@FXML
	private AnchorPane panelRaiz;

	@FXML
	private Label lbTotalMaterialRetirado;

	@FXML
	private Label lbTotalGastado;

	@FXML
	private Label lbTotalMaterialInventario;

	@FXML
	private Label lbTotalInventario;

	@FXML
	private TextField tfBuscarAlmacen;

	@FXML
	private TableView<Taquilla> tvAlmacen;

	@FXML
	private TableColumn<Taquilla, Integer> tcID;

	@FXML
	private TableColumn<Taquilla, Boolean> tcActivo;

	@FXML
	private TableColumn<Taquilla, String> tcTipo;

	@FXML
	private TableColumn<Taquilla, String> tcLugar;

	@FXML
	private TextField tfBuscarRegistro;

	@FXML
	private TableView<OperaTaquillaMaterial> tvRegistro;

	@FXML
	private TableColumn<OperaTaquillaMaterial, String> tcRegistroMaterial;

	@FXML
	private TableColumn<OperaTaquillaMaterial, Integer> tcRegistroCantidad;

	@FXML
	private TableColumn<OperaTaquillaMaterial, LocalDateTime> tcRegistroFecha;

	private Consumer app;

	private ObservableList<Taquilla> listaTaquillas;
	private ObservableList<OperaTaquillaMaterial> listaTaquillasMateriales;

	public void setConsumer(Consumer app) {
		this.app = app;
	}

	public AnchorPane getPanelRaiz() {
		return panelRaiz;
	}

	public void iniciar() {
		listaTaquillas = app.getTaquillaDAO().getTaquillas();
		listaTaquillasMateriales = app.getOperaTaquillaMaterialDAO().getOperaTaquillaMateriales();

		tvAlmacen.setItems(listaTaquillas);
		tcID.setCellValueFactory(v -> v.getValue().codTaquillaProperty().asObject());
		tcActivo.setCellValueFactory(v -> v.getValue().activoProperty());
		tcTipo.setCellValueFactory(v -> v.getValue().getObjTipoTaquilla().nombreProperty());
		tcLugar.setCellValueFactory(v -> v.getValue().lugarProperty());

		tvRegistro.setItems(listaTaquillasMateriales);
		tcRegistroMaterial.setCellValueFactory(v -> v.getValue().getObjMaterial().nombreTecProperty());
		tcRegistroCantidad.setCellValueFactory(v -> v.getValue().cantidadProperty().asObject());
		tcRegistroFecha.setCellValueFactory(v -> v.getValue().fechaProperty());

		panelRaiz.setOnMouseClicked(e -> deseleccionar());
		tvAlmacen.getSelectionModel().selectedItemProperty()
				.addListener((ov, oldValue, newValue) -> reiniciarSeleccion(newValue));
		panelRaiz.setOnMouseClicked(e -> deseleccionar());

		Filtrador.filtrarTablasGenericas(listaTaquillas, tvAlmacen, tfBuscarAlmacen);
		Filtrador.filtrarTablasGenericas(listaTaquillasMateriales, tvRegistro, tfBuscarRegistro);
	}

	private void reiniciarSeleccion(Taquilla taquilla) {
		if (taquilla != null) {
			Filtrador.filtrarTablasGenericas(listaTaquillasMateriales, tvRegistro, tfBuscarRegistro);
		}
	}

	public void deseleccionar() {
		tvAlmacen.getSelectionModel().clearSelection();
		tvRegistro.getSelectionModel().clearSelection();
		Filtrador.filtrarTablasGenericas(listaTaquillasMateriales, tvRegistro, tfBuscarRegistro);
	}

	public Taquilla getTaquillaSeleccionada() {
		return tvAlmacen.getSelectionModel().getSelectedItem();
	}

}
