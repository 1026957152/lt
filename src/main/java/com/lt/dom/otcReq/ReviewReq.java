package com.lt.dom.otcReq;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;


public class ReviewReq {


    private String idX;
    @JsonProperty("object")
    private String object;
    @JsonProperty("billing_zip")

    private String billingZip;


    @JsonProperty("charge")
    private String charge;


    @JsonProperty("closed_reason")
    private String closedReason;
    @JsonProperty("created")
    private Integer created;



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
