package es.rfgr.cr4vegas.modelo.postgresql.dto.parte;

import java.io.Serializable;
import java.util.Objects;

import es.rfgr.cr4vegas.utileria.ObjetoSimple;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

@SuppressWarnings("serial")
public class TipoParte implements ObjetoSimple, Serializable {

	private StringProperty nombre = new SimpleStringProperty();
	private IntegerProperty codigo = new SimpleIntegerProperty();

	public TipoParte() {

	}

	public TipoParte(TipoParte tipoParte) {
		copiarValores(tipoParte);
	}

	public void copiarValores(TipoParte tipoParte) {
		this.nombreProperty().set(tipoParte.getNombre());
		this.codigoProperty().set(tipoParte.getCodigo());
	}

	public final StringProperty nombreProperty() {
		return this.nombre;
	}

	public final String getNombre() {
		return this.nombreProperty().get();
	}

	public final void setNombre(final String nombre) {
		if (nombre == null) {
			throw new NullPointerException("El nombre para el tipo de parte no puede ser nulo.");
		}
		this.nombreProperty().set(nombre);
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Objects.hashCode(this.nombre.get());
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
		TipoParte other = (TipoParte) obj;
		if (!Objects.equals(this.nombre.get(), other.nombre.getBean())) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return codigo.get() + " - " + nombre.get();
	}

	public TipoPartePK createPK() {
		return new TipoPartePK(getCodigo());
	}
	
}
