package controller;

import java.sql.SQLException;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Producto;
import services.AlimentoFactory;
import services.ElectronicoFactory;
import services.ProductoDAO;
import services.ProductoFactory;

public class ProductoController {

    @FXML
    private ComboBox<String> cmbTipoProducto;

    @FXML
    private TextField txtDescripcion;

    @FXML
    private TextField txtPrecio;

    @FXML
    private TextField txtCaracteristica;

    @FXML
    private TableView<Producto> tblProductos;

    @FXML
    private TableColumn<Producto, String> colId;

    @FXML
    private TableColumn<Producto, String> colDescripcion;

    @FXML
    private TableColumn<Producto, Double> colPrecio;

    @FXML
    private TableColumn<Producto, String> colCaracteristica;

    private final ProductoDAO productoDAO = new ProductoDAO();

    @FXML
    private void initialize() {
        cmbTipoProducto.getItems().addAll("Electrónico", "Alimento");

        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colDescripcion.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
        colPrecio.setCellValueFactory(new PropertyValueFactory<>("precio"));
        colCaracteristica.setCellValueFactory(cellData ->
            javafx.beans.binding.Bindings.createStringBinding(() ->
                cellData.getValue().obtenerCaracteristica()
            )
        );

        cmbTipoProducto.setOnAction(event -> cargarProductos());
        cargarProductos();
    }

    @FXML
    private void handleConsultar() {
        cargarProductos();
    }

    @FXML
    private void handleInsertar() {
        String id = "P" + System.currentTimeMillis();
        String descripcion = txtDescripcion.getText().trim();
        String precioTexto = txtPrecio.getText().trim();
        String tipo = cmbTipoProducto.getValue();
        String caracteristicaTexto = txtCaracteristica.getText().trim();

        if (descripcion.isEmpty() || precioTexto.isEmpty() || tipo == null || caracteristicaTexto.isEmpty()) {
            mostrarAlerta("Error", "Todos los campos son obligatorios.");
            return;
        }

        double precio;
        try {
            precio = Double.parseDouble(precioTexto);
        } catch (NumberFormatException e) {
            mostrarAlerta("Error", "El precio debe ser un número válido.");
            return;
        }

        ProductoFactory factory;
        Producto nuevoProducto = null;

        if ("Electrónico".equals(tipo)) {
            factory = new ElectronicoFactory();
        } else if ("Alimento".equals(tipo)) {
            factory = new AlimentoFactory();
        } else {
            mostrarAlerta("Error", "Tipo de producto no válido.");
            return;
        }

        try {
            nuevoProducto = factory.crearProducto(id, descripcion, precio, caracteristicaTexto);
            productoDAO.insertar(nuevoProducto);
            mostrarAlerta("Éxito", "Producto insertado correctamente.");
            cargarProductos();
            limpiarCampos();
        } catch (SQLException e) {
            mostrarAlerta("Error", "No se pudo insertar el producto: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            mostrarAlerta("Error", e.getMessage());
        }
    }

    @FXML
    private void handleEliminar() {
        Producto seleccionado = tblProductos.getSelectionModel().getSelectedItem();
        if (seleccionado == null) {
            mostrarAlerta("Error", "Seleccione un producto para eliminar.");
            return;
        }

        try {
            productoDAO.eliminar(seleccionado.getId());
            mostrarAlerta("Éxito", "Producto eliminado correctamente.");
            cargarProductos();
        } catch (SQLException e) {
            mostrarAlerta("Error", "No se pudo eliminar el producto: " + e.getMessage());
        }
    }

    
    
    @FXML
    private void handleDuplicar() {
        Producto seleccionado = tblProductos.getSelectionModel().getSelectedItem();
        if (seleccionado == null) {
            mostrarAlerta("Error", "Seleccione un producto para duplicar.");
            return;
        }

        try {
            Producto nuevoProducto = seleccionado.clone(); // Aplicamos Prototype
            productoDAO.insertar(nuevoProducto); // Guardamos en la BD
            mostrarAlerta("Éxito", "Producto duplicado correctamente.");
            cargarProductos();
        } catch (Exception e) {
            mostrarAlerta("Error", "Error al duplicar el producto: " + e.getMessage());
        }
    }


    private void cargarProductos() {
        String tipoSeleccionado = cmbTipoProducto.getValue();
        if (tipoSeleccionado != null) {
            try {
                List<Producto> productos = productoDAO.obtenerPorTipo(tipoSeleccionado);
                ObservableList<Producto> listaProductos = FXCollections.observableArrayList(productos);
                tblProductos.setItems(listaProductos);
            } catch (SQLException e) {
                mostrarAlerta("Error", "No se pudo cargar los productos: " + e.getMessage());
            }
        }
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alerta = new Alert(titulo.equals("Éxito") ? Alert.AlertType.INFORMATION : Alert.AlertType.ERROR);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }

    private void limpiarCampos() {
        txtDescripcion.clear();
        txtPrecio.clear();
        txtCaracteristica.clear();
    }
}
