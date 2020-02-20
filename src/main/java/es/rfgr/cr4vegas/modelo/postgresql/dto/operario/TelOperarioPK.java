package es.rfgr.cr4vegas.modelo.postgresql.dto.operario;

import java.io.Serializable;

import es.rfgr.cr4vegas.modelo.postgresql.dto.herencia.telefono.TelefonoPK;

@SuppressWarnings("serial")
public class TelOperarioPK extends TelefonoPK implements Serializable {

	public TelOperarioPK(final String telefono) {
		super(telefono);
	}

}
