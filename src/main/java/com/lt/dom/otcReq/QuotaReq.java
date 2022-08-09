package com.lt.dom.otcReq;


import com.lt.dom.otcenum.EnumClaimOrRedeem;
import com.lt.dom.otcenum.EnumClainQuotaType;
import com.lt.dom.otcenum.EnumQuotaType;

import javax.validation.constraints.NotNull;


public class QuotaReq {




    @NotNull
    private Long compaign;
    @NotNull
    private EnumClaimOrRedeem claim_redeem;
    @NotNull
    private EnumQuotaType type;
    @NotNull
    private String name;
    @NotNull
    private long quota;


    private long supplier; // 公司配额
    private long scenario;

    public EnumClaimOrRedeem getClaim_redeem() {
        return claim_redeem;
    }

    public void setClaim_redeem(EnumClaimOrRedeem claim_redeem) {
        this.claim_redeem = claim_redeem;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
