package com.lt.dom.controllerOct.Axh.model;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

//@Email
public class AuthRequest {
    @NotNull  @Length(min = 5, max = 50)
    private String email;
     
    @NotNull @Length(min = 5, max = 10)
    private String password;

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

    // getters and setters are not shown...
}