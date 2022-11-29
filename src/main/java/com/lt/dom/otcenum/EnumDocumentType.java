package com.lt.dom.otcenum;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.TreeMap;

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
    product_video("license_for_opening_bank_account"),

    attraction_thumb("license_for_opening_bank_account"),
    product_thumb("license_for_opening_bank_account"),

    attraction_video("attraction_video"),

    index_carousel("index_carousel"),


    featured_photo("featured_photo"),
    default_photo("featured_photo"),

    home_page_feature_logo("home_page_feature_logo"),



    theatreImageIcon("theatreImageIcon"),

    theatreImageLarge("theatreImageLarge"),
    theatreImageStandard("theatreImageStandard"),
    theatreImageThumbnail("theatreImageThumbnail"),


    museum_icon("theatreImageIcon"),

    museum_large("theatreImageLarge"),
    museum_standard("theatreImageStandard"),
    museum_thumbnail("theatreImageThumbnail"),

    artwork_icon("theatreImageIcon"),

    artwork_large("theatreImageLarge"),
    artwork_standard("theatreImageStandard"),
    artwork_thumbnail("theatreImageThumbnail"),
    artwork_audio("theatreImageThumbnail"),




    city_walk_large("theatreImageLarge"),
    city_walk_standard("theatreImageStandard"),
    city_walk_thumbnail("theatreImageThumbnail"),
    city_walk_audio("theatreImageThumbnail"),


    WayPoint_audio("theatreImageThumbnail"),
    WayPoint_thumbnail("theatreImageThumbnail"),

    makeplan_resource("theatreImageThumbnail"),
    supllier_logo("theatreImageThumbnail"),


    logo("campaign_logo"),
    card_cover("campaign_logo"),


    document_back_file("campaign_logo"),
    document_front_file("campaign_logo"),

    movie_vidio("campaign_logo"),

    movie_cover("campaign_logo"),
    movie_star_photo("campaign_logo"),


    place_photo("license_for_opening_bank_account"),


    blog_cover("license_for_opening_bank_account"),

    user_avatar("license_for_opening_bank_account"),

    extra_photo("campaign_logo"),
    region_photo("campaign_logo"),
    car_photo("campaign_logo"),
    car_brand_logo("campaign_logo"),


    author_avatar("campaign_logo"),
    ;


    private static ResourceBundle resourceBundle = ResourceBundle.getBundle("messages",
            Locale.getDefault());

    public String name;

    @Autowired
    private MessageSource messageSource;

    EnumDocumentType(String name) {

        this.name = name;
    }

   // @Override
    public String to_String() {

        String displayStatusString = resourceBundle.getString("page.systemadministration.enum.document.type."
                + this.name());
        return displayStatusString;
    }


    private static Map<Integer, EnumDocumentType> ss = new TreeMap<Integer,EnumDocumentType>();
    private static final int START_VALUE = 0;
    private int value;

    static {
        for(int i=0;i<values().length;i++)
        {
            values()[i].value = START_VALUE + i;
            ss.put(values()[i].value, values()[i]);
        }
    }

    public static EnumDocumentType fromInt(int i) {
        return ss.get(i);
    }

    public int value() {
        return value;
    }
}
