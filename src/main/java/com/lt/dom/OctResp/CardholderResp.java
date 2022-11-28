package com.lt.dom.OctResp;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.lt.dom.oct.Cardholder;
import com.lt.dom.otcenum.EnumCardholderStatus;
import com.lt.dom.otcenum.EnumCardholderType;

import javax.persistence.*;
import java.time.LocalDateTime;




@JsonInclude(JsonInclude.Include.NON_NULL)
public class CardholderResp {



    private Long company;

    private Long user;


    private LocalDateTime created_at;

    private String email;

    private Object individual;


    private String name;
    private String phoneNumber;


/*
    @JsonProperty("redaction")
    private Object redaction;
    @JsonProperty("requirements")
    private Card.RequirementsDTO requirements;
    @JsonProperty("spending_controls")
    private Card.SpendingControlsDTO spendingControls;
*/


    private EnumCardholderType type;
    private PhotoResp front;
    private PhotoResp back;
    private String identity;
    private String uuid;

    public static CardholderResp from(Cardholder cardholder) {
        CardholderResp cardholderResp = new CardholderResp();
        cardholderResp.setName(cardholder.getName());
        cardholderResp.setIdentity(cardholder.getIdentity());
        cardholderResp.setType(cardholder.getType());
        cardholderResp.setPhoneNumber(cardholder.getPhoneNumber());
        cardholderResp.setUuid(cardholder.getUuid());


        return cardholderResp;
    }

    public Long getCompany() {
        return company;
    }

    public void setCompany(Long company) {
        this.company = company;
    }

    public Long getUser() {
        return user;
    }

    public void setUser(Long user) {
        this.user = user;
    }

    public LocalDateTime getCreated_at() {
        return created_at;
    }

    public void setCreated_at(LocalDateTime created_at) {
        this.created_at = created_at;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Object getIndividual() {
        return individual;
    }

    public void setIndividual(Object individual) {
        this.individual = individual;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public EnumCardholderType getType() {
        return type;
    }

    public void setType(EnumCardholderType type) {
        this.type = type;
    }

    public void setFront(PhotoResp front) {
        this.front = front;
    }

    public PhotoResp getFront() {
        return front;
    }

    public void setBack(PhotoResp back) {
        this.back = back;
    }

    public PhotoResp getBack() {
        return back;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    public String getIdentity() {
        return identity;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getUuid() {
        return uuid;
    }
}
