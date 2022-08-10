package com.lt.dom.otcenum;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

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


    ;


    EnumDocumentType(String barcode) {

    }
}
