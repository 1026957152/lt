package com.lt.dom.otcReq;


import javax.validation.constraints.NotNull;


public class ClainRedeemAssignmentReq {



    @NotNull
    private String value;


    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
