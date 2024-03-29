package com.lt.dom.otcReq;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.lt.dom.otcenum.EnumCancel_reason;
import com.lt.dom.otcenum.EnumPlatform;
import com.lt.dom.otcenum.EnumRefundReason;
import com.lt.dom.otcenum.EnumRefundStatus;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;


public class RefundReq {


    @NotNull
    private EnumCancel_reason reason;


    @JsonProperty("note")
    private String note;

    private String     message;//A custom message to send to the participants.
    private Boolean notifyParticipants;


/*    @NotNull
    private EnumRefundReason reason;*/
    private EnumRefundStatus status;

    private long failure_balance_transaction;




    private Integer refund_fee;



    private long charge;
    private boolean unlinked;
    private EnumPlatform platform;


/*
    public EnumRefundReason getReason() {
        return reason;
    }

    public void setReason(EnumRefundReason reason) {
        this.reason = reason;
    }
*/

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



    public void setCharge(long charge) {
        this.charge = charge;
    }

    public long getCharge() {
        return charge;
    }



    public void setRefund_fee(Integer refund_fee) {

        this.refund_fee = refund_fee;
    }

    public Integer getRefund_fee() {
        return refund_fee;
    }

    public void setUnlinked(boolean unlinked) {
        this.unlinked = unlinked;
    }

    public boolean isUnlinked() {
        return unlinked;
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
