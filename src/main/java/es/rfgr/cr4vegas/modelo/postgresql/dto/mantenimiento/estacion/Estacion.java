package es.rfgr.cr4vegas.modelo.postgresql.dto.mantenimiento.estacion;

import java.io.Serializable;
import java.util.Objects;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

@SuppressWarnings("serial")
public class Estacion implements Serializable {

	private IntegerProperty codEstacion = new SimpleIntegerProperty();
	private StringProperty nombre = new SimpleStringProperty();
	private StringProperty nota = new SimpleStringProperty();
	private IntegerProperty codImagen = new SimpleIntegerProperty();
	private ObjectProperty<ImagenEstacion> objImagenEstacion = new SimpleObjectProperty<ImagenEstacion>();

	public Estacion() {
		// TODO Auto-generated constructor stub
	}

	public Estacion(Estacion estacion) {
		copiarValores(estacion);
	}
	
	public void copiarValores(Estacion estacion) {
		this.codEstacionProperty().set(estacion.getCodEstacion());
		this.nombreProperty().set(estacion.getNombre());
		this.notaProperty().set(estacion.getNota());
		this.codImagenProperty().set(estacion.getCodImagen());
		this.objImagenEstacionProperty().set(estacion.getObjImagenEstacion());
	}

	public final IntegerProperty codEstacionProperty() {
		return this.codEstacion;
	}

	public final int getCodEstacion() {
		return this.codEstacionProperty().get();
	}

	public final void setCodEstacion(final int codEstacion) {
		this.codEstacionProperty().set(codEstacion);
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

	public final StringProperty notaProperty() {
		return this.nota;
	}

	public final String getNota() {
		return this.notaProperty().get();
	}

	public final void setNota(final String nota) {
		this.notaProperty().set(nota);
	}

	public final IntegerProperty codImagenProperty() {
		return this.codImagen;
	}

	public final int getCodImagen() {
		return this.codImagenProperty().get();
	}

	public final void setCodImagen(final int codImagen) {
		this.codImagenProperty().set(codImagen);
	}

	public final ObjectProperty<ImagenEstacion> objImagenEstacionProperty() {
		return this.objImagenEstacion;
	}

	public final ImagenEstacion getObjImagenEstacion() {
		return this.objImagenEstacionProperty().get();
	}

	public final void setObjImagenEstacion(final ImagenEstacion imagenEstacion) {
		this.objImagenEstacionProperty().set(imagenEstacion);
		this.codImagenProperty().set(imagenEstacion.getCodImagen());
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
		Estacion other = (Estacion) obj;
		if (!Objects.equals(this.nombre.get(), other.nombre.get())) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "Estacion [codEstacion=" + codEstacion + ", nombre=" + nombre + "]";
	}

	public EstacionPK createPK() {
		return new EstacionPK(getCodEstacion());
	}
	
}
