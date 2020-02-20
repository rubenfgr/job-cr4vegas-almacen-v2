package es.rfgr.cr4vegas.modelo.postgresql.dto.mantenimiento.estacion;

import java.io.Serializable;

import es.rfgr.cr4vegas.modelo.postgresql.dto.herencia.imagen.Imagen;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;

@SuppressWarnings("serial")
public class ImagenEstacion extends Imagen implements Serializable {

	private IntegerProperty codEstacion = new SimpleIntegerProperty();
	private ObjectProperty<Estacion> objEstacion = new SimpleObjectProperty<Estacion>();

	public ImagenEstacion() {
		super();
	}

	public ImagenEstacion(ImagenEstacion imagenEstacion) {
		copiarValores(imagenEstacion);
	}
	
	public void copiarValores(ImagenEstacion imagenEstacion) {
		super.copiarValores(imagenEstacion);
		this.codEstacionProperty().set(imagenEstacion.getCodEstacion());
		this.objEstacionProperty().set(imagenEstacion.getObjEstacion());
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

	public final ObjectProperty<Estacion> objEstacionProperty() {
		return this.objEstacion;
	}

	public final Estacion getObjEstacion() {
		return this.objEstacionProperty().get();
	}

	public final void setObjEstacion(final Estacion estacion) {
		this.objEstacionProperty().set(estacion);
		this.codEstacionProperty().set(estacion.getCodEstacion());
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + this.codEstacion.get();
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
		ImagenEstacion other = (ImagenEstacion) obj;
		if (this.codEstacion.get() != other.codEstacion.get()) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "ImagenEstacion [codEstacion=" + codEstacion.get() + super.toString();
	}
}
