package com.example.flowmato.controller;

import com.example.flowmato.model.SqliteProfileDAO;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Label;
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
    private Label errorLabel;

    @FXML
    protected void Login(ActionEvent event) {
        SqliteProfileDAO profileDAO = new SqliteProfileDAO(); //maybe use dependency injection like in week8 reading
        boolean isValid = profileDAO.validateLogin(emailField.getText(), passwordField.getText());

        if (isValid) {
            //System.out.println("login successful");
            try {
                Parent mainAppRoot = FXMLLoader.load(getClass().getResource("/com/example/flowmato/main-application-view.fxml"));
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(new Scene(mainAppRoot));
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            // System.out.println("invalid credentials");
            errorLabel.setText("Invalid credentials, please try again.");
            errorLabel.setVisible(true);
        }
    }
    @FXML
    private void clearErrorMessage() {
        errorLabel.setVisible(false);
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
