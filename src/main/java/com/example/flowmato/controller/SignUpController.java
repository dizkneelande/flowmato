package com.example.flowmato.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import com.example.flowmato.model.SqliteProfileDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Node;

public class SignUpController {

    @FXML
    private TextField emailField;
    @FXML
    private TextField passwordField;
    @FXML
    private TextField preferredNameField;

    private SqliteProfileDAO profileDAO = new SqliteProfileDAO();

    @FXML
    protected void SignUpSubmit(ActionEvent event) {
        String email = emailField.getText();
        String password = passwordField.getText();
        String preferredName = preferredNameField.getText();
        profileDAO.saveNewProfile(email, password, preferredName);
    }

    @FXML
    protected void Back(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/com/example/flowmato/welcome-view.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
