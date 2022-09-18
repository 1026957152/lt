package com.lt.dom.vo;

import com.lt.dom.oct.Supplier;
import com.lt.dom.oct.User;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

public class WrittenOffMerchantVo {
    private Supplier supplier;
    private UserVo user;

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }

    public WrittenOffMerchantVo(Supplier supplier, UserVo user) {
        this.supplier = supplier;
        this.user = user;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    public UserVo getUser() {
        return user;
    }

    public void setUser(UserVo user) {
        this.user = user;
    }
}
