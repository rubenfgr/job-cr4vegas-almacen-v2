package es.rfgr.cr4vegas.modelo.postgresql.dto.parte;

import java.io.Serializable;

import es.rfgr.cr4vegas.modelo.postgresql.dto.herencia.opera.OperaPK;

@SuppressWarnings("serial")
public class OperaParteMaterialPK extends OperaPK implements Serializable {

	public OperaParteMaterialPK(final int codOperacion) {
		super(codOperacion);
	}

}
