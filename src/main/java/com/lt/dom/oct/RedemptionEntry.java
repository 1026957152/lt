package com.lt.dom.oct;

import com.lt.dom.otcenum.EnumPublicationObjectType;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;


@Entity
public class RedemptionEntry {
    @Version
    private Integer version;
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private long id;


    @NotNull
    private int redeemed_amount;
    @NotNull
    private Long redeemed_quantity;

    @NotEmpty
    private String campaign_name;
    @NotNull
    private float customer_latitude;
    @NotNull
    private float customer_longitude;
    @NotNull
    private float merchant_latitude;
    @NotNull
    private float merchant_longitude;
    @NotEmpty
    private String merchant_name;
    @NotEmpty
    private String merchant_code;
    @NotEmpty
    private String campaign_code;

    private String holder_real_name;
    private String holder_id_card;
    private String holder_phone;


    private String written_off_staff_name;
    private String written_off_staff_phone;
    private String written_off_staff_id;


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
    
@Column(unique=true) 
private String code;
    @NotNull
    private boolean bulk;


    @NotNull
    private LocalDateTime createdAt;

    @NotNull
    private LocalDateTime redeemed_at;

    @NotNull
    private String voucher_code;

    @NotNull
    private LocalDateTime published_at;

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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime created_at) {
        this.createdAt = created_at;
    }

    public void setRedeemed_at(LocalDateTime redeem_at) {
        this.redeemed_at = redeem_at;
    }

    public LocalDateTime getRedeemed_at() {
        return redeemed_at;
    }

    public void setVoucher_code(String voucherCode) {
        this.voucher_code = voucherCode;
    }

    public String getVoucher_code() {
        return voucher_code;
    }

    public void setPublished_at(LocalDateTime issued_at) {
        this.published_at = issued_at;
    }

    public LocalDateTime getPublished_at() {
        return published_at;
    }



    public void setRedeemed_amount(int redeemed_amount) {
        this.redeemed_amount = redeemed_amount;
    }

    public int getRedeemed_amount() {
        return redeemed_amount;
    }

    public void setRedeemed_quantity(Long redeemed_quantity) {
        this.redeemed_quantity = redeemed_quantity;
    }

    public Long getRedeemed_quantity() {
        return redeemed_quantity;
    }

    public void setCampaign_name(String campaign_name) {
        this.campaign_name = campaign_name;
    }

    public String getCampaign_name() {
        return campaign_name;
    }

    public void setCustomer_latitude(float customer_latitude) {
        this.customer_latitude = customer_latitude;
    }

    public float getCustomer_latitude() {
        return customer_latitude;
    }

    public void setCustomer_longitude(float customer_longitude) {
        this.customer_longitude = customer_longitude;
    }

    public float getCustomer_longitude() {
        return customer_longitude;
    }

    public void setMerchant_latitude(float merchant_latitude) {
        this.merchant_latitude = merchant_latitude;
    }

    public float getMerchant_latitude() {
        return merchant_latitude;
    }

    public void setMerchant_longitude(float merchant_longitude) {
        this.merchant_longitude = merchant_longitude;
    }

    public float getMerchant_longitude() {
        return merchant_longitude;
    }

    public void setMerchant_name(String merchant_name) {
        this.merchant_name = merchant_name;
    }

    public String getMerchant_name() {
        return merchant_name;
    }

    public void setMerchant_code(String merchant_code) {
        this.merchant_code = merchant_code;
    }

    public String getMerchant_code() {
        return merchant_code;
    }

    public void setCampaign_code(String campaign_code) {
        this.campaign_code = campaign_code;
    }

    public String getCampaign_code() {
        return campaign_code;
    }

    public void setHolder_real_name(String holder_real_name) {
        this.holder_real_name = holder_real_name;
    }

    public String getHolder_real_name() {
        return holder_real_name;
    }

    public void setHolder_id_card(String holder_id_card) {
        this.holder_id_card = holder_id_card;
    }

    public String getHolder_id_card() {
        return holder_id_card;
    }

    public void setHolder_phone(String holder_phone) {
        this.holder_phone = holder_phone;
    }

    public String getHolder_phone() {
        return holder_phone;
    }

    public void setWritten_off_staff_name(String written_off_staff_name) {
        this.written_off_staff_name = written_off_staff_name;
    }

    public String getWritten_off_staff_name() {
        return written_off_staff_name;
    }

    public void setWritten_off_staff_phone(String written_off_staff_phone) {

        this.written_off_staff_phone = written_off_staff_phone;
    }

    public String getWritten_off_staff_phone() {
        return written_off_staff_phone;
    }

    public void setWritten_off_staff_id(String written_off_staff_id) {
        this.written_off_staff_id = written_off_staff_id;
    }

    public String getWritten_off_staff_id() {
        return written_off_staff_id;
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
