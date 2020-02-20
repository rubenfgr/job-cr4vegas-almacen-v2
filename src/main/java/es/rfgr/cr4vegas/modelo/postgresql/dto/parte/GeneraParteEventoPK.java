package es.rfgr.cr4vegas.modelo.postgresql.dto.parte;

import java.io.Serializable;

import es.rfgr.cr4vegas.modelo.postgresql.dto.herencia.genera.GeneraPK;

@SuppressWarnings("serial")
public class GeneraParteEventoPK extends GeneraPK implements Serializable {

	public GeneraParteEventoPK(final int codEvento) {
		super(codEvento);
	}
}
