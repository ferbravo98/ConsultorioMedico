package consultorio;

import java.sql.Connection;
import java.sql.Statement;

public class InicializadorBD {

    public static void inicializar() {
        crearTablaPacientes();
        crearTablaConsultas();
        crearTablaEstudios();
    }

    private static void crearTablaPacientes() {
        String sql = """
            CREATE TABLE IF NOT EXISTS pacientes (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                dni TEXT NOT NULL UNIQUE,
                apellido TEXT NOT NULL,
                nombre TEXT NOT NULL,
                fecha_nacimiento TEXT,
                sexo TEXT,
                telefono TEXT,
                domicilio TEXT,
                obra_social TEXT,
                numero_afiliado TEXT,
                observaciones TEXT
            );
        """;

        ejecutar(sql);
    }

    private static void crearTablaConsultas() {
        String sql = """
            CREATE TABLE IF NOT EXISTS consultas (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                paciente_id INTEGER NOT NULL,
                fecha_consulta TEXT NOT NULL,
                motivo TEXT,
                diagnostico TEXT,
                tratamiento TEXT,
                observaciones TEXT,
                proximo_control TEXT,
                FOREIGN KEY (paciente_id) REFERENCES pacientes(id) ON DELETE CASCADE
            );
        """;

        ejecutar(sql);
    }

    private static void crearTablaEstudios() {
        String sql = """
            CREATE TABLE IF NOT EXISTS estudios (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                paciente_id INTEGER NOT NULL,
                consulta_id INTEGER,
                fecha TEXT NOT NULL,
                tipo TEXT NOT NULL,
                nombre TEXT NOT NULL,
                indicacion TEXT,
                resultado TEXT,
                estado TEXT DEFAULT 'Solicitado',
                ruta_archivo TEXT,
                FOREIGN KEY (paciente_id) REFERENCES pacientes(id) ON DELETE CASCADE,
                FOREIGN KEY (consulta_id) REFERENCES consultas(id) ON DELETE SET NULL
            );
        """;

        ejecutar(sql);
    }

    private static void ejecutar(String sql) {
        try (Connection conn = ConexionSQLite.obtenerConexion();
             Statement stmt = conn.createStatement()) {

            stmt.execute(sql);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}