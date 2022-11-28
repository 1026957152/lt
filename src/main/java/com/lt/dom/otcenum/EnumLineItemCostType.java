package com.lt.dom.otcenum;

import com.lt.dom.OctResp.EnumResp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public enum EnumLineItemCostType {

    Cost_per_action("Cost per action"),
    Cost_per_click("Cost per click"),
    Cost_per_day("Cost per day"),
    Cost_per_mille("Cost per mille"),
    Cost_per_thousand_Active_View_viewable_impressions("Cost per thousand Active View viewable impressions"),
    Cost_per_thousand_in_target_impressions("Cost per thousand in-target impressions"),

    ;





    public static List<EnumResp> EnumList() {
        return Arrays.stream(EnumLineItemCostType.values()).map(x->{
            EnumResp enumResp = new EnumResp();
            enumResp.setId(x.name());
            //  enumResp.setName(x.name());
            enumResp.setText(x.toString());

            return enumResp;
        }).collect(Collectors.toList());
    }


    public static List<EnumResp> from(List<EnumLineItemCostType> supportProductType) {
        return supportProductType.stream().map(x->{
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

    EnumLineItemCostType(String name) {
        this.name = name;
    }

    @Override
    public String toString() {

        String displayStatusString = resourceBundle.getString("page.systemadministration.product.type."
                + this.name());
        return displayStatusString;
    }


   /*
    Enumerations
            CPA
    Cost per action. The LineItem.lineItemType must be one of:
    LineItemType.SPONSORSHIP
    LineItemType.STANDARD
    LineItemType.BULK
    LineItemType.NETWORK
            CPC
    Cost per click. The LineItem.lineItemType must be one of:
    LineItemType.SPONSORSHIP
    LineItemType.STANDARD
    LineItemType.BULK
    LineItemType.NETWORK
    LineItemType.PRICE_PRIORITY
    LineItemType.HOUSE
            CPD
    Cost per day. The LineItem.lineItemType must be one of:
    LineItemType.SPONSORSHIP
    LineItemType.NETWORK
            CPM
    Cost per mille (cost per thousand impressions). The LineItem.lineItemType must be one of:
    LineItemType.SPONSORSHIP
    LineItemType.STANDARD
    LineItemType.BULK
    LineItemType.NETWORK
    LineItemType.PRICE_PRIORITY
    LineItemType.HOUSE
            VCPM
    Cost per thousand Active View viewable impressions. The LineItem.lineItemType must be LineItemType.STANDARD.
            CPM_IN_TARGET
    Cost per thousand in-target impressions. The LineItem.lineItemType must be LineItemType.STANDARD.
            UNKNOWN
    The value returned if the actual value is not exposed by the requested API version.*/
}
