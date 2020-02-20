package es.rfgr.cr4vegas.modelo.postgresql.dto.mantenimiento.unidaddecontrol;

import java.io.Serializable;

import es.rfgr.cr4vegas.modelo.postgresql.dto.herencia.registro.RegistroPK;

@SuppressWarnings("serial")
public class RegistroUnidadDeControlPK extends RegistroPK implements Serializable {

	public RegistroUnidadDeControlPK(final int codRegistro) {
		super(codRegistro);
	}

}
