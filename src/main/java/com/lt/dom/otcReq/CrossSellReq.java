package com.lt.dom.otcReq;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.lt.dom.OctResp.BaseResp;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Transient;


public class CrossSellReq extends BaseResp {

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    private String code;
    private Long id;
    @JsonProperty("title")
    private String title;
    @JsonProperty("description")
    private String description;
    @JsonProperty("reference")
    private String reference;
    private String slug;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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


    @NoArgsConstructor
    @Data
    public static class RestrictionsDTO {
        @JsonProperty("minQuantity")
        private Integer minQuantity;
        @JsonProperty("maxQuantity")
        private Integer maxQuantity;
    }
}
