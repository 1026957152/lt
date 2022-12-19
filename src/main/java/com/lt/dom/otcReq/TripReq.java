package com.lt.dom.otcReq;

import com.lt.dom.OctResp.PhotoResp;
import com.lt.dom.oct.Base;
import com.lt.dom.otcenum.EnumPrivacyLevel;

import javax.persistence.Entity;
import java.time.LocalDate;
import java.util.List;

public class TripReq {




    private String name;
    private Boolean is_deleted;//Trip was soft deleted and should not be displayed.

    private EnumPrivacyLevel privacy_level;
    private List<Long> attractionIds;

    public List<Long> getAttractionIds() {
        return attractionIds;
    }

    public void setAttractionIds(List<Long> attractionIds) {
        this.attractionIds = attractionIds;
    }



    /*    privacy_level: enum["private", "shareable", "public"]
    privileges: {
        edit: bool
        manage: bool
        delete: bool
        join: bool
        The property is false if the trip is private or user is already joined or is trip owner.

    */
  //  Privileges of the current user to the trip.


    private LocalDate starts_on;
    private LocalDate ends_on;
    private Integer day_count;
    private PhotoResp tripCover;
    private String name_long;
    private String desc_short;
    private String desc_long;

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

    public PhotoResp getTripCover() {
        return tripCover;
    }

    public void setTripCover(PhotoResp tripCover) {
        this.tripCover = tripCover;
    }

    public String getName_long() {
        return name_long;
    }

    public void setName_long(String name_long) {
        this.name_long = name_long;
    }

    public String getDesc_short() {
        return desc_short;
    }

    public void setDesc_short(String desc_short) {
        this.desc_short = desc_short;
    }

    public String getDesc_long() {
        return desc_long;
    }

    public void setDesc_long(String desc_long) {
        this.desc_long = desc_long;
    }
}
