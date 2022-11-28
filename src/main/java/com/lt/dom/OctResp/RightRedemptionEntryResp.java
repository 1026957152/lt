package com.lt.dom.OctResp;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.lt.dom.oct.Base;
import com.lt.dom.oct.RightRedemptionEntry;
import com.lt.dom.otcenum.EnumRelatedObjectType;
import com.lt.dom.otcenum.EnumValidatorType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import java.time.LocalDateTime;


@JsonInclude(JsonInclude.Include.NON_NULL)
public class RightRedemptionEntryResp {



    private Integer redeemed_amount;

    private Long redeemed_quantity;


    private EnumValidatorType validatorType;
    private Long user;
    private Long device;



    private Long component_right;



    private Long customer_id;


    private Long supplier;



    private String code;



    private LocalDateTime createdAt;

    private EnumRelatedObjectType relatedObjectType;
    private Long relatedObjectId;
    private ComponentVounchResp componentVoucher;
    private String component_right_code;
    private String component_right_name;
    public static RightRedemptionEntryResp PcFrom(RightRedemptionEntry e) {

        RightRedemptionEntryResp redemptionEntryResp = new RightRedemptionEntryResp();
        redemptionEntryResp.setCode(e.getCode());
        redemptionEntryResp.setValidatorType(e.getValidatorType());
        redemptionEntryResp.setUser(e.getUser());
        redemptionEntryResp.setDevice(e.getDevice());
        redemptionEntryResp.setComponent_right_name(e.getLog_Component_right_name());
        redemptionEntryResp.setComponent_right_code(e.getLog_Component_right_code());

        //    redemptionEntryResp.setComponent_right(e.getComponent_right());
        redemptionEntryResp.setRedeemed_quantity(e.getRedeemed_quantity());
        redemptionEntryResp.setCreatedAt(e.getCreatedDate());
        return redemptionEntryResp;
    }

    public static RightRedemptionEntryResp from(RightRedemptionEntry e) {

        RightRedemptionEntryResp redemptionEntryResp = new RightRedemptionEntryResp();
        redemptionEntryResp.setCode(e.getCode());
        redemptionEntryResp.setComponent_right_name(e.getLog_Component_right_name());
        redemptionEntryResp.setComponent_right_code(e.getLog_Component_right_code());
        redemptionEntryResp.setRedeemed_quantity(e.getRedeemed_quantity());
        redemptionEntryResp.setCreatedAt(e.getCreatedDate());
        return redemptionEntryResp;
    }


    public Integer getRedeemed_amount() {
        return redeemed_amount;
    }

    public void setRedeemed_amount(Integer redeemed_amount) {
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

    public Long getSupplier() {
        return supplier;
    }

    public void setSupplier(Long supplier) {
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

    public void setUser(Long user) {
        this.user = user;
    }

    public Long getUser() {
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

    public void setComponentVoucher(ComponentVounchResp componentVoucher) {

        this.componentVoucher = componentVoucher;
    }

    public ComponentVounchResp getComponentVoucher() {
        return componentVoucher;
    }

    public void setComponent_right_code(String component_right_code) {
        this.component_right_code = component_right_code;
    }

    public String getComponent_right_code() {
        return component_right_code;
    }

    public void setComponent_right_name(String component_right_name) {
        this.component_right_name = component_right_name;
    }

    public String getComponent_right_name() {
        return component_right_name;
    }
}
