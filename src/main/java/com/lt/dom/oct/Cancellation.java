package com.lt.dom.oct;

import com.lt.dom.otcenum.EnumCancel_reason;
import com.lt.dom.otcenum.EnumValidatorRedemExtent;

import javax.persistence.*;
import java.util.List;


@Entity
public class Cancellation extends Base{



    private EnumCancel_reason cancel_reason;
    private String note;
    private long booking;
    private Boolean notifyParticipants;




    public EnumCancel_reason getCancel_reason() {
        return cancel_reason;
    }

    public void setCancel_reason(EnumCancel_reason cancel_reason) {
        this.cancel_reason = cancel_reason;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public void setBooking(long booking) {

        this.booking = booking;
    }

    public long getBooking() {
        return booking;
    }

    public void setNotifyParticipants(Boolean notifyParticipants) {
        this.notifyParticipants = notifyParticipants;
    }

    public Boolean getNotifyParticipants() {
        return notifyParticipants;
    }
}
