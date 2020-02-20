package es.rfgr.cr4vegas.modelo.postgresql.dto.evento;

import java.io.Serializable;
import java.time.LocalDateTime;

import es.rfgr.cr4vegas.modelo.postgresql.dto.grupo.Grupo;
import es.rfgr.cr4vegas.utileria.Origen;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

@SuppressWarnings("serial")
public class Evento implements Serializable {

	private IntegerProperty codEvento = new SimpleIntegerProperty();
	private BooleanProperty activo = new SimpleBooleanProperty();
	private StringProperty grupo = new SimpleStringProperty();
	private ObjectProperty<Grupo> objGrupo = new SimpleObjectProperty<Grupo>();
	private ObjectProperty<Origen> origen = new SimpleObjectProperty<Origen>();
	private StringProperty orden = new SimpleStringProperty();
	private StringProperty descripcion = new SimpleStringProperty();
	private StringProperty prioridad = new SimpleStringProperty();
	private BooleanProperty estado = new SimpleBooleanProperty();
	private ObjectProperty<LocalDateTime> fecha = new SimpleObjectProperty<LocalDateTime>();
	private BooleanProperty parte = new SimpleBooleanProperty();

	public Evento() {
		// TODO Auto-generated constructor stub
	}

	public Evento(Evento evento) {
		copiarValores(evento);
	}
	
	public void copiarValores(Evento evento) {
		this.codEventoProperty().set(evento.getCodEvento());
		this.activoProperty().set(evento.isActivo());
		this.grupoProperty().set(evento.getGrupo());
		this.objGrupoProperty().set(evento.getObjGrupo());
		this.origenProperty().set(evento.getOrigen());
		this.ordenProperty().set(evento.getOrden());
		this.descripcionProperty().set(evento.getDescripcion());
		this.prioridadProperty().set(evento.getPrioridad());
		this.estadoProperty().set(evento.isEstado());
		this.fechaProperty().set(evento.getFecha());
		this.parteProperty().set(evento.isParte());
	}

	public final IntegerProperty codEventoProperty() {
		return this.codEvento;
	}

	public final int getCodEvento() {
		return this.codEventoProperty().get();
	}

	public final void setCodEvento(final int codEvento) {
		this.codEventoProperty().set(codEvento);
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

	public final ObjectProperty<Origen> origenProperty() {
		return this.origen;
	}

	public final Origen getOrigen() {
		return this.origenProperty().get();
	}

	public final void setOrigen(final Origen origen) {
		this.origenProperty().set(origen);
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

	public final StringProperty prioridadProperty() {
		return this.prioridad;
	}

	public final String getPrioridad() {
		return this.prioridadProperty().get();
	}

	public final void setPrioridad(final String prioridad) {
		this.prioridadProperty().set(prioridad);
	}

	public final BooleanProperty estadoProperty() {
		return this.estado;
	}

	public final boolean isEstado() {
		return this.estadoProperty().get();
	}

	public final void setEstado(final boolean estado) {
		this.estadoProperty().set(estado);
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

	public final BooleanProperty parteProperty() {
		return this.parte;
	}

	public final boolean isParte() {
		return this.parteProperty().get();
	}

	public final void setParte(final boolean parte) {
		this.parteProperty().set(parte);
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
		result = prime * result + this.codEvento.get();
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
		Evento other = (Evento) obj;
		if (this.codEvento.get() != other.codEvento.get()) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "Evento [codEvento=" + codEvento.get() + ", activo=" + activo.getName() + ", grupo=" + grupo.get()
				+ ", origen=" + origen.get() + ", orden=" + orden.get() + ", descripcion=" + descripcion.get()
				+ ", prioridad=" + prioridad.get() + ", estado=" + estado.get() + ", fecha=" + fecha.get() + ", parte="
				+ parte.getName() + "]";
	}

	public EventoPK createPK() {
		return new EventoPK(getCodEvento());
	}
}
