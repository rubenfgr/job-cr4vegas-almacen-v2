package es.rfgr.cr4vegas.modelo.postgresql.dto.mantenimiento.estacion;

import java.io.Serializable;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;

@SuppressWarnings("serial")
public class EstacionPK implements Serializable {

	private IntegerProperty codEstacion = new SimpleIntegerProperty();
	
	private BooleanProperty codEstacionNull = new SimpleBooleanProperty();
	
	public EstacionPK(final int codEstacion) {
		setCodEstacion(codEstacion);
	}

	public final IntegerProperty codEstacionProperty() {
		return this.codEstacion;
	}
	
	public final int getCodEstacion() {
		return this.codEstacionProperty().get();
	}
	
	public final void setCodEstacion(final int codEstacion) {
		this.codEstacionProperty().set(codEstacion);
	}
	
	public final BooleanProperty codEstacionNullProperty() {
		return this.codEstacionNull;
	}
	
	public final boolean isCodEstacionNull() {
		return this.codEstacionNullProperty().get();
	}
	
	public final void setCodEstacionNull(final boolean codEstacionNull) {
		this.codEstacionNullProperty().set(codEstacionNull);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codEstacion == null) ? 0 : codEstacion.hashCode());
		result = prime * result + ((codEstacionNull == null) ? 0 : codEstacionNull.hashCode());
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
		EstacionPK other = (EstacionPK) obj;
		if (codEstacion == null) {
			if (other.codEstacion != null)
				return false;
		} else if (!codEstacion.equals(other.codEstacion))
			return false;
		if (codEstacionNull == null) {
			if (other.codEstacionNull != null)
				return false;
		} else if (!codEstacionNull.equals(other.codEstacionNull))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuffer ret = new StringBuffer();
		ret.append("es.rfgr.cr4vegas.modelo.dominio.mantenimiento.estacion.EstacionPK: ");
		ret.append("codEstacion: " + getCodEstacion());
		return ret.toString();
	}
	
}
