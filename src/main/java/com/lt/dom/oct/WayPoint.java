package com.lt.dom.oct;

import com.lt.dom.otcenum.EnumAssetType;
import com.lt.dom.otcenum.EnumAudioOption;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
public class WayPoint extends Base{

    private String title;
    private String desc_long;



    private String slug;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc_long() {
        return desc_long;
    }

    public void setDesc_long(String desc_long) {
        this.desc_long = desc_long;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    private String code;

    private Long walk_to_next_duration;
    private Long walk_to_next_distance;
    private Long visit_time;

    private Float location_lng;
    private Float location_lat;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "location", referencedColumnName = "id")
    private Address location;


    @Transient
    private Pointsofinterest poi;
    private EnumAudioOption audioOption;
    private long cityWalk;

    private Long place;

    public Long getPlace() {
        return place;
    }

    public void setPlace(Long place) {
        this.place = place;
    }

    public Long getWalk_to_next_duration() {
        return walk_to_next_duration;
    }

    public void setWalk_to_next_duration(Long walk_to_next_duration) {
        this.walk_to_next_duration = walk_to_next_duration;
    }

    public Long getWalk_to_next_distance() {
        return walk_to_next_distance;
    }

    public void setWalk_to_next_distance(Long walk_to_next_distance) {
        this.walk_to_next_distance = walk_to_next_distance;
    }

    public Long getVisit_time() {
        return visit_time;
    }

    public void setVisit_time(Long visit_time) {
        this.visit_time = visit_time;
    }

    public Float getLocation_lng() {
        return location_lng;
    }

    public void setLocation_lng(Float location_lng) {
        this.location_lng = location_lng;
    }

    public Float getLocation_lat() {
        return location_lat;
    }

    public void setLocation_lat(Float location_lat) {
        this.location_lat = location_lat;
    }

    public Pointsofinterest getPoi() {
        return poi;
    }

    public void setPoi(Pointsofinterest poi) {
        this.poi = poi;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setAudioOption(EnumAudioOption audioOption) {
        this.audioOption = audioOption;
    }

    public EnumAudioOption getAudioOption() {
        return audioOption;
    }

    public void setCityWalk(long cityWalk) {
        this.cityWalk = cityWalk;
    }

    public long getCityWalk() {
        return cityWalk;
    }

    public void setLocation(Address location) {
        this.location = location;
    }

    public Address getLocation() {
        return location;
    }
}
