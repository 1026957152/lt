package com.lt.dom.OctResp.home;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.lt.dom.OctResp.PhotoResp;
import com.lt.dom.otcenum.EnumLayoutFeatured;
import com.lt.dom.otcenum.EnumLayoutFeaturedFuction;
import com.lt.dom.otcenum.EnumLayoutFeaturedOther;
import com.lt.dom.otcenum.Enumfeatured;


@JsonInclude(JsonInclude.Include.NON_NULL)
public class FeatureResp {


    private String title;
    private String Display_name;
    private String feature_image;

    private String url;
    private String icon;
    private Enumfeatured type;
    private String path;
    private boolean show = true;
    public static FeatureResp from(EnumLayoutFeaturedFuction e) {
        FeatureResp featureResp = new FeatureResp();

        featureResp.setTitle(e.getTitle());
        featureResp.setDisplay_name(e.getTitle());
        featureResp.setIcon(e.getIcon());
        featureResp.setUrl(e.getUrl());
        return featureResp;
    }


    public static FeatureResp from(EnumLayoutFeaturedOther e) {
        FeatureResp featureResp = new FeatureResp();

        featureResp.setTitle(e.getTitle());
        featureResp.setDisplay_name(e.getTitle());
        featureResp.setIcon(e.getIcon());
        featureResp.setUrl(e.getUrl());
        return featureResp;
    }

    public boolean isShow() {
        return show;
    }

    public void setShow(boolean show) {
        this.show = show;
    }

    public static FeatureResp from(Enumfeatured e) {
        FeatureResp featureResp = new FeatureResp();
        featureResp.setType(e);
        featureResp.setTitle(e.getTitle());
        featureResp.setDisplay_name(e.getTitle());
        featureResp.setIcon(e.getIcon());
        featureResp.setUrl(e.getUrl());
        return featureResp;
    }
    public static FeatureResp from(EnumLayoutFeatured e) {
        FeatureResp featureResp = new FeatureResp();
        //featureResp.setType(e);
        featureResp.setTitle(e.getTitle());
       // featureResp.setDisplay_name(e.getTitle());
        featureResp.setIcon(e.getFeature_image());
        featureResp.setUrl(e.getUrl());
        return featureResp;
    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDisplay_name() {
        return Display_name;
    }

    public void setDisplay_name(String display_name) {
        Display_name = display_name;
    }

    public String getFeature_image() {
        return feature_image;
    }

    public void setFeature_image(String feature_image) {
        this.feature_image = feature_image;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public void setType(Enumfeatured type) {
        this.type = type;
    }

    public Enumfeatured getType() {
        return type;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}
