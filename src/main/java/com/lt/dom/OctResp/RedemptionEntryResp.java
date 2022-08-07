package com.lt.dom.OctResp;

import com.lt.dom.otcenum.EnumPublicationObjectType;

import java.time.LocalDate;
import java.time.LocalDateTime;


public class RedemptionEntryResp {





    private EnumPublicationObjectType relatedObjectType;









    private String holder;
    private int redeemed_amount;
    private String campaign_name;
    private String voucher_code;
    private LocalDateTime redeem_at;
    private int redeemed_quantity;

    public String getHolder() {
        return holder;
    }

    public void setHolder(String holder) {
        this.holder = holder;
    }

    public int getRedeemed_amount() {
        return redeemed_amount;
    }

    public void setRedeemed_amount(int redeemed_amount) {
        this.redeemed_amount = redeemed_amount;
    }

    public String getCampaign_name() {
        return campaign_name;
    }

    public void setCampaign_name(String campaign_name) {
        this.campaign_name = campaign_name;
    }

    public String getVoucher_code() {
        return voucher_code;
    }

    public void setVoucher_code(String voucher_code) {
        this.voucher_code = voucher_code;
    }


    private RedemptionStatus result;


    public void setRelatedObjectType(EnumPublicationObjectType relatedObjectType) {
        this.relatedObjectType = relatedObjectType;
    }

    public EnumPublicationObjectType getRelatedObjectType() {
        return relatedObjectType;
    }

    public void setRedeem_at(LocalDateTime redeem_at) {
        this.redeem_at = redeem_at;
    }

    public LocalDateTime getRedeem_at() {
        return redeem_at;
    }

    public void setRedeemed_quantity(int redeemed_quantity) {
        this.redeemed_quantity = redeemed_quantity;
    }

    public int getRedeemed_quantity() {
        return redeemed_quantity;
    }

    public static enum RedemptionStatus {
        SUCCESS,FAILURE;
    }


    public RedemptionStatus getResult() {
        return result;
    }

    public void setResult(RedemptionStatus result) {
        this.result = result;
    }
}
