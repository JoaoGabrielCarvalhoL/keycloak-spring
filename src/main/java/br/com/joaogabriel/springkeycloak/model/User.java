package br.com.joaogabriel.springkeycloak.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class User {
    @NotBlank(message = "Username is required.")
    private String username;

    @NotBlank
    @Size(min = 6, max = 20, message = "Password must be between 6 and 20 characters.")
    private String password;

    public User() {}

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
