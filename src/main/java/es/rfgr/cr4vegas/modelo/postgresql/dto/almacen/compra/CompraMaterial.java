package es.rfgr.cr4vegas.modelo.postgresql.dto.almacen.compra;

import java.io.Serializable;
import java.math.BigDecimal;

import es.rfgr.cr4vegas.modelo.postgresql.dto.almacen.material.Material;
import es.rfgr.cr4vegas.modelo.postgresql.dto.almacen.material.Precio;
import es.rfgr.cr4vegas.modelo.postgresql.exceptions.DAOException;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;

@SuppressWarnings("serial")
public class CompraMaterial implements Serializable {

	private IntegerProperty codCompra = new SimpleIntegerProperty();
	private ObjectProperty<Compra> objCompra = new SimpleObjectProperty<Compra>();
	private IntegerProperty codigo4v = new SimpleIntegerProperty();
	private ObjectProperty<Material> objMaterial = new SimpleObjectProperty<Material>();
	private IntegerProperty cantidad = new SimpleIntegerProperty();
	private ObjectProperty<BigDecimal> precio = new SimpleObjectProperty<BigDecimal>();
	private ObjectProperty<Precio> objPrecio = new SimpleObjectProperty<Precio>();

	public CompraMaterial() {
		// TODO Auto-generated constructor stub
	}

	public CompraMaterial(CompraMaterial compraMaterial) {
		copiarValores(compraMaterial);
	}

	public void copiarValores(CompraMaterial compraMaterialNewValues) {
		this.codCompraProperty().set(compraMaterialNewValues.getCodCompra());
		this.objCompraProperty().set(compraMaterialNewValues.getObjCompra());
		this.codigo4vProperty().set(compraMaterialNewValues.getCodigo4v());
		this.objMaterialProperty().set(compraMaterialNewValues.getObjMaterial());
		this.cantidadProperty().set(compraMaterialNewValues.getCantidad());
		this.precioProperty().set(compraMaterialNewValues.getPrecio());
		this.objPrecio.set(compraMaterialNewValues.getObjPrecio());
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

	public final void setCantidadInicial(final int cantidadNew) {
		this.cantidadProperty().set(cantidadNew);
	}

	public final void setCantidad(final int cantidadNew) {
		this.cantidadProperty().set(cantidadNew);
	}

	public final void setCantidad(final String cantidad) {	
		try {
			int cantidadNew = Integer.valueOf(cantidad);

			setCantidad(cantidadNew);

		} catch (NumberFormatException e) {
			throw new IllegalArgumentException("El valor de la cantidad es incorrecto.");
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

	public final void setPrecio(final String precio) {
		try {
			this.precioProperty().set(BigDecimal.valueOf(Double.valueOf(precio)));
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException("El valor del precio es incorrecto.");
		}
	}

	public final ObjectProperty<Compra> objCompraProperty() {
		return this.objCompra;
	}

	public final Compra getObjCompra() {
		return this.objCompraProperty().get();
	}

	public final void setObjCompra(final Compra compra) {
		this.objCompraProperty().set(compra);
		this.codCompra.set(compra.getCodCompra());
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
		result = prime * result + codCompra.get();
		result = prime * result + codigo4v.get();
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
		CompraMaterial other = (CompraMaterial) obj;
		if (codCompra.get() != other.codCompra.get()) {
			return false;
		}
		if (codigo4v.get() != other.codigo4v.get()) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "CompraMaterial [compra=" + objCompra.toString() + ", material=" + objMaterial.toString() + ", cantidad="
				+ cantidad.get() + ", precio=" + precio.get() + "]";
	}

	public void throwException(Exception e) throws DAOException {
		throw new DAOException(e.getMessage(), e);
	}

	public CompraMaterialPK createPK() {
		return new CompraMaterialPK(getCodCompra(), getCodigo4v());
	}

	public final ObjectProperty<Precio> objPrecioProperty() {
		return this.objPrecio;
	}
	
	public final Precio getObjPrecio() {
		return this.objPrecioProperty().get();
	}
	
	public final void setObjPrecio(final Precio objPrecio) {
		this.objPrecioProperty().set(objPrecio);
	}
	
}
