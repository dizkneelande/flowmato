package com.example.flowmato.controller;

import com.example.flowmato.HelloApplication;
import com.example.flowmato.model.Notification;
import com.example.flowmato.model.Profile;
import com.example.flowmato.model.SqliteProfileDAO;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
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
    @FXML

    private SqliteProfileDAO profileDAO = new SqliteProfileDAO();
    private NotificationController notificationController;

    @FXML
    protected void SignUpSubmit(ActionEvent event) {
        String email = emailField.getText();
        String password = passwordField.getText();
        String preferredName = preferredNameField.getText();

        if (!isValidEmail(email)) {
            notificationController.notify(new Notification("alert", "Invalid email format.", "TOP", 3000));
            return;
        }

        if (!isValidPassword(password)) {
            notificationController.notify(new Notification("alert", "Password must be at least 6 characters.", "TOP", 3000));
            return;
        }

        if (profileDAO.emailExists(email)) {
            notificationController.notify(new Notification("alert", "Email already in use. Please use a different email.", "TOP", 3000));
            return;
        }

        Profile newProfile = new Profile(email, password, preferredName);
        profileDAO.saveNewProfile(newProfile);

        notificationController.notify(new Notification("banner", "Profile created successfully!","TOP", 3000));
        clearFields();
    }
    //check valid signup input
    private boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return email.matches(emailRegex);
    }
    private boolean isValidPassword(String password) {
        return password.length() >= 6;
    }
    //clear signup fields after profile creation
    private void clearFields() {
        emailField.setText("");
        passwordField.setText("");
        preferredNameField.setText("");
    }

    @FXML
    protected void Back(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/com/example/flowmato/options-view.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void ToSignIn(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/com/example/flowmato/login-view.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void initialize() {
        notificationController = HelloApplication.notificationController;
    }
}
