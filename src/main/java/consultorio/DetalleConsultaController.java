package consultorio;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class DetalleConsultaController {

    @FXML private Label lblPaciente;
    @FXML private Label lblFecha;
    @FXML private Label lblMotivo;
    @FXML private Label lblDiagnostico;
    @FXML private Label lblTratamiento;
    @FXML private Label lblObservaciones;
    @FXML private Label lblProximoControl;

    private Consulta consulta;
    private Paciente paciente;

    public void setData(Paciente paciente, Consulta consulta) {
        this.paciente = paciente;
        this.consulta = consulta;
        cargarDatos();
    }

    private void cargarDatos() {
        lblPaciente.setText("Paciente: " + paciente.getNombreCompleto());

        lblFecha.setText(valor(consulta.getFechaConsulta()));
        lblMotivo.setText(valor(consulta.getMotivo()));
        lblDiagnostico.setText(valor(consulta.getDiagnostico()));
        lblTratamiento.setText(valor(consulta.getTratamiento()));
        lblObservaciones.setText(valor(consulta.getObservaciones()));
        lblProximoControl.setText(valor(consulta.getProximoControl()));
    }

    @FXML
private void onEditar() {
    try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/NuevaConsultaView.fxml"));
        Scene scene = new Scene(loader.load(), 900, 600);

        NuevaConsultaController controller = loader.getController();
        controller.setPaciente(paciente);
        controller.setConsultaEditar(consulta);

        Stage stage = (Stage) lblFecha.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Editar Consulta");
        stage.show();

    } catch (Exception e) {
        e.printStackTrace();
    }
}

    @FXML
    private void onVolver() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/HistoriaClinicaView.fxml"));
            Scene scene = new Scene(loader.load(), 900, 600);

            HistoriaClinicaController controller = loader.getController();
            controller.setPaciente(paciente);

            Stage stage = (Stage) lblFecha.getScene().getWindow();
            stage.setScene(scene);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String valor(String v) {
        return v != null ? v : "";
    }
}