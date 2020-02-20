package es.rfgr.cr4vegas.modelo.postgresql.dto.herencia.telefono;

import java.io.Serializable;
import java.util.Objects;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

@SuppressWarnings("serial")
public abstract class Telefono implements Serializable {

	private static final String TELEFONO_VALIDO = "[0-9]{9}";

	private StringProperty telefono = new SimpleStringProperty();
	
	protected void copiarValores(Telefono telefono) {
		this.telefonoProperty().set(telefono.getTelefono());
	}

	public final StringProperty telefonoProperty() {
		return this.telefono;
	}

	public final String getTelefono() {
		return this.telefonoProperty().get();
	}

	public final void setTelefono(final String telefono) {
		if (telefono == null) {
			throw new NullPointerException("El teléfono no puede ser nulo.");
		}
		if (!telefono.matches(TELEFONO_VALIDO)) {
			throw new IllegalArgumentException("El teléfono no es válido. ER = [0-9]{9}");
		}
		this.telefonoProperty().set(telefono);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + telefono.get().hashCode();
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
		Telefono other = (Telefono) obj;
		if (!Objects.equals(this.telefono.get(), other.telefono.get())) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return telefono.get();
	}
}
