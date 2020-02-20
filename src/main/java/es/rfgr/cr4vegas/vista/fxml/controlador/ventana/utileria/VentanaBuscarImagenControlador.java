package es.rfgr.cr4vegas.vista.fxml.controlador.ventana.utileria;

import java.io.File;

import es.rfgr.cr4vegas.controlador.Consumer;
import es.rfgr.cr4vegas.utileria.ImagenUnica;
import es.rfgr.cr4vegas.vista.utileria.ConversorImagenes;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class VentanaBuscarImagenControlador {

	@FXML
	private AnchorPane panelRaiz;

	@FXML
	private Button btBuscarImagen;

	@FXML
	private ImageView ivImagen;

	@FXML
	private Button btAceptar;

	@FXML
	private Button btCancelar;

	private Consumer app;
	private Stage escenarioVentana;

	private Image imagen;
	private String ruta;

	private ImagenUnica imagenUnica;

	public void setConsumer(Consumer app) {
		this.app = app;
	}

	public AnchorPane getPanelRaiz() {
		return panelRaiz;
	}

	public void iniciar() {
		btBuscarImagen.setOnAction(e -> seleccionarArchivo());
		btAceptar.setOnAction(e -> aceptar());
		btCancelar.setOnAction(e -> cerrarVentana());
	}

	public void iniciarCon(ImagenUnica imagenUnica) {
		this.imagenUnica = imagenUnica;
		imagen = imagenUnica.getImagen();
		ivImagen.fitWidthProperty().set(90);
		ivImagen.fitWidthProperty().set(120);

		ivImagen.setImage(imagen);

		abrirVentana();
	}

	private void abrirVentana() {
		escenarioVentana = app.getVistaModelo().getEscenario(panelRaiz, "BUSCAR IMAGEN");
		escenarioVentana.showAndWait();
	}

	public void cerrarVentana() {
		escenarioVentana.close();
	}

	private void seleccionarArchivo() {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Buscar Imagen");

		// Agregar filtros para facilitar la busqueda
		fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Todas las imagenes", "*.*"),
				new FileChooser.ExtensionFilter("JPG", "*.jpg"), new FileChooser.ExtensionFilter("PNG", ".png"));

		// Obtener la imagen seleccionada
		File imgFile = fileChooser.showOpenDialog(escenarioVentana);

		// Mostrar la imagen
		if (imgFile != null) {
			ruta = "file:" + imgFile.getAbsolutePath();
			imagen = new Image(ruta);
			ruta = imgFile.getAbsolutePath();

			ivImagen.setImage(imagen);
		}
	}

	private void aceptar() {
		imagenUnica.setImagen(imagen);
		imagenUnica.setImagenBytes(ConversorImagenes.toByteArray(ruta));
		cerrarVentana();
	}
}
