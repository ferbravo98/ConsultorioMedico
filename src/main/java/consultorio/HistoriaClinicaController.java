package consultorio;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.TableRow;
import javafx.stage.Stage;

import java.util.List;

public class HistoriaClinicaController {

    @FXML private Label lblPaciente;
    @FXML private TableView<Consulta> tablaConsultas;
    @FXML private TableColumn<Consulta, String> colFecha;
    @FXML private TableColumn<Consulta, String> colMotivo;
    @FXML private TableColumn<Consulta, String> colDiagnostico;
    @FXML private TableColumn<Consulta, String> colTratamiento;

    private final ObservableList<Consulta> lista = FXCollections.observableArrayList();
    private Paciente paciente;

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
        lblPaciente.setText("Historia Clínica: " + paciente.getNombreCompleto());
        cargarDatos();
    }

    private void cargarDatos() {
        ConsultaDAO dao = new ConsultaDAO();
        List<Consulta> consultas = dao.listarPorPaciente(paciente.getId());

        lista.clear();
        lista.addAll(consultas);
        tablaConsultas.setItems(lista);
    }

    @FXML
    public void initialize() {
        colFecha.setCellValueFactory(c ->
                new SimpleStringProperty(c.getValue().getFechaConsulta()));

        colMotivo.setCellValueFactory(c ->
                new SimpleStringProperty(valorSeguro(c.getValue().getMotivo())));

        colDiagnostico.setCellValueFactory(c ->
                new SimpleStringProperty(valorSeguro(c.getValue().getDiagnostico())));

        colTratamiento.setCellValueFactory(c ->
                new SimpleStringProperty(valorSeguro(c.getValue().getTratamiento())));

        tablaConsultas.setRowFactory(tv -> {
            TableRow<Consulta> row = new TableRow<>();

            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && !row.isEmpty()) {
                    Consulta consulta = row.getItem();
                    abrirDetalleConsulta(consulta);
                }
            });

            return row;
        });
    }

    private void abrirDetalleConsulta(Consulta consulta) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/DetalleConsultaView.fxml"));
            Scene scene = new Scene(loader.load(), 900, 600);

            DetalleConsultaController controller = loader.getController();
            controller.setData(paciente, consulta);

            Stage stage = (Stage) tablaConsultas.getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Detalle Consulta");
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

            Stage stage = (Stage) tablaConsultas.getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Detalle del Paciente");
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String valorSeguro(String v) {
        return v != null ? v : "";
    }
}