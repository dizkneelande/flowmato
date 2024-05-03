package com.example.flowmato.model;

public class SessionManager {
    private static SessionManager instance;
    private Profile currentUser;

    private SessionManager() {}

    public static SessionManager getInstance() {
        if (instance == null) {
            instance = new SessionManager();
        }
        return instance;
    }

    public void login(Profile user) {
        this.currentUser = user;
    }

    public void logout() {
        this.currentUser = null;
    }

    public Profile getCurrentUser() {
        return currentUser;
    }

    public Integer getCurrentUserId() {
        return currentUser != null ? currentUser.getId() : null;
    }
}
