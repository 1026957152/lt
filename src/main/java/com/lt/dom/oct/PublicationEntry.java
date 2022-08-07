package com.lt.dom.oct;


import com.lt.dom.OctResp.PublicationResp;
import com.lt.dom.otcenum.EnumPublicationObjectType;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Entity
public class PublicationEntry {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private long id;

    private String source_id;

    @NotNull
    private long campaign_id;



    @NotNull
    private long publicationId;


    @NotNull
    private EnumPublicationObjectType toWhoType;



    public EnumPublicationObjectType getToWhoType() {
        return toWhoType;
    }

    public void setToWhoType(EnumPublicationObjectType toWhoType) {
        this.toWhoType = toWhoType;
    }

    @NotNull
    private long voucherId;


    @NotNull
    private long toWho;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LocalDate published_at;

    public String getSource_id() {
        return source_id;
    }

    public void setSource_id(String source_id) {
        this.source_id = source_id;
    }

    public long getCampaign_id() {
        return campaign_id;
    }

    public void setCampaign_id(long campaign_id) {
        this.campaign_id = campaign_id;
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

    public long getToWho() {
        return toWho;
    }

    public void setToWho(long toWho) {
        this.toWho = toWho;
    }

    public LocalDate getPublished_at() {
        return published_at;
    }

    public void setPublished_at(LocalDate published_at) {
        this.published_at = published_at;
    }
}
