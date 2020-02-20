package es.rfgr.cr4vegas.vista.fxml.controlador.principal.modulo;

import javax.naming.OperationNotSupportedException;

import es.rfgr.cr4vegas.controlador.Consumer;
import es.rfgr.cr4vegas.modelo.postgresql.dto.parte.Parte;
import es.rfgr.cr4vegas.vista.utileria.Dialogos;
import javafx.fxml.FXML;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

public class ModuloParteControlador {

    @FXML
    private Hyperlink hlAgregarParte;

    @FXML
    private Hyperlink hlModificarParte;

    @FXML
    private RadioButton rbListado;

    @FXML
    private AnchorPane panelRaiz;

    @FXML
    private StackPane paneInventarioPrincipal;

    @FXML
    private Hyperlink hlEliminarParte;

    @FXML
    private Hyperlink hlMaterialRetirado;

    @FXML
    private Hyperlink hlOperariosAsociados;

    @FXML
    private RadioButton rbInformacion;

    private Consumer app;
    private ToggleGroup grupoRB;

    public void setConsumer(Consumer app) {
        this.app = app;
    }

    public AnchorPane getPanelRaiz() {
        return panelRaiz;
    }

    public void iniciar() {
        grupoRB = new ToggleGroup();
        rbInformacion.setToggleGroup(grupoRB);
        rbListado.setToggleGroup(grupoRB);
        grupoRB.selectedToggleProperty().addListener(e -> obtenerRadioBotonSeleccionado());
        rbListado.setSelected(true);
        obtenerRadioBotonSeleccionado();

        hlAgregarParte.setOnAction(e -> agregarParte());
        hlModificarParte.setOnAction(e -> modificarParte());
        hlEliminarParte.setOnAction(e -> eliminarParte());
        hlMaterialRetirado.setOnAction(e -> materialRetirado());
        hlOperariosAsociados.setOnAction(e -> operariosAsociados());
    }

    private void obtenerRadioBotonSeleccionado() {
        RadioButton rbSeleccionado = (RadioButton) grupoRB.getSelectedToggle();
        paneInventarioPrincipal.getChildren().clear();
        if (rbSeleccionado.equals(rbInformacion)) {
            paneInventarioPrincipal.getChildren()
                    .add(app.getVistaModelo().getModeloPrincipal().getParteInformacionControlador().getPanelRaiz());
        } else {
            // ...
        }
        if (rbSeleccionado.equals(rbListado)) {
            paneInventarioPrincipal.getChildren()
                    .add(app.getVistaModelo().getModeloPrincipal().getPartePrincipalControlador().getPanelRaiz());
            hlAgregarParte.setDisable(false);
            hlModificarParte.setDisable(false);
            hlEliminarParte.setDisable(false);
            hlMaterialRetirado.setDisable(false);
            hlOperariosAsociados.setDisable(false);
        } else {
            hlAgregarParte.setDisable(true);
            hlModificarParte.setDisable(true);
            hlEliminarParte.setDisable(true);
            hlMaterialRetirado.setDisable(true);
            hlOperariosAsociados.setDisable(true);
        }
    }

    private void agregarParte() {
        app.getVistaModelo().getModeloVentana().getVentanaManipulaParteControlador().iniciarCon(null);
    }

    private void modificarParte() {
        try {
            Parte parte = app.getVistaModelo().getModeloPrincipal().getPartePrincipalControlador()
                    .getParteSeleccionado();
            if (parte != null) {
                app.getVistaModelo().getModeloVentana().getVentanaManipulaParteControlador().iniciarCon(parte);

            } else {
                throw new OperationNotSupportedException("Debe seleccionar el parte ha modificar.");
            }
        } catch (Exception e) {
            Dialogos.mostrarDialogoExcepcion(e, app.getVistaModelo().getEscenarioPrincipal());
        }
    }

    private void eliminarParte() {
        try {
            Parte parte = app.getVistaModelo().getModeloPrincipal().getPartePrincipalControlador()
                    .getParteSeleccionado();
            if (parte != null) {
                if (parte.getObjParteOficial() != null) {
                    if (Dialogos.mostrarDialogoConfirmacion("Teniendo en cuenta que este parte con Nº " + parte.getCodParte() + " se encuentra en la BD de la "
                            + "Oficina con Nº " + parte.getObjParteOficial().getCodParte() + ", ¿Está seguro de eliminarlo?",
                            app.getVistaModelo().getEscenarioPrincipal())) {

                        app.getParteOficialDAO().delete(parte.getObjParteOficial().createPK());

                        app.getParteDAO().delete(parte.createPK());
                    }
                } else {
                    if (Dialogos.mostrarDialogoConfirmacion("¿Seguro desea eliminar el parte de Almacen con Nº " + parte.getCodParte() + "?",
                            app.getVistaModelo().getEscenarioPrincipal())) {

                        app.getParteDAO().delete(parte.createPK());
                    }
                }

            } else {
                throw new OperationNotSupportedException("Debe seleccionar el parte ha eliminar.");
            }
        } catch (Exception e) {
            Dialogos.mostrarDialogoExcepcion(e, app.getVistaModelo().getEscenarioPrincipal());
        }
    }

    private void materialRetirado() {

        Parte parte = app.getVistaModelo().getModeloPrincipal().getPartePrincipalControlador().getParteSeleccionado();
        if (parte != null) {
            app.getVistaModelo().getModeloVentana().getVentanaOperaMaterialControlador().iniciarCon(parte);

        } else {
            app.getVistaModelo().getModeloVentana().getVentanaOperaMaterialControlador().iniciarCon(null);
        }
    }

    private void operariosAsociados() {

        Parte parte = app.getVistaModelo().getModeloPrincipal().getPartePrincipalControlador().getParteSeleccionado();
        if (parte != null) {
            app.getVistaModelo().getModeloVentana().getVentanaAsociaParteOperarioControlador().iniciarCon(parte);

        } else {
            Dialogos.mostrarDialogoAdvertencia("Debe seleccionar un parte.", app.getVistaModelo().getEscenarioPrincipal());
        }
    }

}
