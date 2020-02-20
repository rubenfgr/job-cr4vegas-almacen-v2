package es.rfgr.cr4vegas.modelo.postgresql.dto.mantenimiento.unidaddecontrol;

import java.io.Serializable;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;

@SuppressWarnings("serial")
public class UnidadDeControlPK implements Serializable {

	private IntegerProperty codUDControl = new SimpleIntegerProperty();

	private BooleanProperty codUDControlNull = new SimpleBooleanProperty();

	public UnidadDeControlPK(final int codUDControl) {
		setCodUDControl(codUDControl);
	}

	public final IntegerProperty codUDControlProperty() {
		return this.codUDControl;
	}

	public final int getCodUDControl() {
		return this.codUDControlProperty().get();
	}

	public final void setCodUDControl(final int codUDControl) {
		this.codUDControlProperty().set(codUDControl);
	}

	public final BooleanProperty codUDControlNullProperty() {
		return this.codUDControlNull;
	}

	public final boolean isCodUDControlNull() {
		return this.codUDControlNullProperty().get();
	}

	public final void setCodUDControlNull(final boolean codUDControlNull) {
		this.codUDControlNullProperty().set(codUDControlNull);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codUDControl == null) ? 0 : codUDControl.hashCode());
		result = prime * result + ((codUDControlNull == null) ? 0 : codUDControlNull.hashCode());
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
		UnidadDeControlPK other = (UnidadDeControlPK) obj;
		if (codUDControl == null) {
			if (other.codUDControl != null)
				return false;
		} else if (!codUDControl.equals(other.codUDControl))
			return false;
		if (codUDControlNull == null) {
			if (other.codUDControlNull != null)
				return false;
		} else if (!codUDControlNull.equals(other.codUDControlNull))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuffer ret = new StringBuffer();
		ret.append("es.rfgr.cr4vegas.modelo.dominio.mantenimiento.udcontrol.UDControlPK: ");
		ret.append("codUDControl: " + getCodUDControl());
		return ret.toString();
	}

}
