package es.rfgr.cr4vegas.vista.utileria;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import javafx.scene.image.Image;

/**
 * 
 * @author ruben
 * @version 2.0 After update 30/09/2019
 */
public class ConversorImagenes {

	public static Image byteArrayToImage(byte[] array) {
		return new Image(new ByteArrayInputStream(array));
	}

	public static byte[] toByteArray(String path) {
//		int len = path.split("\\.").length;
//		String ext = path.split("\\.")[len - 1];
		try {
//			InputStream asdf = new FileInputStream(ConversorImagenes.class.getResource(path).toString());
//			return asdf.readAllBytes();
			return ConversorImagenes.class.getResourceAsStream(path).readAllBytes();
//			BufferedImage img = ImageIO.read(asdf);
//			
//			ByteArrayOutputStream baos = new ByteArrayOutputStream();
//			ImageIO.write(img, ext, baos);
//			return baos.toByteArray();

		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
}
