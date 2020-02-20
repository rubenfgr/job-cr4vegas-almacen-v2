package es.rfgr.cr4vegas.modelo.postgresql.dto.herencia.opera;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

import es.rfgr.cr4vegas.modelo.postgresql.dto.almacen.material.Material;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;

@SuppressWarnings("serial")
public abstract class Opera implements Serializable {

	private IntegerProperty codOperacion = new SimpleIntegerProperty();
	private IntegerProperty codigo4v = new SimpleIntegerProperty();
	private ObjectProperty<Material> objMaterial = new SimpleObjectProperty<Material>();
	private IntegerProperty cantidad = new SimpleIntegerProperty();
	private ObjectProperty<LocalDateTime> fecha = new SimpleObjectProperty<LocalDateTime>();
	
	protected void copiarValores(Opera opera) {
		this.codOperacionProperty().set(opera.getCodOperacion());
		this.codigo4vProperty().set(opera.getCodigo4v());
		this.objMaterialProperty().set(opera.getObjMaterial());
		this.cantidadProperty().set(opera.getCantidad());
		this.fechaProperty().set(opera.getFecha());
	}

	public final IntegerProperty codOperacionProperty() {
		return this.codOperacion;
	}

	public final int getCodOperacion() {
		return this.codOperacionProperty().get();
	}

	public final void setCodOperacion(final int codOperacion) {
		this.codOperacionProperty().set(codOperacion);
	}

	public final IntegerProperty codigo4vProperty() {
		return this.codigo4v;
	}

	public final int getCodigo4v() {
		return this.codigo4vProperty().get();
	}

	public final void setCodigo4v(final int codigo4v) {
		this.codigo4vProperty().set(codigo4v);
	}

	public final IntegerProperty cantidadProperty() {
		return this.cantidad;
	}

	public final int getCantidad() {
		return this.cantidadProperty().get();
	}

	public final void setCantidad(final int cantidad) {
		this.cantidadProperty().set(cantidad);
	}

	public final void setCantidad(final String cantidad) {
		try {
			this.cantidadProperty().set(Integer.valueOf(cantidad));
		} catch (NumberFormatException e) {
			e.printStackTrace();
			throw new IllegalArgumentException("El formato de la cantidad no es correcto.");
		}
	}

	public final ObjectProperty<LocalDateTime> fechaProperty() {
		return this.fecha;
	}

	public final LocalDateTime getFecha() {
		return this.fechaProperty().get();
	}

	public void setFecha(LocalDateTime fecha) {
		this.fechaProperty().set(fecha);
	}

	public final ObjectProperty<Material> objMaterialProperty() {
		return this.objMaterial;
	}

	public final Material getObjMaterial() {
		return this.objMaterialProperty().get();
	}

	public final void setObjMaterial(final Material material) {
		this.objMaterialProperty().set(material);
		this.codigo4vProperty().set(material.getCodigo4v());
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Objects.hashCode(codOperacionProperty());
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
		Opera other = (Opera) obj;
		if (this.codOperacion.get() != other.codOperacion.get()) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return ", codOperacion=" + codOperacion.get() + ", material=" + objMaterial.get() + ", cantidad=" + cantidad.get()
				+ ", fecha=" + fecha.get() + "]";
	}

}
