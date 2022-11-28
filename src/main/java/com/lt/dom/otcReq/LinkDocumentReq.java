package com.lt.dom.otcReq;

import com.lt.dom.OctResp.AttributeResp;
import com.lt.dom.otcReq.product.ProductGiftVoucherPojo;
import com.lt.dom.otcenum.EnumAvailabilityType;
import com.lt.dom.otcenum.EnumDocumentType;
import com.lt.dom.otcenum.EnumProductPricingType;
import com.lt.dom.otcenum.EnumProductPricingTypeByPerson;
import com.lt.dom.vo.ByHour;
import com.lt.dom.vo.ByItem;
import com.lt.dom.vo.ByPerson;
import com.lt.dom.vo.Fixed;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

public class LinkDocumentReq {
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    private String text;
    @NotNull
    private String tempDocumentCode;
    @NotNull
    private String objectCode;
    @NotNull
    private EnumDocumentType documentType;

    public String getTempDocumentCode() {
        return tempDocumentCode;
    }

    public void setTempDocumentCode(String tempDocumentCode) {
        this.tempDocumentCode = tempDocumentCode;
    }

    public String getObjectCode() {
        return objectCode;
    }

    public void setObjectCode(String objectCode) {
        this.objectCode = objectCode;
    }

    public EnumDocumentType getDocumentType() {
        return documentType;
    }

    public void setDocumentType(EnumDocumentType documentType) {
        this.documentType = documentType;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }

}
