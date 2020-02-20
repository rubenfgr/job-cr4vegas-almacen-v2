package es.rfgr.cr4vegas.modelo.postgresql.dto.almacen.taquilla;

import java.io.Serializable;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;

@SuppressWarnings("serial")
public class OperaTaquillaMaterialPK implements Serializable {

	private IntegerProperty codOperacion = new SimpleIntegerProperty();
	
	private BooleanProperty codOperacionNull = new SimpleBooleanProperty();
	
	public OperaTaquillaMaterialPK() {
		
	}
	
	public OperaTaquillaMaterialPK(final int codOperacion) {
		setCodOperacion(codOperacion);
	}

	public final IntegerProperty codOperacionProperty() {
		return this.codOperacion;
	}
	
	public final int getCodOperacion() {
		return this.codOperacionProperty().get();
	}
	
	public final void setCodOperacion(final int codOperacion) {
		this.codOperacionProperty().set(codOperacion);
	}
	
	public final BooleanProperty codOperacionNullProperty() {
		return this.codOperacionNull;
	}
	
	public final boolean isCodOperacionNull() {
		return this.codOperacionNullProperty().get();
	}
	
	public final void setCodOperacionNull(final boolean codOperacionNull) {
		this.codOperacionNullProperty().set(codOperacionNull);
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codOperacion == null) ? 0 : codOperacion.hashCode());
		result = prime * result + ((codOperacionNull == null) ? 0 : codOperacionNull.hashCode());
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
		OperaTaquillaMaterialPK other = (OperaTaquillaMaterialPK) obj;
		if (codOperacion == null) {
			if (other.codOperacion != null)
				return false;
		} else if (!codOperacion.equals(other.codOperacion))
			return false;
		if (codOperacionNull == null) {
			if (other.codOperacionNull != null)
				return false;
		} else if (!codOperacionNull.equals(other.codOperacionNull))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuffer ret = new StringBuffer();
		ret.append("es.rfgr.cr4vegas.modelo.dominio.almacen.taquilla.OperaTaquillaMaterialPK: ");
		ret.append("codOperacion: " + getCodOperacion());
		return ret.toString();
	}
}
