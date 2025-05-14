package proyectojava;

import java.awt.CardLayout;
import java.awt.Panel;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class ProyectoJavaClase {

    private static final Scanner sc = new Scanner(System.in);

    // Solicita los datos necesarios para crear un nuevo cliente
    private static Cliente crearClienteDesdeInput(String nombre) {
        System.out.println("Nombre de usuario establecido: " + nombre);

        int edad = ValidarEntrada.pedirEdadValida();
        String estadoCivil = ValidarEntrada.pedirEstadoCivilValido();
        String tipoCuenta = ValidarEntrada.formatearNombreConValidacion("Nombre cuenta: ");
        float balance = ValidarEntrada.pedirFloat("Balance (>= 0): ");
        float interes = ValidarEntrada.pedirFloat("Interes (0 - 100): ", 0, 100);
        float deuda = ValidarEntrada.pedirFloat("Monto deuda (>= 0): ");
        boolean enMora = ValidarEntrada.pedirEnMora();

        Cuenta cuenta = new Cuenta(tipoCuenta, balance, interes);
        Deuda d = new Deuda(deuda, enMora);
        return new Cliente(nombre, edad, estadoCivil, cuenta, d);
    }

    // Muestra el menu del usuario logueado con opciones para operar
    private static void menuUsuario(Cliente clienteActivo, Connection conexion) {
        boolean logueado = true;

        while (logueado) {
            System.out.println("\n--- MENU USUARIO ---");
            System.out.println("1. Ver resumen");
            System.out.println("2. Pedir prestamo");
            System.out.println("3. Ver prestamos");
            System.out.println("4. Pagar prestamo");
            System.out.println("5. Eliminar cuenta");
            System.out.println("6. Cerrar sesion");
            System.out.print("Opcion: ");
            String opcion = sc.nextLine();

            switch (opcion) {
                case "1":
                    clienteActivo = PrestamoDAO.refrescarCliente(clienteActivo, conexion);
                    System.out.println("\n--- RESUMEN ---");
                    System.out.println("Nombre: " + clienteActivo.getNombre());
                    System.out.println("Cuenta: " + clienteActivo.getCuenta());
                    System.out.println("Deuda: " + clienteActivo.getDeuda());
                    break;
                case "2":
                    // Solicita datos del préstamo
                    float monto = ValidarEntrada.pedirFloat("Monto del prestamo: ");
                    float interes = ValidarEntrada.pedirFloat("Interes (%): ");
                    int plazo = ValidarEntrada.pedirEntero("Plazo en meses: ", 1, 120);

                    // Crea y guarda el préstamo
                    Prestamo prestamo = new Prestamo(clienteActivo.getNombre(), monto, interes, plazo);
                    PrestamoDAO.agregarPrestamo(prestamo, conexion);

                    // Aumenta el balance de la cuenta con el monto del préstamo
                    float nuevoBalance = clienteActivo.getCuenta().getBalance() + monto;
                    clienteActivo.getCuenta().setBalance(nuevoBalance);

                    // Actualiza en la base de datos
                    ClienteDAO.actualizarCuenta(clienteActivo, conexion);

                    // Actualiza la deuda desde la base de datos para que esté sincronizada
                    clienteActivo = PrestamoDAO.refrescarCliente(clienteActivo, conexion);

                    System.out.println("Prestamo registrado con exito.");
                    break;

                case "3":
                    List<Prestamo> lista = PrestamoDAO.obtenerPrestamosDe(clienteActivo.getNombre(), conexion);
                    if (lista.isEmpty()) {
                        System.out.println("No tienes prestamos registrados.");
                    } else {
                        System.out.println("Tus prestamos:");
                        int i = 0;
                        for (Prestamo p : lista) {
                            System.out.println("[" + i + "] " + p);
                            i++;
                        }
                    }
                    break;
                case "4":
                    System.out.print("Indice del prestamo a pagar: ");
                    int index = Integer.parseInt(sc.nextLine());
                    PrestamoDAO.pagarPrestamo(clienteActivo.getNombre(), index, conexion);
                    break;
                case "5":
                    System.out.print("Estas seguro de que deseas eliminar tu cuenta? (S/N): ");
                    String confirmar = sc.nextLine().trim().toUpperCase();
                    if (confirmar.equals("S")) {
                        PrestamoDAO.eliminarPrestamosDe(clienteActivo.getNombre(), conexion);
                        ClienteDAO.eliminarCliente(clienteActivo.getNombre(), conexion);
                        LoginDAO.eliminarLogin(clienteActivo.getNombre(), conexion);
                        System.out.println("Tu cuenta ha sido eliminada correctamente.");
                        logueado = false;
                    } else {
                        System.out.println("Operacion cancelada.");
                    }
                    break;
                case "6":
                    logueado = false;
                    System.out.println("Sesion cerrada.");
                    break;
                default:
                    System.out.println("Opcion no valida.");
            }
        }
    }

    // Punto de entrada principal del programa
    public static void main(String[] args) {
        
        ConexionBD.inicializarBD();
        
        Connection conexion = ConexionBD.getDBConnection();
        if (conexion == null) {
            return;
        }
        
        Cliente clienteActivo = null;
        
        while (true) {
            
            SwingUtilities.invokeLater(() -> {
                JFrame frame = new JFrame("Trabajo Programación");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setContentPane(new jContenedor()); // Tu panel principal
                frame.pack(); // Ajusta al tamaño del contenido
                frame.setLocationRelativeTo(null); // Centra la ventana
                frame.setVisible(true);
            });
            
            System.out.println("\n=== MENU PRINCIPAL ===");
            System.out.println("1. Iniciar sesion");
            System.out.println("2. Registrarse");
            System.out.println("3. Salir");
            System.out.print("Seleccion: ");
            String opcion = sc.nextLine();
            
            switch (opcion) {
                case "1":
                    System.out.print("Nombre: ");
                    String nombre = sc.nextLine();
                    System.out.print("Contrasena: ");
                    String pass = sc.nextLine();

                    if (!LoginDAO.verificar(nombre, pass, conexion)) {
                        System.out.println("Credenciales incorrectas.");
                        break;
                    }

                    clienteActivo = ClienteDAO.obtenerCliente(nombre, conexion);

                    if (clienteActivo == null) {
                        System.out.println("No tienes perfil creado. Vamos a crearlo ahora.");
                        Cliente nuevo = crearClienteDesdeInput(nombre);
                        ClienteDAO.insertarCliente(nuevo, conexion);
                        clienteActivo = nuevo;
                    }

                    menuUsuario(clienteActivo, conexion);
                    break;

                case "2":
                    String nuevoNombre = ValidarEntrada.formatearNombreConValidacion("Nombre de usuario: ");
                    System.out.println("Nombre registrado como: " + nuevoNombre);

                    if (LoginDAO.existe(nuevoNombre, conexion)) {
                        System.out.println("Ese nombre ya esta en uso.");
                        break;
                    }

                    String nuevaPass = ValidarEntrada.pedirContrasenaSegura();
                    LoginDAO.registrar(nuevoNombre, nuevaPass, conexion);
                    System.out.println("Cuenta creada. Ahora puedes iniciar sesion.");
                    break;

                case "3":
                    System.out.println("Saliendo del programa");
                    return;

                default:
                    System.out.println("Opcion no valida.");
            }
        }
    }
}
