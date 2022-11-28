package com.lt.dom.otcReq;

import com.lt.dom.otcenum.EnumCardType;

import java.util.List;


public class PassLinkPojo {


    private Long pass;
    private String passNumber
            ;


    public String getPassNumber() {
        return passNumber;
    }

    public void setPassNumber(String passNumber) {
        this.passNumber = passNumber;
    }

    private Long user;

    public Long getPass() {
        return pass;
    }

    public void setPass(Long pass) {
        this.pass = pass;
    }

    public Long getUser() {
        return user;
    }

    public void setUser(Long user) {
        this.user = user;
    }
}
