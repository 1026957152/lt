package com.lt.dom.oct;


import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Version;

@Entity
public class Partner extends Base{

    private long supplier;
    private long partner;
    private String code;
    private String shareCode;


    public long getSupplier() {
        return supplier;
    }

    public void setSupplier(long supplierId) {
        this.supplier = supplierId;
    }

    public long getPartner() {
        return partner;
    }

    public void setPartner(long partnerId) {
        this.partner = partnerId;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setShareCode(String shareCode) {
        this.shareCode = shareCode;
    }

    public String getShareCode() {
        return shareCode;
    }
}
