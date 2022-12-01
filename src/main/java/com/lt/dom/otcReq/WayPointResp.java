package com.lt.dom.otcReq;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.lt.dom.OctResp.MediaResp;
import com.lt.dom.OctResp.PlaceResp;
import com.lt.dom.oct.Base;
import com.lt.dom.oct.Place;
import com.lt.dom.oct.Pointsofinterest;
import com.lt.dom.oct.WayPoint;

import javax.persistence.Entity;
import javax.persistence.Transient;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class WayPointResp {



    private String code;

    private Long walk_to_next_duration;
    private Long walk_to_next_distance;
    private Long visit_time;

    private Float location_lng;
    private Float location_lat;


    @Transient
    private Pointsofinterest poi;
    private MediaResp media;
    private PlaceResp place;



    private List<MuseumReq.MediaReq> medias;
    private String title;
    private String intro;
    private Integer seq;
    private LocationResp location;

    public List<MuseumReq.MediaReq> getMedias() {
        return medias;
    }


    public static WayPointResp from(WayPoint place) {

        WayPointResp wayPointResp = new WayPointResp();
        wayPointResp.setTitle(place.getTitle());
        wayPointResp.setIntro(place.getDesc_long());
        wayPointResp.setCode(place.getCode());

        wayPointResp.setLocation(LocationResp.latLngFrom(place.getLocation()));
        wayPointResp.setLocation_lat(place.getLocation_lat());
        wayPointResp.setLocation_lng(place.getLocation_lng());
        wayPointResp.setVisit_time(place.getVisit_time());
        wayPointResp.setWalk_to_next_distance(place.getWalk_to_next_distance());
        wayPointResp.setWalk_to_next_duration(place.getWalk_to_next_duration());



        return wayPointResp;
    }
    public static WayPointResp locationFrom(WayPoint place) {

        WayPointResp wayPointResp = new WayPointResp();
        wayPointResp.setTitle(place.getTitle());
        wayPointResp.setCode(place.getCode());
        wayPointResp.setLocation(LocationResp.latLngFrom(place.getLocation()));
        wayPointResp.setLocation_lat(place.getLocation_lat());
        wayPointResp.setLocation_lng(place.getLocation_lng());

        return wayPointResp;
    }
    public static WayPointResp from(WayPoint e, Place place) {
        WayPointResp wayPointResp = WayPointResp.from(e);


        wayPointResp.setPlace(PlaceResp.from(place));

        return wayPointResp;
    }
    public static WayPointResp from(WayPoint e,CitywalkLatLngPojo pojo, Place place) {
        WayPointResp wayPointResp = WayPointResp.from(e);

        wayPointResp.setPlace(PlaceResp.from(place));
        return wayPointResp;
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

    public void setMedia(MediaResp media) {

        this.media = media;
    }

    public MediaResp getMedia() {
        return media;
    }

    public void setPlace(PlaceResp place) {
        this.place = place;
    }

    public PlaceResp getPlace() {
        return place;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getIntro() {
        return intro;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
    }

    public Integer getSeq() {
        return seq;
    }

    public void setLocation(LocationResp location) {
        this.location = location;
    }

    public LocationResp getLocation() {
        return location;
    }
}
