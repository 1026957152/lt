package com.lt.dom.OctResp;


import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDateTime;



@JsonInclude(JsonInclude.Include.NON_NULL)
public class VerifyPhoneResp {




    private String phone;

    private String id;
    private String message;
    private LocalDateTime expiryDate;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }


    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setExpiryDate(LocalDateTime expiryDate) {
        this.expiryDate = expiryDate;
    }

    public LocalDateTime getExpiryDate() {
        return expiryDate;
    }
}
