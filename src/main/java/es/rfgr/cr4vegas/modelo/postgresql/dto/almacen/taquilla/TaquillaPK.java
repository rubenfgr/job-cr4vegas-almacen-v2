package es.rfgr.cr4vegas.modelo.postgresql.dto.almacen.taquilla;

import java.io.Serializable;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;

@SuppressWarnings("serial")
public class TaquillaPK implements Serializable {

	private IntegerProperty codTaquilla = new SimpleIntegerProperty();
	
	private BooleanProperty codTaquillaNull = new SimpleBooleanProperty();
	
	public TaquillaPK() {
		
	}
	
	public TaquillaPK(final int codTaquilla) {
		setCodTaquilla(codTaquilla);
	}

	public final IntegerProperty codTaquillaProperty() {
		return this.codTaquilla;
	}
	
	public final int getCodTaquilla() {
		return this.codTaquillaProperty().get();
	}
	
	public final void setCodTaquilla(final int codTaquilla) {
		this.codTaquillaProperty().set(codTaquilla);
	}
	
	public final BooleanProperty codTaquillaNullProperty() {
		return this.codTaquillaNull;
	}
	
	public final boolean isCodTaquillaNull() {
		return this.codTaquillaNullProperty().get();
	}
	
	public final void setCodTaquillaNull(final boolean codTaquillaNull) {
		this.codTaquillaNullProperty().set(codTaquillaNull);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codTaquilla == null) ? 0 : codTaquilla.hashCode());
		result = prime * result + ((codTaquillaNull == null) ? 0 : codTaquillaNull.hashCode());
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
		TaquillaPK other = (TaquillaPK) obj;
		if (codTaquilla == null) {
			if (other.codTaquilla != null)
				return false;
		} else if (!codTaquilla.equals(other.codTaquilla))
			return false;
		if (codTaquillaNull == null) {
			if (other.codTaquillaNull != null)
				return false;
		} else if (!codTaquillaNull.equals(other.codTaquillaNull))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuffer ret = new StringBuffer();
		ret.append("es.rfgr.cr4vegas.modelo.dominio.almacen.taquilla.TaquillaPK: ");
		ret.append("codTaquilla: " + getCodTaquilla());
		return ret.toString();
	}
	
	
	
}
