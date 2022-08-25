package com.lt.dom.otcenum;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

import java.util.Locale;
import java.util.ResourceBundle;

public enum EnumSettings {
    default_product_tax_code("barcode",EnumValueType.string_),
    default_shipping_tax_code("reference number",EnumValueType.string_),

    ;

    String barcode;
    EnumValueType type;

    public EnumValueType getType() {
        return type;
    }

    public void setType(EnumValueType type) {
        this.type = type;
    }

    EnumSettings(String barcode, EnumValueType type) {

        this.barcode = barcode;
        this.type = type;
    }

    @Autowired
    private MessageSource messageSource;


    private static ResourceBundle resourceBundle = ResourceBundle.getBundle("messages",
            Locale.getDefault());
    @Override
    public String toString() {

        String displayStatusString = resourceBundle.getString("page.systemadministration.settings."
                + this.name());
        return displayStatusString;
    }

}
