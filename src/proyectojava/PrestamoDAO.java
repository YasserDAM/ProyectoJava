package proyectojava;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PrestamoDAO {

    // Inserta un nuevo prestamo para un cliente
    public static void agregarPrestamo(Prestamo p, Connection conexion) {
        String sql = "INSERT INTO prestamos (cliente, monto, interes, plazo) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, p.getCliente());
            ps.setFloat(2, p.getMonto());
            ps.setFloat(3, p.getInteres());
            ps.setInt(4, p.getPlazo());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error insertando prestamo: " + e.getMessage());
        }

        actualizarDeudaCliente(p.getCliente(), conexion);
    }

    // Obtiene la lista de prestamos de un cliente
    public static List<Prestamo> obtenerPrestamosDe(String cliente, Connection conexion) {
        List<Prestamo> lista = new ArrayList<>();
        String sql = "SELECT * FROM prestamos WHERE cliente = ?";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, cliente);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Prestamo p = new Prestamo(
                    cliente,
                    rs.getFloat("monto"),
                    rs.getFloat("interes"),
                    rs.getInt("plazo")
                );
                lista.add(p);
            }
        } catch (SQLException e) {
            System.out.println("Error obteniendo prestamos: " + e.getMessage());
        }
        return lista;
    }

    // Elimina todos los prestamos de un cliente
    public static void eliminarPrestamosDe(String cliente, Connection conexion) {
        String sql = "DELETE FROM prestamos WHERE cliente = ?";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, cliente);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error eliminando prestamos: " + e.getMessage());
        }

        actualizarDeudaCliente(cliente, conexion);
    }

    // Marca un prestamo como pagado eliminandolo por su indice
    public static void pagarPrestamo(String cliente, int index, Connection conexion) {
        List<Prestamo> prestamos = obtenerPrestamosDe(cliente, conexion);
        if (index < 0 || index >= prestamos.size()) {
            System.out.println("Indice invalido.");
            return;
        }

        Prestamo aEliminar = prestamos.get(index);

        String sql = "DELETE FROM prestamos WHERE cliente = ? AND monto = ? AND interes = ? AND plazo = ? LIMIT 1";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, aEliminar.getCliente());
            ps.setFloat(2, aEliminar.getMonto());
            ps.setFloat(3, aEliminar.getInteres());
            ps.setInt(4, aEliminar.getPlazo());
            int filas = ps.executeUpdate();

            if (filas > 0) {
                System.out.println("Prestamo pagado correctamente.");
            } else {
                System.out.println("No se encontro el prestamo especificado.");
            }

        } catch (SQLException e) {
            System.out.println("Error pagando prestamo: " + e.getMessage());
        }

        actualizarDeudaCliente(cliente, conexion);
    }

    // Actualiza la deuda_total y deuda_enMora del cliente en la base de datos
    public static void actualizarDeudaCliente(String cliente, Connection conexion) {
        List<Prestamo> prestamos = obtenerPrestamosDe(cliente, conexion);
        float total = 0;
        for (Prestamo p : prestamos) {
            total += p.getMonto();
        }

        String sql = "UPDATE clientes SET deuda_total = ?, deuda_enMora = ? WHERE nombre = ?";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setFloat(1, total);
            ps.setBoolean(2, total > 0);
            ps.setString(3, cliente);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error actualizando deuda del cliente: " + e.getMessage());
        }
    }
    
        // Refresca el cliente con la deuda actual desde base de datos
    public static Cliente refrescarCliente(Cliente cliente, Connection conexion) {
        try {
            return ClienteDAO.obtenerCliente(cliente.getNombre(), conexion);
        } catch (Exception e) {
            System.out.println("Error refrescando cliente: " + e.getMessage());
            return cliente; // Retorna el cliente original si hay un error
        }
    }
}

