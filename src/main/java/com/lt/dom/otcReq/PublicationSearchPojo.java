package com.lt.dom.otcReq;


import com.lt.dom.otcenum.EnumVoucherStatus;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.util.List;
import java.util.Map;

public class PublicationSearchPojo {



    private String source_id;

    private long campaign_id;
    private long campaignCount;
    private String campaignName;


    private List<Long> voucher_id;
    private String customerEmail;
    private String customerSourceId;
    private String customerName;
    private Map<String,String> metadata;
    private EnumVoucherStatus status;

    public EnumVoucherStatus getStatus() {
        return status;
    }

    public void setStatus(EnumVoucherStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
