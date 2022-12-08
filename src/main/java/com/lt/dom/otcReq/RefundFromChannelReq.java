package com.lt.dom.otcReq;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.lt.dom.otcenum.EnumCancel_reason;
import com.lt.dom.otcenum.EnumPlatform;
import com.lt.dom.otcenum.EnumRefundStatus;

import javax.validation.constraints.NotNull;


public class RefundFromChannelReq {


    @NotNull
    private EnumCancel_reason reason;


    @JsonProperty("note")
    private String note;

    private String     message;//A custom message to send to the participants.
    private Boolean notifyParticipants;


/*    @NotNull
    private EnumRefundReason reason;*/

    public EnumCancel_reason getReason() {
        return reason;
    }

    public void setReason(EnumCancel_reason reason) {
        this.reason = reason;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean getNotifyParticipants() {
        return notifyParticipants;
    }

    public void setNotifyParticipants(Boolean notifyParticipants) {
        this.notifyParticipants = notifyParticipants;
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
