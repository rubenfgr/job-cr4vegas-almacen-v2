package es.rfgr.cr4vegas.modelo.postgresql.dto.almacen.presupuesto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

import es.rfgr.cr4vegas.modelo.postgresql.dao.almacen.presupuesto.TienePresupuestoMaterialDAO;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@SuppressWarnings("serial")
public class Presupuesto implements Serializable {

	private static final String TELEFONO_VALIDO = "N|[679][0-9]{8}";

	private IntegerProperty codPresupuesto = new SimpleIntegerProperty();
	private StringProperty cliente = new SimpleStringProperty();
	private StringProperty telefono = new SimpleStringProperty();
	private ObjectProperty<BigDecimal> total = new SimpleObjectProperty<BigDecimal>();
	private ObjectProperty<LocalDate> validez = new SimpleObjectProperty<LocalDate>();
	private ObjectProperty<LocalDate> fecha = new SimpleObjectProperty<LocalDate>();

	private JRBeanCollectionDataSource materiales;

	public Presupuesto() {
		cliente.set("");
		telefono.set("");
		total.set(BigDecimal.ZERO);
		validez.set(LocalDate.now().plusMonths(1));
		fecha.set(LocalDate.now());
	}

	public Presupuesto(Presupuesto presupuesto) {
		copiarValores(presupuesto);
	}

	public void copiarValores(Presupuesto presupuesto) {
		this.codPresupuestoProperty().set(presupuesto.getCodPresupuesto());
		this.clienteProperty().set(presupuesto.getCliente());
		this.telefonoProperty().set(presupuesto.getTelefono());
		this.totalProperty().set(presupuesto.getTotal());
		this.validezProperty().set(presupuesto.getValidez());
		this.fechaProperty().set(presupuesto.getFecha());
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

	public final StringProperty clienteProperty() {
		return this.cliente;
	}

	public final String getCliente() {
		return this.clienteProperty().get();
	}

	public final void setCliente(final String cliente) {
		this.clienteProperty().set(cliente);
	}

	public final StringProperty telefonoProperty() {
		return this.telefono;
	}

	public final String getTelefono() {
		return this.telefonoProperty().get();
	}

	public final void setTelefono(final String telefono) {
		if (telefono != null && telefono.matches(TELEFONO_VALIDO)) {
			this.telefonoProperty().set(telefono);
		} else {
			throw new IllegalArgumentException("El télefono del cliente no es válido. ER = [0-9]{9,}");
		}
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

	public final ObjectProperty<LocalDate> validezProperty() {
		return this.validez;
	}

	public final LocalDate getValidez() {
		return this.validezProperty().get();
	}

	public final void setValidez(final LocalDate validez) {
		this.validezProperty().set(validez);
	}

	public final void setValidez(final String validez) {
		try {
			this.validezProperty().set(LocalDate.parse(validez));
		} catch (DateTimeParseException e) {
			throw new IllegalArgumentException("El formato de la fecha no es correcto.");
		}
	}

	public final ObjectProperty<LocalDate> fechaProperty() {
		return this.fecha;
	}

	public final LocalDate getFecha() {
		return this.fechaProperty().get();
	}

	public final JRBeanCollectionDataSource getMateriales(TienePresupuestoMaterialDAO tpm) {
		List<TienePresupuestoMaterial> listaMaterialesPresupuesto = tpm.getPresupuestoMateriales(this);

		if (listaMaterialesPresupuesto != null) {
			this.materiales = new JRBeanCollectionDataSource(listaMaterialesPresupuesto);
		} else {
			this.materiales = null;
		}

		return materiales;
	}

	public void setFecha(LocalDate fecha) {
		this.fechaProperty().set(fecha);
	}
	
	public PresupuestoPK createPK() {
		return new PresupuestoPK(getCodPresupuesto());
	}
	
	public final Double getIVA() {
		return totalProperty().get().doubleValue() * 0.21;
	}
	
	public final Double getTotalConIVA() {
		return getIVA() + totalProperty().get().doubleValue();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + this.codPresupuesto.get();
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
		Presupuesto other = (Presupuesto) obj;
		if (this.codPresupuesto.get() != other.codPresupuesto.get()) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "Presupuesto [codPresupuesto=" + codPresupuesto.get() + ", total=" + total.get() + ", validez="
				+ validez.get() + ", fecha=" + fecha.get() + "]";
	}
	
}
