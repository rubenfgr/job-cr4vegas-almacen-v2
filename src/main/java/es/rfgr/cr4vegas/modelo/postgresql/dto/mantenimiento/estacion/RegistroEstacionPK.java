package es.rfgr.cr4vegas.modelo.postgresql.dto.mantenimiento.estacion;

import java.io.Serializable;

import es.rfgr.cr4vegas.modelo.postgresql.dto.herencia.registro.RegistroPK;

@SuppressWarnings("serial")
public class RegistroEstacionPK extends RegistroPK implements Serializable {

	public RegistroEstacionPK(final int codRegistro) {
		super(codRegistro);
	}

}
