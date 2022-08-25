package com.lt.dom.OctResp;

import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;


public class UserWithTokenResp {





    private String token;
    private EntityModel info;
    private int status;

    public void setInfo(EntityModel info) {

        this.info = info;
    }

    public EntityModel getInfo() {
        return info;
    }


    public void setToken(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }
}
