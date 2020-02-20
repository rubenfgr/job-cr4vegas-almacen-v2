package es.rfgr.cr4vegas.modelo.postgresql.dto.herencia.registro;

import java.io.Serializable;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;

@SuppressWarnings("serial")
public abstract class RegistroPK implements Serializable {

	private IntegerProperty codRegistro = new SimpleIntegerProperty();

	private BooleanProperty codRegistroNull = new SimpleBooleanProperty();

	public RegistroPK(final int codRegistro) {
		setCodRegistro(codRegistro);
	}

	public final IntegerProperty codRegistroProperty() {
		return this.codRegistro;
	}

	public final int getCodRegistro() {
		return this.codRegistroProperty().get();
	}

	public final void setCodRegistro(final int codRegistro) {
		this.codRegistroProperty().set(codRegistro);
	}

	public final BooleanProperty codRegistroNullProperty() {
		return this.codRegistroNull;
	}

	public final boolean isCodRegistroNull() {
		return this.codRegistroNullProperty().get();
	}

	public final void setCodRegistroNull(final boolean codRegistroNull) {
		this.codRegistroNullProperty().set(codRegistroNull);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codRegistro == null) ? 0 : codRegistro.hashCode());
		result = prime * result + ((codRegistroNull == null) ? 0 : codRegistroNull.hashCode());
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
		RegistroPK other = (RegistroPK) obj;
		if (codRegistro == null) {
			if (other.codRegistro != null)
				return false;
		} else if (!codRegistro.equals(other.codRegistro))
			return false;
		if (codRegistroNull == null) {
			if (other.codRegistroNull != null)
				return false;
		} else if (!codRegistroNull.equals(other.codRegistroNull))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "RegistroPK [codRegistro=" + codRegistro + ", codRegistroNull=" + codRegistroNull + "]";
	}

}
