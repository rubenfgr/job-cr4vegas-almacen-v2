package es.rfgr.cr4vegas.modelo.postgresql.dto.mantenimiento.vehiculo;

import java.io.Serializable;
import java.util.Objects;

import es.rfgr.cr4vegas.modelo.postgresql.dto.herencia.imagen.Imagen;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

@SuppressWarnings("serial")
public class ImagenVehiculo extends Imagen implements Serializable {

	private StringProperty matricula = new SimpleStringProperty();

	private ObjectProperty<Vehiculo> objVehiculo = new SimpleObjectProperty<Vehiculo>();

	public ImagenVehiculo() {
		super();
	}

	public ImagenVehiculo(ImagenVehiculo imagenVehiculo) {
		copiarValores(imagenVehiculo);
	}

	public void copiarValores(ImagenVehiculo imagenVehiculo) {
		super.copiarValores(imagenVehiculo);
		this.matriculaProperty().set(imagenVehiculo.getMatricula());
		this.objVehiculoProperty().set(imagenVehiculo.getObjVehiculo());
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

	public final ObjectProperty<Vehiculo> objVehiculoProperty() {
		return this.objVehiculo;
	}

	public final Vehiculo getObjVehiculo() {
		return this.objVehiculoProperty().get();
	}

	public final void setObjVehiculo(final Vehiculo objVehiculo) {
		this.objVehiculoProperty().set(objVehiculo);
		this.matriculaProperty().set(objVehiculo.getMatricula());
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hashCode(this.matricula.get());
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
		ImagenVehiculo other = (ImagenVehiculo) obj;
		if (!Objects.equals(this.matricula.get(), other.matricula.get())) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "ImagenVehiculo [matricula=" + matricula + super.toString();
	}

}
