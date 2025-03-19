module co.edu.poli.EJERCICIO_POLI {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens co.edu.poli.EJERCICIO_POLI to javafx.fxml;
    opens application to javafx.graphics, javafx.fxml;
    opens controller to javafx.fxml;
    opens view to javafx.fxml;
    
    exports co.edu.poli.EJERCICIO_POLI;
    exports application;
    exports controller;
    exports model;
    exports services;
    exports view;
}