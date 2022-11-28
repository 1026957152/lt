package com.lt.dom.otcReq;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.lt.dom.OctResp.AttributeResp;
import com.lt.dom.OctResp.RestrictionResp;
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

public class AvailabilityTabReq {


    private EnumAvailabilityType availability_type = EnumAvailabilityType.OPENING_HOURS;

    public EnumAvailabilityType getAvailability_type() {
        return availability_type;
    }

    public void setAvailability_type(EnumAvailabilityType availability_type) {
        this.availability_type = availability_type;
    }

    @Valid
    private List<BookingRulePojoFuck> booking_rules;

    public List<BookingRulePojoFuck> getBooking_rules() {
        return booking_rules;
    }

    public void setBooking_rules(List<BookingRulePojoFuck> booking_rules) {
        this.booking_rules = booking_rules;
    }

    private Boolean availabilityRequired;

    public Boolean getAvailabilityRequired() {
        return availabilityRequired;
    }

    public void setAvailabilityRequired(Boolean availabilityRequired) {
        this.availabilityRequired = availabilityRequired;
    }

}
