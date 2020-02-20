package es.rfgr.cr4vegas.vista.fxml.controlador.ventana.inventario;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;

import es.rfgr.cr4vegas.controlador.Consumer;
import es.rfgr.cr4vegas.modelo.postgresql.conexion.ConexionAlmacen;
import es.rfgr.cr4vegas.modelo.postgresql.dto.almacen.compra.Compra;
import es.rfgr.cr4vegas.modelo.postgresql.dto.almacen.compra.CompraMaterial;
import es.rfgr.cr4vegas.modelo.postgresql.dto.almacen.compra.CompraPK;
import es.rfgr.cr4vegas.modelo.postgresql.dto.almacen.material.Material;
import es.rfgr.cr4vegas.modelo.postgresql.dto.almacen.material.Precio;
import es.rfgr.cr4vegas.modelo.postgresql.dto.almacen.tienda.Tienda;
import es.rfgr.cr4vegas.modelo.postgresql.dto.operario.Operario;
import es.rfgr.cr4vegas.modelo.postgresql.dto.parte.OperaParteMaterial;
import es.rfgr.cr4vegas.modelo.postgresql.dto.parte.Parte;
import es.rfgr.cr4vegas.modelo.postgresql.exceptions.DAOException;
import es.rfgr.cr4vegas.vista.utileria.Dialogos;
import es.rfgr.cr4vegas.vista.utileria.Filtrador;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class VentanaManipulaCompraControlador {

	@FXML
	private ComboBox<Parte> cbxParte;

	@FXML
	private CheckBox cbParte;

	@FXML
	private AnchorPane panelRaiz;

	@FXML
	private TableView<Material> tvMateriales;

	@FXML
	private TableColumn<Material, String> tcMaterialNombre;

	@FXML
	private TableColumn<Material, Integer> tcMaterialCantidad;

	@FXML
	private TextField tfBuscarMaterial;

	@FXML
	private ComboBox<Tienda> cbxEntidad;

	@FXML
	private TextField tfFactura;

	@FXML
	private ComboBox<Operario> cbxOperario;

	@FXML
	private TableView<CompraMaterial> tvMaterialesAgregados;

	@FXML
	private TableColumn<CompraMaterial, String> tcCompraMaterialNombre;

	@FXML
	private TableColumn<CompraMaterial, String> tcCompraMaterialCantidad;

	@FXML
	private TableColumn<CompraMaterial, String> tcCompraMaterialPrecio;

	@FXML
	private TableColumn<CompraMaterial, String> tcCompraMaterialPrecioPublico;

	@FXML
	private Button btAceptar;

	@FXML
	private Button btCancelar;

	@FXML
	private Button btEliminar;

	@FXML
	private Button btAgregarMaterial;

	private Consumer app;
	private Stage escenarioVentana;

	private CompraPK compraPK;
	private Compra compra;
	private ObservableList<CompraMaterial> listaMaterialARetirar;
	private ObservableList<CompraMaterial> listaMaterialARetirarCopia;

	private boolean agregar;
	private String titulo;

	public void setConsumer(Consumer app) {
		this.app = app;
	}

	public AnchorPane getPanelRaiz() {
		return panelRaiz;
	}

	public void iniciar() {
		listaMaterialARetirar = FXCollections.observableArrayList();
		listaMaterialARetirarCopia = FXCollections.observableArrayList();

		tvMaterialesAgregados.setItems(listaMaterialARetirar);
		tvMaterialesAgregados.setEditable(true);
		tcCompraMaterialNombre.setCellValueFactory(e -> seleccionarNombreCombinadoCompraMaterial(e));
		tcCompraMaterialCantidad.setCellValueFactory(e -> e.getValue().cantidadProperty().asString());
		tcCompraMaterialCantidad.setCellFactory(TextFieldTableCell.forTableColumn());
		tcCompraMaterialCantidad.setOnEditCommit(e -> aceptarCantidad(e));
		tcCompraMaterialPrecio.setCellValueFactory(e -> {
			if (e.getValue().getObjPrecio() == null) {
				return new SimpleStringProperty("");
			} else {
				return e.getValue().getObjPrecio().precioProperty().asString();
			}
		});
		tcCompraMaterialPrecio.setCellFactory(TextFieldTableCell.forTableColumn());
		tcCompraMaterialPrecio.setOnEditCommit(e -> {
			try {
				e.getRowValue().getObjPrecio().setPrecio(BigDecimal.valueOf(Double.valueOf(e.getNewValue())));
			} catch (NumberFormatException e1) {
				e.consume();
				Dialogos.mostrarDialogoAdvertencia("El número no es correcto", escenarioVentana);
			}
		});
		tcCompraMaterialPrecioPublico.setCellValueFactory(e -> {
			if (e.getValue().getObjPrecio() == null) {
				return new SimpleStringProperty("");
			} else {
				return e.getValue().getObjPrecio().precioPublicoProperty().asString();
			}
		});
		tcCompraMaterialPrecioPublico.setCellFactory(TextFieldTableCell.forTableColumn());
		tcCompraMaterialPrecioPublico.setOnEditCommit(e -> {
			try {
				e.getRowValue().getObjPrecio().setPrecioPublico(BigDecimal.valueOf(Double.valueOf(e.getNewValue())));
			} catch (NumberFormatException e1) {
				e.consume();
				Dialogos.mostrarDialogoAdvertencia("El número no es correcto", escenarioVentana);
			}
		});

		tvMateriales.setItems(app.getMaterialDAO().getMateriales());
		tvMateriales.setOnMouseClicked(e -> detectarDobleClic(e));
		tcMaterialNombre.setCellValueFactory(e -> seleccionarNombreCombinadoMaterial(e));
		tcMaterialCantidad.setCellValueFactory(e -> e.getValue().cantidadProperty().asObject());

		cbxEntidad.setItems(app.getTiendaDAO().getTiendas());
		cbxOperario.setItems(app.getOperarioDAO().getOperarios());

		btAceptar.setOnAction(e -> aceptar());
		btCancelar.setOnAction(e -> salir());
		btEliminar.setOnAction(e -> eliminarMaterialARetirar());
		btAgregarMaterial.setOnAction(e -> agregarMaterial());

		cbParte.setOnAction(e -> asociarParte());
		cbxParte.setItems(app.getParteDAO().getPartes());

		Filtrador.filtrarTablasGenericas(app.getMaterialDAO().getMateriales(), tvMateriales, tfBuscarMaterial);
		tfBuscarMaterial.setOnMouseClicked(e -> tvMateriales.refresh());

		cbxEntidad.getSelectionModel().selectedItemProperty()
				.addListener((ov, oldValue, newValue) -> cambiarTienda(newValue));
	}

	private void agregarMaterial() {
		app.getVistaModelo().getModeloVentana().getVentanaManipulaMaterialControlador().iniciarCon(null);
	}

	public void iniciarCon(Compra compra) {
		if (compra != null) {
			iniciarModificarCompra(compra);
		} else {
			iniciarAgregarCompra();
		}

		escenarioVentana = app.getVistaModelo().getEscenario(panelRaiz, titulo);
		escenarioVentana.showAndWait();
	}

	private void iniciarModificarCompra(Compra compra) {
		this.compra = new Compra(compra);
		compraPK = compra.createPK();
		titulo = "MODIFICAR COMPRA";
		agregar = false;

		cbxEntidad.getSelectionModel().select(compra.getObjTienda());
		tfFactura.setText(compra.getFactura());
		cbxOperario.getSelectionModel().select(compra.getObjOperario());
		if (compra.getObjParte() != null) {
			cbParte.setSelected(true);
			cbParte.setDisable(true);
			cbxParte.getSelectionModel().select(compra.getObjParte());
			cbxParte.setDisable(true);
		} else {
			cbParte.setDisable(false);
			cbxParte.setDisable(false);
		}

		listaMaterialARetirar.setAll(app.getCompraMaterialDAO().getCompraMateriales(compra));

		hacerCopiaDeLaListaARetirar();
	}

	public void hacerCopiaDeLaListaARetirar() {
		for (CompraMaterial compraMaterial : listaMaterialARetirar) {
			listaMaterialARetirarCopia.add(new CompraMaterial(compraMaterial));
		}
	}

	private void iniciarAgregarCompra() {
		compra = new Compra();
		titulo = "AGREGAR COMPRA";
		agregar = true;

		cbxEntidad.getSelectionModel().clearSelection();
		tfFactura.setText("");
		cbxOperario.getSelectionModel().clearSelection();
		listaMaterialARetirar.clear();
		cbParte.setDisable(false);
		cbxParte.setDisable(true);
	}

	private void aceptar() {
		Connection userConn = null;
		try {
			modificarEstadoCompra();
			
			userConn = ConexionAlmacen.getConexion();
			userConn.setAutoCommit(false);
			app.setAllConnections(userConn);

			manejadorAceptar();

			userConn.commit();
			userConn.close();

			salir();

		} catch (SQLException e) {
			try {
				userConn.rollback();

				app.findAll();
				app.createAllDependencies();

				userConn.close();

				Dialogos.mostrarDialogoExcepcion(e, escenarioVentana);

			} catch (Exception e1) {
				Dialogos.mostrarDialogoExcepcion(e1, escenarioVentana);
			}
		} catch (DAOException e) {
			Dialogos.mostrarDialogoExcepcion(e, escenarioVentana);
		}

	}

	private void manejadorAceptar() throws DAOException {
		if (agregar) {
			agregarCompra();

		} else {
			modificarCompra();
		}
		app.getVistaModelo().getModeloPrincipal().getInventarioCompraControlador()
				.actualizarTablaCompraMaterial(compra);
	}

	private void agregarCompra() throws DAOException {
		compraPK = app.getCompraDAO().insert(compra);

		compra.setCodCompra(compraPK.getCodCompra());

		agregarCompraAMaterialRetirar();
		
		for (CompraMaterial compraMaterial : listaMaterialARetirar) {
			compraMaterial.setPrecio(compraMaterial.getObjPrecio().getPrecio());
		}

		app.getCompraMaterialDAO().insertAll(listaMaterialARetirar);

		for (CompraMaterial compraMaterial : listaMaterialARetirar) {
			app.getMaterialDAO().updateDTOMaterial(compraMaterial.getObjMaterial());

			Precio precioNuevo = compraMaterial.getObjPrecio();

			Precio precio = app.getPrecioDAO().getPrecioConMaterialYTienda(precioNuevo.getObjMaterial(),
					precioNuevo.getObjTienda());

			if (precio == null) {
				precioNuevo.setObjTienda(compra.getObjTienda());
				app.getPrecioDAO().insert(precioNuevo);
			} else {
				app.getPrecioDAO().update(precioNuevo.createPK(), precioNuevo);
			}
		}

		app.getCompraDAO().updateDTOCompra(compra);

		if (cbParte.isSelected()) {
			if (cbxParte.getSelectionModel().getSelectedItem() == null) {
				Dialogos.mostrarDialogoAdvertencia("Si marca la casilla de Parte debe seleccionar uno.",
						escenarioVentana);
			} else {
				for (CompraMaterial compraMaterial : listaMaterialARetirar) {
					OperaParteMaterial operaParteMaterial = new OperaParteMaterial();
					operaParteMaterial.setCantidad(compraMaterial.getCantidad());
					operaParteMaterial.setObjMaterial(compraMaterial.getObjMaterial());
					operaParteMaterial.setObjParte(cbxParte.getSelectionModel().getSelectedItem());
					operaParteMaterial.setFecha(LocalDateTime.now());

					ObservableList<OperaParteMaterial> listaMaterialesParte = app.getOperaParteMaterialDAO().getParteMateriales(cbxParte.getSelectionModel().getSelectedItem());
					
					boolean insertar = true;
					
					for (OperaParteMaterial opm : listaMaterialesParte) {
						if (opm.equals(operaParteMaterial)) {
							insertar = false;
						} 
					}
					
					if (insertar) {
						app.getOperaParteMaterialDAO().insert(operaParteMaterial);
					} else {
						app.getOperaParteMaterialDAO().update(operaParteMaterial.createPK(), operaParteMaterial);
					}
				}
			}
		}
	}

	private void modificarCompra() throws DAOException {
		app.getCompraDAO().update(compraPK, compra);

		agregarCompraAMaterialRetirar();

		for (CompraMaterial compraMaterial : listaMaterialARetirarCopia) {
			if (!listaMaterialARetirar.contains(compraMaterial)) {
				app.getCompraMaterialDAO().delete(compraMaterial.createPK());
			}
			app.getMaterialDAO().updateDTOMaterial(compraMaterial.getObjMaterial());
		}

		for (CompraMaterial compraMaterial : listaMaterialARetirar) {
			compraMaterial.setPrecio(compraMaterial.getObjPrecio().getPrecio());
			
			if (!listaMaterialARetirarCopia.contains(compraMaterial)) {
				app.getCompraMaterialDAO().insert(compraMaterial);
			} else {
				app.getCompraMaterialDAO().update(compraMaterial.createPK(), compraMaterial);
			}
			app.getMaterialDAO().updateDTOMaterial(compraMaterial.getObjMaterial());
			
			app.getMaterialDAO().updateDTOMaterial(compraMaterial.getObjMaterial());

			Precio precioNuevo = compraMaterial.getObjPrecio();

			Precio precio = app.getPrecioDAO().getPrecioConMaterialYTienda(precioNuevo.getObjMaterial(),
					precioNuevo.getObjTienda());

			if (precio == null) {
				precioNuevo.setObjTienda(compra.getObjTienda());
				app.getPrecioDAO().insert(precioNuevo);
			} else {
				app.getPrecioDAO().update(precioNuevo.createPK(), precioNuevo);
			}
		}
		
		if (cbParte.isSelected()) {
			if (cbxParte.getSelectionModel().getSelectedItem() == null) {
				Dialogos.mostrarDialogoAdvertencia("Si marca la casilla de Parte debe seleccionar uno.",
						escenarioVentana);
			} else {
				for (CompraMaterial compraMaterial : listaMaterialARetirar) {
					OperaParteMaterial operaParteMaterial = new OperaParteMaterial();
					operaParteMaterial.setCantidad(compraMaterial.getCantidad());
					operaParteMaterial.setObjMaterial(compraMaterial.getObjMaterial());
					operaParteMaterial.setObjParte(cbxParte.getSelectionModel().getSelectedItem());
					operaParteMaterial.setFecha(LocalDateTime.now());

					ObservableList<OperaParteMaterial> listaMaterialesParte = app.getOperaParteMaterialDAO().getParteMateriales(cbxParte.getSelectionModel().getSelectedItem());
					
					boolean insertar = true;
					
					for (OperaParteMaterial opm : listaMaterialesParte) {
						if (opm.equals(operaParteMaterial)) {
							insertar = false;
						} 
					}
					
					if (insertar) {
						app.getOperaParteMaterialDAO().insert(operaParteMaterial);
					} else {
						app.getOperaParteMaterialDAO().update(operaParteMaterial.createPK(), operaParteMaterial);
					}
					
				}
			}
		}

		app.getCompraDAO().updateDTOCompra(compra);
	}

	private void modificarEstadoCompra() throws DAOException {
		try {
			compra.setObjOperario(cbxOperario.getSelectionModel().getSelectedItem());
			compra.setObjTienda(cbxEntidad.getSelectionModel().getSelectedItem());
			compra.setFactura(tfFactura.getText());
		} catch (Exception e) {
			throw new DAOException(e.getMessage(), e);
		}
	}

	private void agregarCompraAMaterialRetirar() {
		for (CompraMaterial compraMaterial : listaMaterialARetirar) {
			compraMaterial.setObjCompra(compra);
		}
	}

	private void salir() {
		reiniciarCampos();
		escenarioVentana.close();
	}

	private void reiniciarCampos() {
		tfBuscarMaterial.setText("");
		cbxEntidad.getSelectionModel().clearSelection();
		tfFactura.setText("");
		cbxOperario.getSelectionModel().clearSelection();
		cbParte.setSelected(false);
		cbxParte.getSelectionModel().clearSelection();
		cbxParte.setDisable(true);
		listaMaterialARetirar.clear();
		listaMaterialARetirarCopia.clear();
	}

	private void eliminarMaterialARetirar() {
		CompraMaterial compraMaterialSeleccionada = tvMaterialesAgregados.getSelectionModel().getSelectedItem();
		if (compraMaterialSeleccionada != null) {
			listaMaterialARetirar.remove(compraMaterialSeleccionada);

		} else {
			Dialogos.mostrarDialogoAdvertencia("Debe selecciona el material ha eliminar", escenarioVentana);
		}
	}

	private StringProperty seleccionarNombreCombinadoCompraMaterial(CellDataFeatures<CompraMaterial, String> e) {
		StringProperty nombreCombinado = new SimpleStringProperty();
		nombreCombinado.set(
				e.getValue().getObjMaterial().getNombre() + " (" + e.getValue().getObjMaterial().getNombreTec() + ")");
		return nombreCombinado;
	}

	private StringProperty seleccionarNombreCombinadoMaterial(CellDataFeatures<Material, String> e) {
		StringProperty nombreCombinado = new SimpleStringProperty();
		nombreCombinado.set(e.getValue().getNombreTec() + (e.getValue().getNombre() == null || e.getValue().getNombre().equals("")? "" : " (" + e.getValue().getNombre() + ")"));
		return nombreCombinado;
	}

	private void detectarDobleClic(MouseEvent e) {
		if (e.getClickCount() == 2) {
			agregarMaterialARetirar();
		}
	}

	private void agregarMaterialARetirar() {
		try {
			Material material = tvMateriales.getSelectionModel().getSelectedItem();

			if (material != null) {
				CompraMaterial compraMaterial = new CompraMaterial();
				compraMaterial.setObjMaterial(material);
				compraMaterial.setCantidad("0");
				compraMaterial.setObjCompra(compra);

				Precio precio = app.getPrecioDAO().getPrecioConMaterialYTienda(material,
						cbxEntidad.getSelectionModel().getSelectedItem());

				if (precio == null) {
					precio = new Precio();
					precio.setObjMaterial(material);
				}

				compraMaterial.setObjPrecio(precio);

				listaMaterialARetirar.add(compraMaterial);
			}

		} catch (Exception e) {
			Dialogos.mostrarDialogoAdvertencia(e.getMessage(), escenarioVentana);
		}
	}

	private void asociarParte() {
		if (cbParte.isSelected()) {
			cbxParte.setDisable(false);
		} else {
			cbxParte.setDisable(true);
		}
	}

	private void aceptarCantidad(CellEditEvent<CompraMaterial, String> e) {
		e.getRowValue().setCantidad(e.getNewValue());
	}

	private void cambiarTienda(Tienda tienda) {
		if (tienda != null) {
			for (CompraMaterial compraMaterial : listaMaterialARetirar) {
				Precio precio = app.getPrecioDAO().getPrecioConMaterialYTienda(compraMaterial.getObjMaterial(), tienda);
				if (precio != null) {
					compraMaterial.setObjPrecio(precio);
				} else {
					precio = new Precio();
					precio.setObjMaterial(compraMaterial.getObjMaterial());
					compraMaterial.setObjPrecio(precio);
				}
			}
			tvMaterialesAgregados.refresh();
		}
	}

}
