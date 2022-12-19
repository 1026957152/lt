package com.lt.dom.OctResp;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.lt.dom.oct.Base;
import com.lt.dom.oct.Trip;
import com.lt.dom.oct.TripPlan;
import com.lt.dom.otcenum.EnumPrivacyLevel;

import javax.persistence.Entity;
import java.time.LocalDate;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class TripResp {



    private String name;
    private Boolean is_deleted;//Trip was soft deleted and should not be displayed.

    private EnumPrivacyLevel privacy_level;



    private LocalDate starts_on;
    private LocalDate ends_on;
    private Integer day_count;

    private String code;
    private PhotoResp tripCover;
    private String desc_short;
    private String name_long;
    private String desc_long;
    private List itinerary;
    private Integer attraction_count;
    private String duration;
    private String type;

    public static TripResp from(Trip trip) {
        TripResp tripResp = new TripResp();

        tripResp.setStarts_on(trip.getStarts_on());
        tripResp.setEnds_on(trip.getEnds_on());
        tripResp.setDay_count(trip.getDay_count());
        tripResp.setName(trip.getName());
        tripResp.setName_long(trip.getName_long());
        tripResp.setDesc_short(trip.getDesc_short());
        tripResp.setDesc_long(trip.getDesc_long());


        return tripResp;
    }

    public static TripResp simpleFrom(Trip trip) {
        TripResp tripResp = new TripResp();

        tripResp.setName(trip.getName());

        return tripResp;
    }

    public static TripResp simpleFrom(TripPlan e) {
        TripResp tripResp = new TripResp();

        tripResp.setName(e.getName());

        return tripResp;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getIs_deleted() {
        return is_deleted;
    }

    public void setIs_deleted(Boolean is_deleted) {
        this.is_deleted = is_deleted;
    }

    public EnumPrivacyLevel getPrivacy_level() {
        return privacy_level;
    }

    public void setPrivacy_level(EnumPrivacyLevel privacy_level) {
        this.privacy_level = privacy_level;
    }

    public LocalDate getStarts_on() {
        return starts_on;
    }

    public void setStarts_on(LocalDate starts_on) {
        this.starts_on = starts_on;
    }

    public LocalDate getEnds_on() {
        return ends_on;
    }

    public void setEnds_on(LocalDate ends_on) {
        this.ends_on = ends_on;
    }

    public Integer getDay_count() {
        return day_count;
    }

    public void setDay_count(Integer day_count) {
        this.day_count = day_count;
    }



    public void setCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setTripCover(PhotoResp tripCover) {
        this.tripCover = tripCover;
    }

    public PhotoResp getTripCover() {
        return tripCover;
    }

    public void setDesc_short(String desc_short) {
        this.desc_short = desc_short;
    }

    public String getDesc_short() {
        return desc_short;
    }

    public void setName_long(String name_long) {
        this.name_long = name_long;
    }

    public String getName_long() {
        return name_long;
    }

    public void setDesc_long(String desc_long) {
        this.desc_long = desc_long;
    }

    public String getDesc_long() {
        return desc_long;
    }

    public <R> void setItinerary(List itinerary) {
        this.itinerary = itinerary;
    }

    public List getItinerary() {
        return itinerary;
    }

    public void setAttraction_count(Integer attraction_count) {
        this.attraction_count = attraction_count;
    }

    public Integer getAttraction_count() {
        return attraction_count;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getDuration() {
        return duration;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
