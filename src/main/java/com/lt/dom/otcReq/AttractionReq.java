package com.lt.dom.otcReq;


import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;


public class AttractionReq {


    private String name;
    private float latitude;
    private float longitude;

    @NotEmpty
    private String shortdesc; //Short description (NO HTML)
    @NotEmpty
    private String longdesc;


    private List<ImageReq> images;



    private String thumbnail_image;

    private String image_thumb_url;
    private String image_large_url;
    private String video;



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public String getShortdesc() {
        return shortdesc;
    }

    public void setShortdesc(String shortdesc) {
        this.shortdesc = shortdesc;
    }

    public String getLongdesc() {
        return longdesc;
    }

    public void setLongdesc(String longdesc) {
        this.longdesc = longdesc;
    }

    public List<ImageReq> getImages() {
        return images;
    }

    public void setImages(List<ImageReq> images) {
        this.images = images;
    }

    public String getThumbnail_image() {
        return thumbnail_image;
    }

    public void setThumbnail_image(String thumbnail_image) {
        this.thumbnail_image = thumbnail_image;
    }

    public String getImage_thumb_url() {
        return image_thumb_url;
    }

    public void setImage_thumb_url(String image_thumb_url) {
        this.image_thumb_url = image_thumb_url;
    }

    public String getImage_large_url() {
        return image_large_url;
    }

    public void setImage_large_url(String image_large_url) {
        this.image_large_url = image_large_url;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }
}
