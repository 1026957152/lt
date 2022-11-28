package com.lt.dom.oct;


import com.lt.dom.OctResp.EnumLongIdResp;
import com.lt.dom.otcenum.EnumKnownfor;
import com.lt.dom.otcenum.EnumPrivacyLevel;
import com.lt.dom.otcenum.EnumProductStatus;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import java.util.List;
import java.util.stream.Collectors;

@Entity
public class Attraction extends Base{

    private long supplier;
    
//##@Column(unique=true) 
private String code;
    private EnumProductStatus status;
    private EnumPrivacyLevel privacyLevel;
    private Long selfGuided;
    private String products_json;
    private String tags_json;



    @OneToMany(mappedBy="attraction",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<Knowfor> knowfors;

    public List<Knowfor> getKnowfors() {
        return knowfors;
    }

    public void setKnowfors(List<Knowfor> knowfors) {
        this.knowfors = knowfors;
    }

    public static List List(List<Attraction> componentRightMap) {
        return componentRightMap.stream().map(x->{

            EnumLongIdResp enumResp = new EnumLongIdResp();
            enumResp.setId(x.getId());

            enumResp.setText(x.getName()+"_"+x.getCode());
            return enumResp;
        }).collect(Collectors.toList());
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



    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "location", referencedColumnName = "id")
    private Address location;

    public Address getLocation() {
        return location;
    }

    public void setLocation(Address location) {
        this.location = location;
    }



/*    private String image_thumb_url;
    private String image_large_url;*/



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

    public void setPrivacyLevel(EnumPrivacyLevel privacyLevel) {
        this.privacyLevel = privacyLevel;
    }

    public EnumPrivacyLevel getPrivacyLevel() {
        return privacyLevel;
    }

    public Long getSelfGuided() {
        return selfGuided;
    }

    public void setSelfGuided(Long selfGuided) {
        this.selfGuided = selfGuided;
    }

    public String getProducts_json() {
        return products_json;
    }

    public void setProducts_json(String products_json) {
        this.products_json = products_json;
    }

    public void setTags_json(String tags_json) {
        this.tags_json = tags_json;
    }

    public String getTags_json() {
        return tags_json;
    }
}
