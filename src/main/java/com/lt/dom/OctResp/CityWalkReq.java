package com.lt.dom.OctResp;

import com.lt.dom.oct.WayPoint;
import com.lt.dom.otcReq.LocationResp;
import com.lt.dom.otcReq.MediaGenralReq;
import com.lt.dom.otcReq.MuseumReq;
import com.lt.dom.otcReq.WayPointReq;
import com.lt.dom.otcenum.EnumModeOfTransport;

import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import java.util.List;


public class CityWalkReq {


    private String title;


    @NotNull
    private String     cityRegion;
    private Long walk_duration;






    private String name_long;

    private String tour_summary;
    private String desc_long;

    private String slug;




    private Long walk_distance;
    private Long total_duration;



    private List<WayPointReq> way_points;
    private String intro;

    private PhotoResp introductionAudio;


    private LocationResp location;


    private List<String>     majorLandmarks;
    public String getCityRegion() {
        return cityRegion;
    }

    public void setCityRegion(String cityRegion) {
        this.cityRegion = cityRegion;
    }

    public LocationResp getLocation() {
        return location;
    }

    public void setLocation(LocationResp location) {
        this.location = location;
    }
    private MuseumReq.MediaReq media;

    public MuseumReq.MediaReq getMedia() {
        return media;
    }

    public void setMedia(MuseumReq.MediaReq media) {
        this.media = media;
    }

    private List<MuseumReq.MediaReq> medias;
    private EnumModeOfTransport modeOfTransport;

    public List<MuseumReq.MediaReq> getMedias() {
        return medias;
    }

    public void setMedias(List<MuseumReq.MediaReq> medias) {
        this.medias = medias;
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

    public List<WayPointReq> getWay_points() {
        return way_points;
    }

    public void setWay_points(List<WayPointReq> way_points) {
        this.way_points = way_points;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public PhotoResp getIntroductionAudio() {

        return introductionAudio;
    }

    public void setIntroductionAudio(PhotoResp introductionAudio) {
        this.introductionAudio = introductionAudio;
    }

    public EnumModeOfTransport getModeOfTransport() {
        return modeOfTransport;
    }

    public void setModeOfTransport(EnumModeOfTransport modeOfTransport) {
        this.modeOfTransport = modeOfTransport;
    }

    public String getTour_summary() {
        return tour_summary;
    }

    public void setTour_summary(String tour_summary) {
        this.tour_summary = tour_summary;
    }

    public String getName_long() {
        return name_long;
    }

    public void setName_long(String name_long) {
        this.name_long = name_long;
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

    public List<String> getMajorLandmarks() {
        return majorLandmarks;
    }

    public void setMajorLandmarks(List<String> majorLandmarks) {
        this.majorLandmarks = majorLandmarks;
    }
}
