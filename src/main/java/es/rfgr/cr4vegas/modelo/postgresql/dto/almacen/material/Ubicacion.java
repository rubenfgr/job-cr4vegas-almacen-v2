package es.rfgr.cr4vegas.modelo.postgresql.dto.almacen.material;

import java.io.Serializable;
import java.util.Objects;

import es.rfgr.cr4vegas.utileria.ObjetoSimple;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

@SuppressWarnings("serial")
public class Ubicacion implements ObjetoSimple, Serializable {

	private static final String CODUBICACION_VALIDO = "[áéíóúÁÉÍÓÚa-zA-Z0-9\\s]{1,}";

	private StringProperty nombre = new SimpleStringProperty();

	public Ubicacion() {
		// TODO Auto-generated constructor stub
	}
	
	public Ubicacion(Ubicacion ubicacion) {
		copiarValores(ubicacion);
	}
	
	public void copiarValores(Ubicacion ubicacionNewValues) {
		this.nombre.set(ubicacionNewValues.getNombre());
	}

	public final StringProperty nombreProperty() {
		return this.nombre;
	}

	public final String getNombre() {
		return this.nombreProperty().get();
	}

	public final void setNombre(final String nombre) {
		if (nombre == null) {
			throw new NullPointerException("El código de la ubicación no puede ser nulo.");
		}
		if (!nombre.matches(CODUBICACION_VALIDO)) {
			throw new IllegalArgumentException("El código de la ubicación no es válido. ER = [áéíóúÁÉÍÓÚa-zA-Z0-9]{2,}");
		}
		this.nombreProperty().set(nombre);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + nombre.hashCode();
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
		Ubicacion other = (Ubicacion) obj;
		if (!Objects.equals(this.nombre.get(), other.nombre.get())) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return nombre.get();
	}
	
	public UbicacionPK createPK() {
		return new UbicacionPK(getNombre());
	}
}
