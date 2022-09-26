package com.lt.dom.requestvo;

import com.lt.dom.oct.*;
import com.lt.dom.otcReq.BookingPojo;
import com.lt.dom.otcenum.EnumBookingOjbectType;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

public class BookingTypeTowhoVo {


    private EnumBookingOjbectType toWhoTyp;
    private Traveler traveler;
    private Supplier supplier;
    private User user;
    private long toWho;
    private Tour tour;
    private Campaign campaign;
    private Product product;
    private Long quantity;




    @Valid
    @Size(min=0,max=200)
    private List<Sku> skus;

    public List<Sku> getSkus() {
        return skus;
    }

    public void setSkus(List<Sku> skus) {
        this.skus = skus;
    }

    public static class Sku {
        @NotEmpty
        private PricingType id;
        @NotNull
        private Integer quantity;

        public PricingType getId() {
            return id;
        }

        public void setId(PricingType id) {
            this.id = id;
        }

        public Integer getQuantity() {
            return quantity;
        }

        public void setQuantity(Integer quantity) {
            this.quantity = quantity;
        }
    }



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

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public Long getQuantity() {
        return quantity;
    }
}
