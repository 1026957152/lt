package com.lt.dom.oct;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
public class Redemption {

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private long id;
    private int quantity;
    private int redeemed_quantity;
    private long campaign_id;
    private long validatorSupplier;
    private long voucher_Id;
    private LocalDateTime created_at;

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getRedeemed_quantity() {
        return redeemed_quantity;
    }

    public void setRedeemed_quantity(int redeemed_quantity) {
        this.redeemed_quantity = redeemed_quantity;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
    private String relatedObjectType;
    private long relatedObjectId;

    public String getRelatedObjectType() {
        return relatedObjectType;
    }

    public void setRelatedObjectType(String related_object_type) {
        this.relatedObjectType = related_object_type;
    }

    public long getRelatedObjectId() {
        return relatedObjectId;
    }

    public void setRelatedObjectId(long related_object_id) {
        this.relatedObjectId = related_object_id;
    }

    public void setCampaign_id(long campaign_id) {
        this.campaign_id = campaign_id;
    }

    public long getCampaign_id() {
        return campaign_id;
    }

    public void setValidatorSupplier(long validatorSupplier) {
        this.validatorSupplier = validatorSupplier;
    }

    public long getValidatorSupplier() {
        return validatorSupplier;
    }

    public void setVoucher_Id(long voucher_id) {
        this.voucher_Id = voucher_id;
    }

    public long getVoucher_Id() {
        return voucher_Id;
    }

    public void setCreated_at(LocalDateTime created_at) {
        this.created_at = created_at;
    }

    public LocalDateTime getCreated_at() {
        return created_at;
    }
}
