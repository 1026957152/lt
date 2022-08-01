package com.lt.dom.otcenum;

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


    EnumProductPricingTypeByPerson(String s) {
        
    }
}
