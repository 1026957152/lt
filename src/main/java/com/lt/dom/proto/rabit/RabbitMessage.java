package com.lt.dom.proto.rabit;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;


public class RabbitMessage {



    private EnumRabbitMessageType type ;
    private CityPassBooking cityPassBooking ;
    //  String 景区 = "景区";
    private String token;
    private String phone;

    public EnumRabbitMessageType getType() {
        return type;
    }

    public void setType(EnumRabbitMessageType type) {
        this.type = type;
    }

    public CityPassBooking getCityPassBooking() {
        return cityPassBooking;
    }

    public void setCityPassBooking(CityPassBooking cityPassBooking) {
        this.cityPassBooking = cityPassBooking;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhone() {
        return phone;
    }
}
