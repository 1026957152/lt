package com.lt.dom.otcenum;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

import java.util.Locale;
import java.util.ResourceBundle;

public enum EnumPreference {
    working_space("默认产品税务代码",EnumValueType.string_),


            ;

    String barcode;
    EnumValueType type;

    public EnumValueType getType() {
        return type;
    }

    public void setType(EnumValueType type) {
        this.type = type;
    }

    EnumPreference(String barcode, EnumValueType type) {

        this.barcode = barcode;
        this.type = type;
    }

    @Autowired
    private MessageSource messageSource;


    private static ResourceBundle resourceBundle = ResourceBundle.getBundle("messages",
            Locale.getDefault());
/*
    @Override
    public String toString() {

        String displayStatusString = resourceBundle.getString("page.systemadministration.settings."
                + this.name());
        return displayStatusString;
    }
*/



    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class EnumResp {

        private String id;
        private String value_type;
        private String text;
        private String key;

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

        public void setKey(String key) {
            this.key = key;
        }

        public String getKey() {
            return key;
        }
    }

    public static EnumResp of(EnumPreference e) {
        EnumResp enumResp = new EnumResp();
        enumResp.setText(e.toString());
        enumResp.setKey(e.name());
        enumResp.setId(e.name());
        enumResp.setValue_type(e.getType().name());
        return enumResp;
    }
}
