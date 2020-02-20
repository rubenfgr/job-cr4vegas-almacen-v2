package es.rfgr.cr4vegas.modelo.postgresql.dto.parte;

import java.io.Serializable;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;

@SuppressWarnings("serial")
public class AsociaParteOperarioPK implements Serializable {

	private IntegerProperty codParte = new SimpleIntegerProperty();
	
	private BooleanProperty codParteNull = new SimpleBooleanProperty();
	
	private IntegerProperty codOp = new SimpleIntegerProperty();
	
	private BooleanProperty codOpNull = new SimpleBooleanProperty();
	
	public AsociaParteOperarioPK(final int codParte, final int codOp) {
		setCodParte(codParte);
		setCodOp(codOp);
	}

	public final IntegerProperty codParteProperty() {
		return this.codParte;
	}
	
	public final int getCodParte() {
		return this.codParteProperty().get();
	}
	
	public final void setCodParte(final int codParte) {
		this.codParteProperty().set(codParte);
	}
	
	public final BooleanProperty codParteNullProperty() {
		return this.codParteNull;
	}
	
	public final boolean isCodParteNull() {
		return this.codParteNullProperty().get();
	}
	
	public final void setCodParteNull(final boolean codParteNull) {
		this.codParteNullProperty().set(codParteNull);
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
		result = prime * result + ((codParte == null) ? 0 : codParte.hashCode());
		result = prime * result + ((codParteNull == null) ? 0 : codParteNull.hashCode());
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
		AsociaParteOperarioPK other = (AsociaParteOperarioPK) obj;
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
		if (codParte == null) {
			if (other.codParte != null)
				return false;
		} else if (!codParte.equals(other.codParte))
			return false;
		if (codParteNull == null) {
			if (other.codParteNull != null)
				return false;
		} else if (!codParteNull.equals(other.codParteNull))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuffer ret = new StringBuffer();
		ret.append("es.rfgr.cr4vegas.modelo.dominio.parte.AsociaParteOperarioPK: ");
		ret.append("codParte: " + getCodParte());
		ret.append(", codOp: " + getCodOp());
		return ret.toString();
	}
	
}
