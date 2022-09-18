package com.lt.dom.OctResp;


import com.lt.dom.oct.Passenger;

import javax.validation.constraints.NotEmpty;


public class PassengerResp {




    private String name;///


    private String id_card;///


    private String phone;///
    private String code;

    public static PassengerResp of(Passenger e) {

        PassengerResp passengerResp = new PassengerResp();
        passengerResp.setId_card(e.getIdNo());
        passengerResp.setName(e.getName());
        passengerResp.setPhone(e.getTel_home());
        passengerResp.setCode(e.getCode());
        return passengerResp;
    }

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

    public void setCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
