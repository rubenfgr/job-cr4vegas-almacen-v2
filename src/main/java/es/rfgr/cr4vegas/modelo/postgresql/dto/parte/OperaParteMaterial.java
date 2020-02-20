package es.rfgr.cr4vegas.modelo.postgresql.dto.parte;

import java.io.Serializable;

import es.rfgr.cr4vegas.modelo.postgresql.dto.herencia.opera.Opera;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;

@SuppressWarnings("serial")
public class OperaParteMaterial extends Opera implements Serializable {

	private IntegerProperty codParte = new SimpleIntegerProperty();

	private ObjectProperty<Parte> objParte = new SimpleObjectProperty<Parte>();

	public OperaParteMaterial() {
		super();
	}

	public OperaParteMaterial(OperaParteMaterial operaParteMaterial) {
		copiarValores(operaParteMaterial);
	}

	public void copiarValores(OperaParteMaterial operaParteMaterial) {
		super.copiarValores(operaParteMaterial);
		this.codParteProperty().set(operaParteMaterial.getCodParte());
		this.objParteProperty().set(operaParteMaterial.getObjParte());
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
	public String toString() {
		return "OperaParteMaterial [codParte=" + codParte.get() + "]";
	}

	public OperaParteMaterialPK createPK() {
		return new OperaParteMaterialPK(getCodOperacion());
	}
}
