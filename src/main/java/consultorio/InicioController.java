package consultorio;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;

import java.util.List;

public class InicioController {

    @FXML
    private void onProbarConexion() {
        boolean ok = ConexionMySQL.probarConexion();

        Alert alert = new Alert(ok ? Alert.AlertType.INFORMATION : Alert.AlertType.ERROR);
        alert.setTitle("Conexión");
        alert.setHeaderText(null);
        alert.setContentText(ok
                ? "Conexión exitosa con la base de datos"
                : "Error al conectar con la base de datos");

        alert.showAndWait();
    }

    @FXML
    private void onListarPacientes() {
        PacienteDAO dao = new PacienteDAO();
        List<Paciente> pacientes = dao.listarTodos();

        System.out.println("=== PACIENTES ===");
        for (Paciente p : pacientes) {
            System.out.println(
                    p.getId() + " | " +
                    p.getDni() + " | " +
                    p.getNombreCompleto()
            );
        }

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Pacientes");
        alert.setHeaderText(null);
        alert.setContentText("Se listaron " + pacientes.size() + " pacientes en consola.");
        alert.showAndWait();
    }
}