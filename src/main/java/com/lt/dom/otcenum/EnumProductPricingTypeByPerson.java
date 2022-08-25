package com.lt.dom.otcenum;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

import java.util.Locale;
import java.util.ResourceBundle;

public enum EnumProductPricingTypeByPerson {
    UNIQUE_PRICE("Everyone"),
    ADULT("Adult"),
    CHILD("Child"),
    INFANT("Infant"),
    SENIOR("Senior"),
    TEENAGER("Teenager"),
    STUDENT("Student"),
    CONCESSION("Concession"),

    FAMILY_OF_3("Family of 3"),
    FAMILY_OF_4("Family of 4"),
    FAMILY_OF_5("Family of 5"),
    ONE_ADULT_2_CHILDREN("1 Adult + 2 Children"),
    ONE_ADULT_3_CHILDREN("1 Adult + 3 Children"),
    TWO_ADULTS_1_CHILD("2 Adults + 1 Child"),
    TWO_ADULTS_2_CHILDREN("2 Adults + 2 Children"),
    TWO_ADULTS_3_CHILDREN("2 Adults + 3 Children"),

    SINGLE("Single"),
    DOUBLE("Double"),
    TRIPLE("Triple"),
    QUAD("Quad"),

    GROUP("Group"),

    CUSTOM("Custom"),

    ;


    private static ResourceBundle resourceBundle = ResourceBundle.getBundle("messages",
            Locale.getDefault());

    public String name;

    @Autowired
    private MessageSource messageSource;

    EnumProductPricingTypeByPerson(String name) {
        this.name = name;
    }

    @Override
    public String toString() {

        String displayStatusString = resourceBundle.getString("page.systemadministration.product.price.type.byperson.type."
                + this.name());
        return displayStatusString;
    }

}
