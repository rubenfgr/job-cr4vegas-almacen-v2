package es.rfgr.cr4vegas.modelo.postgresql.dto.almacen.compra;

import java.io.Serializable;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;

@SuppressWarnings("serial")
public class CompraPK implements Serializable {

	protected IntegerProperty codCompra = new SimpleIntegerProperty();
	
	protected BooleanProperty codCompraNull = new SimpleBooleanProperty();
	
	public CompraPK() {
		
	}
	
	public CompraPK(final int codCompra) {
		setCodCompra(codCompra);
	}
	
	public final IntegerProperty codCompraProperty() {
		return this.codCompra;
	}
	
	public final int getCodCompra() {
		return this.codCompraProperty().get();
	}

	public final void setCodCompra(final int codCompra) {
		this.codCompraProperty().set(codCompra);
	}

	public final BooleanProperty codCompraNullProperty() {
		return this.codCompraNull;
	}

	public final boolean isCodCompraNull() {
		return this.codCompraNullProperty().get();
	}

	public final void setCodCompraNull(final boolean codCompraNull) {
		this.codCompraNullProperty().set(codCompraNull);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codCompra == null) ? 0 : codCompra.hashCode());
		result = prime * result + ((codCompraNull == null) ? 0 : codCompraNull.hashCode());
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
		CompraPK other = (CompraPK) obj;
		if (codCompra == null) {
			if (other.codCompra != null)
				return false;
		} else if (!codCompra.equals(other.codCompra))
			return false;
		if (codCompraNull == null) {
			if (other.codCompraNull != null)
				return false;
		} else if (!codCompraNull.equals(other.codCompraNull))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuffer ret = new StringBuffer();
		ret.append("es.rfgr.cr4vegas.modelo.dominio.almacen.compra.CompraPK: ");
		ret.append("codCompra= " + codCompra);
		return ret.toString();
	}

}
