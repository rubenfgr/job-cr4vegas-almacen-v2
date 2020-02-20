package es.rfgr.cr4vegas.modelo.postgresql.dto.mantenimiento.unidaddecontrol;

import java.io.Serializable;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;

@SuppressWarnings("serial")
public class RegistroImagenUnidadDeControl implements Serializable {

	private IntegerProperty codRegistroUnidadDeControl = new SimpleIntegerProperty();

	private ObjectProperty<RegistroUnidadDeControl> objRegistroUnidadDeControl = new SimpleObjectProperty<RegistroUnidadDeControl>();

	private IntegerProperty codImagenUdControl = new SimpleIntegerProperty();

	private ObjectProperty<ImagenUnidadDeControl> objImagenUdControl = new SimpleObjectProperty<ImagenUnidadDeControl>();

	public RegistroImagenUnidadDeControl() {

	}

	public RegistroImagenUnidadDeControl(RegistroImagenUnidadDeControl registroImagenUdControl) {
		copiarValores(registroImagenUdControl);
	}

	public void copiarValores(RegistroImagenUnidadDeControl registroImagenUdControl) {
		this.codRegistroUnidadDeControlProperty().set(registroImagenUdControl.getCodRegistroUnidadDeControl());
		this.objRegistroUnidadDeControlProperty().set(registroImagenUdControl.getObjRegistroUnidadDeControl());
		this.codImagenUdControlProperty().set(registroImagenUdControl.getCodImagenUdControl());
		this.objImagenUdControlProperty().set(registroImagenUdControl.getObjImagenUdControl());
	}

	public final IntegerProperty codRegistroUnidadDeControlProperty() {
		return this.codRegistroUnidadDeControl;
	}

	public final int getCodRegistroUnidadDeControl() {
		return this.codRegistroUnidadDeControlProperty().get();
	}

	public final void setCodRegistroUnidadDeControl(final int codRegistroUnidadDeControl) {
		this.codRegistroUnidadDeControlProperty().set(codRegistroUnidadDeControl);
	}

	public final ObjectProperty<RegistroUnidadDeControl> objRegistroUnidadDeControlProperty() {
		return this.objRegistroUnidadDeControl;
	}

	public final RegistroUnidadDeControl getObjRegistroUnidadDeControl() {
		return this.objRegistroUnidadDeControlProperty().get();
	}

	public final void setObjRegistroUnidadDeControl(final RegistroUnidadDeControl objRegistroUnidadDeControl) {
		this.objRegistroUnidadDeControlProperty().set(objRegistroUnidadDeControl);
	}

	public final IntegerProperty codImagenUdControlProperty() {
		return this.codImagenUdControl;
	}

	public final int getCodImagenUdControl() {
		return this.codImagenUdControlProperty().get();
	}

	public final void setCodImagenUdControl(final int codImagenUdControl) {
		this.codImagenUdControlProperty().set(codImagenUdControl);
	}

	public final ObjectProperty<ImagenUnidadDeControl> objImagenUdControlProperty() {
		return this.objImagenUdControl;
	}

	public final ImagenUnidadDeControl getObjImagenUdControl() {
		return this.objImagenUdControlProperty().get();
	}

	public final void setObjImagenUdControl(final ImagenUnidadDeControl objImagenUdControl) {
		this.objImagenUdControlProperty().set(objImagenUdControl);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codImagenUdControl == null) ? 0 : codImagenUdControl.hashCode());
		result = prime * result + ((codRegistroUnidadDeControl == null) ? 0 : codRegistroUnidadDeControl.hashCode());
		result = prime * result + ((objImagenUdControl == null) ? 0 : objImagenUdControl.hashCode());
		result = prime * result + ((objRegistroUnidadDeControl == null) ? 0 : objRegistroUnidadDeControl.hashCode());
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
		RegistroImagenUnidadDeControl other = (RegistroImagenUnidadDeControl) obj;
		if (codImagenUdControl == null) {
			if (other.codImagenUdControl != null)
				return false;
		} else if (!codImagenUdControl.equals(other.codImagenUdControl))
			return false;
		if (codRegistroUnidadDeControl == null) {
			if (other.codRegistroUnidadDeControl != null)
				return false;
		} else if (!codRegistroUnidadDeControl.equals(other.codRegistroUnidadDeControl))
			return false;
		if (objImagenUdControl == null) {
			if (other.objImagenUdControl != null)
				return false;
		} else if (!objImagenUdControl.equals(other.objImagenUdControl))
			return false;
		if (objRegistroUnidadDeControl == null) {
			if (other.objRegistroUnidadDeControl != null)
				return false;
		} else if (!objRegistroUnidadDeControl.equals(other.objRegistroUnidadDeControl))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "RegistroImagenUnidadDeControl [codRegistroUnidadDeControl=" + codRegistroUnidadDeControl
				+ ", objRegistroUnidadDeControl=" + objRegistroUnidadDeControl + ", codImagenUdControl="
				+ codImagenUdControl + ", objImagenUdControl=" + objImagenUdControl + "]";
	}

	public RegistroImagenUnidadDeControlPK createPK() {
		return new RegistroImagenUnidadDeControlPK(getCodRegistroUnidadDeControl(), getCodImagenUdControl());
	}

}
