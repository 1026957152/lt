package com.lt.dom.otcReq;


import javax.validation.constraints.NotNull;

public class VerifyPhoneSendCodeReq {




    @NotNull
    private String phone;

    private String id;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
