package com.lt.dom.otcReq;

import com.lt.dom.OctResp.BaseResp;
import com.lt.dom.oct.ShippingRate;
import com.lt.dom.otcenum.EnumShippingRateType;
import com.lt.dom.otcenum.EnumTaxbehavior;

import javax.validation.constraints.NotNull;


public class ShippingRateReq extends BaseResp {




  //  type	The type of calculation to used on the shipping rate. Can only be fixed_amount for now.
       //     fixed_amount[amount]	How much to charge, in cents. Set it to 0 if you want to offer free shipping.
   // fixed_amount[currency]	Which currency to charge in (for example, usd, eur).
   // display_name	The name of the shipping rate, to display to the customer.
  //  delivery_estimate	An object describing the estimated delivery time for this shipping rate option.
    @NotNull
    private EnumShippingRateType type;
    @NotNull
    private String fixed_amount;
    private String delivery_estimate;

    private Boolean active;

    private EnumTaxbehavior taxbehavior;


    private String tax_code;
    private String display_name;

    public static ShippingRateReq from(ShippingRate e) {

        ShippingRateReq shippingRateResp = new ShippingRateReq();
        shippingRateResp.setActive(e.getActive());
        shippingRateResp.setType(e.getType());
        shippingRateResp.setFixed_amount(e.getFixed_amount());
        shippingRateResp.setDelivery_estimate(e.getDelivery_estimate());
        return shippingRateResp;
    }


    public EnumShippingRateType getType() {
        return type;
    }

    public void setType(EnumShippingRateType type) {
        this.type = type;
    }

    public String getFixed_amount() {
        return fixed_amount;
    }

    public void setFixed_amount(String fixed_amount) {
        this.fixed_amount = fixed_amount;
    }

    public String getDelivery_estimate() {
        return delivery_estimate;
    }

    public void setDelivery_estimate(String delivery_estimate) {
        this.delivery_estimate = delivery_estimate;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getDisplay_name() {
        return display_name;
    }

    public void setDisplay_name(String display_name) {
        this.display_name = display_name;
    }
}
