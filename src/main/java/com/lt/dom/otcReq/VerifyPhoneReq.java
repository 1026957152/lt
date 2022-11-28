package com.lt.dom.otcReq;

import javax.validation.constraints.NotEmpty;


public class VerifyPhoneReq {




    private String phone;

    private String code;
    private String id;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
