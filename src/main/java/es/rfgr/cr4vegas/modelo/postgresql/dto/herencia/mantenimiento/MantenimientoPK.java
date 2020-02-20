package es.rfgr.cr4vegas.modelo.postgresql.dto.herencia.mantenimiento;

import java.io.Serializable;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;

@SuppressWarnings("serial")
public abstract class MantenimientoPK implements Serializable {

	private IntegerProperty codMan = new SimpleIntegerProperty();

	private BooleanProperty codManNull = new SimpleBooleanProperty();

	public MantenimientoPK(final int codMan) {
		setCodMan(codMan);
	}

	public final IntegerProperty codManProperty() {
		return this.codMan;
	}

	public final int getCodMan() {
		return this.codManProperty().get();
	}

	public final void setCodMan(final int codMan) {
		this.codManProperty().set(codMan);
	}

	public final BooleanProperty codManNullProperty() {
		return this.codManNull;
	}

	public final boolean isCodManNull() {
		return this.codManNullProperty().get();
	}

	public final void setCodManNull(final boolean codManNull) {
		this.codManNullProperty().set(codManNull);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codMan == null) ? 0 : codMan.hashCode());
		result = prime * result + ((codManNull == null) ? 0 : codManNull.hashCode());
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
		MantenimientoPK other = (MantenimientoPK) obj;
		if (codMan == null) {
			if (other.codMan != null)
				return false;
		} else if (!codMan.equals(other.codMan))
			return false;
		if (codManNull == null) {
			if (other.codManNull != null)
				return false;
		} else if (!codManNull.equals(other.codManNull))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "MantenimientoPK [codMan=" + codMan + ", codManNull=" + codManNull + "]";
	}

}
