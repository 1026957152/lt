package com.lt.dom.otcReq;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.lt.dom.otcenum.EnumCancel_reason;


public class BookingCancelPojo {


    private EnumCancel_reason reason;


    @JsonProperty("note")
    private String note;

    private String     message;//A custom message to send to the participants.
    private Boolean notifyParticipants;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Boolean getNotifyParticipants() {
        return notifyParticipants;
    }

    public void setNotifyParticipants(Boolean notifyParticipants) {
        this.notifyParticipants = notifyParticipants;
    }

    public EnumCancel_reason getReason() {
        return reason;
    }

    public void setReason(EnumCancel_reason reason) {
        this.reason = reason;
    }
}
