package com.lt.dom.otcReq;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.lt.dom.OctResp.ExtraResp;
import com.lt.dom.OctResp.PhotoResp;
import com.lt.dom.oct.Extra;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.EntityModel;

import javax.persistence.Transient;


public class ExtraEditReq {

    private ExtraResp extraResp;

    public ExtraResp getExtraResp() {
        return extraResp;
    }

    public void setExtraResp(ExtraResp extraResp) {
        this.extraResp = extraResp;
    }

    private EntityModel editTab;

    public void setEditTab(EntityModel editTab) {
        this.editTab = editTab;
    }

    public EntityModel getEditTab() {
        return editTab;
    }

    public static class EditTab {

        @JsonProperty("internalName")
        private String internalName;
        @JsonProperty("title")
        private String title;
        @JsonProperty("description")
        private String description;

        private String slug;
        private PhotoResp photo;
        private ExtraResp.PricingFrom pricingFrom;

        public ExtraResp.PricingFrom getPricingFrom() {
            return pricingFrom;
        }

        public void setPricingFrom(ExtraResp.PricingFrom pricingFrom) {
            this.pricingFrom = pricingFrom;
        }

        public static EditTab from(Extra movie) {

            EditTab extraReq = new EditTab();
            extraReq.setDescription(movie.getDescription());
            extraReq.setInternalName(movie.getInternalName());
            extraReq.setTitle(movie.getTitle());

            extraReq.setSlug(movie.getSlug());

            ExtraResp.PricingFrom pricingFrom1 = new ExtraResp.PricingFrom();
            pricingFrom1.setOriginal(movie.getPrice_Original());
            pricingFrom1.setNet(movie.getPrice_Net());
            pricingFrom1.setRetail(movie.getPrice_Retail());

            extraReq.setPricingFrom(pricingFrom1);

            //  extraReq.set
            return extraReq;
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
