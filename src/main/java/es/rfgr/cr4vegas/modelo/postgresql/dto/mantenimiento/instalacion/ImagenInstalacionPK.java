package es.rfgr.cr4vegas.modelo.postgresql.dto.mantenimiento.instalacion;

import java.io.Serializable;

import es.rfgr.cr4vegas.modelo.postgresql.dto.herencia.imagen.ImagenPK;

@SuppressWarnings("serial")
public class ImagenInstalacionPK extends ImagenPK implements Serializable {

	public ImagenInstalacionPK(int codImagen) {
		super(codImagen);
	}

}
