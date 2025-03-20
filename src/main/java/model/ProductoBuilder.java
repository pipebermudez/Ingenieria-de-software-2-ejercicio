package model;

public class ProductoBuilder {
    private String id;
    private String descripcion;
    private double precio;
    private String tipo;
    private String voltajeEntrada;
    private int aporteCalorico;

    public ProductoBuilder buildId(String id) {
        this.id = id;
        return this;
    }

    public ProductoBuilder buildDescripcion(String descripcion) {
        this.descripcion = descripcion;
        return this;
    }

    public ProductoBuilder buildPrecio(double precio) {
        this.precio = precio;
        return this;
    }

    public ProductoBuilder buildTipo(String tipo) {
        this.tipo = tipo;
        return this;
    }

    public ProductoBuilder buildVoltajeEntrada(String voltajeEntrada) {
        this.voltajeEntrada = voltajeEntrada;
        return this;
    }

    public ProductoBuilder buildAporteCalorico(int aporteCalorico) {
        this.aporteCalorico = aporteCalorico;
        return this;
    }

    public Producto construct() {
        if ("Electrónico".equals(tipo)) {
            return new ProductoElectronico(id, descripcion, precio, voltajeEntrada);
        } else if ("Alimento".equals(tipo)) {
            return new ProductoAlimento(id, descripcion, precio, aporteCalorico);
        } else {
            throw new IllegalArgumentException("Tipo de producto no válido $.");
        }
    }
}
