package com.example.controle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;


public class HelloController {

    // Strings which hold css elements to easily re-use in the application
    protected
    String successMessage = String.format("-fx-text-fill: GREEN;");
    String errorMessage = String.format("-fx-text-fill: RED;");
    String errorStyle = String.format("-fx-border-color: RED; -fx-border-width: 2; -fx-border-radius: 5;");
    String successStyle = String.format("-fx-border-color: #A9A9A9; -fx-border-width: 2; -fx-border-radius: 5;");

    // Import the application's controls
    @FXML
    private Label invalidLoginCredentials;
    @FXML
    private Label invalidSignupCredentials;
    @FXML
    private Button cancelButton;
    @FXML
    private TextField usernameID;
    @FXML
    private TextField passwordID;
    @FXML
    private TextField signUpUsernameTextField;
    @FXML
    private TextField signUpEmailTextField;
    @FXML
    private TextField signUpPasswordPasswordField;
    @FXML
    private TextField signUpRepeatPasswordPasswordField;
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

    // Creation of methods which are activated on events in the forms
    @FXML
    protected void onCancelButtonClick() {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

   /* @FXML
    protected void onLoginButtonClick() throws IOException {

        if (usernameID.getText().isBlank() || passwordID.getText().isBlank()) {
            invalidLoginCredentials.setText("The Login fields are required!");
            invalidLoginCredentials.setStyle(errorMessage);
            invalidSignupCredentials.setText("");

            if (usernameID.getText().isBlank()) {
                usernameID.setStyle(errorStyle);
            } else if (passwordID.getText().isBlank()) {
                passwordID.setStyle(errorStyle);
            }
        }  else if(usernameID.getText().equals("a")&&passwordID.getText().equals("a")){
            Stage s=(Stage) usernameID.getScene().getWindow();
            FXMLLoader fx = new FXMLLoader(MainApp.class.getResource("Scene2.fxml"));
            Scene scene = new Scene(fx.load());
            s.setScene(scene);
        } else {
            invalidLoginCredentials.setText("Login Successful!");
            invalidLoginCredentials.setStyle(successMessage);
            usernameID.setStyle(successStyle);
            passwordID.setStyle(successStyle);
            invalidSignupCredentials.setText("");
        }
    }
*/
   @FXML
   protected void onLoginButtonClick() {
       String username = usernameID.getText();
       String password = passwordID.getText();

       // Connect to the database
       String url = "jdbc:mysql://localhost:3306/Chat_Db";


       try (Connection connection = DriverManager.getConnection(url, "root", "")) {
           String query = "SELECT * FROM users WHERE username = ? AND password = ?";
           PreparedStatement statement = connection.prepareStatement(query);
           statement.setString(1, username);
           statement.setString(2, password);
           ResultSet resultSet = statement.executeQuery();

           if (resultSet.next()) {
               // Authentication successful
               invalidLoginCredentials.setText("Login Successful!");
               invalidLoginCredentials.setStyle(successMessage);
               usernameID.setStyle(successStyle);
               passwordID.setStyle(successStyle);
               invalidSignupCredentials.setText("");
               Stage s=(Stage) usernameID.getScene().getWindow();
               FXMLLoader fx = new FXMLLoader(MainApp.class.getResource("Scene2.fxml"));
               Scene scene = new Scene(fx.load());
               s.setScene(scene);

               // Proceed with the desired action, such as opening a new scene
           } else {
               // Authentication failed
               Alert alert = new Alert(Alert.AlertType.ERROR);
               alert.setTitle("Authentication error");
               alert.setHeaderText("Invalid username or password");
               alert.show();
           }
       } catch (SQLException e) {
           e.printStackTrace();
           // Handle the database connection error
       } catch (IOException e) {
           throw new RuntimeException(e);
       }
   }

    @FXML
    protected void onSignUpButtonClick() {
Dao dao=new Dao();

        if (signUpUsernameTextField.getText().isBlank() || signUpEmailTextField.getText().isBlank() || signUpPasswordPasswordField.getText().isBlank() || signUpRepeatPasswordPasswordField.getText().isBlank()) {
            invalidSignupCredentials.setText("Please fill in all fields!");
            invalidSignupCredentials.setStyle(errorMessage);
            invalidLoginCredentials.setText("");

            if (signUpUsernameTextField.getText().isBlank()) {
                signUpUsernameTextField.setStyle(errorStyle);
            } else if (signUpEmailTextField.getText().isBlank()) {
                signUpEmailTextField.setStyle(errorStyle);
            } else if (signUpPasswordPasswordField.getText().isBlank()) {
                signUpPasswordPasswordField.setStyle(errorStyle);
            } else if (signUpRepeatPasswordPasswordField.getText().isBlank()) {
                signUpRepeatPasswordPasswordField.setStyle(errorStyle);
            }
        } else if (signUpRepeatPasswordPasswordField.getText().equals(signUpPasswordPasswordField.getText())) {
            invalidSignupCredentials.setText("You are set!");
            invalidSignupCredentials.setStyle(successMessage);
            signUpUsernameTextField.setStyle(successStyle);
            signUpEmailTextField.setStyle(successStyle);
            signUpPasswordPasswordField.setStyle(successStyle);
            signUpRepeatPasswordPasswordField.setStyle(successStyle);
            invalidLoginCredentials.setText("");
            dao.signUp(signUpUsernameTextField.getText(),signUpEmailTextField.getText(),signUpRepeatPasswordPasswordField.getText());
        } else {
            invalidSignupCredentials.setText("The Passwords don't match!");
            invalidSignupCredentials.setStyle(errorMessage);
            signUpPasswordPasswordField.setStyle(errorStyle);
            signUpRepeatPasswordPasswordField.setStyle(errorStyle);
            invalidLoginCredentials.setText("");
        }
    }
}