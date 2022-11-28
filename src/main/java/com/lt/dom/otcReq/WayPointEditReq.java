package com.lt.dom.otcReq;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.lt.dom.oct.Pointsofinterest;
import com.lt.dom.otcenum.EnumAudioOption;

import javax.persistence.Transient;
import java.util.List;


public class WayPointEditReq {

    private String title;
    private String desc_long;


    private Long place;
    private String slug;

    private Long walk_to_next_duration;
    private Long walk_to_next_distance;
    private Long visit_time;

    private Double location_lng;
    private Double location_lat;
    private LocationResp location;

    public LocationResp getLocation() {
        return location;
    }

    public void setLocation(LocationResp location) {
        this.location = location;
    }

    @JsonProperty("audio_option")
    private EnumAudioOption audioOption;
    private String audio;

    public String getAudio() {
        return audio;
    }

    public void setAudio(String audio) {
        this.audio = audio;
    }

    public Long getPlace() {
        return place;
    }

    public void setPlace(Long place) {
        this.place = place;
    }

    private List<MuseumReq.MediaReq> medias;

    public List<MuseumReq.MediaReq> getMedias() {
        return medias;
    }

    public void setMedias(List<MuseumReq.MediaReq> medias) {
        this.medias = medias;
    }

    public EnumAudioOption getAudioOption() {
        return audioOption;
    }

    public void setAudioOption(EnumAudioOption audioOption) {
        this.audioOption = audioOption;
    }

    @Transient
    private Pointsofinterest poi;

/*    public static WayPointReq from(WayPoint place) {

        WayPointReq wayPointResp = new WayPointReq();
        wayPointResp.setCode(place.getCode());
        wayPointResp.setLocation_lat(place.getLocation_lat());
        wayPointResp.setLocation_lng(place.getLocation_lng());
        wayPointResp.setVisit_time(place.getVisit_time());
        wayPointResp.setWalk_to_next_distance(place.getWalk_to_next_distance());
        wayPointResp.setWalk_to_next_duration(place.getWalk_to_next_duration());

        return wayPointResp;
    }*/

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

    public Double getLocation_lng() {
        return location_lng;
    }

    public void setLocation_lng(Double location_lng) {
        this.location_lng = location_lng;
    }

    public Double getLocation_lat() {
        return location_lat;
    }

    public void setLocation_lat(Double location_lat) {
        this.location_lat = location_lat;
    }

    public Pointsofinterest getPoi() {
        return poi;
    }

    public void setPoi(Pointsofinterest poi) {
        this.poi = poi;
    }

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
}
