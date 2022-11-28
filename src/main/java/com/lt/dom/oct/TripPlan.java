package com.lt.dom.oct;

import com.lt.dom.otcenum.EnumPrivacyLevel;

import javax.persistence.Entity;
import javax.persistence.Transient;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
public class TripPlan extends Base{




    private String name;
    private Boolean is_deleted;//Trip was soft deleted and should not be displayed.

    private EnumPrivacyLevel privacy_level;

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
    private LocalDateTime plannedStartTime;
    @Transient
    private Address plannedStartLocation;
    private LocalDate ends_on;
    private Integer day_count;
    private Integer duration_days;
    private Integer age_max;
    private Integer age_min;

    private String meals;
    private String meals_desc;

    @Transient
    private List<String> themes;
    @Transient
    private List<String> tags;

    @Transient
    private List<String> regions;

    private long supplier;
    private String code;

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

    public void setSupplier(long supplier) {
        this.supplier = supplier;
    }

    public long getSupplier() {
        return supplier;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
