package com.lt.dom.otcReq;


import com.lt.dom.oct.Quota;
import com.lt.dom.otcenum.EnumQuotaClaimOrRedeem;
import com.lt.dom.otcenum.EnumQuotaType;

import javax.validation.constraints.NotNull;


public class QuotaReq {





    private EnumQuotaClaimOrRedeem claim_redeem;
    @NotNull
    private EnumQuotaType type;
    @NotNull
  //  private String name = "默认名称";
    @NotNull
    private long quota;


    private long supplier; // 公司配额
    private long scenario;


    public Object id;
    private String name;
    private String type_text;
    private String activity_text;
    private long valueList;

    public Object getId() {
        return id;
    }

    public void setId(Object id) {
        this.id = id;
    }

    public static QuotaReq from(Quota x) {
        QuotaReq quotaReq = new QuotaReq();
        quotaReq.setQuota(x.getQuota());
        quotaReq.setScenario(x.getScenario());
        quotaReq.setValueList(x.getValueList());
        quotaReq.setClaim_redeem(x.getClaimRedeem());
        quotaReq.setActivity_text(x.getClaimRedeem().toString());
        quotaReq.setName(x.getName());
        quotaReq.setSupplier(x.getSupplier());
        quotaReq.setType(x.getType());
        quotaReq.setType_text(x.getType().toString());
        return quotaReq;
    }

    public EnumQuotaClaimOrRedeem getClaim_redeem() {
        return claim_redeem;
    }

    public void setClaim_redeem(EnumQuotaClaimOrRedeem claim_redeem) {
        this.claim_redeem = claim_redeem;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public void setType_text(String type_text) {
        this.type_text = type_text;
    }

    public String getType_text() {
        return type_text;
    }

    public void setActivity_text(String activity_text) {
        this.activity_text = activity_text;
    }

    public String getActivity_text() {
        return activity_text;
    }

    public void setValueList(long valueList) {
        this.valueList = valueList;
    }

    public long getValueList() {
        return valueList;
    }
}
