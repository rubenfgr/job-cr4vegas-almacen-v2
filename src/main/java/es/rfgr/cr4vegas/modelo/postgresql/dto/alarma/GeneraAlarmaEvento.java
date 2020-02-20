package es.rfgr.cr4vegas.modelo.postgresql.dto.alarma;

import java.io.Serializable;

import es.rfgr.cr4vegas.modelo.postgresql.dto.herencia.genera.Genera;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;

@SuppressWarnings("serial")
public class GeneraAlarmaEvento extends Genera implements Serializable {

	private IntegerProperty codAlarma = new SimpleIntegerProperty();
	private ObjectProperty<Alarma> objAlarma = new SimpleObjectProperty<Alarma>();

	public GeneraAlarmaEvento() {
		super();
	}

	public GeneraAlarmaEvento(GeneraAlarmaEvento generaAlarmaEvento) {
		copiarValores(generaAlarmaEvento);
	}
	
	public void copiarValores(GeneraAlarmaEvento generaAlarmaEvento) {
		super.copiarValores(generaAlarmaEvento);
		this.codAlarmaProperty().set(generaAlarmaEvento.getCodAlarma());
		this.objAlarmaProperty().set(generaAlarmaEvento.getObjAlarma());
	}

	public final IntegerProperty codAlarmaProperty() {
		return this.codAlarma;
	}
	
	public final int getCodAlarma() {
		return this.codAlarmaProperty().get();
	}
	
	public final void setCodAlarma(final int codAlarma) {
		this.codAlarmaProperty().set(codAlarma);
	}
	
	public final ObjectProperty<Alarma> objAlarmaProperty() {
		return this.objAlarma;
	}
	
	public final Alarma getObjAlarma() {
		return this.objAlarmaProperty().get();
	}
	
	public final void setObjAlarma(final Alarma alarma) {
		if (alarma == null) {
			throw new NullPointerException("La alarma que genera el evento no puede ser nula.");
		}
		this.objAlarmaProperty().set(alarma);
		this.codAlarmaProperty().set(alarma.getCodAlarma());
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + this.codAlarma.get();
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		GeneraAlarmaEvento other = (GeneraAlarmaEvento) obj;
		if (this.codAlarma.get() != other.codAlarma.get()) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "GeneraAlarmaEvento [codAlarma=" + codAlarma.get() + super.toString();
	}
	
	public GeneraAlarmaEventoPK createPK() {
		return new GeneraAlarmaEventoPK(getCodEvento());
	}
	
}
