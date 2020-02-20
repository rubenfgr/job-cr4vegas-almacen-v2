package es.rfgr.cr4vegas.modelo.postgresql.dto.mantenimiento.unidaddecontrol;

import java.io.Serializable;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;

@SuppressWarnings("serial")
public class RegistroImagenUnidadDeControlPK implements Serializable {

	private IntegerProperty codRegistroUnidadDeControl = new SimpleIntegerProperty();

	private BooleanProperty codRegistroUnidadDeControlNull = new SimpleBooleanProperty();

	private IntegerProperty codImagenUdControl = new SimpleIntegerProperty();

	private BooleanProperty codImagenUnidadDeControl = new SimpleBooleanProperty();

	public RegistroImagenUnidadDeControlPK(final int codRegistro, final int codImagen) {
		setCodRegistroUnidadDeControl(codRegistro);
		setCodImagenUdControl(codImagen);
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

	public final BooleanProperty codRegistroUnidadDeControlNullProperty() {
		return this.codRegistroUnidadDeControlNull;
	}

	public final boolean isCodRegistroUnidadDeControlNull() {
		return this.codRegistroUnidadDeControlNullProperty().get();
	}

	public final void setCodRegistroUnidadDeControlNull(final boolean codRegistroUnidadDeControlNull) {
		this.codRegistroUnidadDeControlNullProperty().set(codRegistroUnidadDeControlNull);
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

	public final BooleanProperty codImagenUnidadDeControlProperty() {
		return this.codImagenUnidadDeControl;
	}

	public final boolean isCodImagenUnidadDeControl() {
		return this.codImagenUnidadDeControlProperty().get();
	}

	public final void setCodImagenUnidadDeControl(final boolean codImagenUnidadDeControl) {
		this.codImagenUnidadDeControlProperty().set(codImagenUnidadDeControl);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codImagenUdControl == null) ? 0 : codImagenUdControl.hashCode());
		result = prime * result + ((codImagenUnidadDeControl == null) ? 0 : codImagenUnidadDeControl.hashCode());
		result = prime * result + ((codRegistroUnidadDeControl == null) ? 0 : codRegistroUnidadDeControl.hashCode());
		result = prime * result
				+ ((codRegistroUnidadDeControlNull == null) ? 0 : codRegistroUnidadDeControlNull.hashCode());
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
		RegistroImagenUnidadDeControlPK other = (RegistroImagenUnidadDeControlPK) obj;
		if (codImagenUdControl == null) {
			if (other.codImagenUdControl != null)
				return false;
		} else if (!codImagenUdControl.equals(other.codImagenUdControl))
			return false;
		if (codImagenUnidadDeControl == null) {
			if (other.codImagenUnidadDeControl != null)
				return false;
		} else if (!codImagenUnidadDeControl.equals(other.codImagenUnidadDeControl))
			return false;
		if (codRegistroUnidadDeControl == null) {
			if (other.codRegistroUnidadDeControl != null)
				return false;
		} else if (!codRegistroUnidadDeControl.equals(other.codRegistroUnidadDeControl))
			return false;
		if (codRegistroUnidadDeControlNull == null) {
			if (other.codRegistroUnidadDeControlNull != null)
				return false;
		} else if (!codRegistroUnidadDeControlNull.equals(other.codRegistroUnidadDeControlNull))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "RegistroImagenUnidadDeControlPK [codRegistroUnidadDeControl=" + codRegistroUnidadDeControl
				+ ", codRegistroUnidadDeControlNull=" + codRegistroUnidadDeControlNull + ", codImagenUdControl="
				+ codImagenUdControl + ", codImagenUnidadDeControl=" + codImagenUnidadDeControl + "]";
	}

}
