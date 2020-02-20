package es.rfgr.cr4vegas.modelo.postgresql.dto.almacen.taquilla;

import java.io.Serializable;

import es.rfgr.cr4vegas.modelo.postgresql.dto.herencia.opera.Opera;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;

@SuppressWarnings("serial")
public class OperaTaquillaMaterial extends Opera implements Serializable {

	private IntegerProperty codTaquilla = new SimpleIntegerProperty();
	private ObjectProperty<Taquilla> objTaquilla = new SimpleObjectProperty<Taquilla>();

	public OperaTaquillaMaterial() {
		super();
	}

	public OperaTaquillaMaterial(OperaTaquillaMaterial operaTaquillaMaterial) {
		copiarValores(operaTaquillaMaterial);
	}
	
	public void copiarValores(OperaTaquillaMaterial operaTaquillaMaterial) {
		super.copiarValores(operaTaquillaMaterial);
		this.codTaquillaProperty().set(operaTaquillaMaterial.getCodTaquilla());
		this.objTaquillaProperty().set(operaTaquillaMaterial.getObjTaquilla());
	}

	public final IntegerProperty codTaquillaProperty() {
		return this.codTaquilla;
	}

	public final int getCodTaquilla() {
		return this.codTaquillaProperty().get();
	}

	public final void setCodTaquilla(final int codTaquilla) {
		this.codTaquillaProperty().set(codTaquilla);
	}
	
	public final ObjectProperty<Taquilla> objTaquillaProperty() {
		return this.objTaquilla;
	}
	
	public final Taquilla getObjTaquilla() {
		return this.objTaquillaProperty().get();
	}
	
	public final void setObjTaquilla(final Taquilla taquilla) {
		this.objTaquillaProperty().set(taquilla);
		this.codTaquillaProperty().set(taquilla.getCodTaquilla());
	}

	@Override
	public String toString() {
		return "OperaTaquillaMaterial [codTaquilla=" + codTaquilla.get() + super.toString();
	}

	public OperaTaquillaMaterialPK createPK() {
		return new OperaTaquillaMaterialPK(getCodOperacion());
	}
}
