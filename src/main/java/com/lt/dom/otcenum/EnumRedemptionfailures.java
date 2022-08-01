package com.lt.dom.otcenum;

public enum EnumRedemptionfailures {

    resource_not_found("voucher with given code does not exist"),
    voucher_not_active(" - voucher is not active yet (before start date)"),
    voucher_expired(" - voucher has already expired (after expiration date)"),
    voucher_disabled(" - voucher has been disabled (active: false)"),
    quantity_exceeded(" - voucher's redemptions limit has been exceeded"),
    gift_amount_exceeded(" - gift amount has been exceeded"),
    customer_rules_violated(" - customer did not match to the segment"),
    order_rules_violated(" - order did not match validation rules"),
    invalid_order(" - order was specified incorrectly"),
    invalid_amount(" - order amount was specified incorrectly"),
    missing_amount(" - order amount was not specified"),
    missing_order_items(" - order items was not specified"),
    missing_customer(" - customer was not specified"),
    ;


    EnumRedemptionfailures(String barcode) {

    }
}
