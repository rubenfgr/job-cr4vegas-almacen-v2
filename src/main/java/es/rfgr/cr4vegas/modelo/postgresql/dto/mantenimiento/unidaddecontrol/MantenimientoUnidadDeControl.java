package es.rfgr.cr4vegas.modelo.postgresql.dto.mantenimiento.unidaddecontrol;

import java.io.Serializable;

import es.rfgr.cr4vegas.modelo.postgresql.dto.herencia.mantenimiento.Mantenimiento;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;

@SuppressWarnings("serial")
public class MantenimientoUnidadDeControl extends Mantenimiento implements Serializable {

	private IntegerProperty codUdControl = new SimpleIntegerProperty();
	private ObjectProperty<UnidadDeControl> objUdControl = new SimpleObjectProperty<UnidadDeControl>();

	public MantenimientoUnidadDeControl() {
		super();
	}

	public MantenimientoUnidadDeControl(MantenimientoUnidadDeControl mantenimientoUdControl) {
		copiarValores(mantenimientoUdControl);
	}

	public void copiarValores(MantenimientoUnidadDeControl mantenimientoUdControl) {
		super.copiarValores(mantenimientoUdControl);
		this.codUdControlProperty().set(mantenimientoUdControl.getCodUdControl());
		this.objUdControlProperty().set(mantenimientoUdControl.getObjUdControl());
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
		MantenimientoUnidadDeControl other = (MantenimientoUnidadDeControl) obj;
		if (this.codUdControl.get() != other.codUdControl.get()) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "MantenimientoUdControl [codUdControl=" + codUdControl + super.toString();
	}

	public MantenimientoUnidadDeControlPK createPK() {
		return new MantenimientoUnidadDeControlPK(getCodMan());
	}
}
