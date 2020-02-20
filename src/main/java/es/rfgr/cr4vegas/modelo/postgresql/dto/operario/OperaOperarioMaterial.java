package es.rfgr.cr4vegas.modelo.postgresql.dto.operario;

import java.io.Serializable;

import es.rfgr.cr4vegas.modelo.postgresql.dto.herencia.opera.Opera;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;

@SuppressWarnings("serial")
public class OperaOperarioMaterial extends Opera implements Serializable {

	private IntegerProperty codOp = new SimpleIntegerProperty();
	private ObjectProperty<Operario> objOperario = new SimpleObjectProperty<Operario>();

	public OperaOperarioMaterial() {
		super();
	}

	public OperaOperarioMaterial(OperaOperarioMaterial operaOperarioMaterial) {
		copiarValores(operaOperarioMaterial);
	}
	
	public void copiarValores(OperaOperarioMaterial operaOperarioMaterial) {
		super.copiarValores(operaOperarioMaterial);
		this.codOpProperty().set(operaOperarioMaterial.getCodOp());
		this.objOperarioProperty().set(operaOperarioMaterial.getObjOperario());
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
		return "OperaOperarioMaterial [codOp=" + codOp.get() + super.toString();
	}

	public OperaOperarioMaterialPK createPK() {
		return new OperaOperarioMaterialPK(getCodOperacion());
	}
	
}
