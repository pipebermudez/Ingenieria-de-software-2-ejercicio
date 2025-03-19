package controller;

import java.sql.SQLException;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Cliente;
import services.ClienteDAO;

public class ClienteController {

    @FXML
    private TextField txtId;
    @FXML
    private TextField txtNombre;
    @FXML
    private TableView<Cliente> tablaClientes;
    @FXML
    private TableColumn<Cliente, String> colId;
    @FXML
    private TableColumn<Cliente, String> colNombre;

    private ClienteDAO clienteDAO;

    public ClienteController() {
        try {
            clienteDAO = new ClienteDAO();
        } catch (SQLException e) {
            mostrarAlerta("Error", "No se pudo conectar con la base de datos.");
        }
    }

    @FXML
    public void initialize() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        cargarClientes();
    }

    @FXML
    private void agregarCliente(ActionEvent event) {
        String id = txtId.getText().trim();
        String nombre = txtNombre.getText().trim();

        if (id.isEmpty() || nombre.isEmpty()) {
            mostrarAlerta("Error", "Todos los campos son obligatorios.");
            return;
        }

        Cliente cliente = new Cliente(id, nombre);
        try {
            if (clienteDAO.insertarCliente(cliente)) {
                mostrarAlerta("Éxito", "Cliente agregado correctamente.");
                cargarClientes();
                limpiarCampos();
            } else {
                mostrarAlerta("Error", "No se pudo agregar el cliente.");
            }
        } catch (SQLException e) {
            mostrarAlerta("Error", "Error en la base de datos: " + e.getMessage());
        }
    }

    @FXML
    private void actualizarCliente(ActionEvent event) {
        Cliente clienteSeleccionado = tablaClientes.getSelectionModel().getSelectedItem();
        if (clienteSeleccionado == null) {
            mostrarAlerta("Error", "Selecciona un cliente para actualizar.");
            return;
        }

        String nombreNuevo = txtNombre.getText().trim();
        if (nombreNuevo.isEmpty()) {
            mostrarAlerta("Error", "El nombre no puede estar vacío.");
            return;
        }

        clienteSeleccionado.setNombre(nombreNuevo);
        try {
            if (clienteDAO.actualizarCliente(clienteSeleccionado)) {
                mostrarAlerta("Éxito", "Cliente actualizado correctamente.");
                cargarClientes();
                limpiarCampos();
            } else {
                mostrarAlerta("Error", "No se pudo actualizar el cliente.");
            }
        } catch (SQLException e) {
            mostrarAlerta("Error", "Error en la base de datos: " + e.getMessage());
        }
    }

    @FXML
    private void eliminarCliente(ActionEvent event) {
        Cliente clienteSeleccionado = tablaClientes.getSelectionModel().getSelectedItem();
        if (clienteSeleccionado == null) {
            mostrarAlerta("Error", "Selecciona un cliente para eliminar.");
            return;
        }

        try {
            if (clienteDAO.eliminarCliente(clienteSeleccionado.getId())) {
                mostrarAlerta("Éxito", "Cliente eliminado correctamente.");
                cargarClientes();
                limpiarCampos();
            } else {
                mostrarAlerta("Error", "No se pudo eliminar el cliente.");
            }
        } catch (SQLException e) {
            mostrarAlerta("Error", "Error en la base de datos: " + e.getMessage());
        }
    }

    @FXML
    private void cargarClientes() {
        try {
            List<Cliente> lista = clienteDAO.obtenerClientes();
            ObservableList<Cliente> clientes = FXCollections.observableArrayList(lista);
            tablaClientes.setItems(clientes);
        } catch (SQLException e) {
            mostrarAlerta("Error", "No se pudieron cargar los clientes: " + e.getMessage());
        }
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    private void limpiarCampos() {
        txtId.clear();
        txtNombre.clear();
    }
}
