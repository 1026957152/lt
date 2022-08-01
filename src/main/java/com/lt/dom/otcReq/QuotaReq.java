package com.lt.dom.otcReq;


import com.lt.dom.otcenum.EnumClainQuotaType;
import com.lt.dom.otcenum.EnumQuotaType;


public class QuotaReq {




    private long compaign;

    private EnumQuotaType type;

    private long quota;


    private long supplier; // 公司配额
    private long scenario;



    public long getCompaign() {
        return compaign;
    }

    public void setCompaign(long compaign) {
        this.compaign = compaign;
    }

    public long getQuota() {
        return quota;
    }

    public void setQuota(long quota) {
        this.quota = quota;
    }



    public long getSupplier() {
        return supplier;
    }

    public void setSupplier(long supplier) {
        this.supplier = supplier;
    }


    public EnumQuotaType getType() {
        return type;
    }

    public void setType(EnumQuotaType type) {
        this.type = type;
    }

    public long getScenario() {
        return scenario;
    }

    public void setScenario(long scenario) {
        this.scenario = scenario;
    }
}
