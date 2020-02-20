package es.rfgr.cr4vegas.vista.fxml.controlador.principal.modulo.operario;

import java.time.LocalDateTime;

import es.rfgr.cr4vegas.controlador.Consumer;
import es.rfgr.cr4vegas.modelo.postgresql.dto.operario.DirOperario;
import es.rfgr.cr4vegas.modelo.postgresql.dto.operario.OperaOperarioMaterial;
import es.rfgr.cr4vegas.modelo.postgresql.dto.operario.Operario;
import es.rfgr.cr4vegas.modelo.postgresql.dto.operario.TelOperario;
import es.rfgr.cr4vegas.vista.utileria.ConversorImagenes;
import es.rfgr.cr4vegas.vista.utileria.Filtrador;
import es.rfgr.cr4vegas.vista.utileria.ImagenesPorDefecto;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class OperarioPrincipalControlador {

	@FXML
	private AnchorPane panelRaiz;

	@FXML
	private TextField tfBuscarOperario;

	@FXML
	private Label lbTotalOperariosActivos;

	@FXML
	private TableView<Operario> tvListadoOperarios;

	@FXML
	private TableColumn<Operario, Integer> tcCodigo;

	@FXML
	private TableColumn<Operario, Boolean> tcActivo;

	@FXML
	private TableColumn<Operario, String> tcNombre;

	@FXML
	private TableColumn<Operario, String> tcApellido1;

	@FXML
	private TableColumn<Operario, String> tcApellido2;

	@FXML
	private TableColumn<Operario, String> tcGrupo;

	@FXML
	private ImageView ivFoto;

	@FXML
	private Label lbNombre;

	@FXML
	private Label lbCodigoOperario;

	@FXML
	private Label lbActivo;

	@FXML
	private Label lbGrupo;

	@FXML
	private Label lbTelefonos;

	@FXML
	private Label lbLocalidad;

	@FXML
	private Label lbCalle;

	@FXML
	private Label lbNumero;

	@FXML
	private Label lbPlanta;

	@FXML
	private Label lbNumeroPlanta;

	@FXML
	private Label lbCP;

	@FXML
	private Label lbTallaCalzado;

	@FXML
	private Label lbTallaPantalonLargo;

	@FXML
	private Label lbTallaPantalonCorto;

	@FXML
	private Label lbTallaForroPolar;

	@FXML
	private Label lbTallaChaqueton;

	@FXML
	private Label lbTallaPolo;

	@FXML
	private Label lbTallaChaqueta;

	@FXML
	private TextField tfBuscarMaterial;

	@FXML
	private TableView<OperaOperarioMaterial> tvOperarioMaterial;

	@FXML
	private TableColumn<OperaOperarioMaterial, String> tcMaterialNombre;

	@FXML
	private TableColumn<OperaOperarioMaterial, Integer> tcMaterialCantidad;

	@FXML
	private TableColumn<OperaOperarioMaterial, LocalDateTime> tcMaterialFecha;

	private Consumer app;
	private ObservableList<Operario> listaOperarios;
	private ObservableList<OperaOperarioMaterial> listaOperarioMaterial;

	public void setConsumer(Consumer app) {
		this.app = app;
	}

	public AnchorPane getPanelRaiz() {
		return panelRaiz;
	}

	public void iniciar() {
		panelRaiz.setOnMouseClicked(e -> tvListadoOperarios.getSelectionModel().clearSelection());

		listaOperarioMaterial = FXCollections.observableArrayList();
		listaOperarioMaterial.setAll(app.getOperaOperarioMaterialDAO().getOperaOperarioMaterial());

		tvListadoOperarios.setItems(listaOperarios);
		tcCodigo.setCellValueFactory(e -> e.getValue().codOpProperty().asObject());
		tcActivo.setCellValueFactory(e -> e.getValue().activoProperty());
		tcNombre.setCellValueFactory(e -> e.getValue().nombreProperty());
		tcApellido1.setCellValueFactory(e -> e.getValue().apellido1Property());
		tcApellido2.setCellValueFactory(e -> e.getValue().apellido2Property());
		tcGrupo.setCellValueFactory(e -> e.getValue().grupoProperty());
		tvListadoOperarios.getSelectionModel().selectedItemProperty()
				.addListener((ov, oldValue, newValue) -> actualizarCampos(newValue));

		tvOperarioMaterial.setItems(listaOperarioMaterial);

		tcMaterialNombre.setCellValueFactory(e -> e.getValue().getObjMaterial().nombreTecProperty());
		tcMaterialCantidad.setCellValueFactory(e -> e.getValue().cantidadProperty().asObject());
		tcMaterialFecha.setCellValueFactory(e -> e.getValue().fechaProperty());

		listaOperarios = app.getOperarioDAO().getOperarios();
		lbTotalOperariosActivos.setText(String.valueOf(listaOperarios.size()));
		actualizarCampos(null);
		Filtrador.filtrarTablasGenericas(listaOperarios, tvListadoOperarios, tfBuscarOperario);
	}

	public Operario getSelectedOperario() {
		return tvListadoOperarios.getSelectionModel().getSelectedItem();
	}

	public void actualizarCampos(Operario operario) {
		vaciarCampos();
		if (operario != null) {
			rellenarCampos(operario);
			listaOperarioMaterial.setAll(app.getOperaOperarioMaterialDAO().getOperarioMateriales(operario));
		} else {
			listaOperarioMaterial.setAll(app.getOperaOperarioMaterialDAO().getOperaOperarioMaterial());
		}
	}

	private void rellenarCampos(Operario operario) {
		ivFoto.setImage(ConversorImagenes.byteArrayToImage(operario.getImagenBytes()));
		lbNombre.setText(operario.getNombre());
		lbCodigoOperario.setText(String.valueOf(operario.getCodOp()));
		lbActivo.setText(String.valueOf(operario.isActivo()));
		lbGrupo.setText(operario.getGrupo());
		lbTallaCalzado.setText(operario.getTCalzado());
		lbTallaPantalonLargo.setText(operario.getTPantLargo());
		lbTallaPantalonCorto.setText(operario.getTPantCorto());
		lbTallaChaqueta.setText(operario.getTChaqueta());
		lbTallaChaqueton.setText(operario.getTChaqueton());
		lbTallaForroPolar.setText(operario.getTForro());
		lbTallaPolo.setText(operario.getTPolo());
		for (TelOperario to : app.getTelOperarioDAO().getOperarioTelefonos(operario)) {
			if (app.getTelOperarioDAO().getOperarioTelefonos(operario).indexOf(to) == 0) {
				lbTelefonos.setText(to.toString());
			} else {
				lbTelefonos.setText(lbTelefonos.getText() + ", " + to);
			}
		}
		DirOperario dirOperario = app.getDirOperarioDAO().getDirOperario(operario);
		if (dirOperario != null) {
			lbLocalidad.setText(dirOperario.getLocalidad());
			lbCalle.setText(dirOperario.getCalle());
			lbNumero.setText(String.valueOf(dirOperario.getNumero()));
			lbPlanta.setText(String.valueOf(dirOperario.getPlanta()));
			lbNumeroPlanta.setText(dirOperario.getNumPlanta());
			lbCP.setText(dirOperario.getCp());
		}
		Filtrador.filtrarTablasGenericas(listaOperarioMaterial, tvOperarioMaterial, tfBuscarMaterial);
	}

	private void vaciarCampos() {
		ivFoto.setImage(ImagenesPorDefecto.getImgDefaultOperario());
		lbNombre.setText("");
		lbCodigoOperario.setText("");
		lbActivo.setText("");
		lbGrupo.setText("");
		lbTallaCalzado.setText("");
		lbTallaPantalonLargo.setText("");
		lbTallaPantalonCorto.setText("");
		lbTallaChaqueta.setText("");
		lbTallaChaqueton.setText("");
		lbTallaForroPolar.setText("");
		lbTallaPolo.setText("");
		lbTelefonos.setText("");
		lbLocalidad.setText("");
		lbCalle.setText("");
		lbNumero.setText(String.valueOf(""));
		lbPlanta.setText(String.valueOf(""));
		lbNumeroPlanta.setText("");
		lbCP.setText("");
		
		refresh();
	}
	
	private void refresh() {
		tvListadoOperarios.refresh();
		tvOperarioMaterial.refresh();
	}
}
