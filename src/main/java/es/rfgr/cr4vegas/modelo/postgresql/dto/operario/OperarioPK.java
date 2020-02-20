package es.rfgr.cr4vegas.modelo.postgresql.dto.operario;

import java.io.Serializable;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;

@SuppressWarnings("serial")
public class OperarioPK implements Serializable {

	private IntegerProperty codOp = new SimpleIntegerProperty();
	
	private BooleanProperty codOpNull = new SimpleBooleanProperty();
	
	public OperarioPK(final int codOp) {
		setCodOp(codOp);
	}

	public final IntegerProperty codOpProperty() {
		return this.codOp;
	}
	
	public final int getCodOp() {
		return this.codOpProperty().get();
	}
	
	public final void setCodOp(final int codOp) {
		this.codOpProperty().set(codOp);
	}
	
	public final BooleanProperty codOpNullProperty() {
		return this.codOpNull;
	}
	
	public final boolean isCodOpNull() {
		return this.codOpNullProperty().get();
	}
	
	public final void setCodOpNull(final boolean codOpNull) {
		this.codOpNullProperty().set(codOpNull);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codOp == null) ? 0 : codOp.hashCode());
		result = prime * result + ((codOpNull == null) ? 0 : codOpNull.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		OperarioPK other = (OperarioPK) obj;
		if (codOp == null) {
			if (other.codOp != null)
				return false;
		} else if (!codOp.equals(other.codOp))
			return false;
		if (codOpNull == null) {
			if (other.codOpNull != null)
				return false;
		} else if (!codOpNull.equals(other.codOpNull))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuffer ret = new StringBuffer();
		ret.append("es.rfgr.cr4vegas.modelo.dominio.operario.OperarioPK: ");
		ret.append("codOp: " + getCodOp());
		return ret.toString();
	}
	
}
