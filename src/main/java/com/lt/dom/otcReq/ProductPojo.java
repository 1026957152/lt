package com.lt.dom.otcReq;

import com.lt.dom.OctResp.AttributeResp;
import com.lt.dom.otcenum.*;
import com.lt.dom.otcReq.product.ProductGiftVoucherPojo;
import com.lt.dom.vo.ByHour;
import com.lt.dom.vo.ByItem;
import com.lt.dom.vo.ByPerson;
import com.lt.dom.vo.Fixed;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.hibernate.validator.constraints.Length;

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

    private EnumAvailabilityType availability_type = EnumAvailabilityType.OPENING_HOURS;
    @NotNull
    private EnumProductType type ;//Museums, Attractions or Hop on Hop off tours
    @NotEmpty
    @Size(max = 100)
    private String name_long;

    @Size(max = 100)
    private String long_desc;

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

    private Boolean is_refund;
    private String note;
    private EnumValidateWay validate_way = EnumValidateWay.none;

    public EnumValidateWay getValidate_way() {
        return validate_way;
    }

    public void setValidate_way(EnumValidateWay validate_way) {
        this.validate_way = validate_way;
    }

    @Valid
    @Size(min=1)
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


    public static class Price {

        @NotNull
        private EnumProductPricingType type;
        @NotNull
        private EnumProductPricingTypeByPerson by;
        private ByHour byHour;
        private ByItem byItem;
        private ByPerson byPerson;
        private Fixed fixed;




        @Size(min = 0,max = 30)
        List<Long> rights;

        public List<Long> getRights() {
            return rights;
        }

        public void setRights(List<Long> rights) {
            this.rights = rights;
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






    @Valid
    public Pass pass;

    public Pass getPass() {
        return pass;
    }

    public void setPass(Pass pass) {
        this.pass = pass;
    }

    public static class Pass {
        @Size(min = 1,max = 30)
        private List<Royalty> royalties;
        private Iterable<Long> rights;

        public List<Royalty> getRoyalties() {
            return royalties;
        }



        public void setRights(Iterable<Long> rights) {
            this.rights = rights;
        }
    }


    public static class Royalty {

        @NotNull
        private Long component_right;
        private int amount;
        private int percent;

        @NotNull
        private EnumValidateWay validate_way = EnumValidateWay.none;

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
