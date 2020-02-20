package es.rfgr.cr4vegas.modelo.postgresql.dto.almacen.material;

import java.io.Serializable;

import es.rfgr.cr4vegas.utileria.ImagenUnica;
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
public class Material implements ImagenUnica, Serializable {

	private IntegerProperty codigo4v = new SimpleIntegerProperty();
	private BooleanProperty activo = new SimpleBooleanProperty();
	private StringProperty nombre = new SimpleStringProperty();
	private StringProperty nombreTec = new SimpleStringProperty();
	private IntegerProperty cantidad = new SimpleIntegerProperty();
	private IntegerProperty cantidadMin = new SimpleIntegerProperty();
	private StringProperty ubicacion = new SimpleStringProperty();
	private ObjectProperty<Ubicacion> objUbicacion = new SimpleObjectProperty<Ubicacion>();
	private StringProperty material = new SimpleStringProperty();
	private ObjectProperty<TipoMaterial> objTipoMaterial = new SimpleObjectProperty<TipoMaterial>();
	private StringProperty familia = new SimpleStringProperty();
	private ObjectProperty<Familia> objFamilia = new SimpleObjectProperty<Familia>();
	private StringProperty diametro = new SimpleStringProperty();
	private StringProperty pn = new SimpleStringProperty();
	private StringProperty marca = new SimpleStringProperty();
	private ObjectProperty<byte[]> imagen = new SimpleObjectProperty<byte[]>();

	public Material() {
		// TODO Auto-generated constructor stub
	}

	public Material(Material material) {
		copiarValores(material);
	}

	public void copiarValores(Material materialNewValues) {
		this.codigo4v.set(materialNewValues.getCodigo4v());
		this.activo.set(materialNewValues.isActivo());
		this.nombre.set(materialNewValues.getNombre());
		this.nombreTec.set(materialNewValues.getNombreTec());
		this.cantidad.set(materialNewValues.getCantidad());
		this.cantidadMin.set(materialNewValues.getCantidadMin());
		this.ubicacion.set(materialNewValues.getUbicacion());
		this.objUbicacion.set(materialNewValues.getObjUbicacion());
		this.material.set(materialNewValues.getMaterial());
		this.objTipoMaterial.set(materialNewValues.getObjTipoMaterial());
		this.familia.set(materialNewValues.getFamilia());
		this.objFamilia.set(materialNewValues.getObjFamilia());
		this.diametro.set(materialNewValues.getDiametro());
		this.pn.set(materialNewValues.getPn());
		this.marca.set(materialNewValues.getMarca());
		this.imagen.set(materialNewValues.getImagenBytes());
	}

	public final IntegerProperty codigo4vProperty() {
		return this.codigo4v;
	}

	public final int getCodigo4v() {
		return this.codigo4vProperty().get();
	}

	public final void setCodigo4v(final int codigo4v) {
		this.codigo4vProperty().set(codigo4v);
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

	public final StringProperty nombreTecProperty() {
		return this.nombreTec;
	}

	public final String getNombreTec() {
		return this.nombreTecProperty().get();
	}

	public final void setNombreTec(final String nombreTec) {
		this.nombreTecProperty().set(nombreTec);
	}

	public final IntegerProperty cantidadProperty() {
		return this.cantidad;
	}

	public final int getCantidad() {
		return this.cantidadProperty().get();
	}

	public final void setCantidad(final String cantidad) {
		try {
			this.cantidadProperty().set(Integer.parseInt(cantidad));
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException("La cantidad no es correcta.");
		}
	}

	public final void setCantidad(final int cantidad) {
		this.cantidadProperty().set(cantidad);
	}

	public final IntegerProperty cantidadMinProperty() {
		return this.cantidadMin;
	}

	public final int getCantidadMin() {
		return this.cantidadMinProperty().get();
	}

	public final void setCantidadMin(final String cantidadMin) {
		try {
			this.cantidadMinProperty().set(Integer.parseInt(cantidadMin));
		} catch (NumberFormatException e) {
			this.cantidadMinProperty().set(0);
		}
	}

	public final void setCantidadMin(final int cantidadMin) {
		this.cantidadMinProperty().set(cantidadMin);
	}

	public final StringProperty ubicacionProperty() {
		return this.ubicacion;
	}

	public final String getUbicacion() {
		return this.ubicacionProperty().get();
	}

	public final void setUbicacion(final String ubicacion) {
		this.ubicacionProperty().set(ubicacion);
	}

	public final StringProperty materialProperty() {
		return this.material;
	}

	public final String getMaterial() {
		return this.materialProperty().get();
	}

	public final void setMaterial(final String material) {
		this.materialProperty().set(material);
	}

	public final StringProperty familiaProperty() {
		return this.familia;
	}

	public final String getFamilia() {
		return this.familiaProperty().get();
	}

	public final void setFamilia(final String familia) {
		this.familiaProperty().set(familia);
	}

	public final StringProperty diametroProperty() {
		return this.diametro;
	}

	public final String getDiametro() {
		return this.diametroProperty().get();
	}

	public final void setDiametro(final String diametro) {
		this.diametroProperty().set(diametro);
	}

	public final StringProperty pnProperty() {
		return this.pn;
	}

	public final String getPn() {
		return this.pnProperty().get();
	}

	public final void setPn(final String pn) {
		this.pnProperty().set(pn);
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

	public final ObjectProperty<byte[]> imagenProperty() {
		return this.imagen;
	}

	public final byte[] getImagenBytes() {
		return this.imagenProperty().get();
	}

	public final void setImagenBytes(final byte[] imagen) {
		this.imagenProperty().set(imagen);
	}

	public final ObjectProperty<Ubicacion> objUbicacionProperty() {
		return this.objUbicacion;
	}

	public final Ubicacion getObjUbicacion() {
		return this.objUbicacionProperty().get();
	}

	public final void setObjUbicacion(final Ubicacion objUbicacion) {
		if (objUbicacion != null) {
			this.objUbicacionProperty().set(objUbicacion);
			this.ubicacionProperty().set(objUbicacion.getNombre());
		} else {
			throw new NullPointerException("La ubicación no puede ser nula.");
		}
	}

	public final ObjectProperty<TipoMaterial> objTipoMaterialProperty() {
		return this.objTipoMaterial;
	}

	public final TipoMaterial getObjTipoMaterial() {
		return this.objTipoMaterialProperty().get();
	}

	public final void setObjTipoMaterial(final TipoMaterial objTipoMaterial) {
		if (objTipoMaterial != null) {
			this.objTipoMaterialProperty().set(objTipoMaterial);
			this.materialProperty().set(objTipoMaterial.getNombre());
		}
	}

	public final ObjectProperty<Familia> objFamiliaProperty() {
		return this.objFamilia;
	}

	public final Familia getObjFamilia() {
		return this.objFamiliaProperty().get();
	}

	public final void setObjFamilia(final Familia objFamilia) {
		if (objFamilia != null) {
			this.objFamiliaProperty().set(objFamilia);
			this.familiaProperty().set(objFamilia.getNombre());
		} else {
			throw new NullPointerException("La familia no puede ser nula.");
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + codigo4v.hashCode();
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
		Material other = (Material) obj;
		if (codigo4v.get() != other.codigo4v.get()) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return nombreTec.get() + " (" + nombre.get() + ")";
	}

	public MaterialPK createPK() {
		return new MaterialPK(getCodigo4v());
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
