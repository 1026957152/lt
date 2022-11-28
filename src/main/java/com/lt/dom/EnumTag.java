package com.lt.dom;

public enum EnumTag {
    barcode("barcode",""),
    reference_number("reference number",""),
    Barcodescan("Barcode scan",""),
    FacialRecognition("Facial Recognition",""),
    NFCtap("NFC tap",""),


    ;

    private String key;
    private String name;

    EnumTag(String key,String name) {

    }
}
