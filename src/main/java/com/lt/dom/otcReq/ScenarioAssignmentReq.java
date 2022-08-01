package com.lt.dom.otcReq;


import javax.annotation.Nonnull;


public class ScenarioAssignmentReq {




    @Nonnull
    private Integer supplier;
    private String note;



    public Integer getSupplier() {
        return supplier;
    }

    public void setSupplier(Integer supplier) {
        this.supplier = supplier;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
