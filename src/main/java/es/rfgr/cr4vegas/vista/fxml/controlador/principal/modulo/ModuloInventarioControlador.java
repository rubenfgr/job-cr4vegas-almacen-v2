package es.rfgr.cr4vegas.vista.fxml.controlador.principal.modulo;

import es.rfgr.cr4vegas.controlador.Consumer;
import es.rfgr.cr4vegas.modelo.postgresql.dto.almacen.compra.Compra;
import es.rfgr.cr4vegas.modelo.postgresql.dto.almacen.material.Material;
import es.rfgr.cr4vegas.modelo.postgresql.dto.almacen.presupuesto.Presupuesto;
import es.rfgr.cr4vegas.modelo.postgresql.dto.almacen.taquilla.Taquilla;
import es.rfgr.cr4vegas.modelo.postgresql.exceptions.DAOException;
import es.rfgr.cr4vegas.vista.fxml.controlador.principal.modulo.inventario.InventarioTaquillaControlador;
import es.rfgr.cr4vegas.vista.utileria.Dialogos;
import javafx.fxml.FXML;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class ModuloInventarioControlador {

	@FXML
	private AnchorPane panelRaiz;

	@FXML
	private RadioButton rbInformacion;

	@FXML
	private RadioButton rbAlmacenPrincipal;

	@FXML
	private Hyperlink hlAlmacenPrincipalAgregarMaterial;

	@FXML
	private Hyperlink hlAlmacenPrincipalModificarMaterial;

	@FXML
	private Hyperlink hlAlmacenPrincipalEliminarMaterial;

	@FXML
	private RadioButton rbCompras;

	@FXML
	private Hyperlink hlComprasRealizarCompra;

	@FXML
	private Hyperlink hlComprasModificarCompra;

	@FXML
	private Hyperlink hlComprasEliminarCompra;

	@FXML
	private RadioButton rbAlmacenes;

	@FXML
	private Hyperlink hlAgregarAlmacen;

	@FXML
	private Hyperlink hlModificarAlmacen;

	@FXML
	private Hyperlink hlEliminarAlmacen;

	@FXML
	private Hyperlink hlAlmacenesMaterialRetirado;

	@FXML
	private RadioButton rbPresupuestos;

	@FXML
	private Hyperlink hlRealizarPresupuesto;

	@FXML
	private Hyperlink hlModificarPresupuesto;

	@FXML
	private Hyperlink hlEliminarPresupuesto;

	@FXML
	private StackPane paneInventarioPrincipal;

	private Consumer app;
	private ToggleGroup grupoRB;

	private Stage escenarioPrincipal;
	private InventarioTaquillaControlador taquillaControlador;

	public void setConsumer(Consumer app) {
		this.app = app;
	}

	public AnchorPane getPanelRaiz() {
		return panelRaiz;
	}

	public void iniciar() {
		escenarioPrincipal = app.getVistaModelo().getEscenarioPrincipal();
		taquillaControlador = app.getVistaModelo().getModeloPrincipal().getInventarioTaquillaControlador();

		hlAlmacenPrincipalAgregarMaterial.setOnAction(
				e -> app.getVistaModelo().getModeloVentana().getVentanaManipulaMaterialControlador().iniciarCon(null));
		hlAlmacenPrincipalModificarMaterial.setOnAction(e -> modificarMaterial());
		hlAlmacenPrincipalEliminarMaterial.setOnAction(e -> eliminarMaterial());

		hlComprasRealizarCompra.setOnAction(
				e -> app.getVistaModelo().getModeloVentana().getVentanaManipulaCompraControlador().iniciarCon(null));
		hlComprasModificarCompra.setOnAction(e -> modificarCompra());
		hlComprasEliminarCompra.setOnAction(e -> eliminarCompra());

		hlAgregarAlmacen.setOnAction(e -> manipularAlmacen("agregar"));
		hlModificarAlmacen.setOnAction(e -> manipularAlmacen("modificar"));
		hlEliminarAlmacen.setOnAction(e -> eliminarAlmacen());
		hlAlmacenesMaterialRetirado.setOnAction(e -> retirarMaterialAlmacenes());

		hlRealizarPresupuesto.setOnAction(e -> manipularPresupuesto("agregar"));
		hlModificarPresupuesto.setOnAction(e -> manipularPresupuesto("modificar"));
		hlEliminarPresupuesto.setOnAction(e -> eliminarPresupuesto());

		grupoRB = new ToggleGroup();
		rbAlmacenes.setToggleGroup(grupoRB);
		rbAlmacenPrincipal.setToggleGroup(grupoRB);
		rbCompras.setToggleGroup(grupoRB);
		rbInformacion.setToggleGroup(grupoRB);
		rbPresupuestos.setToggleGroup(grupoRB);
		rbAlmacenPrincipal.setSelected(true);
		grupoRB.selectedToggleProperty().addListener(e -> obtenerRadioBotonSeleccionado());

		obtenerRadioBotonSeleccionado();
	}

	private void obtenerRadioBotonSeleccionado() {
		RadioButton seleccionado = (RadioButton) grupoRB.getSelectedToggle();
		paneInventarioPrincipal.getChildren().clear();
		if (seleccionado.equals(rbInformacion)) {
			paneInventarioPrincipal.getChildren().add(
					app.getVistaModelo().getModeloPrincipal().getInventarioInformacionControlador().getPanelRaiz());
		} else {
			// ...
		}
		if (seleccionado.equals(rbAlmacenPrincipal)) {
			paneInventarioPrincipal.getChildren()
					.add(app.getVistaModelo().getModeloPrincipal().getInventarioMaterialControlador().getPanelRaiz());
			hlAlmacenPrincipalAgregarMaterial.setDisable(false);
			hlAlmacenPrincipalModificarMaterial.setDisable(false);
			hlAlmacenPrincipalEliminarMaterial.setDisable(false);
		} else {
			hlAlmacenPrincipalAgregarMaterial.setDisable(true);
			hlAlmacenPrincipalModificarMaterial.setDisable(true);
			hlAlmacenPrincipalEliminarMaterial.setDisable(true);
		}
		if (seleccionado.equals(rbCompras)) {
			paneInventarioPrincipal.getChildren()
					.add(app.getVistaModelo().getModeloPrincipal().getInventarioCompraControlador().getPanelRaiz());
			hlComprasRealizarCompra.setDisable(false);
			hlComprasModificarCompra.setDisable(false);
			hlComprasEliminarCompra.setDisable(false);
		} else {
			hlComprasRealizarCompra.setDisable(true);
			hlComprasModificarCompra.setDisable(true);
			hlComprasEliminarCompra.setDisable(true);
		}
		if (seleccionado.equals(rbAlmacenes)) {
			paneInventarioPrincipal.getChildren()
					.add(app.getVistaModelo().getModeloPrincipal().getInventarioTaquillaControlador().getPanelRaiz());
			hlAgregarAlmacen.setDisable(false);
			hlModificarAlmacen.setDisable(false);
			hlEliminarAlmacen.setDisable(false);
			hlAlmacenesMaterialRetirado.setDisable(false);
		} else {
			hlAgregarAlmacen.setDisable(true);
			hlModificarAlmacen.setDisable(true);
			hlEliminarAlmacen.setDisable(true);
			hlAlmacenesMaterialRetirado.setDisable(true);
		}
		if (seleccionado.equals(rbPresupuestos)) {
			paneInventarioPrincipal.getChildren().add(
					app.getVistaModelo().getModeloPrincipal().getInventarioPresupuestoControlador().getPanelRaiz());
			hlRealizarPresupuesto.setDisable(false);
			hlModificarPresupuesto.setDisable(false);
			hlEliminarPresupuesto.setDisable(false);
		} else {
			hlRealizarPresupuesto.setDisable(true);
			hlModificarPresupuesto.setDisable(true);
			hlEliminarPresupuesto.setDisable(true);
		}
	}

	private void modificarMaterial() {
		Material material = app.getVistaModelo().getModeloPrincipal().getInventarioMaterialControlador()
				.getMaterialSeleccionado();
		if (material == null) {
			Dialogos.mostrarDialogoAdvertencia("Debe seleccionar el material a modificar",
					app.getVistaModelo().getEscenarioPrincipal());
		} else {
			app.getVistaModelo().getModeloVentana().getVentanaManipulaMaterialControlador().iniciarCon(material);
		}
	}

	private void eliminarMaterial() {
		Material material = app.getVistaModelo().getModeloPrincipal().getInventarioMaterialControlador()
				.getMaterialSeleccionado();
		try {
			if (material != null) {
				if (Dialogos.mostrarDialogoConfirmacion("¿Está seguro de eliminar el material \""
						+ material.getNombreTec() + " (" + material.getNombre() + ")\"",
						app.getVistaModelo().getEscenarioPrincipal())) {
					app.getMaterialDAO().delete(app.getVistaModelo().getModeloPrincipal()
							.getInventarioMaterialControlador().getMaterialSeleccionado().createPK());
				}
			} else {
				Dialogos.mostrarDialogoAdvertencia("Debe seleccionar el material a eliminar",
						app.getVistaModelo().getEscenarioPrincipal());
			}
		} catch (Exception e) {
			Dialogos.mostrarDialogoError(e.getMessage(), app.getVistaModelo().getEscenarioPrincipal());
			e.printStackTrace();
		}

	}

	private void modificarCompra() {
		Compra compra = app.getVistaModelo().getModeloPrincipal().getInventarioCompraControlador()
				.getCompraSeleccionada();
		if (compra != null) {
			app.getVistaModelo().getModeloVentana().getVentanaManipulaCompraControlador().iniciarCon(compra);
		} else {
			Dialogos.mostrarDialogoAdvertencia("Debe seleccionar la compra a modificar",
					app.getVistaModelo().getEscenarioPrincipal());
		}
	}

	private void eliminarCompra() {
		Compra compra = app.getVistaModelo().getModeloPrincipal().getInventarioCompraControlador()
				.getCompraSeleccionada();
		try {
			if (compra != null) {
				if (Dialogos.mostrarDialogoConfirmacion("¿Está seguro de eliminar la compra?",
						app.getVistaModelo().getEscenarioPrincipal())) {
					app.getCompraDAO().delete(compra.createPK());
					app.getVistaModelo().getModeloPrincipal().getInventarioCompraControlador().reiniciarPanelCompras();
				}
			} else {
				Dialogos.mostrarDialogoAdvertencia("Debe seleccionar la compra a eliminar",
						app.getVistaModelo().getEscenarioPrincipal());
			}
		} catch (Exception e) {
			Dialogos.mostrarDialogoError(e.getMessage(), app.getVistaModelo().getEscenarioPrincipal());
			e.printStackTrace();
		}
	}

	private void manipularAlmacen(String accion) {
		if (accion.equals("agregar")) {
			app.getVistaModelo().getModeloVentana().getVentanaManipulaTaquillaControlador().inciarCon(null);
		}
		if (accion.equals("modificar")) {
			iniciarModificarTaquilla();
		}
	}

	private void iniciarModificarTaquilla() {
		Taquilla taquillaSeleccionada = taquillaControlador.getTaquillaSeleccionada();
		if (taquillaSeleccionada == null) {
			Dialogos.mostrarDialogoAdvertencia("Debe seleccionar el almacén a modificar.", escenarioPrincipal);
		} else {
			app.getVistaModelo().getModeloVentana().getVentanaManipulaTaquillaControlador()
					.inciarCon(taquillaSeleccionada);
		}
	}

	private void eliminarAlmacen() {
		try {
			Taquilla taquillaSeleccionada = taquillaControlador.getTaquillaSeleccionada();
			if (taquillaSeleccionada != null) {
				if (Dialogos.mostrarDialogoConfirmacion("¿Está seguro de eliminar el Almacen?",
						app.getVistaModelo().getEscenarioPrincipal())) {
					app.getTaquillaDAO().delete(taquillaSeleccionada.createPK());
					app.getVistaModelo().getModeloPrincipal().getInventarioTaquillaControlador().deseleccionar();
				}
			} else {
				Dialogos.mostrarDialogoAdvertencia("Debe seleccionar el almacén a eliminar.", escenarioPrincipal);
			}
		} catch (DAOException e) {
			Dialogos.mostrarDialogoExcepcion(e, escenarioPrincipal);
		}
	}

	private void retirarMaterialAlmacenes() {
		Taquilla taquillaSeleccionada = taquillaControlador.getTaquillaSeleccionada();
		if (taquillaSeleccionada == null) {
			Dialogos.mostrarDialogoAdvertencia("Debe seleccionar el almacén que va a retirar material.",
					escenarioPrincipal);
		} else {
			app.getVistaModelo().getModeloVentana().getVentanaOperaMaterialControlador()
					.iniciarCon(taquillaSeleccionada);
		}
	}

	private void manipularPresupuesto(String accion) {
		if (accion.equals("agregar")) {
			app.getVistaModelo().getModeloVentana().getVentanaManipulaPresupuestoControlador().iniciarCon(null);
		}
		if (accion.equals("modificar")) {
			iniciarModificarPresupuesto();
		}
	}

	private void iniciarModificarPresupuesto() {
		Presupuesto presupuesto = app.getVistaModelo().getModeloPrincipal().getInventarioPresupuestoControlador()
				.getPresupuestoSeleccionado();
		if (presupuesto == null) {
			Dialogos.mostrarDialogoAdvertencia("Debe seleccionar el presupuesto a modificar.", escenarioPrincipal);
		} else {
			app.getVistaModelo().getModeloVentana().getVentanaManipulaPresupuestoControlador().iniciarCon(presupuesto);
		}
	}

	private void eliminarPresupuesto() {
		Presupuesto presupuesto = app.getVistaModelo().getModeloPrincipal().getInventarioPresupuestoControlador()
				.getPresupuestoSeleccionado();
		try {
			if (presupuesto != null) {
				if (Dialogos.mostrarDialogoConfirmacion("¿Está seguro de eliminar el presupuesto?",
						app.getVistaModelo().getEscenarioPrincipal())) {
					app.getPresupuestoDAO().delete(presupuesto.createPK());
					app.getVistaModelo().getModeloPrincipal().getInventarioPresupuestoControlador()
							.reiniciarPanelPresupuestos();
				}
			} else {
				Dialogos.mostrarDialogoAdvertencia("Debe seleccionar el presupuesto a eliminar",
						app.getVistaModelo().getEscenarioPrincipal());
			}
		} catch (Exception e) {
			Dialogos.mostrarDialogoError(e.getMessage(), app.getVistaModelo().getEscenarioPrincipal());
			e.printStackTrace();
		}
	}
}
