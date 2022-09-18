package com.lt.dom.otcReq;


import javax.persistence.*;
import javax.validation.constraints.NotEmpty;


public class PassengerReq {



    @NotEmpty
    private String name;///

    @NotEmpty
    private String id_card;///

    @NotEmpty
    private String phone;///

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId_card() {
        return id_card;
    }

    public void setId_card(String id_card) {
        this.id_card = id_card;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
