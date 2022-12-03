package com.lt.dom.otcReq;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.lt.dom.OctResp.AttributeResp;
import com.lt.dom.OctResp.RestrictionResp;
import com.lt.dom.oct.PricingRate;
import com.lt.dom.otcReq.product.ProductGiftVoucherPojo;
import com.lt.dom.otcenum.*;
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
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductPojo {

    public RestrictionResp getRestriction() {
        return restriction;
    }

    public void setRestriction(RestrictionResp restriction) {
        this.restriction = restriction;
    }


    @Valid
    private RestrictionResp restriction;
    private Boolean free = false;


    @JsonProperty("package")
    private Boolean package_ = false;


    @NotEmpty
    @Size(max = 50)

    private String name;
    private String source_id;

    private EnumAvailabilityType availability_type = EnumAvailabilityType.OPENING_HOURS;
    @NotNull
    private EnumProductType type ;//Museums, Attractions or Hop on Hop off tours
    @NotEmpty
    @Size(max = 100)
    private String name_long;

    @Size(max = 2000)
    private String long_desc;
    @Size(max = 100)
    private String desc_short;

    public String getDesc_short() {
        return desc_short;
    }

    public void setDesc_short(String desc_short) {
        this.desc_short = desc_short;
    }


    @Valid
    @NotNull
    private List<EnumTags> tags;

    @NotNull
    @JsonProperty("privacyLevel")
    private EnumPrivacyLevel privacyLevel;

    public EnumAvailabilityType getAvailability_type() {
        return availability_type;
    }

    public void setAvailability_type(EnumAvailabilityType availability_type) {
        this.availability_type = availability_type;
    }

    @Valid
    private List<BookingRulePojoFuck> booking_rules;
    private Integer shipping_rate;

    public List<BookingRulePojoFuck> getBooking_rules() {
        return booking_rules;
    }

    public void setBooking_rules(List<BookingRulePojoFuck> booking_rules) {
        this.booking_rules = booking_rules;
    }

    public Boolean getPackage_() {
        return package_;
    }

    public void setPackage_(Boolean package_) {
        this.package_ = package_;
    }

    public Boolean getFree() {
        return free;
    }

    public void setFree(Boolean free) {
        this.free = free;
    }

    private Boolean is_refund;
    private String note;
    private Boolean availabilityRequired;

    public Boolean getAvailabilityRequired() {
        return availabilityRequired;
    }

    public void setAvailabilityRequired(Boolean availabilityRequired) {
        this.availabilityRequired = availabilityRequired;
    }

    private EnumValidateWay validate_way = EnumValidateWay.none;

    public EnumValidateWay getValidate_way() {
        return validate_way;
    }

    public void setValidate_way(EnumValidateWay validate_way) {
        this.validate_way = validate_way;
    }

    @Valid
   // @Size(min=1)
    private List<Royalty> royalties;

    public List<Royalty> getRoyalties() {
        return royalties;
    }

    public void setRoyalties(List<Royalty> royalties) {
        this.royalties = royalties;
    }


    public String getLong_desc() {
        return long_desc;
    }

    public void setLong_desc(String long_desc) {
        this.long_desc = long_desc;
    }

    public EnumProductType getType() {
        return type;
    }

    public void setType(EnumProductType type) {
        this.type = type;
    }

    private ProductGiftVoucherPojo pojo;

    @Valid
    private List<Price> prices;
    @Valid
    private Price price;

    public Price getPrice() {
        return price;
    }

    public void setPrice(Price price) {
        this.price = price;
    }

    private List<ImageReq> images;

    private List<EnumPayChannel> payment_methods;

    public List<EnumPayChannel> getPayment_methods() {
        return payment_methods;
    }

    public void setPayment_methods(List<EnumPayChannel> payment_methods) {
        this.payment_methods = payment_methods;
    }

    @Size(min = 0,max = 30)
    List<Long> rights;

    public List<Long> getRights() {
        return rights;
    }

    public void setRights(List<Long> rights) {
        this.rights = rights;
    }

    public List<ImageReq> getImages() {
        return images;
    }

    public void setImages(List<ImageReq> images) {
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

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Integer getShipping_rate() {
        return shipping_rate;
    }

    public void setShipping_rate(Integer shipping_rate) {
        this.shipping_rate = shipping_rate;
    }

    public List<EnumTags> getTags() {
        return tags;
    }

    public void setTags(List<EnumTags> tags) {
        this.tags = tags;
    }

    public EnumPrivacyLevel getPrivacyLevel() {
        return privacyLevel;
    }

    public void setPrivacyLevel(EnumPrivacyLevel privacyLevel) {
        this.privacyLevel = privacyLevel;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Price {

        private Long id;
        @JsonInclude(JsonInclude.Include.NON_NULL)
        public static Price from(PricingRate e) {

            Price price1 = new Price();
            price1.setName(e.getNick_name());
            price1.setByPersonType(e.getBy());
            price1.setType(e.getType());
            price1.setActive(e.getActive());
            price1.setId(e.getId());
            ByPerson  byPerson1 = new ByPerson();
            byPerson1.setPrice(e.getPrice());
            byPerson1.setRetail(e.getRetail());
            byPerson1.setOriginal(e.getOriginal());
            byPerson1.setNet(e.getNet());

            byPerson1.setGroupType(e.getGroup_type());
            price1.setByPerson(byPerson1);



            Restriction restriction1 = new Restriction();
            restriction1.setIdRequired(e.getRestriction_IdRequired());
            restriction1.setMinAge(e.getRestriction_MinAge());
            restriction1.setMaxAge(e.getRestriction_MaxAge());
            restriction1.setMaxQuantity(e.getRestriction_MaxQuantity());
            restriction1.setMinQuantity(e.getRestriction_MinQuantity());

            restriction1.setPaxCount(e.getPaxCount());
          //  restriction1.setAccompaniedBy(e.getAccompaniedBy());

            price1.setRestriction(restriction1);


            return price1;
        }

        public Restriction getRestriction() {
            return restriction;
        }

        public void setRestriction(Restriction restriction) {
            this.restriction = restriction;
        }

        private Restriction restriction;

        public void setId(Long id) {
            this.id = id;
        }

        public Long getId() {
            return id;
        }

        public void setActive(Boolean active) {
            this.active = active;
        }

        public Boolean getActive() {
            return active;
        }


        public static class Restriction {


            @JsonProperty("minAge")
            private Integer minAge;
            @JsonProperty("maxAge")
            private Integer maxAge;
            @JsonProperty("idRequired")
            private Boolean idRequired;
            @JsonProperty("minQuantity")
            private Integer minQuantity;
            @JsonProperty("maxQuantity")
            private Integer maxQuantity;
            @JsonProperty("paxCount")
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
            private String name;
        @NotNull
        private EnumProductPricingType type;
        @NotNull
        private EnumProductPricingTypeByPerson byPersonType;
        private ByHour byHour;
        private ByItem byItem;
        private ByPerson byPerson;
        private Fixed fixed;

        private Boolean active = true;


        private EnumTaxbehavior tax_behavior ;



        @Size(min = 0,max = 30)
        List<Long> rights;

        public List<Long> getRights() {
            return rights;
        }

        public void setRights(List<Long> rights) {
            this.rights = rights;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

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

        public EnumProductPricingTypeByPerson getByPersonType() {
            return byPersonType;
        }

        public void setByPersonType(EnumProductPricingTypeByPerson byPersonType) {
            this.byPersonType = byPersonType;
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



/*

    @Size(max = 30)
    List<Long> campaigns;
    public List<Long> getCampaigns() {
        return campaigns;
    }

    public void setCampaigns(List<Long> campaigns) {
        this.campaigns = campaigns;
    }

*/


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
        @Size(min = 1,max = 30)
        List<Long> campaigns;
        public List<Long> getCampaigns() {
            return campaigns;
        }

        public void setCampaigns(List<Long> campaigns) {
            this.campaigns = campaigns;
        }

        EnumDaytourDays days;
        private List<String> hotels;

        public EnumDaytourDays getDays() {
            return days;
        }

        public void setDays(EnumDaytourDays days) {
            this.days = days;
        }

        public List<String> getHotels() {
            return hotels;
        }

        public void setHotels(List<String> hotels) {
            this.hotels = hotels;
        }

        /*        private String guide_id;//	ID number for the lead customer

        @NotNull
        private Long guide;//*/
/*
        public Long getGuide() {
            return guide;
        }

        public void setGuide(Long guide) {
            this.guide = guide;
        }*/

        private String title;//
        private String starts_at;//
        private String ends_at;//
        @NotNull
        private String line_info;//

/*        public String getGuide_id() {
            return guide_id;
        }

        public void setGuide_id(String guide_id) {
            this.guide_id = guide_id;
        }*/

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

    @Valid
    public Refund refund;

    public Boolean getIs_refund() {
        return is_refund;
    }

    public void setIs_refund(Boolean is_refund) {
        this.is_refund = is_refund;
    }

    public Refund getRefund() {
        return refund;
    }

    public void setRefund(Refund refund) {
        this.refund = refund;
    }

    public static class Refund {

        //@NotEmpty
        private String note;

        public String getNote() {
            return note;
        }

        public void setNote(String note) {
            this.note = note;
        }
    }






    public static class Royalty {


        private Long component_right;
        private int amount;
        private int percent;

        private EnumRatePlaneCommissionType ratePlaneCommissionType;


        @NotNull
        private EnumValidateWay validate_way = EnumValidateWay.none;
        private EnumRoyaltyCollection_method collection_method;
        private Long id;
        private Long limit_quantity;

        public EnumValidateWay getValidate_way() {
            return validate_way;
        }

        public void setValidate_way(EnumValidateWay validate_way) {
            this.validate_way = validate_way;
        }

        public Long getComponent_right() {
            return component_right;
        }

        public void setComponent_right(long component_right) {
            this.component_right = component_right;
        }

        public int getPercent() {
            return percent;
        }

        public void setPercent(int percent) {
            this.percent = percent;
        }

        public EnumRoyaltyCollection_method getCollection_method() {
            return collection_method;
        }

        public void setCollection_method(EnumRoyaltyCollection_method collection_method) {
            this.collection_method = collection_method;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public Long getId() {
            return id;
        }

        public void setLimit_quantity(Long limit_quantity) {
            this.limit_quantity = limit_quantity;
        }

        public Long getLimit_quantity() {
            return limit_quantity;
        }
    }







    @Valid
    public Attraction attraction;

    public Attraction getAttraction() {
        return attraction;
    }

    public void setAttraction(Attraction attraction) {
        this.attraction = attraction;
    }

    public static class Attraction {


        private Long id;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }
    }





    public Boolean shippable = false;

    public List<String> shipping_rates;

    public Boolean getShippable() {
        return shippable;
    }

    public void setShippable(Boolean shippable) {
        this.shippable = shippable;
    }

    public List<String> getShipping_rates() {
        return shipping_rates;
    }

    public void setShipping_rates(List<String> shipping_rates) {
        this.shipping_rates = shipping_rates;
    }
}
