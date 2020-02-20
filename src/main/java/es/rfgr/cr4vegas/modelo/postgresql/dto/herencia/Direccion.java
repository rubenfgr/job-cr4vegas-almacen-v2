package es.rfgr.cr4vegas.modelo.postgresql.dto.herencia;

import java.io.Serializable;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

@SuppressWarnings("serial")
public abstract class Direccion implements Serializable {

	private static final String CP_VALIDO = "[0-9]{5}";

	private StringProperty localidad = new SimpleStringProperty();
	private StringProperty cp = new SimpleStringProperty();
	private StringProperty calle = new SimpleStringProperty();
	private IntegerProperty numero = new SimpleIntegerProperty();
	private IntegerProperty planta = new SimpleIntegerProperty();
	private StringProperty numPlanta = new SimpleStringProperty();

	protected void copiarValores(Direccion direccion) {
		this.localidadProperty().set(direccion.getLocalidad());
		this.cpProperty().set(direccion.getCp());
		this.calleProperty().set(direccion.getCalle());
		this.numeroProperty().set(direccion.getNumero());
		this.plantaProperty().set(direccion.getPlanta());
		this.numPlantaProperty().set(direccion.getNumPlanta());
	}

	public final StringProperty localidadProperty() {
		return this.localidad;
	}

	public final String getLocalidad() {
		return this.localidadProperty().get();
	}

	public final void setLocalidad(final String localidad) {
		this.localidadProperty().set(localidad);
	}

	public final StringProperty cpProperty() {
		return this.cp;
	}

	public final String getCp() {
		return this.cpProperty().get();
	}

	public final void setCp(final String cp) {
		if (cp != null) {
			if (!cp.equals("") & !cp.matches(CP_VALIDO)) {
				throw new IllegalArgumentException("El código postal no es válido. ER = [0-9]{5}");
			}
		}
		this.cpProperty().set(cp);
	}

	public final StringProperty calleProperty() {
		return this.calle;
	}

	public final String getCalle() {
		return this.calleProperty().get();
	}

	public final void setCalle(final String calle) {
		this.calleProperty().set(calle);
	}

	public final IntegerProperty numeroProperty() {
		return this.numero;
	}

	public final int getNumero() {
		return this.numeroProperty().get();
	}

	public final void setNumero(final int numero) {
		this.numeroProperty().set(numero);
	}

	public void setNumero(String numero) {
		if (numero != null && !numero.equals("")) {
			try {
				this.numeroProperty().set(Integer.parseInt(numero));
			} catch (NumberFormatException e) {
				e.printStackTrace();
				throw new IllegalArgumentException("El número de la calle no es correcto");
			}
		}
	}

	public final IntegerProperty plantaProperty() {
		return this.planta;
	}

	public final int getPlanta() {
		return this.plantaProperty().get();
	}

	public final void setPlanta(final int planta) {
		this.plantaProperty().set(planta);
	}

	public final void setPlanta(final String planta) {
		try {
			if (planta != null && !planta.equals("")) {
				this.plantaProperty().set(Integer.valueOf(planta));
			}
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException("La planta no es correcta");
		}
	}

	public final StringProperty numPlantaProperty() {
		return this.numPlanta;
	}

	public final String getNumPlanta() {
		return this.numPlantaProperty().get();
	}

	public final void setNumPlanta(final String numPlanta) {
		this.numPlantaProperty().set(numPlanta);
	}

	@Override
	public abstract int hashCode();

	@Override
	public abstract boolean equals(Object obj);

	@Override
	public String toString() {
		return ", localidad=" + localidad.get() + ", cp=" + cp.get() + ", calle=" + calle.get() + ", numero="
				+ numero.get() + ", planta=" + planta.get() + ", numPlanta=" + numPlanta.get() + "]";
	}

}
