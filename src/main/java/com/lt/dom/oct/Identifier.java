package com.lt.dom.oct;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.lt.dom.otcenum.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
//https://api-docs.omnisend.com/reference/get-contacts
@Entity
public class Identifier extends Base{
    @JsonBackReference
    @ManyToOne
    @JoinColumn(name="contact", nullable=false)
    private Contact contact;




    private String linkId;

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    public String getLinkId() {
        return linkId;
    }

    public void setLinkId(String linkId) {
        this.linkId = linkId;
    }

    public EnumIdentifiersType getType() {
        return type;
    }

    public void setType(EnumIdentifiersType type) {
        this.type = type;
    }

    public EnumChannelStatus getStatus() {
        return status;
    }

    public void setStatus(EnumChannelStatus status) {
        this.status = status;
    }

    @NotNull
    @Enumerated(EnumType.STRING)
    private EnumIdentifiersType type;


    @NotNull
    @Enumerated(EnumType.STRING)
    public EnumChannelStatus status;


}
