package es.rfgr.cr4vegas.modelo.postgresql.dto.almacen.material;

import java.io.Serializable;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;

@SuppressWarnings("serial")
public class PrecioPK implements Serializable {

	private IntegerProperty codigo4v = new SimpleIntegerProperty();

	private IntegerProperty codtienda = new SimpleIntegerProperty();
	
	private BooleanProperty codigo4vNull = new SimpleBooleanProperty();
	
	private BooleanProperty codtiendaNull = new SimpleBooleanProperty();
	
	public PrecioPK() {
		
	}
	
	public PrecioPK(final int codigo4v, final int codtienda) {
		setCodigo4v(codigo4v);
		setCodtienda(codtienda);
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
	
	public final IntegerProperty codtiendaProperty() {
		return this.codtienda;
	}
	
	public final int getCodTienda() {
		return this.codtiendaProperty().get();
	}
	
	public final void setCodtienda(final int codtienda) {
		this.codtiendaProperty().set(codtienda);
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
	
	public final BooleanProperty codtiendaNullProperty() {
		return this.codtiendaNull;
	}
	
	public final boolean isCodtiendaNull() {
		return this.codtiendaNullProperty().get();
	}
	
	public final void setCodtiendaNull(final boolean codtiendaNull) {
		this.codtiendaNullProperty().set(codtiendaNull);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codigo4v == null) ? 0 : codigo4v.hashCode());
		result = prime * result + ((codtienda == null) ? 0 : codtienda.hashCode());
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
		PrecioPK other = (PrecioPK) obj;
		if (codigo4v == null) {
			if (other.codigo4v != null)
				return false;
		} else if (!codigo4v.equals(other.codigo4v))
			return false;
		if (codtienda == null) {
			if (other.codtienda != null)
				return false;
		} else if (!codtienda.equals(other.codtienda))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		StringBuffer ret = new StringBuffer();
		ret.append("es.rfgr.cr4vegas.modelo.dominio.almacen.material.PrecioPK: ");
		ret.append("codigo4v= " + getCodigo4v());
		ret.append(", codienda= " + getCodTienda());
		return ret.toString();
	}
	
}
