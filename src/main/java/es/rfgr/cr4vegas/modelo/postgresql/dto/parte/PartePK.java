package es.rfgr.cr4vegas.modelo.postgresql.dto.parte;

import java.io.Serializable;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;

@SuppressWarnings("serial")
public class PartePK implements Serializable {

	private IntegerProperty codParte = new SimpleIntegerProperty();
	
	private BooleanProperty codParteNull = new SimpleBooleanProperty();
	
	public PartePK(final int codParte) {
		setCodParte(codParte);
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
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
		PartePK other = (PartePK) obj;
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
		ret.append("es.rfgr.cr4vegas.modelo.dominio.parte.PartePK: ");
		ret.append("codParte: " + getCodParte());
		return ret.toString();
	}
	
}
