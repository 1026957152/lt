package com.lt.dom.oct;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.lt.dom.otcenum.EnumVoucherRedemptionStatus;

import javax.persistence.*;

@Entity
public class ComponentVounch {   // 这个是 下单的时候， 从 product 中生成 的
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    @JsonProperty("id")
    private long id;

    @Transient
    private RoyaltyRule royaltyRule;
    private long componentId;
    private long royaltyRuleId;
    private String snCode;
    private long reservationId;

    public RoyaltyRule getRoyaltyRule() {
        return royaltyRule;
    }

    public void setRoyaltyRule(RoyaltyRule royaltyRule) {
        this.royaltyRule = royaltyRule;
    }

    @Transient
    private ComponentRight componentRight;

    private long componentRightId;

    public long getComponentRightId() {
        return componentRightId;
    }

    public void setComponentRightId(long componentRightId) {
        this.componentRightId = componentRightId;
    }

    private String name;
    private EnumVoucherRedemptionStatus voucher_redemption_status;
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


    private int count;  //一次， 无数次，  五次，

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void setComponentId(long componentId) {
        this.componentId = componentId;
    }

    public long getComponentId() {
        return componentId;
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

    public void setReservationId(long reservationId) {
        this.reservationId = reservationId;
    }

    public long getReservationId() {
        return reservationId;
    }
}
