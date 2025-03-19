package model;

public class ProductoElectronico extends Producto {
    private String voltajeEntrada;

    public ProductoElectronico(String id, String descripcion, double precio, String voltajeEntrada) {
        super(id, descripcion, precio, "Electrónico");  // Fijamos tipo como "Electrónico"
        this.voltajeEntrada = voltajeEntrada;
    }

    @Override
    public ProductoElectronico clone() {
        return (ProductoElectronico) super.clone();  // Usamos el método de clonación de la superclase
    }

    @Override
    public String obtenerCaracteristica() {
        return "Voltaje de entrada: " + voltajeEntrada + "V";
    }

    public String getVoltajeEntrada() {
        return voltajeEntrada;
    }
}
