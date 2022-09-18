package com.lt.dom.OctResp;

import com.lt.dom.oct.Pass;
import com.lt.dom.otcenum.EnumCardStatus;
import com.lt.dom.otcenum.EnumCardType;
import com.lt.dom.otcenum.EnumPassDorationUnit;
import com.lt.dom.otcenum.EnumPassShippingStatus;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;


public class PassResp {



    private EnumCardType type ;//digital tickets or PDF tickets

    private String code ;//digital tickets or PDF tickets
    private String status_text;
    private EnumCardStatus status;
    private EnumPassShippingStatus shipping_statis;
    private String type_text;

    public static PassResp from(Pass e) {
        PassResp passResp = new PassResp();
        passResp.setCode(e.getCode());
        passResp.setDuration(e.getDuration());
        passResp.setUser(e.getUser());
        passResp.setDurationUnit(e.getDurationUnit());
        passResp.setMaxActivationDate(e.getMaxActivationDate());

        passResp.setExpiringDate(e.getExpiringDate());
        passResp.setStatus(e.getStatus());
        passResp.setStatus_text(e.getStatus().toString());
        if(e.getType().equals(EnumCardType.physical)){
            passResp.setShipping_statis(e.getShipping_statis());
        }
        passResp.setType(e.getType());
        passResp.setType_text(e.getType().toString());
        return passResp;
    }

    public String getCode() {
        return code;
    }
    private long user ;//digital tickets or PDF tickets

    public long getUser() {
        return user;
    }

    public void setUser(long user) {
        this.user = user;
    }

    public void setCode(String code) {
        this.code = code;
    }



    public EnumCardType getType() {
        return type;
    }

    public void setType(EnumCardType type) {
        this.type = type;
    }




    private Integer duration ;//digital tickets or PDF tickets
    private EnumPassDorationUnit durationUnit ;//digital tickets or PDF tickets
    private LocalDateTime maxActivationDate ;//digital tickets or PDF tickets

    private LocalDateTime expiringDate ;//digital tickets or PDF tickets

    private LocalDateTime emissionDate ;//digital tickets or PDF tickets


    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public EnumPassDorationUnit getDurationUnit() {
        return durationUnit;
    }

    public void setDurationUnit(EnumPassDorationUnit durationUnit) {
        this.durationUnit = durationUnit;
    }

    public LocalDateTime getMaxActivationDate() {
        return maxActivationDate;
    }

    public void setMaxActivationDate(LocalDateTime maxActivationDate) {
        this.maxActivationDate = maxActivationDate;
    }

    private Integer balance ;//digital tickets or PDF tickets
    private Integer remaining ;//digital tickets or PDF tickets

    private boolean is_active ;//digital tickets or PDF tickets


    private LocalDateTime deliver_date ;//digital tickets or PDF tickets



    @Transient
    List activities;


    public void setStatus_text(String status_text) {
        this.status_text = status_text;
    }

    public String getStatus_text() {
        return status_text;
    }

    public void setStatus(EnumCardStatus status) {
        this.status = status;
    }

    public EnumCardStatus getStatus() {
        return status;
    }

    public void setExpiringDate(LocalDateTime expiringDate) {
        this.expiringDate = expiringDate;
    }

    public LocalDateTime getExpiringDate() {
        return expiringDate;
    }

    public void setShipping_statis(EnumPassShippingStatus shipping_statis) {
        this.shipping_statis = shipping_statis;
    }

    public EnumPassShippingStatus getShipping_statis() {
        return shipping_statis;
    }

    public void setType_text(String type_text) {
        this.type_text = type_text;
    }

    public String getType_text() {
        return type_text;
    }
}
