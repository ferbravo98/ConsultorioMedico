package consultorio;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class PacienteDAO {

    public List<Paciente> listarTodos() {
        List<Paciente> lista = new ArrayList<>();

        String sql = """
                SELECT id, dni, apellido, nombre, fecha_nacimiento, sexo,
                       telefono, domicilio, obra_social, numero_afiliado, observaciones
                FROM pacientes
                ORDER BY apellido, nombre
                """;

        try (Connection conn = ConexionMySQL.obtenerConexion();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Paciente paciente = new Paciente();
                paciente.setId(rs.getInt("id"));
                paciente.setDni(rs.getString("dni"));
                paciente.setApellido(rs.getString("apellido"));
                paciente.setNombre(rs.getString("nombre"));
                paciente.setFechaNacimiento(rs.getString("fecha_nacimiento"));
                paciente.setSexo(rs.getString("sexo"));
                paciente.setTelefono(rs.getString("telefono"));
                paciente.setDomicilio(rs.getString("domicilio"));
                paciente.setObraSocial(rs.getString("obra_social"));
                paciente.setNumeroAfiliado(rs.getString("numero_afiliado"));
                paciente.setObservaciones(rs.getString("observaciones"));

                lista.add(paciente);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return lista;
    }

    public String guardarConMensaje(Paciente paciente) {
    String sql = """
            INSERT INTO pacientes
            (dni, apellido, nombre, fecha_nacimiento, sexo, telefono, domicilio,
             obra_social, numero_afiliado, observaciones)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
            """;

    try (Connection conn = ConexionMySQL.obtenerConexion();
         PreparedStatement ps = conn.prepareStatement(sql)) {

        ps.setString(1, paciente.getDni());
        ps.setString(2, paciente.getApellido());
        ps.setString(3, paciente.getNombre());
        ps.setString(4, paciente.getFechaNacimiento());
        ps.setString(5, paciente.getSexo());
        ps.setString(6, paciente.getTelefono());
        ps.setString(7, paciente.getDomicilio());
        ps.setString(8, paciente.getObraSocial());
        ps.setString(9, paciente.getNumeroAfiliado());
        ps.setString(10, paciente.getObservaciones());

        ps.executeUpdate();
        return "OK";

    } catch (java.sql.SQLIntegrityConstraintViolationException e) {
        return "DNI_DUPLICADO";
    } catch (Exception e) {
        e.printStackTrace();
        return "ERROR";
    }
}

    public List<Paciente> buscar(String texto) {
    List<Paciente> lista = new ArrayList<>();

    String sql = """
            SELECT id, dni, apellido, nombre, fecha_nacimiento, sexo,
                   telefono, domicilio, obra_social, numero_afiliado, observaciones
            FROM pacientes
            WHERE dni LIKE ? OR apellido LIKE ? OR nombre LIKE ?
            ORDER BY apellido, nombre
            """;

    try (Connection conn = ConexionMySQL.obtenerConexion();
         PreparedStatement ps = conn.prepareStatement(sql)) {

        String filtro = "%" + texto + "%";
        ps.setString(1, filtro);
        ps.setString(2, filtro);
        ps.setString(3, filtro);

        try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Paciente paciente = new Paciente();
                paciente.setId(rs.getInt("id"));
                paciente.setDni(rs.getString("dni"));
                paciente.setApellido(rs.getString("apellido"));
                paciente.setNombre(rs.getString("nombre"));
                paciente.setFechaNacimiento(rs.getString("fecha_nacimiento"));
                paciente.setSexo(rs.getString("sexo"));
                paciente.setTelefono(rs.getString("telefono"));
                paciente.setDomicilio(rs.getString("domicilio"));
                paciente.setObraSocial(rs.getString("obra_social"));
                paciente.setNumeroAfiliado(rs.getString("numero_afiliado"));
                paciente.setObservaciones(rs.getString("observaciones"));

                lista.add(paciente);
            }
        }

    } catch (Exception e) {
        e.printStackTrace();
    }

    return lista;
}
public boolean guardar(Paciente paciente) {
    String sql = """
            INSERT INTO pacientes
            (dni, apellido, nombre, fecha_nacimiento, sexo, telefono, domicilio,
             obra_social, numero_afiliado, observaciones)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
            """;

    try (Connection conn = ConexionMySQL.obtenerConexion();
         PreparedStatement ps = conn.prepareStatement(sql)) {

        ps.setString(1, paciente.getDni());
        ps.setString(2, paciente.getApellido());
        ps.setString(3, paciente.getNombre());
        ps.setString(4, paciente.getFechaNacimiento());
        ps.setString(5, paciente.getSexo());
        ps.setString(6, paciente.getTelefono());
        ps.setString(7, paciente.getDomicilio());
        ps.setString(8, paciente.getObraSocial());
        ps.setString(9, paciente.getNumeroAfiliado());
        ps.setString(10, paciente.getObservaciones());

        return ps.executeUpdate() > 0;

    } catch (Exception e) {
        e.printStackTrace();
        return false;
    }
}
    public boolean actualizar(Paciente paciente) {
    String sql = """
            UPDATE pacientes SET
            dni = ?,
            apellido = ?,
            nombre = ?,
            fecha_nacimiento = ?,
            sexo = ?,
            telefono = ?,
            domicilio = ?,
            obra_social = ?,
            numero_afiliado = ?,
            observaciones = ?
            WHERE id = ?
            """;

    try (Connection conn = ConexionMySQL.obtenerConexion();
         PreparedStatement ps = conn.prepareStatement(sql)) {

        ps.setString(1, paciente.getDni());
        ps.setString(2, paciente.getApellido());
        ps.setString(3, paciente.getNombre());
        ps.setString(4, paciente.getFechaNacimiento());
        ps.setString(5, paciente.getSexo());
        ps.setString(6, paciente.getTelefono());
        ps.setString(7, paciente.getDomicilio());
        ps.setString(8, paciente.getObraSocial());
        ps.setString(9, paciente.getNumeroAfiliado());
        ps.setString(10, paciente.getObservaciones());
        ps.setInt(11, paciente.getId());

        return ps.executeUpdate() > 0;

    } catch (Exception e) {
        e.printStackTrace();
        return false;
    }
}
}