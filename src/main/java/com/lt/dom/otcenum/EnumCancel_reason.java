package com.lt.dom.otcenum;

public enum EnumCancel_reason {
    barcode("barcode"),
    reference_number("reference number"),
    Barcodescan("Barcode scan"),
    FacialRecognition("Facial Recognition"),
    NFCtap("NFC tap"),


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


    EnumCancel_reason(String barcode) {

    }
}
