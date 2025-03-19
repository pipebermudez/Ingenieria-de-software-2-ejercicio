package services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import model.Producto;
import model.ProductoAlimento;
import model.ProductoElectronico;

public class ProductoDAO implements DAO<Producto> {

    @Override
    public void insertar(Producto producto) throws SQLException {
        String sql = "INSERT INTO Producto (id, descripcion, precio, tipo, voltaje_entrada, aporte_calorico) VALUES (?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, producto.getId());
            stmt.setString(2, producto.getDescripcion());
            stmt.setDouble(3, producto.getPrecio());
            stmt.setString(4, producto.getTipo());

            if (producto instanceof ProductoElectronico) {
                stmt.setString(5, ((ProductoElectronico) producto).getVoltajeEntrada());
                stmt.setNull(6, Types.INTEGER);
            } else if (producto instanceof ProductoAlimento) {
                stmt.setNull(5, Types.VARCHAR);
                stmt.setInt(6, ((ProductoAlimento) producto).getAporteCalorico());
            } else {
                stmt.setNull(5, Types.VARCHAR);
                stmt.setNull(6, Types.INTEGER);
            }

            stmt.executeUpdate();
        }
    }

    @Override
    public Producto obtenerPorId(String id) throws SQLException {
        String sql = "SELECT * FROM Producto WHERE id = ?";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapearProducto(rs);
                }
            }
        }
        return null;
    }

    @Override
    public void eliminar(String id) throws SQLException {
        String sql = "DELETE FROM Producto WHERE id = ?";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, id);
            stmt.executeUpdate();
        }
    }

    @Override
    public List<Producto> obtenerPorTipo(String tipo) throws SQLException {
        List<Producto> productos = new ArrayList<>();
        String sql = "SELECT * FROM Producto WHERE tipo = ?";

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, tipo);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    productos.add(mapearProducto(rs));
                }
            }
        }
        return productos;
    }

    @Override
    public List<Producto> obtenerTodos() throws SQLException {
        List<Producto> productos = new ArrayList<>();
        String sql = "SELECT * FROM Producto";

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                productos.add(mapearProducto(rs));
            }
        }

        return productos;
    }

    @Override
    public int count() throws SQLException {
        String sql = "SELECT COUNT(*) FROM Producto";

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);  // Retorna el número de productos
            }
        }

        return 0;
    }

    private Producto mapearProducto(ResultSet rs) throws SQLException {
        String id = rs.getString("id");
        String descripcion = rs.getString("descripcion");
        double precio = rs.getDouble("precio");
        String tipo = rs.getString("tipo");

        if ("Electrónico".equalsIgnoreCase(tipo)) {
            return new ProductoElectronico(id, descripcion, precio, rs.getString("voltaje_entrada"));
        } else if ("Alimento".equalsIgnoreCase(tipo)) {
            return new ProductoAlimento(id, descripcion, precio, rs.getInt("aporte_calorico"));
        } else {
            throw new SQLException("Tipo de producto desconocido: " + tipo);
        }
    }

    // Método de duplicación
    public Producto duplicarProducto(Producto producto) throws SQLException {
        String nuevoId = "P" + System.currentTimeMillis(); // Generamos un nuevo ID único
        String sql = "INSERT INTO Producto (id, descripcion, precio, tipo, voltaje_entrada, aporte_calorico) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nuevoId);
            stmt.setString(2, producto.getDescripcion());
            stmt.setDouble(3, producto.getPrecio());
            stmt.setString(4, producto.getTipo());

            if (producto instanceof ProductoElectronico) {
                stmt.setString(5, ((ProductoElectronico) producto).getVoltajeEntrada());
                stmt.setNull(6, Types.INTEGER);
            } else if (producto instanceof ProductoAlimento) {
                stmt.setNull(5, Types.VARCHAR);
                stmt.setInt(6, ((ProductoAlimento) producto).getAporteCalorico());
            } else {
                stmt.setNull(5, Types.VARCHAR);
                stmt.setNull(6, Types.INTEGER);
            }

            int filasAfectadas = stmt.executeUpdate();
            if (filasAfectadas > 0) {
                if (producto instanceof ProductoElectronico) {
                    return new ProductoElectronico(nuevoId, producto.getDescripcion(), producto.getPrecio(), producto.getTipo(), ((ProductoElectronico) producto).getVoltajeEntrada());
                } else if (producto instanceof ProductoAlimento) {
                    return new ProductoAlimento(nuevoId, producto.getDescripcion(), producto.getPrecio(), producto.getTipo(), ((ProductoAlimento) producto).getAporteCalorico());
                }
            }
        }
        return null;
    }

	@Override
	public void actualizar(Producto t) throws SQLException {
		// TODO Auto-generated method stub
		
	}
}
