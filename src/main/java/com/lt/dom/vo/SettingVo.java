package com.lt.dom.vo;

import com.lt.dom.OctResp.EnumResp;
import com.lt.dom.OctResp.PhotoResp;
import com.lt.dom.otcenum.EnumSettings;
import org.springframework.hateoas.EntityModel;

import java.util.List;
import java.util.Map;

public class SettingVo {


    private String default_product_tax_code;

    private String default_shipping_tax_code;
    private List<EntityModel> items;
    private Integer home_page_high_Quality_Product_recommendation;
    private Map<EnumSettings, EntityModel> itemMap;
    private String terms_and_conditions;
    private PhotoResp logo;
    private Boolean SMS_notification_for_new_reservation_requests;

    public String getDefault_product_tax_code() {
        return default_product_tax_code;
    }

    public void setDefault_product_tax_code(String default_product_tax_code) {
        this.default_product_tax_code = default_product_tax_code;
    }

    public String getDefault_shipping_tax_code() {
        return default_shipping_tax_code;
    }

    public void setDefault_shipping_tax_code(String default_shipping_tax_code) {
        this.default_shipping_tax_code = default_shipping_tax_code;
    }

    public  void setItems(List<EntityModel> items) {
        this.items = items;
    }

    public List<EntityModel> getItems() {
        return items;
    }

    public void setHome_page_high_Quality_Product_recommendation(Integer home_page_high_quality_product_recommendation) {
        this.home_page_high_Quality_Product_recommendation = home_page_high_quality_product_recommendation;
    }

    public Integer getHome_page_high_Quality_Product_recommendation() {
        return home_page_high_Quality_Product_recommendation;
    }

    public void setItemMap(Map<EnumSettings, EntityModel> itemMap) {
        this.itemMap = itemMap;
    }

    public Map<EnumSettings, EntityModel> getItemMap() {
        return itemMap;
    }

    public void setTerms_and_conditions(String terms_and_conditions) {
        this.terms_and_conditions = terms_and_conditions;
    }

    public String getTerms_and_conditions() {
        return terms_and_conditions;
    }

    public void setLogo(PhotoResp logo) {
        this.logo = logo;
    }

    public PhotoResp getLogo() {
        return logo;
    }

    public Boolean getSMS_notification_for_new_reservation_requests() {
        return SMS_notification_for_new_reservation_requests;
    }

    public void setSMS_notification_for_new_reservation_requests(Boolean sms_notification_for_new_reservation_requests) {
        this.SMS_notification_for_new_reservation_requests = sms_notification_for_new_reservation_requests;
    }
}
