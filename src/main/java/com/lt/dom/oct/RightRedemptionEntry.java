package com.lt.dom.oct;

import com.lt.dom.otcenum.EnumDeviceType;
import com.lt.dom.otcenum.EnumRelatedObjectType;
import com.lt.dom.otcenum.EnumValidatorType;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;


@Entity
public class RightRedemptionEntry extends Base{


    @NotNull
    private int redeemed_amount;

    private Long redeemed_quantity;

    @Enumerated(EnumType.STRING)
    @NotNull
    private EnumValidatorType validatorType;
    private long user;
    private Long device;



    @NotNull
    private Long component_right;



    private Long customer_id;


    @NotNull
    private long supplier;



    @NotNull
    
    @Column(unique=true)
    private String code;


    @NotNull
    private LocalDateTime createdAt;

    @Enumerated(EnumType.STRING)
    private EnumRelatedObjectType relatedObjectType;
    private Long relatedObjectId;
    private Long componentVoucher;

    @NotNull
    private Long redemption;
    private EnumDeviceType log_Verifier_device_type;
    private String log_Verifier_device_code;
    private String log_Verifier_device_name;
    private String log_Verifier_user_code;
    private String log_Verifier_user_name;
    private String log_Customer_name;
    private String log_Component_right_code;
    private String log_Component_right_name;


    public int getRedeemed_amount() {
        return redeemed_amount;
    }

    public void setRedeemed_amount(int redeemed_amount) {
        this.redeemed_amount = redeemed_amount;
    }

    public Long getRedeemed_quantity() {
        return redeemed_quantity;
    }

    public void setRedeemed_quantity(Long redeemed_quantity) {
        this.redeemed_quantity = redeemed_quantity;
    }

    public Long getComponent_right() {
        return component_right;
    }

    public void setComponent_right(Long component_right) {
        this.component_right = component_right;
    }

    public Long getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(Long customer_id) {
        this.customer_id = customer_id;
    }

    public long getSupplier() {
        return supplier;
    }

    public void setSupplier(long supplier) {
        this.supplier = supplier;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setValidatorType(EnumValidatorType validatorType) {
        this.validatorType = validatorType;
    }

    public EnumValidatorType getValidatorType() {
        return validatorType;
    }

    public void setUser(long user) {
        this.user = user;
    }

    public long getUser() {
        return user;
    }

    public void setDevice(Long device) {
        this.device = device;
    }

    public Long getDevice() {
        return device;
    }

    public void setRelatedObjectType(EnumRelatedObjectType related_object_type) {
        this.relatedObjectType = related_object_type;
    }

    public EnumRelatedObjectType getRelatedObjectType() {
        return relatedObjectType;
    }

    public void setRelatedObjectId(Long related_object_id) {
        this.relatedObjectId = related_object_id;
    }

    public Long getRelatedObjectId() {
        return relatedObjectId;
    }

    public void setComponentVoucher(Long componentVoucher) {

        this.componentVoucher = componentVoucher;
    }

    public Long getComponentVoucher() {
        return componentVoucher;
    }

    public void setRedemption(Long redemption) {
        this.redemption = redemption;
    }

    public Long getRedemption() {
        return redemption;
    }

    public void setLog_Verifier_device_type(EnumDeviceType log_verifier_device_type) {
        this.log_Verifier_device_type = log_verifier_device_type;
    }

    public EnumDeviceType getLog_Verifier_device_type() {
        return log_Verifier_device_type;
    }

    public void setLog_Verifier_device_code(String log_verifier_device_code) {
        this.log_Verifier_device_code = log_verifier_device_code;
    }

    public String getLog_Verifier_device_code() {
        return log_Verifier_device_code;
    }

    public void setLog_Verifier_device_name(String log_verifier_device_name) {
        this.log_Verifier_device_name = log_verifier_device_name;
    }

    public String getLog_Verifier_device_name() {
        return log_Verifier_device_name;
    }

    public void setLog_Verifier_user_code(String log_verifier_user_code) {
        this.log_Verifier_user_code = log_verifier_user_code;
    }

    public String getLog_Verifier_user_code() {
        return log_Verifier_user_code;
    }

    public void setLog_Verifier_user_name(String log_verifier_user_name) {
        this.log_Verifier_user_name = log_verifier_user_name;
    }

    public String getLog_Verifier_user_name() {
        return log_Verifier_user_name;
    }

    public void setLog_Customer_name(String log_customer_name) {
        this.log_Customer_name = log_customer_name;
    }

    public String getLog_Customer_name() {
        return log_Customer_name;
    }

    public void setLog_Component_right_code(String log_component_right_code) {
        this.log_Component_right_code = log_component_right_code;
    }

    public String getLog_Component_right_code() {
        return log_Component_right_code;
    }

    public void setLog_Component_right_name(String log_component_right_name) {
        this.log_Component_right_name = log_component_right_name;
    }

    public String getLog_Component_right_name() {
        return log_Component_right_name;
    }
}
