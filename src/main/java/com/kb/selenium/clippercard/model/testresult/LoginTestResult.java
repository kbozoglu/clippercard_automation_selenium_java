package com.kb.selenium.clippercard.model.testresult;

import com.kb.selenium.clippercard.model.testcase.LoginTestCase;

import java.util.Date;

/**
 * Created by kbozoglu on 10.07.2015.
 */
public class LoginTestResult {
    private Long id;
    private LoginTestCase testCase;
    private Date startTime;
    private Date endTime;
    private boolean success;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LoginTestCase getTestCase() {
        return testCase;
    }

    public void setTestCase(LoginTestCase testCase) {
        this.testCase = testCase;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
