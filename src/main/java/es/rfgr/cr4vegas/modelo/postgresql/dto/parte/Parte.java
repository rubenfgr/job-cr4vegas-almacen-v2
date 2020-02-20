package es.rfgr.cr4vegas.modelo.postgresql.dto.parte;

import java.io.Serializable;
import java.time.LocalDateTime;

import es.rfgr.cr4vegas.modelo.postgresql.dto.grupo.Grupo;
import es.rfgr.cr4vegas.modelo.postgresql.dto.parteoficial.ParteOficial;
import es.rfgr.cr4vegas.utileria.OperaMaterial;
import es.rfgr.cr4vegas.utileria.Origen;
import es.rfgr.cr4vegas.utileria.Prioridad;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

@SuppressWarnings("serial")
public class Parte implements OperaMaterial, Serializable {

	private IntegerProperty codParte = new SimpleIntegerProperty();
	private IntegerProperty parteOficial = new SimpleIntegerProperty();
	private ObjectProperty<ParteOficial> objParteOficial = new SimpleObjectProperty<ParteOficial>();
	private IntegerProperty tipo = new SimpleIntegerProperty();
	private ObjectProperty<TipoParte> objTipoParte = new SimpleObjectProperty<TipoParte>();
	private StringProperty grupo = new SimpleStringProperty();
	private ObjectProperty<Grupo> objGrupo = new SimpleObjectProperty<Grupo>();
	private ObjectProperty<Origen> origen = new SimpleObjectProperty<Origen>();
	private StringProperty hidrante = new SimpleStringProperty();
	private StringProperty orden = new SimpleStringProperty();
	private StringProperty descripcion = new SimpleStringProperty();
	private ObjectProperty<Prioridad> prioridad = new SimpleObjectProperty<>();
	private ObjectProperty<LocalDateTime> fecha = new SimpleObjectProperty<LocalDateTime>();
	private StringProperty estimado = new SimpleStringProperty();
	private StringProperty llamo = new SimpleStringProperty();
	private StringProperty contador = new SimpleStringProperty();
	private StringProperty telefono = new SimpleStringProperty();
	private BooleanProperty estado = new SimpleBooleanProperty();
	private BooleanProperty impreso = new SimpleBooleanProperty();

	public Parte() {

	}

	public Parte(Parte parte) {
		copiarValores(parte);
	}

	public void copiarValores(Parte parte) {
		this.codParteProperty().set(parte.getCodParte());
		this.parteOficialProperty().set(parte.getParteOficial());
		this.objParteOficialProperty().set(parte.getObjParteOficial());
		this.tipoProperty().set(parte.getTipo());
		this.objTipoParteProperty().set(parte.getObjTipoParte());
		this.grupoProperty().set(parte.getGrupo());
		this.objGrupoProperty().set(parte.getObjGrupo());
		this.origenProperty().set(parte.getOrigen());
		this.hidranteProperty().set(parte.getHidrante());
		this.ordenProperty().set(parte.getOrden());
		this.descripcionProperty().set(parte.getDescripcion());
		this.prioridadProperty().set(parte.getPrioridad());
		this.fechaProperty().set(parte.getFecha());
		this.estimadoProperty().set(parte.getEstimado());
		this.llamoProperty().set(parte.getLlamo());
		this.contadorProperty().set(parte.getContador());
		this.telefonoProperty().set(parte.getTelefono());
		this.estadoProperty().set(parte.isEstado());
		this.impresoProperty().set(parte.isImpreso());
	}

	public void copiarValoresDeParteOficial(ParteOficial parteOficial) {
		this.parteOficialProperty().set(parteOficial.getCodParte());
		this.objParteOficialProperty().set(parteOficial);
		this.tipoProperty().set(parteOficial.getTipo());
		this.objTipoParteProperty().set(parteOficial.getObjTipoParte());
		this.grupoProperty().set("SIN GRUPO");
		this.objGrupoProperty().set(null);
		this.origenProperty().set(Origen.PARTE_OFICINA);
		this.hidranteProperty().set(parteOficial.getIdHidrante());
		this.ordenProperty().set(parteOficial.getOrden());
		this.descripcionProperty().set(parteOficial.getDescripcion());
		this.prioridadProperty().set(Prioridad.MEDIA);
		this.fechaProperty().set(parteOficial.getFecha());
		this.estimadoProperty().set("");
		this.llamoProperty().set(parteOficial.getLlamo());
		this.contadorProperty().set(parteOficial.getContador());
		this.telefonoProperty().set(parteOficial.getTelefono());
		this.estadoProperty().set(parteOficial.isEstado());
		this.impresoProperty().set(parteOficial.isImpreso());
	}

