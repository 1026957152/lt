package com.lt.dom.otcenum;

import com.lt.dom.OctResp.EnumResp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public enum EnumInvoiceStatus {
    draft("0"),
    not_sent(""),

    Posted("1"),
    Canceled("1024"),

   // Invoice state, one of draft, not_sent, sent, viewed, past_due, pending, paid, voided
    ;


  //  active: The product is ready to sell and is available to customers on the online store, sales channels, and apps. By default, existing products are set to active.
   // archived: The product is no longer being sold and isn't available to customers on sales channels and apps.
   // draft: T

    public static List<EnumResp> from() {
        return Arrays.asList(EnumInvoiceStatus.values()).stream().map(x->{
            EnumResp enumResp = new EnumResp();
            enumResp.setId(x.name());
            //  enumResp.setName(x.name());
            enumResp.setText(x.toString());

            return enumResp;
        }).collect(Collectors.toList());
    }

    private static ResourceBundle resourceBundle = ResourceBundle.getBundle("messages",
            Locale.getDefault());

    public String name;

    @Autowired
    private MessageSource messageSource;

    EnumInvoiceStatus(String name) {
        this.name = name;
    }

    @Override
    public String toString() {

        String displayStatusString = resourceBundle.getString("page.systemadministration.product.status."
                + this.name());
        return displayStatusString;
    }

}
