module com.example.controle {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;
    requires java.sql;
    opens com.example.controle to com.google.gson, javafx.fxml;


    exports com.example.controle;
    exports com.example.controle.login;
    opens com.example.controle.login to com.google.gson, javafx.fxml;
}