	public boolean esIgualA(ParteOficial po) {
		if (tipoProperty().equals(po.tipoProperty()) && ordenProperty().equals(po.ordenProperty())
				&& descripcionProperty().equals(po.descripcionProperty()) && llamoProperty().equals(po.llamoProperty())
				&& contadorProperty().equals(po.contadorProperty()) && telefonoProperty().equals(po.telefonoProperty())
				&& estadoProperty().equals(po.estadoProperty()) && impresoProperty().equals(po.impresoProperty())) {
			return false;
		}
		return true;
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

	public final IntegerProperty parteOficialProperty() {
		return this.parteOficial;
	}

	public final int getParteOficial() {
		return this.parteOficialProperty().get();
	}

	public final void setParteOficial(final int parteOficial) {
		this.parteOficialProperty().set(parteOficial);
	}

	public final ObjectProperty<ParteOficial> objParteOficialProperty() {
		return this.objParteOficial;
	}

	public final ParteOficial getObjParteOficial() {
		return this.objParteOficialProperty().get();
	}

	public final void setObjParteOficial(final ParteOficial objParteOficial) {
		this.parteOficialProperty().set(objParteOficial.getCodParte());
		this.objParteOficialProperty().set(objParteOficial);
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

	public final StringProperty grupoProperty() {
		return this.grupo;
	}

	public final String getGrupo() {
		return this.grupoProperty().get();
	}

	public final void setGrupo(final String grupo) {
		this.grupoProperty().set(grupo);
	}

	public final ObjectProperty<Origen> origenProperty() {
		return this.origen;
	}

	public final Origen getOrigen() {
		return this.origenProperty().get();
	}

	public final void setOrigen(Origen origen) {
		if (origen == null) {
			origen = Origen.NINGUNO;
		}
		this.origenProperty().set(origen);
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

	public final ObjectProperty<Prioridad> prioridadProperty() {
		return this.prioridad;
	}

	public final Prioridad getPrioridad() {
		return this.prioridadProperty().get();
	}

	public final void setPrioridad(Prioridad prioridad) {
		if (prioridad == null) {
			prioridad = Prioridad.NINGUNA;
		}
		this.prioridadProperty().set(prioridad);
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

	public void setFecha(String fecha) {
		this.fechaProperty().set(LocalDateTime.parse(fecha));
	}

	public final StringProperty estimadoProperty() {
		return this.estimado;
	}

	public final String getEstimado() {
		return this.estimadoProperty().get();
	}

	public final void setEstimado(final String estimado) {
		this.estimadoProperty().set(estimado);
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
		if (objTipoParte != null) {
			this.objTipoParteProperty().set(objTipoParte);
			this.tipoProperty().set(objTipoParte.getCodigo());
		}
	}

	public final ObjectProperty<Grupo> objGrupoProperty() {
		return this.objGrupo;
	}

	public final Grupo getObjGrupo() {
		return this.objGrupoProperty().get();
	}

	public final void setObjGrupo(final Grupo objGrupo) {
		if (objGrupo != null) {
			this.objGrupoProperty().set(objGrupo);
			this.grupoProperty().set(objGrupo.getNombre());
		}
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
		Parte other = (Parte) obj;
		if (this.codParte.get() != other.codParte.get()) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		String codAlmacen = codParte.get() == 0? "" : "C.Almacen: " + codParte.get();
		String codOficina = parteOficial.get() == 0 || parteOficial == null? "" : ", C.Oficina: " + parteOficial.get();
		String codHidrante = hidrante.get() == null || hidrante.get().equals("")? "" : ", Hidrante: " + hidrante.get();
		return codAlmacen + codOficina + codHidrante;
	}

	public PartePK createPK() {
		return new PartePK(getCodParte());
	}

	public final StringProperty hidranteProperty() {
		return this.hidrante;
	}

	public final String getHidrante() {
		return this.hidranteProperty().get();
	}

	public final void setHidrante(final String hidrante) {
		this.hidranteProperty().set(hidrante);
	}

}
