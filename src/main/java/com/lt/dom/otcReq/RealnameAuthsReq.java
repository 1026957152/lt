package com.lt.dom.otcReq;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.lt.dom.OctResp.PhotoResp;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
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

    public boolean isLive_mode() {
        return live_mode;
    }

    public void setLive_mode(boolean live_mode) {
        this.live_mode = live_mode;
    }

    private boolean live_mode = true;
    @NotEmpty
    private String real_name;
    @NotEmpty
    private String id_card;

    private String phone;


    private String crypto_phone;


    private PhotoResp selfie;
    private PhotoResp document;


    public PhotoResp getSelfie() {
        return selfie;
    }

    public void setSelfie(PhotoResp selfie) {
        this.selfie = selfie;
    }

    public PhotoResp getDocument() {
        return document;
    }

    public void setDocument(PhotoResp document) {
        this.document = document;
    }

    public String getCrypto_phone() {
        return crypto_phone;
    }

    public void setCrypto_phone(String crypto_phone) {
        this.crypto_phone = crypto_phone;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getReal_name() {
        return real_name;
    }

    public void setReal_name(String real_name) {
        this.real_name = real_name;
    }

    public String getId_card() {
        return id_card;
    }

    public void setId_card(String id_card) {
        this.id_card = id_card;
    }

    public String getVerifiedNumber() {
        return verifiedNumber;
    }

    public void setVerifiedNumber(String verifiedNumber) {
        this.verifiedNumber = verifiedNumber;
    }

    public List<String> getVerifiedFileUrl() {
        return verifiedFileUrl;
    }

    public void setVerifiedFileUrl(List<String> verifiedFileUrl) {
        this.verifiedFileUrl = verifiedFileUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getChangeType() {
        return changeType;
    }

    public void setChangeType(Integer changeType) {
        this.changeType = changeType;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public Integer getVerifiedType() {
        return verifiedType;
    }

    public void setVerifiedType(Integer verifiedType) {
        this.verifiedType = verifiedType;
    }

    public Integer getIdentifyType() {
        return identifyType;
    }

    public void setIdentifyType(Integer identifyType) {
        this.identifyType = identifyType;
    }

    public String getXaccountType() {
        return xaccountType;
    }

    public void setXaccountType(String xaccountType) {
        this.xaccountType = xaccountType;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }

}
