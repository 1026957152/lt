package com.lt.dom.otcenum;

public enum EnumPassexpiry {
    NEVER("The pass actually never expires. Simple as that!"),
    FIXED_DATE("The pass is valid until a fixed date as specified in the activity rate fixedPassExpiryDate."),
    RELATIVE_DATE("The pass is valid for X days after purchase, as specified in the activity rate passValidForDays ."),

    ;





    EnumPassexpiry(String barcode) {

    }
}
