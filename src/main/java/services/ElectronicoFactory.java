package services;

import model.Producto;
import model.ProductoElectronico;

public class ElectronicoFactory implements ProductoFactory {
    @Override
    public Producto crearProducto(String id, String descripcion, double precio, String caracteristica) {
        return new ProductoElectronico(id, descripcion, precio, caracteristica);
    }
}
