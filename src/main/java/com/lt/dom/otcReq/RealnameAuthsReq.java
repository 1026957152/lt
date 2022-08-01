package com.lt.dom.otcReq;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;


public class RealnameAuthsReq {


    @JsonProperty("verified_number")
    private String verifiedNumber;
    @JsonProperty("verified_file_url")
    private List<String> verifiedFileUrl;
    @JsonProperty("name")
    private String name;
    @JsonProperty("change_type")
    private Integer changeType;
    @JsonProperty("customer_id")
    private String customerId;
    @JsonProperty("verified_type")
    private Integer verifiedType;
    @JsonProperty("identify_type")
    private Integer identifyType;
    @JsonProperty("xaccount_type")
    private String xaccountType;
    private String realName;
    private String idCard;

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }
}
