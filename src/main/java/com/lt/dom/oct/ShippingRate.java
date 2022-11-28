package com.lt.dom.oct;

import com.lt.dom.OctResp.EnumLongIdResp;
import com.lt.dom.otcenum.EnumAssetType;
import com.lt.dom.otcenum.EnumShippingRateType;
import com.lt.dom.otcenum.EnumTaxbehavior;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

@Entity
public class ShippingRate extends Base{




  //  type	The type of calculation to used on the shipping rate. Can only be fixed_amount for now.
       //     fixed_amount[amount]	How much to charge, in cents. Set it to 0 if you want to offer free shipping.
   // fixed_amount[currency]	Which currency to charge in (for example, usd, eur).
   // display_name	The name of the shipping rate, to display to the customer.
  //  delivery_estimate	An object describing the estimated delivery time for this shipping rate option.
    @NotNull
    private EnumShippingRateType type;

    private String display_name;
    private String code;

    public String getDisplay_name() {
        return display_name;
    }

    public void setDisplay_name(String display_name) {
        this.display_name = display_name;
    }

    @NotNull
    private String fixed_amount;
    private String delivery_estimate;

    private Boolean active;

    private EnumTaxbehavior taxbehavior;


    private String tax_code;

    private Long supplier;

    public EnumTaxbehavior getTaxbehavior() {
        return taxbehavior;
    }

    public void setTaxbehavior(EnumTaxbehavior taxbehavior) {
        this.taxbehavior = taxbehavior;
    }

    public String getTax_code() {
        return tax_code;
    }

    public void setTax_code(String tax_code) {
        this.tax_code = tax_code;
    }

    public static List List(List<ShippingRate> componentRightMap) {
        return componentRightMap.stream().map(x->{

            EnumLongIdResp enumResp = new EnumLongIdResp();
            enumResp.setId(x.getId());

            enumResp.setText(x.getDisplay_name());
            return enumResp;
        }).collect(Collectors.toList());
    }
    public Long getSupplier() {
        return supplier;
    }

    public void setSupplier(Long supplier) {
        this.supplier = supplier;
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

    public void setCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
