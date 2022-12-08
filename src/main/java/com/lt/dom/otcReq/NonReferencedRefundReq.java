package com.lt.dom.otcReq;

import com.lt.dom.otcenum.EnumPayChannel;
import com.lt.dom.otcenum.EnumRefundReason;
import com.lt.dom.otcenum.EnumRefundStatus;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;


public class NonReferencedRefundReq {



    @NotNull
    private EnumRefundReason reason;


    private EnumPayChannel channel;
    private LocalDateTime refundDate;

    public LocalDateTime getRefundDate() {
        return refundDate;
    }

    public void setRefundDate(LocalDateTime refundDate) {
        this.refundDate = refundDate;
    }

    private String comment;
    private String note;

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }


    private Integer amount;

    public EnumPayChannel getChannel() {
        return channel;
    }

    public void setChannel(EnumPayChannel channel) {
        this.channel = channel;
    }

    private Integer refund_fee;



    public EnumRefundReason getReason() {
        return reason;
    }

    public void setReason(EnumRefundReason reason) {
        this.reason = reason;
    }



    public void setRefund_fee(Integer refund_fee) {

        this.refund_fee = refund_fee;
    }

    public Integer getRefund_fee() {
        return refund_fee;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
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
