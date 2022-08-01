package com.lt.dom.notification;

import com.lt.dom.oct.*;
import com.lt.dom.otcenum.EnumPublicationObjectType;

import java.util.List;

public class VouncherPublishedVo {
	
    private long id;
    private String name;
    private String email;
    private String mobile;
    private List<Component> components;
    private User user;
    private PublicationEntry publicationEntry;
    private Publication publication;
    private Voucher voucher;
    private Supplier supplier;
    private EnumPublicationObjectType publicationToType;

    public void setComponents(List<Component> components) {

        this.components = components;
    }

    public List<Component> getComponents() {
        return components;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setPublicationEntry(PublicationEntry publicationEntry) {
        this.publicationEntry = publicationEntry;
    }

    public PublicationEntry getPublicationEntry() {
        return publicationEntry;
    }

    public void setPublication(Publication publication) {
        this.publication = publication;
    }

    public Publication getPublication() {
        return publication;
    }

    public void setVoucher(Voucher voucher) {
        this.voucher = voucher;
    }

    public Voucher getVoucher() {
        return voucher;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setPublicationToType(EnumPublicationObjectType publicationToType) {
        this.publicationToType = publicationToType;
    }

    public EnumPublicationObjectType getPublicationToType() {
        return publicationToType;
    }


    // getter and setter methods
}