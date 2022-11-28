package com.lt.dom.OctResp;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.google.gson.Gson;
import com.lt.dom.oct.Base;
import com.lt.dom.oct.CityWalk;
import com.lt.dom.oct.WayPoint;
import com.lt.dom.otcReq.LocationResp;
import com.lt.dom.otcReq.WayPointResp;
import com.lt.dom.otcenum.Enumfeatured;
import org.checkerframework.checker.units.qual.C;
import org.springframework.hateoas.EntityModel;

import javax.persistence.Entity;
import javax.persistence.Transient;
import java.util.List;
import java.util.Objects;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CityWalkResp extends BaseResp {





    private Long walk_duration;


    private Long walk_distance;
    private Long total_duration;



    private List<EntityModel> way_points;
    private Object address;
    private String title;
    private String intro;
    private String introductionAudio;
    private String portrait;
    private MediaResp media;
    private String walk_duration_icon;
    private String walk_distance_icon;
    private String code;
    private String cityRegion;
    private String[] majorLandmarks;
    private String status_text;

    public static CityWalkResp from(CityWalk e) {

        CityWalkResp cityWalkResp = new CityWalkResp();
        //cityWalkResp.setAddress();
        cityWalkResp.setTitle(e.getTitle());
        cityWalkResp.setIntro(e.getIntro());
        cityWalkResp.setCityRegion(e.getCityRegion());

        cityWalkResp.setCode(e.getCode());
        cityWalkResp.setStatus_text("status");
        cityWalkResp.setMajorLandmarks(new Gson().fromJson(e.getMajorLandmarks_json(),String[].class));

        cityWalkResp.setTotal_duration(e.getTotal_duration());
        cityWalkResp.setWalk_duration(e.getWalk_duration());
        cityWalkResp.setWalk_distance(e.getWalk_distance());

        cityWalkResp.setWalk_duration_icon("http://yulinmei.cn:8080/oct/files/citywalk/duration.svg");
        cityWalkResp.setWalk_distance_icon("http://yulinmei.cn:8080/oct/files/citywalk/map-distance.svg");

        cityWalkResp.setCreatedDate(e.getCreatedDate());
        cityWalkResp.setModifiedDate(e.getModifiedDate());
        return cityWalkResp;
    }

    public static CityWalkResp fromSnip(CityWalk e) {

        CityWalkResp cityWalkResp = new CityWalkResp();
        cityWalkResp.setTotal_duration(e.getTotal_duration());
        cityWalkResp.setWalk_duration(e.getWalk_duration());
        cityWalkResp.setWalk_distance(e.getWalk_distance());
        cityWalkResp.setWalk_duration_icon("http://yulinmei.cn:8080/oct/files/citywalk/duration.svg");
        cityWalkResp.setWalk_distance_icon("http://yulinmei.cn:8080/oct/files/citywalk/map-distance.svg");

        return cityWalkResp;
    }


    public Long getWalk_duration() {
        return walk_duration;
    }

    public void setWalk_duration(Long walk_duration) {
        this.walk_duration = walk_duration;
    }

    public Long getWalk_distance() {
        return walk_distance;
    }

    public void setWalk_distance(Long walk_distance) {
        this.walk_distance = walk_distance;
    }

    public Long getTotal_duration() {
        return total_duration;
    }

    public void setTotal_duration(Long total_duration) {
        this.total_duration = total_duration;
    }

    public List<EntityModel> getWay_points() {
        return way_points;
    }

    public void setWay_points(List<EntityModel> way_points) {
        this.way_points = way_points;
    }

    public void setAddress(Object address) {
        this.address = address;
    }

    public Object getAddress() {
        return address;
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

    public void setIntroductionAudio(String introductionAudio) {
        this.introductionAudio = introductionAudio;
    }

    public String getIntroductionAudio() {
        return introductionAudio;
    }

    public void setPortrait(String portrait) {
        this.portrait = portrait;
    }

    public String getPortrait() {
        return portrait;
    }

    public void setMedia(MediaResp media) {
        this.media = media;
    }

    public MediaResp getMedia() {
        return media;
    }

    public void setWalk_duration_icon(String walk_duration_icon) {
        this.walk_duration_icon = walk_duration_icon;
    }

    public String getWalk_duration_icon() {
        return walk_duration_icon;
    }

    public void setWalk_distance_icon(String walk_distance_icon) {
        this.walk_distance_icon = walk_distance_icon;
    }

    public String getWalk_distance_icon() {
        return walk_distance_icon;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCityRegion(String cityRegion) {
        this.cityRegion = cityRegion;
    }

    public String getCityRegion() {
        return cityRegion;
    }

    public <T> void setMajorLandmarks(String[] majorLandmarks) {
        this.majorLandmarks = majorLandmarks;
    }

    public String[] getMajorLandmarks() {
        return majorLandmarks;
    }

    public void setStatus_text(String status_text) {
        this.status_text = status_text;
    }

    public String getStatus_text() {
        return status_text;
    }
}
