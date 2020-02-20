package es.rfgr.cr4vegas.modelo.postgresql.dto.alarma;

import java.io.Serializable;
import java.time.LocalDateTime;

import es.rfgr.cr4vegas.modelo.postgresql.dto.grupo.Grupo;
import es.rfgr.cr4vegas.modelo.postgresql.dto.operario.Operario;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

@SuppressWarnings("serial")
public class Alarma implements Serializable {

	private IntegerProperty codAlarma = new SimpleIntegerProperty();
	private IntegerProperty codOp = new SimpleIntegerProperty();
	private ObjectProperty<Operario> objOperario = new SimpleObjectProperty<Operario>();
	private BooleanProperty activo = new SimpleBooleanProperty();
	private StringProperty grupo = new SimpleStringProperty();
	private ObjectProperty<Grupo> objGrupo = new SimpleObjectProperty<Grupo>();
	private StringProperty orden = new SimpleStringProperty();
	private StringProperty descripcion = new SimpleStringProperty();
	private ObjectProperty<LocalDateTime> fecha = new SimpleObjectProperty<LocalDateTime>();
	private BooleanProperty repetir = new SimpleBooleanProperty();
	private IntegerProperty frecuencia = new SimpleIntegerProperty();

	public Alarma() {
		// TODO Auto-generated constructor stub
	}

	public Alarma(Alarma alarma) {
		copiarValores(alarma);
	}
	
	public void copiarValores(Alarma alarma) {
		this.codAlarmaProperty().set(alarma.getCodAlarma());
		this.codOpProperty().set(alarma.getCodOp());
		this.objOperarioProperty().set(alarma.getObjOperario());
		this.activoProperty().set(alarma.isActivo());
		this.grupoProperty().set(alarma.getGrupo());
		this.objGrupoProperty().set(alarma.getObjGrupo());
		this.ordenProperty().set(alarma.getOrden());
		this.descripcionProperty().set(alarma.getDescripcion());
		this.fechaProperty().set(alarma.getFecha());
		this.repetirProperty().set(alarma.isRepetir());
		this.frecuenciaProperty().set(alarma.getFrecuencia());
	}

	public final IntegerProperty codAlarmaProperty() {
		return this.codAlarma;
	}
	
	public final int getCodAlarma() {
		return this.codAlarmaProperty().get();
	}
	
	public final void setCodAlarma(final int codAlarma) {
		this.codAlarmaProperty().set(codAlarma);
	}
	
	private final IntegerProperty codOpProperty() {
		return this.codOp;
	}
	
	public final int getCodOp() {
		return this.codOpProperty().get();
	}
	
	public final void setCodOp(final int codOp) {
		this.codOpProperty().set(codOp);
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

	public final ObjectProperty<Operario> objOperarioProperty() {
		return this.objOperario;
	}
	
	public final Operario getObjOperario() {
		return this.objOperarioProperty().get();
	}
	
	public final void setObjOperario(final Operario operario) {
		if (operario != null) {
			this.objOperarioProperty().set(operario);
			this.codOpProperty().set(operario.getCodOp());
		}
	}
	
	public final ObjectProperty<Grupo> objGrupoProperty() {
		return this.objGrupo;
	}
	
	public final Grupo getObjGrupo() {
		return this.objGrupoProperty().get();
	}
	
	public final void setObjGrupo(final Grupo objGrupo) {
		if (objGrupo != null) {
			this.objGrupoProperty().set(objGrupo);
			this.grupoProperty().set(objGrupo.getNombre());
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + this.codAlarma.get();
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
		Alarma other = (Alarma) obj;
		if (this.codAlarma.get() != other.codAlarma.get()) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "Alarma [codAlarma=" + codAlarma.get() + ", codOp=" + codOp.get() + ", activo=" + activo.get() + ", grupo=" + grupo.get() + ", orden=" + orden.get() + ", descripcion="
				+ descripcion.get() + ", fecha=" + fecha.getName() + ", repetir=" + repetir.get() + ", frecuencia=" + frecuencia.get() + "]";
	}
	
	public AlarmaPK createPK() {
		return new AlarmaPK(getCodAlarma());
	}

}
