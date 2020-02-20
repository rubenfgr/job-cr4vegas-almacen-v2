package es.rfgr.cr4vegas.modelo.postgresql.dto.mantenimiento.unidaddecontrol;

import java.io.Serializable;

import es.rfgr.cr4vegas.modelo.postgresql.dto.herencia.imagen.Imagen;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;

@SuppressWarnings("serial")
public class ImagenUnidadDeControl extends Imagen implements Serializable {

	private IntegerProperty codUdControl = new SimpleIntegerProperty();

	private ObjectProperty<UnidadDeControl> objUdControl = new SimpleObjectProperty<UnidadDeControl>();

	public ImagenUnidadDeControl() {
		super();
	}

	public ImagenUnidadDeControl(ImagenUnidadDeControl imagenUnidadDeControl) {
		copiarValores(imagenUnidadDeControl);
	}

	public void copiarValores(ImagenUnidadDeControl imagenUnidadDeControl) {
		super.copiarValores(imagenUnidadDeControl);
		this.codUdControlProperty().set(imagenUnidadDeControl.getCodUdControl());
		this.objUdControlProperty().set(imagenUnidadDeControl.getObjUdControl());
	}

	public final IntegerProperty codUdControlProperty() {
		return this.codUdControl;
	}

	public final int getCodUdControl() {
		return this.codUdControlProperty().get();
	}

	public final void setCodUdControl(final int codUdControl) {
		this.codUdControlProperty().set(codUdControl);
	}

	public final ObjectProperty<UnidadDeControl> objUdControlProperty() {
		return this.objUdControl;
	}

	public final UnidadDeControl getObjUdControl() {
		return this.objUdControlProperty().get();
	}

	public final void setObjUdControl(final UnidadDeControl objUdControl) {
		this.objUdControlProperty().set(objUdControl);
		this.codUdControlProperty().set(objUdControl.getCodUdControl());
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + this.codUdControl.get();
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
		ImagenUnidadDeControl other = (ImagenUnidadDeControl) obj;
		if (this.codUdControl.get() != other.codUdControl.get()) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "ImagenUdControl [codUdControl=" + codUdControl + super.toString();
	}

}
