package proyectojava;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Clase encargada de establecer la conexión con la base de datos MySQL
 * y crear las tablas necesarias.
 */
public class ConexionBD {

    private static final String URL = "jdbc:mysql://localhost:3307";
    private static final String DB_NAME = "banco";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    /**
     * Crea la base de datos y las tablas si no existen.
     */
    public static void inicializarBD() {
        Connection conexion = null;
        Statement stmt = null;

        try {
            // Conectar sin especificar base de datos
            conexion = DriverManager.getConnection(URL, USER, PASSWORD);
            stmt = conexion.createStatement();

            // Crear la base de datos si no existe
            String crearBD = "CREATE DATABASE IF NOT EXISTS " + DB_NAME;
            stmt.executeUpdate(crearBD);
            System.out.println("Base de datos 'banco' creada o ya existente.");

            // Cerrar conexión inicial y abrir nueva conexión a la base de datos
            stmt.close();
            conexion.close();
            conexion = DriverManager.getConnection(URL + "/" + DB_NAME, USER, PASSWORD);
            stmt = conexion.createStatement();

            // Crear tablas
            String logTable = "CREATE TABLE IF NOT EXISTS log ("
                    + "nombre VARCHAR(50) PRIMARY KEY, "
                    + "contrasena VARCHAR(50) NOT NULL)";
            stmt.executeUpdate(logTable);

            String clientesTable = "CREATE TABLE IF NOT EXISTS clientes ("
                    + "nombre VARCHAR(50) PRIMARY KEY, "
                    + "edad INT, "
                    + "estadoCivil VARCHAR(30), "
                    + "cuenta_nombre VARCHAR(50), "
                    + "cuenta_balance FLOAT, "
                    + "cuenta_interes FLOAT, "
                    + "deuda_total FLOAT, "
                    + "deuda_enMora BOOLEAN, "
                    + "FOREIGN KEY (nombre) REFERENCES log(nombre) ON DELETE CASCADE)";
            stmt.executeUpdate(clientesTable);

            String prestamosTable = "CREATE TABLE IF NOT EXISTS prestamos ("
                    + "id INT AUTO_INCREMENT PRIMARY KEY, "
                    + "cliente VARCHAR(50), "
                    + "monto FLOAT, "
                    + "interes FLOAT, "
                    + "plazo INT, "
                    + "FOREIGN KEY (cliente) REFERENCES clientes(nombre) ON DELETE CASCADE)";
            stmt.executeUpdate(prestamosTable);

            System.out.println("Tablas creadas o ya existentes.");

        } catch (SQLException e) {
            System.out.println("Error en la configuración de la base de datos: " + e.getMessage());
        } finally {
            try {
                if (stmt != null) stmt.close();
                if (conexion != null) conexion.close();
            } catch (SQLException e) {
                System.out.println("Error al cerrar la conexión: " + e.getMessage());
            }
        }
    }

    /**
     * Establece y devuelve la conexión con la base de datos.
     * @return una conexión activa o null si falla.
     */
    public static Connection getInitialConnection(){
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e){
            System.out.println("Error en la conexión: " + e.getMessage());
            return null;
        }
    }
    
    public static Connection getDBConnection(){
        try {
            return DriverManager.getConnection(URL +"/banco", USER, PASSWORD);
        } catch (SQLException e){
            System.out.println("Error en la conexión: " + e.getMessage());
            return null;
        }
    }
}
