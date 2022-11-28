package com.lt.dom.otcenum;

import com.lt.dom.OctResp.EnumResp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
//https://developer.ebay.com/api-docs/sell/fulfillment/types/sel:FulfillmentInstructionsType
public enum EnumFulfillmentInstructionsType {
    DIGITAL("active"),
    PREPARE_FOR_PICKUP("INACTIVE"),
    SELLER_DEFINED("SUSPENDED"),
    SHIP_TO("archived"),

    FULFILLED_BY_EBAY("TERMINATED"),


    ;



    public static List<EnumResp> from() {
        return Arrays.asList(EnumFulfillmentInstructionsType.values()).stream().map(x->{
            EnumResp enumResp = new EnumResp();
            enumResp.setId(x.name());
            //  enumResp.setName(x.name());
            enumResp.setText(x.toString());

            return enumResp;
        }).collect(Collectors.toList());
    }

    private static ResourceBundle resourceBundle = ResourceBundle.getBundle("messages",
            Locale.getDefault());

    public String name;

    @Autowired
    private MessageSource messageSource;

    EnumFulfillmentInstructionsType(String name) {
        this.name = name;
    }

/*    @Override
    public String toString() {

        String displayStatusString = resourceBundle.getString("page.systemadministration.product.status."
                + this.name());
        return displayStatusString;
    }*/



/*
    DIGITAL
    This enumeration value indicates the order contains one or more digital gift card line items that are sent to the recipient by email.
            PREPARE_FOR_PICKUP
    This enumeration value indicates that the order is an In-Store Pickup order or a Click and Collect order. If this value is returned for an In-Store Pickup order, the seller can look at the pickupStep container to see the specific store where the buyer will pick up the order. If this value is returned for a Click and Collect order, the seller will look at the shippingStep container to see which store the buyer will pick up the item. The seller is then responsible for shipping the order to that store location.
            SELLER_DEFINED
    This enumeration value indicates that the seller will determine how to deliver these line items to the buyer.
    SHIP_TO
    This enumeration value indicates that the seller will package and ship these line items. If this value is returned, the seller can look at the shippingStep container to see the specific shipping details, including the shipping address and the shipping service option that will be used.
    FULFILLED_BY_EBAY
    This enumeration value indicates that eBay will package and ship an order as specified by fulfillmentType.

*/

}
