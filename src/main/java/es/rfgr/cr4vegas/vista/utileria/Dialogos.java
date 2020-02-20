package es.rfgr.cr4vegas.vista.utileria;

import java.util.Optional;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Dialogos {

	private static final String CSS = "/es/rfgr/cr4vegas/vista/css/modena.css";
	private static final String ID_BT_ACEPTAR = "btAceptar";
	private static final String ID_BT_CANCELAR = "btCancelar";
	private static final String MENSAJE_ERROR = "ERROR";
	private static final String MENSAJE_EXCEPCION = "EXCEPCION";
	private static final String MENSAJE_ADVERTENCIA = "ADVERTENCIA";
	private static final String MENSAJE_CONFIRMACION = "CONFIRMACIÓN";
	private static final String MENSAJE_INFORMACION = "INFORMACIÓN";
	private static final String MENSAJE_DIALOGO_TEXTO = "AÑADIR TEXTO";
	private static Image logo = new Image(
			Dialogos.class.getResourceAsStream("/es/rfgr/cr4vegas/vista/imagenes/icono.png"), 32, 32, true, true);

	private Dialogos() {
	}

	public static void mostrarDialogoError(String contenido, Stage escenarioPropietario) {
		Alert dialogo = new Alert(AlertType.ERROR);
		Stage stage = (Stage) dialogo.getDialogPane().getScene().getWindow();
		stage.getIcons().add(logo);
		dialogo.getDialogPane().getStylesheets().add(Dialogos.class.getResource(CSS).toExternalForm());
		((Button) dialogo.getDialogPane().lookupButton(ButtonType.OK)).setId(ID_BT_ACEPTAR);
		dialogo.setTitle(MENSAJE_ERROR);
		dialogo.setHeaderText(null);
		dialogo.setContentText(contenido);
		if (escenarioPropietario != null) {
			dialogo.initModality(Modality.APPLICATION_MODAL);
			dialogo.initOwner(escenarioPropietario);
		}
		dialogo.showAndWait();
	}

	public static void mostrarDialogoExcepcion(Exception e, Stage escenarioPropietario) {
		Alert dialogo = new Alert(AlertType.ERROR);
		Stage stage = (Stage) dialogo.getDialogPane().getScene().getWindow();
		stage.getIcons().add(logo);
		dialogo.getDialogPane().getStylesheets().add(Dialogos.class.getResource(CSS).toExternalForm());
		((Button) dialogo.getDialogPane().lookupButton(ButtonType.OK)).setId(ID_BT_ACEPTAR);
		dialogo.setTitle(MENSAJE_EXCEPCION);
		dialogo.setHeaderText(null);
		dialogo.setContentText(e.getMessage());
		if (escenarioPropietario != null) {
			dialogo.initModality(Modality.APPLICATION_MODAL);
			dialogo.initOwner(escenarioPropietario);
		}
		dialogo.showAndWait();

		e.printStackTrace();
	}

//	public static void mostrarDialogoError(String titulo, String contenido) {
//		Dialogos.mostrarDialogoError(titulo, contenido, null);
//	}

	public static void mostrarDialogoInformacion(String contenido, Stage escenarioPropietario) {
		Alert dialogo = new Alert(AlertType.INFORMATION);
		Stage stage = (Stage) dialogo.getDialogPane().getScene().getWindow();
		stage.getIcons().add(logo);
		dialogo.getDialogPane().getStylesheets().add(Dialogos.class.getResource(CSS).toExternalForm());
		((Button) dialogo.getDialogPane().lookupButton(ButtonType.OK)).setId(ID_BT_ACEPTAR);
		dialogo.setTitle(MENSAJE_INFORMACION);
		dialogo.setHeaderText(null);
		dialogo.setContentText(contenido);
		if (escenarioPropietario != null) {
			dialogo.initModality(Modality.APPLICATION_MODAL);
			dialogo.initOwner(escenarioPropietario);
		}
		dialogo.showAndWait();
	}

//	public static void mostrarDialogoInformacion(String titulo, String contenido) {
//		Dialogos.mostrarDialogoInformacion(titulo, contenido, null);
//	}

	public static void mostrarDialogoAdvertencia(String contenido, Stage escenarioPropietario) {
		Alert dialogo = new Alert(AlertType.WARNING);
		Stage stage = (Stage) dialogo.getDialogPane().getScene().getWindow();
		stage.getIcons().add(logo);
		dialogo.getDialogPane().getStylesheets().add(Dialogos.class.getResource(CSS).toExternalForm());
		((Button) dialogo.getDialogPane().lookupButton(ButtonType.OK)).setId(ID_BT_ACEPTAR);
		dialogo.setTitle(MENSAJE_ADVERTENCIA);
		dialogo.setHeaderText(null);
		dialogo.setContentText(contenido);
		if (escenarioPropietario != null) {
			dialogo.initModality(Modality.APPLICATION_MODAL);
			dialogo.initOwner(escenarioPropietario);
		}
		dialogo.showAndWait();
	}

//	public static void mostrarDialogoAdvertencia(String titulo, String contenido) {
//		Dialogos.mostrarDialogoAdvertencia(titulo, contenido, null);
//	}

	public static String mostrarDialogoTexto(String contenido, Stage escenarioPropietario) {
		TextInputDialog dialogo = new TextInputDialog();
		Stage stage = (Stage) dialogo.getDialogPane().getScene().getWindow();
		stage.getIcons().add(logo);
		dialogo.getDialogPane().getStylesheets().add(Dialogos.class.getResource(CSS).toExternalForm());
		((Button) dialogo.getDialogPane().lookupButton(ButtonType.OK)).setId(ID_BT_ACEPTAR);
		((Button) dialogo.getDialogPane().lookupButton(ButtonType.CANCEL)).setId(ID_BT_CANCELAR);
		dialogo.setTitle(MENSAJE_DIALOGO_TEXTO);
		dialogo.setHeaderText(null);
		dialogo.setContentText(contenido);

		Optional<String> respuesta = dialogo.showAndWait();
		return (respuesta.isPresent() ? respuesta.get() : null);
	}

	public static boolean mostrarDialogoConfirmacion(String contenido, Stage escenarioPropietario) {
		Alert dialogo = new Alert(AlertType.CONFIRMATION);
		Stage stage = (Stage) dialogo.getDialogPane().getScene().getWindow();
		stage.getIcons().add(logo);
		dialogo.getDialogPane().getStylesheets().add(Dialogos.class.getResource(CSS).toExternalForm());
		((Button) dialogo.getDialogPane().lookupButton(ButtonType.OK)).setId(ID_BT_ACEPTAR);
		((Button) dialogo.getDialogPane().lookupButton(ButtonType.CANCEL)).setId(ID_BT_CANCELAR);
		dialogo.setTitle(MENSAJE_CONFIRMACION);
		dialogo.setHeaderText(null);
		dialogo.setContentText(contenido);
		if (escenarioPropietario != null) {
			dialogo.initModality(Modality.APPLICATION_MODAL);
			dialogo.initOwner(escenarioPropietario);
		}

		Optional<ButtonType> respuesta = dialogo.showAndWait();
		return (respuesta.isPresent() && respuesta.get() == ButtonType.OK);
	}
}
