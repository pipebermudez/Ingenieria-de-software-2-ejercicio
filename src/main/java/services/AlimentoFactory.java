package services;

import model.Producto;
import model.ProductoAlimento;

public class AlimentoFactory implements ProductoFactory {
    @Override
    public Producto crearProducto(String id, String descripcion, double precio, String caracteristica) {
        try {
            int aporteCalorico = Integer.parseInt(caracteristica);
            return new ProductoAlimento(id, descripcion, precio, aporteCalorico);  // Se pasa aporteCalorico
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("El aporte calórico debe ser un número válido.");
        }
    }
}
