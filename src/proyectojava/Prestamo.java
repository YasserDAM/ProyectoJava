package proyectojava;

/**
 * Clase que representa un prestamo solicitado por un cliente. 
 * Contiene informacion sobre el cliente, el monto, la tasa de interes y el plazo.
 */
public class Prestamo {
    private String cliente; // Nombre del cliente que solicito el prestamo
    private float monto; // Monto total del prestamo
    private float tasaInteres; // Tasa de interes anual en porcentaje
    private int plazoEnMeses; // Plazo del prestamo en meses

    /**
     * Constructor con todos los campos.
     * @param cliente nombre del cliente asociado
     * @param monto monto del prestamo
     * @param tasaInteres tasa de interes anual en porcentaje
     * @param plazoEnMeses plazo del prestamo en meses
     */
    public Prestamo(String cliente, float monto, float tasaInteres, int plazoEnMeses) {
        this.cliente = cliente;
        this.monto = monto;
        this.tasaInteres = tasaInteres;
        this.plazoEnMeses = plazoEnMeses;
    }

    // Getters
    public String getCliente() {
        return cliente;
    }

    public float getMonto() {
        return monto;
    }

    public float getInteres() {
        return tasaInteres;
    }

    public int getPlazo() {
        return plazoEnMeses;
    }

    // Setters
    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public void setMonto(float monto) {
        this.monto = monto;
    }

    public void setTasaInteres(float tasaInteres) {
        this.tasaInteres = tasaInteres;
    }

    public void setPlazoEnMeses(int plazoEnMeses) {
        this.plazoEnMeses = plazoEnMeses;
    }

    /**
     * Calcula la cuota mensual utilizando la formula de amortizacion si hay interes,
     * o una division simple si la tasa es 0.
     * @return cuota mensual estimada
     */
    public float calcularCuotaMensual() {
        if (tasaInteres == 0 || plazoEnMeses == 0) return monto / plazoEnMeses;

        float tasaMensual = tasaInteres / 100 / 12;
        return (float)((monto * tasaMensual) / (1 - Math.pow(1 + tasaMensual, -plazoEnMeses)));
    }

    /**
     * Representacion en texto del prestamo.
     * @return informacion formateada del prestamo
     */
    @Override
    public String toString() {
        return String.format("Cliente: %s | Monto: %.2f | Interes: %.2f%% | Plazo: %d meses",
                cliente, monto, tasaInteres, plazoEnMeses);
    }
}
