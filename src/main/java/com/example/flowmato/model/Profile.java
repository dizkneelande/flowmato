package com.example.flowmato.model;

/**
 * profile class - leaving extendability for more fields (like achievements and such)
 */
public class Profile {
    private int id;     // references primary key in database at this point
    private String email;
    private String password;
    private String preferredName;

    // Constructor (without id for creating new instances)
    public Profile(String email, String password, String preferredName) {
        this.email = email;
        this.password = password;
        this.preferredName = preferredName;
    }

    // Constructor with id for retrieval
    public Profile(int id, String email, String password, String preferredName) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.preferredName = preferredName;
    }

    // Getters and Setters
    public int getId() { return id; }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPreferredName() {
        return preferredName;
    }

    public void setPreferredName(String preferredName) {
        this.preferredName = preferredName;
    }
}
