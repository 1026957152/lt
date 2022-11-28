package com.lt.dom.OctResp;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.lt.dom.oct.Description;
import com.lt.dom.oct.Place;
import com.lt.dom.otcenum.EnumPlaceCategories;
import com.lt.dom.otcenum.EnumPlaceLevel;
import com.lt.dom.otcenum.EnumPlaceTyp;
import org.springframework.security.core.parameters.P;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class PlaceResp extends BaseResp{

    private String name;



    private Float lat;
    private Float lng;


    private String  description;

    private Integer media_count;






    private Boolean has_shape_geometry;

    private String name_suffix; //    Suffix contains parent location. Useful for distinguishing ambiguous locations such as „Chinatown“.

    private String name_local; //    Name as used locally, in local language.


    private EnumPlaceLevel level;

    private List<EnumPlaceCategories> categories;


    private Integer duration_estimate;
    private PhotoResp photo;
    private EnumPlaceTyp type;
    private String path;
    private boolean link;
    private String code;
    private String type_text;
    private String level_text;

    public static PlaceResp from(Place place) {
        PlaceResp placeResp = new PlaceResp();
        placeResp.setCode(place.getCode());
        placeResp.setDescription(placeResp.getDescription());
        placeResp.setLevel(place.getLevel());
        placeResp.setLevel_text(place.getLevel().toString());

        placeResp.setName(place.getName());
        placeResp.setType(place.getType());
        placeResp.setType_text(place.getType().toString());

        placeResp.setDescription(place.getDescription_text());

        placeResp.setCreatedDate(place.getCreatedDate());
        placeResp.setModifiedDate(place.getModifiedDate());
        return placeResp;
    }

    public Integer getMedia_count() {
        return media_count;
    }

    public void setMedia_count(Integer media_count) {
        this.media_count = media_count;
    }

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPhoto(PhotoResp photo) {
        this.photo = photo;
    }

    public PhotoResp getPhoto() {
        return photo;
    }

    public void setType(EnumPlaceTyp type) {
        this.type = type;
    }

    public EnumPlaceTyp getType() {
        return type;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    public void setLink(boolean link) {
        this.link = link;
    }

    public boolean isLink() {
        return link;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setType_text(String type_text) {
        this.type_text = type_text;
    }

    public String getType_text() {
        return type_text;
    }

    public void setLevel_text(String level_text) {
        this.level_text = level_text;
    }

    public String getLevel_text() {
        return level_text;
    }
}
