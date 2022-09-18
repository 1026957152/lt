package com.lt.dom.oct;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.lt.dom.otcenum.EnumComponentVoucherStatus;
import com.lt.dom.otcenum.EnumDuration;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
public class ComponentVounch {   // 这个是 下单的时候， 从 product 中生成 的

    @Version
    private Integer version;
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @JsonProperty("id")
    private long id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Transient
    private RoyaltyRule royaltyRule;


    private Long component;
    private long royaltyRuleId;
    private String snCode;
    private long reservation;

    @NotNull
    private EnumDuration duration;

    @NotNull
    private Long user;

    @NotNull
    private Long supplier;
    private long pass;

    public RoyaltyRule getRoyaltyRule() {
        return royaltyRule;
    }

    public void setRoyaltyRule(RoyaltyRule royaltyRule) {
        this.royaltyRule = royaltyRule;
    }


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
    @Transient
    private Voucher voucher;
    private long voucherId;

    public long getVoucherId() {
        return voucherId;
    }

    public void setVoucherId(long voucherId) {
        this.voucherId = voucherId;
    }

    @Transient
    private Product product;


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

    public void setDuration(EnumDuration duration) {
        this.duration = duration;
    }

    public EnumDuration getDuration() {
        return duration;
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
}
