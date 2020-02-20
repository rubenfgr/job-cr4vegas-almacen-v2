package es.rfgr.cr4vegas.modelo.postgresql.dto.mantenimiento.instalacion;

import java.io.Serializable;
import java.util.Objects;

import es.rfgr.cr4vegas.utileria.ObjetoSimple;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

@SuppressWarnings("serial")
public class TipoInstalacion implements ObjetoSimple, Serializable {

	private static final String NOMBRE_VALIDO = "[a-zA-Z]{3,}";

	private StringProperty nombre = new SimpleStringProperty();

	public TipoInstalacion() {
		// TODO Auto-generated constructor stub
	}

	public TipoInstalacion(TipoInstalacion tipoInstalacion) {
		copiarValores(tipoInstalacion);
	}
	
	public void copiarValores(TipoInstalacion tipoInstalacion) {
		this.nombreProperty().set(tipoInstalacion.getNombre());
	}

	public final StringProperty nombreProperty() {
		return this.nombre;
	}

	public final String getNombre() {
		return this.nombreProperty().get();
	}

	public final void setNombre(final String nombre) {
		if (nombre == null) {
			throw new NullPointerException("El nombre del tipo de instalación no puede ser nulo.");
		}
		if (!nombre.matches(NOMBRE_VALIDO)) {
			throw new IllegalArgumentException("El nombre del tipo de instalación no es valido. ER = [a-zA-Z]{3,}");
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
		TipoInstalacion other = (TipoInstalacion) obj;
		if (!Objects.equals(this.nombre.get(), other.nombre.get())) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "TipoInstalacion [nombre=" + nombre + "]";
	}

	public TipoInstalacionPK createPK() {
		return new TipoInstalacionPK(getNombre());
	}
}
