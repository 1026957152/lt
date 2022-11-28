package com.lt.dom.otcReq;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.lt.dom.OctResp.PhotoResp;
import com.lt.dom.oct.Description;
import com.lt.dom.oct.Place;
import com.lt.dom.otcenum.EnumPlaceCategories;
import com.lt.dom.otcenum.EnumPlaceLevel;
import com.lt.dom.otcenum.EnumPlaceTyp;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static com.lt.dom.serviceOtc.JsonParse.GSON;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class PlaceEditReq {

    private Map parameterList;
    @NotEmpty
    private String name;

    public Map getParameterList() {
        return parameterList;
    }

    public void setParameterList(Map parameterList) {
        this.parameterList = parameterList;
    }

    private Float lat;
    private Float lng;


    private DescriptionReq description;

    private Integer media_count;





    @NotNull
    private Boolean has_shape_geometry;

    private String name_suffix; //    Suffix contains parent location. Useful for distinguishing ambiguous locations such as „Chinatown“.

    private String name_local; //    Name as used locally, in local language.

    @NotNull
    private EnumPlaceLevel level;

    @NotNull
    private List<EnumPlaceCategories> categories;


    private Integer duration_estimate;

    @NotNull
    private EnumPlaceTyp type;
    private Long type_reference;
    private PhotoResp photo;

    public static PlaceEditReq from(Place supplier) {

        PlaceEditReq placeEditReq = new PlaceEditReq();

        DescriptionReq description1 = new DescriptionReq();
        description1.setText(supplier.getDescription_text());
        placeEditReq.setDescription(description1);
        placeEditReq.setHas_shape_geometry(supplier.getHas_shape_geometry());

        placeEditReq.setName(supplier.getName());
        placeEditReq.setType(supplier.getType());
        placeEditReq.setLevel(supplier.getLevel());
        placeEditReq.setType(supplier.getType());


        if(supplier.getCategories_json()!= null){
            placeEditReq.setCategories(Arrays.asList(GSON.fromJson(supplier.getCategories_json(),EnumPlaceCategories[].class)));
        }
        return placeEditReq;
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

    public DescriptionReq getDescription() {
        return description;
    }

    public void setDescription(DescriptionReq description) {
        this.description = description;
    }

    public EnumPlaceTyp getType() {
        return type;
    }

    public void setType(EnumPlaceTyp type) {
        this.type = type;
    }

    public Long getType_reference() {
        return type_reference;
    }

    public void setType_reference(Long type_reference) {
        this.type_reference = type_reference;
    }

    public PhotoResp getPhoto() {
        return photo;
    }

    public void setPhoto(PhotoResp photo) {
        this.photo = photo;
    }
}
