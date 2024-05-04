package com.example.flowmato.model;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;

public class SqliteProfileDAO {
    private final String url = "jdbc:sqlite:profiles_";
    private final String db_version = "v1.1.0";
    Integer raw_db_version;
    String old_db_version;
    Integer raw_old_db_version;
    File old_db;

    private Connection connect() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url + db_version + ".db");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    /**
     * Checks and informs the user if their database version is the one required by this version of the app.
     */
    private void checkVersion() {
        Path directory = Paths.get("");

        try {
            Files.list(directory)
                    .forEach(path -> {
                        if (path.getFileName() != null) {
                            String fileName = String.valueOf(path.getFileName());
                            if (fileName.equals("profiles_" + db_version + ".db")) {
                                return;
                            }
                            if (fileName.startsWith("profiles_")) {
                                String substring = fileName.substring(9, fileName.length() - 3);
                                old_db = path.toFile();
                                old_db_version = substring;
                                raw_old_db_version = Integer.parseInt(substring.replaceAll("[^0-9]", ""));
                            }
                        }
                    });

            if (raw_old_db_version == null) {
                return;
            }

            raw_db_version = Integer.parseInt(db_version.replaceAll("[^0-9]", ""));

            if (raw_old_db_version < raw_db_version) {
                System.out.println("Database is currently out of date, expected " + db_version + " found " + old_db_version);

            } else if (raw_old_db_version > raw_db_version) {
                System.out.println("Database version " + old_db_version + " found is newer than " + db_version);
            }
            System.out.println("Creating new database (" + db_version + ")...");
        } catch (IOException e) {
            System.err.println("An error occurred: " + e.getMessage());
        }
    }

    public void initialiseDatabase() {
        checkVersion();
        String profilesSql = "CREATE TABLE IF NOT EXISTS profiles (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "email TEXT NOT NULL, " +
                "password TEXT NOT NULL, " +
                "preferred_name TEXT);";
        String achievementsSql = "CREATE TABLE IF NOT EXISTS achievements (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "profile_id INTEGER, " +
                "achievement_type TEXT NOT NULL, " +
                "achieved_on DATETIME DEFAULT CURRENT_TIMESTAMP, " +
                "FOREIGN KEY(profile_id) REFERENCES profiles(id));";
        try (Connection conn = this.connect();
             PreparedStatement pstmt1 = conn.prepareStatement(profilesSql);
             PreparedStatement pstmt2 = conn.prepareStatement(achievementsSql)) {
            pstmt1.execute();
            pstmt2.execute();
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

    public Profile validateLogin(String email, String password) {
        String sql = "SELECT id, email, preferred_name FROM profiles WHERE email = ? AND password = ?";
        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, email);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return new Profile(rs.getInt("id"), rs.getString("email"), password, rs.getString("preferred_name"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;  //if not user found
    }

    public void saveAchievement(Achievements achievement) {
        String sql = "INSERT INTO achievements (profile_id, achievement_type, achieved_on) VALUES (?, ?, ?)";
        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, achievement.getProfileId());
            pstmt.setString(2, achievement.getAchievementType());
            pstmt.setString(3, achievement.getAchievedOn().toString());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
