/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyectojava;


//Clase Cuenta que contiene el balance, el tipo de interes y si tiene un prestamo pendiente.
//1 Cuenta por Cliente
public class Cuenta {
    
    //Declaracion de atributos de la clase Cuenta
    private String nombreCuenta;
    private float balance;
    private float interes;
    
    
    //Constructor de la clase Cuenta
    public Cuenta(String nombreCuenta, float balance, float interes) {
        this.nombreCuenta = nombreCuenta;
        this.balance = balance;
        this.interes = interes;
    }
    
    
    
    //Metodos get y set de la clase Cuenta
    public String getNombreCuenta(){
        
        return nombreCuenta;
    }
    
    public void setNombreCuenta(String nombreCuenta){
        this.nombreCuenta = nombreCuenta;
    }
    
    public float getBalance() {
        return balance;
    }
    
    public void setBalance(float balance) {
        this.balance = balance;
    }
    
    public float getInteres() {
        return interes;
    }
    
    public void setInteres(float interes) {
        this.interes = interes;
    }
    
    
    
    //Metodo para depositar una cantidad al balance de una cuenta
    public void depositar(float cantidad){
        
        try{
            
            this.balance += cantidad;
            
        } catch (Exception e){
            
            System.out.println("Error al depositar dinero en la cuenta.");
        }
    }
    
    //Metodo para retirar una cantidad al balance de una cuenta
    public void retirar(float cantidad){
        
        try{
            
            if ((getBalance() - cantidad) < 0) {
                
                System.out.println("No dispone del balance suficiente para realizar esta operacion.");
            }
            
            else{
                
                this.balance -= cantidad;
            }
            
        } catch (Exception e){
            
            System.out.println("Error al retirar dinero de la cuenta.");
        }
    }
    
    //Metodo que aplica una tasa de interes al balance de una cuenta
    public void aplicarTasaIntereses(){
        
        setBalance((getBalance() - (getBalance()*interes)/100));
    }
    

    
    //Metodo para mostrar los datos de la cuenta
    @Override
    public String toString() {
        
        return (
                "Nombre de la cuenta: " + getNombreCuenta() + "\n"
                + "Balance: " + getBalance() + "\n"
                + "Interes: " + getInteres() + "\n"
        );
    }  
}
