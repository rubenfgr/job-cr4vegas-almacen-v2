package es.rfgr.cr4vegas.modelo.postgresql.dto.mantenimiento.unidaddecontrol;

import java.io.Serializable;

import es.rfgr.cr4vegas.modelo.postgresql.dto.herencia.registro.Registro;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;

@SuppressWarnings("serial")
public class RegistroUnidadDeControl extends Registro implements Serializable {

	private IntegerProperty codUnidadDeControl = new SimpleIntegerProperty();

	private ObjectProperty<UnidadDeControl> objUnidadDeControl = new SimpleObjectProperty<UnidadDeControl>();

	public RegistroUnidadDeControl() {
		super();
	}

	public RegistroUnidadDeControl(RegistroUnidadDeControl registroUnidadDeControl) {
		copiarValores(registroUnidadDeControl);
	}

	public void copiarValores(RegistroUnidadDeControl registroUnidadDeControl) {
		super.copiarValores(registroUnidadDeControl);
		this.codUdControlProperty().set(registroUnidadDeControl.getCodUdControl());
		this.objUdControlProperty().set(registroUnidadDeControl.getObjUdControl());
	}

	public final IntegerProperty codUdControlProperty() {
		return this.codUnidadDeControl;
	}

	public final int getCodUdControl() {
		return this.codUdControlProperty().get();
	}

	public final void setCodUdControl(final int codUdControl) {
		this.codUdControlProperty().set(codUdControl);
	}

	public final ObjectProperty<UnidadDeControl> objUdControlProperty() {
		return this.objUnidadDeControl;
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
		int result = 1;
		result = prime * result + this.codUnidadDeControl.get();
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
		RegistroUnidadDeControl other = (RegistroUnidadDeControl) obj;
		if (this.codUnidadDeControl.get() != other.codUnidadDeControl.get()) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "RegistroUdControl [codUdControl=" + codUnidadDeControl + super.toString();
	}

	public RegistroUnidadDeControlPK createPK() {
		return new RegistroUnidadDeControlPK(getCodRegistro());
	}

}
