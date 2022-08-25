package com.lt.dom.otcenum;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

import java.util.Locale;
import java.util.ResourceBundle;

public enum EnumPaymentOption {

    //A list of potential payment options for the current order. Some order items will negate the ability to use certain forms of payment, such as purchasing a giftcard will remove the ability to pay with one:
    creditCard(""),
            giftCard(""),
/*    loyalty(""),
            payPal(""),
    venmo(""),
            applePay(""),
    googlePay(""),
            bitPay("");*/
alipay(""),
    wechat_pay("");
    private static ResourceBundle resourceBundle = ResourceBundle.getBundle("messages",
            Locale.getDefault());

    public String name;

    @Autowired
    private MessageSource messageSource;

    EnumPaymentOption(String name) {
        this.name = name;
    }

    @Override
    public String toString() {

        String displayStatusString = resourceBundle.getString("page.systemadministration.payment.option."
                + this.name());
        return displayStatusString;
    }


    }
