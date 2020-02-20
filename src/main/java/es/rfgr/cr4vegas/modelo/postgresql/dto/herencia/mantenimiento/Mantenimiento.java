package es.rfgr.cr4vegas.modelo.postgresql.dto.herencia.mantenimiento;

import java.io.Serializable;
import java.time.LocalDateTime;

import es.rfgr.cr4vegas.modelo.postgresql.dto.grupo.Grupo;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

@SuppressWarnings("serial")
public abstract class Mantenimiento implements Serializable {

	private IntegerProperty codMan = new SimpleIntegerProperty();
	private StringProperty grupo = new SimpleStringProperty();
	private ObjectProperty<Grupo> objGrupo = new SimpleObjectProperty<Grupo>();
	private StringProperty orden = new SimpleStringProperty();
	private StringProperty descripcion = new SimpleStringProperty();
	private ObjectProperty<LocalDateTime> fecha = new SimpleObjectProperty<LocalDateTime>();
	private BooleanProperty repetir = new SimpleBooleanProperty();
	private IntegerProperty frecuencia = new SimpleIntegerProperty();

	protected void copiarValores(Mantenimiento mantenimiento) {
		this.codManProperty().set(mantenimiento.getCodMan());
		this.grupoProperty().set(mantenimiento.getGrupo());
		this.objGrupoProperty().set(mantenimiento.getObjGrupo());
		this.ordenProperty().set(mantenimiento.getOrden());
		this.descripcionProperty().set(mantenimiento.getDescripcion());
		this.fechaProperty().set(mantenimiento.getFecha());
		this.repetirProperty().set(mantenimiento.isRepetir());
		this.frecuenciaProperty().set(mantenimiento.getFrecuencia());
	}

	public final IntegerProperty codManProperty() {
		return this.codMan;
	}

	public final int getCodMan() {
		return this.codManProperty().get();
	}

	public final void setCodMan(final int codMan) {
		this.codManProperty().set(codMan);
	}
	
	public final StringProperty grupoProperty() {
		return this.grupo;
	}

	public final String getGrupo() {
		return this.grupoProperty().get();
	}

	public final void setGrupo(final String grupo) {
		this.grupoProperty().set(grupo);
	}

	public final StringProperty ordenProperty() {
		return this.orden;
	}

	public final String getOrden() {
		return this.ordenProperty().get();
	}

	public final void setOrden(final String orden) {
		this.ordenProperty().set(orden);
	}

	public final StringProperty descripcionProperty() {
		return this.descripcion;
	}

	public final String getDescripcion() {
		return this.descripcionProperty().get();
	}

	public final void setDescripcion(final String descripcion) {
		this.descripcionProperty().set(descripcion);
	}

	public final ObjectProperty<LocalDateTime> fechaProperty() {
		return this.fecha;
	}

	public final LocalDateTime getFecha() {
		return this.fechaProperty().get();
	}

	public final void setFecha(final LocalDateTime fecha) {
		this.fechaProperty().set(fecha);
	}

	public final BooleanProperty repetirProperty() {
		return this.repetir;
	}

	public final boolean isRepetir() {
		return this.repetirProperty().get();
	}

	public final void setRepetir(final boolean repetir) {
		this.repetirProperty().set(repetir);
	}

	public final IntegerProperty frecuenciaProperty() {
		return this.frecuencia;
	}

	public final int getFrecuencia() {
		return this.frecuenciaProperty().get();
	}

	public final void setFrecuencia(final int frecuencia) {
		this.frecuenciaProperty().set(frecuencia);
	}
	
	public final ObjectProperty<Grupo> objGrupoProperty() {
		return this.objGrupo;
	}
	
	public final Grupo getObjGrupo() {
		return this.objGrupoProperty().get();
	}
	
	public final void setObjGrupo(final Grupo objGrupo) {
		this.objGrupoProperty().set(objGrupo);
		this.grupoProperty().set(objGrupo.getNombre());
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + this.codMan.get();
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
		Mantenimiento other = (Mantenimiento) obj;
		if (this.codMan.get() != other.codMan.get()) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return ", codMan=" + codMan.get() + ", grupo=" + grupo.get() + ", orden=" + orden.get() + ", descripcion="
				+ descripcion.get() + ", fecha=" + fecha.getName() + ", repetir=" + repetir.getName() + ", frecuencia="
				+ frecuencia.get() + "]";
	}
	
}
