package com.lt.dom.OctResp;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.lt.dom.oct.Attraction;
import com.lt.dom.oct.Base;
import com.lt.dom.oct.Knowfor;
import com.lt.dom.otcenum.EnumKnownfor;

import javax.persistence.*;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class KnowforResp {


    private EnumKnownfor knownfor;

    private String text;
    private String knownfor_text;

    public static KnowforResp from(Knowfor e) {
        KnowforResp knowforResp = new KnowforResp();
        knowforResp.setKnownfor_text(e.getKnownfor().toString());
        knowforResp.setText(e.getText());
        return knowforResp;
    }

    public EnumKnownfor getKnownfor() {
        return knownfor;
    }

    public void setKnownfor(EnumKnownfor knownfor) {
        this.knownfor = knownfor;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setKnownfor_text(String knownfor_text) {
        this.knownfor_text = knownfor_text;
    }

    public String getKnownfor_text() {
        return knownfor_text;
    }
}
