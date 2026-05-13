package consultorio;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.scene.layout.VBox;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;

public class VacunacionController {

    @FXML private Label lblPaciente;
    @FXML private TableView<PacienteVacuna> tablaVacunas;
    @FXML private TableColumn<PacienteVacuna, String> colEdad;
    @FXML private TableColumn<PacienteVacuna, String> colVacuna;
    @FXML private TableColumn<PacienteVacuna, String> colDosis;
    @FXML private TableColumn<PacienteVacuna, String> colAplicada;
    @FXML private TableColumn<PacienteVacuna, String> colFecha;
    @FXML private TableColumn<PacienteVacuna, String> colEstado;
    @FXML private TableColumn<PacienteVacuna, String> colObservaciones;

    private Paciente paciente;
    private final ObservableList<PacienteVacuna> lista = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        colEdad.setCellValueFactory(v -> new SimpleStringProperty(valor(v.getValue().getEdadTexto())));
        colVacuna.setCellValueFactory(v -> new SimpleStringProperty(valor(v.getValue().getVacuna())));
        colDosis.setCellValueFactory(v -> new SimpleStringProperty(valor(v.getValue().getDosis())));
        colAplicada.setCellValueFactory(v -> new SimpleStringProperty(v.getValue().isAplicada() ? "Sí" : "No"));
        colAplicada.setCellFactory(column -> new TableCell<>() {
    @Override
    protected void updateItem(String valor, boolean empty) {
        super.updateItem(valor, empty);

        if (empty || valor == null) {
            setText(null);
            setStyle("");
            return;
        }

        setText(valor);
        setStyle("-fx-font-weight: bold; -fx-alignment: CENTER;");

        if (valor.equals("Sí")) {
            setStyle("-fx-background-color: #C8E6C9; -fx-text-fill: #1B5E20; -fx-font-weight: bold; -fx-alignment: CENTER;");
        } else {
            setStyle("-fx-background-color: #FFCDD2; -fx-text-fill: #B71C1C; -fx-font-weight: bold; -fx-alignment: CENTER;");
        }
    }
});
        colFecha.setCellValueFactory(v -> new SimpleStringProperty(valor(v.getValue().getFechaAplicacion())));
        colEstado.setCellValueFactory(v -> new SimpleStringProperty(calcularEstado(v.getValue())));
        colEstado.setCellFactory(column -> new TableCell<>() {
    @Override
    protected void updateItem(String estado, boolean empty) {
        super.updateItem(estado, empty);

        if (empty || estado == null) {
            setText(null);
            setStyle("");
            return;
        }

        setText(estado);
        setStyle("-fx-font-weight: bold; -fx-alignment: CENTER;");

        switch (estado) {
            case "Completa" -> setStyle("-fx-background-color: #C8E6C9; -fx-text-fill: #1B5E20; -fx-font-weight: bold; -fx-alignment: CENTER;");
            case "Próxima" -> setStyle("-fx-background-color: #FFF9C4; -fx-text-fill: #795548; -fx-font-weight: bold; -fx-alignment: CENTER;");
            case "Pendiente" -> setStyle("-fx-background-color: #FFCDD2; -fx-text-fill: #B71C1C; -fx-font-weight: bold; -fx-alignment: CENTER;");
            case "No corresponde" -> setStyle("-fx-background-color: #E0E0E0; -fx-text-fill: #424242; -fx-font-weight: bold; -fx-alignment: CENTER;");
            default -> setStyle("-fx-font-weight: bold; -fx-alignment: CENTER;");
        }
    }
});
        colObservaciones.setCellValueFactory(v -> new SimpleStringProperty(valor(v.getValue().getObservaciones())));

        tablaVacunas.setItems(lista);

        tablaVacunas.setRowFactory(tv -> {
            TableRow<PacienteVacuna> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && !row.isEmpty()) {
                    editarVacuna(row.getItem());
                }
            });
            return row;
        });
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
        lblPaciente.setText("Calendario de vacunación: " + paciente.getNombreCompleto());
        cargarVacunas();
    }

    private void cargarVacunas() {
        VacunacionDAO dao = new VacunacionDAO();
        List<PacienteVacuna> vacunas = dao.listarPorPaciente(paciente.getId());

        lista.clear();
        lista.addAll(vacunas);
    }

    @FXML
    private void onEditarVacuna() {
        PacienteVacuna vacuna = tablaVacunas.getSelectionModel().getSelectedItem();

        if (vacuna == null) {
            mostrarAlerta(Alert.AlertType.WARNING, "Atención", "Seleccioná una vacuna.");
            return;
        }

        editarVacuna(vacuna);
    }

    private void editarVacuna(PacienteVacuna vacuna) {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Vacunación");
        dialog.setHeaderText(vacuna.getVacuna() + " - " + vacuna.getDosis());

        CheckBox chkAplicada = new CheckBox("Aplicada");
        chkAplicada.setSelected(vacuna.isAplicada());

        DatePicker dpFecha = new DatePicker();
        if (vacuna.getFechaAplicacion() != null && !vacuna.getFechaAplicacion().isBlank()) {
            dpFecha.setValue(LocalDate.parse(vacuna.getFechaAplicacion()));
        }

        TextArea txtObs = new TextArea();
        txtObs.setPrefRowCount(3);
        txtObs.setText(valor(vacuna.getObservaciones()));

        VBox contenido = new VBox(10,
                chkAplicada,
                new Label("Fecha de aplicación:"),
                dpFecha,
                new Label("Observaciones:"),
                txtObs
        );

        dialog.getDialogPane().setContent(contenido);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        dialog.showAndWait().ifPresent(respuesta -> {
            if (respuesta == ButtonType.OK) {
                vacuna.setAplicada(chkAplicada.isSelected());

                if (dpFecha.getValue() != null) {
                    vacuna.setFechaAplicacion(dpFecha.getValue().toString());
                } else {
                    vacuna.setFechaAplicacion(null);
                }

                vacuna.setObservaciones(txtObs.getText() != null ? txtObs.getText().trim() : "");

                VacunacionDAO dao = new VacunacionDAO();
                boolean ok = dao.guardarEstado(vacuna);

                if (ok) {
                    cargarVacunas();
                } else {
                    mostrarAlerta(Alert.AlertType.ERROR, "Error", "No se pudo guardar la vacuna.");
                }
            }
        });
    }

    @FXML
    private void onVolver() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/DetallePacienteView.fxml"));
            Scene scene = new Scene(loader.load(), 900, 600);

            DetallePacienteController controller = loader.getController();
            controller.setPaciente(paciente);

            Stage stage = (Stage) tablaVacunas.getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Detalle del Paciente");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String calcularEstado(PacienteVacuna vacuna) {
        if (vacuna.isAplicada()) {
            return "Completa";
        }

        if (paciente == null || paciente.getFechaNacimiento() == null || paciente.getFechaNacimiento().isBlank()) {
            return "Pendiente";
        }

        try {
            LocalDate nacimiento = LocalDate.parse(paciente.getFechaNacimiento());
            int edadMesesActual = Period.between(nacimiento, LocalDate.now()).getYears() * 12
                    + Period.between(nacimiento, LocalDate.now()).getMonths();

            if (edadMesesActual >= vacuna.getEdadMeses()) {
                return "Pendiente";
            }

            if (vacuna.getEdadMeses() - edadMesesActual <= 1) {
                return "Próxima";
            }

            return "No corresponde";

        } catch (Exception e) {
            return "Pendiente";
        }
    }

    private String valor(String v) {
        return v != null ? v : "";
    }

    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String mensaje) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}