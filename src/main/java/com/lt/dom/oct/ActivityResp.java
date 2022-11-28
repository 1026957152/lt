package com.lt.dom.oct;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.lt.dom.OctResp.BaseResp;
import com.lt.dom.OctResp.PhotoResp;
import com.lt.dom.otcReq.LocationResp;
import com.lt.dom.otcenum.EnumActivityType;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ActivityResp extends BaseResp {
    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getStartingTime() {
        return startingTime;
    }

    public void setStartingTime(LocalTime startingTime) {
        this.startingTime = startingTime;
    }

    private LocalDate date;
    private LocalTime startingTime;


    @NotNull
    private EnumActivityType type;


    private LocalDateTime activityDate;
    private String code;
    private String name;
    private String desc_short;
    private String status_text;
    private String type_text;
    private PhotoResp thumb;
    private LocationResp address;


    public EnumActivityType getType() {
        return type;
    }

    public void setType(EnumActivityType type) {
        this.type = type;
    }

    public LocalDateTime getActivityDate() {
        return activityDate;
    }

    public void setActivityDate(LocalDateTime activityDate) {
        this.activityDate = activityDate;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setDesc_short(String desc_short) {
        this.desc_short = desc_short;
    }

    public String getDesc_short() {
        return desc_short;
    }

    public void setStatus_text(String status_text) {
        this.status_text = status_text;
    }

    public String getStatus_text() {
        return status_text;
    }

    public void setType_text(String type_text) {
        this.type_text = type_text;
    }

    public String getType_text() {
        return type_text;
    }

    public void setThumb(PhotoResp thumb) {
        this.thumb = thumb;
    }

    public PhotoResp getThumb() {
        return thumb;
    }

    public void setAddress(LocationResp address) {
        this.address = address;
    }

    public LocationResp getAddress() {
        return address;
    }
}
