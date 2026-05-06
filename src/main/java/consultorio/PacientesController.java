package consultorio;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.List;

public class PacientesController {

    @FXML
    private TableView<Paciente> tablaPacientes;

    @FXML
    private TableColumn<Paciente, Number> colId;

    @FXML
    private TableColumn<Paciente, String> colDni;

    @FXML
    private TableColumn<Paciente, String> colApellido;

    @FXML
    private TableColumn<Paciente, String> colNombre;

    @FXML
    private TableColumn<Paciente, String> colTelefono;

    @FXML
    private TableColumn<Paciente, String> colObraSocial;

    @FXML
    private TextField txtBuscar;

    private final ObservableList<Paciente> listaPacientes = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        colId.setCellValueFactory(data ->
                new SimpleIntegerProperty(data.getValue().getId()));

        colDni.setCellValueFactory(data ->
                new SimpleStringProperty(valorSeguro(data.getValue().getDni())));

        colApellido.setCellValueFactory(data ->
                new SimpleStringProperty(valorSeguro(data.getValue().getApellido())));

        colNombre.setCellValueFactory(data ->
                new SimpleStringProperty(valorSeguro(data.getValue().getNombre())));

        colTelefono.setCellValueFactory(data ->
                new SimpleStringProperty(valorSeguro(data.getValue().getTelefono())));

        colObraSocial.setCellValueFactory(data ->
                new SimpleStringProperty(valorSeguro(data.getValue().getObraSocial())));

        tablaPacientes.setItems(listaPacientes);
        tablaPacientes.setRowFactory(tv -> {
    javafx.scene.control.TableRow<Paciente> row = new javafx.scene.control.TableRow<>();

    row.setOnMouseClicked(event -> {
        if (event.getClickCount() == 2 && !row.isEmpty()) {
            Paciente pacienteSeleccionado = row.getItem();
            abrirDetallePaciente(pacienteSeleccionado);
        }
    });

    return row;
});
        cargarTodos();
        txtBuscar.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == null || newValue.isBlank()) {
                cargarTodos();
            } else {
                buscarEnTiempoReal(newValue);
            }
        });
    }

    private void abrirDetallePaciente(Paciente pacienteSeleccionado) {
    if (pacienteSeleccionado == null) {
        return;
    }

    try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/DetallePacienteView.fxml"));
        Scene scene = new Scene(loader.load(), 900, 600);

        DetallePacienteController controller = loader.getController();
        controller.setPaciente(pacienteSeleccionado);

        Stage stage = (Stage) tablaPacientes.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Detalle del Paciente");
        stage.show();

    } catch (Exception e) {
        e.printStackTrace();
    }
}

    @FXML
    private void onBuscar() {
        String texto = txtBuscar.getText();

        if (texto == null || texto.isBlank()) {
            cargarTodos();
            return;
        }

        buscarEnTiempoReal(texto);
    }

    @FXML
    private void onMostrarTodos() {
        txtBuscar.clear();
        cargarTodos();
    }

    @FXML
    private void onNuevoPaciente() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/NuevoPacienteView.fxml"));
            Scene scene = new Scene(loader.load(), 900, 600);

            Stage stage = (Stage) tablaPacientes.getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Nuevo Paciente");
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void onVerDetalle() {
    Paciente pacienteSeleccionado = tablaPacientes.getSelectionModel().getSelectedItem();
    if (pacienteSeleccionado == null) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Atención");
        alert.setHeaderText(null);
        alert.setContentText("Seleccioná un paciente de la tabla.");
        alert.showAndWait();
        return;
    }

    abrirDetallePaciente(pacienteSeleccionado);
}

    private void cargarTodos() {
        PacienteDAO dao = new PacienteDAO();
        List<Paciente> pacientes = dao.listarTodos();

        listaPacientes.clear();
        listaPacientes.addAll(pacientes);
    }

    private void buscarEnTiempoReal(String texto) {
        PacienteDAO dao = new PacienteDAO();
        List<Paciente> pacientes = dao.buscar(texto);

        listaPacientes.clear();
        listaPacientes.addAll(pacientes);
    }

    private String valorSeguro(String valor) {
        return valor != null ? valor : "";
    }

  @FXML
private void onEditarPaciente() {
    Paciente pacienteSeleccionado = tablaPacientes.getSelectionModel().getSelectedItem();

    if (pacienteSeleccionado == null) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Atención");
        alert.setHeaderText(null);
        alert.setContentText("Seleccioná un paciente de la tabla.");
        alert.showAndWait();
        return;
    }

    try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/NuevoPacienteView.fxml"));
        Scene scene = new Scene(loader.load(), 900, 600);

        NuevoPacienteController controller = loader.getController();
        controller.setPacienteEditar(pacienteSeleccionado);

        Stage stage = (Stage) tablaPacientes.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Editar Paciente");
        stage.show();

    } catch (Exception e) {
        e.printStackTrace();
    }
}  
}