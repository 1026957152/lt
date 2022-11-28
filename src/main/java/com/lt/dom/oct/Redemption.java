package com.lt.dom.oct;

import com.lt.dom.otcenum.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Redemption extends Base{

    private Long quantity;
    private Long redeemed_quantity;
    private long campaign_id;
    private long validatorSupplier;
    private long voucher_Id;
    private LocalDateTime created_at;
    
//##@Column(unique=true) 
private String code;


    @Enumerated(EnumType.STRING)
    private EnumValidatorType validatorType;
    private Long validator;
    private Long customer;
    private String log_RelatedObject_lable;
    private String log_RelatedObject_code;
    private EnumVoucherType log_RelatedObject_type;
    private String log_Customer_name;
    private String log_Customer_code;
    private String log_Validator_user_name;
    private String log_Validator_user_code;
    private String log_Validator_device_name;
    private String log_Validator_device_code;
    private EnumDeviceType log_Validator_device_type;

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


    @Enumerated(EnumType.STRING)
    private EnumRelatedObjectType relatedObjectType;
    private long relatedObjectId;

    public EnumRelatedObjectType getRelatedObjectType() {
        return relatedObjectType;
    }

    public void setRelatedObjectType(EnumRelatedObjectType related_object_type) {
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

    public void setValidatorType(EnumValidatorType validatorType) {
        this.validatorType = validatorType;
    }

    public EnumValidatorType getValidatorType() {
        return validatorType;
    }

    public void setValidator(Long validator) {
        this.validator = validator;
    }

    public Long getValidator() {
        return validator;
    }

    public void setCustomer(Long customer) {
        this.customer = customer;
    }

    public Long getCustomer() {
        return customer;
    }

    public void setLog_RelatedObject_lable(String log_relatedObject_lable) {
        this.log_RelatedObject_lable = log_relatedObject_lable;
    }

    public String getLog_RelatedObject_lable() {
        return log_RelatedObject_lable;
    }

    public void setLog_RelatedObject_code(String log_relatedObject_code) {
        this.log_RelatedObject_code = log_relatedObject_code;
    }

    public String getLog_RelatedObject_code() {
        return log_RelatedObject_code;
    }

    public void setLog_RelatedObject_type(EnumVoucherType log_relatedObject_type) {
        this.log_RelatedObject_type = log_relatedObject_type;
    }

    public EnumVoucherType getLog_RelatedObject_type() {
        return log_RelatedObject_type;
    }

    public void setLog_Customer_name(String log_customer_name) {
        this.log_Customer_name = log_customer_name;
    }

    public String getLog_Customer_name() {
        return log_Customer_name;
    }

    public void setLog_Customer_code(String log_customer_code) {
        this.log_Customer_code = log_customer_code;
    }

    public String getLog_Customer_code() {
        return log_Customer_code;
    }

    public void setLog_Validator_user_name(String log_validator_user_name) {
        this.log_Validator_user_name = log_validator_user_name;
    }

    public String getLog_Validator_user_name() {
        return log_Validator_user_name;
    }

    public void setLog_Validator_user_code(String log_validator_user_code) {
        this.log_Validator_user_code = log_validator_user_code;
    }

    public String getLog_Validator_user_code() {
        return log_Validator_user_code;
    }

    public void setLog_Validator_device_name(String log_validator_device_name) {
        this.log_Validator_device_name = log_validator_device_name;
    }

    public String getLog_Validator_device_name() {
        return log_Validator_device_name;
    }

    public void setLog_Validator_device_code(String log_validator_device_code) {
        this.log_Validator_device_code = log_validator_device_code;
    }

    public String getLog_Validator_device_code() {
        return log_Validator_device_code;
    }

    public void setLog_Validator_device_type(EnumDeviceType log_validator_device_type) {
        this.log_Validator_device_type = log_validator_device_type;
    }

    public EnumDeviceType getLog_Validator_device_type() {
        return log_Validator_device_type;
    }
}
