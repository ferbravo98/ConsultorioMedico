package consultorio;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EstudioDAO {

    public boolean guardar(Estudio estudio) {
        String sql = """
            INSERT INTO estudios
            (paciente_id, consulta_id, fecha, tipo, nombre, indicacion, resultado, estado, ruta_archivo)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)
        """;

        try (Connection conn = ConexionSQLite.obtenerConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, estudio.getPacienteId());

            if (estudio.getConsultaId() != null) {
                ps.setInt(2, estudio.getConsultaId());
            } else {
                ps.setNull(2, Types.INTEGER);
            }

            ps.setString(3, estudio.getFecha());
            ps.setString(4, estudio.getTipo());
            ps.setString(5, estudio.getNombre());
            ps.setString(6, estudio.getIndicacion());
            ps.setString(7, estudio.getResultado());
            ps.setString(8, estudio.getEstado());
            ps.setString(9, estudio.getRutaArchivo());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Estudio> listarPorPaciente(int pacienteId) {
        List<Estudio> lista = new ArrayList<>();

        String sql = """
            SELECT * FROM estudios
            WHERE paciente_id = ?
            ORDER BY fecha DESC
        """;

        try (Connection conn = ConexionSQLite.obtenerConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, pacienteId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Estudio e = new Estudio();
                    e.setId(rs.getInt("id"));
                    e.setPacienteId(rs.getInt("paciente_id"));

                    int consultaId = rs.getInt("consulta_id");
                    e.setConsultaId(rs.wasNull() ? null : consultaId);

                    e.setFecha(rs.getString("fecha"));
                    e.setTipo(rs.getString("tipo"));
                    e.setNombre(rs.getString("nombre"));
                    e.setIndicacion(rs.getString("indicacion"));
                    e.setResultado(rs.getString("resultado"));
                    e.setEstado(rs.getString("estado"));
                    e.setRutaArchivo(rs.getString("ruta_archivo"));

                    lista.add(e);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return lista;
    }

    public boolean actualizar(Estudio estudio) {
        String sql = """
            UPDATE estudios SET
            fecha = ?,
            tipo = ?,
            nombre = ?,
            indicacion = ?,
            resultado = ?,
            estado = ?,
            ruta_archivo = ?
            WHERE id = ?
        """;

        try (Connection conn = ConexionSQLite.obtenerConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, estudio.getFecha());
            ps.setString(2, estudio.getTipo());
            ps.setString(3, estudio.getNombre());
            ps.setString(4, estudio.getIndicacion());
            ps.setString(5, estudio.getResultado());
            ps.setString(6, estudio.getEstado());
            ps.setString(7, estudio.getRutaArchivo());
            ps.setInt(8, estudio.getId());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}