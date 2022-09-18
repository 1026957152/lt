package com.lt.dom.oct;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.lt.dom.otcenum.EnumAssetType;
import com.lt.dom.otcenum.EnumCardholderStatus;
import com.lt.dom.otcenum.EnumCardholderType;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
public class Cardholder {
    @Version
    private Integer version;

    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Id
    private long id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


    private long company;

    private long user;


    @JsonProperty("created")
    private LocalDateTime created_at;
    @JsonProperty("email")
    private String email;
/*
    @JsonProperty("individual")
    private Object individual;
*/


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


    private EnumCardholderStatus status;
    private EnumCardholderType type;

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

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

    public EnumCardholderStatus getStatus() {
        return status;
    }

    public void setStatus(EnumCardholderStatus status) {
        this.status = status;
    }

    public EnumCardholderType getType() {
        return type;
    }

    public void setType(EnumCardholderType type) {
        this.type = type;
    }
}
