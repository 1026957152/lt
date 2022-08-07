package com.lt.dom.otcenum;

public enum EnumPublicationObjectType {

    customer("为个人用户"),
    business("为企业用户"),
    traveler("旅行社游客"),


    ;


    EnumPublicationObjectType(String barcode) {

    }

    EnumPublicationObjectType(String scenario, String 应用场景) {

    }
}
