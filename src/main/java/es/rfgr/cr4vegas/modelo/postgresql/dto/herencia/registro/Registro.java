package es.rfgr.cr4vegas.modelo.postgresql.dto.herencia.registro;

import java.io.Serializable;
import java.time.LocalDateTime;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

@SuppressWarnings("serial")
public abstract class Registro implements Serializable {

	private IntegerProperty codRegistro = new SimpleIntegerProperty();
	private StringProperty nota = new SimpleStringProperty();
	private ObjectProperty<LocalDateTime> fecha = new SimpleObjectProperty<LocalDateTime>();

	protected void copiarValores(Registro registro) {
		this.codRegistroProperty().set(registro.getCodRegistro());
		this.notaProperty().set(registro.getNota());
		this.fechaProperty().set(registro.getFecha());
	}

	public final IntegerProperty codRegistroProperty() {
		return this.codRegistro;
	}
	
	public final int getCodRegistro() {
		return this.codRegistroProperty().get();
	}
	
	public final void setCodRegistro(final int codRegistro) {
		this.codRegistroProperty().set(codRegistro);
	}
	
	public final StringProperty notaProperty() {
		return this.nota;
	}
	
	public final String getNota() {
		return this.notaProperty().get();
	}
	
	public final void setNota(final String nota) {
		this.notaProperty().set(nota);
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + this.codRegistro.get();
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
		Registro other = (Registro) obj;
		if (this.codRegistro.get() != other.codRegistro.get()) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return ", codRegistro=" + codRegistro.get() + ", nota=" + nota.get() + ", fecha=" + fecha.getName() + "]";
	}
	
	
}
