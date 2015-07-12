package com.kb.selenium.clippercard.model;

import org.apache.log4j.Logger;

/**
 * Created by kbozoglu on 10.07.2015.
 */
public class User {
    private static Logger logger = Logger.getLogger(User.class);

    private Long id;
    private String email;
    private String firstName;
    private String lastName;
    private String phone;
    private String password;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void logUser(){
        StringBuffer bf = new StringBuffer();
        bf.append("id : ").append(id).append("\n");
        bf.append("email : ").append(email).append("\n");
        bf.append("firstName : ").append(firstName).append("\n");
        bf.append("lastName : ").append(lastName).append("\n");
        bf.append("phone : ").append(phone);

        logger.info(bf.toString());
    }

}
