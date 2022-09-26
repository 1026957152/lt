package com.lt.dom.oct;

import com.lt.dom.otcenum.EnumAssociatedType;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Redemption {
    @Version
    private Integer version;
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private long id;
    private Long quantity;
    private Long redeemed_quantity;
    private long campaign_id;
    private long validatorSupplier;
    private long voucher_Id;
    private LocalDateTime created_at;
    
//##@Column(unique=true) 
private String code;

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public Long getRedeemed_quantity() {
        return redeemed_quantity;
    }

    public void setRedeemed_quantity(Long redeemed_quantity) {
        this.redeemed_quantity = redeemed_quantity;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
    private EnumAssociatedType relatedObjectType;
    private long relatedObjectId;

    public EnumAssociatedType getRelatedObjectType() {
        return relatedObjectType;
    }

    public void setRelatedObjectType(EnumAssociatedType related_object_type) {
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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
