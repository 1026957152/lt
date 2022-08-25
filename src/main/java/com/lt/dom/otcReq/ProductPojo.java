package com.lt.dom.otcReq;

import com.lt.dom.OctResp.AttributeResp;
import com.lt.dom.otcenum.EnumAvailabilityType;
import com.lt.dom.otcenum.EnumProductPricingType;
import com.lt.dom.otcenum.EnumProductPricingTypeByPerson;
import com.lt.dom.otcenum.EnumProductType;
import com.lt.dom.otcReq.product.ProductGiftVoucherPojo;
import com.lt.dom.vo.ByHour;
import com.lt.dom.vo.ByItem;
import com.lt.dom.vo.ByPerson;
import com.lt.dom.vo.Fixed;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

public class ProductPojo {


    @NotEmpty
    @Size(max = 50)
    private String name;
    private String source_id;

    private EnumAvailabilityType availability;
    @NotNull
    private EnumProductType type ;//Museums, Attractions or Hop on Hop off tours
    @NotEmpty
    @Size(max = 100)
    private String name_long;

    public EnumProductType getType() {
        return type;
    }

    public void setType(EnumProductType type) {
        this.type = type;
    }

    private ProductGiftVoucherPojo pojo;

    @Valid
    private List<Price> prices;


    private List<String> images;

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

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

    public DayTour getDay_tour() {
        return day_tour;
    }

    public void setDay_tour(DayTour day_tour) {
        this.day_tour = day_tour;
    }

    public DayTour day_tour;


    public static class DayTour {
        @Size(max = 30)
        List<Long> campaigns;
        public List<Long> getCampaigns() {
            return campaigns;
        }

        public void setCampaigns(List<Long> campaigns) {
            this.campaigns = campaigns;
        }

        private String guide_id;//	ID number for the lead customer

        @NotNull
        private Long guide;//

        public Long getGuide() {
            return guide;
        }

        public void setGuide(Long guide) {
            this.guide = guide;
        }

        private String title;//
        private String starts_at;//
        private String ends_at;//
        private String line_info;//

        public String getGuide_id() {
            return guide_id;
        }

        public void setGuide_id(String guide_id) {
            this.guide_id = guide_id;
        }

        public String getLine_info() {
            return line_info;
        }

        public void setLine_info(String line_info) {
            this.line_info = line_info;
        }
/*
                      "": "2014-09-01",
                              "ends_at": "2014-09-30",*/
        // tour guide 旅行团：tour group 旅行社： travel agency



    }






}
