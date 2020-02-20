package es.rfgr.cr4vegas.utileria;

import javafx.scene.image.Image;

public interface ImagenUnica {

	byte[] getImagenBytes();
	
	void setImagenBytes(byte[] imagenBytes);
	
	Image getImagen();
	
	void setImagen(Image imagen);
		
}
