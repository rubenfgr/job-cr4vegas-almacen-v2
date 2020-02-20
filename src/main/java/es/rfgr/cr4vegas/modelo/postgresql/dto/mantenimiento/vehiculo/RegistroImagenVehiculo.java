package es.rfgr.cr4vegas.modelo.postgresql.dto.mantenimiento.vehiculo;

import java.io.Serializable;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;

@SuppressWarnings("serial")
public class RegistroImagenVehiculo implements Serializable {

	private IntegerProperty codRegistroVehiculo = new SimpleIntegerProperty();

	private ObjectProperty<RegistroVehiculo> objRegistroVehiculo = new SimpleObjectProperty<>();

	private IntegerProperty codImagenVehiculo = new SimpleIntegerProperty();

	private ObjectProperty<ImagenVehiculo> objImagenVehiculo = new SimpleObjectProperty<ImagenVehiculo>();

	public RegistroImagenVehiculo() {

	}

	public RegistroImagenVehiculo(RegistroImagenVehiculo registroImagenVehiculo) {
		copiarValores(registroImagenVehiculo);
	}

	public void copiarValores(RegistroImagenVehiculo registroImagenVehiculo) {
		this.codRegistroVehiculoProperty().set(registroImagenVehiculo.getCodRegistroVehiculo());
		this.objRegistroVehiculoProperty().set(registroImagenVehiculo.getObjRegistroVehiculo());
		this.codImagenVehiculoProperty().set(registroImagenVehiculo.getCodImagenVehiculo());
		this.objImagenVehiculoProperty().set(registroImagenVehiculo.getObjImagenVehiculo());
	}

	public final IntegerProperty codRegistroVehiculoProperty() {
		return this.codRegistroVehiculo;
	}

	public final int getCodRegistroVehiculo() {
		return this.codRegistroVehiculoProperty().get();
	}

	public final void setCodRegistroVehiculo(final int codRegistroVehiculo) {
		this.codRegistroVehiculoProperty().set(codRegistroVehiculo);
	}

	public final ObjectProperty<RegistroVehiculo> objRegistroVehiculoProperty() {
		return this.objRegistroVehiculo;
	}

	public final RegistroVehiculo getObjRegistroVehiculo() {
		return this.objRegistroVehiculoProperty().get();
	}

	public final void setObjRegistroVehiculo(final RegistroVehiculo objRegistroVehiculo) {
		this.objRegistroVehiculoProperty().set(objRegistroVehiculo);
	}

	public final IntegerProperty codImagenVehiculoProperty() {
		return this.codImagenVehiculo;
	}

	public final int getCodImagenVehiculo() {
		return this.codImagenVehiculoProperty().get();
	}

	public final void setCodImagenVehiculo(final int codImagenVehiculo) {
		this.codImagenVehiculoProperty().set(codImagenVehiculo);
	}

	public final ObjectProperty<ImagenVehiculo> objImagenVehiculoProperty() {
		return this.objImagenVehiculo;
	}

	public final ImagenVehiculo getObjImagenVehiculo() {
		return this.objImagenVehiculoProperty().get();
	}

	public final void setObjImagenVehiculo(final ImagenVehiculo objImagenVehiculo) {
		this.objImagenVehiculoProperty().set(objImagenVehiculo);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codImagenVehiculo == null) ? 0 : codImagenVehiculo.hashCode());
		result = prime * result + ((codRegistroVehiculo == null) ? 0 : codRegistroVehiculo.hashCode());
		result = prime * result + ((objImagenVehiculo == null) ? 0 : objImagenVehiculo.hashCode());
		result = prime * result + ((objRegistroVehiculo == null) ? 0 : objRegistroVehiculo.hashCode());
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
		RegistroImagenVehiculo other = (RegistroImagenVehiculo) obj;
		if (codImagenVehiculo == null) {
			if (other.codImagenVehiculo != null)
				return false;
		} else if (!codImagenVehiculo.equals(other.codImagenVehiculo))
			return false;
		if (codRegistroVehiculo == null) {
			if (other.codRegistroVehiculo != null)
				return false;
		} else if (!codRegistroVehiculo.equals(other.codRegistroVehiculo))
			return false;
		if (objImagenVehiculo == null) {
			if (other.objImagenVehiculo != null)
				return false;
		} else if (!objImagenVehiculo.equals(other.objImagenVehiculo))
			return false;
		if (objRegistroVehiculo == null) {
			if (other.objRegistroVehiculo != null)
				return false;
		} else if (!objRegistroVehiculo.equals(other.objRegistroVehiculo))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "RegistroImagenVehiculo [codRegistroVehiculo=" + codRegistroVehiculo + ", objRegistroVehiculo="
				+ objRegistroVehiculo + ", codImagenVehiculo=" + codImagenVehiculo + ", objImagenVehiculo="
				+ objImagenVehiculo + "]";
	}

	public RegistroImagenVehiculoPK createPK() {
		return new RegistroImagenVehiculoPK(getCodRegistroVehiculo(), getCodImagenVehiculo());
	}

}
