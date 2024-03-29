package com.lt.dom.OctResp;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.lt.dom.oct.Review;

import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReviewResp {


    private String object;


    private String billingZip;


    private String charge;


    private String owner;


    private LocalDateTime submitted_at;

    private String commit_id;

    private String author_association;


    private String closedReason;
    private LocalDateTime created_at;
    private long request;

    public static ReviewResp from(Review x) {
        ReviewResp reviewResp = new ReviewResp();
        reviewResp.setOpen(x.getOpen());
        reviewResp.setReason(x.getReason());
        reviewResp.setCreated_at(x.getCreated_at());
        return reviewResp;
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
