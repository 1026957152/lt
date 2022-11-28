package com.lt.dom.OctResp;

import com.lt.dom.oct.Refund;
import com.lt.dom.otcenum.*;

import java.time.LocalDateTime;


public class RefundResp {


    private EnumRefundReason reason;
    private EnumRefundStatus status;

    private long failure_balance_transaction;
    private LocalDateTime created;


    private int amount;
    //##@Column(unique=true)
    private String code;
    private String charge_code;
    private String status_text;
    private String reason_text;
    private String transactionId;
    private String return_msg;
    private int refund_fee;
    private EnumRefundSourceType sourceType;
    private EnumRefundType refundType;
    private EnumPayChannel channel;
    private String channel_text;
    private Boolean unlinked;
    private EnumPlatform platform;
    private String platform_text;

    public static RefundResp from(Refund x) {
        RefundResp resp = new RefundResp();
        resp.setAmount(x.getAmount());
        resp.setCode(x.getCode());
        resp.setReason(x.getReason());
        resp.setReason_text(x.getReason().toString());
        resp.setStatus(x.getStatus());
        resp.setStatus_text(x.getStatus().toString());
        resp.setCharge_code(x.getCharge_Code());
        resp.setCreated_at(x.getCreated());
        resp.setReturn_msg(x.getReturn_msg());
        resp.setRefund_fee(x.getRefund_fee());
        resp.setTransactionId(x.getTransactionId());
        resp.setRefundType(x.getRefundType());
        resp.setUnlinked(x.getUnlinked());
        resp.setSourceType(x.getSourceType());

        resp.setPlatform(x.getPlatform());
        resp.setPlatform_text(x.getPlatform().toString());
        if(!x.getUnlinked()){
            resp.setChannel_text(x.getChannel().toString());
            resp.setChannel(x.getChannel());
        }
        return resp;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }





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

    public void setCreated_at(LocalDateTime created) {
        this.created = created;
    }



    public void setCode(String code) {

        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCharge_code(String charge_code) {

        this.charge_code = charge_code;
    }

    public String getCharge_code() {
        return charge_code;
    }

    public void setStatus_text(String status_text) {
        this.status_text = status_text;
    }

    public String getStatus_text() {
        return status_text;
    }

    public void setReason_text(String reason_text) {
        this.reason_text = reason_text;
    }

    public String getReason_text() {
        return reason_text;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setReturn_msg(String return_msg) {
        this.return_msg = return_msg;
    }

    public String getReturn_msg() {
        return return_msg;
    }

    public void setRefund_fee(int refund_fee) {
        this.refund_fee = refund_fee;
    }

    public int getRefund_fee() {
        return refund_fee;
    }

    public void setSourceType(EnumRefundSourceType sourceType) {
        this.sourceType = sourceType;
    }

    public EnumRefundSourceType getSourceType() {
        return sourceType;
    }

    public void setRefundType(EnumRefundType refundType) {
        this.refundType = refundType;
    }

    public EnumRefundType getRefundType() {
        return refundType;
    }

    public void setChannel(EnumPayChannel channel) {
        this.channel = channel;
    }

    public EnumPayChannel getChannel() {
        return channel;
    }

    public void setChannel_text(String channel_text) {
        this.channel_text = channel_text;
    }

    public String getChannel_text() {
        return channel_text;
    }

    public void setUnlinked(Boolean unlinked) {
        this.unlinked = unlinked;
    }

    public Boolean getUnlinked() {
        return unlinked;
    }

    public void setPlatform(EnumPlatform platform) {
        this.platform = platform;
    }

    public EnumPlatform getPlatform() {
        return platform;
    }

    public void setPlatform_text(String platform_text) {
        this.platform_text = platform_text;
    }

    public String getPlatform_text() {
        return platform_text;
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
