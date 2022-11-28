package com.lt.dom.oct;

import com.lt.dom.otcenum.*;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
public class ComponentVounch extends Base{
    private String reference;   // 这个是 下单的时候， 从 product 中生成 的

    @Enumerated(EnumType.STRING)
    private EnumProductComponentSource source;
    private Long ratePlan;
    private Long subscription;
    private Long unit_off;

    @Enumerated(EnumType.STRING)
    private EnumRoyaltyRuleCategory royalty_mode;

    @Enumerated(EnumType.STRING)
    private EnumValidateWay validate_way;
    private String name;
    private Long product;

    public Long getBelong() {
        return belong;
    }

    public void setBelong(Long beLong) {
        this.belong = beLong;
    }

    public EnumBelongType getBelongType() {
        return belongType;
    }

    public void setBelongType(EnumBelongType beLongType) {
        this.belongType = beLongType;
    }



    @Transient
    private RoyaltyRule royaltyRule;


    private Long component;
    private Long royaltyRuleId;
    private String code;
    private Long reservation;


    private Long belong;
    @Enumerated(EnumType.STRING)
    private EnumBelongType belongType;
    @Enumerated(EnumType.STRING)
    @NotNull
    private EnumDuration duration;

   // @NotNull
    private Long user;

    @NotNull
    private Long supplier;
    private Long pass;

    public RoyaltyRule getRoyaltyRule() {
        return royaltyRule;
    }

    public void setRoyaltyRule(RoyaltyRule royaltyRule) {
        this.royaltyRule = royaltyRule;
    }


    private Long componentRight;

    public Long getComponentRight() {
        return componentRight;
    }

    public void setComponentRight(Long componentRightId) {
        this.componentRight = componentRightId;
    }
    @Enumerated(EnumType.STRING)
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
    private Long voucherId;

    public Long getVoucherId() {
        return voucherId;
    }

    public void setVoucherId(Long voucherId) {
        this.voucherId = voucherId;
    }




    private String note;


    private Long redeemed_quantity;// (integer, required) - How many times a voucher has already been redeemed.

    private Integer redeemed_amount;// (integer) - Total Amount redeemed by the voucher. Value is multiplied by 100 to precisely represent 2 decimal places. For example, $100 balance is written as 10000.

    @Column(name = "try_")
    private Long try_;  //一次， 无数次，  五次，


    @Column(name = "limit_")
    private Long limit;  //一次， 无数次，  五次，

    public Long getLimit() {
        return limit;
    }

    public Long getTry_() {
        return try_;
    }

    public void setTry_(Long try_) {
        this.try_ = try_;
    }

    public Long getRedeemed_quantity() {
        return redeemed_quantity;
    }

    public void setRedeemed_quantity(Long redeemed_quantity) {
        this.redeemed_quantity = redeemed_quantity;
    }

    public void setLimit(Long count) {
        this.limit = count;
    }

    public void setComponent(Long componentId) {
        this.component = componentId;
    }

    public Long getComponent() {
        return component;
    }

    public void setRoyaltyRuleId(Long royaltyRuleId) {
        this.royaltyRuleId = royaltyRuleId;
    }

    public Long getRoyaltyRuleId() {
        return royaltyRuleId;
    }

    public void setCode(String snCode) {
        this.code = snCode;
    }

    public String getCode() {
        return code;
    }

    public void setReservation(Long reservationId) {
        this.reservation = reservationId;
    }

    public Long getReservation() {
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

    public void setPass(Long pass) {
        this.pass = pass;
    }

    public Long getPass() {
        return pass;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getReference() {
        return reference;
    }

    public void setSource(EnumProductComponentSource source) {
        this.source = source;
    }

    public EnumProductComponentSource getSource() {
        return source;
    }

    public void setRatePlan(Long ratePlan) {
        this.ratePlan = ratePlan;
    }

    public Long getRatePlan() {
        return ratePlan;
    }

    public void setSubscription(Long subscription) {
        this.subscription = subscription;
    }

    public Long getSubscription() {
        return subscription;
    }

    public void setUnit_off(Long unit_off) {
        this.unit_off = unit_off;
    }

    public Long getUnit_off() {
        return unit_off;
    }

    public void setRoyalty_mode(EnumRoyaltyRuleCategory royalty_mode) {

        this.royalty_mode = royalty_mode;
    }

    public EnumRoyaltyRuleCategory getRoyalty_mode() {
        return royalty_mode;
    }

    public void setValidate_way(EnumValidateWay validate_way) {

        this.validate_way = validate_way;
    }

    public EnumValidateWay getValidate_way() {
        return validate_way;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }


    public void setProduct(Long product) {
        this.product = product;
    }

    public Long getProduct() {
        return product;
    }
}
