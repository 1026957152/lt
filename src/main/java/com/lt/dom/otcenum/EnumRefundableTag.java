package com.lt.dom.otcenum;

public enum EnumRefundableTag {
    RFN("  refundable"),
    NRFN("non-refundable"),
    NS("not stated"),
    PRFN("partially refundable")
  //  NFCtap("NFC tap"),

    ;

    EnumRefundableTag(String barcode) {

    }
}
