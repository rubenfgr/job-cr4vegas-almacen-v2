package es.rfgr.cr4vegas.modelo.postgresql.dto.mantenimiento.instalacion;

import java.io.Serializable;

import es.rfgr.cr4vegas.modelo.postgresql.dto.herencia.registro.RegistroPK;

@SuppressWarnings("serial")
public class RegistroInstalacionPK extends RegistroPK implements Serializable {

	public RegistroInstalacionPK(final int codRegistroInstalacion) {
		super(codRegistroInstalacion);
	}

}
