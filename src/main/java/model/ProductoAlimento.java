package model;

public class ProductoAlimento extends Producto {
    private int aporteCalorico;

    public ProductoAlimento(String id, String descripcion, double precio, int aporteCalorico) {
        super(id, descripcion, precio, "Alimento");
        this.aporteCalorico = aporteCalorico;
    }

    @Override
    public ProductoAlimento clone() {
        return (ProductoAlimento) super.clone();  // Usamos el método de clonación de la superclase
    }

    @Override
    public String obtenerCaracteristica() {
        return "Aporte calórico: " + aporteCalorico + " kcal";
    }

    public int getAporteCalorico() {
        return aporteCalorico;
    }
}
