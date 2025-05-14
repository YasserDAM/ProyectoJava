/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyectojava;

import java.util.ArrayList;

public class Deuda {
    private float total;
    private boolean enMora;
    private ArrayList<Prestamo> listaPrestamos = new ArrayList<>();

    public Deuda(float total, boolean enMora) {
        this.total = total;
        this.enMora = enMora;
    }

    public float getTotal() {
        return total;
    }

    public boolean isEnMora() {
        return enMora;
    }
//Lo confirma para añadir
    public void checkPrestamo() {
        float acumulado = 0;
        for (Prestamo prestamo : listaPrestamos) {
            acumulado += prestamo.getMonto();
        }
        if (acumulado > 0) {
            System.out.println("Esta cuenta no puede obtener mas prestamos");
        } else {
            System.out.println("Redirigiendo al menu de prestamos");
        }
    }
//Se agrega prestamo
    public void agregarPrestamo(Prestamo prestamo) {
        listaPrestamos.add(prestamo);
        total += prestamo.getMonto();
        enMora = true;
        System.out.println("Se ha añadido el prestamo: " + prestamo);
    }
//Se paga deuda del total
    public void pagarDeuda(float cantidad) {
        if (cantidad <= 0) {
            System.out.println("Cantidad invalida.");
            return;
        }

        if (cantidad >= total) {
            total = 0;
            enMora = false;
            System.out.println("Deuda pagada completamente.");
        } else {
            total -= cantidad;
            System.out.println("Pago parcial realizado. Deuda restante: " + total);
        }
    }
//Calcula el total
    public void calcularTotal() {
        float suma = 0;
        for (Prestamo prestamo : listaPrestamos) {
            suma += prestamo.getMonto();
        }
        total = suma;
        System.out.println("El total de los prestamos es: " + total);
    }

    @Override
    public String toString() {
        return String.format("Total: %.2f | En mora: %b", 
                total, enMora);
    }
}
