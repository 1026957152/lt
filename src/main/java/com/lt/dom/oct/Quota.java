package com.lt.dom.oct;


import com.lt.dom.otcenum.EnumClaimOrRedeem;
import com.lt.dom.otcenum.EnumQuotaType;

import javax.persistence.*;

@Entity
public class Quota {
    @Version
    private Integer version;
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Id
    private long id;


    private long compaign;

    private EnumQuotaType type;

    private long quota;
    private boolean clain;  // 是否需要 clain

    private long value_list; //
    private long supplier; //


    private long scenario;
    private EnumClaimOrRedeem claim_redeem;
    private String name;

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

    public void setClaim_redeem(EnumClaimOrRedeem claim_redeem) {
        this.claim_redeem = claim_redeem;
    }

    public EnumClaimOrRedeem getClaim_redeem() {
        return claim_redeem;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setSupplier(long supplier) {
        this.supplier = supplier;
    }

    public long getSupplier() {
        return supplier;
    }
}
