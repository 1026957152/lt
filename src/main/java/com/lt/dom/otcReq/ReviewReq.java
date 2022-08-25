package com.lt.dom.otcReq;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.lt.dom.otcenum.EnumRequestApproveReject;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;


public class ReviewReq {



    private String idX;

    private EnumRequestApproveReject type;

    public EnumRequestApproveReject getType() {
        return type;
    }

    public void setType(EnumRequestApproveReject type) {
        this.type = type;
    }

    private String billingZip;


    private String charge;

    private String owner;

    private LocalDateTime submitted_at;
    private String commit_id;

    private String author_association;


    private String closedReason;
    private LocalDateTime created_at;
    private long request;


    public String getIdX() {
        return idX;
    }

    public void setIdX(String idX) {
        this.idX = idX;
    }


    public String getBillingZip() {
        return billingZip;
    }

    public void setBillingZip(String billingZip) {
        this.billingZip = billingZip;
    }

    public String getCharge() {
        return charge;
    }

    public void setCharge(String charge) {
        this.charge = charge;
    }

    public String getClosedReason() {
        return closedReason;
    }

    public void setClosedReason(String closedReason) {
        this.closedReason = closedReason;
    }

    public LocalDateTime getCreated_at() {
        return created_at;
    }

    public void setCreated_at(LocalDateTime created_at) {
        this.created_at = created_at;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public Object getIpAddressLocation() {
        return ipAddressLocation;
    }

    public void setIpAddressLocation(Object ipAddressLocation) {
        this.ipAddressLocation = ipAddressLocation;
    }

    public Boolean getLivemode() {
        return livemode;
    }

    public void setLivemode(Boolean livemode) {
        this.livemode = livemode;
    }

    public Boolean getOpen() {
        return open;
    }

    public void setOpen(Boolean open) {
        this.open = open;
    }

    public String getOpenedReason() {
        return openedReason;
    }

    public void setOpenedReason(String openedReason) {
        this.openedReason = openedReason;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getSession() {
        return session;
    }

    public void setSession(String session) {
        this.session = session;
    }

    private String ipAddress;

    private Object ipAddressLocation;
    private Boolean livemode;
    private Boolean open;
    private String openedReason;
    private String reason;


    private String session;


    public void setRequest(long request) {
        this.request = request;
    }

    public long getRequest() {
        return request;
    }

}
