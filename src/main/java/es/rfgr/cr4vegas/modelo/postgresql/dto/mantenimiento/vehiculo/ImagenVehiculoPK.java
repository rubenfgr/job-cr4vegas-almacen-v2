package es.rfgr.cr4vegas.modelo.postgresql.dto.mantenimiento.vehiculo;

import java.io.Serializable;

import es.rfgr.cr4vegas.modelo.postgresql.dto.herencia.imagen.ImagenPK;

@SuppressWarnings("serial")
public class ImagenVehiculoPK extends ImagenPK implements Serializable {

	public ImagenVehiculoPK(int codImagen) {
		super(codImagen);
	}

}
