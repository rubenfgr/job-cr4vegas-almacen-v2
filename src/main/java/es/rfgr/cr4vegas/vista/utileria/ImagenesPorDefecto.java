package es.rfgr.cr4vegas.vista.utileria;

import javafx.scene.image.Image;

public class ImagenesPorDefecto {

	private static final String rutaImagenMaterialRELATIVA = "/es/rfgr/cr4vegas/vista/imagenes/imgDefaultMaterial.jpg";
	private static final String rutaImagenOperarioRELATIVA = "/es/rfgr/cr4vegas/vista/imagenes/imgDefaultOperario.jpg";

	private static Image imgDefaultMaterial = new Image(
			ImagenesPorDefecto.class.getResourceAsStream(rutaImagenMaterialRELATIVA), 300, 230, true, true);
	private static Image imgDefaultOperario = new Image(
			ImagenesPorDefecto.class.getResourceAsStream(rutaImagenOperarioRELATIVA), 90, 120, true, true);

	public static String getRutaimagenmaterialraiz() {
		return rutaImagenMaterialRELATIVA;
	}

	public static String getRutaimagenoperarioraiz() {
		return rutaImagenOperarioRELATIVA;
	}

	public static Image getImgDefaultMaterial() {
		return imgDefaultMaterial;
	}

	public static void setImgDefaultMaterial(Image imgDefaultMaterial) {
		ImagenesPorDefecto.imgDefaultMaterial = imgDefaultMaterial;
	}

	public static Image getImgDefaultOperario() {
		return imgDefaultOperario;
	}

	public static void setImgDefaultOperario(Image imgDefaultOperario) {
		ImagenesPorDefecto.imgDefaultOperario = imgDefaultOperario;
	}

}
