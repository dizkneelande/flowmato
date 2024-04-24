package com.example.flowmato.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SqliteProfileDAO {
    private final String url = "jdbc:sqlite:profiles.db";

    private Connection connect() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    public void initialiseDatabase() {
        String sql = "CREATE TABLE IF NOT EXISTS profiles (id INTEGER PRIMARY KEY AUTOINCREMENT, email TEXT NOT NULL, password TEXT NOT NULL, preferred_name TEXT);";
        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.execute();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void saveNewProfile(Profile profile) {
        String sql = "INSERT INTO profiles(email, password, preferred_name) VALUES(?,?,?)";
        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, profile.getEmail());
            pstmt.setString(2, profile.getPassword());
            pstmt.setString(3, profile.getPreferredName());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
