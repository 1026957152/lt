package com.lt.dom.otcReq;

import com.lt.dom.OctResp.AttributeResp;
import com.lt.dom.OctResp.RestrictionResp;
import com.lt.dom.otcenum.EnumAvailabilityType;
import com.lt.dom.otcenum.EnumConfirmationType;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import javax.validation.Valid;
import javax.validation.constraints.Size;
import java.util.List;

public class ProductEditRuleTabPojo {



    private EnumConfirmationType confirmationType;

    private RestrictionReq restriction;

    public RestrictionReq getRestriction() {
        return restriction;
    }

    public void setRestriction(RestrictionReq restriction) {
        this.restriction = restriction;
    }

    List<AttributeResp> attributes;


    public List<AttributeResp> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<AttributeResp> attributes) {
        this.attributes = attributes;
    }





    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }


    public EnumConfirmationType getConfirmationType() {
        return confirmationType;
    }

    public void setConfirmationType(EnumConfirmationType confirmationType) {
        this.confirmationType = confirmationType;
    }
}
