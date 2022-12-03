package com.lt.dom.oct;


import com.lt.dom.OctResp.EnumLongIdResp;
import com.lt.dom.otcenum.EnumPriceRecurring;
import com.lt.dom.otcenum.EnumProductPricingType;
import com.lt.dom.otcenum.EnumProductPricingTypeByPerson;
import com.lt.dom.otcenum.EnumProductPricingTypeByPersonGroupType;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

@Entity
public class PricingRate extends Base {


    private Long productId;

    private Boolean active;


    @NotNull
    private String nick_name; // per or total


    private EnumProductPricingType type;
    @Column(name = "by_")
    private EnumProductPricingTypeByPerson by;
    private Integer min;
    private Integer max;
    private EnumProductPricingTypeByPersonGroupType group_type; // per or total

    private String unit;

    private Integer price;
    private Integer original; //指导价/建议价 market_price  The original price for this product which will be the same or higher than the sale amount. Use this to show a discount has been applied e.g. $10 $8.50
    private Integer retail; //original_price  门市价/指导价    The sale price you should charge your customers.
    private Integer net;  //The wholesale rate the supplier will charge you for this sale.
    private Integer restriction_MaxAge;
    private Integer restriction_MinAge;
    private Integer restriction_MinQuantity;
    private Integer restriction_MaxQuantity;
    private Boolean restriction_IdRequired;
    private Integer restriction_PaxCount;
    private Integer paxCount;

    public static List List(List<PricingRate> componentRightMap) {
        return componentRightMap.stream().map(x->{

            EnumLongIdResp enumResp = new EnumLongIdResp();
            enumResp.setId(x.getId());

            enumResp.setText(x.getLable()+"_"+x.getNick_name());
            return enumResp;
        }).collect(Collectors.toList());
    }
    // private List<String> accompaniedBy;

    public String getNick_name() {
        return nick_name;
    }

    public void setNick_name(String nick_name) {
        this.nick_name = nick_name;
    }

    private EnumPriceRecurring recurring = EnumPriceRecurring.one_time;




    public Integer getOriginal() {
        return original;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public void setOriginal(Integer original) {
        this.original = original;
    }

    public Integer getRetail() {
        return retail;
    }

    public void setRetail(Integer retail) {
        this.retail = retail;
    }

    public Integer getNet() {
        return net;
    }

    public void setNet(Integer net) {
        this.net = net;
    }

    public Long getMin_booking_size() {
        return min_booking_size;
    }

    public void setMin_booking_size(Long min_booking_size) {
        this.min_booking_size = min_booking_size;
    }

    private String lable;
    private String streamSeq;


    private Long min_booking_size;



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

    public EnumProductPricingTypeByPersonGroupType getGroup_type() {
        return group_type;
    }

    public void setGroup_type(EnumProductPricingTypeByPersonGroupType unit) {
        this.group_type = unit;
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

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setStreamSeq(String streamSeq) {
        this.streamSeq = streamSeq;
    }

    public String getStreamSeq() {
        return streamSeq;
    }

    public void setRestriction_MaxAge(Integer restriction_maxAge) {
        this.restriction_MaxAge = restriction_maxAge;
    }

    public Integer getRestriction_MaxAge() {
        return restriction_MaxAge;
    }

    public void setRestriction_MinAge(Integer restriction_minAge) {
        this.restriction_MinAge = restriction_minAge;
    }

    public Integer getRestriction_MinAge() {
        return restriction_MinAge;
    }

    public void setRestriction_MinQuantity(Integer restriction_minQuantity) {
        this.restriction_MinQuantity = restriction_minQuantity;
    }

    public Integer getRestriction_MinQuantity() {
        return restriction_MinQuantity;
    }

    public void setRestriction_MaxQuantity(Integer restriction_maxQuantity) {
        this.restriction_MaxQuantity = restriction_maxQuantity;
    }

    public Integer getRestriction_MaxQuantity() {
        return restriction_MaxQuantity;
    }

    public void setRestriction_IdRequired(Boolean restriction_idRequired) {
        this.restriction_IdRequired = restriction_idRequired;
    }

    public Boolean getRestriction_IdRequired() {
        return restriction_IdRequired;
    }

    public void setRestriction_PaxCount(Integer restriction_paxCount) {
        this.restriction_PaxCount = restriction_paxCount;
    }

    public Integer getRestriction_PaxCount() {
        return restriction_PaxCount;
    }

    public Integer getPaxCount() {
        return paxCount;
    }

    public void setPaxCount(Integer paxCount) {
        this.paxCount = paxCount;
    }

/*    public List<String> getAccompaniedBy() {

        return accompaniedBy;
    }

    public void setAccompaniedBy(List<String> accompaniedBy) {
        this.accompaniedBy = accompaniedBy;
    }*/
}
