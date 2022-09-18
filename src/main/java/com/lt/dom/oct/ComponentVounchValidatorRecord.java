package com.lt.dom.oct;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.lt.dom.otcenum.EnumComponentVoucherStatus;
import com.lt.dom.otcenum.EnumDuration;
import com.lt.dom.otcenum.EnumValidatorType;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
public class ComponentVounchValidatorRecord {   // 这个是 下单的时候， 从 product 中生成 的

    @Version
    private Integer version;
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @JsonProperty("id")
    private long id;

    private long userId;

    private long device;
    private long validatorGroupId;
    private long role;
    private String holder_name;
    private String holder_id;

    public long getRole() {
        return role;
    }

    public void setRole(long role) {
        this.role = role;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getDevice() {
        return device;
    }

    public void setDevice(long device) {
        this.device = device;
    }

    public long getValidatorGroupId() {
        return validatorGroupId;
    }

    public void setValidatorGroupId(long validatorGroupId) {
        this.validatorGroupId = validatorGroupId;
    }

    private EnumValidatorType validatorType; //指定人工，机器, 所有人工

    public EnumValidatorType getValidatorType() {
        return validatorType;
    }

    public void setValidatorType(EnumValidatorType validatorType) {
        this.validatorType = validatorType;
    }

    public String getRedeem_voucher_key() {
        return redeem_voucher_key;
    }

    public void setRedeem_voucher_key(String redeem_voucher_key) {
        this.redeem_voucher_key = redeem_voucher_key;
    }

    private Long component;
    private long royaltyRuleId;
    private String snCode;
    private long reservation;



    private Long user;


    private Long supplier;
    private long pass;



    private long componentRight;

    public long getComponentRight() {
        return componentRight;
    }

    public void setComponentRight(long componentRightId) {
        this.componentRight = componentRightId;
    }

    @NotNull
    private EnumComponentVoucherStatus status;

    public EnumComponentVoucherStatus getStatus() {
        return status;
    }

    public void setStatus(EnumComponentVoucherStatus status) {
        this.status = status;
    }

    private String redeem_voucher_key;

    private long voucherId;

    public long getVoucherId() {
        return voucherId;
    }

    public void setVoucherId(long voucherId) {
        this.voucherId = voucherId;
    }


    private String note;


    private Long redeemed_quantity;// (integer, required) - How many times a voucher has already been redeemed.

    private Integer redeemed_amount;// (integer) - Total Amount redeemed by the voucher. Value is multiplied by 100 to precisely represent 2 decimal places. For example, $100 balance is written as 10000.



    private Long count;  //一次， 无数次，  五次，

    public Long getCount() {
        return count;
    }

    public Long getRedeemed_quantity() {
        return redeemed_quantity;
    }

    public void setRedeemed_quantity(Long redeemed_quantity) {
        this.redeemed_quantity = redeemed_quantity;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public void setComponent(Long componentId) {
        this.component = componentId;
    }

    public Long getComponent() {
        return component;
    }

    public void setRoyaltyRuleId(long royaltyRuleId) {
        this.royaltyRuleId = royaltyRuleId;
    }

    public long getRoyaltyRuleId() {
        return royaltyRuleId;
    }

    public void setSnCode(String snCode) {
        this.snCode = snCode;
    }

    public String getSnCode() {
        return snCode;
    }

    public void setReservation(long reservationId) {
        this.reservation = reservationId;
    }

    public long getReservation() {
        return reservation;
    }



    public void setUser(Long user) {
        this.user = user;
    }

    public Long getUser() {
        return user;
    }

    public void setSupplier(Long supplier) {
        this.supplier = supplier;
    }

    public Long getSupplier() {
        return supplier;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }

    public void setPass(long pass) {
        this.pass = pass;
    }

    public long getPass() {
        return pass;
    }

    public void setHolder_name(String holder_name) {
        this.holder_name = holder_name;
    }

    public String getHolder_name() {
        return holder_name;
    }

    public void setHolder_id(String holder_id) {
        this.holder_id = holder_id;
    }

    public String getHolder_id() {
        return holder_id;
    }
}
