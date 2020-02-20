package es.rfgr.cr4vegas.modelo.postgresql.dto.herencia.telefono;

import java.io.Serializable;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

@SuppressWarnings("serial")
public abstract class TelefonoPK implements Serializable {

	private StringProperty telefono = new SimpleStringProperty();

	private BooleanProperty telefonoNull = new SimpleBooleanProperty();

	public TelefonoPK(final String telefono) {
		setTelefono(telefono);
	}

	public final StringProperty telefonoProperty() {
		return this.telefono;
	}

	public final String getTelefono() {
		return this.telefonoProperty().get();
	}

	public final void setTelefono(final String telefono) {
		this.telefonoProperty().set(telefono);
	}

	public final BooleanProperty telefonoNullProperty() {
		return this.telefonoNull;
	}

	public final boolean isTelefonoNull() {
		return this.telefonoNullProperty().get();
	}

	public final void setTelefonoNull(final boolean telefonoNull) {
		this.telefonoNullProperty().set(telefonoNull);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((telefono == null) ? 0 : telefono.hashCode());
		result = prime * result + ((telefonoNull == null) ? 0 : telefonoNull.hashCode());
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
		TelefonoPK other = (TelefonoPK) obj;
		if (telefono == null) {
			if (other.telefono != null)
				return false;
		} else if (!telefono.equals(other.telefono))
			return false;
		if (telefonoNull == null) {
			if (other.telefonoNull != null)
				return false;
		} else if (!telefonoNull.equals(other.telefonoNull))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "TelefonoPK [telefono=" + telefono + ", telefonoNull=" + telefonoNull + "]";
	}

}
