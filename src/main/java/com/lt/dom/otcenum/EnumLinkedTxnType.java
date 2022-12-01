package com.lt.dom.otcenum;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

import java.util.Locale;
import java.util.ResourceBundle;

public enum EnumLinkedTxnType {//交易类型


    PurchaseOrder("充值"),
    BillPaymentCheck("充值退款"),
    ReimburseCharge("充值退款失败"),


    ;


    EnumLinkedTxnType(String 入账退款) {

    }

    @Autowired
    private MessageSource messageSource;


    private static ResourceBundle resourceBundle = ResourceBundle.getBundle("messages",
            Locale.getDefault());
    @Override
    public String toString() {

        String displayStatusString = resourceBundle.getString("page.systemadministration.payment.tran.type."
                + this.name());
        return displayStatusString;
    }
}
