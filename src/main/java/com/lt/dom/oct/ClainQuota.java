package com.lt.dom.oct;


import com.lt.dom.otcenum.EnumClainQuotaType;
import com.lt.dom.otcenum.EnumQuotaType;

import javax.persistence.*;

@Entity
public class ClainQuota {
    @Version
    private Integer version;

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private long id;
    private long compaign;
    private EnumClainQuotaType type;
    private long quota;
    private boolean clain;  // 是否需要 clain
    private long supplier; // 公司配额
    private long scenario;


    public void setCompaign(long compaign) {
        this.compaign = compaign;
    }

    public long getCompaign() {
        return compaign;
    }

    public long getQuota() {
        return quota;
    }

    public void setQuota(long quota) {
        this.quota = quota;
    }

    public void setSupplier(long supplier) {
        this.supplier = supplier;
    }

    public long getSupplier() {
        return supplier;
    }

    public void setScenario(long scenario) {
        this.scenario = scenario;
    }

    public long getScenario() {
        return scenario;
    }

    public void setType(EnumClainQuotaType type) {
        this.type = type;
    }

    public EnumClainQuotaType getType() {
        return type;
    }
}
