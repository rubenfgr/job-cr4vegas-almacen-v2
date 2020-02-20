package es.rfgr.cr4vegas.modelo.postgresql.dto.mantenimiento.vehiculo;

import java.io.Serializable;

import es.rfgr.cr4vegas.modelo.postgresql.dto.herencia.genera.GeneraPK;

@SuppressWarnings("serial")
public class GeneraVehiculoEventoPK extends GeneraPK implements Serializable {

	public GeneraVehiculoEventoPK(final int codEvento) {
		super(codEvento);
	}

}
