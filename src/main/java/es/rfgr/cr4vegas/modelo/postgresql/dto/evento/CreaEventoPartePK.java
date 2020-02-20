package es.rfgr.cr4vegas.modelo.postgresql.dto.evento;

import java.io.Serializable;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;

@SuppressWarnings("serial")
public class CreaEventoPartePK implements Serializable {

	private IntegerProperty codEvento = new SimpleIntegerProperty();
	
	private BooleanProperty codEventoNull = new SimpleBooleanProperty();
	
	public CreaEventoPartePK() {
		
	}
	
	public CreaEventoPartePK(final int codEvento) {
		setCodEvento(codEvento);
	}

	public final IntegerProperty codEventoProperty() {
		return this.codEvento;
	}
	
	public final int getCodEvento() {
		return this.codEventoProperty().get();
	}
	
	public final void setCodEvento(final int codEvento) {
		this.codEventoProperty().set(codEvento);
	}
	
	public final BooleanProperty codEventoNullProperty() {
		return this.codEventoNull;
	}
	
	public final boolean isCodEventoNull() {
		return this.codEventoNullProperty().get();
	}
	
	public final void setCodEventoNull(final boolean codEventoNull) {
		this.codEventoNullProperty().set(codEventoNull);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codEvento == null) ? 0 : codEvento.hashCode());
		result = prime * result + ((codEventoNull == null) ? 0 : codEventoNull.hashCode());
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
		CreaEventoPartePK other = (CreaEventoPartePK) obj;
		if (codEvento == null) {
			if (other.codEvento != null)
				return false;
		} else if (!codEvento.equals(other.codEvento))
			return false;
		if (codEventoNull == null) {
			if (other.codEventoNull != null)
				return false;
		} else if (!codEventoNull.equals(other.codEventoNull))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuffer ret = new StringBuffer();
		ret.append("es.rfgr.cr4vegas.modelo.dominio.evento.CreaEventoPartePK: ");
		ret.append("codEvento: " + getCodEvento());
		return ret.toString();
	}

}
