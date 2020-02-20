package es.rfgr.cr4vegas.modelo.postgresql.dto.almacen.tienda;

import java.io.Serializable;

import es.rfgr.cr4vegas.modelo.postgresql.dto.herencia.Direccion;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;

@SuppressWarnings("serial")
public class DirTienda extends Direccion implements Serializable {

	private IntegerProperty codTienda = new SimpleIntegerProperty();
	private ObjectProperty<Tienda> objTienda = new SimpleObjectProperty<Tienda>();

	public DirTienda() {
		super();
	}
	
	public DirTienda(DirTienda dirTienda) {
		copiarValores(dirTienda);
	}
	
	public void copiarValores(DirTienda dirTienda) {
		super.copiarValores(dirTienda);
		this.codTiendaProperty().set(dirTienda.getCodTienda());
		this.objTiendaProperty().set(dirTienda.getObjTienda());
	}

	public final IntegerProperty codTiendaProperty() {
		return this.codTienda;
	}

	public final int getCodTienda() {
		return this.codTiendaProperty().get();
	}

	public final void setCodTienda(final int codTienda) {
		this.codTiendaProperty().set(codTienda);
	}
	
	public final ObjectProperty<Tienda> objTiendaProperty() {
		return this.objTienda;
	}
	
	public final Tienda getObjTienda() {
		return this.objTiendaProperty().get();
	}
	
	public final void setObjTienda(final Tienda tienda) {
		this.objTiendaProperty().set(tienda);
		this.codTiendaProperty().set(tienda.getCodTienda());
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codTienda == null) ? 0 : codTienda.hashCode());
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
		DirTienda other = (DirTienda) obj;
		if (codTienda == null) {
			if (other.codTienda != null)
				return false;
		} else if (codTienda.get() != other.codTienda.get())
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "DirTienda [codTienda=" + codTienda.get() + super.toString();
	}
	
	public DirTiendaPK createPK() {
		return new DirTiendaPK(getCodTienda());
	}
}
