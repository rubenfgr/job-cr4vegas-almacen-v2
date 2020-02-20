package es.rfgr.cr4vegas.vista.fxml.controlador.ventana.inventario;

import es.rfgr.cr4vegas.controlador.Consumer;
import es.rfgr.cr4vegas.modelo.postgresql.dto.almacen.taquilla.Taquilla;
import es.rfgr.cr4vegas.modelo.postgresql.dto.almacen.taquilla.TaquillaPK;
import es.rfgr.cr4vegas.modelo.postgresql.dto.almacen.taquilla.TipoTaquilla;
import es.rfgr.cr4vegas.modelo.postgresql.exceptions.DAOException;
import es.rfgr.cr4vegas.vista.utileria.Dialogos;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class VentanaManipulaTaquillaControlador {

	@FXML
	private Label lbID;

	@FXML
	private CheckBox cbxActivo;

	@FXML
	private Button btAceptar;

	@FXML
	private AnchorPane panelRaiz;

	@FXML
	private TextField tfLugar;

	@FXML
	private ComboBox<TipoTaquilla> cbTipo;

	@FXML
	private Button btCancelar;

	private Consumer app;
	private Stage escenarioVentana;	
	private String titulo;
	private boolean agregar;
	private ObservableList<TipoTaquilla> listaTaquillas;
	private Taquilla taquilla;
	private TaquillaPK taquillaPK;

	public AnchorPane getPanelRaiz() {
		return panelRaiz;
	}

	public void setConsumer(Consumer app) {
		this.app = app;
	}

	public void iniciar() {
		listaTaquillas = app.getTipoTaquillaDAO().getTiposTaquilla();

		cbTipo.setItems(listaTaquillas);
		
		btAceptar.setOnAction(e -> aceptarOperacion());
		btCancelar.setOnAction(e -> salir());
	}
	
	public void inciarCon(Taquilla taquilla) {
		if (taquilla == null) {
			iniciarAgregar();
			titulo = "AGREGAR ALMACEN";
		} else {
			iniciarModificar(taquilla);
			titulo = "MODIFICAR ALMACEN";
		}
		
		escenarioVentana = app.getVistaModelo().getEscenario(panelRaiz, titulo);
		escenarioVentana.setOnCloseRequest(e -> salir());
		escenarioVentana.showAndWait();
	}
	
	private void iniciarAgregar() {
		agregar = true;
		taquilla = new Taquilla();
	}
	
	private void iniciarModificar(Taquilla taquilla) {
		agregar = false;
		this.taquilla = new Taquilla(taquilla);
		taquillaPK = taquilla.createPK();
	}
	
	private void reiniciarCampos() {
		taquilla = null;
		lbID.setText("");
		tfLugar.setText("");
		cbTipo.getSelectionModel().clearSelection();
		cbxActivo.setSelected(false);
	}
	
	private void aceptarOperacion() {
		if (agregar) {
			aceptarAgregar();
		} else {
			aceptarModificar();
		}
		salir();
	}
	
	private void aceptarAgregar() {
		try {
			modificarEstadoTaquillaConCampos();
			app.getTaquillaDAO().insert(taquilla);
		} catch (DAOException e) {
			Dialogos.mostrarDialogoExcepcion(e, escenarioVentana);
		}
	}
	
	private void aceptarModificar() {
		try {
			modificarEstadoTaquillaConCampos();
			app.getTaquillaDAO().update(taquillaPK, taquilla);
		} catch (DAOException e) {
			Dialogos.mostrarDialogoExcepcion(e, escenarioVentana);
		}
	}
	
	private void modificarEstadoTaquillaConCampos() {
		taquilla.setActivo(cbxActivo.isSelected());
		taquilla.setObjTipoTaquilla(cbTipo.getSelectionModel().getSelectedItem());
		taquilla.setLugar(tfLugar.getText());
	}
	
	private void salir() {
		reiniciarCampos();
		escenarioVentana.close();
	}

}
