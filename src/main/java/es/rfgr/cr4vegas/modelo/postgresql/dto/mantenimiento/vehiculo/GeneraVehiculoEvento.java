package es.rfgr.cr4vegas.modelo.postgresql.dto.mantenimiento.vehiculo;

import java.io.Serializable;

import es.rfgr.cr4vegas.modelo.postgresql.dto.herencia.genera.Genera;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;

@SuppressWarnings("serial")
public class GeneraVehiculoEvento extends Genera implements Serializable {

	private IntegerProperty codMan = new SimpleIntegerProperty();
	
	private ObjectProperty<MantenimientoVehiculo> objMantenimientoVehiculo = new SimpleObjectProperty<MantenimientoVehiculo>();

	public GeneraVehiculoEvento() {
		super();
	}

	public GeneraVehiculoEvento(GeneraVehiculoEvento generaVehiculoEvento) {
		copiarValores(generaVehiculoEvento);
	}
	
	public void copiarValores(GeneraVehiculoEvento generaVehiculoEvento) {
		super.copiarValores(generaVehiculoEvento);
		this.codManProperty().set(generaVehiculoEvento.getCodMan());
		this.objMantenimientoVehiculoProperty().set(generaVehiculoEvento.getObjMantenimientoVehiculo());
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

	public final ObjectProperty<MantenimientoVehiculo> objMantenimientoVehiculoProperty() {
		return this.objMantenimientoVehiculo;
	}

	public final MantenimientoVehiculo getObjMantenimientoVehiculo() {
		return this.objMantenimientoVehiculoProperty().get();
	}

	public final void setObjMantenimientoVehiculo(final MantenimientoVehiculo objMantenimientoVehiculo) {
		this.objMantenimientoVehiculoProperty().set(objMantenimientoVehiculo);
		this.codManProperty().set(objMantenimientoVehiculo.getCodMan());
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
		GeneraVehiculoEvento other = (GeneraVehiculoEvento) obj;
		if (this.codMan.get() != other.codMan.get()) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "GeneraVehiculoEvento [codMan=" + codMan + super.toString();
	}

	public GeneraVehiculoEventoPK createPK() {
		return new GeneraVehiculoEventoPK(getCodEvento());
	}

}
