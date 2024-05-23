package com.example.flowmato.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class GuideController {
    @FXML
    Button exitGuideButton;
    @FXML
    protected void exitGuide() {
        Stage stage = (Stage) exitGuideButton.getScene().getWindow();
        stage.close();
    }
}
