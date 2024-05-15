package com.example.flowmato.model;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class SqliteProfileDAO {
    private final String url = "jdbc:sqlite:profiles_";
    private final String db_version = "v1.1.0";
    Integer raw_db_version;
    String old_db_version;
    Integer raw_old_db_version;
    Integer latest_found_db_version = 0;
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
        boolean[] foundExistingDB = {false};

        try {
            Files.list(directory)
                    .forEach(path -> {
                        if (path.getFileName() != null) {
                            String fileName = String.valueOf(path.getFileName());
                            if (fileName.equals("profiles_" + db_version + ".db")) {
                                foundExistingDB[0] = true;
                                return;
                            }
                            if (fileName.startsWith("profiles_")) {
                                String substring = fileName.substring(9, fileName.length() - 3);
                                int dbVersionFound = Integer.parseInt(substring.replaceAll("[^0-9]", ""));
                                if (dbVersionFound > latest_found_db_version) {
                                    old_db = path.toFile();
                                    old_db_version = substring;
                                    raw_old_db_version = dbVersionFound;
                                }
                            }
                        }
                    });

            if (foundExistingDB[0] || raw_old_db_version == null) {
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
                "FOREIGN KEY(profile_id) REFERENCES profiles(id), " +
                "UNIQUE(profile_id, achievement_type));";  //unique constraint for achievements
        String analyticsSql = "CREATE TABLE IF NOT EXISTS analytics (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "profile_id INTEGER NOT NULL UNIQUE," +
                "completed_pomodoros INTEGER DEFAULT 0," +
                "total_focus_time INTEGER DEFAULT 0," +
                "total_break_time INTEGER DEFAULT 0," +
                "last_updated DATETIME DEFAULT CURRENT_TIMESTAMP," +
                "FOREIGN KEY(profile_id) REFERENCES profiles(id));";

        try (Connection conn = this.connect();
             Statement stmt = conn.createStatement()) {
            stmt.execute(profilesSql);
            stmt.execute(achievementsSql);
            stmt.execute(analyticsSql);
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

    /**
     * checks if email already in database
     */
    public boolean emailExists(String email) {
        String sql = "SELECT 1 FROM profiles WHERE email = ?";
        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, email);
            ResultSet rs = pstmt.executeQuery();
            return rs.next(); //return true if at least 1 row exists
        } catch (SQLException e) {
            System.out.println("error checking for existing email: " + e.getMessage());
            return false;
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
        return null;
    }

    public void saveAchievement(Achievements achievement) {
        String sql = "INSERT OR IGNORE INTO achievements (profile_id, achievement_type, achieved_on) VALUES (?, ?, ?)";
        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, achievement.getProfileId());
            pstmt.setString(2, achievement.getAchievementType());
            pstmt.setString(3, achievement.getAchievedOn().toString());
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows == 0) { System.out.println("achievement already exists for profile"); }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void updateAnalytics(int profileId, int completedPomodoros, int focusTime, int breakTime) {
        String updateSql = "UPDATE analytics SET " +
                "completed_pomodoros = completed_pomodoros + ?, " +
                "total_focus_time = total_focus_time + ?, " +
                "total_break_time = total_break_time + ?, " +
                "last_updated = CURRENT_TIMESTAMP " +
                "WHERE profile_id = ?;";

        String insertSql = "INSERT INTO analytics (profile_id, completed_pomodoros, total_focus_time, total_break_time, last_updated) " +
                "SELECT ?, ?, ?, ?, CURRENT_TIMESTAMP " +
                "WHERE NOT EXISTS (SELECT 1 FROM analytics WHERE profile_id = ?);";

        try (Connection conn = this.connect();
             PreparedStatement updateStmt = conn.prepareStatement(updateSql);
             PreparedStatement insertStmt = conn.prepareStatement(insertSql)) {

            updateStmt.setInt(1, completedPomodoros);
            updateStmt.setInt(2, focusTime);
            updateStmt.setInt(3, breakTime);
            updateStmt.setInt(4, profileId);

            int rowsAffected = updateStmt.executeUpdate();

            if (rowsAffected == 0) {
                insertStmt.setInt(1, profileId);
                insertStmt.setInt(2, completedPomodoros);
                insertStmt.setInt(3, focusTime);
                insertStmt.setInt(4, breakTime);
                insertStmt.setInt(5, profileId);

                insertStmt.executeUpdate();
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public Analytics getAnalytics(int profileId) {
        String sql = "SELECT completed_pomodoros, total_focus_time, total_break_time FROM analytics WHERE profile_id = ?";
        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, profileId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return new Analytics(profileId, rs.getInt("completed_pomodoros"), rs.getInt("total_focus_time"), rs.getInt("total_break_time"));
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving analytics: " + e.getMessage());
        }
        return null;
    }

    public List<Achievements> getAchievementsForUser(int profileId) {
        String sql = "SELECT * FROM achievements WHERE profile_id = ?";
        List<Achievements> achievements = new ArrayList<>();
        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, profileId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String achievementType = rs.getString("achievement_type");
                String achievedOnString = rs.getString("achieved_on");
                LocalDateTime achievedOn = LocalDateTime.parse(achievedOnString);
                Achievements achievement = new Achievements(profileId, achievementType, achievedOn);
                achievement.setId(id);
                achievements.add(achievement);
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving achievements: " + e.getMessage());
        }
        return achievements;
    }
}
