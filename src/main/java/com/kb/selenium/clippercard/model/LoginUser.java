package com.kb.selenium.clippercard.model;

/**
 * Created by kbozoglu on 07.07.2015.
 */
public class LoginUser {
    private String username;
    private String password;
    private boolean success;

    public LoginUser(String username, String password, boolean success) {
        this.username = username;
        this.password = password;
        this.success = success;
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

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
