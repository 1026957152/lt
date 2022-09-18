package com.lt.dom.otcenum;

public enum EnumDestination_type {
    Continent("barcode"),
    Country("reference number"),
    State("Barcode scan"),
    Region("Facial Recognition"),
    City("NFC tap");




    EnumDestination_type(String barcode) {

    }
}
