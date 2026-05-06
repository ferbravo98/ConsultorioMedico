package consultorio;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.util.List;

public class EstudiosController {

    @FXML private Label lblPaciente;
    @FXML private TableView<Estudio> tablaEstudios;
    @FXML private TableColumn<Estudio, String> colFecha;
    @FXML private TableColumn<Estudio, String> colTipo;
    @FXML private TableColumn<Estudio, String> colNombre;
    @FXML private TableColumn<Estudio, String> colEstado;
    @FXML private TableColumn<Estudio, String> colIndicacion;

    private Paciente paciente;
    private final ObservableList<Estudio> lista = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        colFecha.setCellValueFactory(e -> new SimpleStringProperty(valor(e.getValue().getFecha())));
        colTipo.setCellValueFactory(e -> new SimpleStringProperty(valor(e.getValue().getTipo())));
        colNombre.setCellValueFactory(e -> new SimpleStringProperty(valor(e.getValue().getNombre())));
        colEstado.setCellValueFactory(e -> new SimpleStringProperty(valor(e.getValue().getEstado())));
        colIndicacion.setCellValueFactory(e -> new SimpleStringProperty(valor(e.getValue().getIndicacion())));

        tablaEstudios.setItems(lista);

        tablaEstudios.setRowFactory(tv -> {
            TableRow<Estudio> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && !row.isEmpty()) {
                    abrirFormulario(row.getItem());
                }
            });
            return row;
        });
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
        lblPaciente.setText("Estudios de: " + paciente.getNombreCompleto());
        cargarEstudios();
    }

    private void cargarEstudios() {
        EstudioDAO dao = new EstudioDAO();
        List<Estudio> estudios = dao.listarPorPaciente(paciente.getId());

        lista.clear();
        lista.addAll(estudios);
    }

    @FXML
    private void onNuevoEstudio() {
        abrirFormulario(null);
    }

    @FXML
    private void onEditarEstudio() {
        Estudio estudio = tablaEstudios.getSelectionModel().getSelectedItem();

        if (estudio == null) {
            mostrarAlerta("Seleccioná un estudio.");
            return;
        }

        abrirFormulario(estudio);
    }

    private void abrirFormulario(Estudio estudio) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/EstudioFormView.fxml"));
            Scene scene = new Scene(loader.load(), 900, 600);

            EstudioFormController controller = loader.getController();
            controller.setPaciente(paciente);

            if (estudio != null) {
                controller.setEstudioEditar(estudio);
            }

            Stage stage = (Stage) tablaEstudios.getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle(estudio == null ? "Nuevo Estudio" : "Editar Estudio");
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void onVolver() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/DetallePacienteView.fxml"));
            Scene scene = new Scene(loader.load(), 900, 600);

            DetallePacienteController controller = loader.getController();
            controller.setPaciente(paciente);

            Stage stage = (Stage) tablaEstudios.getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Detalle del Paciente");
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String valor(String v) {
        return v != null ? v : "";
    }

    private void mostrarAlerta(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Atención");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}