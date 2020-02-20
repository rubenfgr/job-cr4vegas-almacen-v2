package es.rfgr.cr4vegas.modelo.postgresql.dto.operario;

import java.io.Serializable;

import es.rfgr.cr4vegas.modelo.postgresql.dto.grupo.Grupo;
import es.rfgr.cr4vegas.utileria.ImagenUnica;
import es.rfgr.cr4vegas.utileria.OperaMaterial;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.image.Image;

@SuppressWarnings("serial")
public class Operario implements ImagenUnica, OperaMaterial, Serializable {

	private IntegerProperty codOp = new SimpleIntegerProperty();
	private BooleanProperty activo = new SimpleBooleanProperty();
	private StringProperty nombre = new SimpleStringProperty();
	private StringProperty apellido1 = new SimpleStringProperty();
	private StringProperty apellido2 = new SimpleStringProperty();
	private StringProperty grupo = new SimpleStringProperty();
	private ObjectProperty<Grupo> objGrupo = new SimpleObjectProperty<Grupo>();
	private ObjectProperty<byte[]> imagen = new SimpleObjectProperty<byte[]>();
	private StringProperty tCalzado = new SimpleStringProperty();
	private StringProperty tPantLargo = new SimpleStringProperty();
	private StringProperty tPantCorto = new SimpleStringProperty();
	private StringProperty tChaqueta = new SimpleStringProperty();
	private StringProperty tChaqueton = new SimpleStringProperty();
	private StringProperty tForro = new SimpleStringProperty();
	private StringProperty tPolo = new SimpleStringProperty();

	public Operario() {

	}

	public Operario(Operario operario) {
		copiarValores(operario);
	}

	public void copiarValores(Operario operario) {
		this.codOpProperty().set(operario.getCodOp());
		this.activoProperty().set(operario.isActivo());
		this.nombreProperty().set(operario.getNombre());
		this.apellido1Property().set(operario.getApellido1());
		this.apellido2Property().set(operario.getApellido2());
		this.grupoProperty().set(operario.getGrupo());
		this.objGrupoProperty().set(operario.getObjGrupo());
		this.imagenProperty().set(operario.getImagenBytes());
		this.tCalzadoProperty().set(operario.getTCalzado());
		this.tPantLargoProperty().set(operario.getTPantLargo());
		this.tPantCortoProperty().set(operario.getTPantCorto());
		this.tChaquetaProperty().set(operario.getTChaqueta());
		this.tChaquetonProperty().set(operario.getTChaqueton());
		this.tForroProperty().set(operario.getTForro());
		this.tPoloProperty().set(operario.getTPolo());
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

	public final void setCodOp(final String codOp) {
		try {
			if (codOp != null) {
				this.codOpProperty().set(Integer.valueOf(codOp));
			} else {
				throw new NullPointerException("El código del operario no puede ser nulo.");
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
			throw new IllegalArgumentException("El código del operario no es valido.");
		}
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

	public final StringProperty nombreProperty() {
		return this.nombre;
	}

	public final String getNombre() {
		return this.nombreProperty().get();
	}

	public final void setNombre(final String nombre) {
		this.nombreProperty().set(nombre);
	}

	public final StringProperty apellido1Property() {
		return this.apellido1;
	}

	public final String getApellido1() {
		return this.apellido1Property().get();
	}

	public final void setApellido1(final String apellido1) {
		this.apellido1Property().set(apellido1);
	}

	public final StringProperty apellido2Property() {
		return this.apellido2;
	}

	public final String getApellido2() {
		return this.apellido2Property().get();
	}

	public final void setApellido2(final String apellido2) {
		this.apellido2Property().set(apellido2);
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

	public final ObjectProperty<byte[]> imagenProperty() {
		return this.imagen;
	}

	public final byte[] getImagenBytes() {
		return this.imagenProperty().get();
	}

	public final void setImagenBytes(final byte[] foto) {
		this.imagenProperty().set(foto);
	}

	public final StringProperty tCalzadoProperty() {
		return this.tCalzado;
	}

	public final String getTCalzado() {
		return this.tCalzadoProperty().get();
	}

	public final void setTCalzado(final String tCalzado) {
		this.tCalzadoProperty().set(tCalzado);
	}

	public final StringProperty tPantLargoProperty() {
		return this.tPantLargo;
	}

	public final String getTPantLargo() {
		return this.tPantLargoProperty().get();
	}

	public final void setTPantLargo(final String tPantLargo) {
		this.tPantLargoProperty().set(tPantLargo);
	}

	public final StringProperty tPantCortoProperty() {
		return this.tPantCorto;
	}

	public final String getTPantCorto() {
		return this.tPantCortoProperty().get();
	}

	public final void setTPantCorto(final String tPantCorto) {
		this.tPantCortoProperty().set(tPantCorto);
	}

	public final StringProperty tChaquetaProperty() {
		return this.tChaqueta;
	}

	public final String getTChaqueta() {
		return this.tChaquetaProperty().get();
	}

	public final void setTChaqueta(final String tChaqueta) {
		this.tChaquetaProperty().set(tChaqueta);
	}

	public final StringProperty tChaquetonProperty() {
		return this.tChaqueton;
	}

	public final String getTChaqueton() {
		return this.tChaquetonProperty().get();
	}

	public final void setTChaqueton(final String tChaqueton) {
		this.tChaquetonProperty().set(tChaqueton);
	}

	public final StringProperty tForroProperty() {
		return this.tForro;
	}

	public final String getTForro() {
		return this.tForroProperty().get();
	}

	public final void setTForro(final String tForro) {
		this.tForroProperty().set(tForro);
	}

	public final StringProperty tPoloProperty() {
		return this.tPolo;
	}

	public final String getTPolo() {
		return this.tPoloProperty().get();
	}

	public final void setTPolo(final String tPolo) {
		this.tPoloProperty().set(tPolo);
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
		} else {
			throw new NullPointerException("El operario debe pertenecer a un grupo.");
		}
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
		Operario other = (Operario) obj;
		if (codOp == null) {
			if (other.codOp != null)
				return false;
		} else if (codOp.get() != other.codOpProperty().get())
			return false;
		return true;
	}

	@Override
	public String toString() {
		return codOp.get() + " - " + nombre.get() + " " + apellido1.get() + " " + apellido2.get();
	}

	public OperarioPK createPK() {
		return new OperarioPK(getCodOp());
	}

	@Override
	public Image getImagen() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setImagen(Image imagen) {
		// TODO Auto-generated method stub
		
	}
	
}
