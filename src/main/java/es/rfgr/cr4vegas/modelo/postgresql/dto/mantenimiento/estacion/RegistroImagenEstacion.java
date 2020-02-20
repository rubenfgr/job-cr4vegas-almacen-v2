package es.rfgr.cr4vegas.modelo.postgresql.dto.mantenimiento.estacion;

import java.io.Serializable;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;

@SuppressWarnings("serial")
public class RegistroImagenEstacion implements Serializable {

	private IntegerProperty codRegistroEstacion = new SimpleIntegerProperty();

	private ObjectProperty<RegistroEstacion> objRegistroEstacion = new SimpleObjectProperty<RegistroEstacion>();

	private IntegerProperty codImagenEstacion = new SimpleIntegerProperty();

	private ObjectProperty<ImagenEstacion> objImagenEstacion = new SimpleObjectProperty<ImagenEstacion>();

	public RegistroImagenEstacion() {
		super();
	}

	public RegistroImagenEstacion(RegistroImagenEstacion registroImagenEstacion) {
		copiarValores(registroImagenEstacion);
	}

	public void copiarValores(RegistroImagenEstacion registroImagenEstacion) {
		this.codRegistroEstacionProperty().set(registroImagenEstacion.getCodRegistroEstacion());
		this.objRegistroEstacionProperty().set(registroImagenEstacion.getObjRegistroEstacion());
		this.codImagenEstacionProperty().set(registroImagenEstacion.getCodImagenEstacion());
		this.objImagenEstacionProperty().set(registroImagenEstacion.getObjImagenEstacion());
	}

	public RegistroImagenEstacionPK createPK() {
		return new RegistroImagenEstacionPK(getCodRegistroEstacion(), getCodImagenEstacion());
	}

	public final IntegerProperty codRegistroEstacionProperty() {
		return this.codRegistroEstacion;
	}

	public final int getCodRegistroEstacion() {
		return this.codRegistroEstacionProperty().get();
	}

	public final void setCodRegistroEstacion(final int codRegistroEstacion) {
		this.codRegistroEstacionProperty().set(codRegistroEstacion);
	}

	public final ObjectProperty<RegistroEstacion> objRegistroEstacionProperty() {
		return this.objRegistroEstacion;
	}

	public final RegistroEstacion getObjRegistroEstacion() {
		return this.objRegistroEstacionProperty().get();
	}

	public final void setObjRegistroEstacion(final RegistroEstacion objRegistroEstacion) {
		this.objRegistroEstacionProperty().set(objRegistroEstacion);
	}

	public final IntegerProperty codImagenEstacionProperty() {
		return this.codImagenEstacion;
	}

	public final int getCodImagenEstacion() {
		return this.codImagenEstacionProperty().get();
	}

	public final void setCodImagenEstacion(final int codImagenEstacion) {
		this.codImagenEstacionProperty().set(codImagenEstacion);
	}

	public final ObjectProperty<ImagenEstacion> objImagenEstacionProperty() {
		return this.objImagenEstacion;
	}

	public final ImagenEstacion getObjImagenEstacion() {
		return this.objImagenEstacionProperty().get();
	}

	public final void setObjImagenEstacion(final ImagenEstacion objImagenEstacion) {
		this.objImagenEstacionProperty().set(objImagenEstacion);
		this.codImagenEstacionProperty().set(objImagenEstacion.getCodImagen());
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + this.codImagenEstacion.get();
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
		RegistroImagenEstacion other = (RegistroImagenEstacion) obj;
		if (this.codImagenEstacion.get() != other.codImagenEstacion.get()) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "RegistroImagenEstacion [codImagenEstacion=" + codImagenEstacion.get() + super.toString();
	}

}
