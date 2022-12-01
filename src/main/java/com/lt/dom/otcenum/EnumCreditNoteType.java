package com.lt.dom.otcenum;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

import java.util.Locale;
import java.util.ResourceBundle;

public enum EnumCreditNoteType {

    pre_payment("ACCPAY"), //A bill - commonly known as an Accounts Payable or supplier invoice
    post_payment("ACCREC"), //A sales invoice - commonly known as an Accounts Receivable or customer invoice


 //   Type of this credit note, one of pre_payment or post_payment. A pre_payment credit note means it was issued when the invoice was open. A post_payment credit note means it was issued when the invoice was paid.

    ;


            ;

    EnumCreditNoteType(String scenario, String 应用场景) {

    }


    private static ResourceBundle resourceBundle = ResourceBundle.getBundle("messages",
            Locale.getDefault());

    public String name;

    @Autowired
    private MessageSource messageSource;

    EnumCreditNoteType(String name) {
        this.name = name;
    }

/*    @Override
    public String toString() {

        String displayStatusString = resourceBundle.getString("page.systemadministration.broadcastmail.status."
                + this.name);
        return displayStatusString;
    }*/


}
