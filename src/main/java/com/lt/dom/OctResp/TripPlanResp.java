package com.lt.dom.OctResp;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.lt.dom.oct.Trip;
import com.lt.dom.oct.TripPlan;
import com.lt.dom.otcenum.EnumPrivacyLevel;
import com.lt.dom.repository.TripPlanRepository;

import java.time.LocalDate;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class TripPlanResp {



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
    private List attractions;
    private Long id;

    public static TripPlanResp from(TripPlan trip) {
        TripPlanResp tripResp = new TripPlanResp();

        tripResp.setStarts_on(trip.getStarts_on());
        tripResp.setId(trip.getId());
        tripResp.setEnds_on(trip.getEnds_on());
        tripResp.setDay_count(trip.getDay_count());
        tripResp.setName(trip.getName());



        return tripResp;
    }

    public static TripPlanResp simpleFrom(Trip trip) {
        TripPlanResp tripResp = new TripPlanResp();

        tripResp.setName(trip.getName());

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

    public <R> void setAttractions(List attractions) {
        this.attractions = attractions;
    }

    public List getAttractions() {
        return attractions;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}