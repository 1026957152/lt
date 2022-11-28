package com.lt.dom.OctResp;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.google.gson.Gson;
import com.lt.dom.HeroCardInfoResp;
import com.lt.dom.controllerOct.SupplierRestController;
import com.lt.dom.oct.*;
import com.lt.dom.otcReq.AttributeEditReq;
import com.lt.dom.otcReq.FeatureTagReq;
import com.lt.dom.otcReq.LocationEditResp;
import com.lt.dom.otcReq.LocationResp;
import com.lt.dom.otcenum.EnumPrivacyLevel;
import com.lt.dom.otcenum.EnumProductStatus;
import com.lt.dom.otcenum.EnumProductType;
import com.lt.dom.otcenum.EnumTags;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.tomcat.jni.Local;
import org.springframework.hateoas.EntityModel;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class AttractionEditResp {

    private List<EntityModel> products;
    private EntityModel supplier;

    private String code;


    private EntityModel<HeroCardInfoResp> heroCardInfo;



    private EntityModel<AssetResp> tourLink;
    private EntityModel<AssetResp> bookingLink;
    private EntityModel assets;

    private List assetList;
    private EntityModel infoTab;
    private EntityModel aboutTab;
    private EntityModel productTab;
    private EntityModel assetTap;
    private EntityModel selfGuideTap;


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

    public static AttractionEditResp from(Attraction attraction) {
        AttractionEditResp attractionResp = new AttractionEditResp();

        attractionResp.setCode(attraction.getCode());



        return attractionResp;
    }



    public static AttractionEditResp from(Attraction attraction, PhotoResp photoResp) {
        AttractionEditResp attractionResp = AttractionEditResp.from(attraction);
       // attractionResp.setThumbnail_image(photoResp);

        return attractionResp;
    }


    private String CategoryCodes;


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





    public  void setProducts(List<EntityModel> products) {
        this.products = products;
    }

    public List<EntityModel> getProducts() {
        return products;
    }



    public void setCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }





    public <T> void setAssets(EntityModel assets) {
        this.assets = assets;
    }

    public EntityModel getAssets() {
        return assets;
    }



    public void setAssetList(List assetList) {
        this.assetList = assetList;
    }

    public List getAssetList() {
        return assetList;
    }

    public void setInfoTab(EntityModel infoTab) {
        this.infoTab = infoTab;
    }

    public EntityModel getInfoTab() {
        return infoTab;
    }

    public void setAboutTab(EntityModel aboutTab) {
        this.aboutTab = aboutTab;
    }

    public EntityModel getAboutTab() {
        return aboutTab;
    }

    public void setProductTab(EntityModel productTab) {
        this.productTab = productTab;
    }

    public EntityModel getProductTab() {
        return productTab;
    }

    public void setAssetTap(EntityModel assetTap) {
        this.assetTap = assetTap;
    }

    public EntityModel getAssetTap() {
        return assetTap;
    }

    public void setSelfGuideTap(EntityModel selfGuideTap) {
        this.selfGuideTap = selfGuideTap;
    }

    public EntityModel getSelfGuideTap() {
        return selfGuideTap;
    }


    public static class InfoTab {
        @Override
        public String toString() {
            return ReflectionToStringBuilder.toString(this);
        }
        private String name;



        private Float latitude;
        private Float longitude;



        private String shortdesc; //Short description (NO HTML)
        private String longdesc;


        private List<PhotoResp> images;


        private EnumPrivacyLevel privacyLevel;

        public EnumPrivacyLevel getPrivacyLevel() {
            return privacyLevel;
        }

        public void setPrivacyLevel(EnumPrivacyLevel privacyLevel) {
            this.privacyLevel = privacyLevel;
        }
        private List<EnumTags> tags;
        private PhotoResp video;
        private Map parameterList;
        private PhotoResp thumb;


        private LocationEditResp location;

        public LocationEditResp getLocation() {
            return location;
        }

        public void setLocation(LocationEditResp location) {
            this.location = location;
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


        public PhotoResp getVideo() {
            return video;
        }

        public void setVideo(PhotoResp video) {
            this.video = video;
        }

        public static InfoTab from(Attraction product) {


            InfoTab resp = new InfoTab();
            resp.setName(product.getName());
            resp.setShortdesc(product.getShortdesc());
            resp.setLongdesc(product.getLongdesc());
            resp.setLatitude(product.getLatitude());
            resp.setLongitude(product.getLongitude());
            resp.setPrivacyLevel(product.getPrivacyLevel());

            if(product.getTags_json()!= null){
                resp.setTags(Arrays.stream((new Gson()).fromJson(product.getTags_json(),EnumTags[].class)).toList());

            }else{
                resp.setTags(Arrays.asList());
            }
            Address address = product.getLocation();
            if(address != null){
                resp.setLocation(LocationEditResp.from(address));
            }else{
                LocationEditResp locationResp = new LocationEditResp();
                locationResp.setAddress("");
                resp.setLocation(locationResp);

            }



/*
            resp.setFree(product.getFree());
            resp.setShow_note(product.getShow_note());
            resp.setNote(product.getNote());
            resp.setStatus(product.getStatus());
            resp.setStatus_text(product.getStatus().toString());
            product.getFeatureTags_json();



            resp.setFeatureTags(Arrays.stream(new Gson().fromJson(product.getFeatureTags_json(), FeatureTagReq[].class)).map(e->{
                FeatureTagResp featureTagResp = new FeatureTagResp();
                featureTagResp.setType(e.getType());
                featureTagResp.setText(e.getText());
                featureTagResp.setIcon(e.getType().getIcon());
                return featureTagResp;
            }).collect(Collectors.toList()));

            resp.setStatus_text(product.getFeatureTags_json());
            resp.setTags((new Gson()).fromJson(product.getTags_json(),List.class));
*/


            return resp;
        }

        public <V, K> void setParameterList(Map parameterList) {
            this.parameterList = parameterList;
        }

        public Map getParameterList() {
            return parameterList;
        }

        public void setThumb(PhotoResp thumb) {
            this.thumb = thumb;
        }

        public PhotoResp getThumb() {
            return thumb;
        }

        public List<EnumTags> getTags() {
            return tags;
        }

        public void setTags(List<EnumTags> tags) {
            this.tags = tags;
        }
    }


    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class AboutTap {

        private List<AttributeEditReq> knowBeforeYouGo;
        private List<AttributeEditReq> gettingThere;
        private List<AttributeEditReq> hoursOfOperation;
        private Map parameterList;

        private List tips;

        public List<AttributeEditReq> getKnowBeforeYouGo() {
            return knowBeforeYouGo;
        }

        public void setKnowBeforeYouGo(List<AttributeEditReq> knowBeforeYouGo) {
            this.knowBeforeYouGo = knowBeforeYouGo;
        }

        public List<AttributeEditReq> getGettingThere() {
            return gettingThere;
        }

        public void setGettingThere(List<AttributeEditReq> gettingThere) {
            this.gettingThere = gettingThere;
        }

        public List<AttributeEditReq> getHoursOfOperation() {
            return hoursOfOperation;
        }

        public void setHoursOfOperation(List<AttributeEditReq> hoursOfOperation) {
            this.hoursOfOperation = hoursOfOperation;
        }

        public static AboutTap from(List<Attribute> bookingRuleList) {
            AboutTap availability = new AboutTap();

            availability.setKnowBeforeYouGo(bookingRuleList.stream()
                            .filter(e->"knowBeforeYouGo".equals(e.getKey()))
                    .map(e->{
                AttributeEditReq attributeEditReq = new AttributeEditReq();
                attributeEditReq.setName(e.getName());
                attributeEditReq.setText(e.getDescription());
                attributeEditReq.setType(e.getFeatureType());
                attributeEditReq.setId(e.getId());
                return attributeEditReq;

            }).collect(Collectors.toList()));
            availability.setGettingThere(bookingRuleList.stream()
                    .filter(e->"gettingThere".equals(e.getKey()))
                    .map(e->{
                        AttributeEditReq attributeEditReq = new AttributeEditReq();
                        attributeEditReq.setName(e.getName());
                        attributeEditReq.setText(e.getDescription());
                        attributeEditReq.setType(e.getFeatureType());
                        attributeEditReq.setId(e.getId());
                        return attributeEditReq;

                    }).collect(Collectors.toList()));
            availability.setHoursOfOperation(bookingRuleList.stream()
                    .filter(e->"hoursOfOperation".equals(e.getKey()))

                    .map(e->{
                        AttributeEditReq attributeEditReq = new AttributeEditReq();
                        attributeEditReq.setName(e.getName());
                        attributeEditReq.setText(e.getDescription());
                        attributeEditReq.setType(e.getFeatureType());
                        attributeEditReq.setId(e.getId());
                        return attributeEditReq;

                    }).collect(Collectors.toList()));
/*            availability.setComponents(bookingRuleList.stream().map(e->{
                return ComponentResp.from(e);
            }).collect(Collectors.toList()));*/

            return availability;
        }


        public <V, K> void setParameterList(Map parameterList) {
            this.parameterList = parameterList;
        }

        public Map getParameterList() {
            return parameterList;
        }


        public <T> void setTips(List tips) {
            this.tips = tips;
        }

        public List getTips() {
            return tips;
        }


    }



    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class SelfGuideTap {

        private Map parameterList;

        private List tips;
        private Long selfGuided;

        public static SelfGuideTap from() {
            SelfGuideTap availability = new SelfGuideTap();




            return availability;
        }


        public <V, K> void setParameterList(Map parameterList) {
            this.parameterList = parameterList;
        }

        public Map getParameterList() {
            return parameterList;
        }


        public <T> void setTips(List tips) {
            this.tips = tips;
        }

        public List getTips() {
            return tips;
        }

        public void setSelfGuided(Long selfGuided) {
            this.selfGuided = selfGuided;
        }

        public Long getSelfGuided() {
            return selfGuided;
        }
    }



    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class ProductTap {

        private Map parameterList;

        private List tips;
        private Long selfGuided;
        private List<Long> products;

        public static ProductTap from(Attraction product) {
            ProductTap availability = new ProductTap();

            if(product.getProducts_json()!= null){
                availability.setProducts(Arrays.stream(new Gson().fromJson(product.getProducts_json(),Long[].class)).collect(Collectors.toList()));

            }else{
                availability.setProducts(Arrays.asList());
            }


            return availability;
        }


        public <V, K> void setParameterList(Map parameterList) {
            this.parameterList = parameterList;
        }

        public Map getParameterList() {
            return parameterList;
        }


        public <T> void setTips(List tips) {
            this.tips = tips;
        }

        public List getTips() {
            return tips;
        }

        public void setSelfGuided(Long selfGuided) {
            this.selfGuided = selfGuided;
        }

        public Long getSelfGuided() {
            return selfGuided;
        }

        public <R> void setProducts(List<Long> products) {
            this.products = products;
        }

        public List<Long> getProducts() {
            return products;
        }
    }







    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class AssetTap {
        public void setAssetMap(Map assetMap) {
            this.assetMap = assetMap;
        }

        public Map getAssetMap() {
            return assetMap;
        }
        private Map parameterList;

        private List tips;
        private Map assetMap;

        public static AssetTap from() {
            AssetTap availability = new AssetTap();




            return availability;
        }


        public <V, K> void setParameterList(Map parameterList) {
            this.parameterList = parameterList;
        }

        public Map getParameterList() {
            return parameterList;
        }


        public <T> void setTips(List tips) {
            this.tips = tips;
        }

        public List getTips() {
            return tips;
        }


    }
}
