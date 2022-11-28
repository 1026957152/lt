package com.lt.dom.oct;

import com.lt.dom.otcenum.EnumPlaceCategories;
import com.lt.dom.otcenum.EnumPlaceLevel;
import com.lt.dom.otcenum.EnumPlaceTyp;

import javax.persistence.*;
import java.util.List;
import java.util.Set;
import java.util.UUID;


@Entity
public class Place extends Base{

    private String name;
    private long place;


    @Enumerated(EnumType.STRING)
    private EnumPlaceTyp type;
    private Long supplier;

    public EnumPlaceTyp getType() {
        return type;
    }


    private Long type_reference;

    public Long getType_reference() {
        return type_reference;
    }

    public void setType_reference(Long type_reference) {
        this.type_reference = type_reference;
    }

    public void setType(EnumPlaceTyp type) {
        this.type = type;
    }

    private Float lat;
    private Float lng;

    private Boolean has_shape_geometry;

    private String name_suffix; //    Suffix contains parent location. Useful for distinguishing ambiguous locations such as „Chinatown“.

    private String name_local; //    Name as used locally, in local language.

    @OneToMany(mappedBy = "stop")
    Set<PlaceRegistration> registrations;



    private EnumPlaceLevel level;
    @Transient
    private List<EnumPlaceCategories> categories;

    @Transient
    private Description description;

    private Integer media_count;

    private Integer duration_estimate;
    private String categories_json;
    private String description_text;
    private String code;

    public Boolean getHas_shape_geometry() {
        return has_shape_geometry;
    }

    public void setHas_shape_geometry(Boolean has_shape_geometry) {
        this.has_shape_geometry = has_shape_geometry;
    }

    public String getName_suffix() {
        return name_suffix;
    }

    public void setName_suffix(String name_suffix) {
        this.name_suffix = name_suffix;
    }

    public String getName_local() {
        return name_local;
    }

    public void setName_local(String name_local) {
        this.name_local = name_local;
    }

    public EnumPlaceLevel getLevel() {
        return level;
    }

    public void setLevel(EnumPlaceLevel level) {
        this.level = level;
    }

    public List<EnumPlaceCategories> getCategories() {
        return categories;
    }

    public void setCategories(List<EnumPlaceCategories> categories) {
        this.categories = categories;
    }

    public Integer getMedia_count() {
        return media_count;
    }

    public void setMedia_count(Integer media_count) {
        this.media_count = media_count;
    }

    public Integer getDuration_estimate() {
        return duration_estimate;
    }

    public void setDuration_estimate(Integer duration_estimate) {
        this.duration_estimate = duration_estimate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getPlace() {
        return place;
    }

    public void setPlace(long place) {
        this.place = place;
    }

    public Float getLat() {
        return lat;
    }

    public void setLat(Float lat) {
        this.lat = lat;
    }

    public Float getLng() {
        return lng;
    }

    public void setLng(Float lng) {
        this.lng = lng;
    }

    public Description getDescription() {
        return description;
    }

    public void setDescription(Description description) {
        this.description = description;
    }

    public void setCategories_json(String categories_json) {
        this.categories_json = categories_json;
    }

    public String getCategories_json() {
        return categories_json;
    }

    public void setDescription_text(String description_text) {

        this.description_text = description_text;
    }

    public String getDescription_text() {
        return description_text;
    }

    public String getCode() {
        if(code == null){
            this.code = UUID.randomUUID().toString();
        }
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setSupplier(Long supplier) {
        this.supplier = supplier;
    }

    public Long getSupplier() {
        return supplier;
    }
}
