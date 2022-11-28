package com.lt.dom.otcReq;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.lt.dom.OctResp.BaseResp;
import com.lt.dom.OctResp.ExtraResp;
import com.lt.dom.OctResp.PhotoResp;
import com.lt.dom.oct.Extra;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Transient;


public class ExtraReq  {



    private String code;
    @JsonProperty("internalName")
    private String internalName;
    @JsonProperty("title")
    private String title;
    @JsonProperty("description")
    private String description;
    @JsonProperty("reference")
    private String reference;
    private String slug;
    private PhotoResp photo;
    private ExtraResp.PricingFrom pricingFrom;

    public ExtraResp.PricingFrom getPricingFrom() {
        return pricingFrom;
    }

    public void setPricingFrom(ExtraResp.PricingFrom pricingFrom) {
        this.pricingFrom = pricingFrom;
    }

    public static ExtraReq from(Extra movie) {

        ExtraReq extraReq = new ExtraReq();
        extraReq.setDescription(movie.getDescription());
        extraReq.setInternalName(movie.getInternalName());
        extraReq.setTitle(movie.getTitle());


        ExtraResp.PricingFrom pricingFrom1 = new ExtraResp.PricingFrom();
        pricingFrom1.setOriginal(movie.getPrice_Original());
        pricingFrom1.setNet(movie.getPrice_Net());
        pricingFrom1.setRetail(movie.getPrice_Retail());

        extraReq.setPricingFrom(pricingFrom1);

      //  extraReq.set
        return extraReq;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getInternalName() {
        return internalName;
    }

    public void setInternalName(String internalName) {
        this.internalName = internalName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public RestrictionsDTO getRestrictions() {
        return restrictions;
    }

    public void setRestrictions(RestrictionsDTO restrictions) {
        this.restrictions = restrictions;
    }

    @Transient
    @JsonProperty("restrictions")
    private RestrictionsDTO restrictions;

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public PhotoResp getPhoto() {
        return photo;
    }

    public void setPhoto(PhotoResp photo) {
        this.photo = photo;
    }


    @NoArgsConstructor
    @Data
    public static class RestrictionsDTO {
        @JsonProperty("minQuantity")
        private Integer minQuantity;
        @JsonProperty("maxQuantity")
        private Integer maxQuantity;
    }
}
