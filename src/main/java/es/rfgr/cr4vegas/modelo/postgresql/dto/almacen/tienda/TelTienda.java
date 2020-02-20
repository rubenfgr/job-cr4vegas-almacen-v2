package es.rfgr.cr4vegas.modelo.postgresql.dto.almacen.tienda;

import java.io.Serializable;

import es.rfgr.cr4vegas.modelo.postgresql.dto.herencia.telefono.Telefono;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;

@SuppressWarnings("serial")
public class TelTienda extends Telefono implements Serializable {

	private IntegerProperty codTienda = new SimpleIntegerProperty();
	private ObjectProperty<Tienda> objTienda = new SimpleObjectProperty<Tienda>();

	public TelTienda() {
		// TODO Auto-generated constructor stub
	}
	
	public TelTienda(TelTienda telTienda) {
		copiarValores(telTienda);
	}
	
	public void copiarValores(TelTienda telTienda) {
		super.copiarValores(telTienda);
		this.codTiendaProperty().set(telTienda.getCodTienda());
		this.objTiendaProperty().set(telTienda.getObjTienda());
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
	public String toString() {
		return super.toString();
	}
	
	public TelTiendaPK createPK() {
		return new TelTiendaPK(getTelefono());
	}
}
