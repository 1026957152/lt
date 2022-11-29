package com.lt.dom.otcReq;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.lt.dom.oct.Base;
import com.lt.dom.oct.Contact;
import com.lt.dom.oct.Identifier;
import com.lt.dom.otcenum.EnumChannelStatus;
import com.lt.dom.otcenum.EnumIdentifiersType;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

//https://api-docs.omnisend.com/reference/get-contacts
public class IdentifierReq {



    private String id;

    public static IdentifierReq from(Identifier e) {
        IdentifierReq identifierReq = new IdentifierReq();
        identifierReq.setId(e.getLinkId());
        identifierReq.setType(e.getType());

        return identifierReq;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public EnumIdentifiersType getType() {
        return type;
    }

    public void setType(EnumIdentifiersType type) {
        this.type = type;
    }


    @NotNull
    @Enumerated(EnumType.STRING)
    private EnumIdentifiersType type;



}
