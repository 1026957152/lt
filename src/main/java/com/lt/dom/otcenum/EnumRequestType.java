package com.lt.dom.otcenum;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

import java.util.Locale;
import java.util.ResourceBundle;

public enum EnumRequestType {
    //SiteRequest("barcode"),


    Merchants_settled("Merchants_settled"),

    Apply_voucher_for_payment("Apply_voucher_for_payment"),
    approved_for_payment("approved_for_payment"),

    voucher_for_batches("voucher_for_batches"),

    Post_Vouchers("Post_Vouchers"),
    Review_and_approve_batches("voucher_for_batches"),

    tour_approve("voucher_for_batches"),

    ;


    private static ResourceBundle resourceBundle = ResourceBundle.getBundle("messages",
            Locale.getDefault());

    public String name;

    @Autowired
    private MessageSource messageSource;

    EnumRequestType(String name) {
        this.name = name;
    }

    @Override
    public String toString() {

        String displayStatusString = resourceBundle.getString("page.systemadministration.request.type."
                + this.name());
        return displayStatusString;
    }



}
