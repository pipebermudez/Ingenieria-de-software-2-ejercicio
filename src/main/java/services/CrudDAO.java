package services;

import java.sql.SQLException;
import java.util.List;
public interface CrudDAO<T> {
    void insertar(T t) throws SQLException;
    void actualizar(T t) throws SQLException;
    void eliminar(String id) throws SQLException;
    T obtenerPorId(String id) throws SQLException;
    List<T> obtenerTodos() throws SQLException;
    
    List<T> obtenerPorTipo(String tipo) throws SQLException;
}