package com.lt.dom.oct;


import com.lt.dom.otcenum.EnumProductStatus;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import java.util.List;

@Entity
public class Attraction {
    @Version
    private Integer version;
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private long id;
    private long supplier;
    
@Column(unique=true) 
private String code;
    private EnumProductStatus status;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    private String CategoryCodes;
    private String name;
    private Float latitude;
    private Float longitude;

    private String shortdesc; //Short description (NO HTML)
    @Length(max = 3000)
    private String longdesc;

    @Transient
    private List<String> images;



    private String thumbnail_image;

/*    private String image_thumb_url;
    private String image_large_url;*/
    private String video;

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getCategoryCodes() {
        return CategoryCodes;
    }

    public void setCategoryCodes(String categoryCodes) {
        CategoryCodes = categoryCodes;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Float getLatitude() {
        return latitude;
    }

    public void setLatitude(Float latitude) {
        this.latitude = latitude;
    }

    public Float getLongitude() {
        return longitude;
    }

    public void setLongitude(Float longitude) {
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

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public String getThumbnail_image() {
        return thumbnail_image;
    }

    public void setThumbnail_image(String thumbnail_image) {
        this.thumbnail_image = thumbnail_image;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public void setSupplier(long supplier) {
        this.supplier = supplier;
    }

    public long getSupplier() {
        return supplier;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setStatus(EnumProductStatus status) {
        this.status = status;
    }

    public EnumProductStatus getStatus() {
        return status;
    }
}
