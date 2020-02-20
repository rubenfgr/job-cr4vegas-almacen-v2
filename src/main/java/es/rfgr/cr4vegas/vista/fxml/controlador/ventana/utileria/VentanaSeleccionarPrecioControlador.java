package es.rfgr.cr4vegas.vista.fxml.controlador.ventana.utileria;

import java.math.BigDecimal;

import es.rfgr.cr4vegas.controlador.Consumer;
import es.rfgr.cr4vegas.modelo.postgresql.dto.almacen.material.Precio;
import es.rfgr.cr4vegas.modelo.postgresql.dto.almacen.presupuesto.TienePresupuestoMaterial;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class VentanaSeleccionarPrecioControlador {

	@FXML
	private TableColumn<Precio, String> tcTienda;

	@FXML
	private AnchorPane panelRaiz;

	@FXML
	private TableView<Precio> tvPrecios;

	@FXML
	private TableColumn<Precio, BigDecimal> tcPrecio;

	private Consumer app;
	private ObservableList<Precio> listaPreciosMaterial;
	private TienePresupuestoMaterial tienePresupuestoMaterial;
	private Stage escenarioVentana;

	public void iniciar() {
		listaPreciosMaterial = FXCollections.observableArrayList();
		tvPrecios.setItems(listaPreciosMaterial);
		tcTienda.setCellValueFactory(e -> e.getValue().getObjTienda().nombreProperty());
		tcPrecio.setCellValueFactory(e -> e.getValue().precioProperty());
		tvPrecios.setOnMouseClicked(e -> seleccionarPrecioConDobleClic(e));
	}

	public void setConsumer(Consumer app) {
		this.app = app;
	}

	public AnchorPane getPanelRaiz() {
		return panelRaiz;
	}

	public void iniciarCon(TienePresupuestoMaterial tpm) {
		this.tienePresupuestoMaterial = tpm;

		listaPreciosMaterial.addAll(app.getPrecioDAO().getPreciosMaterial(tienePresupuestoMaterial.getObjMaterial()));

		escenarioVentana = app.getVistaModelo().getEscenario(getPanelRaiz(), "SELECCIONAR PRECIO");
		escenarioVentana.setOnCloseRequest(e -> salir());
		escenarioVentana.show();
	}

	private void seleccionarPrecioConDobleClic(MouseEvent e) {
		if (e.getClickCount() >= 2) {
			Precio precio = tvPrecios.getSelectionModel().getSelectedItem();
			if (precio != null) {
				tienePresupuestoMaterial.setPrecio(precio.getPrecio());
				tienePresupuestoMaterial.setTotal();
				app.getVistaModelo().getModeloVentana().getVentanaManipulaPresupuestoControlador().sumarTodoYMostrar();
				salir();
			}
		}
	}

	private void salir() {
		listaPreciosMaterial.clear();
		escenarioVentana.close();
	}
}
