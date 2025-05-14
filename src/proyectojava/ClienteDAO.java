package proyectojava;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ClienteDAO {
    
    // Inserta un nuevo cliente en la base de datos
    public static boolean insertarCliente(Cliente c, Connection conexion) {
        
        //Consulta SQL para insertar los clientes
        String sql = "INSERT INTO clientes (nombre, edad, estadoCivil, cuenta_nombre, cuenta_balance, cuenta_interes, deuda_total, deuda_enMora) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            
            //Inserta en la consulta SQL los valores de los clientes
            ps.setString(1, c.getNombre());
            ps.setInt(2, c.getEdad());
            ps.setString(3, c.getEstadoCivil());
            ps.setString(4, c.getCuenta().getNombreCuenta());
            ps.setFloat(5, c.getCuenta().getBalance());
            ps.setFloat(6, c.getCuenta().getInteres());
            ps.setFloat(7, c.getDeuda().getTotal());
            ps.setBoolean(8, c.getDeuda().isEnMora());
            
            //Ejecuta la consutla
            ps.executeUpdate();
            
            //Devuelve verdadero si se ha insertado correctamente
            return true;
            
        } catch (SQLException e) {
            
            //Informa de un error si salta la excepcion
            System.out.println("Error insertando cliente: " + e.getMessage());
            
            //Y devuelve falso
            return false;
        }
    }
    
    // Recupera un cliente desde la base de datos según su nombre
    public static Cliente obtenerCliente(String nombre, Connection conexion){
        
        //Sentencia SQL para buscar un cliente segun su nombre
        String sql = "SELECT * FROM clientes WHERE nombre = ?";
        
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            
            //Inserta en la consulta el nombre a buscar
            ps.setString(1, nombre);
            
            //Ejecuta la consulta
            ResultSet rs = ps.executeQuery();
            
            //Devuelve los datos de la consulta
            if (rs.next()){
                int edad = rs.getInt("edad");
                String estadoCivil = rs.getString("estadoCivil");
                String nombreCuenta = rs.getString("cuenta_nombre");
                float balance = rs.getFloat("cuenta_balance");
                float interes = rs.getFloat("cuenta_interes");
                float deudaTotal = rs.getFloat("deuda_total");
                boolean enMora = rs.getBoolean("deuda_enMora");
                
                //Crea un nuevo cliente con los datos devueltos
                Cuenta cuenta = new Cuenta(nombreCuenta, balance, interes);
                Deuda deuda = new Deuda(deudaTotal, enMora);
                return new Cliente(nombre, edad, estadoCivil, cuenta, deuda);
            }
            
        } catch (SQLException e) {
            
            //Informa al usuario si ha habido algun problema al buscar el cliente
            System.out.println("Error obteniendo cliente: " + e.getMessage());
        }
        
        return null;
    }
    
    // Elimina un cliente de la base de datos (lo elimina en cascada gracias al FK en la tabla log)
    public static boolean eliminarCliente(String nombre, Connection conexion) {
        
        //Sentencia SQL para borrar un cliente de la BD
        String sql = "DELETE FROM clientes WHERE nombre = ?";
        
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            
            //Inserta en la consulta el nombre del cliente a borrar
            ps.setString(1, nombre);
            int filasAfectadas = ps.executeUpdate();
            return filasAfectadas > 0;
        } catch (SQLException e) {
            System.out.println("Error eliminando cliente: " + e.getMessage());
            return false;
        }
    }

    public static void actualizarCuenta(Cliente cliente, Connection conexion) {
        String sql = "UPDATE clientes SET cuenta_balance = ?, cuenta_interes = ? WHERE nombre = ?";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setFloat(1, cliente.getCuenta().getBalance());
            ps.setFloat(2, cliente.getCuenta().getInteres());
            ps.setString(3, cliente.getNombre());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error actualizando cuenta: " + e.getMessage());
        }
    }

}
