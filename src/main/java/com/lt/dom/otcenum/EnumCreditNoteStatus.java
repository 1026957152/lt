package com.lt.dom.otcenum;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

import java.util.Locale;
import java.util.ResourceBundle;

public enum EnumCreditNoteStatus {


    issued("issued"), //A bill - commonly known as an Accounts Payable or supplier invoice
    void_("void_"), //A sales invoice - commonly known as an Accounts Receivable or customer invoice



    ;


            ;

    EnumCreditNoteStatus(String scenario, String 应用场景) {

    }


    private static ResourceBundle resourceBundle = ResourceBundle.getBundle("messages",
            Locale.getDefault());

    public String name;

    @Autowired
    private MessageSource messageSource;

    EnumCreditNoteStatus(String name) {
        this.name = name;
    }

/*    @Override
    public String toString() {

        String displayStatusString = resourceBundle.getString("page.systemadministration.broadcastmail.status."
                + this.name);
        return displayStatusString;
    }*/


}
