package es.rfgr.cr4vegas.modelo.postgresql.dto.herencia.genera;

import java.io.Serializable;

import es.rfgr.cr4vegas.modelo.postgresql.dto.evento.Evento;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;

@SuppressWarnings("serial")
public abstract class Genera implements Serializable {

	private IntegerProperty codEvento = new SimpleIntegerProperty();
	
	private ObjectProperty<Evento> objEvento = new SimpleObjectProperty<Evento>();
	
	protected void copiarValores(Genera genera) {
		this.codEventoProperty().set(genera.getCodEvento());
		this.objEventoProperty().set(genera.getObjEvento());
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

	public final ObjectProperty<Evento> objEventoProperty() {
		return this.objEvento;
	}
	
	public final Evento getObjEvento() {
		return this.objEventoProperty().get();
	}
	
	public final void setObjEvento(final Evento evento) {
		this.objEventoProperty().set(evento);
		this.codEventoProperty().set(evento.getCodEvento());
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + this.codEvento.get();
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
		Genera other = (Genera) obj;
		if (this.codEvento.get() != other.codEvento.get()) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return ", codEvento=" + codEvento.get() + "]";
	}
	
}
