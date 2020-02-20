package es.rfgr.cr4vegas.vista.fxml.controlador.principal.modulo;

import es.rfgr.cr4vegas.controlador.Consumer;
import es.rfgr.cr4vegas.modelo.postgresql.dto.operario.Operario;
import es.rfgr.cr4vegas.vista.utileria.Dialogos;
import javafx.fxml.FXML;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

public class ModuloOperarioControlador {

    @FXML
    private AnchorPane panelRaiz;

    @FXML
    private RadioButton rbInformacion;

    @FXML
    private RadioButton rbListado;

    @FXML
    private Hyperlink hlAgregarOperario;

    @FXML
    private Hyperlink hlModificarOperario;

    @FXML
    private Hyperlink hlEliminarOperario;

    @FXML
    private Hyperlink hlRetirarMaterial;

    @FXML
    private Hyperlink hlEliminarMaterialRetirado;

    @FXML
    private StackPane paneInventarioPrincipal;

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

        hlAgregarOperario
                .setOnAction(e -> app.getVistaModelo().getModeloVentana().getVentanaManipulaOperarioControlador().iniciarCon(null));
        hlModificarOperario.setOnAction(e -> handleModificarOperario());
        hlEliminarOperario.setOnAction(e -> handleEliminarOperario());

        hlRetirarMaterial.setOnAction(e -> retirarMaterial());
        obtenerRadioBotonSeleccionado();
    }

    public void obtenerRadioBotonSeleccionado() {
        RadioButton rbSeleccionado = (RadioButton) grupoRB.getSelectedToggle();
        paneInventarioPrincipal.getChildren().clear();
        if (rbSeleccionado.equals(rbInformacion)) {
            paneInventarioPrincipal.getChildren()
                    .add(app.getVistaModelo().getModeloPrincipal().getOperarioInformacionControlador().getPanelRaiz());
        } else {
            // ...
        }
        if (rbSeleccionado.equals(rbListado)) {
            paneInventarioPrincipal.getChildren()
                    .add(app.getVistaModelo().getModeloPrincipal().getOperarioPrincipalControlador().getPanelRaiz());
            hlAgregarOperario.setDisable(false);
            hlModificarOperario.setDisable(false);
            hlEliminarOperario.setDisable(false);
            hlRetirarMaterial.setDisable(false);
        } else {
            hlAgregarOperario.setDisable(true);
            hlModificarOperario.setDisable(true);
            hlEliminarOperario.setDisable(true);
            hlRetirarMaterial.setDisable(true);
        }
    }

    private void handleEliminarOperario() {
        try {
            Operario operario = app.getVistaModelo().getModeloPrincipal().getOperarioPrincipalControlador().getSelectedOperario();
            if (operario != null) {
                if (Dialogos.mostrarDialogoConfirmacion("¿Está seguro de eliminar el operario?",
                        app.getVistaModelo().getEscenarioPrincipal())) {
                    app.getOperarioDAO().delete(operario.createPK());
                }
            } else {
                Dialogos.mostrarDialogoAdvertencia("Debe seleccionar un operario",
                        app.getVistaModelo().getEscenarioPrincipal());
            }
        } catch (Exception e) {
            e.printStackTrace();
            Dialogos.mostrarDialogoError(e.getMessage(), app.getVistaModelo().getEscenarioPrincipal());
        }
    }

    private void handleModificarOperario() {
        Operario operario = app.getVistaModelo().getModeloPrincipal().getOperarioPrincipalControlador().getSelectedOperario();
        if (operario != null) {
            app.getVistaModelo().getModeloVentana().getVentanaManipulaOperarioControlador().iniciarCon(operario);
        } else {
            Dialogos.mostrarDialogoAdvertencia("Debe seleccionar un operario",
                    app.getVistaModelo().getEscenarioPrincipal());
        }
    }

    private void retirarMaterial() {
        Operario operario = app.getVistaModelo().getModeloPrincipal().getOperarioPrincipalControlador().getSelectedOperario();
        if (operario != null) {
            app.getVistaModelo().getModeloVentana().getVentanaOperaMaterialControlador().iniciarCon(operario);
        } else {
            Dialogos.mostrarDialogoAdvertencia("Debe seleccionar un operario",
                    app.getVistaModelo().getEscenarioPrincipal());
        }
    }
}
