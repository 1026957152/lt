package com.lt.dom.OctResp;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.lt.dom.controllerOct.SupplierRestController;
import com.lt.dom.oct.Attraction;
import com.lt.dom.oct.ComponentRight;
import com.lt.dom.oct.Supplier;
import com.lt.dom.otcReq.LocationResp;
import org.springframework.hateoas.EntityModel;

import javax.persistence.*;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class AttractionResp {

    private List<EntityModel> products;
    private EntityModel supplier;
    private boolean visible;
    private String code;
    private List<String> tags;
    private LocationResp location;


    public static List<EnumLongIdResp> EnumList(List<Attraction> componentRightList) {
        return componentRightList.stream().map(x->{

            EnumLongIdResp enumResp = new EnumLongIdResp();
            enumResp.setId(x.getId());
            //  enumResp.setName(x.name());
            enumResp.setText(x.getName()+"_"+x.getCode().toString()+"_"+x.getShortdesc());
            return enumResp;
        }).collect(Collectors.toList());
    }

    public EntityModel getSupplier() {
        return supplier;
    }

    public void setSupplier(EntityModel supplier) {
        this.supplier = supplier;
    }

    public static AttractionResp from(Attraction attraction) {
        AttractionResp attractionResp = new AttractionResp();
        attractionResp.setName(attraction.getName());
        attractionResp.setCode(attraction.getCode());
        attractionResp.setShortdesc(attraction.getShortdesc());
        attractionResp.setLongdesc(attraction.getLongdesc());

        attractionResp.setLatitude(attraction.getLatitude());
        attractionResp.setLongitude(attraction.getLongitude());


        attractionResp.setVisible(true);

        return attractionResp;
    }



    public static AttractionResp from(Attraction attraction,PhotoResp photoResp) {
        AttractionResp attractionResp = AttractionResp.from(attraction);
        attractionResp.setThumbnail_image(photoResp);

        return attractionResp;
    }


    private String CategoryCodes;
    private String name;



    private Float latitude;
    private Float longitude;



    private String shortdesc; //Short description (NO HTML)
    private String longdesc;


    private List<PhotoResp> images;



    private PhotoResp thumbnail_image;

/*    private String image_thumb_url;
    private String image_large_url;*/
    private PhotoResp video;

    public void withSupplier(Supplier supplier) {

        EntityModel supplierRespEntityModel = EntityModel.of(SupplierResp.simpleFrom(supplier));

        supplierRespEntityModel.add(linkTo(methodOn(SupplierRestController.class).getSupplier(supplier.getId())).withRel("supplier"));

        this.supplier = supplierRespEntityModel ;
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

    public List<PhotoResp> getImages() {
        return images;
    }

    public void setImages(List<PhotoResp> images) {
        this.images = images;
    }

    public PhotoResp getThumbnail_image() {
        return thumbnail_image;
    }

    public void setThumbnail_image(PhotoResp thumbnail_image) {
        this.thumbnail_image = thumbnail_image;
    }

    public PhotoResp getVideo() {
        return video;
    }

    public void setVideo(PhotoResp video) {
        this.video = video;
    }



    public  void setProducts(List<EntityModel> products) {
        this.products = products;
    }

    public List<EntityModel> getProducts() {
        return products;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public boolean getVisible() {
        return visible;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setLocation(LocationResp location) {
        this.location = location;
    }

    public LocationResp getLocation() {
        return location;
    }
}
