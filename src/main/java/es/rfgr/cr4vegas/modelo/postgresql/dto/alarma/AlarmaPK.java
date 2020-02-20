package es.rfgr.cr4vegas.modelo.postgresql.dto.alarma;

import java.io.Serializable;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;

@SuppressWarnings("serial")
public class AlarmaPK implements Serializable {

	private IntegerProperty codAlarma = new SimpleIntegerProperty();
	
	private BooleanProperty codAlarmaNull = new SimpleBooleanProperty();
	
	public AlarmaPK() {
		
	}
	
	public AlarmaPK(final int codAlarma) {
		setCodAlarma(codAlarma);
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
	
	public final BooleanProperty codAlarmaNullProperty() {
		return this.codAlarmaNull;
	}
	
	public final boolean isCodAlarmaNull() {
		return this.codAlarmaNullProperty().get();
	}
	
	public final void setCodAlarmaNull(final boolean codAlarmaNull) {
		this.codAlarmaNullProperty().set(codAlarmaNull);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codAlarma == null) ? 0 : codAlarma.hashCode());
		result = prime * result + ((codAlarmaNull == null) ? 0 : codAlarmaNull.hashCode());
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
		AlarmaPK other = (AlarmaPK) obj;
		if (codAlarma == null) {
			if (other.codAlarma != null)
				return false;
		} else if (!codAlarma.equals(other.codAlarma))
			return false;
		if (codAlarmaNull == null) {
			if (other.codAlarmaNull != null)
				return false;
		} else if (!codAlarmaNull.equals(other.codAlarmaNull))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuffer ret = new StringBuffer();
		ret.append("es.rfgr.cr4vegas.modelo.dominio.evento.alarma.AlarmaPK: ");
		ret.append("codAlarma: " + getCodAlarma());
		return ret.toString();
	}
}
