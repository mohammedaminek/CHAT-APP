package com.example.controle;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.*;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;

public class Scene2Controller {

    @FXML
    private TextField HostID;
    @FXML
    private TextField PortID;
    @FXML
    private ListView<String> testView;
    @FXML
    private ListView<String> ContactsID;
    @FXML
    private TextField  messageID;


    PrintWriter pw;
    private ObservableList<String> messages;
    private ObservableList<String> contacts;

    @FXML
    public void initialize() {
        messages = FXCollections.observableArrayList();
        testView.setItems(messages);

        // Other initialization code

        ContactsID.setOnMouseClicked(event -> {
            String selectedClient = ContactsID.getSelectionModel().getSelectedItem();
            if (selectedClient != null) {
                StringBuilder number = new StringBuilder();
                for (char c : selectedClient.toCharArray()) {
                    if (Character.isDigit(c)) {
                        number.append(c);
                    }
                }
                messageID.setText(number+"=>");
            }
        });
    }

    @FXML
    public void onConnect() throws Exception {
    String Host=HostID.getText();
    int Port=Integer.parseInt(PortID.getText());
    Socket socket=new Socket(Host,Port);
        InputStream is = socket.getInputStream();
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);
        OutputStream os =socket.getOutputStream();
         pw = new PrintWriter(os, true);
        String delimiter = "#";
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(true) {
                try {
                    String reponse= br.readLine();

/////////////////////////////////////////

                    // Split the received data using the delimiter
                   // String[] parts = reponse.split(delimiter);
                    // Extract the prompt message and the JSON string
                   // String promptMessage = parts[0];
                    //String Clients = parts[1];


                 //   System.out.println(parts);
//////////////////////////////////////////

                    /////////////
                   Platform.runLater(()->{
                       if(reponse.contains("#client")){
                           ContactsID.getItems().add(reponse);
                           ContactsID.refresh();
                       }
                       else{

                           testView.getItems().add(reponse);
                       }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
                }
            }
        }).start();
    }

    @FXML
    public void onSubmit() {
        String message=messageID.getText();
        messages.add("You: " +message);
        messageID.clear();
        ContactsID.getItems().clear();
        ContactsID.refresh();
        pw.println(message);
    }
}
