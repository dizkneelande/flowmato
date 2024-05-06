package com.example.flowmato.controller;

import com.example.flowmato.HelloApplication;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import java.io.IOException;

public class OptionsController {

    private NotificationController notificationController;

    @FXML
    protected void CreateNewProfile(javafx.event.ActionEvent event) throws IOException {
        Parent signUpRoot = FXMLLoader.load(getClass().getResource("/com/example/flowmato/signup-view.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(signUpRoot));
        stage.show();
    }

    @FXML
    protected void LogIn(javafx.event.ActionEvent event) throws IOException {
        Parent loginRoot = FXMLLoader.load(getClass().getResource("/com/example/flowmato/login-view.fxml")); // Ensure you have a login-view.fxml
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(loginRoot));
        stage.show();
    }

    @FXML
    public void initialize() {
        notificationController = HelloApplication.notificationController;
    }
}
