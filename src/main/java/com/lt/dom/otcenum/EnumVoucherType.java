package com.lt.dom.otcenum;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

import java.util.Locale;
import java.util.ResourceBundle;

public enum EnumVoucherType {
    TICKET(""),
    VOUCHER(""),

    TICKET_THEATRE(""),
    TICKET_BUS(""),
    Multi_Ticket(""),
    PASS(""),



    GIFT_VOUCHER(""),
    DISCOUNT_VOUCHER(""),
    RIGHT_VOUCHER(""),  // 这里是 直接 销和的 ，不是优惠券销和

    LOYALTY_CARD("");


    private static ResourceBundle resourceBundle = ResourceBundle.getBundle("messages",
            Locale.getDefault());

    public String name;

    @Autowired
    private MessageSource messageSource;

    EnumVoucherType(String name) {
        this.name = name;
    }

    @Override
    public String toString() {

        String displayStatusString = resourceBundle.getString("page.systemadministration.voucher.type."
                + this.name());
        return displayStatusString;
    }

    }
