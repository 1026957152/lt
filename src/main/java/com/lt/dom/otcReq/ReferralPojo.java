package com.lt.dom.otcReq;

import com.lt.dom.otcenum.EnumReferralType;

import javax.validation.constraints.NotNull;

public class ReferralPojo {

    @NotNull
    private EnumReferralType type;


    private long long_value;

    private String meta_data;

    private String string_value;

    public long getLong_value() {
        return long_value;
    }

    public void setLong_value(long long_value) {
        this.long_value = long_value;
    }

    public String getMeta_data() {
        return meta_data;
    }

    public void setMeta_data(String meta_data) {
        this.meta_data = meta_data;
    }

    public String getString_value() {
        return string_value;
    }

    public void setString_value(String string_value) {
        this.string_value = string_value;
    }

    public EnumReferralType getType() {
        return type;
    }

    public void setType(EnumReferralType type) {
        this.type = type;
    }
}
