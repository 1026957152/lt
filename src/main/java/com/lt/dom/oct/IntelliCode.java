package com.lt.dom.oct;


import com.lt.dom.otcenum.EnumIntelliCodeStatus;
import com.lt.dom.otcenum.EnumThirdParty;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Entity

//https://apidocs.caren.io/?_ga=2.71657875.1268196039.1665162581-1815555920.1665162581#7a6423c5-fdc5-4cf7-8b94-40b59e0ced78@Entity
public class IntelliCode extends Base{

    private String code;



    @Enumerated(EnumType.STRING)
    private EnumIntelliCodeStatus status;

    public EnumIntelliCodeStatus getStatus() {
        return status;
    }

    public void setStatus(EnumIntelliCodeStatus status) {
        this.status = status;
    }

    private String expires_at;

    private Integer max_redemptions;


    private Integer times_redeemed;


    private Long lineItem;
    private Long booking;
    private boolean active;
    private String pin;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getExpires_at() {
        return expires_at;
    }

    public void setExpires_at(String expires_at) {
        this.expires_at = expires_at;
    }

    public Integer getMax_redemptions() {
        return max_redemptions;
    }

    public void setMax_redemptions(Integer max_redemptions) {
        this.max_redemptions = max_redemptions;
    }

    public void setLineItem(Long lineItem) {
        this.lineItem = lineItem;
    }

    public Long getLineItem() {
        return lineItem;
    }

    public void setBooking(Long booking) {
        this.booking = booking;
    }

    public Long getBooking() {
        return booking;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isActive() {
        return active;
    }

    public Integer getTimes_redeemed() {
        return times_redeemed;
    }

    public void setTimes_redeemed(Integer times_redeemed) {
        this.times_redeemed = times_redeemed;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public String getPin() {
        return pin;
    }
}
