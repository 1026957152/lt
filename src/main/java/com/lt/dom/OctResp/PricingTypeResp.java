package com.lt.dom.OctResp;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.lt.dom.oct.PricingType;
import com.lt.dom.otcenum.EnumProductPricingType;
import com.lt.dom.otcenum.EnumProductPricingTypeByPerson;
import com.lt.dom.otcenum.EnumProductPricingTypeByPersonGroupType;
import com.lt.dom.vo.NegotiatedPricingType;

import java.util.List;


@JsonInclude(JsonInclude.Include.NON_NULL)
public class PricingTypeResp {

    private Long productId;

    private EnumProductPricingType type;
    private EnumProductPricingTypeByPerson by;
    private Integer min;
    private Integer max;
    private EnumProductPricingTypeByPersonGroupType unit;
    private Integer price;
    private String lable;
    private String type_Text;
    private String by_text;
    private Integer original;
    private Integer retail;
    private Integer net;
    private String name;
    private Long id;
    private NegotiatedPricingType negotiatedSku;
    private PhotoResp image;

/*
    public static PricingTypeResp from(PricingType e) {
        PricingTypeResp pricingTypeResp = new PricingTypeResp();



        pricingTypeResp.setType(e.getType());
        pricingTypeResp.setType_Text(e.getType().toString());
        pricingTypeResp.setLable(e.getLable());


        pricingTypeResp.setOriginal(e.getPrice());
        pricingTypeResp.setRetail(e.getPrice());
        pricingTypeResp.setNet(e.getPrice());

        if(e.getType().equals(EnumProductPricingType.ByPerson)){
            pricingTypeResp.setPrice(e.getPrice());
            pricingTypeResp.setBy(e.getBy());
            pricingTypeResp.setBy_text(e.getBy().toString());
        }
        if(e.getType().equals(EnumProductPricingType.ByItem)){
            pricingTypeResp.setUnit(e.getUnit());
        }
        if(e.getType().equals(EnumProductPricingType.Fixed)){
            pricingTypeResp.setPrice(e.getPrice());
        }

        if(e.getType().equals(EnumProductPricingType.ByDay) || e.getType().equals(EnumProductPricingType.ByHour) || e.getType().equals(EnumProductPricingType.ByMinute)  ){
            pricingTypeResp.setMin(e.getMin());
            pricingTypeResp.setMax(e.getMax());
        }
        pricingTypeResp.setName(pricingTypeResp.getBy_text());

        return pricingTypeResp;
    }*/


    public static PricingTypeResp from(PricingType e) {
        PricingTypeResp pricingTypeResp = new PricingTypeResp();




        pricingTypeResp.setType(e.getType());
        pricingTypeResp.setType_Text(e.getType().toString());
        pricingTypeResp.setLable(e.getLable());
        pricingTypeResp.setOriginal(e.getPrice());
        pricingTypeResp.setRetail(e.getPrice());
        pricingTypeResp.setNet(e.getPrice());


        if(e.getType().equals(EnumProductPricingType.ByPerson)){
            pricingTypeResp.setPrice(e.getPrice());
            pricingTypeResp.setBy(e.getBy());
            pricingTypeResp.setBy_text(e.getBy().toString());
        }
        if(e.getType().equals(EnumProductPricingType.ByItem)){
            pricingTypeResp.setPrice(e.getPrice());
        }
        if(e.getType().equals(EnumProductPricingType.Fixed)){
            pricingTypeResp.setPrice(e.getPrice());
        }

        if(e.getType().equals(EnumProductPricingType.ByDay) || e.getType().equals(EnumProductPricingType.ByHour) || e.getType().equals(EnumProductPricingType.ByMinute)  ){
            pricingTypeResp.setMin(e.getMin());
            pricingTypeResp.setMax(e.getMax());
            pricingTypeResp.setUnit(e.getGroup_type());
        }
        pricingTypeResp.setName(e.getNick_name());

        RestrictionResp restriction1 = new RestrictionResp();
        restriction1.setMinAge(e.getRestriction_MinAge());
        restriction1.setMinAge(e.getRestriction_MinAge());
        restriction1.setMaxAge(e.getRestriction_MaxAge());
        restriction1.setIdRequired(e.getRestriction_IdRequired());
        restriction1.setMaxQuantity(e.getRestriction_MaxQuantity());
        restriction1.setMinQuantity(e.getRestriction_MinQuantity());
        restriction1.setPaxCount(e.getRestriction_PaxCount());
        pricingTypeResp.setRestriction(restriction1);
       // pricingTypeResp.setName(pricingTypeResp.getBy_text());
        return pricingTypeResp;
    }



