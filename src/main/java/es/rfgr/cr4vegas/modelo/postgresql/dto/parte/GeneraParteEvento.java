package es.rfgr.cr4vegas.modelo.postgresql.dto.parte;

import java.io.Serializable;

import es.rfgr.cr4vegas.modelo.postgresql.dto.herencia.genera.Genera;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;

@SuppressWarnings("serial")
public class GeneraParteEvento extends Genera implements Serializable {

	private IntegerProperty codParte = new SimpleIntegerProperty();
	private ObjectProperty<Parte> objParte = new SimpleObjectProperty<Parte>();

	public GeneraParteEvento() {
		super();
	}

	public GeneraParteEvento(GeneraParteEvento generaParteEvento) {
		copiarValores(generaParteEvento);
	}

	public void copiarValores(GeneraParteEvento generaParteEvento) {
		super.copiarValores(generaParteEvento);
		this.codParteProperty().set(generaParteEvento.getCodParte());
		this.objParteProperty().set(generaParteEvento.getObjParte());
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
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + this.codParte.get();
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		GeneraParteEvento other = (GeneraParteEvento) obj;
		if (this.codParte.get() != other.codParte.get()) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "GeneraParteEvento [codParte=" + codParte.get() + super.toString();
	}

	public GeneraParteEventoPK createPK() {
		return new GeneraParteEventoPK(getCodParte());
	}
}
