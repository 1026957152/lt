package com.lt.dom.otcenum;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public enum EnumDocumentType {
    estimate("estimate"),
    insurance_policy("invoice"),
    contract("contract"),

    photo("photo"),
    bill("bill"),

    campaign_logo("campaign_logo"),
    supplier_business_licence("supplier_business_licence"),
    scenario_image("scenario_image"),
    scenario_image_small("scenario_image_small"),



    business_license("business_license"),
    license("license"),
    liability_insurance("liability_insurance"),

    license_for_opening_bank_account("license_for_opening_bank_account"),


    attraction_photos("license_for_opening_bank_account"),
    product_photos("license_for_opening_bank_account"),

    attraction_thumb("license_for_opening_bank_account"),
    product_thumb("license_for_opening_bank_account"),

    attraction_video("attraction_video"),

    index_carousel("index_carousel"),


    featured_photo("featured_photo"),
    default_photo("featured_photo"),

    home_page_feature_logo("home_page_feature_logo"),

    ;


    private static ResourceBundle resourceBundle = ResourceBundle.getBundle("messages",
            Locale.getDefault());

    public String name;

    @Autowired
    private MessageSource messageSource;

    EnumDocumentType(String name) {

        this.name = name;
    }

    @Override
    public String toString() {

        String displayStatusString = resourceBundle.getString("page.systemadministration.enum.document.type."
                + this.name());
        return displayStatusString;
    }
}
