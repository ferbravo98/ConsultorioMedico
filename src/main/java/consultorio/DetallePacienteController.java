package consultorio;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class DetallePacienteController {

    @FXML private Label lblDni;
    @FXML private Label lblApellido;
    @FXML private Label lblNombre;
    @FXML private Label lblFechaNacimiento;
    @FXML private Label lblSexo;
    @FXML private Label lblTelefono;
    @FXML private Label lblDomicilio;
    @FXML private Label lblObraSocial;
    @FXML private Label lblNumeroAfiliado;
    @FXML private Label lblObservaciones;

    private Paciente paciente;

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
        cargarDatos();
    }

    private void cargarDatos() {
        if (paciente == null) return;

        lblDni.setText(valorSeguro(paciente.getDni()));
        lblApellido.setText(valorSeguro(paciente.getApellido()));
        lblNombre.setText(valorSeguro(paciente.getNombre()));
        lblFechaNacimiento.setText(valorSeguro(paciente.getFechaNacimiento()));
        lblSexo.setText(valorSeguro(paciente.getSexo()));
        lblTelefono.setText(valorSeguro(paciente.getTelefono()));
        lblDomicilio.setText(valorSeguro(paciente.getDomicilio()));
        lblObraSocial.setText(valorSeguro(paciente.getObraSocial()));
        lblNumeroAfiliado.setText(valorSeguro(paciente.getNumeroAfiliado()));
        lblObservaciones.setText(valorSeguro(paciente.getObservaciones()));
    }

    @FXML
    private void onVolver() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/PacientesView.fxml"));
            Scene scene = new Scene(loader.load(), 900, 600);

            Stage stage = (Stage) lblDni.getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Listado de Pacientes");
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @FXML
private void onNuevaConsulta() {
    try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/NuevaConsultaView.fxml"));
        Scene scene = new Scene(loader.load(), 900, 600);

        NuevaConsultaController controller = loader.getController();
        controller.setPaciente(paciente);

        Stage stage = (Stage) lblDni.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Nueva Consulta");
        stage.show();

    } catch (Exception e) {
        e.printStackTrace();
    }
}

    private String valorSeguro(String valor) {
        return valor != null ? valor : "";
    }
    @FXML
private void onHistoriaClinica() {
    try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/HistoriaClinicaView.fxml"));
        Scene scene = new Scene(loader.load(), 900, 600);

        HistoriaClinicaController controller = loader.getController();
        controller.setPaciente(paciente);

        Stage stage = (Stage) lblDni.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Historia Clínica");
        stage.show();

    } catch (Exception e) {
        e.printStackTrace();
    }
}
@FXML
private void onEstudios() {
    try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/EstudiosView.fxml"));
        Scene scene = new Scene(loader.load(), 900, 600);

        EstudiosController controller = loader.getController();
        controller.setPaciente(paciente);

        Stage stage = (Stage) lblDni.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Estudios");

    } catch (Exception e) {
        e.printStackTrace();
    }
}
@FXML
private void onVacunacion() {
    try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/VacunacionView.fxml"));
        Scene scene = new Scene(loader.load(), 1000, 650);

        VacunacionController controller = loader.getController();
        controller.setPaciente(paciente);

        Stage stage = (Stage) lblDni.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Vacunación");

    } catch (Exception e) {
        e.printStackTrace();
    }
}
}