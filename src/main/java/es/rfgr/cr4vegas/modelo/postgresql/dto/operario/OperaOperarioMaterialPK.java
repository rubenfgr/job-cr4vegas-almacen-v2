package es.rfgr.cr4vegas.modelo.postgresql.dto.operario;

import java.io.Serializable;

import es.rfgr.cr4vegas.modelo.postgresql.dto.herencia.opera.OperaPK;

@SuppressWarnings("serial")
public class OperaOperarioMaterialPK extends OperaPK implements Serializable {

	public OperaOperarioMaterialPK(final int codOperacion) {
		super(codOperacion);
	}
}
