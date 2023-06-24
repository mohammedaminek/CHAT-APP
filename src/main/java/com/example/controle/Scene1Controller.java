package com.example.controle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class Scene1Controller {
    @FXML
    private TextField usernameID;
    @FXML
    private PasswordField passwordID;

    @FXML
    protected void onLogin() throws IOException {
        if(usernameID.getText().equals("a")&&passwordID.getText().equals("a")){
            Stage s=(Stage) usernameID.getScene().getWindow();
            FXMLLoader fx = new FXMLLoader(MainApp.class.getResource("Scene2.fxml"));
            Scene scene = new Scene(fx.load());
            s.setScene(scene);
        }else{
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Authentication error");
            alert.setHeaderText("change username or password");
            alert.show();
        }

    }
}
