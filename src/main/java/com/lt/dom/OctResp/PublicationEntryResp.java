package com.lt.dom.OctResp;


import com.lt.dom.otcenum.EnumPublicationObjectType;

import javax.persistence.*;
import java.time.LocalDate;


public class PublicationEntryResp {



    private String source_id;




    private long publicationId;



    private EnumPublicationObjectType type;
    private String campaign;

    public EnumPublicationObjectType getType() {
        return type;
    }

    public void setType(EnumPublicationObjectType type) {
        this.type = type;
    }

    private long voucherId;


    private long customerId;


    public LocalDate published_at;

    public String getSource_id() {
        return source_id;
    }

    public void setSource_id(String source_id) {
        this.source_id = source_id;
    }



    public long getPublicationId() {
        return publicationId;
    }

    public void setPublicationId(long publicationId) {
        this.publicationId = publicationId;
    }

    public long getVoucherId() {
        return voucherId;
    }

    public void setVoucherId(long voucher_id) {
        this.voucherId = voucher_id;
    }

    public long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(long customerId) {
        this.customerId = customerId;
    }

    public LocalDate getPublished_at() {
        return published_at;
    }

    public void setPublished_at(LocalDate published_at) {
        this.published_at = published_at;
    }

    public void setCampaign(String campaign) {
        this.campaign = campaign;
    }

    public String getCampaign() {
        return campaign;
    }
}
