package es.rfgr.cr4vegas.modelo.postgresql.dto.grupo;

import java.io.Serializable;
import java.util.Objects;

import es.rfgr.cr4vegas.utileria.ObjetoSimple;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

@SuppressWarnings("serial")
public class Grupo implements ObjetoSimple, Serializable {

	private StringProperty nombre = new SimpleStringProperty();

	public Grupo() {
		// TODO Auto-generated constructor stub
	}

	public Grupo(Grupo grupo) {
		copiarValores(grupo);
	}
	
	public void copiarValores(Grupo grupo) {
		this.nombreProperty().set(grupo.getNombre());
	}

	public final StringProperty nombreProperty() {
		return this.nombre;
	}

	public final String getNombre() {
		return this.nombreProperty().get();
	}

	public final void setNombre(final String nombre) {
		if (nombre == null) {
			throw new NullPointerException("El nombre de un grupo no puede ser nulo.");
		}
		this.nombreProperty().set(nombre);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Objects.hashCode(this.nombre.get());
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
		Grupo other = (Grupo) obj;
		if (!this.nombre.get().equals(other.nombre.get())) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return nombre.get();
	}

	public GrupoPK createPK() {
		return new GrupoPK(getNombre());
	}
}
