package com.lt.dom.otcenum;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

import java.util.Locale;
import java.util.ResourceBundle;

public enum EnumRefundType {
    Electronic("barcode"),
    External("succeeded"),



   // PENDING, COMPLETED, REJECTED, or FAILED.

//    pending, succeeded, or failed. For other types of refunds, it can be pending, requires_action, succeeded, failed, or canceled

    ;



    private static ResourceBundle resourceBundle = ResourceBundle.getBundle("messages",
            Locale.getDefault());

    public String name;

    @Autowired
    private MessageSource messageSource;

    EnumRefundType(String name) {
        this.name = name;
    }

    @Override
    public String toString() {

        String displayStatusString = resourceBundle.getString("page.systemadministration.payment.refund.status."
                + this.name());
        return displayStatusString;
    }

}