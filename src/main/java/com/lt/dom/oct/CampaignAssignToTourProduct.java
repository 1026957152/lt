package com.lt.dom.oct;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.lt.dom.otcenum.EnumOrderStatus;
import com.lt.dom.otcenum.EnumPaymentOption;
import com.lt.dom.otcenum.EnumProductType;

import javax.persistence.*;
import java.util.List;


@Entity
public class CampaignAssignToTourProduct {


    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @JsonProperty("id")
    private long id;


    private long campaign;
    private long product;
    private long tourId;

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
}
