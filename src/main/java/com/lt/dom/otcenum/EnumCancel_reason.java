package com.lt.dom.otcenum;

import com.lt.dom.OctResp.EnumResp;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum EnumCancel_reason {

    customer("this bookable item is no longer available"),
    inventory("this booking was rejected on account of the weather"),
    fraud("this is a duplicate booking"),

    declined("the product is no longer operating"),


    other("there are too few passengers for the product to operate"),



    CUSTOMER_REQUESTED("Cancellation requested by the buyer."),

            OUT_OF_STOCK("Product is out of stock at fulfillment."),

            INVALID_ADDRESS("Unable to ship to address provided by the buyer."),

            SUSPICIOUS_ORDER("Order is suspicious/possible fraud."),

    CANCEL_REASON_OTHER("Other cancellation reason.")

    ;

    public static List<EnumResp> list() {
        return Arrays.stream(EnumCancel_reason.values()).map(x->{
            EnumResp enumResp = new EnumResp();
            enumResp.setId(x.name());
            //  enumResp.setName(x.name());
            enumResp.setText(x.toString());

            return enumResp;
        }).collect(Collectors.toList());
    }

/*1 - Cancelled by customer request, see notes
2 - Cancelled by customer request, booked another tour
3 - Cancelled by staff, see notes
4 - Cancelled by staff, boook another tour
5 - Cancelled by staff, payment not received
6 - Cancelled by staff, tour not reached min. numbers
7 - Cancelled by staff, booking made in error
8 - Cancelled by staff, capacity exceeded
9 - Cancelled by staff, operational reasons
10 - Cancelled by staff, booking reopened for editing
11 - Cancelled by staff, bad weather
20 - Cancelled by system, expired quotation
21 - Cancelled by system, expired provisional booking
22 - Cancelled by customer (via website)
23 - Cancelled by agent (via website)*/


    EnumCancel_reason(String barcode) {

    }
}
