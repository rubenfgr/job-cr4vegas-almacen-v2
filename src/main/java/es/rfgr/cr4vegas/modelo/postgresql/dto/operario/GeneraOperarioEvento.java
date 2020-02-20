package es.rfgr.cr4vegas.modelo.postgresql.dto.operario;

import java.io.Serializable;

import es.rfgr.cr4vegas.modelo.postgresql.dto.herencia.genera.Genera;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;

@SuppressWarnings("serial")
public class GeneraOperarioEvento extends Genera implements Serializable {

	private IntegerProperty codOp = new SimpleIntegerProperty();

	private ObjectProperty<Operario> objOperario = new SimpleObjectProperty<Operario>();

	public GeneraOperarioEvento() {
		super();
	}

	public GeneraOperarioEvento(GeneraOperarioEvento generaOperarioEvento) {
		copiarValores(generaOperarioEvento);
	}

	public void copiarValores(GeneraOperarioEvento generaOperarioEvento) {
		super.copiarValores(generaOperarioEvento);
		this.codOpProperty().set(generaOperarioEvento.getCodOp());
		this.objOperarioProperty().set(generaOperarioEvento.getObjOperario());
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + this.codOp.get();
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
		GeneraOperarioEvento other = (GeneraOperarioEvento) obj;
		if (this.codOp.get() != other.codOp.get()) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "GeneraOperarioEvento [codOp=" + codOp.get() + super.toString();
	}

	public GeneraOperarioEventoPK createPK() {
		return new GeneraOperarioEventoPK(getCodEvento());
	}

}
