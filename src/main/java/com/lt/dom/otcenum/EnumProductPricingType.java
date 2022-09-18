package com.lt.dom.otcenum;

import com.lt.dom.OctResp.EnumResp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public enum EnumProductPricingType {
    ByPerson("By Person"),
    ByItem("By Item"),
    Fixed("Fixed"),
    ByDay("By Day"),
    ByHour("By Hour"),
    ByMinute("By Minute"),
    ;




    public static List<EnumResp> from() {
        return Arrays.stream(EnumProductPricingType.values()).map(x->{
            EnumResp enumResp = new EnumResp();
            enumResp.setId(x.name());
            //  enumResp.setName(x.name());
            enumResp.setText(x.toString());

            if(x.equals(EnumProductPricingType.ByPerson)){
                enumResp.setSubitems(Arrays.stream(EnumProductPricingTypeByPerson.values()).map(x_by_person->{
                    EnumResp enumResp_by_person = new EnumResp();
                    enumResp_by_person.setId(x_by_person.name());
                    //  enumResp.setName(x.name());
                    enumResp_by_person.setText(x_by_person.toString());
                    return enumResp_by_person;
                }).collect(Collectors.toList()));

            }
            return enumResp;
        }).collect(Collectors.toList());
    }


    private static ResourceBundle resourceBundle = ResourceBundle.getBundle("messages",
            Locale.getDefault());

    public String name;

    @Autowired
    private MessageSource messageSource;

    EnumProductPricingType(String name) {
        this.name = name;
    }

    @Override
    public String toString() {

        String displayStatusString = resourceBundle.getString("page.systemadministration.product.price.type."
                + this.name());
        return displayStatusString;
    }

}
