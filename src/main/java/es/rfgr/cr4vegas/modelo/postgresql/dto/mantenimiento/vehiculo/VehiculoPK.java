package es.rfgr.cr4vegas.modelo.postgresql.dto.mantenimiento.vehiculo;

import java.io.Serializable;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

@SuppressWarnings("serial")
public class VehiculoPK implements Serializable {

	private StringProperty matricula = new SimpleStringProperty();
	
	private BooleanProperty matriculaNull = new SimpleBooleanProperty();
	
	public VehiculoPK(final String matricula) {
		setMatricula(matricula);
	}

	public final StringProperty matriculaProperty() {
		return this.matricula;
	}
	
	public final String getMatricula() {
		return this.matriculaProperty().get();
	}
	
	public final void setMatricula(final String matricula) {
		this.matriculaProperty().set(matricula);
	}
	
	public final BooleanProperty matriculaNullProperty() {
		return this.matriculaNull;
	}
	
	public final boolean isMatriculaNull() {
		return this.matriculaNullProperty().get();
	}
	
	public final void setMatriculaNull(final boolean matriculaNull) {
		this.matriculaNullProperty().set(matriculaNull);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((matricula == null) ? 0 : matricula.hashCode());
		result = prime * result + ((matriculaNull == null) ? 0 : matriculaNull.hashCode());
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
		VehiculoPK other = (VehiculoPK) obj;
		if (matricula == null) {
			if (other.matricula != null)
				return false;
		} else if (!matricula.equals(other.matricula))
			return false;
		if (matriculaNull == null) {
			if (other.matriculaNull != null)
				return false;
		} else if (!matriculaNull.equals(other.matriculaNull))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuffer ret = new StringBuffer();
		ret.append("es.rfgr.cr4vegas.modelo.dominio.mantenimiento.vehiculo.VehiculoPK: ");
		ret.append("matricula: " + getMatricula());
		return ret.toString();
	}
	
}
