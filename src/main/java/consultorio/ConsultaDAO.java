package consultorio;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ConsultaDAO {

    public boolean guardar(Consulta consulta) {
        String sql = """
                INSERT INTO consultas
                (paciente_id, fecha_consulta, motivo, diagnostico, tratamiento, observaciones, proximo_control)
                VALUES (?, ?, ?, ?, ?, ?, ?)
                """;

        try (Connection conn = ConexionMySQL.obtenerConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, consulta.getPacienteId());
            ps.setString(2, consulta.getFechaConsulta());
            ps.setString(3, consulta.getMotivo());
            ps.setString(4, consulta.getDiagnostico());
            ps.setString(5, consulta.getTratamiento());
            ps.setString(6, consulta.getObservaciones());
            ps.setString(7, consulta.getProximoControl());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    public List<Consulta> listarPorPaciente(int pacienteId) {
    List<Consulta> lista = new ArrayList<>();

    String sql = """
        SELECT * FROM consultas
        WHERE paciente_id = ?
        ORDER BY fecha_consulta DESC
    """;

    try (Connection conn = ConexionMySQL.obtenerConexion();
         PreparedStatement ps = conn.prepareStatement(sql)) {

        ps.setInt(1, pacienteId);

        try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Consulta c = new Consulta();
                c.setId(rs.getInt("id"));
                c.setPacienteId(rs.getInt("paciente_id"));
                c.setFechaConsulta(rs.getString("fecha_consulta"));
                c.setMotivo(rs.getString("motivo"));
                c.setDiagnostico(rs.getString("diagnostico"));
                c.setTratamiento(rs.getString("tratamiento"));
                c.setObservaciones(rs.getString("observaciones"));
                c.setProximoControl(rs.getString("proximo_control"));

                lista.add(c);
            }
        }

    } catch (Exception e) {
        e.printStackTrace();
    }

    return lista;
}
public boolean actualizar(Consulta consulta) {
    String sql = """
        UPDATE consultas SET
        fecha_consulta = ?,
        motivo = ?,
        diagnostico = ?,
        tratamiento = ?,
        observaciones = ?,
        proximo_control = ?
        WHERE id = ?
    """;

    try (Connection conn = ConexionMySQL.obtenerConexion();
         PreparedStatement ps = conn.prepareStatement(sql)) {

        ps.setString(1, consulta.getFechaConsulta());
        ps.setString(2, consulta.getMotivo());
        ps.setString(3, consulta.getDiagnostico());
        ps.setString(4, consulta.getTratamiento());
        ps.setString(5, consulta.getObservaciones());
        ps.setString(6, consulta.getProximoControl());
        ps.setInt(7, consulta.getId());

        return ps.executeUpdate() > 0;

    } catch (Exception e) {
        e.printStackTrace();
        return false;
    }
}
}