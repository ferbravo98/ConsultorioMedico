package consultorio;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class EstudioFormController {

    @FXML private Label lblTitulo;
    @FXML private Label lblPaciente;
    @FXML private DatePicker dpFecha;
    @FXML private ComboBox<String> cbTipo;
    @FXML private TextField txtNombre;
    @FXML private ComboBox<String> cbEstado;
    @FXML private TextArea txtIndicacion;
    @FXML private TextArea txtResultado;
    @FXML private TextField txtRutaArchivo;

    private Paciente paciente;
    private Estudio estudioEditar = null;

    private final DateTimeFormatter formatoBD = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @FXML
    public void initialize() {
        cbTipo.setItems(FXCollections.observableArrayList(
                "Laboratorio",
                "Diagnóstico por imágenes",
                "Otro"
        ));

        cbEstado.setItems(FXCollections.observableArrayList(
                "Solicitado",
                "Realizado",
                "Revisado"
        ));

        dpFecha.setValue(LocalDate.now());
        cbEstado.setValue("Solicitado");
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
        lblPaciente.setText("Paciente: " + paciente.getNombreCompleto());
    }

    public void setEstudioEditar(Estudio estudio) {
        this.estudioEditar = estudio;
        lblTitulo.setText("Editar Estudio");

        dpFecha.setValue(LocalDate.parse(estudio.getFecha()));
        cbTipo.setValue(estudio.getTipo());
        txtNombre.setText(valor(estudio.getNombre()));
        cbEstado.setValue(estudio.getEstado());
        txtIndicacion.setText(valor(estudio.getIndicacion()));
        txtResultado.setText(valor(estudio.getResultado()));
        txtRutaArchivo.setText(valor(estudio.getRutaArchivo()));
    }

    @FXML
    private void onGuardar() {
        if (paciente == null) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error", "No hay paciente seleccionado.");
            return;
        }

        if (dpFecha.getValue() == null || cbTipo.getValue() == null || txtNombre.getText().isBlank()) {
            mostrarAlerta(Alert.AlertType.WARNING, "Campos obligatorios", "Fecha, tipo y nombre del estudio son obligatorios.");
            return;
        }

        Estudio estudio = new Estudio();
        estudio.setPacienteId(paciente.getId());
        estudio.setFecha(dpFecha.getValue().format(formatoBD));
        estudio.setTipo(cbTipo.getValue());
        estudio.setNombre(txtNombre.getText().trim());
        estudio.setEstado(cbEstado.getValue());
        estudio.setIndicacion(safeText(txtIndicacion));
        estudio.setResultado(safeText(txtResultado));
        estudio.setRutaArchivo(txtRutaArchivo.getText() != null ? txtRutaArchivo.getText().trim() : "");

        EstudioDAO dao = new EstudioDAO();
        boolean ok;

        if (estudioEditar == null) {
            ok = dao.guardar(estudio);
        } else {
            estudio.setId(estudioEditar.getId());
            ok = dao.actualizar(estudio);
        }

        if (ok) {
            mostrarAlerta(Alert.AlertType.INFORMATION, "Éxito", "Estudio guardado correctamente.");
            volverAEstudios();
        } else {
            mostrarAlerta(Alert.AlertType.ERROR, "Error", "No se pudo guardar el estudio.");
        }
    }

    @FXML
    private void onVolver() {
        volverAEstudios();
    }

    private void volverAEstudios() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/EstudiosView.fxml"));
            Scene scene = new Scene(loader.load(), 900, 600);

            EstudiosController controller = loader.getController();
            controller.setPaciente(paciente);

            Stage stage = (Stage) lblPaciente.getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Estudios");
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String safeText(TextInputControl t) {
        return t.getText() != null ? t.getText().trim() : "";
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