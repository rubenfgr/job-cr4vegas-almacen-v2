package es.rfgr.cr4vegas.vista.fxml.controlador.ventana.parte;

import java.time.LocalDateTime;

import es.rfgr.cr4vegas.controlador.Consumer;
import es.rfgr.cr4vegas.modelo.postgresql.dto.grupo.Grupo;
import es.rfgr.cr4vegas.modelo.postgresql.dto.parte.Parte;
import es.rfgr.cr4vegas.modelo.postgresql.dto.parte.PartePK;
import es.rfgr.cr4vegas.modelo.postgresql.dto.parte.TipoParte;
import es.rfgr.cr4vegas.modelo.postgresql.dto.parteoficial.ParteOficial;
import es.rfgr.cr4vegas.modelo.postgresql.dto.parteoficial.ParteOficialPK;
import es.rfgr.cr4vegas.utileria.Origen;
import es.rfgr.cr4vegas.utileria.Prioridad;
import es.rfgr.cr4vegas.vista.utileria.Dialogos;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class VentanaManipulaParteControlador {

	@FXML
	private TextField tfFecha;

	@FXML
	private CheckBox cbxEstado;

	@FXML
	private Button btAceptar;

	@FXML
	private AnchorPane panelRaiz;

	@FXML
	private TextArea taDescripcion;

	@FXML
	private TextField tfTiempoEstimado;

	@FXML
	private TextField tfCodigo;

	@FXML
	private TextField tfTelefono;

	@FXML
	private ComboBox<Grupo> cbGrupo;

	@FXML
	private ComboBox<Prioridad> cbPrioridad;

	@FXML
	private CheckBox cbxImpreso;

	@FXML
	private ComboBox<TipoParte> cbTipo;

	@FXML
	private TextField tfLlamo;

	@FXML
	private ComboBox<Origen> cbOrigen;

	@FXML
	private TextField tfContador;

	@FXML
	private TextArea taOrden;

	@FXML
	private TextField tfParteOficial;

	@FXML
	private Button btCancelar;

	private Consumer app;
	private Stage escenarioVentana;
	private String titulo;

	private boolean agregar;
	private Parte parte;
	private PartePK partePK;
	private ParteOficial parteOficial;
	private ParteOficialPK parteOficialPK;

	public void setConsumer(Consumer app) {
		this.app = app;
	}

	public AnchorPane getPanelRaiz() {
		return panelRaiz;
	}

	public void iniciar() {
		cbGrupo.setItems(app.getGrupoDAO().getGrupos());
		cbOrigen.setItems(FXCollections.observableArrayList(Origen.values()));
		cbPrioridad.setItems(FXCollections.observableArrayList(Prioridad.values()));
		cbTipo.setItems(app.getTipoParteDAO().getTiposParte());

		btAceptar.setOnAction(e -> aceptar());
		btCancelar.setOnAction(e -> salir());
	}

	public void iniciarCon(Parte parte) {
		if (parte == null) {
			iniciarAgregarParte();
		} else {
			iniciarModificarParte(parte);
		}
		escenarioVentana = app.getVistaModelo().getEscenario(panelRaiz, titulo);
		escenarioVentana.showAndWait();
	}

	private void iniciarAgregarParte() {
		agregar = true;
		titulo = "AGREGAR PARTE";
		parte = new Parte();
		parteOficial = new ParteOficial();
		tfFecha.setText(LocalDateTime.now().toString());
	}

	private void iniciarModificarParte(Parte parte) {
		agregar = false;
		titulo = "MODIFICAR PARTE";
		this.parte = new Parte(parte);
		partePK = parte.createPK();
		
		if (parte.getObjParteOficial() != null) {
			this.parteOficial = new ParteOficial(parte.getObjParteOficial());
			parteOficialPK = parteOficial.createPK();
		} else {
			parte = new Parte();
			parteOficial = new ParteOficial();
		}
		
		rellenarCamposConParte();
	}

	private void rellenarCamposConParte() {
		tfCodigo.setText(String.valueOf(parte.getCodParte()));
		tfContador.setText(parte.getContador());
		tfFecha.setText(parte.getFecha().toString());
		tfLlamo.setText(parte.getLlamo());
		tfParteOficial.setText(String.valueOf(parte.getParteOficial()));
		tfTelefono.setText(parte.getTelefono());
		tfTiempoEstimado.setText(parte.getEstimado());
		taDescripcion.setText(parte.getDescripcion());
		taOrden.setText(parte.getOrden());
		
		cbxEstado.setSelected(parte.isEstado());
		cbxImpreso.setSelected(parte.isImpreso());
		
		cbGrupo.getSelectionModel().select(parte.getObjGrupo());
		cbOrigen.getSelectionModel().select(parte.getOrigen() == null? Origen.NINGUNO : parte.getOrigen());
		cbPrioridad.getSelectionModel().select(parte.getPrioridad() == null? Prioridad.NINGUNA : parte.getPrioridad());
		cbTipo.getSelectionModel().select(parte.getObjTipoParte());
	}

	private void reiniciarCampos() {
		tfCodigo.setText("");
		tfContador.setText("");
		tfFecha.setText("");
		tfLlamo.setText("");
		tfParteOficial.setText("");
		tfTelefono.setText("");
		tfTiempoEstimado.setText("");
		taDescripcion.setText("");
		taOrden.setText("");
		
		cbxEstado.setSelected(false);
		cbxImpreso.setSelected(false);
		
		cbGrupo.getSelectionModel().clearSelection();
		cbOrigen.getSelectionModel().clearSelection();
		cbPrioridad.getSelectionModel().clearSelection();
		cbTipo.getSelectionModel().clearSelection();
		
		parte = null;
		partePK = null;
		parteOficial = null;
		parteOficialPK = null;
		
	}

	private void aceptar() {
		try {
			if (agregar) {
				aceptarAgregarParte();
			} else {
				aceptarModificarParte();
			}
		} catch (Exception e) {
			Dialogos.mostrarDialogoExcepcion(e, escenarioVentana);
		}
		
		salir();
	}

	private void aceptarAgregarParte() throws Exception {
		cargarDatosEnParte();
		
		if (Dialogos.mostrarDialogoConfirmacion("¿Desea agregar el parte en la BD de la Oficina?", escenarioVentana)) {
			parteOficial.copiarValoresDeParteAlmacen(parte);
			app.getParteOficialDAO().insert(parteOficial);

			parte.setObjParteOficial(parteOficial);
		}

		app.getParteDAO().insert(parte);
	}

	private void aceptarModificarParte() throws Exception {
		cargarDatosEnParte();
		
		if (Dialogos.mostrarDialogoConfirmacion("¿Desea actualizar el parte en la BD de la Oficina?", escenarioVentana)) {
			parteOficial.copiarValoresDeParteAlmacen(parte);
			app.getParteOficialDAO().update(parteOficialPK, parteOficial);
		}
		
		app.getParteDAO().update(partePK, parte);
	}

	private void cargarDatosEnParte() {
		parte.setContador(tfContador.getText());
		parte.setDescripcion(taDescripcion.getText());
		parte.setEstado(cbxEstado.isSelected());
		parte.setEstimado(tfTiempoEstimado.getText());
		parte.setFecha(tfFecha.getText());
		parte.setObjGrupo(cbGrupo.getSelectionModel().getSelectedItem());
		parte.setImpreso(cbxImpreso.isSelected());
		parte.setLlamo(tfLlamo.getText());
		parte.setObjTipoParte(cbTipo.getSelectionModel().getSelectedItem());
		parte.setOrden(taOrden.getText());
		parte.setOrigen(cbOrigen.getSelectionModel().getSelectedItem());
		parte.setPrioridad(cbPrioridad.getSelectionModel().getSelectedItem());
		parte.setTelefono(tfTelefono.getText());
		parte.setTipo(cbTipo.getSelectionModel().getSelectedItem().getCodigo());
	}

	private void salir() {
		reiniciarCampos();
		escenarioVentana.close();
	}
}
