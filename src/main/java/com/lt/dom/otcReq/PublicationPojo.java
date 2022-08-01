package com.lt.dom.otcReq;


import com.lt.dom.oct.Customer;
import com.lt.dom.otcenum.EnumPublicationObjectType;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.util.List;
import java.util.Map;

public class PublicationPojo {

    private long supplier;
    private long user;

    public long getSupplier() {
        return supplier;
    }

    public void setSupplier(long supplier) {
        this.supplier = supplier;
    }

    public long getUser() {
        return user;
    }

    public void setUser(long user) {
        this.user = user;
    }

    private String source_id;


    private long campaignCount;
    private String campaignName;


    private List<Long> voucher_id;
    private String customerEmail;
    private String customerSourceId;
    private String customerName;
    private Map<String,String> metadata;


    private EnumPublicationObjectType type;

    public String getSource_id() {
        return source_id;
    }

    public void setSource_id(String source_id) {
        this.source_id = source_id;
    }



    public long getCampaignCount() {
        return campaignCount;
    }

    public void setCampaignCount(long campaignCount) {
        this.campaignCount = campaignCount;
    }

    public String getCampaignName() {
        return campaignName;
    }

    public void setCampaignName(String campaignName) {
        this.campaignName = campaignName;
    }

    public List<Long> getVoucher_id() {
        return voucher_id;
    }

    public void setVoucher_id(List<Long> voucher_id) {
        this.voucher_id = voucher_id;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public String getCustomerSourceId() {
        return customerSourceId;
    }

    public void setCustomerSourceId(String customerSourceId) {
        this.customerSourceId = customerSourceId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public Map<String, String> getMetadata() {
        return metadata;
    }

    public void setMetadata(Map<String, String> metadata) {
        this.metadata = metadata;
    }

    public EnumPublicationObjectType getType() {
        return type;
    }

    public void setType(EnumPublicationObjectType type) {
        this.type = type;
    }
}
