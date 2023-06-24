module com.example.controle {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;
    opens com.example.controle to com.google.gson, javafx.fxml;


    exports com.example.controle;
}
