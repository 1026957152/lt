package com.lt.dom.otcReq;


import javax.annotation.Nonnull;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


public class ScenarioReq {


    private String name;

    private String note;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
