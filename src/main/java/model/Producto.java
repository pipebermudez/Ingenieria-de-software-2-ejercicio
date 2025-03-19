package model;

public abstract class Producto implements Cloneable {
    protected String id;
    protected String descripcion;
    protected double precio;
    protected String tipo;

    public Producto(String id, String descripcion, double precio, String tipo) {
        this.id = id;
        this.descripcion = descripcion;
        this.precio = precio;
        this.tipo = tipo;
    }

    public abstract String obtenerCaracteristica();

    @Override
    public Producto clone() {
        try {
            Producto clon = (Producto) super.clone();
            clon.setId("P" + System.currentTimeMillis());  // Generamos un nuevo ID
            return clon;
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException("Error al clonar el producto", e);
        }
    }

    public void setId(String nuevoId) {
        this.id = nuevoId;
    }

    // Getters y Setters
    public String getId() {
        return id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public double getPrecio() {
        return precio;
    }

    public String getTipo() {
        return tipo;
    }
}
