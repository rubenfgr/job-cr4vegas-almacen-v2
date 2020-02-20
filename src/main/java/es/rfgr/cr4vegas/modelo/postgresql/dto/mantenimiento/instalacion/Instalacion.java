package es.rfgr.cr4vegas.modelo.postgresql.dto.mantenimiento.instalacion;

import java.io.Serializable;

import es.rfgr.cr4vegas.modelo.postgresql.dto.mantenimiento.estacion.Estacion;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

@SuppressWarnings("serial")
public class Instalacion implements Serializable {

	private IntegerProperty codInstalacion = new SimpleIntegerProperty();
	private IntegerProperty codEstacion = new SimpleIntegerProperty();
	private ObjectProperty<Estacion> objEstacion = new SimpleObjectProperty<Estacion>();
	private StringProperty nombre = new SimpleStringProperty();
	private StringProperty nota = new SimpleStringProperty();
	private StringProperty tipo = new SimpleStringProperty();
	private ObjectProperty<TipoInstalacion> objTipoInstalacion = new SimpleObjectProperty<TipoInstalacion>();
	private IntegerProperty codImagen = new SimpleIntegerProperty();
	private ObjectProperty<ImagenInstalacion> objImagenInstalacion = new SimpleObjectProperty<ImagenInstalacion>();

	public Instalacion() {
		// TODO Auto-generated constructor stub
	}

	public Instalacion(Instalacion instalacion) {
		copiarValores(instalacion);
	}
	
	public void copiarValores(Instalacion instalacion) {
		this.codInstalacionProperty().set(instalacion.getCodInstalacion());
		this.codEstacionProperty().set(instalacion.getCodEstacion());
		this.objEstacionProperty().set(instalacion.getObjEstacion());
		this.nombreProperty().set(instalacion.getNombre());
		this.notaProperty().set(instalacion.getNota());
		this.tipoProperty().set(instalacion.getTipo());
		this.objTipoInstalacionProperty().set(instalacion.getObjTipoInstalacion());
		this.codImagenProperty().set(instalacion.getCodImagen());
		this.objImagenInstalacionProperty().set(instalacion.getObjImagenInstalacion());
	}

	public final IntegerProperty codInstalacionProperty() {
		return this.codInstalacion;
	}

	public final int getCodInstalacion() {
		return this.codInstalacionProperty().get();
	}

	public final void setCodInstalacion(final int codInstalacion) {
		this.codInstalacionProperty().set(codInstalacion);
	}

	public final IntegerProperty codEstacionProperty() {
		return this.codEstacion;
	}

	public final int getCodEstacion() {
		return this.codEstacionProperty().get();
	}

	public final void setCodEstacion(final int codEstacion) {
		this.codEstacionProperty().set(codEstacion);
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

	public final StringProperty notaProperty() {
		return this.nota;
	}

	public final String getNota() {
		return this.notaProperty().get();
	}

	public final void setNota(final String nota) {
		this.notaProperty().set(nota);
	}

	public final StringProperty tipoProperty() {
		return this.tipo;
	}

	public final String getTipo() {
		return this.tipoProperty().get();
	}

	public final void setTipo(final String tipo) {
		this.tipoProperty().set(tipo);
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
	
	public final ObjectProperty<Estacion> objEstacionProperty() {
		return this.objEstacion;
	}
	
	public final Estacion getObjEstacion() {
		return this.objEstacionProperty().get();
	}
	
	public final void setObjEstacion(final Estacion estacion) {
		this.objEstacionProperty().set(estacion);
		this.codEstacionProperty().set(estacion.getCodEstacion());
	}
	
	public final ObjectProperty<TipoInstalacion> objTipoInstalacionProperty() {
		return this.objTipoInstalacion;
	}
	
	public final TipoInstalacion getObjTipoInstalacion() {
		return this.objTipoInstalacionProperty().get();
	}
	
	public final void setObjTipoInstalacion(final TipoInstalacion objTipoInstalacion) {
		this.objTipoInstalacionProperty().set(objTipoInstalacion);
		this.tipoProperty().set(objTipoInstalacion.getNombre());
	}
	
	public final ObjectProperty<ImagenInstalacion> objImagenInstalacionProperty() {
		return this.objImagenInstalacion;
	}
	
	public final ImagenInstalacion getObjImagenInstalacion() {
		return this.objImagenInstalacionProperty().get();
	}
	
	public final void setObjImagenInstalacion(final ImagenInstalacion imagenInstalacion) {
		this.objImagenInstalacionProperty().set(imagenInstalacion);
		this.codImagenProperty().set(imagenInstalacion.getCodImagen());
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + this.codInstalacion.get();
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
		Instalacion other = (Instalacion) obj;
		if (codEstacion == null) {
			if (other.codEstacion != null)
				return false;
		} else if (!codEstacion.equals(other.codEstacion))
			return false;
		if (this.codInstalacion.get() != other.codInstalacion.get()) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "Instalacion [codInstalacion=" + codInstalacion.get() + ", codEstacion=" + codEstacion.get()
				+ ", nombre=" + nombre.get() + ", tipo=" + tipo + "]";
	}
	
	public InstalacionPK createPK() {
		return new InstalacionPK(getCodInstalacion());
	}
	
}
