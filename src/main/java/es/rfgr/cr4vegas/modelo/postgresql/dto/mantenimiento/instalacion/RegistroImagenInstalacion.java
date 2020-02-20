package es.rfgr.cr4vegas.modelo.postgresql.dto.mantenimiento.instalacion;

import java.io.Serializable;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;

@SuppressWarnings("serial")
public class RegistroImagenInstalacion implements Serializable {

	private IntegerProperty codRegistroInstalacion = new SimpleIntegerProperty();

	private ObjectProperty<RegistroInstalacion> objRegistroInstalacion = new SimpleObjectProperty<RegistroInstalacion>();

	private IntegerProperty codImagenInstalacion = new SimpleIntegerProperty();

	private ObjectProperty<ImagenInstalacion> objImagenInstalacion = new SimpleObjectProperty<ImagenInstalacion>();

	public RegistroImagenInstalacion() {
		
	}
	
	public RegistroImagenInstalacion(RegistroImagenInstalacion registroImagenInstalacion) {
		copiarValores(registroImagenInstalacion);
	}

	public void copiarValores(RegistroImagenInstalacion registroImagenInstalacion) {
		this.codRegistroInstalacionProperty().set(registroImagenInstalacion.getCodRegistroInstalacion());
		this.objRegistroInstalacionProperty().set(registroImagenInstalacion.getObjRegistroInstalacion());
		this.codImagenInstalacionProperty().set(registroImagenInstalacion.getCodImagenInstalacion());
		this.objImagenInstalacionProperty().set(registroImagenInstalacion.getObjImagenInstalacion());
	}

	public final IntegerProperty codRegistroInstalacionProperty() {
		return this.codRegistroInstalacion;
	}

	public final int getCodRegistroInstalacion() {
		return this.codRegistroInstalacionProperty().get();
	}

	public final void setCodRegistroInstalacion(final int codRegistroInstalacion) {
		this.codRegistroInstalacionProperty().set(codRegistroInstalacion);
	}

	public final ObjectProperty<RegistroInstalacion> objRegistroInstalacionProperty() {
		return this.objRegistroInstalacion;
	}

	public final RegistroInstalacion getObjRegistroInstalacion() {
		return this.objRegistroInstalacionProperty().get();
	}

	public final void setObjRegistroInstalacion(final RegistroInstalacion objRegistroInstalacion) {
		this.objRegistroInstalacionProperty().set(objRegistroInstalacion);
	}

	public final IntegerProperty codImagenInstalacionProperty() {
		return this.codImagenInstalacion;
	}

	public final int getCodImagenInstalacion() {
		return this.codImagenInstalacionProperty().get();
	}

	public final void setCodImagenInstalacion(final int codImagenInstalacion) {
		this.codImagenInstalacionProperty().set(codImagenInstalacion);
	}

	public final ObjectProperty<ImagenInstalacion> objImagenInstalacionProperty() {
		return this.objImagenInstalacion;
	}

	public final ImagenInstalacion getObjImagenInstalacion() {
		return this.objImagenInstalacionProperty().get();
	}

	public final void setObjImagenInstalacion(final ImagenInstalacion objImagenInstalacion) {
		this.objImagenInstalacionProperty().set(objImagenInstalacion);
		this.codImagenInstalacionProperty().set(objImagenInstalacion.getCodImagen());
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codImagenInstalacion == null) ? 0 : codImagenInstalacion.hashCode());
		result = prime * result + ((codRegistroInstalacion == null) ? 0 : codRegistroInstalacion.hashCode());
		result = prime * result + ((objImagenInstalacion == null) ? 0 : objImagenInstalacion.hashCode());
		result = prime * result + ((objRegistroInstalacion == null) ? 0 : objRegistroInstalacion.hashCode());
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
		RegistroImagenInstalacion other = (RegistroImagenInstalacion) obj;
		if (codImagenInstalacion == null) {
			if (other.codImagenInstalacion != null)
				return false;
		} else if (!codImagenInstalacion.equals(other.codImagenInstalacion))
			return false;
		if (codRegistroInstalacion == null) {
			if (other.codRegistroInstalacion != null)
				return false;
		} else if (!codRegistroInstalacion.equals(other.codRegistroInstalacion))
			return false;
		if (objImagenInstalacion == null) {
			if (other.objImagenInstalacion != null)
				return false;
		} else if (!objImagenInstalacion.equals(other.objImagenInstalacion))
			return false;
		if (objRegistroInstalacion == null) {
			if (other.objRegistroInstalacion != null)
				return false;
		} else if (!objRegistroInstalacion.equals(other.objRegistroInstalacion))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "RegistroImagenInstalacion [codRegistroInstalacion=" + codRegistroInstalacion
				+ ", objRegistroInstalacion=" + objRegistroInstalacion + ", codImagenInstalacion="
				+ codImagenInstalacion + ", objImagenInstalacion=" + objImagenInstalacion + "]";
	}

	public RegistroImagenInstalacionPK createPK() {
		return new RegistroImagenInstalacionPK(getCodRegistroInstalacion(), getCodImagenInstalacion());
	}

}
