package com.lt.dom.oct;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;


@Entity
public class CampaignAssignToTourProduct {


    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @JsonProperty("id")
    private long id;

    @NotNull
    private long campaign;
    @NotNull
    private long product;
    private long tourId;
    @NotNull
    private String campaign_code;
    private long tour_booking;

    public long getCampaign() {
        return campaign;
    }

    public long getProduct() {
        return product;
    }

    public void setProduct(long product) {
        this.product = product;
    }

    private String additional_info;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


    private long redeemer;

    public long getRedeemer() {
        return redeemer;
    }

    public void setRedeemer(long redeemer) {
        this.redeemer = redeemer;
    }

    public void setCampaign(long campaign) {
        this.campaign = campaign;
    }

    public void setAdditional_info(String additional_info) {
        this.additional_info = additional_info;
    }

    public String getAdditional_info() {
        return additional_info;
    }

    public void setTourId(long tourId) {
        this.tourId = tourId;
    }

    public long getTourId() {
        return tourId;
    }

    public void setCampaign_code(String campaign_code) {
        this.campaign_code = campaign_code;
    }

    public String getCampaign_code() {
        return campaign_code;
    }

    public void setTour_booking(long tour_booking) {
        this.tour_booking = tour_booking;
    }

    public long getTour_booking() {
        return tour_booking;
    }
}
