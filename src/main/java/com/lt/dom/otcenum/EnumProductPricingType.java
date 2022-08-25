package com.lt.dom.otcenum;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

import java.util.Locale;
import java.util.ResourceBundle;

public enum EnumProductPricingType {
    ByPerson("By Person"),
    ByItem("By Item"),
    Fixed("Fixed"),
    ByDay("By Day"),
    ByHour("By Hour"),
    ByMinute("By Minute"),
    ;






    private static ResourceBundle resourceBundle = ResourceBundle.getBundle("messages",
            Locale.getDefault());

    public String name;

    @Autowired
    private MessageSource messageSource;

    EnumProductPricingType(String name) {
        this.name = name;
    }

    @Override
    public String toString() {

        String displayStatusString = resourceBundle.getString("page.systemadministration.product.price.type."
                + this.name());
        return displayStatusString;
    }

}
