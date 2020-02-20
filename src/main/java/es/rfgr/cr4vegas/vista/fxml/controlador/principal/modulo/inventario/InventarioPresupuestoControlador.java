package es.rfgr.cr4vegas.vista.fxml.controlador.principal.modulo.inventario;

import java.math.BigDecimal;
import java.time.LocalDate;

import es.rfgr.cr4vegas.controlador.Consumer;
import es.rfgr.cr4vegas.modelo.postgresql.dto.almacen.presupuesto.Presupuesto;
import es.rfgr.cr4vegas.modelo.postgresql.dto.almacen.presupuesto.TienePresupuestoMaterial;
import es.rfgr.cr4vegas.modelo.postgresql.exceptions.DAOException;
import es.rfgr.cr4vegas.utileria.informes.InformePresupuesto;
import es.rfgr.cr4vegas.vista.utileria.Dialogos;
import es.rfgr.cr4vegas.vista.utileria.Filtrador;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class InventarioPresupuestoControlador {

	@FXML
	private TextField tfBuscarPresupuesto;

	@FXML
	private TableColumn<Presupuesto, LocalDate> tcFecha;

	@FXML
	private TableColumn<Presupuesto, String> tcTelefono;

	@FXML
	private TableColumn<Presupuesto, BigDecimal> tcTotal;

	@FXML
	private TextField tfBuscarMaterial;

	@FXML
	private TableView<Presupuesto> tvPresupuestos;

	@FXML
	private TableColumn<TienePresupuestoMaterial, BigDecimal> tcMaterialPrecio;

	@FXML
	private AnchorPane panelRaiz;

	@FXML
	private TableView<TienePresupuestoMaterial> tvComprasMateriales;

	@FXML
	private TableColumn<Presupuesto, String> tcCliente;

	@FXML
	private TableColumn<Presupuesto, LocalDate> tcValidez;

	@FXML
	private TableColumn<TienePresupuestoMaterial, String> tcMaterialNombre;

	@FXML
	private TableColumn<TienePresupuestoMaterial, Integer> tcMaterialCantidad;

	@FXML
	private TableColumn<Presupuesto, Integer> tcCodigo;

	@FXML
	private TableColumn<TienePresupuestoMaterial, BigDecimal> tcMaterialTotal;

	@FXML
	private Button btVisualizar;

	private Consumer app;
	private ObservableList<Presupuesto> listaPresupuestos;
	private ObservableList<TienePresupuestoMaterial> listaPresupuestoMateriales;

	public void iniciar() {
		listaPresupuestos = app.getPresupuestoDAO().getPresupuestos();
		listaPresupuestoMateriales = FXCollections.observableArrayList();

		tvPresupuestos.setItems(listaPresupuestos);
		tcCodigo.setCellValueFactory(e -> e.getValue().codPresupuestoProperty().asObject());
		tcCliente.setCellValueFactory(e -> e.getValue().clienteProperty());
		tcTelefono.setCellValueFactory(e -> e.getValue().telefonoProperty());
		tcFecha.setCellValueFactory(e -> e.getValue().fechaProperty());
		tcValidez.setCellValueFactory(e -> e.getValue().validezProperty());
		tcTotal.setCellValueFactory(e -> e.getValue().totalProperty());

		tvPresupuestos.getSelectionModel().selectedItemProperty()
				.addListener((ov, oldValue, newValue) -> cambiarSeleccion(newValue));

		tvComprasMateriales.setItems(listaPresupuestoMateriales);
		tcMaterialNombre.setCellValueFactory(e -> e.getValue().getObjMaterial().nombreTecProperty());
		tcMaterialCantidad.setCellValueFactory(e -> e.getValue().cantidadProperty().asObject());
		tcMaterialPrecio.setCellValueFactory(e -> e.getValue().precioProperty());
		tcMaterialTotal.setCellValueFactory(e -> e.getValue().totalProperty());

		Filtrador.filtrarTablasGenericas(listaPresupuestos, tvPresupuestos, tfBuscarPresupuesto);
		Filtrador.filtrarTablasGenericas(listaPresupuestoMateriales, tvComprasMateriales, tfBuscarMaterial);

		btVisualizar.setOnAction(e -> visualizarInformePresupuesto());
		panelRaiz.setOnMouseClicked(e -> limpiarSeleccion());
	}

	private void cambiarSeleccion(Presupuesto presupuesto) {
		listaPresupuestoMateriales.clear();
		if (presupuesto != null) {
			listaPresupuestoMateriales
					.addAll(app.getTienePresupuestoMaterialDAO().getPresupuestoMateriales(presupuesto));
		} else {
			listaPresupuestoMateriales.clear();
		}
		refrescar();
	}

	private void limpiarSeleccion() {
		tvPresupuestos.getSelectionModel().clearSelection();
	}

	private void visualizarInformePresupuesto() {
		try {
			Presupuesto presupuesto = tvPresupuestos.getSelectionModel().getSelectedItem();
			if (presupuesto != null) {
				InformePresupuesto.rellenar(presupuesto);
			} else {
				Dialogos.mostrarDialogoAdvertencia("Debe seleccionar el presupuesto a visualizar.",
						app.getVistaModelo().getEscenarioPrincipal());
			}
		} catch (DAOException e) {
			Dialogos.mostrarDialogoExcepcion(e, app.getVistaModelo().getEscenarioPrincipal());
		}
	}

	private void refrescar() {
		tvComprasMateriales.refresh();
		tvPresupuestos.refresh();
	}

	public void setConsumer(Consumer app) {
		this.app = app;
	}

	public AnchorPane getPanelRaiz() {
		return panelRaiz;
	}

	public Presupuesto getPresupuestoSeleccionado() {
		return tvPresupuestos.getSelectionModel().getSelectedItem();
	}

	public void reiniciarPanelPresupuestos() {
		tvPresupuestos.getSelectionModel().clearSelection();

		Filtrador.filtrarTablasGenericas(listaPresupuestoMateriales, tvComprasMateriales, tfBuscarMaterial);
	}
}
