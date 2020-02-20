package es.rfgr.cr4vegas.vista.fxml.controlador.ventana.inventario;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;

import es.rfgr.cr4vegas.controlador.Consumer;
import es.rfgr.cr4vegas.modelo.postgresql.conexion.ConexionAlmacen;
import es.rfgr.cr4vegas.modelo.postgresql.dto.almacen.material.Material;
import es.rfgr.cr4vegas.modelo.postgresql.dto.almacen.presupuesto.Presupuesto;
import es.rfgr.cr4vegas.modelo.postgresql.dto.almacen.presupuesto.PresupuestoPK;
import es.rfgr.cr4vegas.modelo.postgresql.dto.almacen.presupuesto.TienePresupuestoMaterial;
import es.rfgr.cr4vegas.modelo.postgresql.exceptions.DAOException;
import es.rfgr.cr4vegas.vista.utileria.Dialogos;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class VentanaManipulaPresupuestoControlador {

	@FXML
	private Label lbFecha;

	@FXML
	private Label lbTotal;

	@FXML
	private Button btSeleccionarPrecio;

	@FXML
	private TextField tfBuscarMaterial;

	@FXML
	private DatePicker dtValidez;

	@FXML
	private Button btAceptar;

	@FXML
	private AnchorPane panelRaiz;

	@FXML
	private TextField tfTelefono;

	@FXML
	private TableColumn<TienePresupuestoMaterial, String> tcPresupuestoMaterialCantidad;

	@FXML
	private Button btAgregarMaterial;

	@FXML
	private TableColumn<TienePresupuestoMaterial, String> tcPresupuestoMaterialNombre;

	@FXML
	private TableColumn<Material, String> tcMaterialNombre;

	@FXML
	private TextField tfCliente;

	@FXML
	private TableColumn<Material, Integer> tcMaterialCantidad;

	@FXML
	private TableColumn<TienePresupuestoMaterial, String> tcPresupuestoMaterialPrecio;

	@FXML
	private Button btEliminar;

	@FXML
	private TableView<TienePresupuestoMaterial> tvMaterialesAgregados;

	@FXML
	private TableView<Material> tvMateriales;

	@FXML
	private TableColumn<TienePresupuestoMaterial, BigDecimal> tcPresupuestoMaterialTotal;

	@FXML
	private Button btCancelar;

	private Consumer app;
	private Stage escenarioVentana;

	private PresupuestoPK presupuestoPK;
	private Presupuesto presupuesto;
	private ObservableList<TienePresupuestoMaterial> listaMaterialARetirar;
	private ObservableList<TienePresupuestoMaterial> listaMaterialARetirarCopia;

	private boolean agregar;
	private String titulo;

	public void iniciar() {
		listaMaterialARetirar = FXCollections.observableArrayList();
		listaMaterialARetirarCopia = FXCollections.observableArrayList();

		tvMaterialesAgregados.setItems(listaMaterialARetirar);
		tcPresupuestoMaterialNombre.setCellValueFactory(e -> {
			StringProperty nombreCombinado = new SimpleStringProperty();
			nombreCombinado.set(
					e.getValue().getObjMaterial().getNombreTec() + (e.getValue().getObjMaterial().getNombre() == null
							|| e.getValue().getObjMaterial().getNombre().equals("") ? ""
									: " (" + e.getValue().getObjMaterial().getNombre() + ")"));
			return nombreCombinado;
		});
		tcPresupuestoMaterialCantidad.setCellValueFactory(e -> e.getValue().cantidadProperty().asString());
		tcPresupuestoMaterialCantidad.setCellFactory(TextFieldTableCell.forTableColumn());
		tcPresupuestoMaterialCantidad.setOnEditCommit(e -> {
			try {
				Integer salida = Integer.valueOf(e.getNewValue());
				e.getRowValue().setCantidad(salida);
				e.getRowValue().setTotal();
				sumarTodoYMostrar();
				tvMaterialesAgregados.refresh();
			} catch (NumberFormatException ex) {
				e.consume();
			}
		});
		tcPresupuestoMaterialPrecio.setCellValueFactory(e -> e.getValue().precioProperty().asString());
		tcPresupuestoMaterialPrecio.setCellFactory(TextFieldTableCell.forTableColumn());
		tcPresupuestoMaterialPrecio.setOnEditCommit(e -> {
			try {
				Double salida = Double.valueOf(e.getNewValue());
				e.getRowValue().setPrecio(BigDecimal.valueOf(salida));
				e.getRowValue().setTotal();
				sumarTodoYMostrar();
				tvMaterialesAgregados.refresh();
			} catch (NumberFormatException ex) {
				e.consume();
			}
		});
		tcPresupuestoMaterialTotal.setCellValueFactory(e -> e.getValue().totalProperty());
		tvMateriales.setItems(app.getMaterialDAO().getMateriales());
		tvMateriales.setOnMouseClicked(e -> agregarMaterialConDobleClic(e));
		tcMaterialNombre.setCellValueFactory(e -> {
			StringProperty nombreCombinado = new SimpleStringProperty();
			nombreCombinado.set(e.getValue().getNombreTec()
					+ (e.getValue().getNombre() == null || e.getValue().getNombre().equals("") ? ""
							: " (" + e.getValue().getNombre() + ")"));
			return nombreCombinado;
		});
		tcMaterialCantidad.setCellValueFactory(e -> e.getValue().cantidadProperty().asObject());

		btAgregarMaterial.setOnAction(e -> agregarMaterial());
		btEliminar.setOnAction(e -> eliminarPresupuestoMaterial());
		btSeleccionarPrecio.setOnAction(e -> seleccionaPrecioMaterial());
		btAceptar.setOnAction(e -> aceptar());
		btCancelar.setOnAction(e -> salir());
	}

	private void agregarMaterial() {
		app.getVistaModelo().getModeloVentana().getVentanaManipulaMaterialControlador().iniciarCon(null);
	}

	public void setConsumer(Consumer app) {
		this.app = app;
	}

	public AnchorPane getPanelRaiz() {
		return panelRaiz;
	}

	public void iniciarCon(Presupuesto presupuesto) {
		if (presupuesto == null) {
			iniciarAgregarPresupuesto();
		} else {
			iniciarModificarPresupuesto(presupuesto);
		}

		escenarioVentana = app.getVistaModelo().getEscenario(panelRaiz, titulo);
		escenarioVentana.setOnCloseRequest(e -> salir());
		escenarioVentana.show();
	}

	private void iniciarAgregarPresupuesto() {
		agregar = true;
		titulo = "AGREGAR PRESUPUESTO";
		presupuesto = new Presupuesto();

		cargarPresupuestoEnCampos();
	}

	private void iniciarModificarPresupuesto(Presupuesto presupuesto) {
		agregar = false;
		titulo = "MODIFICAR PRESUPUESTO";
		this.presupuesto = new Presupuesto(presupuesto);
		presupuestoPK = this.presupuesto.createPK();

		cargarPresupuestoEnCampos();
	}

	private void cargarPresupuestoEnCampos() {
		tfCliente.setText(presupuesto.getCliente());
		tfTelefono.setText(presupuesto.getTelefono());
		dtValidez.getEditor().setText(presupuesto.getValidez() == null ? "" : presupuesto.getValidez().toString());
		lbTotal.setText(presupuesto.getTotal().toString());
		lbFecha.setText(presupuesto.getFecha().toString());
	}

	private void agregarMaterialConDobleClic(MouseEvent e) {
		if (e.getClickCount() >= 2) {
			Material material = tvMateriales.getSelectionModel().getSelectedItem();

			if (material != null) {
				TienePresupuestoMaterial tpm = new TienePresupuestoMaterial();
				tpm.setObjMaterial(material);
				tpm.setObjPresupuesto(presupuesto);
				tpm.setCantidad(0);
				tpm.setPrecio(BigDecimal.ZERO);

				listaMaterialARetirar.add(tpm);
			}
		}
	}

	private void aceptar() {
		Connection conn = null;
		try {
			cargarCamposEnPresupuesto();
			conn = ConexionAlmacen.getConexion();
			conn.setAutoCommit(false);

			if (conn != null) {
				app.setAllConnections(conn);
				if (agregar) {
					aceptarAgregarPresupuesto();
				} else {
					aceptarModificarPresupuesto();
				}
			}

			conn.commit();
			
			salir();
			
		} catch (Exception e) {
			e.printStackTrace();
			try {
				conn.rollback();

				app.findAll();
				app.createAllDependencies();

			} catch (Exception e1) {
				e1.printStackTrace();
				Dialogos.mostrarDialogoExcepcion(e1, escenarioVentana);
			}
		} finally {
			try {
				ConexionAlmacen.close(conn);

			} catch (SQLException e) {
				e.printStackTrace();
				Dialogos.mostrarDialogoExcepcion(e, escenarioVentana);
			}
		}
	}

	private void aceptarAgregarPresupuesto() throws DAOException {
		presupuestoPK = app.getPresupuestoDAO().insert(presupuesto);
		presupuesto.setCodPresupuesto(presupuestoPK.getCodPresupuesto());

		agregarPresupuestoAMaterial();

		app.getTienePresupuestoMaterialDAO().insertAll(listaMaterialARetirar);
	}

	private void aceptarModificarPresupuesto() throws DAOException {
		app.getPresupuestoDAO().update(presupuesto.createPK(), presupuesto);

		agregarPresupuestoAMaterial();

		for (TienePresupuestoMaterial tpm : listaMaterialARetirarCopia) {
			if (!listaMaterialARetirar.contains(tpm)) {
				app.getTienePresupuestoMaterialDAO().delete(tpm.createPK());
			}
		}

		for (TienePresupuestoMaterial tpm : listaMaterialARetirar) {
			if (!listaMaterialARetirarCopia.contains(tpm)) {
				app.getTienePresupuestoMaterialDAO().insert(tpm);
			} else {
				app.getTienePresupuestoMaterialDAO().update(tpm.createPK(), tpm);
			}
		}
	}

	private void agregarPresupuestoAMaterial() {
		for (TienePresupuestoMaterial tpm : listaMaterialARetirar) {
			tpm.setObjPresupuesto(presupuesto);
		}
	}

	private void cargarCamposEnPresupuesto() throws Exception {
		presupuesto.setCliente(tfCliente.getText());
		presupuesto.setTelefono(tfTelefono.getText());
		presupuesto.setValidez(dtValidez.getEditor().getText());
	}

	public void sumarTodoYMostrar() {
		Double resultado = 0.0;
		for (TienePresupuestoMaterial tpm : listaMaterialARetirar) {
			if (tpm.getTotal() != null) {
				resultado = resultado + tpm.getTotal().doubleValue();
			}
		}
		presupuesto.setTotal(BigDecimal.valueOf(resultado));
		lbTotal.setText(presupuesto.getTotal().toString());
	}

	private void eliminarPresupuestoMaterial() {
		TienePresupuestoMaterial tienePresupuestoMaterial = tvMaterialesAgregados.getSelectionModel().getSelectedItem();
		if (tienePresupuestoMaterial != null) {
			listaMaterialARetirar.remove(tienePresupuestoMaterial);
			sumarTodoYMostrar();

		} else {
			Dialogos.mostrarDialogoAdvertencia("Debe selecciona el material ha eliminar", escenarioVentana);
		}
	}

	private void seleccionaPrecioMaterial() {
		TienePresupuestoMaterial tienePresupuestoMaterial = tvMaterialesAgregados.getSelectionModel().getSelectedItem();
		if (tienePresupuestoMaterial != null) {
			app.getVistaModelo().getModeloVentana().getVentanaSeleccionarPrecioControlador()
					.iniciarCon(tienePresupuestoMaterial);

		} else {
			Dialogos.mostrarDialogoAdvertencia("Debe selecciona el material ha eliminar", escenarioVentana);
		}
	}

	public void refrescar() {
		tvMateriales.refresh();
		tvMaterialesAgregados.refresh();
	}

	private void salir() {
		reiniciar();
		escenarioVentana.close();
	}

	private void reiniciar() {
		tfBuscarMaterial.clear();
		tfCliente.clear();
		tfTelefono.clear();
		lbFecha.setText("---");
		dtValidez.getEditor().clear();
		listaMaterialARetirar.clear();
		listaMaterialARetirarCopia.clear();
		presupuesto = null;
		presupuestoPK = null;
	}
}
