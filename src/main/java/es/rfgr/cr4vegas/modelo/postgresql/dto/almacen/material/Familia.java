package es.rfgr.cr4vegas.modelo.postgresql.dto.almacen.material;

import java.io.Serializable;
import java.util.Objects;

import es.rfgr.cr4vegas.utileria.ObjetoSimple;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

@SuppressWarnings("serial")
public class Familia implements ObjetoSimple, Serializable {

	private static final String NOMBRE_VALIDO = "[a-zA-Z]{1,}";

	private StringProperty nombre = new SimpleStringProperty();

	public Familia() {
		// TODO Auto-generated constructor stub
	}

	public Familia(Familia familia) {
		copiarValores(familia);
	}
	
	public void copiarValores(Familia familiaNewValues) {
		this.nombre.set(familiaNewValues.getNombre());
	}

	public final StringProperty nombreProperty() {
		return this.nombre;
	}

	public final String getNombre() {
		return this.nombreProperty().get();
	}

	public final void setNombre(final String nombre) {
		if (nombre == null) {
			throw new NullPointerException("El nombre de la familia no puede ser nulo.");
		}
		if (!nombre.matches(NOMBRE_VALIDO)) {
			throw new IllegalArgumentException("El nombre de la familia no es válido. ER = [a-zA-Z]{3,}");
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
		Familia other = (Familia) obj;
		if (!Objects.equals(this.nombre.get(), other.nombre.get())) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return nombre.get();
	}

	public FamiliaPK createPK() {
		return new FamiliaPK(getNombre());
	}
}
