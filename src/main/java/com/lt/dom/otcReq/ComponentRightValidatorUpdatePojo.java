package com.lt.dom.otcReq;

import com.lt.dom.otcenum.EnumValidatorRedemExtent;
import com.lt.dom.otcenum.EnumValidatorType;

import java.util.List;


public class ComponentRightValidatorUpdatePojo {  // 这个就是机器了啊


    private List<Long> item_ids;

    public List<Long> getItem_ids() {
        return item_ids;
    }

    public void setItem_ids(List<Long> item_ids) {
        this.item_ids = item_ids;
    }

    private EnumValidatorType type; //指定人工，机器, 所有人工

    public EnumValidatorType getType() {
        return type;
    }

    public void setType(EnumValidatorType type) {
        this.type = type;
    }


    private List<EnumValidatorRedemExtent> extents; //指定人工，机器, 所有人工

    public List<EnumValidatorRedemExtent> getExtents() {
        return extents;
    }

    public void setExtents(List<EnumValidatorRedemExtent> extents) {
        this.extents = extents;
    }
}
