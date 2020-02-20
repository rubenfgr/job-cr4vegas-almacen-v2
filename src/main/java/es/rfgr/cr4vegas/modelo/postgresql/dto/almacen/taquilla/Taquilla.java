package es.rfgr.cr4vegas.modelo.postgresql.dto.almacen.taquilla;

import java.io.Serializable;
import java.util.Objects;

import es.rfgr.cr4vegas.utileria.OperaMaterial;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

@SuppressWarnings("serial")
public class Taquilla implements OperaMaterial, Serializable {

	private IntegerProperty codTaquilla = new SimpleIntegerProperty();
	private StringProperty tipoTaquilla = new SimpleStringProperty();
	private ObjectProperty<TipoTaquilla> objTipoTaquilla = new SimpleObjectProperty<TipoTaquilla>();
	private BooleanProperty activo = new SimpleBooleanProperty();
	private StringProperty lugar = new SimpleStringProperty();

	public Taquilla() {
		// TODO Auto-generated constructor stub
	}

	public Taquilla(Taquilla taquilla) {
		copiarValores(taquilla);
	}

	public void copiarValores(Taquilla taquilla) {
		this.codTaquillaProperty().set(taquilla.getCodTaquilla());
		this.tipoTaquillaProperty().set(taquilla.getTipoTaquilla());
		this.objTipoTaquillaProperty().set(taquilla.getObjTipoTaquilla());
		this.activoProperty().set(taquilla.isActivo());
		this.lugarProperty().set(taquilla.getLugar());
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

	public final StringProperty tipoTaquillaProperty() {
		return this.tipoTaquilla;
	}

	public final String getTipoTaquilla() {
		return this.tipoTaquillaProperty().get();
	}

	public final void setTipoTaquilla(final String tipoTaquilla) {
		this.tipoTaquillaProperty().set(tipoTaquilla);
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

	public final StringProperty lugarProperty() {
		return this.lugar;
	}

	public final String getLugar() {
		return this.lugarProperty().get();
	}

	public final void setLugar(final String lugar) {
		this.lugarProperty().set(lugar);
	}
	
	public final ObjectProperty<TipoTaquilla> objTipoTaquillaProperty() {
		return this.objTipoTaquilla;
	}
	
	public final TipoTaquilla getObjTipoTaquilla() {
		return this.objTipoTaquillaProperty().get();
	}
	
	public final void setObjTipoTaquilla(final TipoTaquilla objTipoTaquilla) {
		this.objTipoTaquillaProperty().set(objTipoTaquilla);
		this.tipoTaquillaProperty().set(objTipoTaquilla.getNombre());
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Objects.hashCode(this.codTaquilla.get());
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
		Taquilla other = (Taquilla) obj;
		if (this.codTaquilla.get() == other.codTaquilla.get()) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return tipoTaquilla.get() + ", \"" + lugar.get() + "\"";
	}

	public TaquillaPK createPK() {
		return new TaquillaPK(getCodTaquilla());
	}
}
