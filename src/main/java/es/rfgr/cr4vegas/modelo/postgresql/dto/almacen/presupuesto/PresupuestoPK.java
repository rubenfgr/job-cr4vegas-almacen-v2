package es.rfgr.cr4vegas.modelo.postgresql.dto.almacen.presupuesto;

import java.io.Serializable;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;

@SuppressWarnings("serial")
public class PresupuestoPK implements Serializable {

	private IntegerProperty codPresupuesto = new SimpleIntegerProperty();
	
	private BooleanProperty codPresupuestoNull = new SimpleBooleanProperty();
	
	public PresupuestoPK() {
		
	}
	
	public PresupuestoPK(final int codPresupuesto) {
		setCodPresupuesto(codPresupuesto);
	}

	public final IntegerProperty codPresupuestoProperty() {
		return this.codPresupuesto;
	}
	
	public final int getCodPresupuesto() {
		return this.codPresupuestoProperty().get();
	}
	
	public final void setCodPresupuesto(final int codPresupuesto) {
		this.codPresupuestoProperty().set(codPresupuesto);
	}
	
	public final BooleanProperty codPresupuestoNullProperty() {
		return this.codPresupuestoNull;
	}
	
	public final boolean isCodPresupuestoNull() {
		return this.codPresupuestoNullProperty().get();
	}
	
	public final void setCodPresupuestoNull(final boolean codPresupuestoNull) {
		this.codPresupuestoNullProperty().set(codPresupuestoNull);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codPresupuesto == null) ? 0 : codPresupuesto.hashCode());
		result = prime * result + ((codPresupuestoNull == null) ? 0 : codPresupuestoNull.hashCode());
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
		PresupuestoPK other = (PresupuestoPK) obj;
		if (codPresupuesto == null) {
			if (other.codPresupuesto != null)
				return false;
		} else if (!codPresupuesto.equals(other.codPresupuesto))
			return false;
		if (codPresupuestoNull == null) {
			if (other.codPresupuestoNull != null)
				return false;
		} else if (!codPresupuestoNull.equals(other.codPresupuestoNull))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuffer ret = new StringBuffer();
		ret.append("es.rfgr.cr4vegas.modelo.dominio.almacen.presupuesto.PresupuestoPK: ");
		ret.append("codPresupuesto: " + getCodPresupuesto());
		return ret.toString();
	}
}
