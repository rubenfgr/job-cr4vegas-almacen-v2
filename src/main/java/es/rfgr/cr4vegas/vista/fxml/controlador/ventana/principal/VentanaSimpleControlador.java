package es.rfgr.cr4vegas.vista.fxml.controlador.ventana.principal;

import es.rfgr.cr4vegas.controlador.Consumer;
import es.rfgr.cr4vegas.modelo.postgresql.dto.almacen.material.Familia;
import es.rfgr.cr4vegas.modelo.postgresql.dto.almacen.material.FamiliaPK;
import es.rfgr.cr4vegas.modelo.postgresql.dto.almacen.material.TipoMaterial;
import es.rfgr.cr4vegas.modelo.postgresql.dto.almacen.material.TipoMaterialPK;
import es.rfgr.cr4vegas.modelo.postgresql.dto.almacen.material.Ubicacion;
import es.rfgr.cr4vegas.modelo.postgresql.dto.almacen.material.UbicacionPK;
import es.rfgr.cr4vegas.modelo.postgresql.dto.almacen.taquilla.TipoTaquilla;
import es.rfgr.cr4vegas.modelo.postgresql.dto.almacen.taquilla.TipoTaquillaPK;
import es.rfgr.cr4vegas.modelo.postgresql.dto.grupo.Grupo;
import es.rfgr.cr4vegas.modelo.postgresql.dto.grupo.GrupoPK;
import es.rfgr.cr4vegas.vista.utileria.Dialogos;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class VentanaSimpleControlador<T> {

	@FXML
	private AnchorPane panelRaiz;

	@FXML
	private Button btAceptar;

	@FXML
	private ListView<T> lvObjetos;

	@FXML
	private TextField tfAgregarObjeto;

	@FXML
	private Button btAgregarObjeto;

	@FXML
	private TextField tfModificarObjeto;

	@FXML
	private Button btModificarObjeto;

	@FXML
	private Button btEliminarObjeto;

	private Consumer app;
	private String tipoObjeto;
	private Stage escenarioVentana;
	
	private Familia familia;
	private Ubicacion ubicacion;
	private TipoMaterial tipoMaterial;
	private TipoTaquilla tipoTaquilla;
	private Grupo grupo;
	
	private FamiliaPK familiaPK;
	private UbicacionPK ubicacionPK;
	private TipoMaterialPK tipoMaterialPK;
	private TipoTaquillaPK tipoTaquillaPK;	
	private GrupoPK grupoPK;

	public void setConsumer(Consumer app) {
		this.app = app;
	}

	public AnchorPane getPanelRaiz() {
		return panelRaiz;
	}

	public void iniciar() {
		panelRaiz.setOnMouseClicked(e -> desactivarCampos());
		lvObjetos.getSelectionModel().selectedItemProperty()
				.addListener((ov, oldValue, newValue) -> activarCampos(newValue));
		btAgregarObjeto.setOnAction(e -> agregarObjeto());
		btModificarObjeto.setOnAction(e -> modificarObjeto());
		btEliminarObjeto.setOnAction(e -> eliminarObjeto());

		btAceptar.setOnAction(e -> escenarioVentana.close());
	}

	@SuppressWarnings("unchecked")
	public void iniciarCon(String obj) {
		this.tipoObjeto = obj;
		String titulo = "";
		if (tipoObjeto.equals("familia")) {
			lvObjetos.setItems((ObservableList<T>) app.getFamiliaDAO().getFamilias());
			titulo = "FAMILIAS";
		}
		if (tipoObjeto.equals("ubicacion")) {
			lvObjetos.setItems((ObservableList<T>) app.getUbicacionDAO().getUbicaciones());
			titulo = "UBICACIONES";
		}
		if (tipoObjeto.equals("tipoMaterial")) {
			lvObjetos.setItems((ObservableList<T>) app.getTipoMaterialDAO().getTiposMaterial());
			titulo = "TIPOS DE MATERIAL";
		}
		if (tipoObjeto.equals("grupo")) {
			lvObjetos.setItems((ObservableList<T>) app.getGrupoDAO().getGrupos());
			titulo = "GRUPOS DE TRABAJO";
		}
		if (tipoObjeto.equals("tipoTaquilla")) {
			lvObjetos.setItems((ObservableList<T>) app.getTipoTaquillaDAO().getTiposTaquilla());
			titulo = "TIPOS DE ALMACEN";
		}
		escenarioVentana = app.getVistaModelo().getEscenario(panelRaiz, titulo);
		escenarioVentana.showAndWait();
	}

	private void activarCampos(T obj) {
		tfModificarObjeto.setDisable(false);
		btModificarObjeto.setDisable(false);
		btEliminarObjeto.setDisable(false);
		
		if (tipoObjeto.equals("familia")) {
			familia = new Familia((Familia) obj);
			familiaPK = familia.createPK();
			tfModificarObjeto.setText(familia.getNombre());
			
		}
		if (tipoObjeto.equals("ubicacion")) {
			ubicacion = new Ubicacion((Ubicacion) obj);
			ubicacionPK = ubicacion.createPK();
			tfModificarObjeto.setText(ubicacion.getNombre());
			
		}
		if (tipoObjeto.equals("tipoMaterial")) {
			tipoMaterial = new TipoMaterial((TipoMaterial) obj);
			tipoMaterialPK = tipoMaterial.createPK();
			tfModificarObjeto.setText(tipoMaterial.getNombre());
			
		}
		if (tipoObjeto.equals("grupo")) {
			grupo = new Grupo((Grupo) obj);
			grupoPK = grupo.createPK();
			tfModificarObjeto.setText(grupo.getNombre());
		}
		if (tipoObjeto.equals("tipoTaquilla")) {
			tipoTaquilla = new TipoTaquilla((TipoTaquilla) obj);
			tipoTaquillaPK = tipoTaquilla.createPK();
			tfModificarObjeto.setText(tipoTaquilla.getNombre());
		}
	}

	private void desactivarCampos() {
		lvObjetos.getSelectionModel().clearSelection();
		tfModificarObjeto.setText("");
		tfModificarObjeto.setDisable(true);
		btModificarObjeto.setDisable(true);
		btEliminarObjeto.setDisable(true);
	}

	private void agregarObjeto() {
		String nombre = tfAgregarObjeto.getText();
		try {
			if (tipoObjeto.equals("familia")) {
				Familia familia = new Familia();
				familia.setNombre(nombre.toUpperCase());
				app.getFamiliaDAO().insert(familia);
			}
			if (tipoObjeto.equals("ubicacion")) {
				Ubicacion ubicacion = new Ubicacion();
				ubicacion.setNombre(nombre.toUpperCase());
				app.getUbicacionDAO().insert(ubicacion);
			}
			if (tipoObjeto.equals("tipoMaterial")) {
				TipoMaterial tipoMaterial = new TipoMaterial();
				tipoMaterial.setNombre(nombre.toUpperCase());
				app.getTipoMaterialDAO().insert(tipoMaterial);
			}
			if (tipoObjeto.equals("grupo")) {
				Grupo grupo = new Grupo();
				grupo.setNombre(nombre.toUpperCase());
				app.getGrupoDAO().insert(grupo);
			}
			if (tipoObjeto.equals("tipoTaquilla")) {
				TipoTaquilla tipoTaquilla = new TipoTaquilla();
				tipoTaquilla.setNombre(nombre.toUpperCase());
				app.getTipoTaquillaDAO().insert(tipoTaquilla);
			}
			tfAgregarObjeto.setText("");
			
		} catch (Exception e) {
			Dialogos.mostrarDialogoExcepcion(e, escenarioVentana);
		}
	}

	private void modificarObjeto() {
		String nombre = tfModificarObjeto.getText();
		try {
			if (tipoObjeto.equals("familia")) {
				familia.setNombre(nombre.toUpperCase());
				app.getFamiliaDAO().update(familiaPK, familia);
			}
			if (tipoObjeto.equals("ubicacion")) {
				ubicacion.setNombre(nombre.toUpperCase());
				app.getUbicacionDAO().update(ubicacionPK, ubicacion);
			}
			if (tipoObjeto.equals("tipoMaterial")) {
				tipoMaterial.setNombre(nombre.toUpperCase());
				app.getTipoMaterialDAO().update(tipoMaterialPK, tipoMaterial);
			}
			if (tipoObjeto.equals("grupo")) {
				grupo.setNombre(nombre.toUpperCase());
				app.getGrupoDAO().update(grupoPK, grupo);
			}
			if (tipoObjeto.equals("tipoTaquilla")) {
				tipoTaquilla.setNombre(nombre.toUpperCase());
				app.getTipoTaquillaDAO().update(tipoTaquillaPK, tipoTaquilla);
			}
			lvObjetos.refresh();
			tfModificarObjeto.setText("");
			
		} catch (Exception e) {
			Dialogos.mostrarDialogoExcepcion(e, escenarioVentana);
		}
	}

	private void eliminarObjeto() {
		try {
			if (tipoObjeto.equals("familia")) {
				app.getFamiliaDAO().delete(familiaPK);
			}
			if (tipoObjeto.equals("ubicacion")) {
				app.getUbicacionDAO().delete(ubicacionPK);
			}
			if (tipoObjeto.equals("tipomaterial")) {
				app.getTipoMaterialDAO().delete(tipoMaterialPK);
			}
			if (tipoObjeto.equals("grupo")) {
				app.getGrupoDAO().delete(grupoPK);
			}
			if (tipoObjeto.equals("tipotaquilla")) {
				app.getTipoTaquillaDAO().delete(tipoTaquillaPK);
			}
		} catch (Exception e) {
			Dialogos.mostrarDialogoExcepcion(e, escenarioVentana);
		}
	}
}
