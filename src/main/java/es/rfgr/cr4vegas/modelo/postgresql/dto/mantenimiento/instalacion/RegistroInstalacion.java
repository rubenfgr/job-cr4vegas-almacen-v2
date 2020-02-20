package es.rfgr.cr4vegas.modelo.postgresql.dto.mantenimiento.instalacion;

import java.io.Serializable;

import es.rfgr.cr4vegas.modelo.postgresql.dto.herencia.registro.Registro;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;

@SuppressWarnings("serial")
public class RegistroInstalacion extends Registro implements Serializable {

	private IntegerProperty codInstalacion = new SimpleIntegerProperty();
	
	private ObjectProperty<Instalacion> objInstalacion = new SimpleObjectProperty<Instalacion>();

	public RegistroInstalacion() {
		super();
	}

	public RegistroInstalacion(RegistroInstalacion registroInstalacion) {
		copiarValores(registroInstalacion);
	}
	
	public void copiarValores(RegistroInstalacion registroInstalacion) {
		super.copiarValores(registroInstalacion);
		this.codInstalacionProperty().set(registroInstalacion.getCodInstalacion());
		this.objInstalacionProperty().set(registroInstalacion.getObjInstalacion());
	}

	public final IntegerProperty codInstalacionProperty() {
		return this.codInstalacion;
	}

	public final int getCodInstalacion() {
		return this.codInstalacionProperty().get();
	}

	public final void setCodInstalacion(final int codInstalacion) {
		this.codInstalacionProperty().set(codInstalacion);
	}

	public final ObjectProperty<Instalacion> objInstalacionProperty() {
		return this.objInstalacion;
	}

	public final Instalacion getObjInstalacion() {
		return this.objInstalacionProperty().get();
	}

	public final void setObjInstalacion(final Instalacion objInstalacion) {
		this.objInstalacionProperty().set(objInstalacion);
		this.codInstalacionProperty().set(objInstalacion.getCodInstalacion());
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + this.codInstalacion.get();
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
		RegistroInstalacion other = (RegistroInstalacion) obj;
		if (this.codInstalacion.get() != other.codInstalacion.get()) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "RegistroInstalacion [codInstalacion=" + codInstalacion + super.toString();
	}
	
	public RegistroInstalacionPK createPK() {
		return new RegistroInstalacionPK(getCodRegistro());
	}

}
