package com.example.controle;

import com.example.controle.login.HelloApplication;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class MainApp extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        // Create the database if it does not exist
        String url = "jdbc:mysql://localhost:3306/";
        String dbName = "Chat_Db";
        String username = "root";
        String password = "";
        Statement statement = null;
        String createTableQuery = "CREATE TABLE IF NOT EXISTS users ("
                + "id INT PRIMARY KEY AUTO_INCREMENT,"
                + "username VARCHAR(255) NOT NULL,"
                + "email VARCHAR(255) NOT NULL,"
                + "password VARCHAR(255) NOT NULL"
                + ")";
        try {
            Connection connection = DriverManager.getConnection(url, username, password);
             statement = connection.createStatement();

            // Create the database if it does not exist
            String createDatabaseQuery = "CREATE DATABASE IF NOT EXISTS " + dbName;
            statement.executeUpdate(createDatabaseQuery);

            // Connect to the specified database
            connection = DriverManager.getConnection(url + dbName, username, password);
            statement = connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            statement.executeUpdate(createTableQuery);
        } catch (SQLException e) {
            e.printStackTrace();
        }



        FXMLLoader fxmlLoader = new FXMLLoader();
        URL ur = getClass().getResource("Scene1.fxml");
        fxmlLoader.setLocation(ur);
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("CHAT App!");
        stage.setScene(scene);
        stage.show();

    }
    public static void main(String[] args) {
        launch();
    }
}