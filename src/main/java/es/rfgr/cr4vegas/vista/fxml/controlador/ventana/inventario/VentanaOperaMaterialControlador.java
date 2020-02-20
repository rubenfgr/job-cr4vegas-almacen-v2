package es.rfgr.cr4vegas.vista.fxml.controlador.ventana.inventario;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;

import es.rfgr.cr4vegas.controlador.Consumer;
import es.rfgr.cr4vegas.modelo.postgresql.conexion.ConexionAlmacen;
import es.rfgr.cr4vegas.modelo.postgresql.dto.almacen.material.Material;
import es.rfgr.cr4vegas.modelo.postgresql.dto.almacen.taquilla.OperaTaquillaMaterial;
import es.rfgr.cr4vegas.modelo.postgresql.dto.almacen.taquilla.Taquilla;
import es.rfgr.cr4vegas.modelo.postgresql.dto.herencia.opera.Opera;
import es.rfgr.cr4vegas.modelo.postgresql.dto.operario.OperaOperarioMaterial;
import es.rfgr.cr4vegas.modelo.postgresql.dto.operario.Operario;
import es.rfgr.cr4vegas.modelo.postgresql.dto.parte.OperaParteMaterial;
import es.rfgr.cr4vegas.modelo.postgresql.dto.parte.Parte;
import es.rfgr.cr4vegas.modelo.postgresql.exceptions.DAOException;
import es.rfgr.cr4vegas.utileria.OperaMaterial;
import es.rfgr.cr4vegas.vista.utileria.Dialogos;
import es.rfgr.cr4vegas.vista.utileria.Filtrador;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class VentanaOperaMaterialControlador {

	@FXML
	private AnchorPane panelRaiz;

	@FXML
	private TextField tfBuscar;

	@FXML
	private Label lbIdentificador;

	@FXML
	private Label lbValor;

	@FXML
	private Button btEliminarMaterial;

	@FXML
	private TableView<Material> tvMaterial;

	@FXML
	private TableColumn<Material, String> tcMaterial;

	@FXML
	private TableColumn<Material, Integer> tcCantidad;

	@FXML
	private TableView<Opera> tvMaterialRetirar;

	@FXML
	private TableColumn<Opera, String> tcRetirarMaterial;

	@FXML
	private TableColumn<Opera, String> tcRetirarCantidad;

	@FXML
	private Button btAceptar;

	@FXML
	private Button btCancelar;

	private Consumer app;
	private Operario operario;
	private Parte parte;
	private Taquilla taquilla;
	private ObservableList<Material> listadoMaterial;
	private ObservableList<Opera> listadoMaterialRetirar;
	private ObservableList<Opera> listadoMaterialRetirarCopia;

	private String titulo;
	private Stage escenarioVentana;

	public void setConsumer(Consumer app) {
		this.app = app;
	}

	public AnchorPane getPanelRaiz() {
		return panelRaiz;
	}

	public void iniciar() {
		listadoMaterialRetirar = FXCollections.observableArrayList();
		listadoMaterialRetirarCopia = FXCollections.observableArrayList();
		listadoMaterial = FXCollections.observableArrayList();

		tvMaterial.setItems(listadoMaterial);
		tvMaterialRetirar.setItems(listadoMaterialRetirar);
		tvMaterialRetirar.setEditable(true);

		tcMaterial.setCellValueFactory(e -> {
			String salida = e.getValue().getNombreTec();
			salida = salida + " (" + e.getValue().getNombre() + ")";
			return new SimpleStringProperty(salida);
		});
		tcCantidad.setCellValueFactory(e -> e.getValue().cantidadProperty().asObject());

		tcRetirarMaterial.setCellValueFactory(e -> {
			String salida = e.getValue().getObjMaterial().getNombreTec();
			salida = salida + " (" + e.getValue().getObjMaterial().getNombre() + ")";
			return new SimpleStringProperty(salida);
		});
		tcRetirarCantidad.setCellValueFactory(e -> e.getValue().cantidadProperty().asString());
		tcRetirarCantidad.setCellFactory(TextFieldTableCell.forTableColumn());
		tcRetirarCantidad.setOnEditCommit(e -> modificarCantidadARetirar(e));

		tvMaterial.setOnMouseClicked(e -> getMaterialSeleccionadoConDobleClic(e));

		btEliminarMaterial.setOnAction(e -> eliminarMaterialRetirar());
		btAceptar.setOnAction(e -> aceptarOperacion());
		btCancelar.setOnAction(e -> salir());
	}

	public void iniciarCon(OperaMaterial obj) {

		if (obj instanceof Operario) {
			operario = (Operario) obj;
			iniciarConOperario();

		} else if (obj instanceof Parte) {
			parte = (Parte) obj;
			iniciarConParte();

		} else if (obj instanceof Taquilla) {
			taquilla = (Taquilla) obj;
			iniciarConTaquilla();
		}

		listadoMaterial = app.getMaterialDAO().getMateriales();
		Filtrador.filtrarTablasGenericas(listadoMaterial, tvMaterial, tfBuscar);

		escenarioVentana = app.getVistaModelo().getEscenario(panelRaiz, titulo);
		escenarioVentana.setOnCloseRequest(e -> salir());
		escenarioVentana.showAndWait();
	}

	private void iniciarConOperario() {
		lbIdentificador.setText("Operario:");
		lbValor.setText(operario.getCodOp() + ", " + operario.getNombre() + " " + operario.getApellido1() + " "
				+ operario.getApellido2());

		iniciarConOperarioModificarMaterialRetirar();

	}

	private void iniciarConOperarioModificarMaterialRetirar() {
		titulo = "MODIFICAR MATERIAL RETIRADO - OPERARIO";
		ObservableList<OperaOperarioMaterial> listaOperaOperarioMaterial = app.getOperaOperarioMaterialDAO()
				.getOperarioOperaOperariosMateriales(operario);
		for (OperaOperarioMaterial dtoOnList : listaOperaOperarioMaterial) {
			listadoMaterialRetirar.add(new OperaOperarioMaterial(dtoOnList));
			listadoMaterialRetirarCopia.add(new OperaOperarioMaterial(dtoOnList));
		}
	}

	private void iniciarConParte() {
		lbIdentificador.setText("Parte:");
		lbValor.setText("" + parte.getCodParte());

		iniciarConParteModificarMaterialRetirar();
	}

	private void iniciarConParteModificarMaterialRetirar() {
		titulo = "MODIFICAR MATERIAL RETIRADO - PARTE";
		ObservableList<OperaParteMaterial> listaOperaParteMaterial = app.getOperaParteMaterialDAO()
				.getParteMateriales(parte);
		for (OperaParteMaterial dtoOnList : listaOperaParteMaterial) {
			listadoMaterialRetirar.add(new OperaParteMaterial(dtoOnList));
			listadoMaterialRetirarCopia.add(new OperaParteMaterial(dtoOnList));
		}
	}

	private void iniciarConTaquilla() {
		lbIdentificador.setText("Almacen:");
		lbValor.setText(taquilla.getTipoTaquilla() + ", " + taquilla.getLugar());

		iniciarConTaquillaModificarMaterialRetirar();
	}

	private void iniciarConTaquillaModificarMaterialRetirar() {
		titulo = "MODIFICAR MATERIAL RETIRADO - ALMACEN SECUNDARIO";
		ObservableList<OperaTaquillaMaterial> listaOperaTaquillaMaterial = app.getOperaTaquillaMaterialDAO()
				.getTaquillaMateriales(taquilla);
		for (OperaTaquillaMaterial dtoOnList : listaOperaTaquillaMaterial) {
			listadoMaterialRetirar.add(new OperaTaquillaMaterial(dtoOnList));
			listadoMaterialRetirarCopia.add(new OperaTaquillaMaterial(dtoOnList));
		}
	}

	private void getMaterialSeleccionadoConDobleClic(MouseEvent e) {
		if (e.getClickCount() == 2) {
			Material material = tvMaterial.getSelectionModel().getSelectedItem();

			if (!comprobarMaterialContenido(material)) {
				comprobarCantidadMaterial(material);

			} else {
				Dialogos.mostrarDialogoAdvertencia("El material ya se agregó.", escenarioVentana);
			}
		}
	}

	private void comprobarCantidadMaterial(Material material) {
		if (material.getCantidad() > 0) {
			agregarMaterialARetirarSegunOperador(material);

		} else {
			Dialogos.mostrarDialogoAdvertencia("No se puede agregar un material sin existencias.", escenarioVentana);
		}
	}

	private boolean comprobarMaterialContenido(Material material) {
		for (Opera opera : listadoMaterialRetirar) {
			if (opera.getObjMaterial().equals(material)) {
				return true;
			}
		}
		return false;
	}

	private void agregarMaterialARetirarSegunOperador(Material material) {
		if (operario != null) {
			OperaOperarioMaterial opera = new OperaOperarioMaterial();
			opera.setObjMaterial(material);
			opera.setCantidad(0);
			opera.setObjOperario(operario);
			listadoMaterialRetirar.add(opera);
		}
		if (parte != null) {
			OperaParteMaterial opera = new OperaParteMaterial();
			opera.setObjMaterial(material);
			opera.setCantidad(0);
			opera.setObjParte(parte);
			listadoMaterialRetirar.add(opera);
		}
		if (taquilla != null) {
			OperaTaquillaMaterial opera = new OperaTaquillaMaterial();
			opera.setObjMaterial(material);
			opera.setCantidad(0);
			opera.setObjTaquilla(taquilla);
			listadoMaterialRetirar.add(opera);
		}
	}

	private void eliminarMaterialRetirar() {
		Opera opera = tvMaterialRetirar.getSelectionModel().getSelectedItem();
		if (opera != null) {
			listadoMaterialRetirar.remove(opera);
		} else {
			Dialogos.mostrarDialogoAdvertencia(
					"Debe seleccionar el material a eliminar de la lista de materiales a retirar", escenarioVentana);
		}
	}

	private void aceptarOperacion() {
		Connection userConn = null;
		try {
			userConn = ConexionAlmacen.getConexion();
			userConn.setAutoCommit(false);
			app.setAllConnections(userConn);

			aceptarOperacionModificar();

			userConn.commit();

		} catch (Exception e) {
			try {
				e.printStackTrace();
				userConn.rollback();
				app.findAll();
				app.createAllDependencies();

			} catch (Exception e1) {
				Dialogos.mostrarDialogoExcepcion(e, escenarioVentana);
			}

		} finally {
			try {
				ConexionAlmacen.close(userConn);
			} catch (SQLException e1) {
				Dialogos.mostrarDialogoExcepcion(e1, escenarioVentana);
			}
		}
		salir();
	}

	private void aceptarOperacionModificar() throws Exception {
		if (operario != null) {
			ObservableList<OperaOperarioMaterial> listaMaterialRetirarOperario = FXCollections.observableArrayList();
			ObservableList<OperaOperarioMaterial> listaMaterialRetirarOperarioCopia = FXCollections
					.observableArrayList();
			for (Opera o : listadoMaterialRetirar) {
				listaMaterialRetirarOperario.add((OperaOperarioMaterial) o);
			}
			for (Opera o : listadoMaterialRetirarCopia) {
				listaMaterialRetirarOperarioCopia.add((OperaOperarioMaterial) o);
			}

			for (OperaOperarioMaterial otmCopia : listaMaterialRetirarOperarioCopia) {
				if (!listaMaterialRetirarOperario.contains(otmCopia)) {
					app.getOperaOperarioMaterialDAO().delete(otmCopia.createPK());
					app.getMaterialDAO().updateDTOMaterial(otmCopia.getObjMaterial());
				}
			}

			for (OperaOperarioMaterial otm : listaMaterialRetirarOperario) {
				otm.setFecha(LocalDateTime.now());
				if (!listaMaterialRetirarOperarioCopia.contains(otm)) {
					app.getOperaOperarioMaterialDAO().insert(otm);
				} else {
					app.getOperaOperarioMaterialDAO().update(otm.createPK(), otm);
				}
				app.getMaterialDAO().updateDTOMaterial(otm.getObjMaterial());
			}
		}
		if (parte != null) {
			ObservableList<OperaParteMaterial> listaMaterialRetirarParte = FXCollections.observableArrayList();
			ObservableList<OperaParteMaterial> listaMaterialRetirarParteCopia = FXCollections.observableArrayList();
			for (Opera o : listadoMaterialRetirar) {
				listaMaterialRetirarParte.add((OperaParteMaterial) o);
			}
			for (Opera o : listadoMaterialRetirarCopia) {
				listaMaterialRetirarParteCopia.add((OperaParteMaterial) o);
			}

			for (OperaParteMaterial otmCopia : listaMaterialRetirarParteCopia) {
				if (!listaMaterialRetirarParte.contains(otmCopia)) {
					app.getOperaParteMaterialDAO().delete(otmCopia.createPK());
					app.getMaterialDAO().updateDTOMaterial(otmCopia.getObjMaterial());
				}
			}

			for (OperaParteMaterial otm : listaMaterialRetirarParte) {
				otm.setFecha(LocalDateTime.now());
				if (!listaMaterialRetirarParteCopia.contains(otm)) {
					app.getOperaParteMaterialDAO().insert(otm);
				} else {
					app.getOperaParteMaterialDAO().update(otm.createPK(), otm);
				}
				app.getMaterialDAO().updateDTOMaterial(otm.getObjMaterial());
			}
		}
		if (taquilla != null) {
			ObservableList<OperaTaquillaMaterial> listaMaterialRetirarTaquilla = FXCollections.observableArrayList();
			ObservableList<OperaTaquillaMaterial> listaMaterialRetirarTaquillaCopia = FXCollections
					.observableArrayList();
			for (Opera o : listadoMaterialRetirar) {
				listaMaterialRetirarTaquilla.add((OperaTaquillaMaterial) o);
			}
			for (Opera o : listadoMaterialRetirarCopia) {
				listaMaterialRetirarTaquillaCopia.add((OperaTaquillaMaterial) o);
			}

			for (OperaTaquillaMaterial otmCopia : listaMaterialRetirarTaquillaCopia) {
				if (!listaMaterialRetirarTaquilla.contains(otmCopia)) {
					app.getOperaTaquillaMaterialDAO().delete(otmCopia.createPK());
					app.getMaterialDAO().updateDTOMaterial(otmCopia.getObjMaterial());
				}
			}

			for (OperaTaquillaMaterial otm : listaMaterialRetirarTaquilla) {
				otm.setFecha(LocalDateTime.now());
				if (!listaMaterialRetirarTaquillaCopia.contains(otm)) {
					app.getOperaTaquillaMaterialDAO().insert(otm);
				} else {
					app.getOperaTaquillaMaterialDAO().update(otm.createPK(), otm);
				}
				app.getMaterialDAO().updateDTOMaterial(otm.getObjMaterial());
			}
		}
	}

	private void reiniciarEstado() {
		lbIdentificador.setText("");
		lbValor.setText("");
		listadoMaterialRetirar.clear();
		listadoMaterialRetirarCopia.clear();
		operario = null;
		parte = null;
		taquilla = null;
	}

	private void salir() {
		reiniciarEstado();
		app.getVistaModelo().getModeloPrincipal().getOperarioPrincipalControlador().actualizarCampos(
				app.getVistaModelo().getModeloPrincipal().getOperarioPrincipalControlador().getSelectedOperario());
		escenarioVentana.close();
	}

	private void modificarCantidadARetirar(CellEditEvent<Opera, String> e) {
		try {
			if (Integer.valueOf(e.getNewValue()) > e.getRowValue().getObjMaterial().getCantidad()) {
				throw new DAOException("La cantidad ha retirar no puede ser superior a la actual");
			}
			e.getRowValue().setCantidad(e.getNewValue());
		} catch (Exception e1) {
			Dialogos.mostrarDialogoAdvertencia(e1.getMessage(), escenarioVentana);
			e.getRowValue().setCantidad(e.getOldValue());
			tvMaterialRetirar.refresh();
		}
	}
}
