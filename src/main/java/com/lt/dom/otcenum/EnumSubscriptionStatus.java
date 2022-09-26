package com.lt.dom.otcenum;

import com.lt.dom.OctResp.EnumResp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public enum EnumSubscriptionStatus {
    active("1"),
    deleted("1024"),
    draft("0"),
    inactive("7"),

    ;


  //  active: The product is ready to sell and is available to customers on the online store, sales channels, and apps. By default, existing products are set to active.
   // archived: The product is no longer being sold and isn't available to customers on sales channels and apps.
   // draft: T

    public static List<EnumResp> from() {
        return Arrays.asList(EnumSubscriptionStatus.values()).stream().map(x->{
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

    EnumSubscriptionStatus(String name) {
        this.name = name;
    }

    @Override
    public String toString() {

        String displayStatusString = resourceBundle.getString("page.systemadministration.product.status."
                + this.name());
        return displayStatusString;
    }

}
