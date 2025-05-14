/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyectojava;

import java.util.Scanner;

public class ValidarEntrada {

    private static final Scanner sc = new Scanner(System.in);

    public static String formatearNombreConValidacion(String mensaje) {
        String input = "";
        while (true) {
            System.out.print(mensaje);
            input = sc.nextLine().trim();
            if (!input.matches(".*\\d.*")) {
                break;
            }
            System.out.println("No se permiten numeros.");
        }

        String[] palabras = input.split("\\s+");
        StringBuilder resultado = new StringBuilder();

        for (int i = 0; i < palabras.length; i++) {
            String palabra = palabras[i].toLowerCase();
            palabra = Character.toUpperCase(palabra.charAt(0)) + palabra.substring(1);
            resultado.append(palabra);
            if (i < palabras.length - 1) {
                resultado.append(".");
            }
        }

        return resultado.toString();
    }

    public static String pedirContrasenaSegura() {
        String contrasena;
        while (true) {
            System.out.print("Contrasena (minimo 8 caracteres): ");
            contrasena = sc.nextLine();
            if (contrasena.length() >= 8) {
                break;
            }
            System.out.println("La contrasena debe tener al menos 8 caracteres.");
        }
        return contrasena;
    }

    public static int pedirEntero(String mensaje, int min, int max) {
        int valor = -1;
        while (true) {
            System.out.print(mensaje);
            try {
                valor = Integer.parseInt(sc.nextLine());
                if (valor < min || valor > max) {
                    System.out.println("Debe estar entre " + min + " y " + max);
                } else {
                    return valor;
                }
            } catch (NumberFormatException e) {
                System.out.println("Entrada invalida. Debe ser un numero entero.");
            }
        }
    }

    public static int pedirEdadValida() {
        int edad = 0;
        while (edad < 17 || edad > 119) {
            System.out.print("Edad (17-119): ");
            try {
                edad = Integer.parseInt(sc.nextLine());
                if (edad < 17 || edad > 119) {
                    System.out.println("Edad fuera de rango.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Edad invalida.");
            }
        }
        return edad;
    }

    public static String pedirEstadoCivilValido() {
        String estadoCivil = "";
        while (true) {
            System.out.print("Estado civil (Soltero/a, Divorciado/a, Viudo/a, Otro): ");
            estadoCivil = sc.nextLine().trim();
            if (esEstadoCivilValido(estadoCivil)) {
                return estadoCivil;
            }
            System.out.println("Estado civil invalido.");
        }
    }

    private static boolean esEstadoCivilValido(String estado) {
        String[] validos = {"Soltero", "Soltera", "Divorciado", "Divorciada", "Viudo", "Viuda", "Otro"};
        for (String val : validos) {
            if (estado.equalsIgnoreCase(val)) {
                return true;
            }
        }
        return false;
    }

    public static float pedirFloat(String mensaje) {
        float valor = -1;
        while (valor < 0) {
            System.out.print(mensaje);
            try {
                valor = Float.parseFloat(sc.nextLine().replace(",", "."));
                if (valor < 0) {
                    System.out.println("Debe ser positivo.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Formato invalido.");
            }
        }
        return valor;
    }

    public static float pedirFloat(String mensaje, float min, float max) {
        float valor = -1;
        while (valor < min || valor > max) {
            System.out.print(mensaje);
            try {
                valor = Float.parseFloat(sc.nextLine().replace(",", "."));
                if (valor < min || valor > max) {
                    System.out.println("Debe estar entre " + min + " y " + max);
                }
            } catch (NumberFormatException e) {
                System.out.println("Formato invalido.");
            }
        }
        return valor;
    }

    public static boolean pedirEnMora() {
        Boolean enMora = null;
        while (enMora == null) {
            System.out.print("En mora (true/false): ");
            String entrada = sc.nextLine().trim().toLowerCase();
            if (entrada.equals("true")) {
                enMora = true;
            } else if (entrada.equals("false")) {
                enMora = false;
            } else {
                System.out.println("Solo se acepta true o false.");
            }
        }
        return enMora;
    }
}
