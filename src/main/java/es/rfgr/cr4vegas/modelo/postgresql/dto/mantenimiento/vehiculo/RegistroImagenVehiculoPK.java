package es.rfgr.cr4vegas.modelo.postgresql.dto.mantenimiento.vehiculo;

import java.io.Serializable;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;

@SuppressWarnings("serial")
public class RegistroImagenVehiculoPK implements Serializable {

	private IntegerProperty codRegistro = new SimpleIntegerProperty();

	private BooleanProperty codRegistroNull = new SimpleBooleanProperty();

	private IntegerProperty codImagen = new SimpleIntegerProperty();

	private BooleanProperty codImagenNull = new SimpleBooleanProperty();

	public RegistroImagenVehiculoPK(final int codRegistro, final int codImagen) {
		setCodRegistro(codRegistro);
		setCodImagen(codImagen);
	}

	public final IntegerProperty codRegistroProperty() {
		return this.codRegistro;
	}

	public final int getCodRegistro() {
		return this.codRegistroProperty().get();
	}

	public final void setCodRegistro(final int codRegistro) {
		this.codRegistroProperty().set(codRegistro);
	}

	public final BooleanProperty codRegistroNullProperty() {
		return this.codRegistroNull;
	}

	public final boolean isCodRegistroNull() {
		return this.codRegistroNullProperty().get();
	}

	public final void setCodRegistroNull(final boolean codRegistroNull) {
		this.codRegistroNullProperty().set(codRegistroNull);
	}

	public final IntegerProperty codImagenProperty() {
		return this.codImagen;
	}

	public final int getCodImagen() {
		return this.codImagenProperty().get();
	}

	public final void setCodImagen(final int codImagen) {
		this.codImagenProperty().set(codImagen);
	}

	public final BooleanProperty codImagenNullProperty() {
		return this.codImagenNull;
	}

	public final boolean isCodImagenNull() {
		return this.codImagenNullProperty().get();
	}

	public final void setCodImagenNull(final boolean codImagenNull) {
		this.codImagenNullProperty().set(codImagenNull);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codImagen == null) ? 0 : codImagen.hashCode());
		result = prime * result + ((codImagenNull == null) ? 0 : codImagenNull.hashCode());
		result = prime * result + ((codRegistro == null) ? 0 : codRegistro.hashCode());
		result = prime * result + ((codRegistroNull == null) ? 0 : codRegistroNull.hashCode());
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
		RegistroImagenVehiculoPK other = (RegistroImagenVehiculoPK) obj;
		if (codImagen == null) {
			if (other.codImagen != null)
				return false;
		} else if (!codImagen.equals(other.codImagen))
			return false;
		if (codImagenNull == null) {
			if (other.codImagenNull != null)
				return false;
		} else if (!codImagenNull.equals(other.codImagenNull))
			return false;
		if (codRegistro == null) {
			if (other.codRegistro != null)
				return false;
		} else if (!codRegistro.equals(other.codRegistro))
			return false;
		if (codRegistroNull == null) {
			if (other.codRegistroNull != null)
				return false;
		} else if (!codRegistroNull.equals(other.codRegistroNull))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuffer ret = new StringBuffer();
		ret.append("es.rfgr.cr4vegas.modelo.dominio.mantenimiento.vehiculo.RegistroImagenVehiculoPK: ");
		ret.append("codRegistro: " + getCodRegistro());
		ret.append(", codImagen: " + getCodImagen());
		return ret.toString();
	}

}
