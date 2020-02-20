package es.rfgr.cr4vegas.modelo.postgresql.dto.almacen.presupuesto;

import java.io.Serializable;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;

@SuppressWarnings("serial")
public class TienePresupuestoMaterialPK implements Serializable {

	private IntegerProperty codPresupuesto = new SimpleIntegerProperty();
	
	private BooleanProperty codPresupuestoNull = new SimpleBooleanProperty();
	
	private IntegerProperty codigo4v = new SimpleIntegerProperty();
	
	private BooleanProperty codigo4vNull = new SimpleBooleanProperty();
	
	public TienePresupuestoMaterialPK() {
		
	}
	
	public TienePresupuestoMaterialPK(final int codPresupuesto, final int codigo4v) {
		setCodPresupuesto(codPresupuesto);
		setCodigo4v(codigo4v);
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
	
	public final IntegerProperty codigo4vProperty() {
		return this.codigo4v;
	}
	
	public final int getCodigo4v() {
		return this.codigo4vProperty().get();
	}
	
	public final void setCodigo4v(final int codigo4v) {
		this.codigo4vProperty().set(codigo4v);
	}
	
	public final BooleanProperty codigo4vNullProperty() {
		return this.codigo4vNull;
	}
	
	public final boolean isCodigo4vNull() {
		return this.codigo4vNullProperty().get();
	}
	
	public final void setCodigo4vNull(final boolean codigo4vNull) {
		this.codigo4vNullProperty().set(codigo4vNull);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codPresupuesto == null) ? 0 : codPresupuesto.hashCode());
		result = prime * result + ((codPresupuestoNull == null) ? 0 : codPresupuestoNull.hashCode());
		result = prime * result + ((codigo4v == null) ? 0 : codigo4v.hashCode());
		result = prime * result + ((codigo4vNull == null) ? 0 : codigo4vNull.hashCode());
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
		TienePresupuestoMaterialPK other = (TienePresupuestoMaterialPK) obj;
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
		if (codigo4v == null) {
			if (other.codigo4v != null)
				return false;
		} else if (!codigo4v.equals(other.codigo4v))
			return false;
		if (codigo4vNull == null) {
			if (other.codigo4vNull != null)
				return false;
		} else if (!codigo4vNull.equals(other.codigo4vNull))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuffer ret = new StringBuffer();
		ret.append("es.rfgr.cr4vegas.modelo.dominio.almacen.presupuesto.TienePresupeustoMaterialPK: ");
		ret.append("codPresupuesto: " + getCodPresupuesto());
		ret.append(", codigo4v: " + getCodigo4v());
		return ret.toString();
	}
	
}
