package es.rfgr.cr4vegas.modelo.postgresql.dto.almacen.material;

import java.io.Serializable;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;

@SuppressWarnings("serial")
public class MaterialPK implements Serializable {

	private IntegerProperty codigo4v = new SimpleIntegerProperty();
	
	private BooleanProperty codigo4vNull = new SimpleBooleanProperty();
	
	public MaterialPK() {
		
	}
	
	public MaterialPK(final int codigo4v) {
		setCodigo4v(codigo4v);
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
		MaterialPK other = (MaterialPK) obj;
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
		ret.append("es.rfgr.cr4vegas.modelo.dominio.almacen.material.MaterialPK: ");
		ret.append("codigo4v= " + getCodigo4v());
		return ret.toString();
	}

}
