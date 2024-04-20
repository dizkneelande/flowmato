package com.example.flowmato.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SqliteProfileDAO {

    private final String url = "jdbc:sqlite:profiles.db";
    //connect to profiles.db
    private Connection connect() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    //initialise database with email/pw/name for now
    public void initialiseDatabase() {
        String sql = "CREATE TABLE IF NOT EXISTS profiles (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "email TEXT NOT NULL," +
                "password TEXT NOT NULL," +
                "preferred_name TEXT);";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.execute();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    //save new profile to db
    public void saveNewProfile(String email, String password, String preferredName) {
        String sql = "INSERT INTO profiles(email, password, preferred_name) VALUES(?,?,?)";
        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, email);
            pstmt.setString(2, password);
            pstmt.setString(3, preferredName);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
