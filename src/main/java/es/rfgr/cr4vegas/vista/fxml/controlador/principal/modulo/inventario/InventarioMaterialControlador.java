package es.rfgr.cr4vegas.vista.fxml.controlador.principal.modulo.inventario;

import java.math.BigDecimal;

import es.rfgr.cr4vegas.controlador.Consumer;
import es.rfgr.cr4vegas.modelo.postgresql.dto.almacen.material.Material;
import es.rfgr.cr4vegas.modelo.postgresql.dto.almacen.material.Precio;
import es.rfgr.cr4vegas.vista.utileria.ConversorImagenes;
import es.rfgr.cr4vegas.vista.utileria.Filtrador;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class InventarioMaterialControlador {

	@FXML
	private AnchorPane panelRaiz;

	@FXML
	private Label lbTotalTiposMaterial;

	@FXML
	private Label lbTotalMateriales;

	@FXML
	private Label lbPrecioTotalActual;

	@FXML
	private TextField tfBuscarMaterial;

	@FXML
	private TableView<Material> tvMateriales;

	@FXML
	private TableColumn<Material, Integer> tcCodigo;

	@FXML
	private TableColumn<Material, Boolean> tcActivo;

	@FXML
	private TableColumn<Material, String> tcNombreTecnico;

	@FXML
	private TableColumn<Material, String> tcNombre;

	@FXML
	private TableColumn<Material, Integer> tcCantidad;

	@FXML
	private TableColumn<Material, Integer> tcCantidadMin;

	@FXML
	private TableColumn<Material, String> tcUbicacion;

	@FXML
	private ImageView ivImagenMaterial;

	@FXML
	private Label lbNombreTecnico;

	@FXML
	private Label lbNombre;

	@FXML
	private Label lbCantidad;

	@FXML
	private Label lbCantidadMinima;

	@FXML
	private Label lbUbicacion;

	@FXML
	private Label lbFamilia;

	@FXML
	private Label lbMaterial;

	@FXML
	private Label lbDiametro;

	@FXML
	private Label lbPN;

	@FXML
	private Label lbPrecio;

	@FXML
	private Label lbCodigo4v;

	@FXML
	private Label lbActivo;

	@FXML
	private TableView<Precio> tvPrecios;

	@FXML
	private TableColumn<Precio, String> tcTienda;

	@FXML
	private TableColumn<Precio, BigDecimal> tcPrecio;

	@FXML
	private TableColumn<Precio, BigDecimal> tcPrecioPublico;

	private ObservableList<Material> listaMateriales;
	private ObservableList<Precio> listaPreciosMaterial;
	private Consumer app;

	public void setConsumer(Consumer app) {
		this.app = app;
	}

	public AnchorPane getPanelRaiz() {
		return panelRaiz;
	}

	public void iniciar() {
		listaPreciosMaterial = FXCollections.observableArrayList();
		listaMateriales = app.getMaterialDAO().getMateriales();
		tvMateriales.setItems(listaMateriales);

		tcCodigo.setCellValueFactory(material -> material.getValue().codigo4vProperty().asObject());
		tcActivo.setCellValueFactory(material -> material.getValue().activoProperty());
		tcNombreTecnico.setCellValueFactory(material -> material.getValue().nombreTecProperty());
		tcNombre.setCellValueFactory(material -> material.getValue().nombreProperty());
		tcCantidad.setCellValueFactory(material -> material.getValue().cantidadProperty().asObject());
		tcCantidadMin.setCellValueFactory(material -> material.getValue().cantidadMinProperty().asObject());
		tcUbicacion.setCellValueFactory(material -> material.getValue().getObjUbicacion().nombreProperty());

		tvMateriales.getSelectionModel().selectedItemProperty()
				.addListener((ov, oldValue, newValue) -> seleccionaMaterial(newValue));
		
		tvPrecios.setItems(listaPreciosMaterial);
		tcTienda.setCellValueFactory(precio -> precio.getValue().getObjTienda().nombreProperty());
		tcPrecio.setCellValueFactory(precio -> precio.getValue().precioProperty());
		tcPrecioPublico.setCellValueFactory(precio -> precio.getValue().precioPublicoProperty());
		
		panelRaiz.setOnMouseClicked(e -> limpiarSeleccion());

		lbTotalTiposMaterial.setText(String.valueOf(listaMateriales.size()));
		Filtrador.filtrarTablasGenericas(listaMateriales, tvMateriales, tfBuscarMaterial);
	}

	private void seleccionaMaterial(Material material) {
		if (material != null) {
			lbActivo.setText(String.valueOf(material.isActivo()));
			lbCodigo4v.setText(String.valueOf(material.getCodigo4v()));
			lbNombre.setText(material.getNombre());
			lbNombreTecnico.setText(material.getNombreTec());
			lbCantidad.setText(String.valueOf(material.getCantidad()));
			lbCantidadMinima.setText(String.valueOf(material.getCantidadMin()));
			lbDiametro.setText(String.valueOf(material.getDiametro()));
			lbFamilia.setText(material.getFamilia());
			lbMaterial.setText(material.getMaterial());
			lbUbicacion.setText(material.getUbicacion());
			lbPN.setText(material.getPn());
			
			listaPreciosMaterial.clear();
			listaPreciosMaterial.addAll(app.getPrecioDAO().getPreciosMaterial(material));

			ivImagenMaterial.setImage(ConversorImagenes.byteArrayToImage(material.getImagenBytes()));
		} else {
			lbActivo.setText("");
			lbCodigo4v.setText("");
			lbNombre.setText("");
			lbNombreTecnico.setText("");
			lbCantidad.setText("");
			lbCantidadMinima.setText("");
			lbDiametro.setText("");
			lbFamilia.textProperty().unbind();
			lbFamilia.setText("");
			lbMaterial.textProperty().unbind();
			lbMaterial.setText("");
			lbUbicacion.textProperty().unbind();
			lbUbicacion.setText("");
			lbPN.setText("");
			
			listaPreciosMaterial.clear();

			ivImagenMaterial.setImage(null);
		}
	}

	public Material getMaterialSeleccionado() {
		return tvMateriales.getSelectionModel().getSelectedItem();
	}

	private void limpiarSeleccion() {
		tvMateriales.getSelectionModel().clearSelection();
		listaPreciosMaterial.clear();
		refresh();
	}

	private void refresh() {
		tvMateriales.refresh();
	}

}
