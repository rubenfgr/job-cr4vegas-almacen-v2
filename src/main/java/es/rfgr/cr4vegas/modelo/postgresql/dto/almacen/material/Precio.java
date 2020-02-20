package es.rfgr.cr4vegas.modelo.postgresql.dto.almacen.material;

import java.io.Serializable;
import java.math.BigDecimal;

import es.rfgr.cr4vegas.modelo.postgresql.dto.almacen.tienda.Tienda;
import es.rfgr.cr4vegas.utileria.ObjetoSimple;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;

@SuppressWarnings("serial")
public class Precio implements ObjetoSimple, Serializable {

	private IntegerProperty codigo4v = new SimpleIntegerProperty();
	private ObjectProperty<Material> objMaterial = new SimpleObjectProperty<>();
	private IntegerProperty codtienda = new SimpleIntegerProperty();
	private ObjectProperty<Tienda> objTienda = new SimpleObjectProperty<>();
	private ObjectProperty<BigDecimal> precio = new SimpleObjectProperty<>();
	private ObjectProperty<BigDecimal> precioPublico = new SimpleObjectProperty<>();

	public Precio() {
		precio.set(BigDecimal.ZERO);
		precioPublico.set(BigDecimal.ZERO);
	}

	public Precio(Precio precio) {
		copiarValores(precio);
	}

	public void copiarValores(Precio precioNuevo) {
		this.codigo4v.set(precioNuevo.getCodigo4v());
		this.objMaterial.set(precioNuevo.getObjMaterial());
		this.codtienda.set(precioNuevo.getCodtienda());
		this.objTienda.set(precioNuevo.getObjTienda());
		this.precio.set(precioNuevo.getPrecio());
		this.precioPublico.set(precioNuevo.getPrecioPublico());
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

	public final ObjectProperty<Material> objMaterialProperty() {
		return this.objMaterial;
	}

	public final Material getObjMaterial() {
		return this.objMaterialProperty().get();
	}

	public final void setObjMaterial(final Material objMaterial) {
		if (objMaterial != null) {
			this.codigo4v.set(objMaterial.getCodigo4v());
			this.objMaterialProperty().set(objMaterial);
		}
	}

	public final IntegerProperty codtiendaProperty() {
		return this.codtienda;
	}

	public final int getCodtienda() {
		return this.codtiendaProperty().get();
	}

	public final void setCodtienda(final int codtienda) {
		this.codtiendaProperty().set(codtienda);
	}

	public final ObjectProperty<Tienda> objTiendaProperty() {
		return this.objTienda;
	}

	public final Tienda getObjTienda() {
		return this.objTiendaProperty().get();
	}

	public final void setObjTienda(final Tienda objTienda) {
		if (objTienda != null) {
			this.codtienda.set(objTienda.getCodTienda());
			this.objTiendaProperty().set(objTienda);
		}
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

	public final ObjectProperty<BigDecimal> precioPublicoProperty() {
		return this.precioPublico;
	}

	public final BigDecimal getPrecioPublico() {
		return this.precioPublicoProperty().get();
	}

	public final void setPrecioPublico(final BigDecimal precioPublico) {
		this.precioPublicoProperty().set(precioPublico);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codigo4v == null) ? 0 : codigo4v.hashCode());
		result = prime * result + ((codtienda == null) ? 0 : codtienda.hashCode());
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
		Precio other = (Precio) obj;
		if (codigo4v.get() != other.codigo4v.get()) {
			return false;
		}
		if (codtienda.get() != other.codtienda.get()) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "Precio [codigo4v=" + codigo4v + ", objMaterial=" + objMaterial + ", codtienda=" + codtienda
				+ ", objTienda=" + objTienda + ", precio=" + precio + ", precioPublico=" + precioPublico + "]";
	}

	public PrecioPK createPK() {
		return new PrecioPK(getCodigo4v(), getCodtienda());
	}

}
