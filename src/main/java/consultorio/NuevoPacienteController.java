package consultorio;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class NuevoPacienteController {

    @FXML private TextField txtDni;
    @FXML private TextField txtApellido;
    @FXML private TextField txtNombre;
    @FXML private DatePicker dpFechaNacimiento;
    @FXML private ComboBox<String> cbSexo;
    @FXML private TextField txtTelefono;
    @FXML private TextField txtDomicilio;
    @FXML private TextField txtObraSocial;
    @FXML private TextField txtNumeroAfiliado;
    @FXML private TextArea txtObservaciones;

    private final DateTimeFormatter formatoArgentino = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    private final DateTimeFormatter formatoBD = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    
    private Paciente pacienteEditar = null;

    public void setPacienteEditar(Paciente paciente) {
    this.pacienteEditar = paciente;

    txtDni.setText(valorSeguro(paciente.getDni()));
    txtApellido.setText(valorSeguro(paciente.getApellido()));
    txtNombre.setText(valorSeguro(paciente.getNombre()));

    if (paciente.getFechaNacimiento() != null && !paciente.getFechaNacimiento().isBlank()) {
        dpFechaNacimiento.setValue(java.time.LocalDate.parse(paciente.getFechaNacimiento()));
    }

    cbSexo.setValue(valorSeguro(paciente.getSexo()));
    txtTelefono.setText(valorSeguro(paciente.getTelefono()));
    txtDomicilio.setText(valorSeguro(paciente.getDomicilio()));
    txtObraSocial.setText(valorSeguro(paciente.getObraSocial()));
    txtNumeroAfiliado.setText(valorSeguro(paciente.getNumeroAfiliado()));
    txtObservaciones.setText(valorSeguro(paciente.getObservaciones()));
}
    private String valorSeguro(String valor) {
    return valor != null ? valor : "";
}

    @FXML
    public void initialize() {
        cbSexo.setItems(FXCollections.observableArrayList("Masculino", "Femenino"));

        dpFechaNacimiento.setConverter(new StringConverter<>() {
            @Override
            public String toString(LocalDate date) {
                return date != null ? formatoArgentino.format(date) : "";
            }

            @Override
            public LocalDate fromString(String string) {
                if (string == null || string.isBlank()) {
                    return null;
                }
                return LocalDate.parse(string, formatoArgentino);
            }
        });

        dpFechaNacimiento.setPromptText("DD-MM-AAAA");
    }

    @FXML
    private void onGuardar() {
        if (txtDni.getText().isBlank() || txtApellido.getText().isBlank() || txtNombre.getText().isBlank()) {
            mostrarAlerta(Alert.AlertType.WARNING, "Campos obligatorios", "DNI, Apellido y Nombre son obligatorios.");
            return;
        }

        Paciente paciente = new Paciente();
        paciente.setDni(txtDni.getText().trim());
        paciente.setApellido(txtApellido.getText().trim());
        paciente.setNombre(txtNombre.getText().trim());

        if (dpFechaNacimiento.getValue() != null) {
            paciente.setFechaNacimiento(dpFechaNacimiento.getValue().format(formatoBD));
        } else {
            paciente.setFechaNacimiento(null);
        }

        paciente.setSexo(cbSexo.getValue());
        paciente.setTelefono(txtTelefono.getText().trim());
        paciente.setDomicilio(txtDomicilio.getText().trim());
        paciente.setObraSocial(txtObraSocial.getText().trim());
        paciente.setNumeroAfiliado(txtNumeroAfiliado.getText().trim());
        paciente.setObservaciones(txtObservaciones.getText().trim());

        PacienteDAO dao = new PacienteDAO();
boolean ok;

if (pacienteEditar == null) {
    String resultado = dao.guardarConMensaje(paciente);

    switch (resultado) {
        case "OK" -> {
            mostrarAlerta(Alert.AlertType.INFORMATION, "Éxito", "Paciente guardado correctamente.");
            volverAlListado();
        }
        case "DNI_DUPLICADO" ->
                mostrarAlerta(Alert.AlertType.WARNING, "DNI existente", "Ya existe un paciente con ese DNI.");
        default ->
                mostrarAlerta(Alert.AlertType.ERROR, "Error", "No se pudo guardar el paciente.");
    }
    return;
} else {
    paciente.setId(pacienteEditar.getId());
    ok = dao.actualizar(paciente);

    if (ok) {
        mostrarAlerta(Alert.AlertType.INFORMATION, "Éxito", "Paciente actualizado correctamente.");
        volverAlListado();
    } else {
        mostrarAlerta(Alert.AlertType.ERROR, "Error", "No se pudo actualizar el paciente.");
    }
}
    }
    @FXML
    private void onVolver() {
        volverAlListado();
    }

    private void volverAlListado() {
    try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/PacientesView.fxml"));
        Scene scene = new Scene(loader.load(), 900, 600);

        Stage stage = (Stage) txtDni.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Listado de Pacientes");
        stage.show();

    } catch (Exception e) {
        e.printStackTrace();
        mostrarAlerta(Alert.AlertType.ERROR, "Error", "No se pudo volver al listado.");
    }
}
    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String mensaje) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
    @FXML
private void onLimpiar() {
    limpiarCampos();
}

private void limpiarCampos() {
    txtDni.clear();
    txtApellido.clear();
    txtNombre.clear();
    dpFechaNacimiento.setValue(null);
    cbSexo.setValue(null);
    txtTelefono.clear();
    txtDomicilio.clear();
    txtObraSocial.clear();
    txtNumeroAfiliado.clear();
    txtObservaciones.clear();
}
}