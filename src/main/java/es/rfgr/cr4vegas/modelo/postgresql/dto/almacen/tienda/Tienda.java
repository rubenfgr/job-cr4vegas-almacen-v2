package es.rfgr.cr4vegas.modelo.postgresql.dto.almacen.tienda;

import java.io.Serializable;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

@SuppressWarnings("serial")
public class Tienda implements Serializable {

	private IntegerProperty codTienda = new SimpleIntegerProperty();
	private BooleanProperty activo = new SimpleBooleanProperty();
	private StringProperty nombre = new SimpleStringProperty();
	private StringProperty nif = new SimpleStringProperty();

	public Tienda() {
		// TODO Auto-generated constructor stub
	}

	public Tienda(Tienda tienda) {
		copiarValores(tienda);
	}

	public void copiarValores(Tienda tiendaNewValues) {
		this.codTiendaProperty().set(tiendaNewValues.getCodTienda());
		this.activoProperty().set(tiendaNewValues.isActivo());
		this.nombreProperty().set(tiendaNewValues.getNombre());
		this.nifProperty().set(tiendaNewValues.getNif());
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

	public final BooleanProperty activoProperty() {
		return this.activo;
	}

	public final boolean isActivo() {
		return this.activoProperty().get();
	}

	public final void setActivo(final boolean activo) {
		this.activoProperty().set(activo);
	}

	public final StringProperty nombreProperty() {
		return this.nombre;
	}

	public final String getNombre() {
		return this.nombreProperty().get();
	}

	public final void setNombre(final String nombre) {
		this.nombreProperty().set(nombre);
	}

	public final StringProperty nifProperty() {
		return this.nif;
	}

	public final String getNif() {
		return this.nifProperty().get();
	}

	public final void setNif(final String nif) {
		if (nif != null) {
			this.nifProperty().set(nif);
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + this.codTienda.get();
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
		Tienda other = (Tienda) obj;
		if (this.codTienda.get() != other.codTienda.get()) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return codTienda.get() + " - " + nombre.get();
	}
	
	public TiendaPK createPK() {
		return new TiendaPK(getCodTienda());
	}
}
