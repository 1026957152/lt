package com.lt.dom.otcReq;

import com.lt.dom.OctResp.AttributeResp;
import com.lt.dom.otcenum.EnumConfirmationType;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.util.List;

public class ProductEditAboutTabPojo {


    List<AttributeEditReq> attributes;



    public List<AttributeEditReq> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<AttributeEditReq> attributes) {
        this.attributes = attributes;
    }





    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }


}
