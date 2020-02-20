package es.rfgr.cr4vegas.modelo.postgresql.dto.mantenimiento.vehiculo;

import java.io.Serializable;

import es.rfgr.cr4vegas.modelo.postgresql.dto.herencia.registro.RegistroPK;

@SuppressWarnings("serial")
public class RegistroVehiculoPK extends RegistroPK implements Serializable {

	public RegistroVehiculoPK(final int codRegistro) {
		super(codRegistro);
	}

}
