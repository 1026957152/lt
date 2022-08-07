package com.lt.dom.oct;

import com.lt.dom.otcenum.EnumPublicationObjectType;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;


@Entity
public class RedemptionEntry {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private long id;
    private String holder;
    private int redeemed_amount;
    private int redeemed_quantity;
    private String campaign_name;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @NotNull
    private long voucher;
    private int gift_amount; //
    private long customer_id;


    private long validatorId;
    private long redemption;
    private long relatedObjectId;
    private EnumPublicationObjectType relatedObjectType;
    private long supplier;
    private long campaign;


    private boolean rollback;
    @NotNull
    private String code;
    @NotNull
    private boolean bulk;
    private LocalDateTime created_at;
    private LocalDateTime redeem_at;
    private String voucherCode;
    private LocalDateTime issued_at;

    public boolean isRollback() {
        return rollback;
    }

    public void setRollback(boolean rollback) {
        this.rollback = rollback;
    }

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

    public void setRelatedObjectType(EnumPublicationObjectType relatedObjectType) {
        this.relatedObjectType = relatedObjectType;
    }

    public EnumPublicationObjectType getRelatedObjectType() {
        return relatedObjectType;
    }

    public void setSupplier(long supplier) {
        this.supplier = supplier;
    }

    public long getSupplier() {
        return supplier;
    }

    public void setCampaign(long campaign) {
        this.campaign = campaign;
    }

    public long getCampaign() {
        return campaign;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setBulk(boolean bulk) {
        this.bulk = bulk;
    }

    public boolean getBulk() {
        return bulk;
    }

    public LocalDateTime getCreated_at() {
        return created_at;
    }

    public void setCreated_at(LocalDateTime created_at) {
        this.created_at = created_at;
    }

    public void setRedeem_at(LocalDateTime redeem_at) {
        this.redeem_at = redeem_at;
    }

    public LocalDateTime getRedeem_at() {
        return redeem_at;
    }

    public void setVoucherCode(String voucherCode) {
        this.voucherCode = voucherCode;
    }

    public String getVoucherCode() {
        return voucherCode;
    }

    public void setIssued_at(LocalDateTime issued_at) {
        this.issued_at = issued_at;
    }

    public LocalDateTime getIssued_at() {
        return issued_at;
    }

    public void setHolder(String holder) {
        this.holder = holder;
    }

    public String getHolder() {
        return holder;
    }

    public void setRedeemed_amount(int redeemed_amount) {
        this.redeemed_amount = redeemed_amount;
    }

    public int getRedeemed_amount() {
        return redeemed_amount;
    }

    public void setRedeemed_quantity(int redeemed_quantity) {
        this.redeemed_quantity = redeemed_quantity;
    }

    public int getRedeemed_quantity() {
        return redeemed_quantity;
    }

    public void setCampaign_name(String campaign_name) {
        this.campaign_name = campaign_name;
    }

    public String getCampaign_name() {
        return campaign_name;
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
