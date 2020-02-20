package es.rfgr.cr4vegas.modelo.postgresql.dto.almacen.presupuesto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;

import es.rfgr.cr4vegas.modelo.postgresql.dto.almacen.material.Material;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;

@SuppressWarnings("serial")
public class TienePresupuestoMaterial implements Serializable {

	private IntegerProperty codPresupuesto = new SimpleIntegerProperty();
	private ObjectProperty<Presupuesto> objPresupuesto = new SimpleObjectProperty<Presupuesto>();
	private IntegerProperty codigo4v = new SimpleIntegerProperty();
	private ObjectProperty<Material> objMaterial = new SimpleObjectProperty<Material>();
	private IntegerProperty cantidad = new SimpleIntegerProperty();
	private ObjectProperty<BigDecimal> precio = new SimpleObjectProperty<>();
	private ObjectProperty<BigDecimal> total = new SimpleObjectProperty<>();

	public TienePresupuestoMaterial() {
		// TODO Auto-generated constructor stub
	}

	public TienePresupuestoMaterial(TienePresupuestoMaterial tienePresupuestoMaterial) {
		copiarValores(tienePresupuestoMaterial);
	}

	public void copiarValores(TienePresupuestoMaterial tienePresupuestoMaterialNewValues) {
		this.codPresupuestoProperty().set(tienePresupuestoMaterialNewValues.getCodPresupuesto());
		this.objPresupuestoProperty().set(tienePresupuestoMaterialNewValues.getObjPresupuesto());
		this.codigo4vProperty().set(tienePresupuestoMaterialNewValues.getCodigo4v());
		this.objMaterialProperty().set(tienePresupuestoMaterialNewValues.getObjMaterial());
		this.cantidadProperty().set(tienePresupuestoMaterialNewValues.getCantidad());
		this.precioProperty().set(tienePresupuestoMaterialNewValues.getPrecio());
		this.totalProperty().set(tienePresupuestoMaterialNewValues.getTotal());
	}

	public final IntegerProperty codPresupuestoProperty() {
		return this.codPresupuesto;
	}

	public final int getCodPresupuesto() {
		return this.codPresupuestoProperty().get();
	}

	public final void setCodPresupuesto(final int codPresupuesto) {
		this.codPresupuestoProperty().set(codPresupuesto);
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

	public final ObjectProperty<BigDecimal> precioProperty() {
		return this.precio;
	}

	public final BigDecimal getPrecio() {
		return this.precioProperty().get();
	}

	public final void setPrecio(final BigDecimal precio) {
		this.precioProperty().set(precio);
	}

	public final ObjectProperty<Presupuesto> objPresupuestoProperty() {
		return this.objPresupuesto;
	}

	public final Presupuesto getObjPresupuesto() {
		return this.objPresupuestoProperty().get();
	}

	public final void setObjPresupuesto(final Presupuesto presupuesto) {
		this.objPresupuestoProperty().set(presupuesto);
		this.codPresupuestoProperty().set(presupuesto.getCodPresupuesto());
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

	public final ObjectProperty<BigDecimal> totalProperty() {
		return this.total;
	}

	public final BigDecimal getTotal() {
		return this.totalProperty().get();
	}

	public final void setTotal(final BigDecimal total) {
		this.totalProperty().set(total);
	}

	public final void setTotal() {
		Double resultado = precioProperty().get().doubleValue() * cantidadProperty().get();
		totalProperty().set(BigDecimal.valueOf(resultado));
		totalProperty().get().setScale(2, RoundingMode.HALF_UP);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + this.codPresupuesto.get();
		result = prime * result + this.codigo4v.get();
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
		TienePresupuestoMaterial other = (TienePresupuestoMaterial) obj;
		if (this.codPresupuesto.get() != other.codPresupuesto.get()) {
			return false;
		}
		if (this.codigo4v.get() != other.codigo4v.get()) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "TienePresupuestoMaterial [codPresupuesto=" + codPresupuesto.get() + ", codigo4v=" + codigo4v.get()
				+ ", cantidad=" + cantidad.get() + ", precio=" + precio.get() + "]";
	}

	public TienePresupuestoMaterialPK createPK() {
		return new TienePresupuestoMaterialPK(getCodPresupuesto(), getCodigo4v());
	}

}
