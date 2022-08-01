package com.lt.dom.otcenum;

public enum Enumfailures {

    already_rolled_back("redemption was rolled back before the current operation start time"),
    customer_rules_violated("customer did not match to the segment"),
    duplicate_resource_key("resource identifier is in use"),
    gift_amount_exceeded("gift amount has been exceeded"),
    invalid_add_balance_params("balance object was specified incorrectly"),
    invalid_amount("order amount was specified incorrectly"),
    invalid_campaign_params("campaign object was specified incorrectly"),
    invalid_code_config("voucher code configuration object was specified incorrectly"),
    invalid_customer("customer object was specified incorrectly"),
    invalid_export_params("export object was specified incorrectly"),
    invalid_gift("gift object was specified incorrectly"),
    invalid_order("order object was specified incorrectly"),
    invalid_payload("a request body is invalid and cannot be processed"),
    invalid_product("product object was specified incorrectly"),
    invalid_publish_params("publication object was specified incorrectly"),
    invalid_query_params("input request query parameters are invalid"),
    invalid_rollback_params("redemption rollback object was specified incorrectly"),
    invalid_sku("SKU object was specified incorrectly"),
    invalid_validation_rules("validation rules object was specified incorrectly"),
    invalid_voucher("voucher object was specified incorrectly (e.g.,聽gift聽or聽discount聽is missing)"),
    loyalty_card_points_exceeded("loyalty card points were exceeded"),
    missing_amount("order amount was not specified"),
    missing_customer("customer was not specified"),
    missing_order("order is missing"),
    missing_order_items("order items were not specified"),
    missing_order_items_amount("missing order item's amount(s)"),
    missing_reward("reward is missing"),
    multiple_requests("multiple requests detected; i.e. when a redemption is in progress and a session lock is in place, then an attempt to validate the voucher will result in this error because it is unknown whether the voucher can or cannot be used"),
    no_voucher_suitable_for_publication("lack of vouchers suitable for publication"),
    not_found("resource with given ID/code does not exist"),
    order_rules_violated("order did not match validation rules"),
    promotion_inactive("promotion is inactive"),
    promotion_not_active_now("promotion is not active in the given timeframe"),
    quantity_exceeded("voucher's redemptions limit has been exceeded"),
    referrer_not_permitted_to_redeem("the referral code is being redeemed by the referrer"),
    resource_not_found("voucher with given code does not exist"),
    voucher_disabled("voucher has been disabled (active: false)"),
    voucher_expired("voucher has already expired (after expiration date)"),
    voucher_not_active("voucher is not active yet (before start date)"),
    voucher_not_active_now("voucher is not active in the given timeframe"),



    booking_not_pending("voucher is not active yet (before start date)"),
    missing_documents("documents was not specified"),

    ;


    Enumfailures(String barcode) {

    }
}
