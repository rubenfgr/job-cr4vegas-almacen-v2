package es.rfgr.cr4vegas.modelo.postgresql.dto.almacen.tienda;

import java.io.Serializable;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;

@SuppressWarnings("serial")
public class TiendaPK implements Serializable {
	
	private IntegerProperty codTienda = new SimpleIntegerProperty();
	
	private BooleanProperty codTiendaNull = new SimpleBooleanProperty();
	
	public TiendaPK() {
		
	}
	
	public TiendaPK(final int codTienda) {
		setCodTienda(codTienda);
	}

	public final IntegerProperty codTiendaProperty() {
		return this.codTienda;
	}
	
	public final int getCodTienda() {
		return this.codTiendaProperty().get();
	}
	
	public final void setCodTienda(final int codTienda) {
		this.codTiendaProperty().set(codTienda);
	}
	
	public final BooleanProperty codTiendaNullProperty() {
		return this.codTiendaNull;
	}
	
	public final boolean isCodTiendaNull() {
		return this.codTiendaNullProperty().get();
	}
	
	public final void setCodTiendaNull(final boolean codTiendaNull) {
		this.codTiendaNullProperty().set(codTiendaNull);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codTienda == null) ? 0 : codTienda.hashCode());
		result = prime * result + ((codTiendaNull == null) ? 0 : codTiendaNull.hashCode());
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
		TiendaPK other = (TiendaPK) obj;
		if (codTienda == null) {
			if (other.codTienda != null)
				return false;
		} else if (!codTienda.equals(other.codTienda))
			return false;
		if (codTiendaNull == null) {
			if (other.codTiendaNull != null)
				return false;
		} else if (!codTiendaNull.equals(other.codTiendaNull))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuffer ret = new StringBuffer();
		ret.append("es.rfgr.cr4vegas.modelo.dominio.almacen.compra.tienda.DirTiendaPK: ");
		ret.append("codTienda= " + codTienda);
		return ret.toString();
	}
	
	
	
}
