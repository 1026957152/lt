package com.lt.dom.otcenum;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

import java.util.Arrays;
import java.util.Locale;
import java.util.ResourceBundle;

public enum EnumRole {
/*
    ROLE_ADMIN("barcode"),
    ROLE_USER("reference number"),
    ROLE_NOT_REAL_NAME("Barcode scan"),
    ROLE_VOUCHER_APPROVAL("Facial Recognition"),
    ROLE_VOUCHER_PROCESSING("NFC tap"),

    ROLE_AP_MANAGER("barcode"),
    ROLE_AP_SPECIALIST("reference number"),
    ROLE_VOUCHER_ENTRY("Barcode scan"),
*/

    ROLE_ADMIN("barcode"),
    ROLE_GOVERNMENT("barcode"),
    ROLE_MERCHANT("barcode"),
    ROLE_TRAVEL_AGENCY("barcode"),
    ROLE_VOUCHER_REDEEMER(" Voucher Redeemer"),

    ROLE_HEAD_OFFICE(" Voucher Redeemer"),
    ROLE_BRANCH(""),
    ROLE_BANK_STAFF(""),







    //https://ordercloud.io/api-reference/authentication-and-authorization/security-profiles/list

    FullAccess(""),
    SupplierAdmin(""),
    SupplierReader(""),
    BuyerAdmin(""),
    SecurityProfileAdmin(""),
    SetSecurityProfile(""),
            SecurityProfileReader(""),




    ;


    private static ResourceBundle resourceBundle = ResourceBundle.getBundle("messages",
            Locale.getDefault());

    public String name;

    @Autowired
    private MessageSource messageSource;

    EnumRole(String name) {
        this.name = name;
    }

    @Override
    public String toString() {

        String displayStatusString = resourceBundle.getString("page.systemadministration.role."
                + this.name());
        return displayStatusString;
    }
}
