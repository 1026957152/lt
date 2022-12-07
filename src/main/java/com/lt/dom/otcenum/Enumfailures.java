package com.lt.dom.otcenum;

public enum Enumfailures {

    already_rolled_back(1,"redemption was rolled back before the current operation start time"),
    customer_rules_violated(1,"customer did not match to the segment"),
    duplicate_resource_key(1,"resource identifier is in use"),
    gift_amount_exceeded(1,"gift amount has been exceeded"),
    invalid_add_balance_params(1,"balance object was specified incorrectly"),
    invalid_amount(1,"order amount was specified incorrectly"),
    invalid_campaign_params(1,"campaign object was specified incorrectly"),
    invalid_code_config(1,"voucher code configuration object was specified incorrectly"),
    invalid_customer(1,"customer object was specified incorrectly"),
    invalid_export_params(1,"export object was specified incorrectly"),
    invalid_gift(1,"gift object was specified incorrectly"),
    invalid_order(1,"order object was specified incorrectly"),
    invalid_payload(1,"a request body is invalid and cannot be processed"),
    invalid_product(1,"product object was specified incorrectly"),
    invalid_publish_params(1,"publication object was specified incorrectly"),
    invalid_query_params(1,"input request query parameters are invalid"),
    invalid_rollback_params(1,"redemption rollback object was specified incorrectly"),
    invalid_sku(1,"SKU object was specified incorrectly"),
    invalid_validation_rules(1,"validation rules object was specified incorrectly"),
    invalid_voucher(1,"voucher object was specified incorrectly (e.g.,聽gift聽or聽discount聽is missing)"),
    loyalty_card_points_exceeded(1,"loyalty card points were exceeded"),
    missing_amount(1,"order amount was not specified"),
    missing_customer(1,"customer was not specified"),
    missing_order(1,"order is missing"),
    missing_order_items(1,"order items were not specified"),
    missing_order_items_amount(1,"missing order item's amount(s)"),
    missing_reward(1,"reward is missing"),
    multiple_requests(1,"multiple requests detected; i.e. when a redemption is in progress and a session lock is in place, then an attempt to validate the voucher will result in this error because it is unknown whether the voucher can or cannot be used"),
    no_voucher_suitable_for_publication(1,"lack of vouchers suitable for publication"),
    not_found(1,"resource with given ID/code does not exist"), //404	The object you’re requesting doesn’t exist.

    

/*    not_found
    user_not_found
            app_not_found
    app_maker_not_found
            integration_not_found
    client_not_found
            menu_not_found
    conversation_not_found
            webhook_not_found
    processor_not_found
            message_not_found
    route_not_found	*/
    
    
    
    order_rules_violated(1,"order did not match validation rules"),
    promotion_inactive(1,"promotion is inactive"),
    promotion_not_active_now(1,"promotion is not active in the given timeframe"),
    quantity_exceeded(1,"voucher's redemptions limit has been exceeded"),
    referrer_not_permitted_to_redeem(1,"the referral code is being redeemed by the referrer"),
    resource_not_found(1,"voucher with given code does not exist"),
    voucher_disabled(1,"voucher has been disabled (active: false)"),
    voucher_expired(1,"voucher has already expired (after expiration date)"),
    voucher_not_active(1,"voucher is not active yet (before start date)"),
    voucher_not_active_now(1,"voucher is not active in the given timeframe"),



    booking_not_pending(1,"voucher is not active yet (before start date)"),
    missing_documents(1,"documents was not specified"),
    user_already_be_guide(1,"已经是导游身份了"),
    user_realname_auth_failure(1,"实名认证失败"),
    voucher_has_no_permistion_redeem(1,"商户没有核销改券的权力"),
    voucher_not_publish(1,"还没有认领"),
    campaign_inactive(1,"campaign_inactive"),




    wx_login_error(1,"签名校验失败"),

    payment_login_error(1,"支付失败"),


    username_already_exists_error(1,"用户名已经存在"),

    general_exists_error(1,"用户名已经存在"),

    user_not_found(1,"user_not_found"),




    SUCCESS(1,"	S	Success"),
  //  The registration is successful, no further action is needed."),

    INVALID_CLIENT(1,"	F	The client is invalid."),
  //  Check whether clientId is correct."),

    MEDIA_TYPE_NOT_ACCEPTABLE(1,"	F	The server does not implement the media type that is acceptable to the client."),
   // Check whether the media type is correct."),

    METHOD_NOT_SUPPORTED(1,"	F	The server does not implement the requested HTTP method."),
   // Check whether the HTTP method is correct."),

    REPEAT_REQ_INCONSISTENT(1,"	F	Repeated requests are inconsistent."),
   // Change registrationRequestId and call the interface again.

    REGISTRATION_UNDER_REVIEW(1,"	F	A previously submitted registration is currently under review. No new registration or modification is allowed."),
   // The submitted registration is currently under review. Please wait for the final result.

    REPEAT_REGISTRATION(1,"	F	Repeated registration for merchant"),
   // Prompt the user not to register repeatedly.


    Missing_credentials(1,"","401.missing_credential"),
    //BadJwtException(1,"用户名已经存在"),


    Invalid_credentials(1,"","401.invalid_credentials"),
    Expired_credentials(1,"","401.expired_credentials"),


    _401_Unauthorized(1,"未登录"),


    NEED_REAL_NAME(40301,"需要实名人认证"),

    bad_request(1,"需要实名人认证"),

    _305(1,"The message has expired."),
    _310(1,"Rate limit reached."),
    _350(1,"Message not allowed."),
    _510(1,"Unsupported message."),
    _512(1,"Unknown message."),
    _520(1,"Invalid message structure."),
    _530(1,"Invalid destination."),
    _500(1,"General error."),










    ;

    // 401	40	Missing credentials: e.g. missing Authorization header, missing token, missing Bearer prefix…	Verify Authorization header is present, with valid Bearer token
//401	41	Invalid credentials: e.g. invalid client_id and/or client_secret, pending or revoked client_id…	Check your app’s credentials on the ‘Application details’ page of our developer portal. You may have renewed your credentials
//401	42	Expired credentials: e.g. expired OAuth 2.0 token

    ;


    private String text;
    private Integer code;
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

    Enumfailures(Integer code,String barcode,String message_code) {

        this.text = barcode;
        this.message_code = message_code;
    }
    Enumfailures(Integer code,String message_code) {

        this.code = code;
        this.message_code = message_code;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
