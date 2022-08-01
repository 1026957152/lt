package com.lt.dom.otcenum;

public enum EnumValidationRuleType {
    product__discount_applicable("product.discount_applicable"),
    product__id("product.id"),
    order__amount("order.amount"),




    redemption__metadata("redemption.metadata"),

    customer__segment("customer.segment"),
    redemption__count__per_customer("redemption.count.per_customer"),

    order__items__price_each("order.items.price_each"),


    ;


    EnumValidationRuleType(String barcode) {

    }
}
