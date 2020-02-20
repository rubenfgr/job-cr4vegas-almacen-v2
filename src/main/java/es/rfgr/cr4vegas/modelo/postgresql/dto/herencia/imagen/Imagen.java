package es.rfgr.cr4vegas.modelo.postgresql.dto.herencia.imagen;

import java.io.Serializable;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

@SuppressWarnings("serial")
public class Imagen implements Serializable {

	private IntegerProperty codImagen = new SimpleIntegerProperty();
	private StringProperty nombre = new SimpleStringProperty();
	private ObjectProperty<byte[]> imagen = new SimpleObjectProperty<byte[]>();

	public Imagen() {
		// TODO Auto-generated constructor stub
	}

	public Imagen(Imagen imagen) {
		copiarValores(imagen);
	}
	
	protected void copiarValores(Imagen imagen) {
		this.codImagenProperty().set(imagen.getCodImagen());
		this.nombreProperty().set(imagen.getNombre());
		this.imagenProperty().set(imagen.getImagen());
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

	public final StringProperty nombreProperty() {
		return this.nombre;
	}

	public final String getNombre() {
		return this.nombreProperty().get();
	}

	public final void setNombre(final String nombre) {
		this.nombreProperty().set(nombre);
	}

	public final ObjectProperty<byte[]> imagenProperty() {
		return this.imagen;
	}

	public final byte[] getImagen() {
		return this.imagenProperty().get();
	}

	public final void setImagen(final byte[] imagen) {
		this.imagenProperty().set(imagen);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codImagen == null) ? 0 : codImagen.hashCode());
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
		Imagen other = (Imagen) obj;
		if (codImagen == null) {
			if (other.codImagen != null)
				return false;
		} else if (!codImagen.equals(other.codImagen))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Imagen [codImagen=" + codImagen.get() + ", nombre=" + nombre.get() + ", imagen=" + imagen.getName()
				+ "]";
	}

	public ImagenPK createPK() {
		return new ImagenPK(getCodImagen());
	}
}
