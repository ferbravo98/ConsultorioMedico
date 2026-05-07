package consultorio;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class ConexionSQLite {

    private static final String URL = "jdbc:sqlite:consultorio.db";

    public static Connection obtenerConexion() throws SQLException {
        Connection conn = DriverManager.getConnection(URL);

        try (Statement stmt = conn.createStatement()) {
            stmt.execute("PRAGMA foreign_keys = ON;");
        }

        return conn;
    }

    public static boolean probarConexion() {
        try (Connection conn = obtenerConexion()) {
            return conn != null && !conn.isClosed();
        } catch (SQLException e) {
            System.out.println("Error SQLite: " + e.getMessage());
            return false;
        }
    }
}