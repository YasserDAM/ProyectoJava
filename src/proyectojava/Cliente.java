/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyectojava;


/**
 *
 * @author Usuario
 */

//Clase que almacena a los diferentes clientes que se logean en el banco, sus datos, cuentas y deudas
public class Cliente {
    
    //Atributos
    private String nombre;
    private int edad;
    private String estadoCivil;
    private Cuenta cuenta;
    private Deuda deuda;
    
    //Constructores
    public Cliente(String nombre, int edad, String estadoCivil, Cuenta cuenta, Deuda deuda) {
        
        this.nombre = nombre;
        this.edad = edad;
        this.estadoCivil = estadoCivil;
        this.cuenta = cuenta;
        this.deuda = deuda;
    }

    
    public Cliente() {}
    
    //Metodos set y get de los atributos
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String getEstadoCivil() {
        return estadoCivil;
    }

    public void setEstadoCivil(String estadoCivil) {
        this.estadoCivil = estadoCivil;
    }

    public Cuenta getCuenta() {
        return cuenta;
    }

    public void setCuenta(Cuenta cuenta) {
        this.cuenta = cuenta;
    }

    public Deuda getDeuda() {
        return deuda;
    }

    public void setDeuda(Deuda deuda) {
        this.deuda = deuda;
    }
    
    //Muestra la informacion del cliente
    @Override
    public String toString() {
        return String.format("%s %d %s %s %.2f %.2f %.2f %b",
                nombre,
                edad,
                estadoCivil,
                cuenta.getNombreCuenta(),
                cuenta.getBalance(),
                cuenta.getInteres(),
                deuda.getTotal(),
                deuda.isEnMora()
        );
    }
    
    //Patron para construir Cliente
    public static class Builder {
        
        private String nombre;
        private int edad;
        private String estadoCivil;
        private Cuenta cuenta;
        private Deuda deuda;

        public Builder setNombre(String nombre) {
            
            this.nombre = nombre;
            return this;
        }

        public Builder setEdad(int edad) {
            
            this.edad = edad;
            return this;
        }

        public Builder setEstadoCivil(String estadoCivil) {
            
            this.estadoCivil = estadoCivil;
            return this;
        }

        public Builder setCuenta(Cuenta cuenta) {
            
            this.cuenta = cuenta;
            return this;
        }

        public Builder setDeuda(Deuda deuda) {
            
            this.deuda = deuda;
            return this;
        }

        public Cliente build() {
            
            return new Cliente(nombre, edad, estadoCivil, cuenta, deuda);
        }
    }
}
