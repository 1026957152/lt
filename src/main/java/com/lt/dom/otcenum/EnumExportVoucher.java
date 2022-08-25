package com.lt.dom.otcenum;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

import java.util.Locale;
import java.util.ResourceBundle;

public enum EnumExportVoucher {
    voucher("barcode"),
    redemption("reference number"),
    publication("Barcode scan"),
    customer("Facial Recognition"),
    points_expiration("NFC tap"),


    ;


    private static ResourceBundle resourceBundle = ResourceBundle.getBundle("messages",
            Locale.getDefault());

    public String name;

    @Autowired
    private MessageSource messageSource;

    EnumExportVoucher(String name) {
        this.name = name;
    }

    @Override
    public String toString() {

        String displayStatusString = resourceBundle.getString("page.systemadministration.export.type."
                + this.name());
        return displayStatusString;
    }


}
