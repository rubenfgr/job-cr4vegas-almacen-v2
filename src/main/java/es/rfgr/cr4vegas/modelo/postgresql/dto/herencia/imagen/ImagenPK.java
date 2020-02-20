package es.rfgr.cr4vegas.modelo.postgresql.dto.herencia.imagen;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class ImagenPK {
	
	private IntegerProperty codImagen = new SimpleIntegerProperty();
	
	private BooleanProperty codImagenNull = new SimpleBooleanProperty();
	
	public ImagenPK(int codImagen) {
		setCodImagen(codImagen);
	}

	public final IntegerProperty codImagenProperty() {
		return this.codImagen;
	}
	
	public final int getCodImagen() {
		return this.codImagenProperty().get();
	}
	
	public final void setCodImagen(final int codImagen) {
		this.codImagenProperty().set(codImagen);
	}
	
	public final BooleanProperty codImagenNullProperty() {
		return this.codImagenNull;
	}
	
	public final boolean isCodImagenNull() {
		return this.codImagenNullProperty().get();
	}
	
	public final void setCodImagenNull(final boolean codImagenNull) {
		this.codImagenNullProperty().set(codImagenNull);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codImagen == null) ? 0 : codImagen.hashCode());
		result = prime * result + ((codImagenNull == null) ? 0 : codImagenNull.hashCode());
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
		ImagenPK other = (ImagenPK) obj;
		if (codImagen == null) {
			if (other.codImagen != null)
				return false;
		} else if (!codImagen.equals(other.codImagen))
			return false;
		if (codImagenNull == null) {
			if (other.codImagenNull != null)
				return false;
		} else if (!codImagenNull.equals(other.codImagenNull))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ImagenPK [codImagen=" + codImagen.get() + ", codImagenNull=" + codImagenNull.getName() + "]";
	}

}
