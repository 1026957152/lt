package com.lt.dom.oct;


import com.lt.dom.otcenum.EnumAssociatedType;
import com.lt.dom.otcenum.EnumPublicationObjectType;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
public class PublicationEntry {
    @Version
    private Integer version;
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private long id;

    private String source_id;

    @NotNull
    private long campaign;



    @NotNull
    private long publication;

    @NotNull
    private long associatedId;

    @NotNull
    private EnumAssociatedType associatedType;
    private boolean free;
    private long charge;
    private boolean paied;
    private String session_ip;

    public long getAssociatedId() {
        return associatedId;
    }

    public void setAssociatedId(long associated_id) {
        this.associatedId = associated_id;
    }

    public EnumAssociatedType getAssociatedType() {
        return associatedType;
    }

    public void setAssociatedType(EnumAssociatedType associated_type) {
        this.associatedType = associated_type;
    }

    @NotNull
    private EnumPublicationObjectType toWhoType;



    public EnumPublicationObjectType getToWhoType() {
        return toWhoType;
    }

    public void setToWhoType(EnumPublicationObjectType toWhoType) {
        this.toWhoType = toWhoType;
    }

    @NotNull
    private long voucher;


    @NotNull
    private long toWho;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LocalDateTime published_at;

    public String getSource_id() {
        return source_id;
    }

    public void setSource_id(String source_id) {
        this.source_id = source_id;
    }

    public long getCampaign() {
        return campaign;
    }

    public void setCampaign(long campaign_id) {
        this.campaign = campaign_id;
    }

    public long getPublication() {
        return publication;
    }

    public void setPublication(long publicationId) {
        this.publication = publicationId;
    }

    public long getVoucher() {
        return voucher;
    }

    public void setVoucher(long voucher_id) {
        this.voucher = voucher_id;
    }

    public long getToWho() {
        return toWho;
    }

    public void setToWho(long toWho) {
        this.toWho = toWho;
    }

    public LocalDateTime getPublished_at() {
        return published_at;
    }

    public void setPublished_at(LocalDateTime published_at) {
        this.published_at = published_at;
    }

    public void setFree(boolean free) {
        this.free = free;
    }

    public boolean getFree() {
        return free;
    }

    public void setCharge(long charge) {
        this.charge = charge;
    }

    public long getCharge() {
        return charge;
    }

    public void setPaied(boolean paied) {
        this.paied = paied;
    }

    public boolean getPaied() {
        return paied;
    }

    public void setSession_ip(String session_ip) {
        this.session_ip = session_ip;
    }

    public String getSession_ip() {
        return session_ip;
    }
}
