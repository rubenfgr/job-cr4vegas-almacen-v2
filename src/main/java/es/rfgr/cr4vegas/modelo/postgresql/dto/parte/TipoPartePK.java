package es.rfgr.cr4vegas.modelo.postgresql.dto.parte;

import java.io.Serializable;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;

@SuppressWarnings("serial")
public class TipoPartePK implements Serializable {

	private IntegerProperty codigo = new SimpleIntegerProperty();
	
	private BooleanProperty nombreNull = new SimpleBooleanProperty();
	
	public TipoPartePK(final int codigo) {
		setCodigo(codigo);
	}

	public final IntegerProperty codigoProperty() {
		return this.codigo;
	}
	
	public final int getCodigo() {
		return this.codigoProperty().get();
	}

	public final void setCodigo(final int codigo) {
		this.codigoProperty().set(codigo);
	}
	
	public final BooleanProperty nombreNullProperty() {
		return this.nombreNull;
	}
	
	public final boolean isNombreNull() {
		return this.nombreNullProperty().get();
	}
	
	public final void setNombreNull(final boolean nombreNull) {
		this.nombreNullProperty().set(nombreNull);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codigo == null) ? 0 : codigo.hashCode());
		result = prime * result + ((nombreNull == null) ? 0 : nombreNull.hashCode());
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
		TipoPartePK other = (TipoPartePK) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (codigo != other.codigo)
			return false;
		if (nombreNull == null) {
			if (other.nombreNull != null)
				return false;
		} else if (!nombreNull.equals(other.nombreNull))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuffer ret = new StringBuffer();
		ret.append("es.rfgr.cr4vegas.modelo.dominio.parte.TipoPartePK: ");
		ret.append("codigo: " + getCodigo());
		return ret.toString();
	}
	
}
