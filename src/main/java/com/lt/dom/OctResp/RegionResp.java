package com.lt.dom.OctResp;


import com.lt.dom.oct.Place;
import com.lt.dom.oct.Region;

import java.util.List;

public class RegionResp {



    private String name;
    private PhotoResp photo;
    private String code;
    private List recommend_attractions;
    private String description;

    public static RegionResp simpleFrom(Region region) {
        RegionResp resp = new RegionResp();
        resp.setName(region.getName());
        resp.setCode(region.getCode());
        resp.setDescription(region.getDescription());
        return resp;

    }
    public static RegionResp from(Region region) {
        RegionResp resp = new RegionResp();
        resp.setName(region.getName());
        resp.setCode(region.getCode());
        resp.setDescription(region.getDescription());
        return resp;

    }


    public static RegionResp from(Place region) {
        RegionResp resp = new RegionResp();
        resp.setName(region.getName());
        resp.setCode(region.getCode());
        resp.setDescription(region.getDescription_text());
        return resp;

    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PhotoResp getPhoto() {
        return photo;
    }

    public void setPhoto(PhotoResp photo) {
        this.photo = photo;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public <R> void setRecommend_attractions(List recommend_attractions) {
        this.recommend_attractions = recommend_attractions;
    }

    public List getRecommend_attractions() {
        return recommend_attractions;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
