package consultorio;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class NuevaConsultaController {

    @FXML
    private Label lblPaciente;
    @FXML
    private DatePicker dpFechaConsulta;
    @FXML
    private TextArea txtMotivo;
    @FXML
    private TextArea txtDiagnostico;
    @FXML
    private TextArea txtTratamiento;
    @FXML
    private TextArea txtObservaciones;
    @FXML
    private DatePicker dpProximoControl;

    private Paciente paciente;
    private Consulta consultaEditar = null;

    private final DateTimeFormatter formatoBD = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private String safeText(TextInputControl t) {
        return t.getText() != null ? t.getText().trim() : "";
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
        lblPaciente.setText("Paciente: " + paciente.getNombreCompleto() + " - DNI: " + paciente.getDni());

        if (consultaEditar == null) {
            dpFechaConsulta.setValue(LocalDate.now());
        }
    }

    public void setConsultaEditar(Consulta consulta) {
        this.consultaEditar = consulta;

        dpFechaConsulta.setValue(LocalDate.parse(consulta.getFechaConsulta()));
        txtMotivo.setText(consulta.getMotivo());
        txtDiagnostico.setText(consulta.getDiagnostico());
        txtTratamiento.setText(consulta.getTratamiento());
        txtObservaciones.setText(consulta.getObservaciones());

        if (consulta.getProximoControl() != null && !consulta.getProximoControl().isBlank()) {
            dpProximoControl.setValue(LocalDate.parse(consulta.getProximoControl()));
        }
    }

    @FXML
    private void onGuardar() {
        System.out.println("Entró a onGuardar");

        if (paciente == null) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error", "No hay paciente seleccionado.");
            return;
        }

        if (dpFechaConsulta.getValue() == null) {
            mostrarAlerta(Alert.AlertType.WARNING, "Campo obligatorio", "La fecha de consulta es obligatoria.");
            return;
        }

        Consulta consulta = new Consulta();
        consulta.setPacienteId(paciente.getId());
        consulta.setFechaConsulta(dpFechaConsulta.getValue().format(formatoBD));
        consulta.setMotivo(safeText(txtMotivo));
        consulta.setDiagnostico(safeText(txtDiagnostico));
        consulta.setTratamiento(safeText(txtTratamiento));
        consulta.setObservaciones(safeText(txtObservaciones));

        if (dpProximoControl.getValue() != null) {
            consulta.setProximoControl(dpProximoControl.getValue().format(formatoBD));
        } else {
            consulta.setProximoControl(null);
        }

        ConsultaDAO dao = new ConsultaDAO();
        boolean ok;

        if (consultaEditar == null) {
            ok = dao.guardar(consulta);
        } else {
            consulta.setId(consultaEditar.getId());
            ok = dao.actualizar(consulta);
        }
        if (ok) {
            mostrarAlerta(Alert.AlertType.INFORMATION, "Éxito", "Consulta guardada correctamente.");
            volverAHistoria();
        } else {
            mostrarAlerta(Alert.AlertType.ERROR, "Error", "No se pudo guardar la consulta.");
        }
    }

    @FXML
    private void onVolver() {
        volverAHistoria();
    }

    private void volverAHistoria() {
    try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/HistoriaClinicaView.fxml"));
        Scene scene = new Scene(loader.load(), 900, 600);

        HistoriaClinicaController controller = loader.getController();
        controller.setPaciente(paciente);

        Stage stage = (Stage) lblPaciente.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Historia Clínica");
        stage.show();

    } catch (Exception e) {
        e.printStackTrace();
        mostrarAlerta(Alert.AlertType.ERROR, "Error", "No se pudo volver a la historia clínica.");
    }
}

    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String mensaje) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}