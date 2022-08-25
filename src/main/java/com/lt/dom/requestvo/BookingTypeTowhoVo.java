package com.lt.dom.requestvo;

import com.lt.dom.oct.*;
import com.lt.dom.otcenum.EnumBookingOjbectType;
import com.lt.dom.otcenum.EnumPublicationObjectType;

public class BookingTypeTowhoVo {


    private EnumBookingOjbectType toWhoTyp;
    private Traveler traveler;
    private Supplier supplier;
    private User user;
    private long toWho;
    private Tour tour;
    private Campaign campaign;
    private Product product;

    public long getToWho() {
        return toWho;
    }

    public void setToWho(long toWho) {
        this.toWho = toWho;
    }

    public void setToWhoTyp(EnumBookingOjbectType toWhoTyp) {
        this.toWhoTyp = toWhoTyp;
    }

    public EnumBookingOjbectType getToWhoTyp() {
        return toWhoTyp;
    }

    public void setTraveler(Traveler traveler) {
        this.traveler = traveler;
    }

    public Traveler getTraveler() {
        return traveler;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setTour(Tour tour) {
        this.tour = tour;
    }

    public Tour getTour() {
        return tour;
    }

    public void setCampaign(Campaign campaign) {
        this.campaign = campaign;
    }

    public Campaign getCampaign() {
        return campaign;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Product getProduct() {
        return product;
    }
}
