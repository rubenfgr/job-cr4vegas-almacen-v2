package es.rfgr.cr4vegas.modelo.postgresql.dto.operario;

import java.io.Serializable;
import java.util.Objects;

import es.rfgr.cr4vegas.modelo.postgresql.dto.parte.Parte;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;

@SuppressWarnings("serial")
public class CreaOperarioParte implements Serializable {

	private IntegerProperty codOp = new SimpleIntegerProperty();
	private ObjectProperty<Operario> objOperario = new SimpleObjectProperty<Operario>();
	private IntegerProperty codParte = new SimpleIntegerProperty();
	private ObjectProperty<Parte> objParte = new SimpleObjectProperty<Parte>();

	public CreaOperarioParte() {
		
	}

	public CreaOperarioParte(CreaOperarioParte creaOperarioParte) {
		copiarValores(creaOperarioParte);
	}
	
	public void copiarValores(CreaOperarioParte creaOperarioParte) {
		this.codOpProperty().set(creaOperarioParte.getCodOp());
		this.objOperarioProperty().set(creaOperarioParte.getObjOperario());
		this.codParteProperty().set(creaOperarioParte.getCodParte());
		this.objParteProperty().set(creaOperarioParte.getObjParte());
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
		result = prime * result + Objects.hashCode(codOp.get());
		result = prime * result + Objects.hashCode(codParte.get());
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
		CreaOperarioParte other = (CreaOperarioParte) obj;
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
		return "CreaOperarioParte [codOp=" + codOp + ", codParte=" + codParte + "]";
	}
	
	public CreaOperarioPartePK createPK() {
		return new CreaOperarioPartePK(getCodOp(), getCodParte());
	}

}
