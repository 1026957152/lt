package com.lt.dom.otcenum;
//https://developer.bigcommerce.com/api-reference/e9b1d990a9fcb-create-a-product-variant-option
public enum EnumGuideType {
    Guide("barcode"),
    AudioGuide("barcode"),
    Guide_and_AudioGuide("Guide and  Audio Guide"),
    Not_Included("barcode"),



    ;


    EnumGuideType(String barcode) {

    }
}
