package com.lt.dom.OctResp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;



public class RedemptionEntryResp {




    private long voucher;
    private int gift_amount; //
    private long customer_id;

    private LocalDate date;
    private long validatorId;
    private long redemption;
    private long relatedObjectId;
    private String relatedObjectType;

    public long getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(long customer_id) {
        this.customer_id = customer_id;
    }

    private RedemptionStatus result;

    public void setValidatorId(long validatorId) {
        this.validatorId = validatorId;
    }

    public long getValidatorId() {
        return validatorId;
    }

    public void setRedemption(long redemption) {
        this.redemption = redemption;
    }

    public long getRedemption() {
        return redemption;
    }

    public void setRelatedObjectId(long relatedObjectId) {
        this.relatedObjectId = relatedObjectId;
    }

    public long getRelatedObjectId() {
        return relatedObjectId;
    }

    public void setRelatedObjectType(String relatedObjectType) {
        this.relatedObjectType = relatedObjectType;
    }

    public String getRelatedObjectType() {
        return relatedObjectType;
    }

    public static enum RedemptionStatus {
        SUCCESS,FAILURE;
    }

    public long getVoucher() {
        return voucher;
    }

    public void setVoucher(long voucher) {
        this.voucher = voucher;
    }

    public int getGift_amount() {
        return gift_amount;
    }

    public void setGift_amount(int gift_amount) {
        this.gift_amount = gift_amount;
    }

    public RedemptionStatus getResult() {
        return result;
    }

    public void setResult(RedemptionStatus result) {
        this.result = result;
    }
}
