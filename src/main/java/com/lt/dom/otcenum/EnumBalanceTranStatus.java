package com.lt.dom.otcenum;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

import java.util.Locale;
import java.util.ResourceBundle;

public enum EnumBalanceTranStatus {//交易类型

    available(""),

    pending(""),

    ;


    EnumBalanceTranStatus(String 入账退款) {

    }

    @Autowired
    private MessageSource messageSource;


    private static ResourceBundle resourceBundle = ResourceBundle.getBundle("messages",
            Locale.getDefault());
    @Override
    public String toString() {

        String displayStatusString = resourceBundle.getString("page.systemadministration.payment.tran.status."
                + this.name());
        return displayStatusString;
    }

}
