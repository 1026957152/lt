package com.lt.dom.otcReq;


import javax.persistence.Entity;
import javax.persistence.Id;


public class ClainQuotaPojo {





    private long compaign;

    private long quota;
    private boolean clain;  // 是否需要 clain

    private long supplier; // 公司配额
    private long scheduling;  //时间配额。

    private long byPersonCount;  //没人限领一张。

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

    public boolean isClain() {
        return clain;
    }

    public void setClain(boolean clain) {
        this.clain = clain;
    }

    public long getSupplier() {
        return supplier;
    }

    public void setSupplier(long supplier) {
        this.supplier = supplier;
    }

    public long getScheduling() {
        return scheduling;
    }

    public void setScheduling(long scheduling) {
        this.scheduling = scheduling;
    }

    public long getByPersonCount() {
        return byPersonCount;
    }

    public void setByPersonCount(long byPersonCount) {
        this.byPersonCount = byPersonCount;
    }
}
