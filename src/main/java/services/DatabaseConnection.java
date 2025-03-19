package services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class DatabaseConnection {

    private static DatabaseConnection instance;
    private Connection connection;

    private DatabaseConnection() throws SQLException {
        ResourceBundle bundle = ResourceBundle.getBundle("config"); 
        String url = bundle.getString("db.url");
        String username = bundle.getString("db.username");
        String password = bundle.getString("db.password");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            this.connection = DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException e) {
            throw new SQLException("Driver JDBC no encontrado: " + e.getMessage());
        }
    }

    public static DatabaseConnection getInstance() throws SQLException {
        if (instance == null || instance.getConnection().isClosed()) {
            instance = new DatabaseConnection();
        }
        return instance;
    }

    public Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            ResourceBundle bundle = ResourceBundle.getBundle("config");
            String url = bundle.getString("db.url");
            String username = bundle.getString("db.username");
            String password = bundle.getString("db.password");

            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                connection = DriverManager.getConnection(url, username, password);
            } catch (ClassNotFoundException e) {
                throw new SQLException("Driver JDBC no encontrado: " + e.getMessage());
            }
        }
        return connection;
    }
}
