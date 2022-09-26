package com.lt.dom.oct;


import com.lt.dom.OctResp.CampaignResp;
import com.lt.dom.otcenum.EnumQuotaClaimOrRedeem;
import com.lt.dom.otcenum.EnumQuotaType;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

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

    private long supplier; //


    private long scenario;

    @NotNull
    private EnumQuotaClaimOrRedeem claimRedeem;
    private String name;
    
//##@Column(unique=true) 
private String code;
    private long valueList;

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

    public void setClaimRedeem(EnumQuotaClaimOrRedeem claim_redeem) {
        this.claimRedeem = claim_redeem;
    }

    public EnumQuotaClaimOrRedeem getClaimRedeem() {
        return claimRedeem;
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

    public void setCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setValueList(long valueList) {
        this.valueList = valueList;
    }

    public long getValueList() {
        return valueList;
    }



    private Long total_published = 0l;
    private Long total_bulk_published = 0l;
    private Long total_bulk_writenoff = 0l;
    private Long totol = 0l;
    private Long total_unpublished = 0l;
    private Long total_redeemed = 0l;
    private Long total_failed = 0l;
    private Long total_succeeded = 0l;

    public Long getTotal_published() {
        return total_published;
    }

    public void setTotal_published(Long total_published) {
        this.total_published = total_published;
    }

    public Long getTotal_bulk_published() {
        return total_bulk_published;
    }

    public void setTotal_bulk_published(Long total_bulk_published) {
        this.total_bulk_published = total_bulk_published;
    }

    public Long getTotal_bulk_writenoff() {
        return total_bulk_writenoff;
    }

    public void setTotal_bulk_writenoff(Long total_bulk_writenoff) {
        this.total_bulk_writenoff = total_bulk_writenoff;
    }

    public Long getTotol() {
        return totol;
    }

    public void setTotol(Long totol) {
        this.totol = totol;
    }

    public Long getTotal_unpublished() {
        return total_unpublished;
    }

    public void setTotal_unpublished(Long total_unpublished) {
        this.total_unpublished = total_unpublished;
    }

    public Long getTotal_redeemed() {
        return total_redeemed;
    }

    public void setTotal_redeemed(Long total_redeemed) {
        this.total_redeemed = total_redeemed;
    }

    public Long getTotal_failed() {
        return total_failed;
    }

    public void setTotal_failed(Long total_failed) {
        this.total_failed = total_failed;
    }

    public Long getTotal_succeeded() {
        return total_succeeded;
    }

    public void setTotal_succeeded(Long total_succeeded) {
        this.total_succeeded = total_succeeded;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
