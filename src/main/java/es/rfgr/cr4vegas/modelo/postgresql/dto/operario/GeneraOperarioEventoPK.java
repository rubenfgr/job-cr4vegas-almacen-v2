package es.rfgr.cr4vegas.modelo.postgresql.dto.operario;

import java.io.Serializable;

import es.rfgr.cr4vegas.modelo.postgresql.dto.herencia.genera.GeneraPK;

@SuppressWarnings("serial")
public class GeneraOperarioEventoPK extends GeneraPK implements Serializable {

	public GeneraOperarioEventoPK(final int codEvento) {
		super(codEvento);
	}

}
