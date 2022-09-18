package com.lt.dom.specification;

import com.lt.dom.otcenum.EnumSupplierStatus;
import com.lt.dom.otcenum.EnumSupplierType;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.springframework.web.bind.annotation.RequestParam;

public class SupplierSearchQuery {

    String search;
    EnumSupplierType type;
    EnumSupplierStatus status;

    public EnumSupplierType getType() {
        return type;
    }

    public void setType(EnumSupplierType type) {
        this.type = type;
    }

    public EnumSupplierStatus getStatus() {
        return status;
    }

    public void setStatus(EnumSupplierStatus status) {
        this.status = status;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
