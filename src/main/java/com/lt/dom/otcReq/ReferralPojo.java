package com.lt.dom.otcReq;

import com.lt.dom.otcenum.EnumReferralType;

import javax.validation.constraints.NotNull;

public class ReferralPojo {

    @NotNull
    private EnumReferralType type;


    private long long_value;

    private String meta_data;

    private String string_value;

    private Fill_up_passager_info fill_up_passager_info;

    public class Fill_up_passager_info {


    }


    public Fill_up_passager_info getFill_up_passager_info() {
        return fill_up_passager_info;
    }

    public void setFill_up_passager_info(Fill_up_passager_info fill_up_passager_info) {
        this.fill_up_passager_info = fill_up_passager_info;
    }

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
