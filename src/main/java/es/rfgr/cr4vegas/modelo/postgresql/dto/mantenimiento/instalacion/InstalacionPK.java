package es.rfgr.cr4vegas.modelo.postgresql.dto.mantenimiento.instalacion;

import java.io.Serializable;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;

@SuppressWarnings("serial")
public class InstalacionPK implements Serializable {

	private IntegerProperty codInstalacion = new SimpleIntegerProperty();
	
	private BooleanProperty codInstalacionNull = new SimpleBooleanProperty();
	
	public InstalacionPK(final int codInstalacion) {
		setCodInstalacion(codInstalacion);
	}

	public final IntegerProperty codInstalacionProperty() {
		return this.codInstalacion;
	}
	
	public final int getCodInstalacion() {
		return this.codInstalacionProperty().get();
	}
	
	public final void setCodInstalacion(final int codInstalacion) {
		this.codInstalacionProperty().set(codInstalacion);
	}
	
	public final BooleanProperty codInstalacionNullProperty() {
		return this.codInstalacionNull;
	}
	
	public final boolean isCodInstalacionNull() {
		return this.codInstalacionNullProperty().get();
	}
	
	public final void setCodInstalacionNull(final boolean codInstalacionNull) {
		this.codInstalacionNullProperty().set(codInstalacionNull);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codInstalacion == null) ? 0 : codInstalacion.hashCode());
		result = prime * result + ((codInstalacionNull == null) ? 0 : codInstalacionNull.hashCode());
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
		InstalacionPK other = (InstalacionPK) obj;
		if (codInstalacion == null) {
			if (other.codInstalacion != null)
				return false;
		} else if (!codInstalacion.equals(other.codInstalacion))
			return false;
		if (codInstalacionNull == null) {
			if (other.codInstalacionNull != null)
				return false;
		} else if (!codInstalacionNull.equals(other.codInstalacionNull))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuffer ret = new StringBuffer();
		ret.append("es.rfgr.cr4vegas.modelo.dominio.mantenimiento.instalacion.InstalacionPK: ");
		ret.append("codInstalacion: " + getCodInstalacion());
		return ret.toString();
	}
	
}
