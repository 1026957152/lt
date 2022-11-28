package com.lt.dom.otcReq;

import com.lt.dom.OctResp.AttributeResp;
import com.lt.dom.otcenum.EnumAvailabilityType;
import com.lt.dom.otcenum.EnumPayChannel;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import javax.validation.Valid;
import javax.validation.constraints.Size;
import java.util.List;

public class ProductEditPaymentPojo {

    private List<EnumPayChannel> payment_methods;

    public List<EnumPayChannel> getPayment_methods() {
        return payment_methods;
    }

    public void setPayment_methods(List<EnumPayChannel> payment_methods) {
        this.payment_methods = payment_methods;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }

}
