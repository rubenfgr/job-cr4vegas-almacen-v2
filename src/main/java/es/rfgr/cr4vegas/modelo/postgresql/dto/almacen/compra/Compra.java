package es.rfgr.cr4vegas.modelo.postgresql.dto.almacen.compra;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import es.rfgr.cr4vegas.modelo.postgresql.dto.almacen.tienda.Tienda;
import es.rfgr.cr4vegas.modelo.postgresql.dto.operario.Operario;
import es.rfgr.cr4vegas.modelo.postgresql.dto.parte.Parte;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

@SuppressWarnings("serial")
public class Compra implements Serializable {

	private IntegerProperty codCompra = new SimpleIntegerProperty();
	private IntegerProperty codOp = new SimpleIntegerProperty();
	private ObjectProperty<Operario> objOperario = new SimpleObjectProperty<Operario>();
	private IntegerProperty codTienda = new SimpleIntegerProperty();
	private ObjectProperty<Tienda> objTienda = new SimpleObjectProperty<Tienda>();
	private StringProperty factura = new SimpleStringProperty();
	private ObjectProperty<BigDecimal> total = new SimpleObjectProperty<BigDecimal>();
	private ObjectProperty<LocalDateTime> fecha = new SimpleObjectProperty<LocalDateTime>();
	private ObjectProperty<Parte> objParte = new SimpleObjectProperty<Parte>();

	public Compra() {
		
	}
	
	public Compra(Compra compra) {
		copiarValores(compra);
	}
	
	public void copiarValores(Compra compra) {
		this.codCompraProperty().set(compra.getCodCompra());
		this.codOpProperty().set(compra.getCodOp());
		this.objOperarioProperty().set(compra.getObjOperario());
		this.codTiendaProperty().set(compra.getCodTienda());
		this.objTiendaProperty().set(compra.getObjTienda());
		this.facturaProperty().set(compra.getFactura());
		this.totalProperty().set(compra.getTotal());
		this.fechaProperty().set(compra.getFecha());
		this.objParteProperty().set(compra.getObjParte());
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

	public final IntegerProperty codOpProperty() {
		return this.codOp;
	}

	public final int getCodOp() {
		return this.codOpProperty().get();
	}

	public final void setCodOp(final int codOp) {
		this.codOpProperty().set(codOp);
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

	public final StringProperty facturaProperty() {
		return this.factura;
	}

	public final String getFactura() {
		return this.facturaProperty().get();
	}

	public final void setFactura(final String factura) {
		if (factura == null || factura.equals("")) {
			throw new NullPointerException("La factura no puede quedar vacía.");
		}
		this.facturaProperty().set(factura);
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

	public final ObjectProperty<LocalDateTime> fechaProperty() {
		return this.fecha;
	}

	public final LocalDateTime getFecha() {
		return this.fechaProperty().get();
	}
	
	public final void setFecha(LocalDateTime fecha) {
		this.fechaProperty().set(fecha);
	}

	public final ObjectProperty<Operario> objOperarioProperty() {
		return this.objOperario;
	}
	
	public final Operario getObjOperario() {
		return this.objOperarioProperty().get();
	}
	
	public final void setObjOperario(final Operario operario) {
		if (operario == null) {
			throw new NullPointerException("El operario de la compra no puede ser nulo.");
		}
		this.objOperarioProperty().set(operario);
		this.codOpProperty().set(operario.getCodOp());
	}
	
	public final ObjectProperty<Tienda> objTiendaProperty() {
		return this.objTienda;
	}
	
	public final Tienda getObjTienda() {
		return this.objTiendaProperty().get();
	}
	
	public final void setObjTienda(final Tienda tienda) {
		if (tienda == null) {
			throw new NullPointerException("La entidad de la compra no puede ser nula.");
		}
		this.objTiendaProperty().set(tienda);
		this.codTiendaProperty().set(tienda.getCodTienda());
	}
	
	public final ObjectProperty<Parte> objParteProperty() {
		return this.objParte;
	}
	
	public final Parte getObjParte() {
		return this.objParteProperty().get();
	}
	
	public final void setObjParte(final Parte parte) {
		this.objParteProperty().set(parte);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + this.codCompra.get();
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
		Compra other = (Compra) obj;
		if (this.codCompra.get() != other.codCompra.get()) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "Compra [codCompra=" + codCompra.get() + ", operario=" + objOperario.toString() + ", tienda=" + objTienda.toString()
				+ ", factura=" + factura.get() + ", total=" + total.get() + ", fecha=" + fecha.get() + "]";
	}
	
	public CompraPK createPK() {
		return new CompraPK(getCodCompra());
	}
	
}
