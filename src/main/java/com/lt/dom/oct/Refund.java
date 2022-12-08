package com.lt.dom.oct;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.lt.dom.otcenum.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Entity
public class Refund extends Base{

    @Enumerated(EnumType.STRING)
    private EnumRefundReason reason;

    @Enumerated(EnumType.STRING)
    private EnumRefundStatus status;

    @Enumerated(EnumType.STRING)
    private EnumRefundType refundType;

    @Enumerated(EnumType.STRING)
    private EnumRefundSourceType sourceType;
    private Long booking;



    private Boolean unlinked;

    @NotNull
    private EnumPlatform platform;

    public EnumRefundSourceType getSourceType() {
        return sourceType;
    }

    public void setSourceType(EnumRefundSourceType sourceType) {
        this.sourceType = sourceType;
    }

    public EnumRefundType getRefundType() {
        return refundType;
    }

    public void setRefundType(EnumRefundType refundType) {
        this.refundType = refundType;
    }

    private long failure_balance_transaction;
    private LocalDateTime created;


    @OneToMany(mappedBy="refund",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    List<RefundLineItem> refundLineItems;

    private int amount;
    
//##@Column(unique=true)
private String code;
    private String charge_Code;
    private int refund_fee;
    private EnumPayChannel channel;
    private String TransactionId;
    private String return_msg;

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    private long charge;




    public EnumRefundReason getReason() {
        return reason;
    }

    public void setReason(EnumRefundReason reason) {
        this.reason = reason;
    }

    public EnumRefundStatus getStatus() {
        return status;
    }

    public void setStatus(EnumRefundStatus status) {
        this.status = status;
    }

    public long getFailure_balance_transaction() {
        return failure_balance_transaction;
    }

    public void setFailure_balance_transaction(long failure_balance_transaction) {
        this.failure_balance_transaction = failure_balance_transaction;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public void setCharge(long charge) {
        this.charge = charge;
    }

    public long getCharge() {
        return charge;
    }

    public void setCode(String code) {

        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCharge_Code(String charge_code) {
        this.charge_Code = charge_code;
    }

    public String getCharge_Code() {
        return charge_Code;
    }

    public void setRefund_fee(int refund_fee) {

        this.refund_fee = refund_fee;
    }

    public int getRefund_fee() {
        return refund_fee;
    }

    public void setChannel(EnumPayChannel channel) {
        this.channel = channel;
    }

    public EnumPayChannel getChannel() {
        return channel;
    }

    public void setTransactionId(String rransactionId) {
        this.TransactionId = rransactionId;
    }

    public String getTransactionId() {
        return TransactionId;
    }

    public void setReturn_msg(String return_msg) {
        this.return_msg = return_msg;
    }

    public String getReturn_msg() {
        return return_msg;
    }

    public void setBooking(Long booking) {
        this.booking = booking;
    }

    public Long getBooking() {
        return booking;
    }

    public Boolean getUnlinked() {
        return unlinked;
    }

    public void setUnlinked(Boolean unlinked) {
        this.unlinked = unlinked;
    }

    public void setPlatform(EnumPlatform platform) {
        this.platform = platform;
    }

    public EnumPlatform getPlatform() {
        return platform;
    }

/*    "id": "re_y1u944PmfnrTHyvnL0nD0iD1",
              "object": "refund",
              "order_no": "y1u944PmfnrTHyvnL0nD0iD1",
              "amount": 1,
              "created": 1409634160,
              "succeed": true,
              "status": "succeeded",
              "time_succeed": 1409634192,
              "description": "Refund Description",
              "failure_code": null,
              "failure_msg": null,
              "metadata": {},
            "charge": "ch_L8qn10mLmr1GS8e5OODmHaL4",
            "charge_order_no": "123456789",
            "transaction_no": "2004450349201512090096425284",
            "funding_source": null,
            "extra": {}*/
}
