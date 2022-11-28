package com.lt.dom.oct;

import com.lt.dom.OctResp.EnumLongIdResp;
import com.lt.dom.otcenum.EnumAssetQtStatus;
import com.lt.dom.otcenum.EnumAssetType;
import com.lt.dom.otcenum.EnumAssetType_dynamic_static;
import com.lt.dom.otcenum.EnumModeOfTransport;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Entity
public class CityWalk extends Base{

    public static List List(List<CityWalk> componentRightMap) {
        return componentRightMap.stream().map(x->{

            EnumLongIdResp enumResp = new EnumLongIdResp();
            enumResp.setId(x.getId());

            enumResp.setText(x.getTitle()+"_"+x.getCode());
            return enumResp;
        }).collect(Collectors.toList());
    }

    @NotNull
    private EnumModeOfTransport modeOfTransport;
    private String cityRegion;
    private String tour_summary;
    private String slug;
    private String desc_long;
    private String name_long;
    private String majorLandmarks_json;

    public EnumModeOfTransport getModeOfTransport() {
        return modeOfTransport;
    }

    public void setModeOfTransport(EnumModeOfTransport modeOfTransport) {
        this.modeOfTransport = modeOfTransport;
    }

    private String title;

    private String intro;


    private String introductionAudio;
    private String code;
    private long supplier;




    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "location", referencedColumnName = "id")
    private Address location;



    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getIntroductionAudio() {
        return introductionAudio;
    }

    public void setIntroductionAudio(String introductionAudio) {
        this.introductionAudio = introductionAudio;
    }

    private Long walk_duration;


    private Long walk_distance;
    private Long total_duration;


    @Transient
    private List<WayPoint> way_points;


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

    public List<WayPoint> getWay_points() {
        return way_points;
    }

    public void setWay_points(List<WayPoint> way_points) {
        this.way_points = way_points;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setSupplier(long supplier) {
        this.supplier = supplier;
    }

    public long getSupplier() {
        return supplier;
    }

    public void setCityRegion(String cityRegion) {
        this.cityRegion = cityRegion;
    }

    public String getCityRegion() {
        return cityRegion;
    }

    public void setTour_summary(String tour_summary) {
        this.tour_summary = tour_summary;
    }

    public String getTour_summary() {
        return tour_summary;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getSlug() {
        return slug;
    }

    public void setDesc_long(String desc_long) {
        this.desc_long = desc_long;
    }

    public String getDesc_long() {
        return desc_long;
    }

    public void setName_long(String name_long) {
        this.name_long = name_long;
    }

    public String getName_long() {
        return name_long;
    }

    public void setMajorLandmarks_json(String majorLandmarks_json) {
        this.majorLandmarks_json = majorLandmarks_json;
    }

    public String getMajorLandmarks_json() {
        return majorLandmarks_json;
    }

    public void setLocation(Address location) {
        this.location = location;
    }

    public Address getLocation() {
        return location;
    }
}