    public static PricingTypeResp sku(PricingType e) {
        PricingTypeResp pricingTypeResp = new PricingTypeResp();


        pricingTypeResp.setId(e.getId());
    //    pricingTypeResp.setType(e.getType());

    //    pricingTypeResp.setType_Text(e.getType().toString());
      //  pricingTypeResp.setLable(e.getLable());
        pricingTypeResp.setOriginal(e.getPrice());
        pricingTypeResp.setRetail(e.getPrice());
        pricingTypeResp.setNet(e.getPrice());

/*
        if(e.getType().equals(EnumProductPricingType.ByPerson)){
            pricingTypeResp.setPrice(e.getPrice());
            pricingTypeResp.setBy(e.getBy());
            pricingTypeResp.setBy_text(e.getBy().toString());
        }
        if(e.getType().equals(EnumProductPricingType.ByItem)){
            pricingTypeResp.setUnit(e.getUnit());
        }
        if(e.getType().equals(EnumProductPricingType.Fixed)){
            pricingTypeResp.setPrice(e.getPrice());
        }

        if(e.getType().equals(EnumProductPricingType.ByDay) || e.getType().equals(EnumProductPricingType.ByHour) || e.getType().equals(EnumProductPricingType.ByMinute)  ){
            pricingTypeResp.setMin(e.getMin());
            pricingTypeResp.setMax(e.getMax());
        }*/
        pricingTypeResp.setName(e.getNick_name());

        RestrictionResp restriction1 = new RestrictionResp();
        restriction1.setMinAge(e.getRestriction_MinAge());
        restriction1.setMinAge(e.getRestriction_MinAge());
        restriction1.setMaxAge(e.getRestriction_MaxAge());
        restriction1.setIdRequired(e.getRestriction_IdRequired());
        restriction1.setMaxQuantity(e.getRestriction_MaxQuantity());
        restriction1.setMinQuantity(e.getRestriction_MinQuantity());
        restriction1.setPaxCount(e.getRestriction_PaxCount());
        pricingTypeResp.setRestriction(restriction1);
        return pricingTypeResp;
    }

    public static PricingTypeResp sku_simpleBooking(PricingType e) {
        PricingTypeResp pricingTypeResp = new PricingTypeResp();

        pricingTypeResp.setOriginal(e.getPrice());
        pricingTypeResp.setRetail(e.getPrice());
        pricingTypeResp.setNet(e.getPrice());
        pricingTypeResp.setId(e.getId());

        pricingTypeResp.setName(e.getNick_name());


        return pricingTypeResp;
    }
    public static PricingTypeResp sku_simple(PricingType e) {
        PricingTypeResp pricingTypeResp = new PricingTypeResp();

        pricingTypeResp.setOriginal(e.getPrice());
        pricingTypeResp.setRetail(e.getPrice());
        pricingTypeResp.setNet(e.getPrice());

        pricingTypeResp.setName(e.getNick_name());


        return pricingTypeResp;
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

    public Integer getMin() {
        return min;
    }

    public void setMin(Integer min) {
        this.min = min;
    }

    public Integer getMax() {
        return max;
    }

    public void setMax(Integer max) {
        this.max = max;
    }

    public EnumProductPricingTypeByPersonGroupType getUnit() {
        return unit;
    }

    public void setUnit(EnumProductPricingTypeByPersonGroupType unit) {
        this.unit = unit;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getLable() {
        return lable;
    }

    public void setLable(String lable) {
        this.lable = lable;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setType_Text(String type_text) {
        this.type_Text = type_text;
    }

    public String getType_Text() {
        return type_Text;
    }

    public void setBy_text(String by_text) {
        this.by_text = by_text;
    }

    public String getBy_text() {
        return by_text;
    }

    public void setOriginal(Integer original) {
        this.original = original;
    }

    public Integer getOriginal() {
        return original;
    }

    public void setRetail(Integer retail) {
        this.retail = retail;
    }

    public Integer getRetail() {
        return retail;
    }

    public void setNet(Integer net) {
        this.net = net;
    }

    public Integer getNet() {
        return net;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }


    public RestrictionResp getRestriction() {
        return restriction;
    }

    public void setRestriction(RestrictionResp restriction) {
        this.restriction = restriction;
    }

    private RestrictionResp restriction;

    public void setNegotiatedSku(NegotiatedPricingType negotiatedSku) {
        this.negotiatedSku = negotiatedSku;
    }

    public NegotiatedPricingType getNegotiatedSku() {
        return negotiatedSku;
    }

    public void setImage(PhotoResp image) {
        this.image = image;
    }

    public PhotoResp getImage() {
        return image;
    }


    public static class Restriction {


        @JsonProperty("minAge")
        private Integer minAge;
        @JsonProperty("maxAge")
        private Integer maxAge;
        @JsonProperty("idRequired ")
        private Boolean idRequired;
        @JsonProperty("minQuantity ")
        private Integer minQuantity;
        @JsonProperty("maxQuantity ")
        private Integer maxQuantity;
        @JsonProperty("paxCount ")
        private Integer paxCount;
        @JsonProperty("accompaniedBy")
        private List<String> accompaniedBy;

        public Integer getMinAge() {
            return minAge;
        }

        public void setMinAge(Integer minAge) {
            this.minAge = minAge;
        }

        public Integer getMaxAge() {
            return maxAge;
        }

        public void setMaxAge(Integer maxAge) {
            this.maxAge = maxAge;
        }

        public Boolean getIdRequired() {
            return idRequired;
        }

        public void setIdRequired(Boolean idRequired) {
            this.idRequired = idRequired;
        }

        public Integer getMinQuantity() {
            return minQuantity;
        }

        public void setMinQuantity(Integer minQuantity) {
            this.minQuantity = minQuantity;
        }

        public Integer getMaxQuantity() {
            return maxQuantity;
        }

        public void setMaxQuantity(Integer maxQuantity) {
            this.maxQuantity = maxQuantity;
        }

        public Integer getPaxCount() {
            return paxCount;
        }

        public void setPaxCount(Integer paxCount) {
            this.paxCount = paxCount;
        }

        public List<String> getAccompaniedBy() {
            return accompaniedBy;
        }

        public void setAccompaniedBy(List<String> accompaniedBy) {
            this.accompaniedBy = accompaniedBy;
        }
    }
}
