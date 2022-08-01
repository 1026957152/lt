package com.lt.dom.oct;


import com.lt.dom.otcenum.EnumQuotaType;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Quota {

    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Id
    private long id;


    private long compaign;

    private EnumQuotaType type;

    private long quota;
    private boolean clain;  // 是否需要 clain

    private long supplier; // 公司配额
    private long scenario;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

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


    public EnumQuotaType getType() {
        return type;
    }

    public void setType(EnumQuotaType type) {
        this.type = type;
    }

    public void setScenario(long scenario) {
        this.scenario = scenario;
    }

    public long getScenario() {
        return scenario;
    }
}
