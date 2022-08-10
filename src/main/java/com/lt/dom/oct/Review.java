package com.lt.dom.oct;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor
@Data
@Entity
public class Review {

    @Version
    private Integer version;
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private long id;

    @JsonProperty("id")
    private String idX;
    @JsonProperty("object")
    private String object;
    @JsonProperty("billing_zip")

    private String billingZip;


    @JsonProperty("charge")
    private String charge;


    @JsonProperty("closed_reason")
    private String closedReason;
    private LocalDateTime created_at;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getIdX() {
        return idX;
    }

    public void setIdX(String idX) {
        this.idX = idX;
    }

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
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

    @JsonProperty("ip_address")
    private String ipAddress;

    @Transient
    @JsonProperty("ip_address_location")
    private Object ipAddressLocation;
    @JsonProperty("livemode")
    private Boolean livemode;
    @JsonProperty("open")
    private Boolean open;
    @JsonProperty("opened_reason")
    private String openedReason;
    @JsonProperty("reason")
    private String reason;


    @JsonProperty("session")
    private String session;



}
