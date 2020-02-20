package es.rfgr.cr4vegas.modelo.postgresql.dto.parteoficial;

import java.io.Serializable;
import java.time.LocalDateTime;

import es.rfgr.cr4vegas.modelo.postgresql.dto.parte.Parte;
import es.rfgr.cr4vegas.modelo.postgresql.dto.parte.TipoParte;
import es.rfgr.cr4vegas.utileria.OperaMaterial;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

@SuppressWarnings("serial")
public class ParteOficial implements OperaMaterial, Serializable {

	private IntegerProperty codParte = new SimpleIntegerProperty();
	private StringProperty idHidrante = new SimpleStringProperty();
	private ObjectProperty<LocalDateTime> fecha = new SimpleObjectProperty<LocalDateTime>();
	private IntegerProperty tipo = new SimpleIntegerProperty();
	private ObjectProperty<TipoParte> objTipoParte = new SimpleObjectProperty<TipoParte>();
	private StringProperty descripcion = new SimpleStringProperty();
	private StringProperty orden = new SimpleStringProperty();
	private BooleanProperty estado = new SimpleBooleanProperty();
	private StringProperty telefono = new SimpleStringProperty();
	private StringProperty llamo = new SimpleStringProperty();
	private StringProperty contador = new SimpleStringProperty();
	private BooleanProperty impreso = new SimpleBooleanProperty();

	public ParteOficial() {

	}

	public ParteOficial(ParteOficial parte) {
		copiarValores(parte);
	}

	public void copiarValores(ParteOficial parte) {
		this.codParteProperty().set(parte.getCodParte());
		this.idHidranteProperty().set(parte.getIdHidrante());
		this.tipoProperty().set(parte.getTipo());
		this.objTipoParteProperty().set(parte.getObjTipoParte());
		this.ordenProperty().set(parte.getOrden());
		this.descripcionProperty().set(parte.getDescripcion());
		this.fechaProperty().set(parte.getFecha());
		this.llamoProperty().set(parte.getLlamo());
		this.contadorProperty().set(parte.getContador());
		this.telefonoProperty().set(parte.getTelefono());
		this.estadoProperty().set(parte.isEstado());
		this.impresoProperty().set(parte.isImpreso());
	}

	public void copiarValoresDeParteAlmacen(Parte parteAlmacen) {
		this.tipoProperty().set(parteAlmacen.getTipo());
		this.objTipoParteProperty().set(parteAlmacen.getObjTipoParte());
		this.ordenProperty().set(parteAlmacen.getOrden());
		this.descripcionProperty().set(parteAlmacen.getDescripcion());
		this.fechaProperty().set(parteAlmacen.getFecha());
		this.llamoProperty().set(parteAlmacen.getLlamo());
		this.contadorProperty().set(parteAlmacen.getContador());
		this.telefonoProperty().set(parteAlmacen.getTelefono());
		this.estadoProperty().set(parteAlmacen.isEstado());
		this.impresoProperty().set(parteAlmacen.isImpreso());
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

	public final StringProperty idHidranteProperty() {
		return this.idHidrante;
	}

	public final String getIdHidrante() {
		return this.idHidranteProperty().get();
	}

	public final void setIdHidrante(final String idHidrante) {
		this.idHidranteProperty().set(idHidrante);
	}

	public final IntegerProperty tipoProperty() {
		return this.tipo;
	}

	public final int getTipo() {
		return this.tipoProperty().get();
	}

	public final void setTipo(final int tipo) {
		this.tipoProperty().set(tipo);
	}

	public final StringProperty ordenProperty() {
		return this.orden;
	}

	public final String getOrden() {
		return this.ordenProperty().get();
	}

	public final void setOrden(final String orden) {
		this.ordenProperty().set(orden);
	}

	public final StringProperty descripcionProperty() {
		return this.descripcion;
	}

	public final String getDescripcion() {
		return this.descripcionProperty().get();
	}

	public final void setDescripcion(final String descripcion) {
		this.descripcionProperty().set(descripcion);
	}

	public final ObjectProperty<LocalDateTime> fechaProperty() {
		return this.fecha;
	}

	public final LocalDateTime getFecha() {
		return this.fechaProperty().get();
	}

	public void setFecha(LocalDateTime fecha) {
		this.fechaProperty().set(fecha);
	}

	public final StringProperty llamoProperty() {
		return this.llamo;
	}

	public final String getLlamo() {
		return this.llamoProperty().get();
	}

	public final void setLlamo(final String llamo) {
		this.llamoProperty().set(llamo);
	}

	public final StringProperty contadorProperty() {
		return this.contador;
	}

	public final String getContador() {
		return this.contadorProperty().get();
	}

	public final void setContador(final String contador) {
		this.contadorProperty().set(contador);
	}

	public final StringProperty telefonoProperty() {
		return this.telefono;
	}

	public final String getTelefono() {
		return this.telefonoProperty().get();
	}

	public final void setTelefono(final String telefono) {
		this.telefonoProperty().set(telefono);
	}

	public final BooleanProperty estadoProperty() {
		return this.estado;
	}

	public final boolean isEstado() {
		return this.estadoProperty().get();
	}

	public final void setEstado(final boolean estado) {
		this.estadoProperty().set(estado);
	}

	public final BooleanProperty impresoProperty() {
		return this.impreso;
	}

	public final boolean isImpreso() {
		return this.impresoProperty().get();
	}

	public final void setImpreso(final boolean impreso) {
		this.impresoProperty().set(impreso);
	}

	public final ObjectProperty<TipoParte> objTipoParteProperty() {
		return this.objTipoParte;
	}

	public final TipoParte getObjTipoParte() {
		return this.objTipoParteProperty().get();
	}

	public final void setObjTipoParte(final TipoParte objTipoParte) {
		if (objTipoParte == null) {
			System.out.println("que es lo que es");
		} else {

		}
		this.objTipoParteProperty().set(objTipoParte);
		this.tipoProperty().set(objTipoParte.getCodigo());
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + this.codParte.get();
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
		ParteOficial other = (ParteOficial) obj;
		if (this.codParte.get() != other.codParte.get()) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "ParteOficial [codParte=" + codParte + ", idHidrante=" + idHidrante + ", fecha=" + fecha + ", tipo="
				+ tipo + ", objTipoParte=" + objTipoParte + ", descripcion=" + descripcion + ", orden=" + orden
				+ ", estado=" + estado + ", telefono=" + telefono + ", llamo=" + llamo + ", contador=" + contador
				+ ", impreso=" + impreso + "]";
	}

	public ParteOficialPK createPK() {
		return new ParteOficialPK(getCodParte());
	}

}
