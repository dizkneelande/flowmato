package com.example.flowmato.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class SqliteProfileDAO {
    private Connection connection;

    public SqliteProfileDAO() {
        connection = SqliteConnection.getInstance();
        createProfileTable();
    }

    private void createProfileTable() {
        try {
            Statement statement = connection.createStatement();
            String query = "CREATE TABLE IF NOT EXISTS profiles ("
                    + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + "username VARCHAR NOT NULL,"
                    + "password VARCHAR NOT NULL"
                    + ")";
            statement.execute(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createProfile(Profile profile) {
        try {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO profiles (username, password) VALUES (?, ?)");
            statement.setString(1, profile.getUsername());
            statement.setString(2, profile.getPassword());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Profile selectProfile(String username, String password) {
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM profiles WHERE username = ? AND password = ?");
            statement.setString(1, username);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int id = resultSet.getInt("id");
                String storedUsername = resultSet.getString("username");
                String storedPassword = resultSet.getString("password");
                return new Profile(id, storedUsername, storedPassword);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // TODO: implement updateProfile and deleteProfile
    // TODO: close connection when in main view i guess

}
