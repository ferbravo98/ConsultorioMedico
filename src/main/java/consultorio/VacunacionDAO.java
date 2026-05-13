package consultorio;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class VacunacionDAO {

    public List<PacienteVacuna> listarPorPaciente(int pacienteId) {
        List<PacienteVacuna> lista = new ArrayList<>();

        String sql = """
            SELECT 
                cv.id AS calendario_id,
                cv.edad_texto,
                cv.edad_meses,
                cv.vacuna,
                cv.dosis,
                pv.id AS paciente_vacuna_id,
                pv.aplicada,
                pv.fecha_aplicacion,
                pv.observaciones
            FROM calendario_vacunas cv
            LEFT JOIN paciente_vacunas pv
                ON pv.calendario_vacuna_id = cv.id
               AND pv.paciente_id = ?
            ORDER BY cv.orden
        """;

        try (Connection conn = ConexionSQLite.obtenerConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, pacienteId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    PacienteVacuna v = new PacienteVacuna();

                    v.setId(rs.getInt("paciente_vacuna_id"));
                    v.setPacienteId(pacienteId);
                    v.setCalendarioVacunaId(rs.getInt("calendario_id"));
                    v.setEdadTexto(rs.getString("edad_texto"));
                    v.setEdadMeses(rs.getInt("edad_meses"));
                    v.setVacuna(rs.getString("vacuna"));
                    v.setDosis(rs.getString("dosis"));
                    v.setAplicada(rs.getInt("aplicada") == 1);
                    v.setFechaAplicacion(rs.getString("fecha_aplicacion"));
                    v.setObservaciones(rs.getString("observaciones"));

                    lista.add(v);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return lista;
    }

    public boolean guardarEstado(PacienteVacuna vacuna) {
        String sql = """
            INSERT INTO paciente_vacunas
            (paciente_id, calendario_vacuna_id, aplicada, fecha_aplicacion, observaciones)
            VALUES (?, ?, ?, ?, ?)
            ON CONFLICT(paciente_id, calendario_vacuna_id)
            DO UPDATE SET
                aplicada = excluded.aplicada,
                fecha_aplicacion = excluded.fecha_aplicacion,
                observaciones = excluded.observaciones
        """;

        try (Connection conn = ConexionSQLite.obtenerConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, vacuna.getPacienteId());
            ps.setInt(2, vacuna.getCalendarioVacunaId());
            ps.setInt(3, vacuna.isAplicada() ? 1 : 0);
            ps.setString(4, vacuna.getFechaAplicacion());
            ps.setString(5, vacuna.getObservaciones());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}