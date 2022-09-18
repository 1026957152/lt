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
    user_already_be_guide("已经是导游身份了"),
    user_realname_auth_failure("实名认证失败"),
    voucher_has_no_permistion_redeem("商户没有核销改券的权力"),
    voucher_not_publish("还没有认领"),
    campaign_inactive("campaign_inactive"),




    wx_login_error("签名校验失败"),

    payment_login_error("支付失败"),


    username_already_exists_error("用户名已经存在"),

    general_exists_error("用户名已经存在"),

    user_not_found("user_not_found"),




    SUCCESS("	S	Success"),
  //  The registration is successful, no further action is needed."),

    INVALID_CLIENT("	F	The client is invalid."),
  //  Check whether clientId is correct."),

    MEDIA_TYPE_NOT_ACCEPTABLE("	F	The server does not implement the media type that is acceptable to the client."),
   // Check whether the media type is correct."),

    METHOD_NOT_SUPPORTED("	F	The server does not implement the requested HTTP method."),
   // Check whether the HTTP method is correct."),

    REPEAT_REQ_INCONSISTENT("	F	Repeated requests are inconsistent."),
   // Change registrationRequestId and call the interface again.

    REGISTRATION_UNDER_REVIEW("	F	A previously submitted registration is currently under review. No new registration or modification is allowed."),
   // The submitted registration is currently under review. Please wait for the final result.

    REPEAT_REGISTRATION("	F	Repeated registration for merchant"),
   // Prompt the user not to register repeatedly.


    Missing_credentials("","401.missing_credential"),
    //BadJwtException("用户名已经存在"),


    Invalid_credentials("","401.invalid_credentials"),
    Expired_credentials("","401.expired_credentials"),



    Need_real_name("需要实名人认证"),
    bad_request("需要实名人认证");

    // 401	40	Missing credentials: e.g. missing Authorization header, missing token, missing Bearer prefix…	Verify Authorization header is present, with valid Bearer token
//401	41	Invalid credentials: e.g. invalid client_id and/or client_secret, pending or revoked client_id…	Check your app’s credentials on the ‘Application details’ page of our developer portal. You may have renewed your credentials
//401	42	Expired credentials: e.g. expired OAuth 2.0 token

    ;


    private String text;

    private String message_code;

    public String getMessage_code() {
        return message_code;
    }

    public void setMessage_code(String message_code) {
        this.message_code = message_code;
    }

    Enumfailures(String barcode) {

        this.text = barcode;
    }

    Enumfailures(String barcode,String message_code) {

        this.text = barcode;
        this.message_code = message_code;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
