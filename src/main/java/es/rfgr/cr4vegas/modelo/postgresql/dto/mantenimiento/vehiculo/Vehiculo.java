package es.rfgr.cr4vegas.modelo.postgresql.dto.mantenimiento.vehiculo;

import java.io.Serializable;
import java.time.LocalDate;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

@SuppressWarnings("serial")
public class Vehiculo implements Serializable {

	private StringProperty matricula = new SimpleStringProperty();
	private BooleanProperty activo = new SimpleBooleanProperty();
	private StringProperty modelo = new SimpleStringProperty();
	private StringProperty marca = new SimpleStringProperty();
	private StringProperty numBastidor = new SimpleStringProperty();
	private ObjectProperty<LocalDate> fechaMatriculacion = new SimpleObjectProperty<LocalDate>();
	private IntegerProperty km = new SimpleIntegerProperty();
	private ObjectProperty<LocalDate> fechaItv = new SimpleObjectProperty<LocalDate>();
	private BooleanProperty itv = new SimpleBooleanProperty();
	private StringProperty nota = new SimpleStringProperty();
	private IntegerProperty codImagen = new SimpleIntegerProperty();
	private ObjectProperty<ImagenVehiculo> objImagenVehiculo = new SimpleObjectProperty<ImagenVehiculo>();

	public Vehiculo() {
		// TODO Auto-generated constructor stub
	}

	public Vehiculo(Vehiculo vehiculo) {
		copiarValores(vehiculo);
	}

	public void copiarValores(Vehiculo vehiculo) {
		this.matriculaProperty().set(vehiculo.getMatricula());
		this.activoProperty().set(vehiculo.isActivo());
		this.modeloProperty().set(vehiculo.getModelo());
		this.marcaProperty().set(vehiculo.getMarca());
		this.numBastidorProperty().set(vehiculo.getNumBastidor());
		this.fechaMatriculacionProperty().set(vehiculo.getFechaMatriculacion());
		this.kmProperty().set(vehiculo.getKm());
		this.fechaItvProperty().set(vehiculo.getFechaItv());
		this.itvProperty().set(vehiculo.isItv());
		this.notaProperty().set(vehiculo.getNota());
		this.codImagenProperty().set(vehiculo.getCodImagen());
		this.objImagenVehiculoProperty().set(vehiculo.getObjImagenVehiculo());
	}

	public final StringProperty matriculaProperty() {
		return this.matricula;
	}

	public final String getMatricula() {
		return this.matriculaProperty().get();
	}

	public final void setMatricula(final String matricula) {
		this.matriculaProperty().set(matricula);
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

	public final StringProperty modeloProperty() {
		return this.modelo;
	}

	public final String getModelo() {
		return this.modeloProperty().get();
	}

	public final void setModelo(final String modelo) {
		this.modeloProperty().set(modelo);
	}

	public final StringProperty marcaProperty() {
		return this.marca;
	}

	public final String getMarca() {
		return this.marcaProperty().get();
	}

	public final void setMarca(final String marca) {
		this.marcaProperty().set(marca);
	}

	public final StringProperty numBastidorProperty() {
		return this.numBastidor;
	}

	public final String getNumBastidor() {
		return this.numBastidorProperty().get();
	}

	public final void setNumBastidor(final String numBastidor) {
		this.numBastidorProperty().set(numBastidor);
	}

	public final ObjectProperty<LocalDate> fechaMatriculacionProperty() {
		return this.fechaMatriculacion;
	}

	public final LocalDate getFechaMatriculacion() {
		return this.fechaMatriculacionProperty().get();
	}

	public final void setFechaMatriculacion(final LocalDate fechaMatriculacion) {
		this.fechaMatriculacionProperty().set(fechaMatriculacion);
	}

	public final IntegerProperty kmProperty() {
		return this.km;
	}

	public final int getKm() {
		return this.kmProperty().get();
	}

	public final void setKm(final int km) {
		this.kmProperty().set(km);
	}

	public final ObjectProperty<LocalDate> fechaItvProperty() {
		return this.fechaItv;
	}

	public final LocalDate getFechaItv() {
		return this.fechaItvProperty().get();
	}

	public final void setFechaItv(final LocalDate fechaItv) {
		this.fechaItvProperty().set(fechaItv);
	}

	public final BooleanProperty itvProperty() {
		return this.itv;
	}

	public final boolean isItv() {
		return this.itvProperty().get();
	}

	public final void setItv(final boolean itv) {
		this.itvProperty().set(itv);
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

	public final IntegerProperty codImagenProperty() {
		return this.codImagen;
	}

	public final int getCodImagen() {
		return this.codImagenProperty().get();
	}

	public final void setCodImagen(final int nota) {
		this.codImagenProperty().set(nota);
	}

	public final ObjectProperty<ImagenVehiculo> objImagenVehiculoProperty() {
		return this.objImagenVehiculo;
	}

	public final ImagenVehiculo getObjImagenVehiculo() {
		return this.objImagenVehiculoProperty().get();
	}

	public final void setObjImagenVehiculo(final ImagenVehiculo objImagenVehiculo) {
		this.objImagenVehiculoProperty().set(objImagenVehiculo);
		this.codImagenProperty().set(objImagenVehiculo.getCodImagen());
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((matricula == null) ? 0 : matricula.hashCode());
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
		Vehiculo other = (Vehiculo) obj;
		if (matricula == null) {
			if (other.matricula != null)
				return false;
		} else if (!matricula.equals(other.matricula))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Vehiculo [matricula=" + matricula.get() + ", activo=" + activo.get() + ", modelo=" + modelo.get()
				+ ", marca=" + marca.get() + ", numBastidor=" + numBastidor.get() + ", fechaMatriculacion="
				+ fechaMatriculacion.getName() + ", km=" + km.get() + ", fechaItv=" + fechaItv.getName() + ", itv="
				+ itv.get() + ", nota=" + nota.get() + "]";
	}

	public VehiculoPK createPK() {
		return new VehiculoPK(getMatricula());
	}
}
