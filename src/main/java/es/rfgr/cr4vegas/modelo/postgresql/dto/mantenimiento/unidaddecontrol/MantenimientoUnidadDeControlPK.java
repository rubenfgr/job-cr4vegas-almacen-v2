package es.rfgr.cr4vegas.modelo.postgresql.dto.mantenimiento.unidaddecontrol;

import java.io.Serializable;

import es.rfgr.cr4vegas.modelo.postgresql.dto.herencia.mantenimiento.MantenimientoPK;

@SuppressWarnings("serial")
public class MantenimientoUnidadDeControlPK extends MantenimientoPK implements Serializable {

	public MantenimientoUnidadDeControlPK(final int codMan) {
		super(codMan);
	}
}
