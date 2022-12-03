package com.lt.dom.otcenum;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

import java.util.Locale;
import java.util.ResourceBundle;

public enum EnumAgentStatus {
    Active("Active"),
    PendingApproval("PendingApproval"),
    Rejected("Rejected"),
    Blocked("Blockedp"),

    ;

    private static ResourceBundle resourceBundle = ResourceBundle.getBundle("messages",
            Locale.getDefault());

    public String name;

    @Autowired
    private MessageSource messageSource;

    EnumAgentStatus(String name) {
        this.name = name;
    }

    @Override
    public String toString() {

        String displayStatusString = resourceBundle.getString("page.systemadministration.supplier.status."
                + this.name());
        return displayStatusString;
    }
}
