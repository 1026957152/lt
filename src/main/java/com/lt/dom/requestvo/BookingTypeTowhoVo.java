package com.lt.dom.requestvo;

import com.lt.dom.oct.*;
import com.lt.dom.otcReq.BookingSkuPojo;
import com.lt.dom.otcenum.EnumBookingOjbectType;
import com.lt.dom.otcenum.EnumDeliveryFormats;
import com.lt.dom.vo.NegotiatedPricingType;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

public class BookingTypeTowhoVo {


    private EnumBookingOjbectType toWhoTyp;
    private List<BookingSkuPojo.TravelerReq> traveler;
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
    private PricingRate sku;
    private NegotiatedPricingType negotiatedSku;
    private EnumDeliveryFormats deliveryFormat;
    private String remark;
    private Float base_cost_price;
    private Integer retail;

    public List<Sku> getSkus() {
        return skus;
    }

    public void setSkus(List<Sku> skus) {
        this.skus = skus;
    }

    public void setSku(PricingRate sku) {
        this.sku = sku;
    }

    public PricingRate getSku() {
        return sku;
    }

    public void setNegotiatedSku(NegotiatedPricingType negotiatedSku) {
        this.negotiatedSku = negotiatedSku;
    }

    public NegotiatedPricingType getNegotiatedSku() {
        return negotiatedSku;
    }

    public void setDeliveryFormat(EnumDeliveryFormats deliveryFormat) {
        this.deliveryFormat = deliveryFormat;
    }

    public EnumDeliveryFormats getDeliveryFormat() {
        return deliveryFormat;
    }

    public void setRemark(String remark) {

        this.remark = remark;
    }

    public String getRemark() {
        return remark;
    }

    public Float getBase_cost_price() {
        return base_cost_price;
    }

    public void setBase_cost_price(Float base_cost_price) {
        this.base_cost_price = base_cost_price;
    }

    public Integer getRetail() {
        return retail;
    }

    public void setRetail(Integer retail) {
        this.retail = retail;
    }

    public static class Sku {
        @NotEmpty
        private PricingRate id;
        @NotNull
        private Integer quantity;

        public PricingRate getId() {
            return id;
        }

        public void setId(PricingRate id) {
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

    public void setTraveler(List<BookingSkuPojo.TravelerReq> traveler) {
        this.traveler = traveler;
    }

    public List<BookingSkuPojo.TravelerReq> getTraveler() {
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
