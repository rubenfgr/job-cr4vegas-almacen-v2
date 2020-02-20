package es.rfgr.cr4vegas.modelo.postgresql.dto.parte;

import java.io.Serializable;
import java.time.LocalDateTime;

import es.rfgr.cr4vegas.modelo.postgresql.dto.operario.Operario;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;

@SuppressWarnings("serial")
public class AsociaParteOperario implements Serializable {

	private IntegerProperty codOp = new SimpleIntegerProperty();
	private ObjectProperty<Operario> objOperario = new SimpleObjectProperty<Operario>();
	private IntegerProperty codParte = new SimpleIntegerProperty();
	private ObjectProperty<Parte> objParte = new SimpleObjectProperty<Parte>();
	private ObjectProperty<LocalDateTime> fecha = new SimpleObjectProperty<LocalDateTime>();

	public AsociaParteOperario() {
		
	}

	public AsociaParteOperario(AsociaParteOperario asociaParteOperario) {
		copiarValores(asociaParteOperario);
	}
	
	public void copiarValores(AsociaParteOperario asociaParteOperario) {
		this.codOpProperty().set(asociaParteOperario.getCodOp());
		this.objOperarioProperty().set(asociaParteOperario.getObjOperario());
		this.codParteProperty().set(asociaParteOperario.getCodParte());
		this.objParteProperty().set(asociaParteOperario.getObjParte());
		this.fechaProperty().set(asociaParteOperario.getFecha());
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

	public final IntegerProperty codParteProperty() {
		return this.codParte;
	}

	public final int getCodParte() {
		return this.codParteProperty().get();
	}

	public final void setCodParte(final int codParte) {
		this.codParteProperty().set(codParte);
	}

	public final ObjectProperty<LocalDateTime> fechaProperty() {
		return this.fecha;
	}

	public final LocalDateTime getFecha() {
		return this.fechaProperty().get();
	}

	public final void setFecha() {
		this.fechaProperty().set(LocalDateTime.now());
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
	
	public final void setObjOperario(final Operario objOperario) {
		this.objOperarioProperty().set(objOperario);
		this.codOpProperty().set(objOperario.getCodOp());
	}
	
	public final ObjectProperty<Parte> objParteProperty() {
		return this.objParte;
	}
	
	public final Parte getObjParte() {
		return this.objParteProperty().get();
	}
	
	public final void setObjParte(final Parte objParte) {
		this.objParteProperty().set(objParte);
		this.codParteProperty().set(objParte.getCodParte());
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + this.codOp.get();
		result = prime * result + this.codParte.get();
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
		AsociaParteOperario other = (AsociaParteOperario) obj;
		if (this.codOp.get() != other.codOp.get()) {
			return false;
		}
		if (this.codParte.get() != other.codParte.get()) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "AsociaOperarioParte [codOp=" + codOp.get() + ", codParte=" + codParte.get() + ", fecha="
				+ fecha.getName() + "]";
	}

	public AsociaParteOperarioPK createPK() {
		return new AsociaParteOperarioPK(getCodParte(), getCodOp());
	}
	
}
