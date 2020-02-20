package es.rfgr.cr4vegas.modelo.postgresql.dto.mantenimiento.unidaddecontrol;

import java.io.Serializable;

import es.rfgr.cr4vegas.modelo.postgresql.dto.herencia.genera.Genera;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;

@SuppressWarnings("serial")
public class GeneraUnidadDeControlEvento extends Genera implements Serializable {

	private IntegerProperty codMan = new SimpleIntegerProperty();
	private ObjectProperty<MantenimientoUnidadDeControl> objMantenimientoUdControl = new SimpleObjectProperty<MantenimientoUnidadDeControl>();

	public GeneraUnidadDeControlEvento() {
		super();
	}

	public GeneraUnidadDeControlEvento(GeneraUnidadDeControlEvento generaUdControlEvento) {
		copiarValores(generaUdControlEvento);
	}
	
	public void copiarValores(GeneraUnidadDeControlEvento generaUdControlEvento) {
		super.copiarValores(generaUdControlEvento);
		this.codManProperty().set(generaUdControlEvento.getCodMan());
		this.objMantenimientoUdControlProperty().set(generaUdControlEvento.getObjMantenimientoUdControl());
	}

	public final IntegerProperty codManProperty() {
		return this.codMan;
	}
	
	public final int getCodMan() {
		return this.codManProperty().get();
	}
	
	public final void setCodMan(final int codMan) {
		this.codManProperty().set(codMan);
	}
	
	public final ObjectProperty<MantenimientoUnidadDeControl> objMantenimientoUdControlProperty() {
		return this.objMantenimientoUdControl;
	}
	
	public final MantenimientoUnidadDeControl getObjMantenimientoUdControl() {
		return this.objMantenimientoUdControlProperty().get();
	}
	
	public final void setObjMantenimientoUdControl(final MantenimientoUnidadDeControl codManUdControl) {
		this.objMantenimientoUdControlProperty().set(codManUdControl);
		this.codManProperty().set(codManUdControl.getCodMan());
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + this.codMan.get();
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
		GeneraUnidadDeControlEvento other = (GeneraUnidadDeControlEvento) obj;
		if (this.codMan.get() != other.codMan.get()) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "GeneraUdControlEvento [codMan=" + codMan + super.toString();
	}
	
	public GeneraUnidadDeControlEventoPK createPK() {
		return new GeneraUnidadDeControlEventoPK(getCodEvento());
	}

}
