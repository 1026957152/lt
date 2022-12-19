package com.lt.dom.OctResp;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.google.gson.Gson;
import com.lt.dom.HeroCardInfoResp;
import com.lt.dom.controllerOct.SupplierRestController;
import com.lt.dom.oct.*;
import com.lt.dom.otcReq.AttributeEditReq;
import com.lt.dom.otcReq.LocationResp;
import com.lt.dom.otcenum.EnumKnownfor;
import com.lt.dom.otcenum.EnumProductStatus;
import com.lt.dom.otcenum.EnumTags;
import org.springframework.hateoas.EntityModel;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class AttractionResp extends BaseResp {

    private List<EntityModel> products;
    private EntityModel supplier;
    private Boolean visible;
    private String code;
    private List<String> tags;
    private LocationResp location;

    private EntityModel<HeroCardInfoResp> heroCardInfo;
    private List<AttributeResp> knowBeforeYouGo;
    private List<AttributeResp> gettingThere;
    private List<AttributeResp> hoursOfOperation;
    private EntityModel selfGuidedTour;
    private Boolean selfGuided;

    private EntityModel<AssetResp> tourLink;
    private EntityModel<AssetResp> bookingLink;
    private EntityModel assets;
    private Map assetMap;
    private List assetList;
    private EnumProductStatus status;
    private String status_text;
    private PhotoResp thumb;
    private List knowfors;
    private String privacyLevel_text;
    private Long id;


    public EntityModel<AssetResp> getTourLink() {
        return tourLink;
    }

    public void setTourLink(EntityModel<AssetResp> tourLink) {
        this.tourLink = tourLink;
    }

    public EntityModel<AssetResp> getBookingLink() {
        return bookingLink;
    }

    public void setBookingLink(EntityModel<AssetResp> bookingLink) {
        this.bookingLink = bookingLink;
    }

    public EntityModel<HeroCardInfoResp> getHeroCardInfo() {
        return heroCardInfo;
    }

    public void setHeroCardInfo(EntityModel<HeroCardInfoResp> heroCardInfo) {
        this.heroCardInfo = heroCardInfo;
    }

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


    public static AttractionResp cityPassfrom20221026(Attraction attraction) {
        AttractionResp attractionResp = new AttractionResp();

        attractionResp.setName(attraction.getName());
        attractionResp.setShortdesc(attraction.getShortdesc());
        return attractionResp;
    }
    public static AttractionResp from(Attraction attraction) {
        AttractionResp attractionResp = new AttractionResp();
        attractionResp.setName(attraction.getName());
        attractionResp.setCode(attraction.getCode());
        attractionResp.setShortdesc(attraction.getShortdesc());
        attractionResp.setLongdesc(attraction.getLongdesc());

        attractionResp.setLatitude(attraction.getLatitude());
        attractionResp.setLongitude(attraction.getLongitude());



        attractionResp.setLongitude(attraction.getLongitude());
        if(attraction.getTags_json() != null){
            attractionResp.setTags(Arrays.stream((new Gson()).fromJson(attraction.getTags_json(),EnumTags[].class)).map(e->e.name()).toList());

        }else{
            attractionResp.setTags(Arrays.asList());

        }


        attractionResp.setCreatedDate(attraction.getCreatedDate());
        attractionResp.setModifiedDate(attraction.getModifiedDate());
        attractionResp.setStatus(attraction.getStatus());
        attractionResp.setStatus_text(attraction.getStatus().toString());
        attractionResp.setPrivacyLevel_text(attraction.getPrivacyLevel().toString());

        return attractionResp;
    }



    public static AttractionResp from(Attraction attraction,PhotoResp photoResp) {
        AttractionResp attractionResp = AttractionResp.from(attraction);
        attractionResp.setThumbnail_image(photoResp);

        return attractionResp;
    }

    public static AttractionResp simpleFrom(Attraction attraction) {
        AttractionResp attractionResp = new AttractionResp();
        attractionResp.setName(attraction.getName());
        attractionResp.setShortdesc(attraction.getShortdesc());

        attractionResp.setStatus_text(attraction.getStatus().toString());

        return attractionResp;

    }
    public static AttractionResp simpleFrom(Attraction attraction,PhotoResp photoResp) {
        AttractionResp attractionResp = new AttractionResp();
        attractionResp.setName(attraction.getName());
        attractionResp.setShortdesc(attraction.getShortdesc());

        if(attraction.getTags_json() != null){
            attractionResp.setTags(Arrays.stream((new Gson()).fromJson(attraction.getTags_json(),EnumTags[].class)).map(e->e.name()).toList());

        }else{
            attractionResp.setTags(Arrays.asList());

        }

        if(attraction.getKnowfors().isEmpty()){
            KnowforResp knowforResp = new KnowforResp();
            knowforResp.setKnownfor_text(EnumKnownfor.History.toString());
            knowforResp.setText(EnumKnownfor.History.toString());

            attractionResp.setKnowfors(Arrays.asList(knowforResp));

        }else{
            attractionResp.setKnowfors(attraction.getKnowfors().stream().map(e->{
                KnowforResp knowforResp = KnowforResp.from(e);
                return knowforResp;
            }).collect(Collectors.toList()));
        }
/*        attractionResp.setKnowfors(attraction.getKnowfors().stream().map(e->{
            KnowforResp knowforResp = KnowforResp.from(e);
            return knowforResp;
        }).collect(Collectors.toList()));*/
        attractionResp.setThumbnail_image(photoResp);

        attractionResp.setStatus_text(attraction.getStatus().toString());
        attractionResp.setThumbnail_image(photoResp);
        return attractionResp;
/*        attractionResp.setThumbnail_image(photoResp);

        return attractionResp;*/
    }









    public static AttractionResp simpleFromId(Attraction attraction,PhotoResp photoResp) {
        AttractionResp attractionResp = new AttractionResp();
        attractionResp.setName(attraction.getName());
        attractionResp.setId(attraction.getId());
        attractionResp.setShortdesc(attraction.getShortdesc());

        if(attraction.getTags_json() != null){
            attractionResp.setTags(Arrays.stream((new Gson()).fromJson(attraction.getTags_json(),EnumTags[].class)).map(e->e.name()).toList());

        }else{
            attractionResp.setTags(Arrays.asList());

        }

        if(attraction.getKnowfors().isEmpty()){
            KnowforResp knowforResp = new KnowforResp();
            knowforResp.setKnownfor_text(EnumKnownfor.History.toString());
            knowforResp.setText(EnumKnownfor.History.toString());

            attractionResp.setKnowfors(Arrays.asList(knowforResp));

        }else{
            attractionResp.setKnowfors(attraction.getKnowfors().stream().map(e->{
                KnowforResp knowforResp = KnowforResp.from(e);
                return knowforResp;
            }).collect(Collectors.toList()));
        }

        attractionResp.setThumbnail_image(photoResp);

        attractionResp.setStatus_text(attraction.getStatus().toString());
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

    public void setVisible(Boolean visible) {
        this.visible = visible;
    }

    public Boolean getVisible() {
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

    public void setKnowBeforeYouGo(List<AttributeResp> knowBeforeYouGo) {
        this.knowBeforeYouGo = knowBeforeYouGo;
    }

    public List<AttributeResp> getKnowBeforeYouGo() {
        return knowBeforeYouGo;
    }

    public  void setGettingThere(List<AttributeResp> gettingThere) {
        this.gettingThere = gettingThere;
    }

    public List<AttributeResp> getGettingThere() {
        return gettingThere;
    }

    public  void setHoursOfOperation(List<AttributeResp> hoursOfOperation) {
        this.hoursOfOperation = hoursOfOperation;
    }

    public List<AttributeResp> getHoursOfOperation() {
        return hoursOfOperation;
    }

    public <V, K> void setSelfGuidedTour(EntityModel selfGuidedTour) {
        this.selfGuidedTour = selfGuidedTour;
    }

    public EntityModel getSelfGuidedTour() {
        return selfGuidedTour;
    }

    public void setSelfGuided(Boolean selfGuided) {
        this.selfGuided = selfGuided;
    }

    public Boolean isSelfGuided() {
        return selfGuided;
    }

    public <T> void setAssets(EntityModel assets) {
        this.assets = assets;
    }

    public EntityModel getAssets() {
        return assets;
    }

    public void setAssetMap(Map assetMap) {
        this.assetMap = assetMap;
    }

    public Map getAssetMap() {
        return assetMap;
    }

    public void setAssetList(List assetList) {
        this.assetList = assetList;
    }

    public List getAssetList() {
        return assetList;
    }

    public void withAbout(List<Attribute> bookingRuleList) {

        this.setKnowBeforeYouGo(bookingRuleList.stream()
                .filter(e->"knowBeforeYouGo".equals(e.getKey()))
                .map(e->{
                    AttributeResp attributeEditReq = new AttributeResp();
                    attributeEditReq.setName(e.getName());
                    attributeEditReq.setDescription(e.getDescription());
                    attributeEditReq.setType(FeatureTagResp.from(e.getFeatureType()));
                    return attributeEditReq;

                }).collect(Collectors.toList()));
        this.setGettingThere(bookingRuleList.stream()
                .filter(e->"gettingThere".equals(e.getKey()))
                .map(e->{
                    AttributeResp attributeEditReq = new AttributeResp();
                    attributeEditReq.setName(e.getName());
                    attributeEditReq.setDescription(e.getDescription());
                    attributeEditReq.setType(FeatureTagResp.from(e.getFeatureType()));
                    return attributeEditReq;

                }).collect(Collectors.toList()));
        this.setHoursOfOperation(bookingRuleList.stream()
                .filter(e->"hoursOfOperation".equals(e.getKey()))
                .map(e->{
                    AttributeResp attributeEditReq = new AttributeResp();
                    attributeEditReq.setName(e.getName());
                    attributeEditReq.setDescription(e.getDescription());
                    attributeEditReq.setType(FeatureTagResp.from(e.getFeatureType()));
                    return attributeEditReq;

                }).collect(Collectors.toList()));
    }

    public void setStatus(EnumProductStatus status) {
        this.status = status;
    }

    public EnumProductStatus getStatus() {
        return status;
    }

    public void setStatus_text(String status_text) {
        this.status_text = status_text;
    }

    public String getStatus_text() {
        return status_text;
    }

    public void setThumb(PhotoResp thumb) {
        this.thumb = thumb;
    }

    public PhotoResp getThumb() {
        return thumb;
    }

    public <R> void setKnowfors(List knowfors) {
        this.knowfors = knowfors;
    }

    public List getKnowfors() {
        return knowfors;
    }

    public void setPrivacyLevel_text(String privacyLevel_text) {
        this.privacyLevel_text = privacyLevel_text;
    }

    public String getPrivacyLevel_text() {
        return privacyLevel_text;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
