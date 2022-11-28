package com.lt.dom.otcenum;

import com.lt.dom.OctResp.EnumResp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public enum EnumProductPricingTypeByPersonGroupType {
    Total("Everyone"),
    Per_person("Per person"),


    ;
    public static List<EnumResp> EnumList() {
        return Arrays.stream(EnumProductPricingTypeByPersonGroupType.values()).map(x->{

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

    EnumProductPricingTypeByPersonGroupType(String name) {
        this.name = name;
    }

    //@Override
    public String to_String() {

        String displayStatusString = resourceBundle.getString("page.systemadministration.product.price.type.byperson.type."
                + this.name());
        return displayStatusString;
    }

}
