package com.lt.dom.oct;

import com.fasterxml.jackson.annotation.JsonProperty;


public class BookingAmendmentReq {


    @JsonProperty("note")
    private String note;
    @JsonProperty("notify")
    private Boolean notify;

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Boolean getNotify() {
        return notify;
    }

    public void setNotify(Boolean notify) {
        this.notify = notify;
    }
}
