package com.lt.dom.oct;

import com.lt.dom.otcenum.EnumAssetType;
import com.lt.dom.otcenum.EnumShippingRateType;
import com.lt.dom.otcenum.EnumTaxbehavior;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
public class ShippingRate {

    @Version
    private Integer version;

    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Id
    private long id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }



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


    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
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
}
