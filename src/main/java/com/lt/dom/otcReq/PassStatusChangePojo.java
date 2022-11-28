package com.lt.dom.otcReq;


import com.lt.dom.otcenum.EnumCardStatus;
import com.lt.dom.otcenum.EnumReasonCode;

public class PassStatusChangePojo {


    private EnumReasonCode status_reason_code
            ;
    private EnumCardStatus status;
    private String note;

    public EnumReasonCode getStatus_reason_code() {
        return status_reason_code;
    }

    public void setStatus_reason_code(EnumReasonCode status_reason_code) {
        this.status_reason_code = status_reason_code;
    }

    public EnumCardStatus getStatus() {
        return status;
    }

    public void setStatus(EnumCardStatus status) {
        this.status = status;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
