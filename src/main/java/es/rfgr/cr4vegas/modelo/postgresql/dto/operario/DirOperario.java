package es.rfgr.cr4vegas.modelo.postgresql.dto.operario;

import es.rfgr.cr4vegas.modelo.postgresql.dto.herencia.Direccion;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;

@SuppressWarnings("serial")
public class DirOperario extends Direccion {

	private IntegerProperty codOp = new SimpleIntegerProperty();
	
	private ObjectProperty<Operario> objOperario = new SimpleObjectProperty<Operario>();

	public DirOperario() {
		super();
	}

	public DirOperario(DirOperario dirOperario) {
		copiarValores(dirOperario);
	}
	
	public void copiarValores(DirOperario dirOperario) {
		super.copiarValores(dirOperario);
		this.codOpProperty().set(dirOperario.getCodOp());
		this.objOperarioProperty().set(dirOperario.getObjOperario());
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
		result = prime * result + ((codOp == null) ? 0 : codOp.hashCode());
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
		DirOperario other = (DirOperario) obj;
		if (codOp == null) {
			if (other.codOp != null)
				return false;
		} else if (codOp.get() != other.codOp.get())
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "DirOperario [codOp=" + codOp.get() + super.toString();
	}
	
	public DirOperarioPK createPK() {
		return new DirOperarioPK(getCodOp());
	}
}
