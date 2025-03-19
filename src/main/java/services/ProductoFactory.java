package services;

import model.Producto;

public interface ProductoFactory {
    Producto crearProducto(String id, String descripcion, double precio, String caracteristica);
}
