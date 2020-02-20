package es.rfgr.cr4vegas.modelo.postgresql.dto.mantenimiento.vehiculo;

import java.io.Serializable;

import es.rfgr.cr4vegas.modelo.postgresql.dto.herencia.mantenimiento.MantenimientoPK;

@SuppressWarnings("serial")
public class MantenimientoVehiculoPK extends MantenimientoPK implements Serializable {

	public MantenimientoVehiculoPK(final int codMan) {
		super(codMan);
	}

}
