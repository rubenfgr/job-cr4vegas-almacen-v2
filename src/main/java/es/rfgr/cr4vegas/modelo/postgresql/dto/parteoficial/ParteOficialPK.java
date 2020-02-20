package es.rfgr.cr4vegas.modelo.postgresql.dto.parteoficial;

import java.io.Serializable;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;

@SuppressWarnings("serial")
public class ParteOficialPK implements Serializable {

	private IntegerProperty codParteOficial = new SimpleIntegerProperty();
	
	private BooleanProperty codParteOficialNull = new SimpleBooleanProperty();
	
	public ParteOficialPK(final int codParte) {
		setCodParte(codParte);
	}

	public final IntegerProperty codParteProperty() {
		return this.codParteOficial;
	}
	
	public final int getCodParte() {
		return this.codParteProperty().get();
	}
	
	public final void setCodParte(final int codParte) {
		this.codParteProperty().set(codParte);
	}
	
	public final BooleanProperty codParteNullProperty() {
		return this.codParteOficialNull;
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
		result = prime * result + ((codParteOficial == null) ? 0 : codParteOficial.hashCode());
		result = prime * result + ((codParteOficialNull == null) ? 0 : codParteOficialNull.hashCode());
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
		ParteOficialPK other = (ParteOficialPK) obj;
		if (codParteOficial == null) {
			if (other.codParteOficial != null)
				return false;
		} else if (!codParteOficial.equals(other.codParteOficial))
			return false;
		if (codParteOficialNull == null) {
			if (other.codParteOficialNull != null)
				return false;
		} else if (!codParteOficialNull.equals(other.codParteOficialNull))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuffer ret = new StringBuffer();
		ret.append("es.rfgr.cr4vegas.modelo.dominio.parteoficial.ParteOficialPK: ");
		ret.append("codParteOficial: " + getCodParte());
		return ret.toString();
	}
	
}
