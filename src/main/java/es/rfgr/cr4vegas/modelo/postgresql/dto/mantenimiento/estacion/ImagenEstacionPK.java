package es.rfgr.cr4vegas.modelo.postgresql.dto.mantenimiento.estacion;

import java.io.Serializable;

import es.rfgr.cr4vegas.modelo.postgresql.dto.herencia.imagen.ImagenPK;

@SuppressWarnings("serial")
public class ImagenEstacionPK extends ImagenPK implements Serializable {

	public ImagenEstacionPK(int codImagen) {
		super(codImagen);
	}

}
