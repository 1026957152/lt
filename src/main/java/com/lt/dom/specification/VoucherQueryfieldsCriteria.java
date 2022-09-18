package com.lt.dom.specification;


import com.lt.dom.otcenum.EnumVoucherStatus;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

public class VoucherQueryfieldsCriteria {


    private EnumVoucherStatus status;
    private long publishTo;

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }

    public EnumVoucherStatus getStatus() {
        return status;
    }

    public void setStatus(EnumVoucherStatus status) {
        this.status = status;
    }

    public void setPublishTo(long publishTo) {
        this.publishTo = publishTo;
    }

    public long getPublishTo() {
        return publishTo;
    }
}