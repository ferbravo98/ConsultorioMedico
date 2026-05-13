package consultorio;

import java.sql.Connection;
import java.sql.Statement;

public class InicializadorBD {

    public static void inicializar() {
    crearTablaPacientes();
    crearTablaConsultas();
    crearTablaEstudios();
    crearTablaCalendarioVacunas();
    crearTablaPacienteVacunas();
    cargarCalendarioVacunasBase();
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
    private static void crearTablaCalendarioVacunas() {
    String sql = """
        CREATE TABLE IF NOT EXISTS calendario_vacunas (
            id INTEGER PRIMARY KEY AUTOINCREMENT,
            edad_texto TEXT NOT NULL,
            edad_meses INTEGER,
            vacuna TEXT NOT NULL,
            dosis TEXT NOT NULL,
            orden INTEGER NOT NULL
        );
    """;

    ejecutar(sql);
}

private static void crearTablaPacienteVacunas() {
    String sql = """
        CREATE TABLE IF NOT EXISTS paciente_vacunas (
            id INTEGER PRIMARY KEY AUTOINCREMENT,
            paciente_id INTEGER NOT NULL,
            calendario_vacuna_id INTEGER NOT NULL,
            aplicada INTEGER DEFAULT 0,
            fecha_aplicacion TEXT,
            observaciones TEXT,
            FOREIGN KEY (paciente_id) REFERENCES pacientes(id) ON DELETE CASCADE,
            FOREIGN KEY (calendario_vacuna_id) REFERENCES calendario_vacunas(id) ON DELETE CASCADE,
            UNIQUE(paciente_id, calendario_vacuna_id)
        );
    """;

    ejecutar(sql);
}
private static void cargarCalendarioVacunasBase() {
    String verificar = "SELECT COUNT(*) FROM calendario_vacunas";

    try (Connection conn = ConexionSQLite.obtenerConexion();
         Statement stmt = conn.createStatement();
         var rs = stmt.executeQuery(verificar)) {

        if (rs.next() && rs.getInt(1) > 0) {
            return;
        }

    } catch (Exception e) {
        e.printStackTrace();
        return;
    }

    String sql = """
        INSERT INTO calendario_vacunas
        (edad_texto, edad_meses, vacuna, dosis, orden)
        VALUES (?, ?, ?, ?, ?)
    """;

    Object[][] datos = {
            {"Recién nacido", 0, "BCG", "Única dosis", 1},
            {"Recién nacido", 0, "Hepatitis B", "Única dosis", 2},

            {"2 meses", 2, "Neumococo conjugada", "1° dosis", 3},
            {"2 meses", 2, "Pentavalente", "1° dosis", 4},
            {"2 meses", 2, "IPV", "1° dosis", 5},
            {"2 meses", 2, "Rotavirus", "1° dosis", 6},

            {"3 meses", 3, "Meningococo ACWY", "1° dosis", 7},

            {"4 meses", 4, "Neumococo conjugada", "2° dosis", 8},
            {"4 meses", 4, "Pentavalente", "2° dosis", 9},
            {"4 meses", 4, "IPV", "2° dosis", 10},
            {"4 meses", 4, "Rotavirus", "2° dosis", 11},

            {"5 meses", 5, "Meningococo ACWY", "2° dosis", 12},

            {"6 meses", 6, "Pentavalente", "3° dosis", 13},
            {"6 meses", 6, "IPV", "3° dosis", 14},

            {"12 meses", 12, "Neumococo conjugada", "Refuerzo", 15},
            {"12 meses", 12, "Hepatitis A", "Única dosis", 16},
            {"12 meses", 12, "Triple viral", "1° dosis", 17},

            {"15 meses", 15, "Meningococo ACWY", "Refuerzo", 18},
            {"15 meses", 15, "Varicela", "1° dosis", 19},

            {"18 meses", 18, "Pentavalente", "1° refuerzo", 20},
            {"18 meses", 18, "Triple viral", "2° dosis", 21},
            {"18 meses", 18, "Fiebre amarilla", "1° dosis", 22},

            {"5 años", 60, "IPV", "1° refuerzo", 23},
            {"5 años", 60, "Triple viral", "2° dosis", 24},
            {"5 años", 60, "Varicela", "2° dosis", 25},
            {"5 años", 60, "Triple bacteriana celular", "2° refuerzo", 26},

            {"11 años", 132, "Meningococo ACWY", "Única dosis", 27},
            {"11 años", 132, "Triple bacteriana acelular", "Refuerzo", 28},
            {"11 años", 132, "VPH", "Única dosis", 29}
    };

    try (Connection conn = ConexionSQLite.obtenerConexion();
         var ps = conn.prepareStatement(sql)) {

        for (Object[] fila : datos) {
            ps.setString(1, (String) fila[0]);
            ps.setInt(2, (Integer) fila[1]);
            ps.setString(3, (String) fila[2]);
            ps.setString(4, (String) fila[3]);
            ps.setInt(5, (Integer) fila[4]);
            ps.addBatch();
        }

        ps.executeBatch();

    } catch (Exception e) {
        e.printStackTrace();
    }
}
}
