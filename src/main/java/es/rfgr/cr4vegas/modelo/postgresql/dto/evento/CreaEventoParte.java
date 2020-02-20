package es.rfgr.cr4vegas.modelo.postgresql.dto.evento;

import java.io.Serializable;
import java.util.Objects;

import es.rfgr.cr4vegas.modelo.postgresql.dto.parte.Parte;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;

@SuppressWarnings("serial")
public class CreaEventoParte implements Serializable {

	private IntegerProperty codEvento = new SimpleIntegerProperty();
	private ObjectProperty<Evento> objEvento = new SimpleObjectProperty<Evento>();
	private IntegerProperty codParte = new SimpleIntegerProperty();
	private ObjectProperty<Parte> objParte = new SimpleObjectProperty<Parte>();
	
	public CreaEventoParte () {
		
	}
	
	public CreaEventoParte (CreaEventoParte creaEventoParte) {
		copiarValores(creaEventoParte);
	}
	
	public void copiarValores(CreaEventoParte creaEventoParte) {
		this.codEventoProperty().set(creaEventoParte.getCodEvento());
		this.objEventoProperty().set(creaEventoParte.getObjEvento());
		this.codParteProperty().set(creaEventoParte.getCodParte());
		this.objParteProperty().set(creaEventoParte.getObjParte());
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
	

	public final IntegerProperty codParteProperty() {
		return this.codParte;
	}
	

	public final int getCodParte() {
		return this.codParteProperty().get();
	}
	

	public final void setCodParte(final int codParte) {
		this.codParteProperty().set(codParte);
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
	
	public final ObjectProperty<Parte> objParteProperty() {
		return this.objParte;
	}
	
	public final Parte getObjParte() {
		return this.objParteProperty().get();
	}
	
	public final void setObjParte(final Parte parte) {
		this.objParteProperty().set(parte);
		this.codParteProperty().set(parte.getCodParte());
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Objects.hashCode(codEvento.get());
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
		CreaEventoParte other = (CreaEventoParte) obj;
		if (this.codEvento.get() != other.codEvento.get()) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "CreaEventoParte [codEvento=" + codEvento + ", codParte=" + codParte + "]";
	}
	
	public CreaEventoPartePK createPK() {
		return new CreaEventoPartePK(getCodEvento());
	}
	
}
