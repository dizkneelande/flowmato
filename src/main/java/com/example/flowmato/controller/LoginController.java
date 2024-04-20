package com.example.flowmato.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import java.io.IOException;

public class LoginController {

    @FXML
    private TextField emailField;
    @FXML
    private PasswordField passwordField;

    @FXML
    protected void Login(ActionEvent event) {
        //login logic goes here (maybe get jozef to help)
        System.out.println("logging in as: " + emailField.getText());
    }

    @FXML
    protected void Back(ActionEvent event) {
        try {
            Parent optionsRoot = FXMLLoader.load(getClass().getResource("/com/example/flowmato/options-view.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(optionsRoot));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
