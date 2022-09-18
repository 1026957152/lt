package com.lt.dom.oct;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.lt.dom.otcenum.EnumCampaignToTourBookingStatus;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;


@Entity
public class CampaignAssignToTourBooking {


    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private long id;

    @NotNull
    private long campaign;
    @NotNull
    private String campaign_code;
    @NotNull
    private long tourBooking;
    @NotNull
    private String booking_code;
    @NotNull
    

private String code;

    @NotNull
    private EnumCampaignToTourBookingStatus status;
    @NotNull
    private Integer redeemed_count;
    @NotNull
    private Integer unredeemed_count;


    public String getBooking_code() {
        return booking_code;
    }

    public void setBooking_code(String booking_code) {
        this.booking_code = booking_code;
    }

    public long getCampaign() {
        return campaign;
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



    public void setCampaign_code(String campaign_code) {
        this.campaign_code = campaign_code;
    }

    public String getCampaign_code() {
        return campaign_code;
    }

    public void setTourBooking(long tour_booking) {
        this.tourBooking = tour_booking;
    }

    public long getTourBooking() {
        return tourBooking;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public Integer getRedeemed_count() {
        return redeemed_count;
    }

    public void setRedeemed_count(Integer redeemed_count) {
        this.redeemed_count = redeemed_count;
    }

    public Integer getUnredeemed_count() {
        return unredeemed_count;
    }

    public void setUnredeemed_count(Integer unredeemed_count) {
        this.unredeemed_count = unredeemed_count;
    }

    public void setStatus(EnumCampaignToTourBookingStatus status) {
        this.status = status;
    }

    public EnumCampaignToTourBookingStatus getStatus() {
        return status;
    }
}
