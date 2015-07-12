package com.kb.selenium.clippercard.model.testcase;

import com.kb.selenium.clippercard.model.User;

/**
 * Created by kbozoglu on 10.07.2015.
 */
public class LoginTestCase {
    private Long id;
    private User user;
    private boolean success;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
