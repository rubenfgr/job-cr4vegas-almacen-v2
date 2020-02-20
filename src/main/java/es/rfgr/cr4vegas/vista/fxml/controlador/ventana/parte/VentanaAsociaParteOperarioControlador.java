package es.rfgr.cr4vegas.vista.fxml.controlador.ventana.parte;

import java.time.LocalDateTime;

import es.rfgr.cr4vegas.controlador.Consumer;
import es.rfgr.cr4vegas.modelo.postgresql.dto.operario.Operario;
import es.rfgr.cr4vegas.modelo.postgresql.dto.parte.AsociaParteOperario;
import es.rfgr.cr4vegas.modelo.postgresql.dto.parte.Parte;
import es.rfgr.cr4vegas.vista.utileria.Dialogos;
import es.rfgr.cr4vegas.vista.utileria.Filtrador;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class VentanaAsociaParteOperarioControlador {

	@FXML
	private ListView<Operario> lvOperario;

	@FXML
	private Label lbAsociaValor;

	@FXML
	private TableView<AsociaParteOperario> tvOperariosAsociados;

	@FXML
	private TableColumn<AsociaParteOperario, LocalDateTime> tcFecha;

	@FXML
	private TableColumn<AsociaParteOperario, Operario> tcOperario;

	@FXML
	private Button btAceptar;

	@FXML
	private AnchorPane panelRaiz;

	@FXML
	private Label lbAsociaID;

	@FXML
	private TextField tfBuscar;

	@FXML
	private Button btEliminar;

	@FXML
	private Button btCancelar;

	private Consumer app;
	private Stage escenarioVentana;
	private ObservableList<Operario> listaOperarios;
	private ObservableList<AsociaParteOperario> listaOperariosAsociados;
	private ObservableList<AsociaParteOperario> listaOperariosAsocidadosCopia;
	private Parte parte;

	public void setConsumer(Consumer app) {
		this.app = app;
	}

	public AnchorPane getPanelRaiz() {
		return panelRaiz;
	}

	public void iniciar() {
		listaOperarios = app.getOperarioDAO().getOperarios();
		listaOperariosAsociados = FXCollections.observableArrayList();
		listaOperariosAsocidadosCopia = FXCollections.observableArrayList();

		lvOperario.setItems(listaOperarios);
		lvOperario.setOnMouseClicked(e -> agregarAsociado(e));
		
		tvOperariosAsociados.setItems(listaOperariosAsociados);
		tcOperario.setCellValueFactory(e -> e.getValue().objOperarioProperty());
		tcFecha.setCellValueFactory(e -> e.getValue().fechaProperty());

		btAceptar.setOnAction(e -> aceptar());
		btCancelar.setOnAction(e -> cancelar());
		btEliminar.setOnAction(e -> eliminar());
	}

	public void iniciarCon(Parte parte) {
		this.parte = parte;
		lbAsociaValor.setText("" + parte.getCodParte());
		for (AsociaParteOperario asociado : app.getAsociaParteOperarioDAO().getParteOperarios(parte)) {
			listaOperariosAsociados.add(new AsociaParteOperario(asociado));
			listaOperariosAsocidadosCopia.add(new AsociaParteOperario(asociado));
		}

		Filtrador.filtrarListasGenericas(listaOperarios, lvOperario, tfBuscar);

		escenarioVentana = app.getVistaModelo().getEscenario(panelRaiz, "ASOCIAR OPERARIOS");
		escenarioVentana.show();
	}
	
	private void agregarAsociado(MouseEvent e) {
		if (e.getClickCount() == 2) {
			Operario operario = lvOperario.getSelectionModel().getSelectedItem();
			AsociaParteOperario asociado = new AsociaParteOperario();
			asociado.setObjParte(parte);
			asociado.setObjOperario(operario);
			asociado.setFecha(LocalDateTime.now());
			if (listaOperariosAsociados.contains(asociado)) {
				Dialogos.mostrarDialogoAdvertencia("Este operario ya se está asociado.", escenarioVentana);
			} else {
				listaOperariosAsociados.add(asociado);
			}
		}
	}

	private void eliminar() {
		try {
			AsociaParteOperario asociado = tvOperariosAsociados.getSelectionModel().getSelectedItem();
			if (asociado == null) {
				Dialogos.mostrarDialogoAdvertencia("Debe seleccionar el operario asociado que desea eliminar",
						escenarioVentana);
			} else {
				listaOperariosAsociados.remove(asociado);
			}
		} catch (Exception e) {
			Dialogos.mostrarDialogoExcepcion(e, escenarioVentana);
		}
	}

	private void aceptar() {
		try {
			for (AsociaParteOperario asociadoCopia : listaOperariosAsocidadosCopia) {
				if (!listaOperariosAsociados.contains(asociadoCopia)) {
					app.getAsociaParteOperarioDAO().delete(asociadoCopia.createPK());
				}
			}
			for (AsociaParteOperario asociado : listaOperariosAsociados) {
				if (!listaOperariosAsocidadosCopia.contains(asociado)) {
					app.getAsociaParteOperarioDAO().insert(asociado);
				} else {
					app.getAsociaParteOperarioDAO().update(asociado.createPK(), asociado);
				}
			}
		} catch (Exception e) {
			Dialogos.mostrarDialogoExcepcion(e, escenarioVentana);
		}
		cancelar();
	}

	private void cancelar() {
		reiniciarCampos();
		escenarioVentana.close();
	}

	private void reiniciarCampos() {
		tfBuscar.setText("");
		listaOperariosAsociados.clear();
		listaOperariosAsocidadosCopia.clear();
		lbAsociaValor.setText("");
	}
}
