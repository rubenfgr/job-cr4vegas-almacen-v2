package es.rfgr.cr4vegas.vista.fxml.controlador.ventana.inventario;

import es.rfgr.cr4vegas.controlador.Consumer;
import es.rfgr.cr4vegas.modelo.postgresql.dto.almacen.material.Familia;
import es.rfgr.cr4vegas.modelo.postgresql.dto.almacen.material.Material;
import es.rfgr.cr4vegas.modelo.postgresql.dto.almacen.material.TipoMaterial;
import es.rfgr.cr4vegas.modelo.postgresql.dto.almacen.material.Ubicacion;
import es.rfgr.cr4vegas.utileria.ImagenUnica;
import es.rfgr.cr4vegas.vista.utileria.ConversorImagenes;
import es.rfgr.cr4vegas.vista.utileria.Dialogos;
import es.rfgr.cr4vegas.vista.utileria.ImagenesPorDefecto;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class VentanaManipulaMaterialControlador implements ImagenUnica {

	@FXML
	private AnchorPane panelRaiz;

	@FXML
	private CheckBox cbActivo;

	@FXML
	private TextField tfNombreTecnico;

	@FXML
	private TextField tfNombre;

    @FXML
    private TextField tfCantidad;
    
	@FXML
	private TextField tfCantidadMinima;

	@FXML
	private ComboBox<Ubicacion> cbxUbicacion;

	@FXML
	private ComboBox<Familia> cbxFamilia;

	@FXML
	private ComboBox<TipoMaterial> cbxTipoMaterial;

	@FXML
	private TextField tfDiametro;

	@FXML
	private TextField tfPN;

	@FXML
	private TextField tfMarca;

	@FXML
	private Button btAgregarImagen;

	@FXML
	private ImageView ivImagen;

	@FXML
	private Button btAceptar;

	@FXML
	private Button btCancelar;

	private Consumer app;
	private Stage escenarioVentana;
	
	private Material materialInicial;
	private boolean modificar;
	private String titulo;
	
	private Image imagen;
	private byte[] imagenBytes;

	public void setConsumer(Consumer app) {
		this.app = app;
	}

	public AnchorPane getPanelRaiz() {
		return panelRaiz;
	}

	public void iniciar() {
		cbActivo.setSelected(true);

		btAceptar.setOnAction(e -> manejadorAceptar());
		btCancelar.setOnAction(e -> manejadorCancelar());
		btAgregarImagen.setOnAction(e -> app.getVistaModelo().getModeloVentana().getVentanaBuscarImagenControlador().iniciarCon(this));
		cargarCheckBoxs();
	}

	public void iniciarCon(Material material) {
		iniciarComponentes(material);
		
		escenarioVentana = app.getVistaModelo().getEscenario(panelRaiz, titulo);
		escenarioVentana.showAndWait();
	}

	public void iniciarComponentes(Material material) {
		if (material != null) {
			titulo = "MODIFICAR MATERIAL";
			modificar = true;
			materialInicial = material;
			iniciarConMaterial();
		} else {
			titulo = "AGREGAR MATERIAL";
			modificar = false;
			materialInicial = new Material();
			iniciarSinMaterial();
		}
	}

	private void iniciarConMaterial() {
		cbActivo.setSelected(materialInicial.isActivo());
		tfNombreTecnico.setText(materialInicial.getNombreTec());
		tfNombre.setText(materialInicial.getNombre());
		tfCantidad.setText(String.valueOf(materialInicial.getCantidad()));
		tfCantidadMinima.setText(String.valueOf(materialInicial.getCantidadMin()));
		seleccionarUbicacion(materialInicial.getObjUbicacion());
		seleccionarTipoMaterial(materialInicial.getObjTipoMaterial());
		seleccionarFamilia(materialInicial.getObjFamilia());
		tfDiametro.setText(materialInicial.getDiametro());
		tfPN.setText(materialInicial.getPn());
		tfMarca.setText(materialInicial.getMarca());
		if (materialInicial.getImagenBytes() != null) {
			ivImagen.setImage(ConversorImagenes.byteArrayToImage(materialInicial.getImagenBytes()));
			imagen = ivImagen.getImage();
			imagenBytes = materialInicial.getImagenBytes();
		} else {
			ivImagen.setImage(ImagenesPorDefecto.getImgDefaultMaterial());
			imagen = ivImagen.getImage();
			imagenBytes = ConversorImagenes.toByteArray(ImagenesPorDefecto.getRutaimagenmaterialraiz());
		}

	}

	private void iniciarSinMaterial() {
		cbActivo.setSelected(true);
		tfNombreTecnico.setText("");
		tfNombre.setText("");
		tfCantidadMinima.setText("");
		tfCantidad.setText("");
		seleccionarUbicacion(null);
		seleccionarTipoMaterial(null);
		seleccionarFamilia(null);
		tfDiametro.setText("");
		tfPN.setText("");
		tfMarca.setText("");
		ivImagen.setImage(ImagenesPorDefecto.getImgDefaultMaterial());
		imagen = ivImagen.getImage();
		imagenBytes = ConversorImagenes.toByteArray(ImagenesPorDefecto.getRutaimagenmaterialraiz());
	}

	private void cargarCheckBoxs() {
		cbxUbicacion.setItems(app.getUbicacionDAO().getUbicaciones());
		cbxTipoMaterial.setItems(app.getTipoMaterialDAO().getTiposMaterial());
		cbxFamilia.setItems(app.getFamiliaDAO().getFamilias());
	}

	private void seleccionarUbicacion(Ubicacion ubicacion) {
		if (ubicacion != null) {
			if (cbxUbicacion.getItems().contains(ubicacion)) {
				int indice = cbxUbicacion.getItems().indexOf(ubicacion);
				cbxUbicacion.getSelectionModel().select(indice);
			}
		} else {
			cbxUbicacion.getSelectionModel().clearSelection();
		}
	}

	private void seleccionarTipoMaterial(TipoMaterial tipoMaterial) {
		if (tipoMaterial != null) {
			if (cbxTipoMaterial.getItems().contains(tipoMaterial)) {
				int indice = cbxTipoMaterial.getItems().indexOf(tipoMaterial);
				cbxTipoMaterial.getSelectionModel().select(indice);
			}
		} else {
			cbxTipoMaterial.getSelectionModel().clearSelection();
		}
	}

	private void seleccionarFamilia(Familia familia) {
		if (familia != null) {
			if (cbxFamilia.getItems().contains(familia)) {
				cbxFamilia.getSelectionModel().select(familia);
			}
		} else {
			cbxFamilia.getSelectionModel().clearSelection();
		}
	}

	private void manejadorAceptar() {
		try {
			actualizarMaterial();
			if (!modificar) {
				app.getMaterialDAO().insert(materialInicial);
			} else {
				app.getMaterialDAO().update(materialInicial.createPK(), materialInicial);
			}
			escenarioVentana.close();
		} catch (Exception e) {
			e.printStackTrace();
			Dialogos.mostrarDialogoError(e.getMessage(), escenarioVentana);
		}

	}

	private void manejadorCancelar() {
		escenarioVentana.close();
	}

	private Material actualizarMaterial() {
		materialInicial.setActivo(cbActivo.isSelected());
		materialInicial.setNombre(tfNombre.getText());
		materialInicial.setNombreTec(tfNombreTecnico.getText());
		materialInicial.setCantidad(tfCantidad.getText());
		materialInicial.setCantidadMin(tfCantidadMinima.getText());
		materialInicial.setObjUbicacion(cbxUbicacion.getSelectionModel().getSelectedItem());
		materialInicial.setObjFamilia(cbxFamilia.getSelectionModel().getSelectedItem());
		materialInicial.setObjTipoMaterial(cbxTipoMaterial.getSelectionModel().getSelectedItem());
		materialInicial.setDiametro(tfDiametro.getText());
		materialInicial.setPn(tfPN.getText());
		materialInicial.setMarca(tfMarca.getText());
		materialInicial.setImagenBytes(imagenBytes);

		return materialInicial;
	}

	@Override
	public byte[] getImagenBytes() {
		return imagenBytes;
	}

	@Override
	public void setImagenBytes(byte[] imagenBytes) {
		if (imagenBytes != null) {
			this.imagenBytes = imagenBytes;
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
			ivImagen.setImage(imagen);
		}
	}
}
