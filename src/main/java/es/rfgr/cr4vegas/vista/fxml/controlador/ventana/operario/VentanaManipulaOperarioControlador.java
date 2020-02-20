package es.rfgr.cr4vegas.vista.fxml.controlador.ventana.operario;

import es.rfgr.cr4vegas.controlador.Consumer;
import es.rfgr.cr4vegas.modelo.postgresql.dto.grupo.Grupo;
import es.rfgr.cr4vegas.modelo.postgresql.dto.operario.DirOperario;
import es.rfgr.cr4vegas.modelo.postgresql.dto.operario.DirOperarioPK;
import es.rfgr.cr4vegas.modelo.postgresql.dto.operario.Operario;
import es.rfgr.cr4vegas.modelo.postgresql.dto.operario.OperarioPK;
import es.rfgr.cr4vegas.modelo.postgresql.dto.operario.TelOperario;
import es.rfgr.cr4vegas.utileria.ImagenUnica;
import es.rfgr.cr4vegas.vista.utileria.ConversorImagenes;
import es.rfgr.cr4vegas.vista.utileria.Dialogos;
import es.rfgr.cr4vegas.vista.utileria.ImagenesPorDefecto;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class VentanaManipulaOperarioControlador implements ImagenUnica {

	@FXML
	private AnchorPane panelRaiz;

	@FXML
	private CheckBox cbActivo;

	@FXML
	private TextField tfCodigo;

	@FXML
	private TextField tfNombre;

	@FXML
	private TextField tfApellidoUno;

	@FXML
	private ComboBox<Grupo> cbxGrupo;

	@FXML
	private ChoiceBox<String> cbxCalzado;

	@FXML
	private ChoiceBox<String> cbxPantalonLargo;

	@FXML
	private ChoiceBox<String> cbxPantalonCorto;

	@FXML
	private ChoiceBox<String> cbxChaqueta;

	@FXML
	private ChoiceBox<String> cbxChaqueton;

	@FXML
	private ChoiceBox<String> cbxForroPolar;

	@FXML
	private ChoiceBox<String> cbxPolo;

	@FXML
	private TextField tfApellidoDos;

	@FXML
	private ImageView ivFoto;

	@FXML
	private Button btAgregarFoto;

	@FXML
	private ListView<TelOperario> lvTelefonos;

	@FXML
	private TextField tfLocalidad;

	@FXML
	private TextField tfCalle;

	@FXML
	private TextField tfNombre3;

	@FXML
	private TextField tfNombre4;

	@FXML
	private TextField tfNumPlanta;

	@FXML
	private TextField tfCP;

	@FXML
	private Button btAgregarTelefono;

	@FXML
	private TextField tfTelefono;

	@FXML
	private Button btBorrarTelefono;

	@FXML
	private TextField tfPlanta;

	@FXML
	private TextField tfNumero;

	@FXML
	private Button btAceptar;

	@FXML
	private Button btCancelar;

	private Consumer app;
	private boolean agregar;
	private Operario operario;
	private OperarioPK operarioPK;
	private ObservableList<TelOperario> listaTelefonos;
	private ObservableList<TelOperario> listaTelefonosCopia;
	private DirOperario dirOperario;
	private DirOperarioPK dirOperarioPK;
	private ObservableList<String> listaTallas;
	private ObservableList<String> listaTallasCalzado;
	private Stage escenarioVentana;
	private byte[] imagenBytes;
	private Image imagen;

	public void setConsumer(Consumer app) {
		this.app = app;
	}

	public AnchorPane getPanelRaiz() {
		return panelRaiz;
	}

	public void iniciar() {
		listaTallas = FXCollections.observableArrayList();
		listaTallasCalzado = FXCollections.observableArrayList();
		listaTallas.addAll("S", "M", "L", "XL", "XXL", "XXXL", "XXXXL", "38-40", "42-44", "46-48", "50-52", "54-56",
				"58-60");
		listaTallasCalzado.addAll("36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "48");

		btAceptar.setOnAction(e -> handleAceptarOperacion());
		btCancelar.setOnAction(e -> salir());
		btAgregarFoto.setOnAction(e -> agregarImagen());

		btAgregarTelefono.setOnAction(e -> agregarTelefono());
		btBorrarTelefono.setOnAction(e -> borrarTelefono());

		listaTelefonos = FXCollections.observableArrayList();
		listaTelefonosCopia = FXCollections.observableArrayList();

		lvTelefonos.setItems(listaTelefonos);

		cbxGrupo.setItems(app.getGrupoDAO().getGrupos());
		cbxCalzado.setItems(listaTallasCalzado);
		cbxChaqueta.setItems(listaTallas);
		cbxChaqueton.setItems(listaTallas);
		cbxForroPolar.setItems(listaTallas);
		cbxPantalonCorto.setItems(listaTallas);
		cbxPantalonLargo.setItems(listaTallas);
		cbxPolo.setItems(listaTallas);

	}

	public void iniciarCon(Operario operario) {
		String titulo = "";

		if (operario == null) {
			iniciarAgregarOperario();
			titulo = "AGREGAR OPERARIO";

		} else {
			iniciarModificarOperario(operario);
			titulo = "MODIFICAR OPERARIO";
		}
		escenarioVentana = app.getVistaModelo().getEscenario(panelRaiz, titulo);
		escenarioVentana.setOnCloseRequest(e -> cerrarEscenarioYReiniciarCampos());
		escenarioVentana.showAndWait();
	}

	private void iniciarAgregarOperario() {
		agregar = true;
		operario = new Operario();
		dirOperario = new DirOperario();
		dirOperario.setObjOperario(operario);
		ivFoto.setImage(ImagenesPorDefecto.getImgDefaultOperario());
		imagen = ivFoto.getImage();
		imagenBytes = ConversorImagenes.toByteArray(ImagenesPorDefecto.getRutaimagenoperarioraiz());
	}

	private void iniciarModificarOperario(Operario operario) {
		agregar = false;
		this.operario = new Operario(operario);
		this.operarioPK = operario.createPK();
		if (app.getDirOperarioDAO().getDirOperario(operario) == null) {
			this.dirOperario = new DirOperario();
			this.dirOperario.setObjOperario(operario);
		} else {
			this.dirOperario = new DirOperario(app.getDirOperarioDAO().getDirOperario(operario));
			this.dirOperarioPK = dirOperario.createPK();
		}

		for (TelOperario telOperario : app.getTelOperarioDAO().getOperarioTelefonos(operario)) {
			listaTelefonos.add(new TelOperario(telOperario));
			listaTelefonosCopia.add(new TelOperario(telOperario));
		}

		ivFoto.setImage(ConversorImagenes.byteArrayToImage(operario.getImagenBytes()));
		imagen = ivFoto.getImage();
		imagenBytes = operario.getImagenBytes();

		iniciarCamposModificarOperario();
	}

	private void iniciarCamposModificarOperario() {
		tfCodigo.setText(String.valueOf(operario.getCodOp()));
		cbActivo.setSelected(true);
		tfNombre.setText(operario.getNombre());
		tfApellidoUno.setText(operario.getApellido1());
		tfApellidoDos.setText(operario.getApellido2());
		cbxGrupo.getSelectionModel().select(operario.getObjGrupo());
		cbxCalzado.getSelectionModel().select(operario.getTCalzado());
		cbxPantalonLargo.getSelectionModel().select(operario.getTPantLargo());
		cbxPantalonCorto.getSelectionModel().select(operario.getTPantCorto());
		cbxPolo.getSelectionModel().select(operario.getTPolo());
		if (operario.getImagenBytes() != null) {
			ivFoto.setImage(ConversorImagenes.byteArrayToImage(operario.getImagenBytes()));
		}

		tfLocalidad.setText(dirOperario.getLocalidad());
		tfCalle.setText(dirOperario.getCalle());
		tfNumero.setText(String.valueOf(dirOperario.getNumero()));
		tfPlanta.setText(String.valueOf(dirOperario.getPlanta()));
		tfNumPlanta.setText(dirOperario.getNumPlanta());
		tfCP.setText(dirOperario.getCp());
	}

	private void borrarTelefono() {
		TelOperario telOperario = lvTelefonos.getSelectionModel().getSelectedItem();
		if (telOperario != null) {
			lvTelefonos.getItems().remove(telOperario);
		}
	}

	private void agregarTelefono() {
		try {
			TelOperario telOperario = new TelOperario();
			telOperario.setTelefono(tfTelefono.getText());
			listaTelefonos.add(telOperario);
			tfTelefono.setText("");

		} catch (Exception e) {
			Dialogos.mostrarDialogoExcepcion(e, escenarioVentana);
		}
	}

	private void agregarImagen() {
		app.getVistaModelo().getModeloVentana().getVentanaBuscarImagenControlador().iniciarCon(this);
	}

	private void handleAceptarOperacion() {
		if (agregar) {
			aceptarAgregarOperario();

		} else {
			aceptarModificarOperario();
		}
		salir();
	}

	private void aceptarAgregarOperario() {
		try {
			cargarEstadoOperarioConCampos();

			agregarOperario();

		} catch (Exception e) {
			Dialogos.mostrarDialogoExcepcion(e, escenarioVentana);
		}
	}

	private void agregarOperario() throws Exception {
		operarioPK = app.getOperarioDAO().insert(operario);

		dirOperario.setObjOperario(operario);
		app.getDirOperarioDAO().insert(dirOperario);

		modificarTelefonosOperario();
		app.getTelOperarioDAO().insertAll(listaTelefonos);
	}

	private void aceptarModificarOperario() {
		try {
			cargarEstadoOperarioConCampos();

			modificarOperario();

		} catch (Exception e) {
			Dialogos.mostrarDialogoExcepcion(e, escenarioVentana);
		}
	}

	private void modificarOperario() throws Exception {
		app.getOperarioDAO().update(operarioPK, operario);

		dirOperario.setObjOperario(operario);

		if (dirOperarioPK == null) {
			app.getDirOperarioDAO().insert(dirOperario);
		} else {
			dirOperarioPK = dirOperario.createPK();
			app.getDirOperarioDAO().update(dirOperarioPK, dirOperario);
		}

		modificarTelefonosOperario();
		
		for (TelOperario tel : listaTelefonosCopia) {
			if (!listaTelefonos.contains(tel)) {
				app.getTelOperarioDAO().delete(tel.createPK());
			}
		}
		for (TelOperario tel : listaTelefonos) {
			if (!listaTelefonosCopia.contains(tel)) {
				app.getTelOperarioDAO().insert(tel);
			} else {
				app.getTelOperarioDAO().update(tel.createPK(), tel);
			}
		}
	}

	private void cargarEstadoOperarioConCampos() throws Exception {
		operario.setCodOp(tfCodigo.getText());
		operario.setNombre(tfNombre.getText());
		operario.setApellido1(tfApellidoUno.getText());
		operario.setApellido2(tfApellidoDos.getText());
		operario.setObjGrupo(cbxGrupo.getSelectionModel().getSelectedItem());
		operario.setTCalzado(cbxCalzado.getSelectionModel().getSelectedItem());
		operario.setTPantLargo(cbxPantalonLargo.getSelectionModel().getSelectedItem());
		operario.setTPantCorto(cbxPantalonCorto.getSelectionModel().getSelectedItem());
		operario.setTChaqueta(cbxChaqueta.getSelectionModel().getSelectedItem());
		operario.setTChaqueton(cbxChaqueton.getSelectionModel().getSelectedItem());
		operario.setTForro(cbxForroPolar.getSelectionModel().getSelectedItem());
		operario.setTPolo(cbxPolo.getSelectionModel().getSelectedItem());
		operario.setImagenBytes(imagenBytes);

		dirOperario.setLocalidad(tfLocalidad.getText());
		dirOperario.setCalle(tfCalle.getText());
		dirOperario.setNumero(tfNumero.getText());
		dirOperario.setPlanta(tfPlanta.getText());
		dirOperario.setNumPlanta(tfNumPlanta.getText());
		dirOperario.setCp(tfCP.getText());
	}

	private void modificarTelefonosOperario() {
		for (TelOperario tel : listaTelefonos) {
			tel.setObjOperario(operario);
		}
		for (TelOperario tel : listaTelefonosCopia) {
			tel.setObjOperario(operario);
		}
	}

	private void cerrarEscenarioYReiniciarCampos() {
		escenarioVentana.close();
		reiniciarCampos();
	}

	private void reiniciarCampos() {
		tfCodigo.setText("");
		cbActivo.setSelected(false);
		tfNombre.setText("");
		tfApellidoUno.setText("");
		tfApellidoDos.setText("");
		cbxGrupo.getSelectionModel().clearSelection();
		cbxCalzado.getSelectionModel().clearSelection();
		cbxPantalonLargo.getSelectionModel().clearSelection();
		cbxPantalonCorto.getSelectionModel().clearSelection();
		cbxPolo.getSelectionModel().clearSelection();

		tfLocalidad.setText("");
		tfCalle.setText("");
		tfNumero.setText("");
		tfPlanta.setText("");
		tfNumPlanta.setText("");
		tfCP.setText("");
		tfTelefono.setText("");

		operario = null;
		operarioPK = null;
		dirOperario = null;
		dirOperarioPK = null;
		listaTelefonos.clear();
		listaTelefonosCopia.clear();
	}

	private void salir() {
		reiniciarCampos();
		escenarioVentana.close();
	}

	@Override
	public byte[] getImagenBytes() {
		return imagenBytes;
	}

	@Override
	public void setImagenBytes(byte[] imagen) {
		if (imagen != null) {
			this.imagenBytes = imagen;
		}
	}

	@Override
	public Image getImagen() {
		return imagen;
	}

	@Override
	public void setImagen(Image imagen) {
		if (imagen != null) {
			this.imagen = imagen;
			ivFoto.setImage(imagen);
		}
	}
}
