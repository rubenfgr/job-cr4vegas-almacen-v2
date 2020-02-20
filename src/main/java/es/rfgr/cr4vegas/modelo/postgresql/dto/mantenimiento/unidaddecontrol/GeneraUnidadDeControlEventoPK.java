package es.rfgr.cr4vegas.modelo.postgresql.dto.mantenimiento.unidaddecontrol;

import java.io.Serializable;

import es.rfgr.cr4vegas.modelo.postgresql.dto.herencia.genera.GeneraPK;

@SuppressWarnings("serial")
public class GeneraUnidadDeControlEventoPK extends GeneraPK implements Serializable {

	public GeneraUnidadDeControlEventoPK(final int codEvento) {
		super(codEvento);
	}

}
