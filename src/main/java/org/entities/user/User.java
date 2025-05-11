package org.entities.user;

import java.io.Serializable;
import java.util.UUID;

public class User implements Serializable {


    private final UUID id;
    private String username;
    private String password;

    public User(String username, String password) {
        this.id = UUID.randomUUID();
        setUsername(username);
        setPassword(password);
    }

    public UUID getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    void setUsername(String username) {
        UserValidator.validateUsername(username);
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    void setPassword(String password) {
        UserValidator.validatePassword(password);
        this.password = password;
    }

}
