package com.lt.dom.otcenum;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.lt.dom.OctResp.EnumResp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public enum EnumSettings {
    default_product_tax_code("barcode",EnumValueType.string_),
    default_shipping_tax_code("reference number",EnumValueType.string_),
    home_page_high_Quality_Product_recommendation("reference number",EnumValueType.int_),



    ;

    String barcode;
    EnumValueType type;

    public EnumValueType getType() {
        return type;
    }

    public void setType(EnumValueType type) {
        this.type = type;
    }

    EnumSettings(String barcode, EnumValueType type) {

        this.barcode = barcode;
        this.type = type;
    }

    @Autowired
    private MessageSource messageSource;


    private static ResourceBundle resourceBundle = ResourceBundle.getBundle("messages",
            Locale.getDefault());
    @Override
    public String toString() {

        String displayStatusString = resourceBundle.getString("page.systemadministration.settings."
                + this.name());
        return displayStatusString;
    }



    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class EnumResp {

        private String id;
        private String value_type;
        private String text;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getValue_type() {
            return value_type;
        }

        public void setValue_type(String value_type) {
            this.value_type = value_type;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }
    }

    public static EnumResp of(EnumSettings e) {
        EnumResp enumResp = new EnumResp();
        enumResp.setText(e.toString());
        enumResp.setId(e.name());
        enumResp.setValue_type(e.getType().name());
        return enumResp;
    }
}
