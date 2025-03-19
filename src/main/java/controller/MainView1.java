package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainView1 {

    @FXML
    private void abrirGestionProductos() {
        abrirVentana("/view/Productos.fxml", "Gestión de Productos");
    }

    @FXML
    private void abrirGestionClientes() {
        abrirVentana("/view/Cliente.fxml", "Gestión de Clientes");
    }

    private void abrirVentana(String rutaFXML, String titulo) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(rutaFXML));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle(titulo);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error cargando " + rutaFXML);
        }
    }
}
