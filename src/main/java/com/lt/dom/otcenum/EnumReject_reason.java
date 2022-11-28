package com.lt.dom.otcenum;

public enum EnumReject_reason {
    BOOKABLE_ITEM_IS_NO_LONGER_AVAILABLE("this bookable item is no longer available"),
    WEATHER("this booking was rejected on account of the weather"),
    DUPLICATE_BOOKING("this is a duplicate booking"),

    PRODUCT_NO_LONGER_OPERATING("the product is no longer operating"),


    MINIMUM_NUMBER_OF_PASSENGERS_NOT_MET("there are too few passengers for the product to operate"),
    SIGNIFICANT_GLOBAL_EVENT_FORCE_MAJEURE("the booking was rejected due to a natural disaster or other large-scale catastrophe"),
    TOUR_WAS_CANCELLED("the product was cancelled"),
    ISSUE_WITH_TICKET("there was an issue with the ticket"),

    ISSUE_WITH_PICKUP("there was an issue with pickup"),
    OTHER("other/unlisted reason"),


    ;

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


    EnumReject_reason(String barcode) {

    }
}
