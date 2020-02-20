package es.rfgr.cr4vegas.vista.fxml.controlador.principal.modulo.inventario;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import es.rfgr.cr4vegas.controlador.Consumer;
import es.rfgr.cr4vegas.modelo.postgresql.dto.almacen.compra.Compra;
import es.rfgr.cr4vegas.modelo.postgresql.dto.almacen.compra.CompraMaterial;
import es.rfgr.cr4vegas.vista.utileria.Filtrador;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.SortType;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class InventarioCompraControlador {

	@FXML
	private AnchorPane panelRaiz;

	@FXML
	private Label lbTotalComprasRealizadas;

	@FXML
	private Label lbTotalMaterialesComprados;

	@FXML
	private Label lbTotalGastado;

	@FXML
	private TextField tfBuscarCompra;

	@FXML
	private TableView<Compra> tvCompras;

	@FXML
	private TableColumn<Compra, Integer> tcCompraCodigo;

	@FXML
	private TableColumn<Compra, String> tcCompraOperario;

	@FXML
	private TableColumn<Compra, String> tcCompraTienda;

	@FXML
	private TableColumn<Compra, String> tcCompraFactura;

	@FXML
	private TableColumn<Compra, BigDecimal> tcCompraTotal;

	@FXML
	private TableColumn<Compra, LocalDateTime> tcCompraFecha;

	@FXML
	private TextField tfBuscarMaterial;

	@FXML
	private TableView<CompraMaterial> tvComprasMateriales;

	@FXML
	private TableColumn<CompraMaterial, String> tcMaterialNombre;

	@FXML
	private TableColumn<CompraMaterial, Integer> tcMaterialCantidad;

	@FXML
	private TableColumn<CompraMaterial, BigDecimal> tcMaterialPrecio;

	private Consumer app;
	private ObservableList<Compra> listaCompras;
	private ObservableList<CompraMaterial> listaComprasMateriales;

	public void setConsumer(Consumer app) {
		this.app = app;
	}

	public AnchorPane getPanelRaiz() {
		return panelRaiz;
	}

	public void iniciar() {
		listaCompras = app.getCompraDAO().getCompras();
		listaComprasMateriales = app.getCompraMaterialDAO().getComprasMaterial();
		lbTotalComprasRealizadas.setText(String.valueOf(listaCompras.size()));

		tvCompras.setItems(listaCompras);
		tvComprasMateriales.setItems(listaComprasMateriales);
		tcCompraCodigo.setCellValueFactory(compra -> compra.getValue().codCompraProperty().asObject());
		tcCompraOperario.setCellValueFactory(compra -> compra.getValue().getObjOperario().nombreProperty());
		tcCompraTienda.setCellValueFactory(compra -> compra.getValue().getObjTienda().nombreProperty());
		tcCompraFactura.setCellValueFactory(compra -> compra.getValue().facturaProperty());
		tcCompraTotal.setCellValueFactory(compra -> compra.getValue().totalProperty());
		tcCompraFecha.setCellValueFactory(compra -> compra.getValue().fechaProperty());
		tcMaterialNombre
				.setCellValueFactory(compraMaterial -> compraMaterial.getValue().getObjMaterial().nombreTecProperty());
		tcMaterialCantidad
				.setCellValueFactory(compraMaterial -> compraMaterial.getValue().cantidadProperty().asObject());
		tcMaterialPrecio.setCellValueFactory(compraMaterial -> {
			if (compraMaterial.getValue().getObjPrecio() == null) {
				return new SimpleObjectProperty<BigDecimal>(0, null);
			} else {
				return compraMaterial.getValue().getObjPrecio().precioProperty();
			}
		});

		tvCompras.getSelectionModel().selectedItemProperty()
				.addListener((ov, oldValue, newValue) -> actualizarTablaCompraMaterial(newValue));

		tcCompraCodigo.setSortType(SortType.DESCENDING);
		tvCompras.getSortOrder().add(tcCompraCodigo);

		panelRaiz.setOnMouseClicked(e -> reiniciarPanelCompras());

		Filtrador.filtrarTablasGenericas(listaCompras, tvCompras, tfBuscarCompra);

		Filtrador.filtrarTablasGenericas(listaComprasMateriales, tvComprasMateriales, tfBuscarMaterial);
	}

	public void reiniciarPanelCompras() {
		tvCompras.getSelectionModel().clearSelection();

		Filtrador.filtrarTablasGenericas(listaComprasMateriales, tvComprasMateriales, tfBuscarMaterial);
	}

	public Compra getCompraSeleccionada() {
		return tvCompras.getSelectionModel().getSelectedItem();
	}

	public void actualizarTablaCompraMaterial(Compra compra) {
		if (compra != null) {
			Filtrador.filtrarTablasGenericas(app.getCompraMaterialDAO().getCompraMateriales(compra),
					tvComprasMateriales, tfBuscarMaterial);
		}
	}
}
