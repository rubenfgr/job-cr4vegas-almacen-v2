package es.rfgr.cr4vegas.modelo.postgresql.dto.operario;

import java.io.Serializable;

import es.rfgr.cr4vegas.modelo.postgresql.dto.herencia.telefono.Telefono;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;

@SuppressWarnings("serial")
public class TelOperario extends Telefono implements Serializable {

	private IntegerProperty codOp = new SimpleIntegerProperty();

	private ObjectProperty<Operario> objOperario = new SimpleObjectProperty<Operario>();

	public TelOperario() {
		super();
	}

	public TelOperario(TelOperario telOperario) {
		copiarValores(telOperario);
	}

	public void copiarValores(TelOperario telOperario) {
		this.codOpProperty().set(telOperario.getCodOp());
		this.objOperarioProperty().set(telOperario.getObjOperario());
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
	public String toString() {
		return super.toString();
	}

	public TelOperarioPK createPK() {
		return new TelOperarioPK(getTelefono());
	}
}
