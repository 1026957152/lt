package com.lt.dom.otcenum;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

import java.util.Locale;
import java.util.ResourceBundle;

public enum EnumSettingSpace {
    default_("default_seting_key",EnumValueType.string_),
 ;

    String key;
    EnumValueType type;

    public EnumValueType getType() {
        return type;
    }

    public void setType(EnumValueType type) {
        this.type = type;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    EnumSettingSpace(String key, EnumValueType type) {

        this.key = key;
        this.type = type;
    }

}
