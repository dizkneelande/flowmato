package com.example.flowmato.controller;

import com.example.flowmato.model.Profile;
import com.example.flowmato.model.SqliteProfileDAO;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class WelcomeController {

    @FXML
    private TextField usernameField;

    @FXML
    private TextField passwordField;

    private SqliteProfileDAO profileDAO;

    public WelcomeController() {
        profileDAO = new SqliteProfileDAO();
    }

    @FXML
    protected void createProfile() {
        String username = usernameField.getText();
        String password = passwordField.getText();
        Profile profile = new Profile(0, username, password);
        profileDAO.createProfile(profile);
        System.out.println("Profile created with username: " + username);
    }

    @FXML
    protected void selectProfile() {
        String username = usernameField.getText();
        String password = passwordField.getText();
        Profile profile = profileDAO.selectProfile(username, password);
        if (profile != null) {
            System.out.println("Profile selected with username: " + username);
        } else {
            System.out.println("Profile not found with username: " + username);
        }
    }
}
