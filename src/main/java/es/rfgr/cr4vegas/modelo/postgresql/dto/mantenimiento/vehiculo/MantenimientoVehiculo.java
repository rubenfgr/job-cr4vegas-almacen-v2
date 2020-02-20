package es.rfgr.cr4vegas.modelo.postgresql.dto.mantenimiento.vehiculo;

import java.io.Serializable;

import es.rfgr.cr4vegas.modelo.postgresql.dto.herencia.mantenimiento.Mantenimiento;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

@SuppressWarnings("serial")
public class MantenimientoVehiculo extends Mantenimiento implements Serializable {

	private StringProperty matricula = new SimpleStringProperty();
	
	private ObjectProperty<Vehiculo> objVehiculo = new SimpleObjectProperty<Vehiculo>();

	public MantenimientoVehiculo() {
		super();
	}

	public MantenimientoVehiculo(MantenimientoVehiculo mantenimientoVehiculo) {
		copiarValores(mantenimientoVehiculo);
	}
	
	public void copiarValores(MantenimientoVehiculo mantenimientoVehiculo) {
		super.copiarValores(mantenimientoVehiculo);
		this.matriculaProperty().set(mantenimientoVehiculo.getMatricula());
		this.objVehiculoProperty().set(mantenimientoVehiculo.getObjVehiculo());
	}

	public final StringProperty matriculaProperty() {
		return this.matricula;
	}

	public final String getMatricula() {
		return this.matriculaProperty().get();
	}

	public final void setMatricula(final String matricula) {
		this.matriculaProperty().set(matricula);
	}

	public final ObjectProperty<Vehiculo> objVehiculoProperty() {
		return this.objVehiculo;
	}

	public final Vehiculo getObjVehiculo() {
		return this.objVehiculoProperty().get();
	}

	public final void setObjVehiculo(final Vehiculo objVehiculo) {
		this.objVehiculoProperty().set(objVehiculo);
		this.matriculaProperty().set(objVehiculo.getMatricula());
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((matricula == null) ? 0 : matricula.hashCode());
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
		MantenimientoVehiculo other = (MantenimientoVehiculo) obj;
		if (matricula == null) {
			if (other.matricula != null)
				return false;
		} else if (!matricula.equals(other.matricula))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "MantenimientoVehiculo [matricula=" + matricula.get() + super.toString();
	}

	public MantenimientoVehiculoPK createPK() {
		return new MantenimientoVehiculoPK(getCodMan());
	}
}
