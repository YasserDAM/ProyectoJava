package proyectojava;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginDAO {

    /**
     * Verifica si el usuario y la contraseña coinciden.
     */
    public static boolean verificar(String nombre, String pass, Connection conexion) {
        String sql = "SELECT * FROM log WHERE nombre = ? AND contrasena = ?";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, nombre);
            ps.setString(2, pass);
            return ps.executeQuery().next(); // Devuelve true si hay resultado
        } catch (SQLException e) {
            System.out.println("Error en login: " + e.getMessage());
            return false;
        }
    }

    /**
     * Verifica si el usuario ya existe en la tabla log.
     */
    public static boolean existe(String nombre, Connection conexion) {
        String sql = "SELECT * FROM log WHERE nombre = ?";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, nombre);
            return ps.executeQuery().next();
        } catch (SQLException e) {
            System.out.println("Error al comprobar existencia: " + e.getMessage());
            return false;
        }
    }

    /**
     * Registra un nuevo usuario en la tabla log.
     */
    public static boolean registrar(String nombre, String pass, Connection conexion) {
        String sql = "INSERT INTO log (nombre, contrasena) VALUES (?, ?)";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, nombre);
            ps.setString(2, pass);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error al registrar usuario: " + e.getMessage());
            return false;
        }
    }

    /**
     * Elimina el login del usuario de la tabla log.
     */
    public static boolean eliminarLogin(String nombre, Connection conexion) {
        String sql = "DELETE FROM log WHERE nombre = ?";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, nombre);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error al eliminar usuario: " + e.getMessage());
            return false;
        }
    }
}
