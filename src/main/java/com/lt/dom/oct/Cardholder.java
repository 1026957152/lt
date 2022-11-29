package com.lt.dom.oct;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.lt.dom.otcenum.EnumAssetType;
import com.lt.dom.otcenum.EnumCardholderStatus;
import com.lt.dom.otcenum.EnumCardholderType;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
public class Cardholder extends Base{

    private String identity;



    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "identification", referencedColumnName = "id")
    private Identification identification;
    private String uuid;
    private String code;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @OneToOne(mappedBy = "cardholder")
    private Pass pass;

   public Pass getPass() {
        return pass;
    }

    public void setPass(Pass pass) {
        this.pass = pass;
    }

    public Identification getIdentification() {
        return identification;
    }

    public void setIdentification(Identification identification) {
        this.identification = identification;
    }

    private Long company;

    private Long user;


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

@Enumerated(EnumType.STRING)
    private EnumCardholderStatus status;

    @Enumerated(EnumType.STRING)
    private EnumCardholderType type;



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
