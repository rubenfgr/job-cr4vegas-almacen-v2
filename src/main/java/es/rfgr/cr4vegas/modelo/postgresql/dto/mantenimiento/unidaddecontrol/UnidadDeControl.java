package es.rfgr.cr4vegas.modelo.postgresql.dto.mantenimiento.unidaddecontrol;

import java.io.Serializable;

import es.rfgr.cr4vegas.modelo.postgresql.dto.mantenimiento.instalacion.Instalacion;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

@SuppressWarnings("serial")
public class UnidadDeControl implements Serializable {

	private IntegerProperty codUdControl = new SimpleIntegerProperty();
	private IntegerProperty codInstalacion = new SimpleIntegerProperty();
	private ObjectProperty<Instalacion> objInstalacion = new SimpleObjectProperty<Instalacion>();
	private StringProperty nombre = new SimpleStringProperty();
	private StringProperty modelo = new SimpleStringProperty();
	private StringProperty nota = new SimpleStringProperty();
	private BooleanProperty activo = new SimpleBooleanProperty();
	private StringProperty normativa = new SimpleStringProperty();
	private StringProperty mantenimiento = new SimpleStringProperty();
	private IntegerProperty codImagen = new SimpleIntegerProperty();
	private ObjectProperty<ImagenUnidadDeControl> objImagenUdControl = new SimpleObjectProperty<ImagenUnidadDeControl>();

	public UnidadDeControl() {
		
	}

	public UnidadDeControl(UnidadDeControl unidadDeControl) {
		copiarValores(unidadDeControl);
	}

	public void copiarValores(UnidadDeControl unidadDeControl) {
		this.codUdControlProperty().set(unidadDeControl.getCodUdControl());
		this.codInstalacionProperty().set(unidadDeControl.getCodInstalacion());
		this.objInstalacionProperty().set(unidadDeControl.getObjInstalacion());
		this.nombreProperty().set(unidadDeControl.getNombre());
		this.modeloProperty().set(unidadDeControl.getModelo());
		this.notaProperty().set(unidadDeControl.getNota());
		this.activoProperty().set(unidadDeControl.isActivo());
		this.normativaProperty().set(unidadDeControl.getNormativa());
		this.mantenimientoProperty().set(unidadDeControl.getMantenimiento());
		this.codImagenProperty().set(unidadDeControl.getCodImagen());
		this.objImagenUdControlProperty().set(unidadDeControl.getObjImagenUdControl());
	}

	public final IntegerProperty codUdControlProperty() {
		return this.codUdControl;
	}

	public final int getCodUdControl() {
		return this.codUdControlProperty().get();
	}

	public final void setCodUdControl(final int codUdControl) {
		this.codUdControlProperty().set(codUdControl);
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

	public final StringProperty nombreProperty() {
		return this.nombre;
	}

	public final String getNombre() {
		return this.nombreProperty().get();
	}

	public final void setNombre(final String nombre) {
		this.nombreProperty().set(nombre);
	}

	public final StringProperty modeloProperty() {
		return this.modelo;
	}

	public final String getModelo() {
		return this.modeloProperty().get();
	}

	public final void setModelo(final String modelo) {
		this.modeloProperty().set(modelo);
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

	public final BooleanProperty activoProperty() {
		return this.activo;
	}

	public final boolean isActivo() {
		return this.activoProperty().get();
	}

	public final void setActivo(final boolean activo) {
		this.activoProperty().set(activo);
	}

	public final StringProperty normativaProperty() {
		return this.normativa;
	}

	public final String getNormativa() {
		return this.normativaProperty().get();
	}

	public final void setNormativa(final String normativa) {
		this.normativaProperty().set(normativa);
	}

	public final StringProperty mantenimientoProperty() {
		return this.mantenimiento;
	}

	public final String getMantenimiento() {
		return this.mantenimientoProperty().get();
	}

	public final void setMantenimiento(final String mantenimiento) {
		this.mantenimientoProperty().set(mantenimiento);
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

	public final ObjectProperty<Instalacion> objInstalacionProperty() {
		return this.objInstalacion;
	}

	public final Instalacion getObjInstalacion() {
		return this.objInstalacionProperty().get();
	}

	public final void setObjInstalacion(final Instalacion objInstalacion) {
		this.objInstalacionProperty().set(objInstalacion);
		this.codInstalacionProperty().set(objInstalacion.getCodInstalacion());
	}

	public final ObjectProperty<ImagenUnidadDeControl> objImagenUdControlProperty() {
		return this.objImagenUdControl;
	}

	public final ImagenUnidadDeControl getObjImagenUdControl() {
		return this.objImagenUdControlProperty().get();
	}

	public final void setObjImagenUdControl(final ImagenUnidadDeControl objImagenUdControl) {
		this.objImagenUdControlProperty().set(objImagenUdControl);
		this.codImagenProperty().set(objImagenUdControl.getCodImagen());
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + this.codUdControl.get();
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
		UnidadDeControl other = (UnidadDeControl) obj;
		if (this.codUdControl.get() != other.codUdControl.get()) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "UdControl [codUdControl=" + codUdControl.get() + ", codInstalacion=" + codInstalacion.get()
				+ ", nombre=" + nombre.get() + ", modelo=" + modelo.get() + ", activo=" + activo.getName() + "]";
	}

	public UnidadDeControlPK createPK() {
		return new UnidadDeControlPK(getCodUdControl());
	}

}
