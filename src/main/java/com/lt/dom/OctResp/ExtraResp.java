package com.lt.dom.OctResp;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.lt.dom.oct.Base;
import com.lt.dom.oct.Extra;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.parameters.P;

import javax.persistence.Entity;
import javax.persistence.Transient;


@JsonInclude(JsonInclude.Include.NON_NULL)
public class ExtraResp extends BaseResp {



    private String code;
    @JsonProperty("internalName")
    private String internalName;
    @JsonProperty("title")
    private String title;
    @JsonProperty("description")
    private String description;
    @JsonProperty("reference")
    private String reference;


    private PricingFrom pricingFrom;
    private Long id;
    private PhotoResp photo;

    public PricingFrom getPricingFrom() {
        return pricingFrom;
    }

    public void setPricingFrom(PricingFrom pricingFrom) {
        this.pricingFrom = pricingFrom;
    }

    public static ExtraResp from(Extra movie) {

        ExtraResp extraResp = new ExtraResp();
        extraResp.setCode(movie.getCode());
        extraResp.setDescription(movie.getDescription());
        extraResp.setTitle(movie.getTitle());
        extraResp.setInternalName(movie.getInternalName());
        extraResp.setId(movie.getId());

        PricingFrom pricingFrom1 = new PricingFrom();
        pricingFrom1.setOriginal(movie.getPrice_Original());
        pricingFrom1.setNet(movie.getPrice_Net());
        pricingFrom1.setRetail(movie.getPrice_Retail());

        extraResp.setPricingFrom(pricingFrom1);
        extraResp.setCreatedDate(movie.getCreatedDate());
        extraResp.setModifiedDate(movie.getModifiedDate());

        return extraResp;
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

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setPhoto(PhotoResp photo) {
        this.photo = photo;
    }

    public PhotoResp getPhoto() {
        return photo;
    }


    @NoArgsConstructor
    @Data
    public static class RestrictionsDTO {
        @JsonProperty("minQuantity")
        private Integer minQuantity;
        @JsonProperty("maxQuantity")
        private Integer maxQuantity;
    }
    public static class PricingFrom {

        private Float original;
        private Float retail;
        private Float net;

        public Float getOriginal() {
            return original;
        }

        public void setOriginal(Float original) {
            this.original = original;
        }

        public Float getRetail() {
            return retail;
        }

        public void setRetail(Float retail) {
            this.retail = retail;
        }

        public Float getNet() {
            return net;
        }

        public void setNet(Float net) {
            this.net = net;
        }
    }

}
