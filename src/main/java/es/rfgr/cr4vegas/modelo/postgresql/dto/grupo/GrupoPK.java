package es.rfgr.cr4vegas.modelo.postgresql.dto.grupo;

import java.io.Serializable;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

@SuppressWarnings("serial")
public class GrupoPK implements Serializable {

	private StringProperty nombre = new SimpleStringProperty();
	
	private BooleanProperty nombreNull = new SimpleBooleanProperty();
	
	public GrupoPK() {
		
	}
	
	public GrupoPK(final String nombre) {
		setNombre(nombre);
	}

	public final StringProperty nombreProperty() {
		return this.nombre;
	}
	
	public final String getNombre() {
		return this.nombreProperty().get();
	}
	
	public final void setNombre(final String nombre) {
		this.nombreProperty().set(nombre);
	}
	
	public final BooleanProperty nombreNullProperty() {
		return this.nombreNull;
	}
	
	public final boolean isNombreNull() {
		return this.nombreNullProperty().get();
	}
	
	public final void setNombreNull(final boolean nombreNull) {
		this.nombreNullProperty().set(nombreNull);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
		result = prime * result + ((nombreNull == null) ? 0 : nombreNull.hashCode());
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
		GrupoPK other = (GrupoPK) obj;
		if (nombre == null) {
			if (other.nombre != null)
				return false;
		} else if (!nombre.equals(other.nombre))
			return false;
		if (nombreNull == null) {
			if (other.nombreNull != null)
				return false;
		} else if (!nombreNull.equals(other.nombreNull))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuffer ret = new StringBuffer();
		ret.append("es.rfgr.cr4vegas.modelo.dominio.grupo.GrupoPK: ");
		ret.append("nombre: " + getNombre());
		return ret.toString();
	}
	
	
	
}
