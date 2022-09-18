package com.lt.dom.OctResp;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.lt.dom.otcenum.EnumCardholderStatus;
import com.lt.dom.otcenum.EnumCardholderType;

import javax.persistence.*;
import java.time.LocalDateTime;


public class CardholderResp {



    private long company;

    private long user;


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

    public long getCompany() {
        return company;
    }

    public void setCompany(long company) {
        this.company = company;
    }

    public long getUser() {
        return user;
    }

    public void setUser(long user) {
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
}
