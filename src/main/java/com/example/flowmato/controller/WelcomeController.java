package com.example.flowmato.controller;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import java.io.IOException;

public class WelcomeController {

    @FXML
    protected void GetStarted(javafx.event.ActionEvent event) throws IOException {
        Parent optionsRoot = FXMLLoader.load(getClass().getResource("/com/example/flowmato/options-view.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(optionsRoot));
        stage.show();
    }
}
