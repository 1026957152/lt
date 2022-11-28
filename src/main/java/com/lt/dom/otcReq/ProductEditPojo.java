package com.lt.dom.otcReq;

import com.lt.dom.OctResp.AttributeResp;
import com.lt.dom.OctResp.PhotoResp;
import com.lt.dom.otcReq.product.ProductGiftVoucherPojo;
import com.lt.dom.otcenum.*;
import com.lt.dom.vo.ByHour;
import com.lt.dom.vo.ByItem;
import com.lt.dom.vo.ByPerson;
import com.lt.dom.vo.Fixed;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

public class ProductEditPojo {

    private List<FeatureTagReq> featureTags;

    public List<FeatureTagReq> getFeatureTags() {
        return featureTags;
    }

    public void setFeatureTags(List<FeatureTagReq> featureTags) {
        this.featureTags = featureTags;
    }

    @Size(max = 50)
    private String name;
    private String source_id;

    private EnumAvailabilityType availability;

    public String getDesc_short() {
        return desc_short;
    }

    public void setDesc_short(String desc_short) {
        this.desc_short = desc_short;
    }

    @Size(max = 100)
    private String name_long;
    private String desc_long;
    private String desc_short;
    private String note;
    private Boolean show_note;
    private Boolean free;
    private EnumAvailabilityType availabilityType;
    private EnumPrivacyLevel privacyLevel;
    private List<EnumTags> tags;


    private List<PhotoResp> images;

    private PhotoResp thumb;
    private PhotoResp video;

    public PhotoResp getVideo() {
        return video;
    }

    public PhotoResp getThumb() {
        return thumb;
    }

    public void setThumb(PhotoResp thumb) {
        this.thumb = thumb;
    }

    public void setVideo(PhotoResp video) {
        this.video = video;
    }

    public void setImages(List<PhotoResp> images) {
        this.images = images;
    }

    public EnumAvailabilityType getAvailability() {
        return availability;
    }

    public void setAvailability(EnumAvailabilityType availability) {
        this.availability = availability;
    }

    public String getDesc_long() {
        return desc_long;
    }

    public void setDesc_long(String desc_long) {
        this.desc_long = desc_long;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Boolean getShow_note() {
        return show_note;
    }

    public void setShow_note(Boolean show_note) {
        this.show_note = show_note;
    }

    public Boolean getFree() {
        return free;
    }

    public void setFree(Boolean free) {
        this.free = free;
    }

    public EnumAvailabilityType getAvailabilityType() {
        return availabilityType;
    }

    public List<PhotoResp> getImages() {
        return images;
    }

    public void setAvailabilityType(EnumAvailabilityType availabilityType) {
        this.availabilityType = availabilityType;
    }

    public ProductGiftVoucherPojo getPojo() {
        return pojo;
    }

    public void setPojo(ProductGiftVoucherPojo pojo) {
        this.pojo = pojo;
    }

    private ProductGiftVoucherPojo pojo;

    @Valid
    private List<Price> prices;



    public List<Price> getPrices() {
        return prices;
    }

    public void setPrices(List<Price> prices) {
        this.prices = prices;
    }

    public String getName_long() {
        return name_long;
    }

    public void setName_long(String name_long) {
        this.name_long = name_long;
    }

    public EnumPrivacyLevel getPrivacyLevel() {
        return privacyLevel;
    }

    public void setPrivacyLevel(EnumPrivacyLevel privacyLevel) {
        this.privacyLevel = privacyLevel;
    }

    public List<EnumTags> getTags() {
        return tags;
    }

    public void setTags(List<EnumTags> tags) {
        this.tags = tags;
    }


    public static class Price {

        @NotNull
        private EnumProductPricingType type;
        @NotNull
        private EnumProductPricingTypeByPerson by;
        private ByHour byHour;
        private ByItem byItem;
        private ByPerson byPerson;
        private Fixed fixed;
        @Override
        public String toString() {
            return ReflectionToStringBuilder.toString(this);
        }
        public EnumProductPricingType getType() {
            return type;
        }

        public void setType(EnumProductPricingType type) {
            this.type = type;
        }

        public EnumProductPricingTypeByPerson getBy() {
            return by;
        }

        public void setBy(EnumProductPricingTypeByPerson by) {
            this.by = by;
        }

        public ByHour getByHour() {
            return byHour;
        }

        public void setByHour(ByHour byHour) {
            this.byHour = byHour;
        }

        public ByItem getByItem() {
            return byItem;
        }

        public void setByItem(ByItem byItem) {
            this.byItem = byItem;
        }

        public ByPerson getByPerson() {
            return byPerson;
        }

        public void setByPerson(ByPerson byPerson) {
            this.byPerson = byPerson;
        }

        public Fixed getFixed() {
            return fixed;
        }

        public void setFixed(Fixed fixed) {
            this.fixed = fixed;
        }
    }




    List<AttributeResp> attributes;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSource_id() {
        return source_id;
    }

    public void setSource_id(String source_id) {
        this.source_id = source_id;
    }



    public List<AttributeResp> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<AttributeResp> attributes) {
        this.attributes = attributes;
    }




    @Size(max = 30)
    List<Long> campaigns;

    public List<Long> getCampaigns() {
        return campaigns;
    }

    public void setCampaigns(List<Long> campaigns) {
        this.campaigns = campaigns;
    }



    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }

}
